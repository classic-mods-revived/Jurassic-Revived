package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.fluid.FluidStack;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.custom.FossilCleanerBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.recipe.FossilCleanerRecipe;
import net.cmr.jurassicrevived.recipe.FossilCleanerRecipeInput;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.custom.FossilCleanerMenu;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

public class FossilCleanerBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {

	public final SimpleContainer itemHandler = new SimpleContainer(5) {
		@Override
		public void setChanged() {
			super.setChanged();
			FossilCleanerBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};

	private static final int WATER_SLOT = 0;
	private static final int FOSSILBLOCK_SLOT = 1;
	private static final int[] OUTPUT_SLOTS = {2, 3, 4};
	private static final int WATER_CRAFT_AMOUNT = 250;
	private static final long TANK_CAPACITY = 16000;

	private FluidStack fluidStack = FluidStack.empty();
	private ItemStack lockedOutput = ItemStack.EMPTY;
	private String lastInputSignature = "";

	private final ContainerData data;
	private int progress = 0;
	private int maxProgress = 200;
	private final int DEFAULT_MAX_PROGRESS = 200;

	private static final int TRANSFER_RATE = 1000;
	private final ModEnergyStorage energyStorage = createEnergyStorage();

	public FossilCleanerBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.FOSSIL_CLEANER_BE.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return switch (pIndex) {
					case 0 -> FossilCleanerBlockEntity.this.progress;
					case 1 -> FossilCleanerBlockEntity.this.maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int pIndex, int pValue) {
				switch (pIndex) {
					case 0 -> FossilCleanerBlockEntity.this.progress = pValue;
					case 1 -> FossilCleanerBlockEntity.this.maxProgress = pValue;
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

	public FluidStack getFluid() {
		return fluidStack;
	}

	private void setFluid(FluidStack stack) {
		this.fluidStack = stack == null || stack.isEmpty() ? FluidStack.empty() : stack;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.jurassicrevived.fossil_cleaner");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new FossilCleanerMenu(i, inventory, this, this.data);
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && fluidStack.isEmpty() && progress == 0;
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("Inventory", itemHandler.createTag(registries));
		tag.putInt("Prog", this.progress);
		tag.putInt("MaxProg", this.maxProgress);
		tag.put("Energy", energyStorage.saveNBT());
		CompoundTag fluidTag = new CompoundTag();
		fluidStack.write(registries, fluidTag);
		tag.put("Fluid", fluidTag);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		itemHandler.fromTag(tag.getList("Inventory", 10), registries);
		progress = tag.getInt("Prog");
		maxProgress = tag.getInt("MaxProg");
		if (tag.contains("Energy")) energyStorage.loadNBT(tag.getCompound("Energy"));
		if (tag.contains("Fluid")) fluidStack = FluidStack.read(registries, tag.getCompound("Fluid")).orElse(FluidStack.empty());
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Inventory", itemHandler.createTag());
		tag.putInt("Prog", this.progress);
		tag.putInt("MaxProg", this.maxProgress);
		tag.put("Energy", energyStorage.saveNBT());
		CompoundTag fluidTag = new CompoundTag();
		fluidStack.write(fluidTag);
		tag.put("Fluid", fluidTag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		itemHandler.fromTag(tag.getList("Inventory", 10));
		progress = tag.getInt("Prog");
		maxProgress = tag.getInt("MaxProg");
		if (tag.contains("Energy")) energyStorage.loadNBT(tag.getCompound("Energy"));
		if (tag.contains("Fluid")) fluidStack = FluidStack.read(tag.getCompound("Fluid"));
	}
	//?}

	public void tick(Level level, BlockPos pos, BlockState state) {
		if (level.isClientSide) return;

		pullEnergyFromNeighbors();
		handleBucketInput();

		//? if >1.20.1 {
		/*Optional<RecipeHolder<FossilCleanerRecipe>> recipeOpt = getCurrentRecipe();
		 *///?} else {
		Optional<FossilCleanerRecipe> recipeOpt = getCurrentRecipe();
		//?}

		if (recipeOpt.isEmpty()) {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(FossilCleanerBlock.LIT, false));
			return;
		}

		String currentSignature = stackSig(itemHandler.getItem(FOSSILBLOCK_SLOT));
		if (progress == 0 && (lockedOutput.isEmpty() || !currentSignature.equals(lastInputSignature))) {
			//? if >1.20.1 {
			/*lockedOutput = determineOutput(recipeOpt.get().value()).copy();
			 *///?} else {
			lockedOutput = determineOutput(recipeOpt.get()).copy();
			//?}
			lastInputSignature = currentSignature;
		}

		if (!lockedOutput.isEmpty() && canInsertOutput(lockedOutput) && fluidStack.getAmount() >= WATER_CRAFT_AMOUNT) {
			if (energyStorage.getEnergyStored() < 10) return;
			energyStorage.extractEnergy(10, false);

			progress++;
			level.setBlockAndUpdate(pos, state.setValue(FossilCleanerBlock.LIT, true));

			if (progress >= maxProgress) {
				craftItem(lockedOutput);
				fluidStack.setAmount(fluidStack.getAmount() - WATER_CRAFT_AMOUNT);
				resetProgress();
				level.setBlockAndUpdate(pos, state.setValue(FossilCleanerBlock.LIT, false));
			}
		} else {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(FossilCleanerBlock.LIT, false));
		}
	}

	private void handleBucketInput() {
		ItemStack stack = itemHandler.getItem(WATER_SLOT);
		if (stack.is(Items.WATER_BUCKET) && (TANK_CAPACITY - fluidStack.getAmount() >= 1000)) {
			if (fluidStack.isEmpty() || fluidStack.getFluid().is(FluidTags.WATER)) {
				fluidStack = FluidStack.create(net.minecraft.world.level.material.Fluids.WATER, fluidStack.getAmount() + 1000);
				itemHandler.setItem(WATER_SLOT, new ItemStack(Items.BUCKET));
				setChanged();
			}
		}
	}

	private void craftItem(ItemStack output) {
		for (int slot : OUTPUT_SLOTS) {
			ItemStack stack = itemHandler.getItem(slot);
			if (stack.isEmpty()) {
				itemHandler.setItem(slot, output.copy());
				itemHandler.removeItem(FOSSILBLOCK_SLOT, 1);
				return;
			} else if (isSameItem(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize()) {
				stack.grow(output.getCount());
				itemHandler.removeItem(FOSSILBLOCK_SLOT, 1);
				return;
			}
		}
	}

	private boolean canInsertOutput(ItemStack output) {
		for (int slot : OUTPUT_SLOTS) {
			ItemStack stack = itemHandler.getItem(slot);
			if (stack.isEmpty() || (isSameItem(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize())) return true;
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
	/*private Optional<RecipeHolder<FossilCleanerRecipe>> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.FOSSIL_CLEANER_RECIPE_TYPE.get(), new FossilCleanerRecipeInput(itemHandler.getItem(FOSSILBLOCK_SLOT), itemHandler.getItem(WATER_SLOT)), level);
	}
	*///?} else {
	private Optional<FossilCleanerRecipe> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.FOSSIL_CLEANER_RECIPE_TYPE.get(), new FossilCleanerRecipeInput(itemHandler.getItem(FOSSILBLOCK_SLOT), itemHandler.getItem(WATER_SLOT)), level);
	}
	//?}

	private ItemStack determineOutput(FossilCleanerRecipe recipe) {
		var registry = level.registryAccess().registryOrThrow(Registries.ITEM);
		var tagged = registry.getTag(ModTags.Items.FOSSILS);

		//? if >1.20.1 {
		/*ItemStack fallback = recipe.output().copy();
		 *///?} else {
		ItemStack fallback = recipe.getResultItem(level.registryAccess()).copy();
		//?}

		if (tagged.isEmpty()) return fallback;

		int total = 0;
		java.util.List<net.minecraft.world.item.Item> items = new java.util.ArrayList<>();
		java.util.List<Integer> weights = new java.util.ArrayList<>();
		for (var h : tagged.get()) {
			int w = recipe.getWeightFor(h.value());
			if (w > 0) {
				items.add(h.value());
				weights.add(w);
				total += w;
			}
		}
		if (total <= 0) return fallback;
		int roll = level.random.nextInt(total);
		int acc = 0;
		for (int i = 0; i < items.size(); i++) {
			acc += weights.get(i);
			if (roll < acc) return new ItemStack(items.get(i), Math.max(1, fallback.getCount()));
		}
		return fallback;
	}

	private String stackSig(ItemStack s) {
		return s.isEmpty() ? "empty" : BuiltInRegistries.ITEM.getKey(s.getItem()).toString() + ":" + s.getCount();
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