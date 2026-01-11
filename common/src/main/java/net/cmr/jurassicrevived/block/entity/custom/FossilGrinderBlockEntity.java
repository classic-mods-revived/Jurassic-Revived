package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.custom.FossilGrinderBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.recipe.FossilGrinderRecipe;
import net.cmr.jurassicrevived.recipe.FossilGrinderRecipeInput;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.custom.FossilGrinderMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
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

public class FossilGrinderBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {

	public final SimpleContainer itemHandler = new SimpleContainer(4) {
		@Override
		public void setChanged() {
			super.setChanged();
			FossilGrinderBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};

	private static final int FOSSIL_SLOT = 0;
	private static final int[] OUTPUT_SLOTS = {1, 2, 3};

	private ItemStack lockedOutput = ItemStack.EMPTY;
	private String lastInputSignature = "";

	private final ContainerData data;
	private int progress = 0;
	private int maxProgress = 100;
	private final int DEFAULT_MAX_PROGRESS = 100;

	private static final int TRANSFER_RATE = 1000;
	private final ModEnergyStorage energyStorage = createEnergyStorage();

	public FossilGrinderBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.FOSSIL_GRINDER_BE.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return switch (pIndex) {
					case 0 -> FossilGrinderBlockEntity.this.progress;
					case 1 -> FossilGrinderBlockEntity.this.maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int pIndex, int pValue) {
				switch (pIndex) {
					case 0 -> FossilGrinderBlockEntity.this.progress = pValue;
					case 1 -> FossilGrinderBlockEntity.this.maxProgress = pValue;
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
		return Component.translatable("block.jurassicrevived.fossil_grinder");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new FossilGrinderMenu(i, inventory, this, this.data);
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
		tag.put("Inventory", itemHandler.createTag());
		saveCommonData(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		itemHandler.fromTag(tag.getList("Inventory", 10));
		loadCommonData(tag);
	}
	//?}

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
		/*Optional<RecipeHolder<FossilGrinderRecipe>> recipeOpt = getCurrentRecipe();
		 *///?} else {
		Optional<FossilGrinderRecipe> recipeOpt = getCurrentRecipe();
		//?}

		if (recipeOpt.isEmpty()) {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(FossilGrinderBlock.LIT, false));
			this.lockedOutput = ItemStack.EMPTY;
			this.lastInputSignature = "";
			return;
		}

		String currentSignature = stackSig(itemHandler.getItem(FOSSIL_SLOT));

		//? if >1.20.1 {
		/*FossilGrinderRecipe recipe = recipeOpt.get().value();
		 *///?} else {
		FossilGrinderRecipe recipe = recipeOpt.get();
		//?}

		if (progress == 0) {
			if (lockedOutput.isEmpty() || !currentSignature.equals(lastInputSignature)) {
				lockedOutput = determineOutputForCurrentInputs(recipe).copy();
				lastInputSignature = currentSignature;
			}
		}

		ItemStack output = lockedOutput;
		boolean canOutput = !output.isEmpty() && canInsertOutput(output);

		if (canOutput) {
			if (energyStorage.getEnergyStored() < 10) return;
			energyStorage.extractEnergy(10, false);

			progress++;
			level.setBlockAndUpdate(pos, state.setValue(FossilGrinderBlock.LIT, true));

			if (progress >= maxProgress) {
				craftItem(output);
				resetProgress();
				level.setBlockAndUpdate(pos, state.setValue(FossilGrinderBlock.LIT, false));
				this.lockedOutput = ItemStack.EMPTY;
				this.lastInputSignature = "";
			}
		} else {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(FossilGrinderBlock.LIT, false));
		}
	}

	private void craftItem(ItemStack output) {
		for (int slot : OUTPUT_SLOTS) {
			ItemStack stack = itemHandler.getItem(slot);
			if (stack.isEmpty()) {
				itemHandler.setItem(slot, output.copy());
				itemHandler.removeItem(FOSSIL_SLOT, 1);
				return;
			} else if (isSameItem(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize()) {
				stack.grow(output.getCount());
				itemHandler.removeItem(FOSSIL_SLOT, 1);
				return;
			}
		}
	}

	private boolean canInsertOutput(ItemStack output) {
		for (int slot : OUTPUT_SLOTS) {
			ItemStack stack = itemHandler.getItem(slot);
			if (stack.isEmpty() || (isSameItem(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize())) {
				return true;
			}
		}
		return false;
	}

	private boolean isSameItem(ItemStack stack, ItemStack other) {
		//? if >1.20.1 {
		/*return ItemStack.isSameItemSameComponents(stack, other);
		 *///?} else {
		return ItemStack.isSameItemSameTags(stack, other);
		//?}
	}

	//? if >1.20.1 {
	/*private Optional<RecipeHolder<FossilGrinderRecipe>> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.FOSSIL_GRINDER_RECIPE_TYPE.get(),
			new FossilGrinderRecipeInput(itemHandler.getItem(FOSSIL_SLOT)), level);
	}
	*///?} else {
	private Optional<FossilGrinderRecipe> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.FOSSIL_GRINDER_RECIPE_TYPE.get(),
			new FossilGrinderRecipeInput(itemHandler.getItem(FOSSIL_SLOT)), level);
	}
	//?}

	private ItemStack determineOutputForCurrentInputs(FossilGrinderRecipe recipe) {
		if (!recipe.weights().isEmpty()) {
			return pickWeighted(recipe);
		}
		//? if >1.20.1 {
		/*return recipe.output().copy();
		 *///?} else {
		return recipe.getResultItem(level.registryAccess()).copy();
		//?}
	}

	private ItemStack pickWeighted(FossilGrinderRecipe recipe) {
		java.util.Map<ResourceLocation, Integer> map = recipe.weights();
		int total = map.values().stream().mapToInt(Integer::intValue).sum();
		if (total <= 0) return ItemStack.EMPTY;

		int roll = level.random.nextInt(total);
		int acc = 0;
		for (var entry : map.entrySet()) {
			acc += entry.getValue();
			if (roll < acc) {
				var item = BuiltInRegistries.ITEM.get(entry.getKey());
				//? if >1.20.1 {
				/*int count = recipe.output().getCount();
				 *///?} else {
				int count = recipe.getResultItem(level.registryAccess()).getCount();
				//?}
				return item != null ? new ItemStack(item, Math.max(1, count)) : ItemStack.EMPTY;
			}
		}
		return ItemStack.EMPTY;
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
						int extracted = source.extractEnergy(accepted, false);
						energyStorage.receiveEnergy(extracted, false);
					}
				}
			}
		}
	}

	//? if >1.20.1 {
	/*@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithoutMetadata(registries);
	}
	*///?} else {
	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}
	//?}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
}