package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.screen.custom.PowerCellMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

//? if >1.20.1 {
/*import net.minecraft.core.HolderLookup;
 *///?}

public class PowerCellBlockEntity extends BlockEntity implements ExtendedMenuProvider, ModEnergyUtil.EnergyProvider {
	public final SimpleContainer itemHandler = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			PowerCellBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}

		@Override
		public int getMaxStackSize() {
			return 64;
		}
	};

	private final ModEnergyStorage energyStorage = createEnergyStorage();
	private static final int TRANSFER_RATE = 1000;

	public PowerCellBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.POWER_CELL_BE.get(), pos, blockState);
	}

	private ModEnergyStorage createEnergyStorage() {
		return new ModEnergyStorage(256000, TRANSFER_RATE) {
			@Override
			public void onEnergyChanged() {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && energyStorage.getEnergyStored() == 0;
	}

	@Override
	public ModEnergyStorage getEnergyStorage(@Nullable Direction direction) {
		return this.energyStorage;
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("Power Cell");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new PowerCellMenu(containerId, playerInventory, this);
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	public void drops() {
		Containers.dropContents(this.level, this.worldPosition, itemHandler);
	}

	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		if (level.isClientSide) return;
		pushEnergyToAboveNeighbour();
	}

	private void pushEnergyToAboveNeighbour() {
		BlockPos up = worldPosition.above();
		BlockEntity targetBE = level.getBlockEntity(up);

		if (targetBE instanceof ModEnergyUtil.EnergyProvider provider) {
			ModEnergyStorage targetStorage = provider.getEnergyStorage(Direction.DOWN);
			if (targetStorage != null && targetStorage.canReceive()) {
				int available = energyStorage.getEnergyStored();
				int toSend = Math.min(available, TRANSFER_RATE);

				int accepted = targetStorage.receiveEnergy(toSend, true);
				if (accepted > 0) {
					int extracted = energyStorage.extractEnergy(accepted, false);
					targetStorage.receiveEnergy(extracted, false);
				}
			}
		}
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.saveAdditional(pTag, pRegistries);
		pTag.put("Inventory", itemHandler.createTag(pRegistries));
		pTag.put("Energy", energyStorage.saveNBT());
	}

	@Override
	protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.loadAdditional(pTag, pRegistries);
		itemHandler.fromTag(pTag.getList("Inventory", 10), pRegistries);
		if (pTag.contains("Energy")) {
			energyStorage.loadNBT(pTag.getCompound("Energy"));
		}
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.put("Inventory", itemHandler.createTag());
		pTag.put("Energy", energyStorage.saveNBT());
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		itemHandler.fromTag(pTag.getList("Inventory", 10));
		if (pTag.contains("Energy")) {
			energyStorage.loadNBT(pTag.getCompound("Energy"));
		}
	}
	//?}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	//? if >1.20.1 {
	/*@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
		return saveWithoutMetadata(pRegistries);
	}
	*///?} else {
	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}
	//?}
}