package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.custom.IncubatorBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.recipe.IncubatorRecipe;
import net.cmr.jurassicrevived.recipe.IncubatorRecipeInput;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.custom.IncubatorMenu;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

//? if >1.20.1 {
/*import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.RecipeHolder;
*///?} else {
import net.minecraft.core.RegistryAccess;
//?}

import java.util.Optional;

public class IncubatorBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {

	private boolean allowInternalExtraction = false;

	public final SimpleContainer itemHandler = new SimpleContainer(3) {
		@Override
		public void setChanged() {
			super.setChanged();
			IncubatorBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}

		@Override
		public boolean canPlaceItem(int slot, ItemStack stack) {
			return stack.is(net.cmr.jurassicrevived.util.ModTags.Items.EGGS);
		}

		@Override
		public ItemStack removeItem(int slot, int amount) {
			ItemStack stack = getItem(slot);
			if (!stack.isEmpty()) {
				boolean isIncubated = stack.getItem() instanceof net.minecraft.world.item.BlockItem blockItem
				                      && blockItem.getBlock().defaultBlockState().is(net.cmr.jurassicrevived.util.ModTags.Blocks.INCUBATED_EGGS);

				if (!isIncubated && !allowInternalExtraction) {
					boolean isPlayer = false;
					for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
						String className = element.getClassName();
						if (className.contains("inventory") || className.contains("player") || className.contains("ServerGamePacketListenerImpl")) {
							isPlayer = true;
							break;
						}
					}
					if (!isPlayer) return ItemStack.EMPTY;
				}
			}
			return super.removeItem(slot, amount);
		}
	};

	private final ContainerData data;
	private final int[] progress = new int[]{0, 0, 0};
	private final int[] maxProgress = new int[]{4800, 4800, 4800};
	private final int DEFAULT_MAX_PROGRESS = 4800;

	private static final int TRANSFER_RATE = 1000;

	private final ModEnergyStorage energyStorage = createEnergyStorage();

	public IncubatorBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.INCUBATOR_BE.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int pIndex) {
				return switch (pIndex) {
					case 0 -> progress[0];
					case 1 -> maxProgress[0];
					case 2 -> progress[1];
					case 3 -> maxProgress[1];
					case 4 -> progress[2];
					case 5 -> maxProgress[2];
					default -> 0;
				};
			}

			@Override
			public void set(int pIndex, int pValue) {
				switch (pIndex) {
					case 0 -> progress[0] = pValue;
					case 1 -> maxProgress[0] = pValue;
					case 2 -> progress[1] = pValue;
					case 3 -> maxProgress[1] = pValue;
					case 4 -> progress[2] = pValue;
					case 5 -> maxProgress[2] = pValue;
				}
			}

			@Override
			public int getCount() {
				return 6;
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

			@Override
			public boolean canExtract() {
				return false;
			}

			@Override
			public int extractEnergy(int maxExtract, boolean simulate) {
				return 0;
			}
		};
	}

	@Override
	public ModEnergyStorage getEnergyStorage(@Nullable Direction direction) {
		return this.energyStorage;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.jurassicrevived.incubator");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && progress[0] == 0 && progress[1] == 0 && progress[2] == 0;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new IncubatorMenu(i, inventory, this, this.data);
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("Inventory", saveInventory(registries));
		tag.putInt("Prog0", this.progress[0]);
		tag.putInt("Prog1", this.progress[1]);
		tag.putInt("Prog2", this.progress[2]);
		tag.putInt("Max0", this.maxProgress[0]);
		tag.putInt("Max1", this.maxProgress[1]);
		tag.putInt("Max2", this.maxProgress[2]);
		tag.put("Energy", energyStorage.saveNBT());
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		loadInventory(tag.getList("Inventory", 10), registries);
		if (tag.contains("Energy")) {
			energyStorage.loadNBT(tag.getCompound("Energy"));
		}
		progress[0] = tag.getInt("Prog0");
		progress[1] = tag.getInt("Prog1");
		progress[2] = tag.getInt("Prog2");
		maxProgress[0] = tag.getInt("Max0");
		maxProgress[1] = tag.getInt("Max1");
		maxProgress[2] = tag.getInt("Max2");
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
		tag.putInt("Prog0", this.progress[0]);
		tag.putInt("Prog1", this.progress[1]);
		tag.putInt("Prog2", this.progress[2]);
		tag.putInt("Max0", this.maxProgress[0]);
		tag.putInt("Max1", this.maxProgress[1]);
		tag.putInt("Max2", this.maxProgress[2]);
		tag.put("Energy", energyStorage.saveNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		loadInventory(tag.getList("Inventory", 10));
		if (tag.contains("Energy")) {
			energyStorage.loadNBT(tag.getCompound("Energy"));
		}
		progress[0] = tag.getInt("Prog0");
		progress[1] = tag.getInt("Prog1");
		progress[2] = tag.getInt("Prog2");
		maxProgress[0] = tag.getInt("Max0");
		maxProgress[1] = tag.getInt("Max1");
		maxProgress[2] = tag.getInt("Max2");
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

	public void tick(Level level, BlockPos pos, BlockState state) {
		if (level.isClientSide) return;

		pullEnergyFromNeighbors();
		pushOutputsToHoppers();

		boolean changed = false;
		boolean anyActive = false;

		for (int s = 0; s < 3; s++) {
			ItemStack stack = itemHandler.getItem(s);
			if (stack.isEmpty()) {
				if (progress[s] != 0) { progress[s] = 0; changed = true; }
				continue;
			}

			//? if >1.20.1 {
			/*Optional<RecipeHolder<IncubatorRecipe>> recipeOpt = getRecipeFor(stack);
			 *///?} else {
			Optional<IncubatorRecipe> recipeOpt = getRecipeFor(stack);
			//?}
			if (recipeOpt.isEmpty()) {
				if (progress[s] != 0) { progress[s] = 0; changed = true; }
				continue;
			}
			anyActive = true;
		}

		if (state.getValue(IncubatorBlock.LIT) != anyActive) {
			level.setBlockAndUpdate(pos, state.setValue(IncubatorBlock.LIT, anyActive));
		}

		if (anyActive) {
			if (JRConfigManager.get().requirePower) {
				if (energyStorage.getEnergyStored() < 10) return;
				energyStorage.extractEnergy(10, false);
			}
		}

		for (int s = 0; s < 3; s++) {
			ItemStack stack = itemHandler.getItem(s);
			if (stack.isEmpty()) continue;

			//? if >1.20.1 {
			/*Optional<RecipeHolder<IncubatorRecipe>> recipeOpt = getRecipeFor(stack);
			 *///?} else {
			Optional<IncubatorRecipe> recipeOpt = getRecipeFor(stack);
			//?}
			if (recipeOpt.isEmpty()) continue;

			if (progress[s] < maxProgress[s]) {
				progress[s]++;
				changed = true;
			}

			if (progress[s] >= maxProgress[s]) {
				//? if >1.20.1 {
				/*ItemStack out = recipeOpt.get().value().assemble(new IncubatorRecipeInput(stack), level.registryAccess());
				 *///?} else {
				ItemStack out = recipeOpt.get().assemble(new IncubatorRecipeInput(stack), level.registryAccess());
				//?}
				if (!out.isEmpty()) {
					allowInternalExtraction = true;
					try {
						itemHandler.setItem(s, out.copy());
					} finally {
						allowInternalExtraction = false;
					}
					progress[s] = 0;
					maxProgress[s] = DEFAULT_MAX_PROGRESS;
					changed = true;
				}
			}
		}

		if (changed) setChanged();
	}

	//? if >1.20.1 {
	/*private Optional<RecipeHolder<IncubatorRecipe>> getRecipeFor(ItemStack input) {
		return level.getRecipeManager().getRecipeFor(ModRecipes.INCUBATOR_RECIPE_TYPE.get(), new IncubatorRecipeInput(input), level);
	}
	*///?} else {
	private Optional<IncubatorRecipe> getRecipeFor(ItemStack input) {
		return level.getRecipeManager().getRecipeFor(ModRecipes.INCUBATOR_RECIPE_TYPE.get(), new IncubatorRecipeInput(input), level);
	}
	//?}

	private void pullEnergyFromNeighbors() {
		for (Direction dir : Direction.values()) {
			BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
			if (be instanceof ModEnergyUtil.EnergyProvider provider) {
				ModEnergyStorage source = provider.getEnergyStorage(dir.getOpposite());
				if (source != null && source.canExtract()) {
					int canAccept = energyStorage.receiveEnergy(TRANSFER_RATE, true);
					if (canAccept > 0) {
						int extracted = source.extractEnergy(canAccept, false);
						energyStorage.receiveEnergy(extracted, false);
					}
				}
			}
		}
	}

	private void pushOutputsToHoppers() {
		for (int slot = 0; slot < itemHandler.getContainerSize(); slot++) {
			pushSlotToHoppers(slot);
		}
	}

	private void pushSlotToHoppers(int slot) {
		ItemStack stack = itemHandler.getItem(slot);
		if (!isIncubatedEgg(stack)) return;

		for (Direction dir : Direction.values()) {
			BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
			if (!(be instanceof Container target)) continue;

			ItemStack toMove = stack.copy();
			ItemStack remainder = HopperBlockEntity.addItem(itemHandler, target, toMove, dir);

			if (remainder.getCount() != stack.getCount()) {
				itemHandler.setItem(slot, remainder);
				setChanged();
				return;
			}
		}
	}

	private boolean isIncubatedEgg(ItemStack stack) {
		return stack.getItem() instanceof BlockItem blockItem
		       && blockItem.getBlock().defaultBlockState().is(ModTags.Blocks.INCUBATED_EGGS);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
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
}