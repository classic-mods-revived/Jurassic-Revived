package net.cmr.jurassicrevived.entity.custom;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.ai.DinoData;
import net.cmr.jurassicrevived.entity.ai.DinoEntityBase;
import net.cmr.jurassicrevived.entity.ai.IDinoData;
import net.cmr.jurassicrevived.entity.ai.SprintingPanicGoal;
import net.cmr.jurassicrevived.entity.client.AlbertosaurusVariant;
import net.cmr.jurassicrevived.entity.client.OuranosaurusVariant;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.sound.ModSounds;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
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

public class OuranosaurusEntity extends DinoEntityBase implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

   	public static final int BABY_TO_ADULT_AGE_TICKS = 52800;
	private static final float ANIMAL_SCALE = 1.2F;
	private static final float MIN_ANIMAL_SCALE = !Constants.DEBUG_SIZES ? (ANIMAL_SCALE - 0.2F) : ANIMAL_SCALE;
	private static final float MAX_ANIMAL_SCALE = !Constants.DEBUG_SIZES ? (ANIMAL_SCALE + 0.2F) : ANIMAL_SCALE;

	private float lastDimensionsScale = 1.0F;

	private static final EntityDataAccessor<Integer> VARIANT =
		SynchedEntityData.defineId(OuranosaurusEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_SYNCED_AGE =
		SynchedEntityData.defineId(OuranosaurusEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> DATA_ANIMAL_SCALE =
		SynchedEntityData.defineId(OuranosaurusEntity.class, EntityDataSerializers.FLOAT);

    // Procedural tail sway state (client-side use for rendering)
    private float tailSwayOffset;   // Smoothed offset in range roughly [-1, 1]
    private float tailSwayVelocity; // Internal velocity for spring-damper
    private float tailSwayPrev;     // Previous frame value for interpolation

    public OuranosaurusEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.dinoData = new DinoData(
            getAIConfig().maxHunger(),
            getAIConfig().maxThirst(),
            IDinoData.Mood.NEUTRAL,
            IDinoData.Aggression.TERRITORIAL,
            0.75f,
            IDinoData.DietaryClassification.HERBIVORE,
            IDinoData.Type.TERRESTRIAL,
            IDinoData.Group.ORNITHOPOD,
            IDinoData.BirthType.EGG_LAYING,
            IDinoData.ActivityPattern.CATHEMERAL
        );
    }

	@Override
	public ItemStack getPickResult() {
		return new ItemStack(ModItems.OURANOSAURUS_SPAWN_EGG.get());
	}

    @Override
    public boolean isCarnivore() {
        return false;
    }

    @Override
    public boolean isMarine() {
        return false;
    }

    @Override
    public boolean isAmphibious() {
        return false;
    }

    @Override
    public Block getEggBlock() {
        return ModBlocks.INCUBATED_OURANOSAURUS_EGG.get();
    }

   	@Override
   	public DinoAIConfig getAIConfig() {
   		return new DinoAIConfig(0.3D, 1.1D, 1.5D, 100, 100, 0.05f, 0.1f, 20);
   	}

   	public void setBaby(boolean baby) {
   		this.setAge(baby ? -BABY_TO_ADULT_AGE_TICKS : 0);
   	}

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 55D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ARMOR, 0D)
                .add(Attributes.FOLLOW_RANGE, 32D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0D)
                .add(Attributes.ATTACK_DAMAGE, 12D);
    }


   	@Nullable
   	@Override
   	public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
   		AgeableMob child = ModEntities.OURANOSAURUS.get().create(pLevel);
   		if (child instanceof OuranosaurusEntity baby) {
   			OuranosaurusVariant randomVariant = Util.getRandom(OuranosaurusVariant.values(), this.random);
   			baby.setVariant(randomVariant);
   			baby.setBaby(true);
			baby.setAnimalScale(Mth.nextFloat(this.random, MIN_ANIMAL_SCALE, MAX_ANIMAL_SCALE));
   		}
   		return child;
   	}

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (!level().isClientSide && hit && target instanceof LivingEntity) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.triggerAnim("attackController", "attack");
            }
        }
        return hit;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Walk/Run/Idle", 5, state -> {
            if (state.isMoving())
                return state.setAndContinue(OuranosaurusEntity.this.isSprinting() ? RawAnimation.begin().then("anim.ouranosaurus.run", Animation.LoopType.LOOP) : RawAnimation.begin().then("anim.ouranosaurus.walk", Animation.LoopType.LOOP));

            return state.setAndContinue(RawAnimation.begin().then("anim.ouranosaurus.idle", Animation.LoopType.LOOP));
        }));

        controllers.add(new AnimationController<>(this, "attackController", 5, state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("anim.ouranosaurus.attack", Animation.LoopType.PLAY_ONCE)));

        controllers.add(new AnimationController<>(this, "mouthController", 5, state -> PlayState.STOP)
                .triggerableAnim("mouth", RawAnimation.begin().then("anim.ouranosaurus.mouth", Animation.LoopType.PLAY_ONCE)));
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

		updateDynamicDimensions();

		if (!level().isClientSide) {
            if (mouthAnimCooldown > 0) {
                mouthAnimCooldown--;
            } else {
                this.triggerAnim("mouthController", "mouth");
                this.playSound(ModSounds.OURANOSAURUS_CALL.get(), 1.0F, 1.0F);
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
		this.entityData.define(DATA_ANIMAL_SCALE, 1.0F);
	}
	/*?} else {*/
	/*@Override
	protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
		super.defineSynchedData(pBuilder);
		pBuilder.define(VARIANT, 0);
		pBuilder.define(DATA_SYNCED_AGE, 0);
		pBuilder.define(DATA_ANIMAL_SCALE, 1.0F);
	}
	*//*?}*/

	public int getSyncedAge() {
		return this.entityData.get(DATA_SYNCED_AGE);
	}

	public float getAnimalScale() {
		return this.entityData.get(DATA_ANIMAL_SCALE);
	}

	private void setAnimalScale(float animalScale) {
		this.entityData.set(DATA_ANIMAL_SCALE, animalScale);
	}

	public float getGrowthScale() {
		if (!this.isBaby()) {
			return 1.0F;
		}

		int age = this.level().isClientSide ? this.getSyncedAge() : this.getAge();
		float growthProgress = Mth.clamp((BABY_TO_ADULT_AGE_TICKS + age) / (float) BABY_TO_ADULT_AGE_TICKS, 0.0F, 1.0F);
		return Mth.lerp(growthProgress, 0.2F, 1.0F);
	}

	public float getTotalModelScale() {
		return this.getAnimalScale() * this.getGrowthScale();
	}

	private void updateDynamicDimensions() {
		float dimensionsScale = this.getTotalModelScale();
		if (Math.abs(dimensionsScale - this.lastDimensionsScale) > 0.01F) {
			this.lastDimensionsScale = dimensionsScale;
			this.refreshDimensions();
		}
	}

	@Override
public float getLiveDinoScale() {
    return this.getTotalModelScale();
}
    public int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public OuranosaurusVariant getVariant() {
        return OuranosaurusVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(OuranosaurusVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public boolean canMate(Animal other) {
        if (!super.canMate(other)) return false;
        if (!(other instanceof OuranosaurusEntity that)) return false;
        return this.getVariant() != that.getVariant();
    }
   	@Override
   	public void addAdditionalSaveData(CompoundTag pCompound) {
   		super.addAdditionalSaveData(pCompound);
   		pCompound.putInt("Variant", this.getTypeVariant());
   		pCompound.putFloat("AnimalScale", this.getAnimalScale());
   	}

   	@Override
   	public void readAdditionalSaveData(CompoundTag pCompound) {
   		super.readAdditionalSaveData(pCompound);
   		this.entityData.set(VARIANT, pCompound.getInt("Variant"));
   		if (pCompound.contains("AnimalScale")) {
   			this.setAnimalScale(pCompound.getFloat("AnimalScale"));
   		}
   	}

   	/*? if <=1.20.1 {*/
   	@Override
   	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
   		OuranosaurusVariant variant = Util.getRandom(OuranosaurusVariant.values(), this.random);
   		this.setVariant(variant);
   		this.setAnimalScale(Mth.nextFloat(this.random, MIN_ANIMAL_SCALE, MAX_ANIMAL_SCALE));
   		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
   	}
   	/*?} else {*/
   	/*@Override
   	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
   		OuranosaurusVariant variant = Util.getRandom(OuranosaurusVariant.values(), this.random);    this.setVariant(variant);    this.setAnimalScale(Mth.nextFloat(this.random, MIN_ANIMAL_SCALE, MAX_ANIMAL_SCALE));    return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);}
   	*//*?}*/

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.OURANOSAURUS_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.OURANOSAURUS_DEATH.get();
    }

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return ModSounds.OURANOSAURUS_CALL.get();
	}

	@Override
	public double getFluidJumpThreshold() {
		return this.getEyeHeight();
	}
}