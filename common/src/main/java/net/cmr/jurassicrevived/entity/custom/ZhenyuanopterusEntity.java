package net.cmr.jurassicrevived.entity.custom;

import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.ai.SprintingMeleeAttackGoal;
import net.cmr.jurassicrevived.entity.ai.SprintingPanicGoal;
import net.cmr.jurassicrevived.entity.client.ZhenyuanopterusVariant;
import net.cmr.jurassicrevived.entity.client.ZhenyuanopterusVariant;
import net.cmr.jurassicrevived.sound.ModSounds;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
/*? if <=1.20.1 {*/
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
/*?} else {*/
/*import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
*//*?}*/

public class ZhenyuanopterusEntity extends Animal implements GeoEntity, FlyingAnimal {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ZhenyuanopterusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SYNCED_AGE =
            SynchedEntityData.defineId(ZhenyuanopterusEntity.class, EntityDataSerializers.INT);

    // Procedural tail sway state (client-side use for rendering)
    private float tailSwayOffset;   // Smoothed offset in range roughly [-1, 1]
    private float tailSwayVelocity; // Internal velocity for spring-damper
    private float tailSwayPrev;     // Previous frame value for interpolation

    public ZhenyuanopterusEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SprintingPanicGoal(this, 1.15) {
            @Override
            public boolean canUse() {
                return ZhenyuanopterusEntity.this.isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, BrachiosaurusEntity.class, (float) 20, 1, 1));
        this.goalSelector.addGoal(4, new SprintingMeleeAttackGoal(this, 1.1, false));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25));

        // Goal 7: Wander on ground (Walk) - Only when on ground
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0) {
            @Override
            public boolean canUse() {
                return ZhenyuanopterusEntity.this.onGround() && super.canUse();
            }
        });

        // Goal 8: Wander in air (Fly) - Handles takeoff, flying, and landing
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0, 20) {
            @Override
            public boolean canUse() {
                boolean isFlying = !ZhenyuanopterusEntity.this.onGround();
                // If flying, keep flying. If on ground, small chance (1/400 ticks) to take off.
                return (isFlying || ZhenyuanopterusEntity.this.getRandom().nextInt(400) == 0) && super.canUse();
            }

            @Override
            protected Vec3 getPosition() {
                Vec3 pos = ZhenyuanopterusEntity.this.position();
                RandomSource random = ZhenyuanopterusEntity.this.getRandom();

                double x = pos.x + (random.nextFloat() * 2 - 1) * 32;
                double z = pos.z + (random.nextFloat() * 2 - 1) * 32;

                // Get ground height at the random destination (returns Y of first air block)
                int groundY = ZhenyuanopterusEntity.this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)x, (int)z);
                double y;

                if (ZhenyuanopterusEntity.this.onGround()) {
                    // Takeoff: Target well above ground to ensure liftoff
                    y = pos.y + 15 + random.nextInt(10);
                } else {
                    // Flying: 5% chance to land, otherwise stay airborne but capped height
                    if (random.nextFloat() < 0.05f) {
                        y = groundY; // Land
                    } else if (pos.y > groundY + 20) {
                        // Too high: Force descent
                        y = pos.y - 5 - random.nextInt(10);
                    } else {
                        // Just wander up or down a bit
                        y = pos.y + (random.nextFloat() * 2 - 1) * 10;
                    }
                }

                // Don't target below the ground (blocks)
                if (y < groundY) y = groundY;

                return new Vec3(x, y, z);
            }
        });

        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new FollowMobGoal(this, 0.8, (float) 20, (float) 10));
        this.targetSelector.addGoal(11, new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false,
                target -> {
                    // 1. Don't eat your own species
                    if (target.getType() == this.getType()) return false;

                    // 2. Don't eat Flying Animals
                    if (target instanceof FlyingAnimal) return false;

                    // 3. SIZE CHECK: specific height and width limits
                    // Example: Height < 1.0 blocks AND Width < 1.0 blocks
                    boolean isSmallEnough = target.getBbHeight() <= 1.0F && target.getBbWidth() <= 1.0F;

                    return isSmallEnough;
                }
        ));
        this.targetSelector.addGoal(12, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(13, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(14, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(15, new FloatGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.ARMOR, 0D)
                .add(Attributes.FOLLOW_RANGE, 32D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0D)
                .add(Attributes.ATTACK_DAMAGE, 8D);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.BEEF);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        AgeableMob child = ModEntities.ZHENYUANOPTERUS.get().create(pLevel);
        if (child instanceof ZhenyuanopterusEntity baby) {
            ZhenyuanopterusVariant randomVariant = Util.getRandom(ZhenyuanopterusVariant.values(), this.random);
            baby.setVariant(randomVariant);
        }
        return child;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (!level().isClientSide && hit && target instanceof LivingEntity) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.triggerAnim("attackController", "attack");
                this.playSound(ModSounds.BEAK_ATTACK.get(), 1.0F, 1.0F);
            }
        }
        return hit;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Walk/Run/Idle", state -> {
            if (!ZhenyuanopterusEntity.this.onGround()) {
                return state.setAndContinue(RawAnimation.begin().then("anim.zhenyuanopterus.fly", Animation.LoopType.LOOP));
            }

            if (state.isMoving())
                return state.setAndContinue(ZhenyuanopterusEntity.this.isSprinting() ? RawAnimation.begin().then("anim.zhenyuanopterus.walk", Animation.LoopType.LOOP) : RawAnimation.begin().then("anim.zhenyuanopterus.walk", Animation.LoopType.LOOP));

            return state.setAndContinue(RawAnimation.begin().then("anim.zhenyuanopterus.idle", Animation.LoopType.LOOP));
        }));

        controllers.add(new AnimationController<>(this, "attackController", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("anim.zhenyuanopterus.attack", Animation.LoopType.PLAY_ONCE)));

        controllers.add(new AnimationController<>(this, "mouthController", state -> PlayState.STOP)
                .triggerableAnim("mouth", RawAnimation.begin().then("anim.zhenyuanopterus.mouth", Animation.LoopType.PLAY_ONCE)));
    }

    private float getSignedTurnDelta() {
        // Only consider the body (torso) rotation so head look does not affect tail sway
        return Mth.wrapDegrees(this.yBodyRot - this.yBodyRotO);
    }

    private int mouthAnimCooldown = 0;

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            this.entityData.set(DATA_SYNCED_AGE, this.getAge());
            var maxHealthAttr = getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttr != null) {
                double baseAdult = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) this.getType()).getValue(Attributes.MAX_HEALTH);
                double desired = this.isBaby() ? baseAdult * 0.10D : baseAdult;
                if (maxHealthAttr.getBaseValue() != desired) {
                    double oldMax = maxHealthAttr.getBaseValue();
                    double healthRatio = this.getHealth() / (float) oldMax;
                    maxHealthAttr.setBaseValue(desired);
                    this.setHealth((float) (desired * Mth.clamp(healthRatio, 0.0F, 1.0F)));
                }
            }
        }

        if (!level().isClientSide) {
            if (mouthAnimCooldown > 0) {
                mouthAnimCooldown--;
            } else {
                this.triggerAnim("mouthController", "mouth");
                this.playSound(ModSounds.ZHENYUANOPTERUS_CALL.get(), 1.0F, 1.0F);
                // 30s–60s in ticks
                mouthAnimCooldown = this.random.nextInt(1200 - 600 + 1) + 600;
            }
        }

        if (level().isClientSide) {
            // Capture previous for smooth interpolation between ticks
            this.tailSwayPrev = this.tailSwayOffset;
            updateProceduralTailSway();
        }
    }

    private void updateProceduralTailSway() {
        // Turn input derived from rotation deltas; works even when standing still and turning
        float turnDegrees = getSignedTurnDelta();

        // Deadzone to ignore tiny jitter so the tail can return to center cleanly
        float deadzoneDeg = 0.6f; // smaller deadzone for more responsiveness
        float turnInput = 0.0f;
        if (Math.abs(turnDegrees) >= deadzoneDeg) {
            // Higher sensitivity so small in-place turns still affect the model
            turnInput = Mth.clamp(turnDegrees / 15.0f, -1.0f, 1.0f);
        }

        // Target offset: keep intuitive sign (positive input -> positive sway)
        float target = turnInput;

        // One-pole low-pass (no bounce). Larger alpha => snappier and less "stiff".
        float alpha = 0.24f; // try 0.20–0.30 to taste

        tailSwayOffset += (target - tailSwayOffset) * alpha;

        // Snap tiny residuals to zero so it visibly settles
        if (Math.abs(tailSwayOffset) < 0.003f) {
            tailSwayOffset = 0.0f;
        }

        // No oscillation velocity retained
        tailSwayVelocity = 0.0f;

        tailSwayOffset = Mth.clamp(tailSwayOffset, -1.5f, 1.5f);
    }

    // Expose to the model for bone rotation
    public float getTailSwayOffset() {
        return tailSwayOffset;
    }

    // Interpolated sway for smooth rendering between ticks
    public float getTailSwayOffset(float partialTick) {
        return Mth.lerp(Mth.clamp(partialTick, 0.0f, 1.0f), tailSwayPrev, tailSwayOffset);
    }

	/*? if <=1.20.1 {*/
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(DATA_SYNCED_AGE, 0);
	}
	/*?} else {*/
	/*@Override
	protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
		super.defineSynchedData(pBuilder);
		pBuilder.define(VARIANT, 0);
		pBuilder.define(DATA_SYNCED_AGE, 0);
	}
	*//*?}*/

    public int getSyncedAge() {
        return this.entityData.get(DATA_SYNCED_AGE);
    }

    public ZhenyuanopterusVariant getVariant() {
        return ZhenyuanopterusVariant.byId(this.getTypeVariant() & 255);
    }
    public int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(ZhenyuanopterusVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public boolean canMate(Animal other) {
        if (!super.canMate(other)) return false;
        if (!(other instanceof ZhenyuanopterusEntity that)) return false;
        return this.getVariant() != that.getVariant();
    }

	/*? if <=1.20.1 {*/
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
		ZhenyuanopterusVariant variant = Util.getRandom(ZhenyuanopterusVariant.values(), this.random);
		this.setVariant(variant);
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	/*?} else {*/
	/*@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		ZhenyuanopterusVariant variant = Util.getRandom(ZhenyuanopterusVariant.values(), this.random);
		this.setVariant(variant);
		return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
	}
	*//*?}*/

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ZHENYUANOPTERUS_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.ZHENYUANOPTERUS_DEATH.get();
    }
}