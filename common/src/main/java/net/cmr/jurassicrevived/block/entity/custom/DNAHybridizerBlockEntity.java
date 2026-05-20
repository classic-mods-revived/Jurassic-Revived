package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.custom.DNAHybridizerBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipe;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipeInput;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.custom.DNAHybridizerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.item.crafting.Ingredient;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DNAHybridizerBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {

	public final SimpleContainer itemHandler = new SimpleContainer(11) {
		@Override
		public void setChanged() {
			super.setChanged();
			DNAHybridizerBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};

	private static final int[] DNA_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7};
	private static final int CATALYST_SLOT = 8;
	private static final int OUTPUT_SLOT = 9;

	private ItemStack lockedOutput = ItemStack.EMPTY;
	private String lastInputSignature = "";

	private final ContainerData data;
	private int progress = 0;
	private int maxProgress = 3000;
	private final int DEFAULT_MAX_PROGRESS = 3000;

	private static final int TRANSFER_RATE = 1000;
	private final ModEnergyStorage energyStorage = createEnergyStorage();

	public DNAHybridizerBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.DNA_HYBRIDIZER_BE.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return switch (pIndex) {
					case 0 -> DNAHybridizerBlockEntity.this.progress;
					case 1 -> DNAHybridizerBlockEntity.this.maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int pIndex, int pValue) {
				switch (pIndex) {
					case 0 -> DNAHybridizerBlockEntity.this.progress = pValue;
					case 1 -> DNAHybridizerBlockEntity.this.maxProgress = pValue;
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
		return Component.translatable("block.jurassicrevived.dna_hybridizer");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new DNAHybridizerMenu(i, inventory, this, this.data);
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && this.progress == 0;
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("Inventory", itemHandler.createTag(registries));
		saveCommonData(tag);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		itemHandler.fromTag(tag.getList("Inventory", 10), registries);
		loadCommonData(tag);
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Inventory", saveInventory());
		saveCommonData(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		loadInventory(tag.getList("Inventory", 10));
		loadCommonData(tag);
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

	private void saveCommonData(CompoundTag tag) {
		tag.putInt("Prog", this.progress);
		tag.putInt("MaxProg", this.maxProgress);
		tag.put("Energy", energyStorage.saveNBT());
	}

	private void loadCommonData(CompoundTag tag) {
		progress = tag.getInt("Prog");
		maxProgress = tag.getInt("MaxProg");
		if (tag.contains("Energy")) {
			energyStorage.loadNBT(tag.getCompound("Energy"));
		}
	}

	public void tick(Level level, BlockPos pos, BlockState state) {
		if (level.isClientSide) return;

		pullEnergyFromNeighbors();

		//? if >1.20.1 {
		/*Optional<RecipeHolder<DNAHybridizerRecipe>> recipeOpt = getCurrentRecipe();
		 *///?} else {
		Optional<DNAHybridizerRecipe> recipeOpt = getCurrentRecipe();
		//?}

		if (recipeOpt.isEmpty()) {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(DNAHybridizerBlock.LIT, false));
			this.lockedOutput = ItemStack.EMPTY;
			this.lastInputSignature = "";
			return;
		}

		String currentSignature = buildSignature();

		//? if >1.20.1 {
		/*DNAHybridizerRecipe recipe = recipeOpt.get().value();
		 *///?} else {
		DNAHybridizerRecipe recipe = recipeOpt.get();
		//?}

		if (progress == 0 && (lockedOutput.isEmpty() || !currentSignature.equals(lastInputSignature))) {
			//? if >1.20.1 {
			/*lockedOutput = recipe.output().copy();
			 *///?} else {
			lockedOutput = recipe.getResultItem(level.registryAccess()).copy();
			//?}
			lastInputSignature = currentSignature;
		}

		List<Integer> exactMatch = findExactUnorderedMatchIndices(recipe);
		boolean canProceed = exactMatch != null && !lockedOutput.isEmpty() && canInsertOutput(lockedOutput);

		if (canProceed) {
			if (energyStorage.getEnergyStored() < 10) return;
			energyStorage.extractEnergy(10, false);

			progress++;
			level.setBlockAndUpdate(pos, state.setValue(DNAHybridizerBlock.LIT, true));

			if (progress >= maxProgress) {
				craftItem(lockedOutput, exactMatch);
				resetProgress();
				level.setBlockAndUpdate(pos, state.setValue(DNAHybridizerBlock.LIT, false));
				this.lockedOutput = ItemStack.EMPTY;
				this.lastInputSignature = "";
			}
		} else {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(DNAHybridizerBlock.LIT, false));
		}
	}

	private void craftItem(ItemStack output, List<Integer> matchedIndices) {
		ItemStack current = itemHandler.getItem(OUTPUT_SLOT);
		if (current.isEmpty()) {
			itemHandler.setItem(OUTPUT_SLOT, output.copy());
		} else {
			current.grow(output.getCount());
		}
		for (int idx : matchedIndices) {
			itemHandler.removeItem(idx, 1);
		}
	}

	private boolean canInsertOutput(ItemStack output) {
		ItemStack stack = itemHandler.getItem(OUTPUT_SLOT);
		//? if >1.20.1 {
		/*return stack.isEmpty() || (ItemStack.isSameItemSameComponents(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize());
		 *///?} else {
		return stack.isEmpty() || (ItemStack.isSameItemSameTags(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize());
		//?}
	}

	private @Nullable List<Integer> findExactUnorderedMatchIndices(DNAHybridizerRecipe recipe) {
		var inputs = recipe.getIngredients();
		boolean hasCatalyst = inputs.size() >= 9 && !inputs.get(8).isEmpty();
		ItemStack catStack = itemHandler.getItem(CATALYST_SLOT);

		if (hasCatalyst) {
			if (catStack.isEmpty() || !inputs.get(8).test(catStack)) return null;
		} else if (!catStack.isEmpty()) return null;

		List<Ingredient> required = new ArrayList<>();
		for (int i = 0; i < Math.min(8, inputs.size()); i++) {
			if (!inputs.get(i).isEmpty()) required.add(inputs.get(i));
		}
		if (required.isEmpty()) return null;

		boolean[] used = new boolean[8];
		List<Integer> matched = new ArrayList<>();

		for (var need : required) {
			boolean found = false;
			for (int i = 0; i < 8; i++) {
				if (used[i]) continue;
				ItemStack stack = itemHandler.getItem(i);
				if (!stack.isEmpty() && need.test(stack)) {
					used[i] = true;
					matched.add(i);
					found = true;
					break;
				}
			}
			if (!found) return null;
		}

		for (int i = 0; i < 8; i++) {
			if (!used[i] && !itemHandler.getItem(i).isEmpty()) return null;
		}

		if (hasCatalyst) matched.add(CATALYST_SLOT);
		return matched;
	}

	//? if >1.20.1 {
	/*private Optional<RecipeHolder<DNAHybridizerRecipe>> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.DNA_HYBRIDIZER_RECIPE_TYPE.get(),
			new DNAHybridizerRecipeInput(
				itemHandler.getItem(0), itemHandler.getItem(1), itemHandler.getItem(2),
				itemHandler.getItem(3), itemHandler.getItem(4), itemHandler.getItem(5),
				itemHandler.getItem(6), itemHandler.getItem(7), itemHandler.getItem(8)
			), level);
	}
	*///?} else {
	private Optional<DNAHybridizerRecipe> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.DNA_HYBRIDIZER_RECIPE_TYPE.get(),
			new DNAHybridizerRecipeInput(
				itemHandler.getItem(0), itemHandler.getItem(1), itemHandler.getItem(2),
				itemHandler.getItem(3), itemHandler.getItem(4), itemHandler.getItem(5),
				itemHandler.getItem(6), itemHandler.getItem(7), itemHandler.getItem(8)
			), level);
	}
	//?}

	private String buildSignature() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			ItemStack s = itemHandler.getItem(i);
			sb.append(s.isEmpty() ? "e" : BuiltInRegistries.ITEM.getKey(s.getItem()) + "x" + s.getCount()).append("#");
		}
		return sb.toString();
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