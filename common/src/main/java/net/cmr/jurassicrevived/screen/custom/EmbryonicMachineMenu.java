package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.block.entity.custom.EmbryonicMachineBlockEntity;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EmbryonicMachineMenu extends AbstractContainerMenu {
	public final EmbryonicMachineBlockEntity blockEntity;
	public final Level level;
	public final ContainerData data;

	public EmbryonicMachineMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
	}

	public EmbryonicMachineMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(ModMenuTypes.EMBRYONIC_MACHINE_MENU.get(), containerId);
		blockEntity = ((EmbryonicMachineBlockEntity) entity);
		this.level = inventory.player.level();
		this.data = data;

		addPlayerInventory(inventory);
		addPlayerHotbar(inventory);

		// Syringe Slot (0)
		this.addSlot(new Slot(blockEntity.itemHandler, 0, 39, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == ModItems.SYRINGE.get();
			}
		});

		// DNA Slot (1)
		this.addSlot(new Slot(blockEntity.itemHandler, 1, 57, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ModTags.Items.DNA);
			}
		});

		// Frog DNA Slot (2)
		this.addSlot(new Slot(blockEntity.itemHandler, 2, 48, 53) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ModItems.FROG_DNA.get());
			}
		});

		// Output Slot (3)
		this.addSlot(new Slot(blockEntity.itemHandler, 3, 103, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		addDataSlots(data);
	}

	public boolean isCrafting() {
		return data.get(0) > 0;
	}

	public int getScaledArrowProgress() {
		int progress = this.data.get(0);
		int maxProgress = this.data.get(1);
		int arrowPixelSize = 24;

		return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
	}

	// Inventory move logic constants
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_SLOT_COUNT;
	private static final int TE_INVENTORY_SLOT_COUNT = 4;

	@Override
	public ItemStack quickMoveStack(Player playerIn, int pIndex) {
		Slot sourceSlot = slots.get(pIndex);
		if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		if (pIndex < VANILLA_SLOT_COUNT) {
			// Player inventory -> machine inputs
			int teInputStart = TE_INVENTORY_FIRST_SLOT_INDEX;
			int teInputEndExclusive = TE_INVENTORY_FIRST_SLOT_INDEX + 3;
			if (!moveItemStackTo(sourceStack, teInputStart, teInputEndExclusive, false)) {
				return ItemStack.EMPTY;
			}
		} else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
			// Machine -> player inventory
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

	public void addPlayerInventory(Inventory playerInventory) {
		for (int i = 0; i < 3; i++) {
			for (int l = 0; l < 9; l++) {
				this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
			}
		}
	}

	public void addPlayerHotbar(Inventory playerInventory) {
		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
}