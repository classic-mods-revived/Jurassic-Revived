package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.custom.EmbryoCalcificationMachineBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.recipe.EmbryoCalcificationMachineRecipe;
import net.cmr.jurassicrevived.recipe.EmbryoCalcificationMachineRecipeInput;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.custom.EmbryoCalcificationMachineMenu;
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

public class EmbryoCalcificationMachineBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {

	public final SimpleContainer itemHandler = new SimpleContainer(5) {
		@Override
		public void setChanged() {
			super.setChanged();
			EmbryoCalcificationMachineBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};

	private static final int SYRINGE_SLOT = 0;
	private static final int EGG_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;

	private ItemStack lockedOutput = ItemStack.EMPTY;
	private String lastInputSignature = "";

	private final ContainerData data;
	private int progress = 0;
	private int maxProgress = 100;
	private final int DEFAULT_MAX_PROGRESS = 100;

	private static final int TRANSFER_RATE = 1000;
	private final ModEnergyStorage energyStorage = createEnergyStorage();

	public EmbryoCalcificationMachineBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.EMBRYO_CALCIFICATION_MACHINE_BE.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return switch (pIndex) {
					case 0 -> EmbryoCalcificationMachineBlockEntity.this.progress;
					case 1 -> EmbryoCalcificationMachineBlockEntity.this.maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int pIndex, int pValue) {
				switch (pIndex) {
					case 0 -> EmbryoCalcificationMachineBlockEntity.this.progress = pValue;
					case 1 -> EmbryoCalcificationMachineBlockEntity.this.maxProgress = pValue;
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
		return Component.translatable("block.jurassicrevived.embryo_calcification_machine");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new EmbryoCalcificationMachineMenu(i, inventory, this, this.data);
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && this.progress == 0;
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("Inventory", saveInventory(registries));
		saveCommonData(tag);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		loadInventory(tag.getList("Inventory", 10), registries);
		loadCommonData(tag);
	}

	private ListTag saveInventory(HolderLookup.Provider registries) {
		ListTag listTag = new ListTag();

		for (int slot = 0; slot < itemHandler.getContainerSize(); slot++) {
			ItemStack stack = itemHandler.getItem(slot);
			if (!stack.isEmpty()) {
				CompoundTag stackTag = new CompoundTag();
				stackTag.putByte("Slot", (byte) slot);
				listTag.add(stack.save(registries, stackTag));
			}
		}

		return listTag;
	}

	private void loadInventory(ListTag listTag, HolderLookup.Provider registries) {
		itemHandler.clearContent();

		for (int i = 0; i < listTag.size(); i++) {
			CompoundTag stackTag = listTag.getCompound(i);
			int slot = stackTag.getByte("Slot") & 255;

			if (slot >= 0 && slot < itemHandler.getContainerSize()) {
				itemHandler.setItem(slot, ItemStack.parseOptional(registries, stackTag));
			}
		}
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
		/*Optional<RecipeHolder<EmbryoCalcificationMachineRecipe>> recipeOpt = getCurrentRecipe();
		 *///?} else {
		Optional<EmbryoCalcificationMachineRecipe> recipeOpt = getCurrentRecipe();
		//?}

		if (recipeOpt.isEmpty()) {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(EmbryoCalcificationMachineBlock.LIT, false));
			this.lockedOutput = ItemStack.EMPTY;
			this.lastInputSignature = "";
			return;
		}

		String currentSignature = signatureOf(itemHandler.getItem(SYRINGE_SLOT), itemHandler.getItem(EGG_SLOT));

		if (progress == 0 && (lockedOutput.isEmpty() || !currentSignature.equals(lastInputSignature))) {
			//? if >1.20.1 {
			/*lockedOutput = recipeOpt.get().value().assemble(new EmbryoCalcificationMachineRecipeInput(itemHandler.getItem(SYRINGE_SLOT), itemHandler.getItem(EGG_SLOT)), level.registryAccess()).copy();
			 *///?} else {
			lockedOutput = recipeOpt.get().assemble(new EmbryoCalcificationMachineRecipeInput(itemHandler.getItem(SYRINGE_SLOT), itemHandler.getItem(EGG_SLOT)), level.registryAccess()).copy();
			//?}
			lastInputSignature = currentSignature;
		}

		if (!lockedOutput.isEmpty() && canInsertOutput(lockedOutput)) {
			if (JRConfigManager.get().requirePower) {
				if (energyStorage.getEnergyStored() < 10) return;
				energyStorage.extractEnergy(10, false);
			}

			progress++;
			level.setBlockAndUpdate(pos, state.setValue(EmbryoCalcificationMachineBlock.LIT, true));

			if (progress >= maxProgress) {
				craftItem(lockedOutput);
				resetProgress();
				level.setBlockAndUpdate(pos, state.setValue(EmbryoCalcificationMachineBlock.LIT, false));
				this.lockedOutput = ItemStack.EMPTY;
				this.lastInputSignature = "";
			}
		} else {
			resetProgress();
			level.setBlockAndUpdate(pos, state.setValue(EmbryoCalcificationMachineBlock.LIT, false));
		}
	}

	private void craftItem(ItemStack output) {
		ItemStack current = itemHandler.getItem(OUTPUT_SLOT);
		if (current.isEmpty()) {
			itemHandler.setItem(OUTPUT_SLOT, output.copy());
		} else {
			current.grow(output.getCount());
		}
		itemHandler.removeItem(SYRINGE_SLOT, 1);
		itemHandler.removeItem(EGG_SLOT, 1);
	}

	private boolean canInsertOutput(ItemStack output) {
		ItemStack stack = itemHandler.getItem(OUTPUT_SLOT);
		//? if >1.20.1 {
		/*return stack.isEmpty() || (ItemStack.isSameItemSameComponents(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize());
		 *///?} else {
		return stack.isEmpty() || (ItemStack.isSameItemSameTags(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize());
		//?}
	}

	//? if >1.20.1 {
	/*private Optional<RecipeHolder<EmbryoCalcificationMachineRecipe>> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.EMBRYO_CALCIFICATION_MACHINE_RECIPE_TYPE.get(),
			new EmbryoCalcificationMachineRecipeInput(itemHandler.getItem(SYRINGE_SLOT), itemHandler.getItem(EGG_SLOT)), level);
	}
	*///?} else {
	private Optional<EmbryoCalcificationMachineRecipe> getCurrentRecipe() {
		return level.getRecipeManager().getRecipeFor(ModRecipes.EMBRYO_CALCIFICATION_MACHINE_RECIPE_TYPE.get(),
			new EmbryoCalcificationMachineRecipeInput(itemHandler.getItem(SYRINGE_SLOT), itemHandler.getItem(EGG_SLOT)), level);
	}
	//?}

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