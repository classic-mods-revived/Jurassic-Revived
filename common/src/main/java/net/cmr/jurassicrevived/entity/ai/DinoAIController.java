package net.cmr.jurassicrevived.entity.ai;

import net.cmr.jurassicrevived.config.JRConfig;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DinoAIController {

	private static final float VITAL_DECAY_MULTIPLIER = 0.05f;
	private static final double TERRITORIAL_ROAM_SPEED_MULTIPLIER = 2.0D;
	private static final double MIN_ROAM_SPEED = 0.55D;
	private static final double ROAM_SPEED_MULTIPLIER = 1.25D;
	private static final double ICE_ROAM_SPEED_MULTIPLIER = 1.75D;
	private static final int ROAM_SELECTION_IDLE_TIME = 15;
	private static final float ROAM_SELECTION_CHANCE = 0.85f;
	private static final double FLYER_GROUND_WALK_TARGET_RANGE = 10.0D;
	private static final double WATER_SURFACE_BOOST = 0.11D;
	private static final double TERRESTRIAL_WATER_EXIT_BOOST = 0.18D;
	private static final double AVIAN_DIVE_SPEED = 0.10D;
	private static final double AVIAN_DIVE_MAX_DOWNWARD_SPEED = -0.12D;
	private static final double AVIAN_WATER_EXIT_BOOST = 0.10D;
	private static final int AVIAN_DIVE_RECOVERY_TICKS = 60;
	private static final int AVIAN_MIN_SURFACE_RECOVERY_TICKS = 20;
	private static final int AVIAN_MAX_UNDERWATER_DIVE_TICKS = 40;
	private static final int AVIAN_BETWEEN_DIVE_REST_TICKS = 80;
	private static final double AVIAN_RECOVERY_HEIGHT_ABOVE_WATER = 5.0D;
	private static final double AVIAN_RECOVERY_TARGET_REACHED_DISTANCE_SQR = 2.25D;
	private static final double AVIAN_RECOVERY_HORIZONTAL_SPEED = 0.08D;
	private static final int ROAM_STATE_DURATION = 320;
	private static final int ROAM_RETARGET_INTERVAL = 80;
	private static final int NATURAL_BREEDING_CHECK_INTERVAL = 200;
	private static final int NATURAL_BREEDING_PAIR_CHANCE = 180;
	private static final int NATURAL_BREEDING_SELF_CHANCE = 2880;
	private static final double NATURAL_BREEDING_PARTNER_RANGE = 12.0D;
	private static final int FULL_HUNGER_REGEN_INTERVAL = 80;
	private static final int KILL_REGEN_DURATION_TICKS = 100;
	private static final double AVIAN_RECOVERY_AIR_UPWARD_SPEED = 0.08D;
	private static final double AVIAN_RECOVERY_MAX_UPWARD_SPEED = 0.22D;
	private static final int AVIAN_MAX_TARGET_DEPTH_BELOW_SURFACE = 10;
	private static final int AVIAN_WATER_SURFACE_SEARCH_UP = 16;
	private static final boolean AVIAN_UNDERWATER_HUNTING_ENABLED = true;
	private static final int HERBIVORE_SELF_FEED_INTERVAL = 40;
	private static final float HERBIVORE_SELF_FEED_CHANCE = 0.35f;
	private static final int HERBIVORE_BROWSE_HORIZONTAL_RANGE = 2;
	private static final int HERBIVORE_BROWSE_VERTICAL_RANGE = 2;
	private static final float HERBIVORE_SELF_FEED_HUNGER_THRESHOLD = 0.75f;
	private static final float HERBIVORE_SELF_FEED_REPLENISHMENT_MULTIPLIER = 0.50f;


	private final DinoEntityBase dino;
    private State currentState = State.IDLE;

    private LivingEntity attackTarget;
    private Animal mateTarget; // Target for mating
    private BlockPos waterTarget;
    private BlockPos homePos; // Center of territory
    private Vec3 roamTarget;

    private int stateTimer = 0;
    private int pathRecalcTimer = 0;
    private int failedPathfindingAttempts = 0;
    private boolean isSelfBreeding = false; // For parthenogenesis
	private int breedingCheckCooldown = 0;
	private int avianDiveRecoveryTimer = 0;
	private int avianSurfaceRecoveryTimer = 0;
	private int avianDiveRestTimer = 0;
	private boolean avianDiveInProgress = false;
	private int avianUnderwaterDiveTimer = 0;
	private boolean avianDiveAttackSpent = false;
	private Vec3 avianRecoveryTarget;

    // Attack Cooldown Tracker
    private int attackCooldown = 0;

    public State getCurrentState() { return currentState; }
    public LivingEntity getAttackTarget() { return attackTarget; }
    public BlockPos getWaterTarget() { return waterTarget; }

    public enum State {
        IDLE,
        ROAMING,
        TERRITORIAL_ROAMING,
        CHASING,
        ATTACKING,
        FLEEING,
        SLEEPING,
        MATING, // New state
        DEFACATING, DROWNING, FLOCKING, HIBERNATING, HIDING, HUNTING, NESTING, RAMPAGING, SOCIALIZING
    }

    public DinoAIController(DinoEntityBase dino) {
        this.dino = dino;
    }

	public void tick() {
		if (homePos == null) homePos = dino.blockPosition();

		handleFloating();

		updateSensors();
		checkBreedingReadiness();

		switch (currentState) {
			case IDLE -> tickIdle();
			case ROAMING -> tickRoaming();
			case TERRITORIAL_ROAMING -> tickTerritorialRoaming();
			case CHASING -> tickChasing();
			case ATTACKING -> tickAttacking();
			case FLEEING -> tickFleeing();
			case SLEEPING -> tickSleeping();
			case MATING -> tickMating();
		}

		stateTimer++;
		if (attackCooldown > 0) attackCooldown--;
		if (breedingCheckCooldown > 0) breedingCheckCooldown--;

		if (avianDiveRecoveryTimer > 0) {
			avianDiveRecoveryTimer--;
		}

		if (avianSurfaceRecoveryTimer > 0 && !dino.isInWater()) {
			avianSurfaceRecoveryTimer--;
		}

		if (avianDiveRestTimer > 0 && !dino.isInWater() && avianDiveRecoveryTimer <= 0 && avianSurfaceRecoveryTimer <= 0) {
			avianDiveRestTimer--;
		}

		if (isAvianWaterHunter()
		    && attackTarget != null
		    && currentState == State.CHASING
		    && !isRecoveringFromAvianDive()
		    && avianDiveRestTimer <= 0
		    && avianDiveAttackSpent
		    && isUnderwaterTarget(attackTarget)) {
			avianDiveAttackSpent = false;
			avianDiveInProgress = true;
			avianUnderwaterDiveTimer = 0;
			avianRecoveryTarget = null;
		}
	}

	private void handleFloating() {
		if (!dino.isInWater()) {
			return;
		}

		if (isAvianWaterHunter()) {
			return;
		}

		if (handleWaterMovementHelper(null)) {
			return;
		}

		double fluidHeight = dino.getFluidHeight(FluidTags.WATER);
		if (fluidHeight <= dino.getFluidJumpThreshold()) {
			return;
		}

		Vec3 velocity = dino.getDeltaMovement();

		if (velocity.y < WATER_SURFACE_BOOST) {
			dino.setDeltaMovement(
				velocity.x,
				Math.min(velocity.y + 0.03D, WATER_SURFACE_BOOST),
				velocity.z
			);
		}
	}

	public void onHurtBy(LivingEntity attacker) {
		if (isExcludedAttackTarget(attacker)) {
			return;
		}

		// Retaliate if we are capable of attacking (have damage attribute > 0)
		// Carnivores always attack back. Herbivores/others attack back if they have strength.
		// We SKIP the generic canAttack check here because if something hurt us,
		// we should try to fight back even if it's "too big" or technically invalid by roaming standards.
		boolean canFightBack = dino.getAttributeValue(Attributes.ATTACK_DAMAGE) > 0 && !dino.isBaby();

		if (canFightBack) {
			this.attackTarget = attacker;
			transitionTo(State.CHASING);
		} else {
			this.attackTarget = attacker;
			transitionTo(State.FLEEING);
		}
	}

    private void transitionTo(State newState) {
		// Handle Condition updates
		if (dino.dinoData != null) {
			if (newState == State.SLEEPING) dino.dinoData.addCondition(IDinoData.Condition.SLEEPING);
			else dino.dinoData.removeCondition(IDinoData.Condition.SLEEPING);
		}

		this.currentState = newState;
		this.stateTimer = 0;
		this.pathRecalcTimer = 0;
		this.failedPathfindingAttempts = 0;

		if (newState != State.CHASING && newState != State.ATTACKING) {
			this.avianDiveRecoveryTimer = 0;
			this.avianSurfaceRecoveryTimer = 0;
			this.avianDiveRestTimer = 0;
			this.avianDiveInProgress = false;
			this.avianUnderwaterDiveTimer = 0;
			this.avianDiveAttackSpent = false;
			this.avianRecoveryTarget = null;
		}

		// Reset sprinting if we aren't in a high-speed state
		if (newState != State.CHASING && newState != State.ATTACKING && newState != State.FLEEING) {
			dino.setSprinting(false);
		}

        // Do NOT stop navigation here if switching Chasing <-> Attacking to maintain momentum
        if (newState == State.IDLE || newState == State.ROAMING || newState == State.TERRITORIAL_ROAMING || newState == State.SLEEPING || newState == State.MATING) {
            this.dino.getNavigation().stop();
        }
    }

    // --- SENSORS ---

    private void updateSensors() {
        DinoEntityBase.DinoAIConfig config = dino.getAIConfig();

        // 1. Check for Mating (High priority)
        // If we are in love but not currently fighting or already mating, switch to mating.
        if (dino.isInLove() && currentState != State.CHASING && currentState != State.ATTACKING && currentState != State.FLEEING && currentState != State.MATING) {
            transitionTo(State.MATING);
        }

        // 2. Vitals Update
        if (dino.dinoData != null) {
            JRConfig jrConfig = JRConfigManager.get();

            float hungerDecay = jrConfig.hungerConsumption ? config.hungerDecay() * VITAL_DECAY_MULTIPLIER : 0.0f;
            float thirstDecay = jrConfig.waterConsumption ? config.thirstDecay() * VITAL_DECAY_MULTIPLIER : 0.0f;

            if (currentState == State.SLEEPING) {
                hungerDecay *= 0.5f;
                thirstDecay *= 0.5f;
                if (dino.tickCount % 40 == 0 && dino.getHealth() < dino.getMaxHealth()) {
                    dino.heal(1.0f);
                }
            }

            if (hungerDecay > 0.0f) {
                dino.dinoData.modifyHunger(-hungerDecay);
            } else if (!jrConfig.hungerConsumption) {
                dino.dinoData.setHunger(config.maxHunger());
            }

            if (thirstDecay > 0.0f) {
                float currentThirst = dino.dinoData.getThirst();
                dino.dinoData.setThirst(Math.max(0, currentThirst - thirstDecay));
            } else if (!jrConfig.waterConsumption) {
                dino.dinoData.setThirst(config.maxThirst());
            }

            float hunger = dino.dinoData.getHunger();
            float thirst = dino.dinoData.getThirst();

            if (hunger >= config.maxHunger() && dino.tickCount % FULL_HUNGER_REGEN_INTERVAL == 0 && dino.getHealth() < dino.getMaxHealth()) {
                dino.heal(1.0f);
            }

            if (hunger <= 0 || thirst <= 0) {
                if (currentState == State.SLEEPING) {
                    transitionTo(State.IDLE);
                }

                if (stateTimer % 20 == 0) {
                    if (hunger <= 0) {
                        dino.hurt(dino.damageSources().starve(), 1.0f);
                        dino.dinoData.addCondition(IDinoData.Condition.STARVING);
                    }
                    if (thirst <= 0) {
                        dino.hurt(dino.damageSources().dryOut(), 1.0f);
                        dino.dinoData.addCondition(IDinoData.Condition.DEHYDRATED);
                    }
                }
            } else {
                dino.dinoData.removeCondition(IDinoData.Condition.STARVING);
                dino.dinoData.removeCondition(IDinoData.Condition.DEHYDRATED);
            }

            if (hunger < 30) dino.dinoData.addCondition(IDinoData.Condition.HUNGRY);
            else dino.dinoData.removeCondition(IDinoData.Condition.HUNGRY);

            if (thirst < 30) dino.dinoData.addCondition(IDinoData.Condition.THIRSTY);
            else dino.dinoData.removeCondition(IDinoData.Condition.THIRSTY);

            if (dino.getHealth() < dino.getMaxHealth() * 0.25) dino.dinoData.addCondition(IDinoData.Condition.LOW_HEALTH);
            else dino.dinoData.removeCondition(IDinoData.Condition.LOW_HEALTH);
        }

        // 3. Sleep check
        if (currentState == State.IDLE || currentState == State.ROAMING || currentState == State.TERRITORIAL_ROAMING || currentState == State.SLEEPING) {
            if (dino.dinoData != null) {
                IDinoData.ActivityPattern activity = dino.dinoData.getActivityPattern();
                boolean isDay = dino.level().isDay();

                boolean shouldSleep = false;
                if (activity == IDinoData.ActivityPattern.DIURNAL && !isDay) shouldSleep = true;
                if (activity == IDinoData.ActivityPattern.NOCTURNAL && isDay) shouldSleep = true;

                if (dino.getLastHurtByMob() != null && dino.tickCount - dino.getLastHurtByMobTimestamp() < 200) {
                    shouldSleep = false;
                }

                if (shouldSleep && currentState != State.SLEEPING) {
                    transitionTo(State.SLEEPING);
                } else if (!shouldSleep && currentState == State.SLEEPING) {
                    transitionTo(State.IDLE);
                }
            }
        }

		// 4. Target Validation (Attack)
		if (attackTarget != null) {
			boolean shouldStop = false;

			// Basic checks
			if (!attackTarget.isAlive() || dino.distanceToSqr(attackTarget) > 64 * 64) {
				shouldStop = true;
			}

			if (isExcludedAttackTarget(attackTarget)) {
				shouldStop = true;
			}

			if (!canTargetInCurrentEnvironment(attackTarget)) {
				shouldStop = true;
			}

			// Player specific checks (Creative/Spectator/Peaceful)
			if (attackTarget instanceof Player player) {
				if (player.isCreative() || player.isSpectator()) shouldStop = true;
			}

			// Note: We deliberately do NOT call dino.canAttack(attackTarget) here.
			// canAttack() includes "preferences" (like size limits or whitelist) which should
			// be ignored if we are actively retaliating or hunting a valid target we already selected.

			if (shouldStop) {
				attackTarget = null;
				if (currentState == State.CHASING || currentState == State.ATTACKING) {
					transitionTo(State.IDLE);
				}
			}
		}

		// 5. Hunt check
		if ((currentState == State.IDLE || currentState == State.ROAMING || currentState == State.TERRITORIAL_ROAMING) && dino.isCarnivore()) {
			JRConfig jrConfig = JRConfigManager.get();
			boolean hungerConsumptionEnabled = jrConfig.hungerConsumption;
			boolean waterConsumptionEnabled = jrConfig.waterConsumption;
			boolean territorial = dino.dinoData != null && dino.dinoData.getAggression() == IDinoData.Aggression.TERRITORIAL;
			boolean shouldHunt;

			if (hungerConsumptionEnabled) {
				boolean hungry = dino.dinoData != null && dino.dinoData.getHunger() < 70;
				shouldHunt = hungry || (territorial && dino.dinoData != null && dino.dinoData.getHunger() < 90);
			} else {
				float baseChance = territorial ? 0.55f : 0.35f;

				if (!waterConsumptionEnabled) { 
					baseChance += territorial ? 0.30f : 0.25f;
				}

				shouldHunt = stateTimer % 80 == 0 && dino.getRandom().nextFloat() < baseChance;
			}

			if (shouldHunt && stateTimer % 10 == 0) {
				findTarget();
			}
		}

        // 6. Water check
        if ((currentState == State.IDLE || currentState == State.ROAMING || currentState == State.TERRITORIAL_ROAMING)) {
            if (dino.dinoData != null && dino.dinoData.getThirst() < 50 && waterTarget == null) {
                if (stateTimer % 10 == 0) findWater();
            }
        }
    }

    private void findWater() {
        // Increase vertical search range to find water even if we are high up
        BlockPos pos = BlockPos.findClosestMatch(dino.blockPosition(), 32, 16, p -> dino.level().getFluidState(p).is(FluidTags.WATER)).orElse(null);
        if (pos != null) {
            this.waterTarget = pos;
            dino.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), dino.getAIConfig().walkSpeed());
            if (currentState == State.IDLE) {
                float territoriality = dino.dinoData != null ? dino.dinoData.getTerritoriality() : 0.0f;
                if (dino.getRandom().nextFloat() < territoriality) {
                    transitionTo(State.TERRITORIAL_ROAMING);
                } else {
                    transitionTo(State.ROAMING);
                }
            }
        }
    }

    private void findTarget() {
        double range = dino.getAttributeValue(Attributes.FOLLOW_RANGE);
        if (range <= 0) range = 32.0;

        List<LivingEntity> nearby = dino.level().getEntitiesOfClass(LivingEntity.class,
                dino.getBoundingBox().inflate(range));

        List<LivingEntity> candidates = new ArrayList<>();

		for (LivingEntity e : nearby) {
			if (e == dino) continue;
			if (isExcludedAttackTarget(e)) continue;
			if (!dino.canAttack(e)) continue;
			if (!canTargetInCurrentEnvironment(e)) continue;

			if (e instanceof Player player) {
				// Apply motivation logic for Players
				boolean isTerritorial = dino.dinoData != null && dino.dinoData.getAggression() == IDinoData.Aggression.TERRITORIAL;
				boolean hungerConsumptionEnabled = JRConfigManager.get().hungerConsumption;
				boolean hungry = dino.dinoData != null && dino.dinoData.getHunger() < 70;
                boolean instinctivelyAggressive = !hungerConsumptionEnabled && dino.isCarnivore();

                boolean validPlayerTarget = false;
                if (isTerritorial) {
                    if (hungry || instinctivelyAggressive || dino.distanceToSqr(player) < 225) {
                        validPlayerTarget = true;
                    }
                } else {
                    if (hungry || instinctivelyAggressive) {
                        validPlayerTarget = true;
                    }
                }

                if (validPlayerTarget) {
                    candidates.add(player);
                }
            } else {
                // Animals are valid if canAttack passes (which checks size etc)
                candidates.add(e);
            }
        }

        // Sort candidates by distance to prefer closest valid target
        candidates.sort(Comparator.comparingDouble(dino::distanceToSqr));

        // Check pathfinding for the closest targets to ensure we can reach them
        int checks = 0;
        for (LivingEntity candidate : candidates) {
            if (checks >= 5) break; // Limit pathfinding checks to avoid lag
            checks++;

			if (AVIAN_UNDERWATER_HUNTING_ENABLED && isAvianWaterHunter() && isUnderwaterTarget(candidate)) {
				this.attackTarget = candidate;
				this.avianDiveInProgress = true;
				this.avianUnderwaterDiveTimer = 0;
				this.avianDiveAttackSpent = false;
				transitionTo(State.CHASING);
				return;
			}

                Path path = dino.getNavigation().createPath(candidate, 0);
            if (path != null) {
                // VERIFY PATH REACHES TARGET
                // The pathfinder may return a partial path that ends at a wall.
                // We check if the end point of the path is reasonably close to the target.
                Node endNode = path.getEndNode();
                if (endNode != null) {
                    double distToTargetSqr = candidate.distanceToSqr(endNode.x + 0.5, endNode.y + 0.5, endNode.z + 0.5);
                    // 25.0 = 5 blocks tolerance. If the path ends > 5 blocks away from target, it's likely obstructed.
                    if (distToTargetSqr < 25.0) {
                        this.attackTarget = candidate;
                        transitionTo(State.CHASING);
                        return;
                    }
                }
            }
        }
    }

	private boolean canTargetInCurrentEnvironment(LivingEntity target) {
		if (isUnderwaterTarget(target)) {
			return isAvianWaterHunter() && AVIAN_UNDERWATER_HUNTING_ENABLED;
		}

		if (isAvianWaterHunter()) {
			return true;
		}

		if (!isGroundCreature()) {
			return true;
		}

		return true;
	}

	private boolean isAvianWaterHunter() {
		return dino instanceof FlyingAnimal && dino.isCarnivore();
	}

	private boolean handleWaterMovementHelper(LivingEntity target) {
		if (isAvianWaterHunter()) {
			return handleAvianWaterHuntingMovement(target);
		}

		if (isGroundCreature()) {
			return handleTerrestrialWaterExitMovement();
		}

		return false;
	}

	private boolean handleAvianWaterHuntingMovement(LivingEntity target) {
		if (isRecoveringFromAvianDive()) {
			handleAvianDiveRecoveryMovement();
			return true;
		}

		if (target != null && target.isAlive() && target.isInWater()) {
			if (avianDiveRestTimer > 0) {
				handleAvianDiveRecoveryMovement();
				return true;
			}

			if (!isUnderwaterTarget(target)) {
				if (avianDiveInProgress || dino.isInWater()) {
					beginAvianDiveRecovery();
					return true;
				}

				avianDiveInProgress = false;
				avianUnderwaterDiveTimer = 0;
				return false;
			}

			if (dino.isInWater()) {
				avianUnderwaterDiveTimer++;
				if (avianUnderwaterDiveTimer > AVIAN_MAX_UNDERWATER_DIVE_TICKS) {
					beginAvianDiveRecovery();
					return true;
				}
			} else {
				avianUnderwaterDiveTimer = 0;
			}

			Vec3 toTarget = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D).subtract(dino.position());

			if (toTarget.lengthSqr() > 0.01D) {
				Vec3 dive = toTarget.normalize().scale(AVIAN_DIVE_SPEED);
				Vec3 velocity = dino.getDeltaMovement();

				dino.getNavigation().stop();
				faceAvianMovement(toTarget);

				dino.setDeltaMovement(
					velocity.x * 0.85D + dive.x,
					Math.max(AVIAN_DIVE_MAX_DOWNWARD_SPEED, velocity.y * 0.75D + dive.y),
					velocity.z * 0.85D + dive.z
				);
				dino.hasImpulse = true;
			}

			return true;
		}

		if (avianDiveInProgress && dino.isInWater()) {
			beginAvianDiveRecovery();
			handleAvianDiveRecoveryMovement();
			return true;
		}

		avianDiveInProgress = false;
		avianUnderwaterDiveTimer = 0;
		return false;
	}

	private boolean isRecoveringFromAvianDive() {
		return avianDiveRecoveryTimer > 0 || avianSurfaceRecoveryTimer > 0;
	}

	private void beginAvianDiveRecovery() {
		this.avianDiveRecoveryTimer = AVIAN_DIVE_RECOVERY_TICKS;
		this.avianSurfaceRecoveryTimer = AVIAN_MIN_SURFACE_RECOVERY_TICKS;
		this.avianDiveRestTimer = AVIAN_BETWEEN_DIVE_REST_TICKS;
		this.avianDiveInProgress = false;
		this.avianUnderwaterDiveTimer = 0;
		this.avianDiveAttackSpent = true;
		this.avianRecoveryTarget = findAvianRecoveryTarget();
		this.pathRecalcTimer = 10;
		dino.getNavigation().stop();
	}

	private Vec3 findAvianRecoveryTarget() {
		BlockPos origin = dino.blockPosition();
		BlockPos surface = BlockPos.findClosestMatch(
			origin,
			10,
			16,
			p -> dino.level().getFluidState(p).is(FluidTags.WATER)
			     && !dino.level().getFluidState(p.above()).is(FluidTags.WATER)
		).orElse(null);

		if (surface == null) {
			return dino.position().add(0.0D, AVIAN_RECOVERY_HEIGHT_ABOVE_WATER, 0.0D);
		}

		return Vec3.atCenterOf(surface).add(0.0D, AVIAN_RECOVERY_HEIGHT_ABOVE_WATER, 0.0D);
	}

	private void handleAvianDiveRecoveryMovement() {
		Vec3 velocity = dino.getDeltaMovement();

		dino.getNavigation().stop();

		if (avianRecoveryTarget == null) {
			avianRecoveryTarget = findAvianRecoveryTarget();
		}

		if (dino.isInWater()) {
			Vec3 upwardRecovery = new Vec3(velocity.x * 0.45D, 1.0D, velocity.z * 0.45D);
			faceAvianMovement(upwardRecovery);

			dino.setDeltaMovement(
				velocity.x * 0.45D,
				Math.min(Math.max(velocity.y + 0.035D, AVIAN_WATER_EXIT_BOOST), AVIAN_RECOVERY_MAX_UPWARD_SPEED),
				velocity.z * 0.45D
			);
			dino.hasImpulse = true;
			return;
		}

		Vec3 toRecoveryTarget = avianRecoveryTarget.subtract(dino.position());
		Vec3 horizontal = new Vec3(toRecoveryTarget.x, 0.0D, toRecoveryTarget.z);
		double horizontalLengthSqr = horizontal.lengthSqr();

		double xMotion = velocity.x * 0.82D;
		double zMotion = velocity.z * 0.82D;

		if (horizontalLengthSqr > AVIAN_RECOVERY_TARGET_REACHED_DISTANCE_SQR) {
			Vec3 horizontalMotion = horizontal.normalize().scale(AVIAN_RECOVERY_HORIZONTAL_SPEED);
			xMotion += horizontalMotion.x;
			zMotion += horizontalMotion.z;
		}

		double yMotion;
		if (toRecoveryTarget.y > 0.35D) {
			yMotion = Math.min(Math.max(velocity.y, AVIAN_RECOVERY_AIR_UPWARD_SPEED), AVIAN_RECOVERY_MAX_UPWARD_SPEED);
		} else if (toRecoveryTarget.y < -0.75D) {
			yMotion = Math.max(velocity.y - 0.03D, -0.08D);
		} else {
			yMotion = velocity.y * 0.55D;
		}

		faceAvianMovement(new Vec3(xMotion, yMotion, zMotion));

		dino.setDeltaMovement(xMotion, yMotion, zMotion);
		dino.hasImpulse = true;
	}

	private boolean handleTerrestrialWaterExitMovement() {
		if (!dino.isInWater()) {
			return false;
		}

		double fluidHeight = dino.getFluidHeight(FluidTags.WATER);
		Vec3 velocity = dino.getDeltaMovement();

		if (fluidHeight > dino.getFluidJumpThreshold() * 0.6D || dino.horizontalCollision) {
			dino.setDeltaMovement(
				velocity.x,
				Math.min(velocity.y + 0.06D, TERRESTRIAL_WATER_EXIT_BOOST),
				velocity.z
			);

			return true;
		}

		return false;
	}

	private boolean isExcludedAttackTarget(LivingEntity target) {
		return target instanceof Creeper
		       || dino.getDinoTags().stream().anyMatch(tag -> target.getType().is(tag));
	}

	private boolean isGroundCreature() {
		return !dino.isMarine()
		       && !dino.isAmphibious()
		       && !(dino instanceof FlyingAnimal);
	}

	private boolean isUnderwaterTarget(LivingEntity target) {
		if (target == null || !target.isInWater()) {
			return false;
		}

		if (target.getFluidHeight(FluidTags.WATER) <= target.getFluidJumpThreshold()) {
			return false;
		}

		if (isAvianWaterHunter()) {
			return isWithinAvianDiveDepth(target);
		}

		return true;
	}

	private boolean isWithinAvianDiveDepth(LivingEntity target) {
		BlockPos targetPos = target.blockPosition();

		for (int y = 0; y <= AVIAN_WATER_SURFACE_SEARCH_UP; y++) {
			BlockPos checkPos = targetPos.above(y);

			if (dino.level().getFluidState(checkPos).is(FluidTags.WATER)) {
				continue;
			}

			int depthBelowSurface = checkPos.getY() - targetPos.getY();
			return depthBelowSurface <= AVIAN_MAX_TARGET_DEPTH_BELOW_SURFACE;
		}

		return false;
	}

	private void faceAvianMovement(Vec3 direction) {
		if (direction.lengthSqr() < 0.0001D) {
			return;
		}

		Vec3 normalized = direction.normalize();

		float yaw = (float)(Mth.atan2(normalized.z, normalized.x) * Mth.RAD_TO_DEG) - 90.0F;
		float pitch = (float)(-(Mth.atan2(normalized.y, Math.sqrt(normalized.x * normalized.x + normalized.z * normalized.z)) * Mth.RAD_TO_DEG));

		dino.setYRot(yaw);
		dino.yRotO = yaw;
		dino.yBodyRot = yaw;
		dino.yBodyRotO = yaw;
		dino.yHeadRot = yaw;
		dino.yHeadRotO = yaw;
		dino.setXRot(Mth.clamp(pitch, -75.0F, 75.0F));
		dino.xRotO = dino.getXRot();
	}

    // --- STATE LOGIC ---

	private void tickIdle() {
		dino.getNavigation().stop();

		if (tryHerbivoreSelfFeed()) {
			return;
		}

		float territoriality = 0.0f;
		if (dino.dinoData != null) {
			territoriality = dino.dinoData.getTerritoriality();
		}

        int idleTime = ROAM_SELECTION_IDLE_TIME;
        // If we are a flying animal and on the ground, stay down briefly, but do not linger too long.
        if (dino instanceof FlyingAnimal && dino.onGround()) {
            idleTime = 80 + dino.getRandom().nextInt(120);
        }

        // Flying animals should not idle in mid-air. Keep selecting flight waypoints.
        if (dino instanceof FlyingAnimal && !dino.onGround()) {
            transitionTo(State.ROAMING);
            return;
        }

        if (stateTimer > idleTime) {
            if (dino.getRandom().nextFloat() < ROAM_SELECTION_CHANCE) {
                if (dino.getRandom().nextFloat() < territoriality) {
                    transitionTo(State.TERRITORIAL_ROAMING);
                } else {
                    transitionTo(State.ROAMING);
                }
            }
        }
    }

	private void checkBreedingReadiness() {
		if (dino.level().isClientSide || breedingCheckCooldown > 0 || !isBreedingCheckState()) {
			return;
		}

		breedingCheckCooldown = NATURAL_BREEDING_CHECK_INTERVAL;

		if (dino.dinoData != null && dino.dinoData.hasCondition(IDinoData.Condition.READY_TO_MATE)) {
			tryForcedMatingFromReadyCondition();
		} else if (JRConfigManager.get().naturalBreeding) {
			tryNaturalBreeding();
		}
	}

	private boolean isBreedingCheckState() {
		return currentState == State.IDLE
		       || currentState == State.ROAMING
		       || currentState == State.TERRITORIAL_ROAMING;
	}

	private boolean canUseForBreeding(DinoEntityBase entity) {
		return entity != null
		       && !entity.isBaby()
		       && !entity.isInLove()
		       && entity.getAge() == 0;
	}

	private void tryForcedMatingFromReadyCondition() {
		if (!canUseForBreeding(dino)) {
			return;
		}

		List<DinoEntityBase> nearbyPartners = dino.level().getEntitiesOfClass(
			DinoEntityBase.class,
			dino.getBoundingBox().inflate(NATURAL_BREEDING_PARTNER_RANGE),
			e -> e != dino
			     && e.getType() == dino.getType()
			     && canUseForBreeding(e)
			     && e.dinoData != null
			     && e.dinoData.hasCondition(IDinoData.Condition.READY_TO_MATE)
		);

		if (nearbyPartners.isEmpty()) {
			return;
		}

		DinoEntityBase partner = nearbyPartners.get(dino.getRandom().nextInt(nearbyPartners.size()));

		dino.setInLoveTime(600);
		partner.setInLoveTime(600);

		transitionTo(State.MATING);
	}

	private void tryNaturalBreeding() {
		if (!canUseForBreeding(dino)) {
			return;
		}

		List<DinoEntityBase> nearbyPartners = dino.level().getEntitiesOfClass(
			DinoEntityBase.class,
			dino.getBoundingBox().inflate(NATURAL_BREEDING_PARTNER_RANGE),
			e -> e != dino
			     && e.getType() == dino.getType()
			     && canUseForBreeding(e)
		);

		if (!nearbyPartners.isEmpty() && dino.getRandom().nextInt(NATURAL_BREEDING_PAIR_CHANCE) == 0) {
			DinoEntityBase partner = nearbyPartners.get(dino.getRandom().nextInt(nearbyPartners.size()));

			dino.setInLoveTime(600);
			partner.setInLoveTime(600);

			transitionTo(State.MATING);
			return;
		}

		if (nearbyPartners.isEmpty() && dino.getRandom().nextInt(NATURAL_BREEDING_SELF_CHANCE) == 0) {
			dino.setInLoveTime(600);
			this.isSelfBreeding = true;
			transitionTo(State.MATING);
		}
	}

	private void tickMating() {
		if (!dino.isInLove()) {
			this.mateTarget = null;
			this.isSelfBreeding = false;
			if (dino.dinoData != null) {
				dino.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
			}
			transitionTo(State.IDLE);
			return;
		}

		if (this.isSelfBreeding) {
			dino.spawnChildFromBreeding((net.minecraft.server.level.ServerLevel)dino.level(), dino);
			dino.setInLoveTime(0);
			this.isSelfBreeding = false;
			if (dino.dinoData != null) {
				dino.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
			}
			transitionTo(State.IDLE);
			return;
		}

		if (this.mateTarget == null || !this.mateTarget.isAlive() || !this.mateTarget.isInLove()) {
			List<Animal> nearby = dino.level().getEntitiesOfClass(
				Animal.class,
				dino.getBoundingBox().inflate(NATURAL_BREEDING_PARTNER_RANGE),
				e -> e.getType() == dino.getType()
				     && e != dino
				     && e.isInLove()
				     && dino.canMate(e)
			);

			this.mateTarget = nearby.stream()
				.min(Comparator.comparingDouble(dino::distanceToSqr))
				.orElse(null);
		}

		if (this.mateTarget == null) {
			transitionTo(State.IDLE);
			return;
		}

		dino.getNavigation().moveTo(this.mateTarget, getRoamSpeed());

		if (dino.distanceToSqr(this.mateTarget) < 4.0) {
			dino.spawnChildFromBreeding((net.minecraft.server.level.ServerLevel)dino.level(), this.mateTarget);

			if (dino.dinoData != null) {
				dino.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
			}

			if (this.mateTarget instanceof DinoEntityBase partner && partner.dinoData != null) {
				partner.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
			}

			this.mateTarget = null;
			transitionTo(State.IDLE);
		}
	}

    private void tickSleeping() {
        dino.getNavigation().stop();
    }

    private boolean handleWaterPathing() {
        if (waterTarget != null) {
            double dist = dino.distanceToSqr(waterTarget.getCenter());
            double reach = dino.getAIConfig().attackReach() * dino.getBbWidth();
            if (dist < (reach * reach) + 9.0) {
                dino.dinoData.setThirst(dino.getAIConfig().maxThirst());
                waterTarget = null;
                dino.getNavigation().stop();
                transitionTo(State.IDLE);
                return true;
            }

            if (dino.getNavigation().isDone()) {
                if (dist < 1024) {
                    dino.getNavigation().moveTo(waterTarget.getX(), waterTarget.getY(), waterTarget.getZ(), getRoamSpeed());
                } else {
                    waterTarget = null;
                }
            }
            return true;
        }
        return false;
    }

    private void tickRoaming() {
        if (handleWaterPathing()) return;

		if (tryHerbivoreSelfFeed()) {
			transitionTo(State.IDLE);
			return;
		}

        if (stateTimer == 0) {
            this.roamTarget = null;
            findAndSetRoamTarget();
            if (this.roamTarget == null) {
                if (dino instanceof FlyingAnimal && !dino.onGround()) {
                    transitionTo(State.ROAMING);
                } else {
                    transitionTo(State.IDLE);
                }
                return;
            }
        }

        if (stateTimer > 0 && stateTimer % ROAM_RETARGET_INTERVAL == 0) {
            findAndSetRoamTarget();
        }

        if (stateTimer > ROAM_STATE_DURATION) {
            if (dino instanceof FlyingAnimal && !dino.onGround()) {
                findAndSetRoamTarget();
            } else {
                transitionTo(State.IDLE);
            }
            return;
        }

        if (dino.getNavigation().isDone()) {
            if (roamTarget != null && dino.distanceToSqr(roamTarget) < 9.0) {
                // If we are a flyer and in the air, don't stop! Keep flying!
                if (dino instanceof FlyingAnimal && !dino.onGround()) {
                    findAndSetRoamTarget(); // Immediately pick next waypoint
                    return;
                }

                findAndSetRoamTarget();
                if (this.roamTarget != null) {
                    return;
                }

                transitionTo(State.IDLE);
                return;
            }

            boolean resumed = false;
            if (roamTarget != null) {
                resumed = dino.getNavigation().moveTo(roamTarget.x, roamTarget.y, roamTarget.z, getRoamSpeed());
            }

            if (!resumed) {
                findAndSetRoamTarget();
                if (this.roamTarget == null) {
                    if (dino instanceof FlyingAnimal && !dino.onGround()) {
                        transitionTo(State.ROAMING);
                    } else {
                        transitionTo(State.IDLE);
                    }
                }
            }
        }
    }

	private void findAndSetRoamTarget() {
		this.roamTarget = null;

		// Grounded flyers should walk to nearby grounded targets instead of taking off immediately.
		if (dino instanceof FlyingAnimal && dino.onGround()) {
			Vec3 groundPos = getNearbyGroundRoamPosForFlyer();
			if (groundPos != null && dino.getNavigation().moveTo(groundPos.x, groundPos.y, groundPos.z, getRoamSpeed())) {
				this.roamTarget = groundPos;
				return;
			}
		}

		// Flying Logic
		if (dino instanceof FlyingAnimal) {
			Vec3 airPos = getAirRoamPos();
			if (airPos != null) {
				// Use boosted roaming speed as cruising speed.
				if (dino.getNavigation().moveTo(airPos.x, airPos.y, airPos.z, getRoamSpeed())) {
					this.roamTarget = airPos;
					return;
				}
			}
		}

		// Ground Logic
		for (int i = 0; i < 5; i++) {
			Vec3 pos = DefaultRandomPos.getPos(dino, 20, 7);
			if (pos != null && dino.distanceToSqr(pos) > 49.0) {
				if (dino.getNavigation().moveTo(pos.x, pos.y, pos.z, getRoamSpeed())) {
					this.roamTarget = pos;
					return;
				}
			}
		}
	}

	private Vec3 getNearbyGroundRoamPosForFlyer() {
		double maxDistanceSqr = FLYER_GROUND_WALK_TARGET_RANGE * FLYER_GROUND_WALK_TARGET_RANGE;

		for (int i = 0; i < 5; i++) {
			Vec3 pos = DefaultRandomPos.getPos(dino, (int) FLYER_GROUND_WALK_TARGET_RANGE, 3);
			if (pos == null) {
				continue;
			}

			if (dino.distanceToSqr(pos) > maxDistanceSqr) {
				continue;
			}

			BlockPos targetBlock = BlockPos.containing(pos);
			int groundY = dino.level().getHeight(
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				targetBlock.getX(),
				targetBlock.getZ()
			);

			if (Math.abs(pos.y - groundY) <= 1.5D) {
				return new Vec3(pos.x, groundY, pos.z);
			}
		}

		return null;
	}

    private Vec3 getAirRoamPos() {
        net.minecraft.util.RandomSource random = dino.getRandom();
        Vec3 pos = dino.position();

        // Increased radius for better flight
        double x = pos.x + (random.nextFloat() * 2 - 1) * 64;
        double z = pos.z + (random.nextFloat() * 2 - 1) * 64;

        // Height check
        int groundY = dino.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)x, (int)z);
        double y;

        if (dino.onGround()) {
            // Takeoff: 10-20 blocks up
            y = pos.y + 10 + random.nextInt(10);
        } else {
            // 20% chance to land if already flying
            if (random.nextFloat() < 0.20f) {
                y = groundY;
            } else {
                // Wander vertically
                if (random.nextFloat() < 0.30f) {
                    y = groundY + 10 + random.nextInt(30);
                } else {
                    y = pos.y + (random.nextFloat() * 2 - 1) * 20;
                }
                
                if (y < groundY + 5) y = groundY + 5;
                if (y > groundY + 50) y = groundY + 50;
            }
        }

        // --- Water Avoidance ---
        // If the target is over water, try to pull it back towards us/land
        BlockPos targetPos = new BlockPos((int)x, (int)groundY, (int)z);
        if (dino.level().getFluidState(targetPos).is(FluidTags.WATER)) {
            // Check if it's "deep" water (just checking surface isn't enough, but usually good proxy)
            // Move target 50% closer to current position (which presumably was safe)
            x = (x + pos.x) / 2.0;
            z = (z + pos.z) / 2.0;
            // Re-calculate ground Y for new X/Z
            groundY = dino.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)x, (int)z);
            if (y < groundY) y = groundY;
        }

        // --- Landing Logic ---
        // If we are aiming for a spot very close to the ground (<= 4 blocks), just land.
        // This prevents awkward low-altitude hovering.
        if (y <= groundY + 4) {
            y = groundY;
        }

        return new Vec3(x, y, z);
    }

    private void tickTerritorialRoaming() {
        if (handleWaterPathing()) return;

		if (tryHerbivoreSelfFeed()) {
			transitionTo(State.IDLE);
			return;
		}

        if (stateTimer == 0) {
            findAndSetTerritorialTarget();
            if (this.roamTarget == null) {
                if (dino instanceof FlyingAnimal && !dino.onGround()) {
                    transitionTo(State.ROAMING);
                } else {
                    transitionTo(State.IDLE);
                }
                return;
            }
        }

        if (stateTimer > 0 && stateTimer % ROAM_RETARGET_INTERVAL == 0) {
            findAndSetTerritorialTarget();
        }

        if (stateTimer > ROAM_STATE_DURATION) {
            if (dino instanceof FlyingAnimal && !dino.onGround()) {
                findAndSetTerritorialTarget();
            } else {
                transitionTo(State.IDLE);
            }
            return;
        }

        if (dino.getNavigation().isDone()) {
            if (roamTarget != null && dino.distanceToSqr(roamTarget) < 9.0) {
                transitionTo(State.IDLE);
                return;
            }

            boolean resumed = false;
            if (roamTarget != null) {
                resumed = dino.getNavigation().moveTo(
                        roamTarget.x,
                        roamTarget.y,
                        roamTarget.z,
                        getTerritorialRoamSpeed()
                );
            }

            if (!resumed) {
                findAndSetTerritorialTarget();
                if (this.roamTarget == null) {
                    transitionTo(State.IDLE);
                }
            }
        }
    }

    private double getBaseRoamSpeed() {
        double speed = Math.max(dino.getAIConfig().walkSpeed(), MIN_ROAM_SPEED);

        if (isOnIce()) {
            speed *= ICE_ROAM_SPEED_MULTIPLIER;
        }

        return speed;
    }

    private double getRoamSpeed() {
        return getBaseRoamSpeed() * ROAM_SPEED_MULTIPLIER;
    }

    private double getTerritorialRoamSpeed() {
        return getBaseRoamSpeed() * TERRITORIAL_ROAM_SPEED_MULTIPLIER;
    }

    private boolean isOnIce() {
        BlockPos below = dino.blockPosition().below();
        BlockState state = dino.level().getBlockState(below);

        return state.is(Blocks.ICE)
                || state.is(Blocks.PACKED_ICE)
                || state.is(Blocks.BLUE_ICE)
                || state.is(Blocks.FROSTED_ICE);
    }

	private boolean tryHerbivoreSelfFeed() {
		if (dino.level().isClientSide || dino.dinoData == null || dino.isCarnivore()) {
			return false;
		}

		if (dino.tickCount % HERBIVORE_SELF_FEED_INTERVAL != 0 || dino.getRandom().nextFloat() > HERBIVORE_SELF_FEED_CHANCE) {
			return false;
		}

		float hunger = dino.dinoData.getHunger();
		float maxHunger = dino.getAIConfig().maxHunger();

		if (hunger >= maxHunger * HERBIVORE_SELF_FEED_HUNGER_THRESHOLD) {
			return false;
		}

		BlockPos below = dino.blockPosition().below();
		BlockState belowState = dino.level().getBlockState(below);

		if (belowState.is(Blocks.GRASS_BLOCK)) {
			dino.level().levelEvent(2001, below, Block.getId(belowState));
			dino.level().setBlock(below, Blocks.DIRT.defaultBlockState(), 3);
			replenishHungerFromPlant();
			return true;
		}

		BlockPos browsePos = findNearbyEdiblePlant();
		if (browsePos != null) {
			BlockState browseState = dino.level().getBlockState(browsePos);
			dino.level().levelEvent(2001, browsePos, Block.getId(browseState));
			dino.level().destroyBlock(browsePos, false, dino);
			replenishHungerFromPlant();
			return true;
		}

		return false;
	}

	private BlockPos findNearbyEdiblePlant() {
		BlockPos origin = dino.blockPosition();

		for (int y = -1; y <= HERBIVORE_BROWSE_VERTICAL_RANGE; y++) {
			for (int x = -HERBIVORE_BROWSE_HORIZONTAL_RANGE; x <= HERBIVORE_BROWSE_HORIZONTAL_RANGE; x++) {
				for (int z = -HERBIVORE_BROWSE_HORIZONTAL_RANGE; z <= HERBIVORE_BROWSE_HORIZONTAL_RANGE; z++) {
					BlockPos pos = origin.offset(x, y, z);
					BlockState state = dino.level().getBlockState(pos);

					if (isEdiblePlantBlock(state)) {
						return pos;
					}
				}
			}
		}

		return null;
	}

	private boolean isEdiblePlantBlock(BlockState state) {
		return state.is(ModTags.Blocks.PLANTS)
		       || state.is(BlockTags.LEAVES)
		       || state.is(BlockTags.FLOWERS)
		       || state.is(BlockTags.CROPS)
		       || state.is(BlockTags.SAPLINGS)
		/*? if <=1.20.1 {*/
		|| state.is(Blocks.GRASS)
		 /*?} else {*/
		       /*|| state.is(Blocks.GRASS_BLOCK)
		*//*?}*/
		       || state.is(Blocks.TALL_GRASS)
		       || state.is(Blocks.FERN)
		       || state.is(Blocks.LARGE_FERN)
		       || state.is(Blocks.DEAD_BUSH)
		       || state.is(Blocks.VINE)
		       || state.is(Blocks.GLOW_LICHEN)
		       || state.is(Blocks.SEAGRASS)
		       || state.is(Blocks.TALL_SEAGRASS)
		       || state.is(Blocks.KELP)
		       || state.is(Blocks.KELP_PLANT)
		       || state.is(Blocks.SUGAR_CANE)
		       || state.is(Blocks.CACTUS)
		       || state.is(Blocks.BAMBOO)
		       || state.is(Blocks.BAMBOO_SAPLING)
		       || state.is(Blocks.MOSS_BLOCK)
		       || state.is(Blocks.MOSS_CARPET)
		       || state.is(Blocks.HANGING_ROOTS)
		       || state.is(Blocks.ROOTED_DIRT)
		       || state.is(Blocks.AZALEA)
		       || state.is(Blocks.FLOWERING_AZALEA)
		       || state.is(Blocks.BROWN_MUSHROOM)
		       || state.is(Blocks.RED_MUSHROOM)
		       || state.is(Blocks.CRIMSON_FUNGUS)
		       || state.is(Blocks.WARPED_FUNGUS)
		       || state.is(Blocks.CRIMSON_ROOTS)
		       || state.is(Blocks.WARPED_ROOTS)
		       || state.is(Blocks.NETHER_SPROUTS);
	}

	private void replenishHungerFromPlant() {
		float hunger = dino.dinoData.getHunger();
		float maxHunger = dino.getAIConfig().maxHunger();
		float amount = Math.max(1.0f, dino.getAIConfig().defaultHungerReplenishment() * HERBIVORE_SELF_FEED_REPLENISHMENT_MULTIPLIER);

		dino.dinoData.setHunger(Math.min(maxHunger, hunger + amount));
	}

    private void findAndSetTerritorialTarget() {
		this.roamTarget = null;
		Vec3 target = null;

		// Grounded flyers should walk to nearby grounded territorial targets before taking off.
		if (dino instanceof FlyingAnimal && dino.onGround()) {
			Vec3 groundPos = getNearbyGroundRoamPosForFlyer();
			if (groundPos != null && dino.getNavigation().moveTo(groundPos.x, groundPos.y, groundPos.z, getTerritorialRoamSpeed())) {
				this.roamTarget = groundPos;
				return;
			}
		}

		for (int i = 0; i < 8; i++) {
			Vec3 candidate;
			if (homePos != null && dino.distanceToSqr(homePos.getCenter()) > 40 * 40) {
				Vec3 toHome = Vec3.atCenterOf(homePos).subtract(dino.position()).normalize().scale(10);
				Vec3 biasTarget = dino.position().add(toHome);
				candidate = DefaultRandomPos.getPosTowards(dino, 15, 7, biasTarget, 1.57);
			} else {
				candidate = DefaultRandomPos.getPos(dino, 15, 7);
			}

			if (candidate != null && dino.distanceToSqr(candidate) > 25.0) {
				if (dino.getNavigation().moveTo(candidate.x, candidate.y, candidate.z, getTerritorialRoamSpeed())) {
					this.roamTarget = candidate;
					return;
				}
			}
		}

		Vec3 fallback = DefaultRandomPos.getPos(dino, 10, 5);
		if (fallback != null) {
			if (dino.getNavigation().moveTo(fallback.x, fallback.y, fallback.z, getTerritorialRoamSpeed())) {
				this.roamTarget = fallback;
			}
		}
	}

	private void tickChasing() {
		if (attackTarget == null) {
			transitionTo(State.IDLE);
			return;
		}
		dino.setSprinting(true);

		waterTarget = null;

		boolean waterMovementHandled = handleWaterMovementHelper(attackTarget);

		dino.getLookControl().setLookAt(attackTarget, 30.0F, 30.0F);

		double distSqr = dino.distanceToSqr(attackTarget);
		double reachMult = dino.getAIConfig().attackReach();
		double reach = (double)(dino.getBbWidth() * reachMult * dino.getBbWidth() * reachMult) + attackTarget.getBbWidth();

		if (isAvianWaterHunter() && isRecoveringFromAvianDive()) {
			dino.getNavigation().stop();
			return;
		}

		if (isAvianWaterHunter()
		    && !avianDiveInProgress
		    && !avianDiveAttackSpent
		    && avianDiveRestTimer <= 0
		    && isUnderwaterTarget(attackTarget)) {
			avianDiveInProgress = true;
			avianUnderwaterDiveTimer = 0;
			dino.getNavigation().stop();
			return;
		}

		if (distSqr <= reach * 1.1) {
			transitionTo(State.ATTACKING);
			return;
		}

		if (waterMovementHandled && isAvianWaterHunter()) {
			dino.getNavigation().stop();
			return;
		}

		if (isAvianWaterHunter() && avianDiveInProgress) {
			dino.getNavigation().stop();
			return;
		}

		if (pathRecalcTimer-- <= 0 || dino.getNavigation().isDone()) {
			if (!dino.getNavigation().moveTo(attackTarget, dino.getAIConfig().runSpeed())) {
				pathRecalcTimer = 10; // Wait before retrying to prevent rapid failure loops
				failedPathfindingAttempts++;

				// Tolerance allows for temporary pathfinding failures (e.g., target inside hitbox)
				if (failedPathfindingAttempts > 5) {
					if (isAvianWaterHunter() && isUnderwaterTarget(attackTarget)) {
						failedPathfindingAttempts = 0;
					} else {
						attackTarget = null;
						transitionTo(State.IDLE);
					}
				}
			} else {
				pathRecalcTimer = 10;
				failedPathfindingAttempts = 0;
			}
		}
	}

	private void tickAttacking() {
		if (attackTarget == null) {
			transitionTo(State.IDLE);
			return;
		}
		dino.setSprinting(true);

		boolean waterMovementHandled = handleWaterMovementHelper(attackTarget);

		dino.getLookControl().setLookAt(attackTarget, 30.0F, 30.0F);

		double distSqr = dino.distanceToSqr(attackTarget);
		double reachMult = dino.getAIConfig().attackReach();
		double reach = (double)(dino.getBbWidth() * reachMult * dino.getBbWidth() * reachMult) + attackTarget.getBbWidth();

		if (isAvianWaterHunter() && isRecoveringFromAvianDive()) {
			dino.getNavigation().stop();
			transitionTo(State.CHASING);
			return;
		}

		if (isAvianWaterHunter()
		    && !avianDiveInProgress
		    && !avianDiveAttackSpent
		    && isUnderwaterTarget(attackTarget)) {
			avianDiveInProgress = true;
			avianUnderwaterDiveTimer = 0;
			dino.getNavigation().stop();
			transitionTo(State.CHASING);
			return;
		}

		if (distSqr > reach * 2.5) {
			if (isAvianWaterHunter() && avianDiveInProgress) {
				transitionTo(State.CHASING);
				return;
			}

			transitionTo(State.CHASING);
			return;
		}

		double stopDist = (dino.getBbWidth()/2.0 + attackTarget.getBbWidth()/2.0) + 0.5;
		double stopDistSqr = stopDist * stopDist;

		if (waterMovementHandled && isAvianWaterHunter() && isUnderwaterTarget(attackTarget)) {
			dino.getNavigation().stop();
		} else if (isAvianWaterHunter() && avianDiveInProgress) {
			dino.getNavigation().stop();
		} else if (distSqr > stopDistSqr) {
			dino.getNavigation().moveTo(attackTarget, dino.getAIConfig().runSpeed());
		} else {
			dino.getNavigation().stop();
		}

		if (attackCooldown <= 0) {
			boolean avianUnderwaterDiveAttack = isAvianWaterHunter()
			                                    && avianDiveInProgress
			                                    && isUnderwaterTarget(attackTarget);

			if (avianUnderwaterDiveAttack && avianDiveAttackSpent) {
				beginAvianDiveRecovery();
				transitionTo(State.CHASING);
				return;
			}

			dino.swing(InteractionHand.MAIN_HAND);

			boolean success = false;
			boolean targetWasAlive = attackTarget.isAlive();

			if (dino.isWithinMeleeAttackRange(attackTarget)) {
				success = dino.doHurtTarget(attackTarget);
			}

			if (!success && attackTarget.isAlive()) {
				if (distSqr <= reach) {
					success = attackTarget.hurt(dino.damageSources().mobAttack(dino), (float)dino.getAttributeValue(Attributes.ATTACK_DAMAGE));
				}
			}

			if (avianUnderwaterDiveAttack) {
				avianDiveAttackSpent = true;

				if (success && targetWasAlive && !attackTarget.isAlive()) {
					handleKillReward();
				}

				attackCooldown = 20;
				beginAvianDiveRecovery();
				transitionTo(State.CHASING);
				return;
			}

			if (success) {
				if (targetWasAlive && !attackTarget.isAlive()) {
					handleKillReward();
				}

				attackCooldown = 20;
			} else {
				attackCooldown = 5;
			}
		}
	}

	private void handleKillReward() {
		if (dino.dinoData == null) {
			return;
		}

		if (dino.dinoData.getHunger() >= dino.getAIConfig().maxHunger()) {
			dino.addEffect(new MobEffectInstance(MobEffects.REGENERATION, KILL_REGEN_DURATION_TICKS, 0));
		}
	}

    private void tickFleeing() {
        if (attackTarget == null) {
            transitionTo(State.IDLE);
            return;
        }

        if (dino.getNavigation().isDone() || stateTimer % 10 == 0) {
            Vec3 awayDir = DefaultRandomPos.getPosAway(dino, 16, 7, attackTarget.position());
            if (awayDir != null) {
                dino.getNavigation().moveTo(awayDir.x, awayDir.y, awayDir.z, dino.getAIConfig().runSpeed() * 1.2);
            }
        }

        if (dino.distanceToSqr(attackTarget) > 48 * 48) {
            transitionTo(State.IDLE);
        }
    }
}