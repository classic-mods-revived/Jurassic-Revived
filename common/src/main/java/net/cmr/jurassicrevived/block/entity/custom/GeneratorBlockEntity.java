package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.custom.GeneratorBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.screen.custom.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

//? if >1.20.1 {
/*import net.minecraft.core.HolderLookup;
 *///?}

public class GeneratorBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {

	public final SimpleContainer itemHandler = new SimpleContainer(1) {
		@Override
		public void setChanged() {
			super.setChanged();
			GeneratorBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}

		@Override
		public ItemStack removeItem(int slot, int amount) {
			if (slot == INPUT_SLOT) {
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
			return super.removeItem(slot, amount);
		}
	};

	public static final int INPUT_SLOT = 0;
	protected final ContainerData data;
	private int burnTime = 0;
	private int burnTimeTotal = 0;
	private boolean isBurning = false;

	private static final int TRANSFER_RATE = 1000;

	private final ModEnergyStorage energyStorage = createEnergyStorage();

	public GeneratorBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.GENERATOR_BE.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int i) {
				return switch (i) {
					case 0 -> GeneratorBlockEntity.this.burnTime;
					case 1 -> GeneratorBlockEntity.this.burnTimeTotal;
					default -> 0;
				};
			}

			@Override
			public void set(int i, int i1) {
				switch (i) {
					case 0 -> GeneratorBlockEntity.this.burnTime = i1;
					case 1 -> GeneratorBlockEntity.this.burnTimeTotal = i1;
				}
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	private ModEnergyStorage createEnergyStorage() {
		return new ModEnergyStorage(256000, 0, TRANSFER_RATE, 0) {
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
		return Component.translatable("block.jurassicrevived.generator");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new GeneratorMenu(i, inventory, this, this.data);
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && !this.isBurning;
	}

	public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
		if (level1.isClientSide) return;

		boolean storageFull = this.energyStorage.getEnergyStored() >= this.energyStorage.getMaxEnergyStored();

		if (!storageFull && !isBurningFuel() && hasFuelItemInSlot()) {
			startBurning();
		}

		if (isBurningFuel() && !storageFull) {
			int space = this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored();
			int toAdd = Math.min(100, Math.max(0, space));
			if (toAdd > 0) {
				this.energyStorage.setEnergy(this.energyStorage.getEnergyStored() + toAdd);
			}

			this.burnTime++;
			if (this.burnTime >= this.burnTimeTotal) {
				this.isBurning = false;
			}
		} else if (!isBurningFuel() && this.burnTime >= this.burnTimeTotal && this.burnTimeTotal > 0) {
			this.burnTime = 0;
			this.burnTimeTotal = 0;
		}

		pushEnergyToNeighbors();

		boolean shouldBeLit = isBurningFuel() && !storageFull;
		if (blockState.hasProperty(GeneratorBlock.LIT) && blockState.getValue(GeneratorBlock.LIT) != shouldBeLit) {
			level1.setBlockAndUpdate(blockPos, blockState.setValue(GeneratorBlock.LIT, shouldBeLit));
		}
	}

	private void pushEnergyToNeighbors() {
		if (this.energyStorage.getEnergyStored() <= 0) return;

		for (Direction dir : Direction.values()) {
			BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
			if (be instanceof ModEnergyUtil.EnergyProvider provider) {
				ModEnergyStorage target = provider.getEnergyStorage(dir.getOpposite());
				if (target != null && target.canReceive()) {
					int toSend = Math.min(TRANSFER_RATE, this.energyStorage.getEnergyStored());
					int accepted = target.receiveEnergy(toSend, true);
					if (accepted > 0) {
						int actuallyExtracted = this.energyStorage.extractEnergy(accepted, false);
						target.receiveEnergy(actuallyExtracted, false);
					}
				}
			}
		}
	}

	private boolean isBurningFuel() {
		return this.isBurning && this.burnTimeTotal > 0 && this.burnTime < this.burnTimeTotal;
	}

	private void startBurning() {
		ItemStack stack = this.itemHandler.getItem(INPUT_SLOT);
		//? if >1.20.1 {
		/*int burn = AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
		 *///?} else {
		int burn = AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
		//?}
		if (burn <= 0) return;

		stack.shrink(1);
		this.burnTimeTotal = burn;
		this.burnTime = 0;
		this.isBurning = true;
		setChanged();
	}

	private boolean hasFuelItemInSlot() {
		ItemStack stack = this.itemHandler.getItem(INPUT_SLOT);
		if (stack.isEmpty()) return false;
		//? if >1.20.1 {
		/*return AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0) > 0;
		 *///?} else {
		return AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0) > 0;
		//?}
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
		tag.putInt("BurnTime", this.burnTime);
		tag.putInt("BurnTotal", this.burnTimeTotal);
		tag.putBoolean("IsBurning", this.isBurning);
		tag.put("Energy", energyStorage.saveNBT());
	}

	private void loadCommonData(CompoundTag tag) {
		if (tag.contains("Energy")) {
			energyStorage.loadNBT(tag.getCompound("Energy"));
		}
		this.burnTime = tag.getInt("BurnTime");
		this.burnTimeTotal = tag.getInt("BurnTotal");
		this.isBurning = tag.getBoolean("IsBurning");
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
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