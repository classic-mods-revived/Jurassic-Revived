package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.fluid.FluidStack;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.screen.custom.TankMenu;
import net.minecraft.core.BlockPos;
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

public class TankBlockEntity extends BlockEntity implements ExtendedMenuProvider {
	public final SimpleContainer itemHandler = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			TankBlockEntity.this.setChanged();
			if (level != null && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}

		@Override
		public int getMaxStackSize() {
			return 64;
		}
	};


	private FluidStack fluidStack = FluidStack.empty();
	private static final long CAPACITY = 64000;

	public TankBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.TANK_BE.get(), pos, blockState);
	}

	public FluidStack getFluid() {
		return fluidStack;
	}

	public void setFluid(FluidStack stack) {
		this.fluidStack = stack;
		setChanged();
		if (level != null && !level.isClientSide()) {
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
		}
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("Tank");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new TankMenu(containerId, playerInventory, this);
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	public void drops() {
		Containers.dropContents(this.level, this.worldPosition, itemHandler);
	}

	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && fluidStack.isEmpty();
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.saveAdditional(pTag, pRegistries);
		pTag.put("Inventory", itemHandler.createTag(pRegistries));
		CompoundTag fluidTag = new CompoundTag();
		fluidStack.write(pRegistries, fluidTag);
		pTag.put("Fluid", fluidTag);
	}

	@Override
	protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.loadAdditional(pTag, pRegistries);
		itemHandler.fromTag(pTag.getList("Inventory", 10), pRegistries);
		if (pTag.contains("Fluid")) {
			this.fluidStack = FluidStack.read(pRegistries, pTag.getCompound("Fluid")).orElse(FluidStack.empty());
		}
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.put("Inventory", itemHandler.createTag());
		CompoundTag fluidTag = new CompoundTag();
		fluidStack.write(fluidTag);
		pTag.put("Fluid", fluidTag);
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		itemHandler.fromTag(pTag.getList("Inventory", 10));
		if (pTag.contains("Fluid")) {
			this.fluidStack = FluidStack.read(pTag.getCompound("Fluid"));
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