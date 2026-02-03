package net.cmr.jurassicrevived.screen.custom;

import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.entity.custom.TankBlockEntity;
import net.cmr.jurassicrevived.networking.ModPackets;
import net.cmr.jurassicrevived.platform.Services;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TankMenu extends AbstractContainerMenu {
	public final TankBlockEntity blockEntity;
	private final Level level;
	private FluidStack fluidStack = FluidStack.empty();

	public TankMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
		this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
	}

	public TankMenu(int pContainerId, Inventory inv, BlockEntity blockEntity) {
		super(ModMenuTypes.TANK_MENU.get(), pContainerId);
		this.blockEntity = ((TankBlockEntity) blockEntity);
		this.level = inv.player.level();
		
		// Initialize fluidStack with the current state of the block entity
		// This ensures the client sees the correct fluid immediately if the BE is already synced
		if (this.blockEntity != null) {
			this.fluidStack = this.blockEntity.getFluid().copy();
		}

		addPlayerInventory(inv);
		addPlayerHotbar(inv);

		// Input Slot (0)
		this.addSlot(new Slot(this.blockEntity.itemHandler, 0, 44, 34) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return Services.ITEM_FLUID.isFluidHandler(stack);
			}
		});

		// Output Slot (1)
		this.addSlot(new Slot(this.blockEntity.itemHandler, 1, 116, 34){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		addDataSlot(new DataSlot() {
			@Override
			public int get() {
				return 0;
			}

			@Override
			public void set(int pValue) {
			}
		});
	}

	public void setFluid(FluidStack stack) {
		this.fluidStack = stack;
	}

	public FluidStack getFluid() {
		return this.fluidStack;
	}
	public void syncFluidToPlayers() {
		if (this.level != null && !this.level.isClientSide()) {
			for (Player player : this.level.players()) {
				if (player.containerMenu == this && player instanceof ServerPlayer serverPlayer) {
					ModPackets.sendTankSync(serverPlayer, this.fluidStack);
				}
			}
		}
	}

	@Override
	public void sendAllDataToRemote() {
		super.sendAllDataToRemote();
		// Ensure we have the latest fluid state from the block entity before syncing
		if (this.blockEntity != null) {
			this.fluidStack = this.blockEntity.getFluid().copy();
		}
		syncFluidToPlayers();
	}
	
	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		if (this.blockEntity != null) {
			FluidStack currentFluid = this.blockEntity.getFluid();
			if (!currentFluid.equals(this.fluidStack)) {
				this.fluidStack = currentFluid.copy();
				syncFluidToPlayers();
			}
		}
	}

	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_SLOT_COUNT;
	private static final int TE_INVENTORY_SLOT_COUNT = 2;

	@Override
	public ItemStack quickMoveStack(Player playerIn, int pIndex) {
		Slot sourceSlot = slots.get(pIndex);
		if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (pIndex < VANILLA_SLOT_COUNT) {
			// Player inventory -> tank slots
			if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
																			 + TE_INVENTORY_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
			// Tank slots -> player inventory
			if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			return ItemStack.EMPTY;
		}

		if (sourceStack.isEmpty()) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(playerIn, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
			pPlayer, ModBlocks.TANK.get());
	}

	private void addPlayerInventory(Inventory playerInventory) {
		for (int i = 0; i < 3; ++i) {
			for (int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
			}
		}
	}

	private void addPlayerHotbar(Inventory playerInventory) {
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
}