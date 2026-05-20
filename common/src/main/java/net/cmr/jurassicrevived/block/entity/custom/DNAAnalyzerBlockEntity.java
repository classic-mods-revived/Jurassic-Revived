package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.custom.DNAAnalyzerBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.DNAAnalyzerRecipe;
import net.cmr.jurassicrevived.recipe.DNAAnalyzerRecipeInput;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.custom.DNAAnalyzerMenu;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

//? if >1.20.1 {
/*import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.RecipeHolder;
*///?} else {
import net.minecraft.core.RegistryAccess;
//?}

import java.util.Optional;

public class DNAAnalyzerBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {

	public final SimpleContainer itemHandler = new SimpleContainer(5) {
		@Override
		public void setChanged() {
			super.setChanged();
			DNAAnalyzerBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};

	private static final int TEST_TUBE_SLOT = 0;
	private static final int MATERIAL_SLOT = 1;
	private static final int OUTPUT_SLOT_1 = 2;

	private ItemStack lockedOutput = ItemStack.EMPTY;
	private String lastInputSignature = "";

	private final ContainerData data;
	private int progress = 0;
	private int maxProgress = 600;
	private final int DEFAULT_MAX_PROGRESS = 600;

	private static final int TRANSFER_RATE = 1000;
	private final ModEnergyStorage energyStorage = createEnergyStorage();

	public DNAAnalyzerBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.DNA_ANALYZER_BE.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return switch (pIndex) {
					case 0 -> DNAAnalyzerBlockEntity.this.progress;
					case 1 -> DNAAnalyzerBlockEntity.this.maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int pIndex, int pValue) {
				switch (pIndex) {
					case 0 -> DNAAnalyzerBlockEntity.this.progress = pValue;
					case 1 -> DNAAnalyzerBlockEntity.this.maxProgress = pValue;
				}
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	private ModEnergyStorage createEnergyStorage() {
		return new ModEnergyStorage(64000, TRANSFER_RATE) {
			@Override
			public void onEnergyChanged() {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public ModEnergyStorage getEnergyStorage(@Nullable Direction direction) {
		return this.energyStorage;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.jurassicrevived.dna_analyzer");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new DNAAnalyzerMenu(i, inventory, this, this.data);
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && this.progress == 0;
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("Inventory", itemHandler.createTag(registries));
		tag.putInt("Prog", this.progress);
		tag.putInt("MaxProg", this.maxProgress);
		tag.put("Energy", energyStorage.saveNBT());
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		itemHandler.fromTag(tag.getList("Inventory", 10), registries);
		progress = tag.getInt("Prog");
		maxProgress = tag.getInt("MaxProg");
		if (tag.contains("Energy")) {
			energyStorage.loadNBT(tag.getCompound("Energy"));
		}
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Inventory", saveInventory());
		tag.putInt("Prog", this.progress);
		tag.putInt("MaxProg", this.maxProgress);
		tag.put("Energy", energyStorage.saveNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		loadInventory(tag.getList("Inventory", 10));
		progress = tag.getInt("Prog");
		maxProgress = tag.getInt("MaxProg");
		if (tag.contains("Energy")) {
			energyStorage.loadNBT(tag.getCompound("Energy"));
		}
	}
	//?}

	private ListTag saveInventory() {
		ListTag listTag = new ListTag();

		for (int slot = 0; slot < itemHandler.getContainerSize(); slot++) {
			ItemStack stack = itemHandler.getItem(slot);
			if (!stack.isEmpty()) {
				CompoundTag stackTag = new CompoundTag();
				stackTag.putByte("Slot", (byte) slot);
				stack.save(stackTag);
				listTag.add(stackTag);
			}
		}

		return listTag;
	}

	private void loadInventory(ListTag listTag) {
		itemHandler.clearContent();

		for (int i = 0; i < listTag.size(); i++) {
			CompoundTag stackTag = listTag.getCompound(i);
			int slot = stackTag.getByte("Slot") & 255;

			if (slot >= 0 && slot < itemHandler.getContainerSize()) {
				itemHandler.setItem(slot, ItemStack.of(stackTag));
			}
		}
	}

	public void tick(Level level, BlockPos pos, BlockState state) {
		if (level.isClientSide) return;

		pullEnergyFromNeighbors();

		//? if >1.20.1 {
		/*Optional<RecipeHolder<DNAAnalyzerRecipe>> recipeOpt = getCurrentRecipe();
		 *///?} else {
		Optional<DNAAnalyzerRecipe> recipeOpt = getCurrentRecipe();
		//?}

		if (recipeOpt.isEmpty()) {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(DNAAnalyzerBlock.LIT, false));
			this.lockedOutput = ItemStack.EMPTY;
			this.lastInputSignature = "";
			return;
		}

		String currentSignature = signatureOf(itemHandler.getItem(TEST_TUBE_SLOT), itemHandler.getItem(MATERIAL_SLOT));

		if (progress == 0 && (lockedOutput.isEmpty() || !currentSignature.equals(lastInputSignature))) {
			//? if >1.20.1 {
			/*lockedOutput = determineOutput(recipeOpt.get().value()).copy();
			 *///?} else {
			lockedOutput = determineOutput(recipeOpt.get()).copy();
			//?}
			lastInputSignature = currentSignature;
		}

		if (!lockedOutput.isEmpty() && canInsertOutput(lockedOutput)) {
			if (energyStorage.getEnergyStored() < 10) return;
			energyStorage.extractEnergy(10, false);

			progress++;
			level.setBlockAndUpdate(pos, state.setValue(DNAAnalyzerBlock.LIT, true));

			if (progress >= maxProgress) {
				craftItem(lockedOutput);
				resetProgress();
				level.setBlockAndUpdate(pos, state.setValue(DNAAnalyzerBlock.LIT, false));
				this.lockedOutput = ItemStack.EMPTY;
				this.lastInputSignature = "";
			}
		} else {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(DNAAnalyzerBlock.LIT, false));
		}
	}

	private void craftItem(ItemStack output) {
		ItemStack current = itemHandler.getItem(OUTPUT_SLOT_1);
		if (current.isEmpty()) {
			itemHandler.setItem(OUTPUT_SLOT_1, output.copy());
		} else {
			current.grow(output.getCount());
		}
		itemHandler.removeItem(TEST_TUBE_SLOT, 1);
		itemHandler.removeItem(MATERIAL_SLOT, 1);
	}

	private boolean canInsertOutput(ItemStack output) {
		ItemStack stack = itemHandler.getItem(OUTPUT_SLOT_1);
		//? if >1.20.1 {
		/*return stack.isEmpty() || (ItemStack.isSameItemSameComponents(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize());
		 *///?} else {
		return stack.isEmpty() || (ItemStack.isSameItemSameTags(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize());
		//?}
	}

	//? if >1.20.1 {
	/*private Optional<RecipeHolder<DNAAnalyzerRecipe>> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.DNA_ANALYZER_RECIPE_TYPE.get(),
			new DNAAnalyzerRecipeInput(itemHandler.getItem(TEST_TUBE_SLOT), itemHandler.getItem(MATERIAL_SLOT)), level);
	}
	*///?} else {
	private Optional<DNAAnalyzerRecipe> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.DNA_ANALYZER_RECIPE_TYPE.get(),
			new DNAAnalyzerRecipeInput(itemHandler.getItem(TEST_TUBE_SLOT), itemHandler.getItem(MATERIAL_SLOT)), level);
	}
	//?}

	private ItemStack determineOutput(DNAAnalyzerRecipe recipe) {
		ItemStack material = itemHandler.getItem(MATERIAL_SLOT);
		if (material.is(ModItems.MOSQUITO_IN_AMBER.get())) {
			return pickWeightedRandomDna(recipe);
		}
		//? if >1.20.1 {
		/*return recipe.output().copy();
		 *///?} else {
		return recipe.getResultItem(level.registryAccess()).copy();
		//?}
	}

	private ItemStack pickWeightedRandomDna(DNAAnalyzerRecipe recipe) {
		var registry = level.registryAccess().registryOrThrow(Registries.ITEM);
		var tagged = registry.getTag(ModTags.Items.DNA);

		//? if >1.20.1 {
		/*ItemStack fallback = recipe.output().copy();
		 *///?} else {
		ItemStack fallback = recipe.getResultItem(level.registryAccess()).copy();
		//?}

		if (tagged.isEmpty()) return fallback;

		int totalWeight = 0;
		java.util.List<net.minecraft.world.item.Item> items = new java.util.ArrayList<>();
		java.util.List<Integer> weights = new java.util.ArrayList<>();
		for (var h : tagged.get()) {
			int w = recipe.getWeightFor(h.value());
			if (w > 0) {
				items.add(h.value());
				weights.add(w);
				totalWeight += w;
			}
		}
		if (totalWeight <= 0) return fallback;
		int roll = level.random.nextInt(totalWeight);
		int acc = 0;
		for (int i = 0; i < items.size(); i++) {
			acc += weights.get(i);
			if (roll < acc) return new ItemStack(items.get(i), Math.max(1, fallback.getCount()));
		}
		return fallback;
	}

	private String signatureOf(ItemStack s1, ItemStack s2) {
		return stackSig(s1) + "#" + stackSig(s2);
	}

	private String stackSig(ItemStack s) {
		return s.isEmpty() ? "empty" : BuiltInRegistries.ITEM.getKey(s.getItem()) + "x" + s.getCount();
	}

	private void resetProgress() {
		this.progress = 0;
		this.maxProgress = DEFAULT_MAX_PROGRESS;
	}

	private void pullEnergyFromNeighbors() {
		for (Direction dir : Direction.values()) {
			BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
			if (be instanceof ModEnergyUtil.EnergyProvider provider) {
				ModEnergyStorage source = provider.getEnergyStorage(dir.getOpposite());
				if (source != null && source.canExtract()) {
					int accepted = energyStorage.receiveEnergy(TRANSFER_RATE, true);
					if (accepted > 0) {
						energyStorage.receiveEnergy(source.extractEnergy(accepted, false), false);
					}
				}
			}
		}
	}

	//? if >1.20.1 {
	/*@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) { return saveWithoutMetadata(registries); }
	*///?} else {
	@Override
	public CompoundTag getUpdateTag() { return saveWithoutMetadata(); }
	//?}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }
}