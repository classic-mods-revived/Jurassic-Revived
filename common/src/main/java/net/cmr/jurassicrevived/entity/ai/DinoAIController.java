package net.cmr.jurassicrevived.entity.ai;

import net.cmr.jurassicrevived.config.JRConfig;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
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
    }

	private void handleFloating() {
		if (!dino.isInWater()) {
			return;
		}

		double fluidHeight = dino.getFluidHeight(FluidTags.WATER);
		if (fluidHeight <= dino.getFluidJumpThreshold()) {
			return;
		}

		Vec3 velocity = dino.getDeltaMovement();

		if (velocity.y < 0.08D) {
			dino.setDeltaMovement(
				velocity.x,
				Math.min(velocity.y + 0.03D, 0.08D),
				velocity.z
			);
		}
	}

    public void onHurtBy(LivingEntity attacker) {
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
            }

            if (thirstDecay > 0.0f) {
                float currentThirst = dino.dinoData.getThirst();
                dino.dinoData.setThirst(Math.max(0, currentThirst - thirstDecay));
            }

            float hunger = dino.dinoData.getHunger();
            float thirst = dino.dinoData.getThirst();

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
            boolean hungerConsumptionEnabled = JRConfigManager.get().hungerConsumption;
            boolean territorial = dino.dinoData != null && dino.dinoData.getAggression() == IDinoData.Aggression.TERRITORIAL;
            boolean shouldHunt;

            if (hungerConsumptionEnabled) {
                boolean hungry = dino.dinoData != null && dino.dinoData.getHunger() < 70;
                shouldHunt = hungry || (territorial && dino.dinoData != null && dino.dinoData.getHunger() < 90);
            } else {
                shouldHunt = stateTimer % 100 == 0 && dino.getRandom().nextFloat() < (territorial ? 0.35f : 0.15f);
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
            if (!dino.canAttack(e)) continue;

            if (e instanceof Player player) {
                // Apply motivation logic for Players
                boolean isTerritorial = dino.dinoData != null && dino.dinoData.getAggression() == IDinoData.Aggression.TERRITORIAL;
                boolean hungry = dino.dinoData != null && dino.dinoData.getHunger() < 70;

                boolean validPlayerTarget = false;
                if (isTerritorial) {
                    if (hungry || dino.distanceToSqr(player) < 225) {
                        validPlayerTarget = true;
                    }
                } else {
                    if (hungry) {
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

    // --- STATE LOGIC ---

    private void tickIdle() {
        dino.getNavigation().stop();

        // Check for Natural Breeding (approx once every 2 in-game days = 48000 ticks)
        if (!dino.level().isClientSide && stateTimer % 100 == 0) {
            // 1. Trigger Ready State
            if (dino.getAge() == 0 && !dino.isInLove() && dino.canBreed()) {
                if (dino.dinoData != null && !dino.dinoData.hasCondition(IDinoData.Condition.READY_TO_MATE)) {
                    // Chance: 1 in 480 checks (~ once per 48000 ticks / 2 days)
                    if (dino.getRandom().nextInt(480) == 0) {
                        // Parthenogenesis check (1% chance)
                        if (dino.getRandom().nextInt(100) == 0) {
                            dino.setInLoveTime(600); // 30 Seconds of hearts
                            this.isSelfBreeding = true;
                        } else {
                            // Standard: Set condition, wait for partner
                            dino.dinoData.addCondition(IDinoData.Condition.READY_TO_MATE);
                        }
                    }
                }
            }

            // 2. Scan for Partner if Ready
            if (dino.dinoData != null && dino.dinoData.hasCondition(IDinoData.Condition.READY_TO_MATE)) {
                // Add a chance to "lose interest" so they aren't ready forever (1 in 50 chance every 5 seconds = approx 4 minutes duration)
                if (dino.getRandom().nextInt(50) == 0) {
                    dino.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
                } else {
                    List<DinoEntityBase> nearby = dino.level().getEntitiesOfClass(DinoEntityBase.class, 
                        dino.getBoundingBox().inflate(8.0), 
                        e -> e.getType() == dino.getType() && e != dino && !e.isBaby());

                    for (DinoEntityBase potentialPartner : nearby) {
                        if (dino.canMate(potentialPartner)) {
                            // Initiate mating for both
                            dino.setInLoveTime(600); // 30 seconds
                            potentialPartner.setInLoveTime(600); // 30 seconds
                            
                            dino.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
                            if (potentialPartner.dinoData != null) {
                                potentialPartner.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
                            }
                            break; 
                        }
                    }
                }
            }
        }

        float territoriality = 0.0f;
        if (dino.dinoData != null) {
            territoriality = dino.dinoData.getTerritoriality();
        }

        int idleTime = 60; // Default 3 seconds
        // If we are a flying animal and on the ground, stay down longer (e.g. 15-30 seconds) to walk around
        if (dino instanceof FlyingAnimal && dino.onGround()) {
            idleTime = 300 + dino.getRandom().nextInt(300);
        }

        if (stateTimer > idleTime) {
            if (dino.getRandom().nextFloat() < 0.05f) {
                if (dino.getRandom().nextFloat() < territoriality) {
                    transitionTo(State.TERRITORIAL_ROAMING);
                } else {
                    transitionTo(State.ROAMING);
                }
            }
        }
    }

    private void tickMating() {
        // If love ran out, stop
        if (!dino.isInLove()) {
            this.mateTarget = null;
            this.isSelfBreeding = false;
            // Also ensure we don't have the condition anymore if we just failed/finished
            if (dino.dinoData != null) dino.dinoData.removeCondition(IDinoData.Condition.READY_TO_MATE);
            transitionTo(State.IDLE);
            return;
        }

        // Parthenogenesis Logic
        if (this.isSelfBreeding) {
            dino.spawnChildFromBreeding((net.minecraft.server.level.ServerLevel)dino.level(), dino);
            dino.setInLoveTime(0); // Reset
            this.isSelfBreeding = false;
            transitionTo(State.IDLE);
            return;
        }

        // Find Partner
        if (this.mateTarget == null || !this.mateTarget.isAlive() || !this.mateTarget.isInLove()) {
            List<Animal> nearby = dino.level().getEntitiesOfClass(Animal.class, dino.getBoundingBox().inflate(16.0),
                    e -> e.getType() == dino.getType() && e != dino && e.isInLove());

            this.mateTarget = nearby.stream()
                    .min(Comparator.comparingDouble(dino::distanceToSqr))
                    .orElse(null);
        }

        if (this.mateTarget != null) {
            dino.getNavigation().moveTo(this.mateTarget, dino.getAIConfig().walkSpeed());
            if (dino.distanceToSqr(this.mateTarget) < 4.0) { // < 2 blocks
                dino.spawnChildFromBreeding((net.minecraft.server.level.ServerLevel)dino.level(), this.mateTarget);
                // Breeding consumes love in spawnChildFromBreeding
                this.mateTarget = null;
                transitionTo(State.IDLE);
            }
        } else {
            // No partner found yet, wander slowly?
            if (dino.getNavigation().isDone()) {
                Vec3 pos = DefaultRandomPos.getPos(dino, 10, 3);
                if (pos != null) dino.getNavigation().moveTo(pos.x, pos.y, pos.z, dino.getAIConfig().walkSpeed());
            }
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
                    dino.getNavigation().moveTo(waterTarget.getX(), waterTarget.getY(), waterTarget.getZ(), dino.getAIConfig().walkSpeed());
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

        if (stateTimer == 0) {
            this.roamTarget = null;
            findAndSetRoamTarget();
            if (this.roamTarget == null) {
                transitionTo(State.IDLE);
                return;
            }
        }

        if (stateTimer > 400) {
            transitionTo(State.IDLE);
            return;
        }

        if (dino.getNavigation().isDone()) {
            if (roamTarget != null && dino.distanceToSqr(roamTarget) < 9.0) {
                // If we are a flyer and in the air, don't stop! Keep flying!
                if (dino instanceof FlyingAnimal && !dino.onGround()) {
                    findAndSetRoamTarget(); // Immediately pick next waypoint
                    return;
                }

                transitionTo(State.IDLE);
                return;
            }

            boolean resumed = false;
            if (roamTarget != null) {
                resumed = dino.getNavigation().moveTo(roamTarget.x, roamTarget.y, roamTarget.z, dino.getAIConfig().walkSpeed());
            }

            if (!resumed) {
                findAndSetRoamTarget();
                if (this.roamTarget == null) {
                    transitionTo(State.IDLE);
                }
            }
        }
    }

    private void findAndSetRoamTarget() {
        this.roamTarget = null;

        // Flying Logic
        if (dino instanceof FlyingAnimal) {
            Vec3 airPos = getAirRoamPos();
            if (airPos != null) {
                // Use walkSpeed as cruising speed
                if (dino.getNavigation().moveTo(airPos.x, airPos.y, airPos.z, dino.getAIConfig().walkSpeed())) {
                    this.roamTarget = airPos;
                    return;
                }
            }
        }

        // Ground Logic
        for (int i = 0; i < 3; i++) {
            Vec3 pos = DefaultRandomPos.getPos(dino, 20, 7);
            if (pos != null && dino.distanceToSqr(pos) > 49.0) {
                if (dino.getNavigation().moveTo(pos.x, pos.y, pos.z, dino.getAIConfig().walkSpeed())) {
                    this.roamTarget = pos;
                    return;
                }
            }
        }
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

        if (stateTimer == 0) {
            findAndSetTerritorialTarget();
            if (this.roamTarget == null) {
                transitionTo(State.IDLE);
                return;
            }
        }

        if (stateTimer > 400) {
            transitionTo(State.IDLE);
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

    private double getTerritorialRoamSpeed() {
        return dino.getAIConfig().walkSpeed() * TERRITORIAL_ROAM_SPEED_MULTIPLIER;
    }

    private void findAndSetTerritorialTarget() {
        this.roamTarget = null;
        Vec3 target = null;

        for (int i = 0; i < 5; i++) {
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

        dino.getLookControl().setLookAt(attackTarget, 30.0F, 30.0F);

        double distSqr = dino.distanceToSqr(attackTarget);
        double reachMult = dino.getAIConfig().attackReach();
        double reach = (double)(dino.getBbWidth() * reachMult * dino.getBbWidth() * reachMult) + attackTarget.getBbWidth();

        if (distSqr <= reach * 1.1) {
            transitionTo(State.ATTACKING);
            return;
        }

        if (pathRecalcTimer-- <= 0 || dino.getNavigation().isDone()) {
            if (!dino.getNavigation().moveTo(attackTarget, dino.getAIConfig().runSpeed())) {
                pathRecalcTimer = 10; // Wait before retrying to prevent rapid failure loops
                failedPathfindingAttempts++;
                
                // Tolerance allows for temporary pathfinding failures (e.g., target inside hitbox)
                if (failedPathfindingAttempts > 5) {
                    attackTarget = null;
                    transitionTo(State.IDLE);
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

        dino.getLookControl().setLookAt(attackTarget, 30.0F, 30.0F);

        double distSqr = dino.distanceToSqr(attackTarget);
        double reachMult = dino.getAIConfig().attackReach();
        double reach = (double)(dino.getBbWidth() * reachMult * dino.getBbWidth() * reachMult) + attackTarget.getBbWidth();

        if (distSqr > reach * 2.5) {
            transitionTo(State.CHASING);
            return;
        }

        double stopDist = (dino.getBbWidth()/2.0 + attackTarget.getBbWidth()/2.0) + 0.5;
        double stopDistSqr = stopDist * stopDist;

        if (distSqr > stopDistSqr) {
            dino.getNavigation().moveTo(attackTarget, dino.getAIConfig().runSpeed());
        } else {
            dino.getNavigation().stop();
        }

        if (attackCooldown <= 0) {
            dino.swing(InteractionHand.MAIN_HAND);

            boolean success = false;
            if (dino.isWithinMeleeAttackRange(attackTarget)) {
                success = dino.doHurtTarget(attackTarget);
            }

            if (!success && attackTarget.isAlive()) {
                if (distSqr <= reach) {
                    success = attackTarget.hurt(dino.damageSources().mobAttack(dino), (float)dino.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }

            if (success) {
                attackCooldown = 20;
            } else {
                attackCooldown = 5;
            }
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