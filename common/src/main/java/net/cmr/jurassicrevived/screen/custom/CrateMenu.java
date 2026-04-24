package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.block.entity.custom.CrateBlockEntity;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CrateMenu extends AbstractContainerMenu {
    public final CrateBlockEntity blockEntity;
	private final Level level;

    public CrateMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
        this(containerId, inventory, (CrateBlockEntity) inventory.player.level().getBlockEntity(data.readBlockPos()));
    }

    public CrateMenu(int containerId, Inventory inventory, CrateBlockEntity entity) {
		super(resolveType(entity), containerId);
		this.blockEntity = entity;
		this.level = inventory.player.level();

		addPlayerInventory(inventory);
		addPlayerHotbar(inventory);

		int size = entity.getSize();
		if (size == 9) {
			// One row of 9 at (8,62)
			int startX = 8;
			int startY = 62;
			for (int c = 0; c < 9; c++) {
				this.addSlot(new Slot(entity.itemHandler, c, startX + c * 18, startY));
			}
		} else {
			// 18 slots: two rows of 9, y=44 and y=62
			int startX = 8;
			int row1Y = 44;
			int row2Y = 62;
			for (int c = 0; c < 9; c++) {
				this.addSlot(new Slot(entity.itemHandler, c, startX + c * 18, row1Y));
			}
			for (int c = 0; c < 9; c++) {
				this.addSlot(new Slot(entity.itemHandler, 9 + c, startX + c * 18, row2Y));
			}
		}
	}

	private static MenuType<?> resolveType(CrateBlockEntity be) {
		return be.getSize() == 9 ? ModMenuTypes.WOOD_CRATE_MENU.get() : ModMenuTypes.IRON_CRATE_MENU.get();
	}

	// Inventory move logic
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		Slot source = slots.get(index);
		if (source == null || !source.hasItem()) return ItemStack.EMPTY;
		ItemStack sourceStack = source.getItem();
		ItemStack copy = sourceStack.copy();

		int teSlots = blockEntity.itemHandler.getContainerSize();
		int teFirst = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
		int teEnd = teFirst + teSlots;

		if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			if (!moveItemStackTo(sourceStack, teFirst, teEnd, false)) {
				return ItemStack.EMPTY;
			}
		} else if (index >= teFirst && index < teEnd) {
			if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			return ItemStack.EMPTY;
		}

		if (sourceStack.isEmpty()) source.set(ItemStack.EMPTY);
		else source.setChanged();
		source.onTake(playerIn, sourceStack);
		return copy;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
	}

	private void addPlayerInventory(Inventory inv) {
		for (int r = 0; r < 3; ++r) {
			for (int c = 0; c < 9; ++c) {
				this.addSlot(new Slot(inv, c + r * 9 + 9, 8 + c * 18, 84 + r * 18));
			}
		}
	}

	private void addPlayerHotbar(Inventory inv) {
		for (int c = 0; c < 9; ++c) {
			this.addSlot(new Slot(inv, c, 8 + c * 18, 142));
		}
	}
}