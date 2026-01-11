package net.cmr.jurassicrevived.compat;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.custom.EggBlock;
import net.cmr.jurassicrevived.block.custom.IncubatedEggBlock;
import net.cmr.jurassicrevived.block.entity.custom.EggBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.*;
//? if <=1.20.1 {
import net.minecraft.client.gui.navigation.ScreenDirection;
import snownee.jade.impl.ui.ProgressStyle;
//?}

// ... existing code ...
@WailaPlugin
public class EggJadePlugin implements IWailaPlugin {
    private static final ResourceLocation EGG_UID = Constants.rl("egg");
    private static final String NBT_SECS = "jr_secs";
    private static final String NBT_TOTAL = "jr_total";

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
			//? if >1.20.1 {
			/*@Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                CompoundTag data = accessor.getServerData();
                if (data == null || !data.contains(NBT_SECS)) return;

                int secs = data.getInt(NBT_SECS);
                int total = data.contains(NBT_TOTAL) ? Math.max(1, data.getInt(NBT_TOTAL)) : 5;
                float ratio = Mth.clamp(1.0f - (secs / (float) total), 0.0f, 1.0f);

                IElementHelper h = IElementHelper.get();
                ProgressStyle style = h.progressStyle()
                        .color(0xFFFFFFFF, 0xFFFFFFFF)
                        .direction(ScreenDirection.RIGHT)
                        .fitContentX(true)
                        .fitContentY(true);
                BoxStyle box = BoxStyle.getNestedBox();
                IElement progress = h.progress(ratio, Component.empty(), style, box, true);
                tooltip.add(progress);

                tooltip.add(Component.translatable("tooltip.jurassicrevived.egg.hatches_in_seconds", secs)
                        .withStyle(ChatFormatting.YELLOW));
            }
			*///?} else {
			@Override
			public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
				CompoundTag data = accessor.getServerData();
				if (!data.contains(NBT_SECS)) return;

				int secs = data.getInt(NBT_SECS);
				int total = data.contains(NBT_TOTAL) ? Math.max(1, data.getInt(NBT_TOTAL)) : 5;
				float ratio = Mth.clamp(1.0f - (secs / (float) total), 0.0f, 1.0f);

				IElementHelper h = tooltip.getElementHelper();

				// Properly build each style from its own factory
				IProgressStyle pStyle = h.progressStyle()
					.color(0xFFFFFFFF)      // ARGB filled color
					.textColor(0xFFFFFFFF); // ARGB text color

				IBoxStyle box = new ThickBorderBox(1.0f); // any width you want
				tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));

				tooltip.add(Component.translatable("tooltip.jurassicrevived.egg.hatches_in_seconds", secs)
					.withStyle(ChatFormatting.YELLOW));
			}
			//?}
            @Override
            public ResourceLocation getUid() {
                return EGG_UID;
            }
        }, EggBlock.class);
        // Register the same provider for IncubatedEggBlock separately
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
			//? if >1.20.1 {
            /*@Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                CompoundTag data = accessor.getServerData();
                if (data == null || !data.contains(NBT_SECS)) return;

                int secs = data.getInt(NBT_SECS);
                int total = data.contains(NBT_TOTAL) ? Math.max(1, data.getInt(NBT_TOTAL)) : 5;
                float ratio = Mth.clamp(1.0f - (secs / (float) total), 0.0f, 1.0f);

                IElementHelper h = IElementHelper.get();
                ProgressStyle style = h.progressStyle()
                        .color(0xFFFFFFFF, 0xFFFFFFFF)
                        .direction(ScreenDirection.RIGHT)
                        .fitContentX(true)
                        .fitContentY(true);
                BoxStyle box = BoxStyle.getNestedBox();
                IElement progress = h.progress(ratio, Component.empty(), style, box, true);
                tooltip.add(progress);

                tooltip.add(Component.translatable("tooltip.jurassicrevived.egg.hatches_in_seconds", secs)
                        .withStyle(ChatFormatting.YELLOW));
            }
			*///?} else {
			@Override
			public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
				CompoundTag data = accessor.getServerData();
				if (!data.contains(NBT_SECS)) return;

				int secs = data.getInt(NBT_SECS);
				int total = data.contains(NBT_TOTAL) ? Math.max(1, data.getInt(NBT_TOTAL)) : 5;
				float ratio = Mth.clamp(1.0f - (secs / (float) total), 0.0f, 1.0f);

				IElementHelper h = tooltip.getElementHelper();

				// Properly build each style from its own factory
				IProgressStyle pStyle = h.progressStyle()
					.color(0xFFFFFFFF)      // ARGB filled color
					.textColor(0xFFFFFFFF); // ARGB text color

				IBoxStyle box = new ThickBorderBox(1.0f); // any width you want
				tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));

				tooltip.add(Component.translatable("tooltip.jurassicrevived.egg.hatches_in_seconds", secs)
					.withStyle(ChatFormatting.YELLOW));
			}
			//?}

            @Override
            public ResourceLocation getUid() {
                return EGG_UID;
            }
        }, IncubatedEggBlock.class);
    }

    @Override
    public void register(IWailaCommonRegistration reg) {
        reg.registerBlockDataProvider(new IServerDataProvider<BlockAccessor>() {
            @Override
            public void appendServerData(CompoundTag data, BlockAccessor accessor) {
                BlockEntity be = accessor.getBlockEntity();
                if (be instanceof EggBlockEntity egg) {
                    int secs = egg.getSecondsRemaining(accessor.getLevel());
                    data.putInt(NBT_SECS, secs);
                    data.putInt(NBT_TOTAL, egg.getTotalSeconds());
                }
            }

            @Override
            public ResourceLocation getUid() {
                return EGG_UID;
            }
        }, EggBlockEntity.class); // BE is shared by both blocks
    }
}