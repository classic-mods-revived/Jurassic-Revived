package net.cmr.jurassicrevived.compat;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.custom.*;
import net.cmr.jurassicrevived.block.entity.custom.*;
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

@WailaPlugin
public class MachineJadePlugin implements IWailaPlugin {
    private static final ResourceLocation UID = Constants.rl("machine_progress");
    private static final String NBT_PROGRESS = "jr_progress";
    private static final String NBT_MAX = "jr_max";

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
										//? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
										@Override
										public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
											CompoundTag data = accessor.getServerData();
											if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

											int progress = data.getInt(NBT_PROGRESS);
											int max = Math.max(1, data.getInt(NBT_MAX));
											float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

											IElementHelper h = tooltip.getElementHelper();
											IProgressStyle pStyle = h.progressStyle()
												.color(0xFFFFFFFF)
												.textColor(0xFFFFFFFF);
											IBoxStyle box = new ThickBorderBox(1.0f);

											tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
										}
										//?}
                                       @Override
                                       public ResourceLocation getUid() {
                                           return UID;
                                       }
                                   }, DNAExtractorBlock.class
        );
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
										//? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
										@Override
										public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
											CompoundTag data = accessor.getServerData();
											if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

											int progress = data.getInt(NBT_PROGRESS);
											int max = Math.max(1, data.getInt(NBT_MAX));
											float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

											IElementHelper h = tooltip.getElementHelper();
											IProgressStyle pStyle = h.progressStyle()
												.color(0xFFFFFFFF)
												.textColor(0xFFFFFFFF);
											IBoxStyle box = new ThickBorderBox(1.0f);

											tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
										}
										//?}
                                       @Override
                                       public ResourceLocation getUid() {
                                           return UID;
                                       }
                                   }, DNAAnalyzerBlock.class
        );
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
			//? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
			@Override
			public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
				CompoundTag data = accessor.getServerData();
				if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

				int progress = data.getInt(NBT_PROGRESS);
				int max = Math.max(1, data.getInt(NBT_MAX));
				float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

				IElementHelper h = tooltip.getElementHelper();
				IProgressStyle pStyle = h.progressStyle()
					.color(0xFFFFFFFF)
					.textColor(0xFFFFFFFF);
				IBoxStyle box = new ThickBorderBox(1.0f);

				tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
			}
			//?}

                                       @Override
                                       public ResourceLocation getUid() {
                                           return UID;
                                       }
                                   }, DNAHybridizerBlock.class
        );
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
			//? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
			@Override
			public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
				CompoundTag data = accessor.getServerData();
				if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

				int progress = data.getInt(NBT_PROGRESS);
				int max = Math.max(1, data.getInt(NBT_MAX));
				float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

				IElementHelper h = tooltip.getElementHelper();
				IProgressStyle pStyle = h.progressStyle()
					.color(0xFFFFFFFF)
					.textColor(0xFFFFFFFF);
				IBoxStyle box = new ThickBorderBox(1.0f);

				tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
			}
			//?}

                                       @Override
                                       public ResourceLocation getUid() {
                                           return UID;
                                       }
                                   }, EmbryoCalcificationMachineBlock.class
        );
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
			//? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
			@Override
			public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
				CompoundTag data = accessor.getServerData();
				if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

				int progress = data.getInt(NBT_PROGRESS);
				int max = Math.max(1, data.getInt(NBT_MAX));
				float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

				IElementHelper h = tooltip.getElementHelper();
				IProgressStyle pStyle = h.progressStyle()
					.color(0xFFFFFFFF)
					.textColor(0xFFFFFFFF);
				IBoxStyle box = new ThickBorderBox(1.0f);

				tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
			}
			//?}

                                       @Override
                                       public ResourceLocation getUid() {
                                           return UID;
                                       }
                                   }, EmbryonicMachineBlock.class
        );
		reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
									   //? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
									   @Override
									   public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
										   CompoundTag data = accessor.getServerData();
										   if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

										   int progress = data.getInt(NBT_PROGRESS);
										   int max = Math.max(1, data.getInt(NBT_MAX));
										   float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

										   IElementHelper h = tooltip.getElementHelper();
										   IProgressStyle pStyle = h.progressStyle()
											   .color(0xFFFFFFFF)
											   .textColor(0xFFFFFFFF);
										   IBoxStyle box = new ThickBorderBox(1.0f);

										   tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
									   }
									   //?}

									   @Override
									   public ResourceLocation getUid() {
										   return UID;
									   }
								   }, FossilCleanerBlock.class
		);
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
			//? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
			@Override
			public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
				CompoundTag data = accessor.getServerData();
				if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

				int progress = data.getInt(NBT_PROGRESS);
				int max = Math.max(1, data.getInt(NBT_MAX));
				float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

				IElementHelper h = tooltip.getElementHelper();
				IProgressStyle pStyle = h.progressStyle()
					.color(0xFFFFFFFF)
					.textColor(0xFFFFFFFF);
				IBoxStyle box = new ThickBorderBox(1.0f);

				tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
			}
			//?}

                                       @Override
                                       public ResourceLocation getUid() {
                                           return UID;
                                       }
                                   }, FossilGrinderBlock.class
        );
        reg.registerBlockComponent(new snownee.jade.api.IBlockComponentProvider() {
			//? if >1.20.1 {
                                       /*@Override
                                       public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                                           CompoundTag data = accessor.getServerData();
                                           if (data == null || !data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                                           int progress = Math.max(0, data.getInt(NBT_PROGRESS));
                                           int max = Math.max(1, data.getInt(NBT_MAX));
                                           float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                                           IElementHelper h = IElementHelper.get();
                                           ProgressStyle style = h.progressStyle()
                                                   .color(0xFFFFFFFF, 0xFFFFFFFF)
                                                   .direction(ScreenDirection.RIGHT)
                                                   .fitContentX(true)
                                                   .fitContentY(true);
                                           BoxStyle box = BoxStyle.getNestedBox();
                                           IElement bar = h.progress(ratio, Component.empty(), style, box, true);
                                           tooltip.add(bar);
                                       }
									   *///?} else {
			@Override
			public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
				CompoundTag data = accessor.getServerData();
				if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

				int progress = data.getInt(NBT_PROGRESS);
				int max = Math.max(1, data.getInt(NBT_MAX));
				float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

				IElementHelper h = tooltip.getElementHelper();
				IProgressStyle pStyle = h.progressStyle()
					.color(0xFFFFFFFF)
					.textColor(0xFFFFFFFF);
				IBoxStyle box = new ThickBorderBox(1.0f);

				tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
			}
			//?}

                                       @Override
                                       public ResourceLocation getUid() {
                                           return UID;
                                       }
                                   }, IncubatorBlock.class
        );
    }

    @Override
    public void register(IWailaCommonRegistration reg) {
        IServerDataProvider<BlockAccessor> provider = new IServerDataProvider<>() {
            @Override
            public void appendServerData(CompoundTag data, BlockAccessor accessor) {
                BlockEntity be = accessor.getBlockEntity();
                if (be == null) return;

                if (be instanceof IncubatorBlockEntity inc) {
                    int[] p = getArray(inc, "progress");
                    int[] m = getArray(inc, "maxProgress");
                    if (p != null && m != null && p.length == m.length && p.length > 0) {
                        float best = 0f;
                        for (int i = 0; i < p.length; i++) {
                            int max = Math.max(1, m[i]);
                            best = Math.max(best, p[i] / (float) max);
                        }
                        int scaledMax = 1000;
                        int scaledProgress = Math.round(best * scaledMax);
                        put(data, scaledProgress, scaledMax);
                    }
                    return;
                }

                int progress = getInt(be, "progress");
                int max = Math.max(1, getInt(be, "maxProgress"));
                put(data, progress, max);
            }

            private void put(CompoundTag data, int progress, int max) {
                data.putInt(NBT_PROGRESS, progress);
                data.putInt(NBT_MAX, Math.max(1, max));
            }

            private int getInt(Object be, String field) {
                try {
                    var f = be.getClass().getDeclaredField(field);
                    f.setAccessible(true);
                    return f.getInt(be);
                } catch (Throwable t) {
                    return 0;
                }
            }

            private int[] getArray(Object be, String field) {
                try {
                    var f = be.getClass().getDeclaredField(field);
                    f.setAccessible(true);
                    return (int[]) f.get(be);
                } catch (Throwable t) {
                    return null;
                }
            }

            @Override
            public ResourceLocation getUid() {
                return UID;
            }
        };

        // Register individually per BE class
        reg.registerBlockDataProvider(provider, DNAExtractorBlockEntity.class);
        reg.registerBlockDataProvider(provider, DNAAnalyzerBlockEntity.class);
        reg.registerBlockDataProvider(provider, DNAHybridizerBlockEntity.class);
        reg.registerBlockDataProvider(provider, EmbryoCalcificationMachineBlockEntity.class);
        reg.registerBlockDataProvider(provider, EmbryonicMachineBlockEntity.class);
        reg.registerBlockDataProvider(provider, FossilCleanerBlockEntity.class);
        reg.registerBlockDataProvider(provider, FossilGrinderBlockEntity.class);
        reg.registerBlockDataProvider(provider, IncubatorBlockEntity.class);
    }
}