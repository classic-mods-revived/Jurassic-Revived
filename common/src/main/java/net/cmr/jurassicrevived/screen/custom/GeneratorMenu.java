package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.block.entity.custom.GeneratorBlockEntity;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GeneratorMenu extends AbstractContainerMenu {
	public final GeneratorBlockEntity blockEntity;
	private final Level level;
	private final ContainerData data;

	public GeneratorMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
		this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
	}

	public GeneratorMenu(int pContainerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
		super(ModMenuTypes.GENERATOR_MENU.get(), pContainerId);
		this.blockEntity = ((GeneratorBlockEntity) blockEntity);
		this.level = inv.player.level();
		this.data = data;

		addPlayerInventory(inv);
		addPlayerHotbar(inv);

		this.addSlot(new Slot(this.blockEntity.itemHandler, 0, 80, 35));

		addDataSlots(data);
	}

	public boolean isBurning() {
		int elapsed = data.get(0);
		int total = data.get(1);
		return total > 0 && elapsed < total;
	}

	public float getFuelProgress() {
		int elapsed = this.data.get(0);
		int total = this.data.get(1);
		if (total <= 0) return 0.0f;
		float remaining = (float) (total - Math.min(elapsed, total)) / (float) total;
		return Mth.clamp(remaining, 0.0f, 1.0f);
	}

	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_SLOT_COUNT;
	private static final int TE_INVENTORY_SLOT_COUNT = 1;

	@Override
	public ItemStack quickMoveStack(Player playerIn, int pIndex) {
		Slot sourceSlot = slots.get(pIndex);
		if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (pIndex < VANILLA_SLOT_COUNT) {
			if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
																			 + TE_INVENTORY_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
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
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(this.level, this.blockEntity.getBlockPos()),
			player, this.blockEntity.getBlockState().getBlock());
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