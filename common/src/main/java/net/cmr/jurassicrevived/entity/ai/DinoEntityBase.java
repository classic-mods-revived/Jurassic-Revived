package net.cmr.jurassicrevived.entity.ai;

import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.entity.ai.navigation.CustomDinoNavigation;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DinoEntityBase extends Animal {

    protected IDinoData dinoData;
    protected final DinoAIController aiController;

    public DinoEntityBase(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.aiController = new DinoAIController(this);
    }

    // Disable Vanilla Goals
    @Override
    protected void registerGoals() {}

    // Ensure our AI ticks regardless of what vanilla method is called
    @Override
    public void tick() {
        super.tick(); // Vanilla Mob tick

        if (!this.level().isClientSide) {
            // FORCE TICK AI
            this.aiController.tick();

            // Sync
            if (dinoData != null) {
                dinoData.tickSync(this.entityData);
            }
        }
    }

    // --- Navigation ---
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new CustomDinoNavigation(this, level);
    }

    // --- Hooks ---
    public abstract boolean isCarnivore();
    public abstract boolean isMarine();
    public abstract boolean isAmphibious();
    public abstract Block getEggBlock(); // New hook for breeding
    public abstract DinoAIConfig getAIConfig();

    public record DinoAIConfig(double walkSpeed, double runSpeed, double attackReach,
                               float maxHunger, float maxThirst,
                               float hungerDecay, float thirstDecay,
                               float defaultHungerReplenishment) {}

    private static final Map<EntityType<?>, Float> ENTITY_HUNGER_VALUES = new HashMap<>();

    public static void registerHungerValue(EntityType<?> type, float value) {
        ENTITY_HUNGER_VALUES.put(type, value);
    }

    public float getHungerReplenishment(LivingEntity target) {
        return ENTITY_HUNGER_VALUES.getOrDefault(target.getType(), getAIConfig().defaultHungerReplenishment());
    }

    public IDinoData getDinoData() { return this.dinoData; }

	public List<TagKey<EntityType<?>>> getDinoTags() {
		ResourceLocation id = EntityType.getKey(this.getType());
		String path = id.getPath();

		return List.of(
			ModTags.EntityTypes.forgeDino(path),
			ModTags.EntityTypes.neoforgeDino(path),
			ModTags.EntityTypes.fabricDino(path)
		);
	}

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown() && player.getMainHandItem().isEmpty()) {
            if (this.dinoData != null) {
                // Format details nicely
                StringBuilder sb = new StringBuilder();
                sb.append("§6--- Dino Status ---§r\n");
                sb.append("§eState:§r ").append(this.aiController.getCurrentState()).append("\n");
                
                // Line 1: Biological Classification
                sb.append("§eType:§r ").append(dinoData.getType())
                  .append("  §eGroup:§r ").append(dinoData.getGroup())
                  .append("  §eDiet:§r ").append(dinoData.getDiet()).append("\n");
                
                // Line 2: Behavioral/Lifecycle
                sb.append("§eBehavior:§r ").append(dinoData.getAggression())
                  .append("  §eBirth:§r ").append(dinoData.getBirthType())
                  .append("  §eActivity:§r ").append(dinoData.getActivityPattern()).append("\n");
                
                // Line 3: Status
                sb.append("§eMood:§r ").append(dinoData.getMood())
                  .append("  §eConditions:§r ").append(dinoData.getConditions()).append("\n");
                
                // Line 4: Vitals
                sb.append("§aHunger:§r ").append(String.format("%.1f", dinoData.getHunger()))
                  .append("  §bThirst:§r ").append(String.format("%.1f", dinoData.getThirst())).append("\n");
            
                if (this.aiController.getAttackTarget() != null) {
                    sb.append("§cTarget:§r ").append(this.aiController.getAttackTarget().getName().getString()).append("\n");
                }
                if (this.aiController.getWaterTarget() != null) {
                    sb.append("§9Water Target:§r ").append(this.aiController.getWaterTarget().toShortString()).append("\n");
                }

                player.sendSystemMessage(Component.literal(sb.toString()));
				return InteractionResult.SUCCESS;
			}
		}

		ItemStack stack = player.getItemInHand(hand);
		if (this.isFood(stack)) {
			if (!this.level().isClientSide) {
				feedDino(player, stack);
			}

			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}

		return super.mobInteract(player, hand);
	}

	private void feedDino(Player player, ItemStack stack) {
		if (this.dinoData != null) {
			float hunger = this.dinoData.getHunger();
			float maxHunger = this.getAIConfig().maxHunger();
			float replenishAmount = Math.max(1.0f, this.getAIConfig().defaultHungerReplenishment());

			this.dinoData.setHunger(Math.min(maxHunger, hunger + replenishAmount));
		}

		if (this.canFallInLove()) {
			this.setInLove(player);

			if (this.dinoData != null) {
				this.dinoData.addCondition(IDinoData.Condition.READY_TO_MATE);
			}
		}

		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}
	}

	@Override
	public boolean isFood(ItemStack stack) {
		if (dinoData == null) return false;
		IDinoData.DietaryClassification diet = dinoData.getDiet();

		if (diet == IDinoData.DietaryClassification.CARNIVORE) {
			return isMeatFood(stack);
		}

		if (diet == IDinoData.DietaryClassification.PISCIVORE) {
			return isMeatFood(stack) || isFishFood(stack);
		}

		if (diet == IDinoData.DietaryClassification.HERBIVORE) {
			return isPlantFood(stack);
		}

		if (diet == IDinoData.DietaryClassification.OMNIVORE) {
			return isMeatFood(stack) || isFishFood(stack) || isPlantFood(stack);
		}

		return false;
	}

	private boolean isMeatFood(ItemStack stack) {
		return stack.is(Items.BEEF)
		       || stack.is(Items.COOKED_BEEF)
		       || stack.is(Items.PORKCHOP)
		       || stack.is(Items.COOKED_PORKCHOP)
		       || stack.is(Items.CHICKEN)
		       || stack.is(Items.COOKED_CHICKEN)
		       || stack.is(Items.MUTTON)
		       || stack.is(Items.COOKED_MUTTON)
		       || stack.is(Items.RABBIT)
		       || stack.is(Items.COOKED_RABBIT)
		       || stack.is(Items.ROTTEN_FLESH);
	}

	private boolean isFishFood(ItemStack stack) {
		return stack.is(ItemTags.FISHES)
		       || stack.is(Items.COD)
		       || stack.is(Items.COOKED_COD)
		       || stack.is(Items.SALMON)
		       || stack.is(Items.COOKED_SALMON)
		       || stack.is(Items.TROPICAL_FISH)
		       || stack.is(Items.PUFFERFISH);
	}

	private boolean isPlantFood(ItemStack stack) {
		return stack.is(ItemTags.LEAVES)
		       || stack.is(ItemTags.FLOWERS)
		       || stack.is(Items.WHEAT)
		       || stack.is(Items.CARROT)
		       || stack.is(Items.POTATO)
		       || stack.is(Items.BEETROOT)
		       || stack.is(Items.APPLE)
		       || stack.is(Items.MELON_SLICE)
		       || stack.is(Items.SWEET_BERRIES)
		       || stack.is(Items.GLOW_BERRIES)
		       || stack.is(Items.SEAGRASS)
		       || stack.is(Items.KELP);
	}

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal partner) {
        // Custom breeding logic
        if (this.dinoData != null && this.dinoData.getBirthType() == IDinoData.BirthType.EGG_LAYING) {
            // Place Egg Block
            Block eggBlock = getEggBlock();
            if (eggBlock != null) {
                // Consume breeding status
                this.setAge(6000);
                partner.setAge(6000);
                this.resetLove();
                partner.resetLove();

                // Place egg at parent location
                level.setBlock(this.blockPosition(), eggBlock.defaultBlockState(), 3);
                level.levelEvent(2001, this.blockPosition(), Block.getId(eggBlock.defaultBlockState())); // Particles?
            }
        } else {
            // Live Birth (Default Vanilla)
            super.spawnChildFromBreeding(level, partner);
        }
    }

    // --- Attack Logic ---
    public boolean canAttack(LivingEntity target) {
        if (target == null || target == this) return false;
        if (!target.isAlive()) return false;
        if (this.getClass().equals(target.getClass())) return false;

        // Prevent targeting if the entity is invisible (unless we have special senses, but basic AI implies sight)
        if (target.hasEffect(MobEffects.INVISIBILITY)) return false;

        if (target instanceof Player player) {
            if (player.isCreative() || player.isSpectator()) return false;

            // Peaceful mode check: Do not target players if difficulty is Peaceful
            if (this.level().getDifficulty() == Difficulty.PEACEFUL) return false;

            if (dinoData != null && dinoData.isWhitelisted(player.getUUID())) return false;
            return true; // Always attack valid players
        }

        double myVolume = this.getBbWidth() * this.getBbWidth() * this.getBbHeight();
        double targetVolume = target.getBbWidth() * target.getBbWidth() * target.getBbHeight();
    
        // Prevent attacking entities significantly larger than self
        // Check Volume (2.5x)
        if (myVolume > 0 && targetVolume > myVolume * 2.5) return false;
        
        // Check Height (1.2x) - Prevents attacking tall things like Brachiosaurus even if volume calc is close
        if (this.getBbHeight() > 0 && target.getBbHeight() > this.getBbHeight() * 1.2) return false;

        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result && !this.level().isClientSide && source.getEntity() instanceof LivingEntity attacker) {
            this.aiController.onHurtBy(attacker);
        }
        return result;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if(dinoData != null) dinoData.saveNBT(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if(dinoData != null) dinoData.loadNBT(tag);
    }
}