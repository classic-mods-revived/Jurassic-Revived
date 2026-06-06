package net.cmr.jurassicrevived.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
//? if >1.20.1 {
/*import net.minecraft.core.registries.BuiltInRegistries;
*///?}
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

//? if >1.20.1 {
/*import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
*///?} else {
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
//?}

import java.util.List;
import java.util.function.Supplier;

public class CustomGenderedSpawnEggItem extends SpawnEggItem {
	private static final String KEY_SELECTED_VARIANT = "SelectedVariant";
	private static final String KEY_VARIANT = "Variant";
	private static final String KEY_ENTITY_TAG = "EntityTag";
	private static final int VARIANT_COUNT = 2; // 0=Male, 1=Female
	private static final int SELECTION_MODE_COUNT = 3; // 0=Male, 1=Female, 2=Random

	private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;

	public CustomGenderedSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type,
		int backgroundColor,
		int highlightColor,
		Item.Properties properties) {
		super(EntityType.PIG, backgroundColor, highlightColor, properties);
		this.typeSupplier = type;
	}

	//? if >1.20.1 {
	/*@Override
	public EntityType<?> getType(ItemStack stack) {
		return typeSupplier.get();
	}
	*///?} else {
	@Override
	public EntityType<?> getType(@Nullable CompoundTag nbt) {
		return typeSupplier.get();
	}
	//?}

	private static int getSelectedVariant(ItemStack stack) {
		//? if >1.20.1 {
        /*CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return 0;
        CompoundTag tag = data.copyTag();
        if (!tag.contains(KEY_SELECTED_VARIANT)) return 0;
        return Math.floorMod(tag.getInt(KEY_SELECTED_VARIANT), SELECTION_MODE_COUNT);
        *///?} else {
		CompoundTag tag = stack.getTag();
		if (tag == null || !tag.contains(KEY_SELECTED_VARIANT)) return 0;
		return Math.floorMod(tag.getInt(KEY_SELECTED_VARIANT), SELECTION_MODE_COUNT);
		//?}
	}

	private static void setSelectedVariant(ItemStack stack, int variant) {
		int v = Math.floorMod(variant, SELECTION_MODE_COUNT);
		//? if >1.20.1 {
        /*stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, existing -> {
            CompoundTag tag = existing.copyTag();
            tag.putInt(KEY_SELECTED_VARIANT, v);
            return CustomData.of(tag);
        });
        *///?} else {
		stack.getOrCreateTag().putInt(KEY_SELECTED_VARIANT, v);
		//?}
	}

	private static void cycleVariant(ItemStack stack) {
		setSelectedVariant(stack, (getSelectedVariant(stack) + 1) % SELECTION_MODE_COUNT);
	}

	private void ensureEntityDataHasVariant(ItemStack stack, Level level) {
		final int selectedVariant = getSelectedVariant(stack);
		final int variant = selectedVariant == 2 ? level.random.nextInt(VARIANT_COUNT) : selectedVariant;
		//? if >1.20.1 {
        /*stack.update(DataComponents.ENTITY_DATA, CustomData.EMPTY, existing -> {
            CompoundTag tag = existing.copyTag();
            tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(typeSupplier.get()).toString());
            tag.putInt(KEY_VARIANT, variant);
            return CustomData.of(tag);
        });
        *///?} else {
		CompoundTag root = stack.getOrCreateTag();
		CompoundTag entityTag = root.getCompound(KEY_ENTITY_TAG);
		entityTag.putInt(KEY_VARIANT, variant);
		root.put(KEY_ENTITY_TAG, entityTag);
		//?}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (player.isSecondaryUseActive()) {
			if (!level.isClientSide) {
				cycleVariant(stack);
				level.playSound(null, player.getX(), player.getY(), player.getZ(),
					SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5f, 1.1f);
			}
			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
		}

		ensureEntityDataHasVariant(stack, level);
		return super.use(level, player, hand);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		Level level = context.getLevel();

		if (player != null && player.isSecondaryUseActive()) {
			if (!level.isClientSide) {
				cycleVariant(context.getItemInHand());
				level.playSound(null, context.getClickedPos(),
					SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5f, 1.1f);
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}

		if (player == null || !player.isSecondaryUseActive()) {
			ensureEntityDataHasVariant(context.getItemInHand(), level);
		}
		return super.useOn(context);
	}

	//? if >1.20.1 {
    /*@Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        addGenderTooltip(stack, tooltip);
    }
    *///?} else {
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		addGenderTooltip(stack, tooltip);
	}
	//?}

	private void addGenderTooltip(ItemStack stack, List<Component> tooltip) {
		int v = getSelectedVariant(stack);
		String genderText = switch (v) {
			case 0 -> "Male";
			case 1 -> "Female";
			default -> "Random";
		};
		tooltip.add(Component.translatable("tooltip.jurassicrevived.gender", genderText));
		tooltip.add(Component.translatable("tooltip.jurassicrevived.gender.hint", "Shift-Right-Click"));
	}

	@Override
	public Component getName(ItemStack stack) {
		Component base = super.getName(stack);
		int variant = getSelectedVariant(stack);
		Component gender = switch (variant) {
			case 0 -> Component.literal("Male").withStyle(ChatFormatting.AQUA);
			case 1 -> Component.literal("Female").withStyle(ChatFormatting.LIGHT_PURPLE);
			default -> Component.literal("Random").withStyle(ChatFormatting.YELLOW);
		};

		return base.copy().append(Component.literal(" (")).append(gender).append(Component.literal(")"));
	}
}