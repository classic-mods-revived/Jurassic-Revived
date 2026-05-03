package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.fluid.FluidStack;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.platform.transfer.InternalFluidHandler;
import net.cmr.jurassicrevived.platform.transfer.InternalFluidProvider;
import net.cmr.jurassicrevived.screen.custom.TankMenu;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.platform.Services;
import net.cmr.jurassicrevived.platform.services.IItemFluidHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

//? if >1.20.1 {
/*import net.minecraft.core.HolderLookup;
 *///?}

public class TankBlockEntity extends BlockEntity implements ExtendedMenuProvider, InternalFluidProvider {
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

	public TankFluidHandler getTank(@Nullable Direction side) {
		return tank;
	}

	private final TankFluidHandler tank = new TankFluidHandler();

	@Override
	public InternalFluidHandler getFluidHandler(@Nullable Direction side) {
		return tank;
	}

	public class TankFluidHandler implements InternalFluidHandler {
		@Override
		public FluidStack getFluid() {
			return fluidStack;
		}

		@Override
		public long getCapacity() {
			return CAPACITY;
		}

		public long fill(FluidStack stack, boolean simulate) {
			if (stack.isEmpty()) return 0;
			if (!fluidStack.isEmpty() && fluidStack.getFluid() != stack.getFluid()) return 0;

			long space = CAPACITY - fluidStack.getAmount();
			if (space <= 0) return 0;

			long toFill = Math.min(space, stack.getAmount());
			if (!simulate) {
				fluidStack = FluidStack.create(stack.getFluid(), fluidStack.getAmount() + toFill);
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
			return toFill;
		}

		public FluidStack drain(long amount, boolean simulate) {
			if (fluidStack.isEmpty() || amount <= 0) return FluidStack.empty();

			long drained = Math.min(amount, fluidStack.getAmount());
			FluidStack out = FluidStack.create(fluidStack.getFluid(), drained);

			if (!simulate) {
				long remaining = fluidStack.getAmount() - drained;
				fluidStack = remaining > 0
					? FluidStack.create(fluidStack.getFluid(), remaining)
					: FluidStack.empty();
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
			return out;
		}
	}

	public TankBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.TANK_BE.get(), pos, blockState);
	}

	public FluidStack getFluid() {
		return fluidStack;
	}

	public void setFluid(FluidStack stack) {
		this.fluidStack = stack == null || stack.isEmpty() ? FluidStack.empty() : stack;
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
		if (level.isClientSide) return;

		ItemStack input = itemHandler.getItem(0);
		if (input.isEmpty()) return;

		ItemStack output = itemHandler.getItem(1);

		long rate = Math.max(1, JRConfigManager.get().milliBucketsPerSecond / 20);
		IItemFluidHelper helper = Services.ITEM_FLUID;

		ItemStack inputOne = input.copy();
		inputOne.setCount(1);

		var containedOpt = helper.getContainedFluid(inputOne);

		if (containedOpt.isPresent() && !containedOpt.get().isEmpty()) {
			FluidStack contained = containedOpt.get();
			long canFill = tank.fill(contained, true);
			long toDrain = Math.min(rate, canFill);
			if (toDrain <= 0) return;

			IItemFluidHelper.TransferResult drained = helper.drain(inputOne, toDrain, false);
			
			// If partial drain failed (amount 0), and we requested less than full capacity, try requesting full capacity
			if (drained.amount() == 0 && toDrain < canFill) {
				drained = helper.drain(inputOne, canFill, false);
			}

			if (drained.amount() > 0) {
				ItemStack resultStack = drained.stack();
				boolean emptyNow = helper.getContainedFluid(resultStack)
					.map(FluidStack::isEmpty)
					.orElse(true);

				if (emptyNow) {
					if (canMoveToOutput(resultStack, output)) {
						tank.fill(FluidStack.create(contained.getFluid(), drained.amount()), false);
						input.shrink(1);
						if (input.isEmpty()) {
							itemHandler.setItem(0, ItemStack.EMPTY);
						} else {
							itemHandler.setChanged();
						}
						addToOutput(resultStack, output);
					}
				} else {
					if (input.getCount() == 1) {
						tank.fill(FluidStack.create(contained.getFluid(), drained.amount()), false);
						itemHandler.setItem(0, resultStack);
					}
				}
			}
		} else {
			FluidStack tankFluid = tank.getFluid();
			if (tankFluid.isEmpty()) return;

			long toFill = Math.min(rate, tankFluid.getAmount());
			IItemFluidHelper.TransferResult filled = helper.fill(inputOne, tankFluid, toFill, false);

			// If partial fill failed, and we requested less than available fluid, try requesting more
			if (filled.amount() == 0 && toFill < tankFluid.getAmount()) {
				filled = helper.fill(inputOne, tankFluid, tankFluid.getAmount(), false);
			}

			if (filled.amount() > 0) {
				ItemStack resultStack = filled.stack();
				boolean fullNow = helper.fill(resultStack, tankFluid, 1, true).amount() == 0;
				boolean tankWillBeEmpty = tankFluid.getAmount() - filled.amount() <= 0;

				if (fullNow || tankWillBeEmpty) {
					if (canMoveToOutput(resultStack, output)) {
						tank.drain(filled.amount(), false);
						input.shrink(1);
						if (input.isEmpty()) {
							itemHandler.setItem(0, ItemStack.EMPTY);
						} else {
							itemHandler.setChanged();
						}
						addToOutput(resultStack, output);
					}
				} else {
					if (input.getCount() == 1) {
						tank.drain(filled.amount(), false);
						itemHandler.setItem(0, resultStack);
					}
				}
			}
		}
	}

	private boolean canMoveToOutput(ItemStack result, ItemStack output) {
		if (output.isEmpty()) return true;
		//? if >1.20.1 {
		/*return ItemStack.isSameItemSameComponents(result, output) && output.getCount() + result.getCount() <= output.getMaxStackSize();
		*///?} else {
		return ItemStack.isSameItemSameTags(result, output) && output.getCount() + result.getCount() <= output.getMaxStackSize();
		//?}
	}

	private void addToOutput(ItemStack result, ItemStack output) {
		if (output.isEmpty()) {
			itemHandler.setItem(1, result);
		} else {
			output.grow(result.getCount());
			itemHandler.setChanged();
		}
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty() && fluidStack.isEmpty();
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.saveAdditional(pTag, pRegistries);
		pTag.put("Inventory", itemHandler.createTag(pRegistries));
		if (!fluidStack.isEmpty()) {
			pTag.put("Fluid", fluidStack.write(pRegistries, new CompoundTag()));
		}
	}

	@Override
	protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.loadAdditional(pTag, pRegistries);
		itemHandler.fromTag(pTag.getList("Inventory", 10), pRegistries);
		if (pTag.contains("Fluid", 10)) {
			CompoundTag fluidTag = pTag.getCompound("Fluid");
			if (fluidTag.contains("id") && fluidTag.contains("amount")) {
				this.fluidStack = FluidStack.read(pRegistries, fluidTag).orElse(FluidStack.empty());
			} else {
				this.fluidStack = FluidStack.empty();
			}
		} else {
			this.fluidStack = FluidStack.empty();
		}
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.put("Inventory", itemHandler.createTag());
		if (!fluidStack.isEmpty()) {
			CompoundTag fluidTag = new CompoundTag();
			fluidStack.write(fluidTag);
			pTag.put("Fluid", fluidTag);
		}
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		itemHandler.fromTag(pTag.getList("Inventory", 10));
		if (pTag.contains("Fluid")) {
			this.fluidStack = FluidStack.read(pTag.getCompound("Fluid"));
		} else {
			this.fluidStack = FluidStack.empty();
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