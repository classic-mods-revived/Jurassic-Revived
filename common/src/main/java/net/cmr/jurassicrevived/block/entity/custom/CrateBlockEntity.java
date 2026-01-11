package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.screen.custom.CrateMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.minecraft.network.FriendlyByteBuf;
//? if >1.20.1 {
/*import net.minecraft.core.HolderLookup;
 *///?}

public class CrateBlockEntity extends BlockEntity implements ExtendedMenuProvider {
	private final int size;
	public final SimpleContainer itemHandler;

	public CrateBlockEntity(BlockPos pos, BlockState state, int size) {
		super(ModBlockEntities.CRATE_BE.get(), pos, state);
		this.size = size;
		this.itemHandler = new SimpleContainer(size) {
			@Override
			public void setChanged() {
				super.setChanged();
				CrateBlockEntity.this.setChanged();
			}
		};
	}

	public boolean isEmptyForDrop() {
		return itemHandler.isEmpty();
	}

	public int getSize() { return size; }

	public void dropContents(Level level, BlockPos pos) {
		Containers.dropContents(level, pos, itemHandler);
	}

	public int redstoneSignal() {
		int filled = 0;
		for (int i = 0; i < itemHandler.getContainerSize(); i++) {
			if (!itemHandler.getItem(i).isEmpty()) filled++;
		}
		if (itemHandler.getContainerSize() == 0) return 0;
		return Math.round((filled / (float) itemHandler.getContainerSize()) * 15f);
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putInt("crate.size", this.size);
		tag.put("crate.inventory", itemHandler.createTag(registries));
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("crate.inventory")) {
			itemHandler.fromTag(tag.getList("crate.inventory", 10), registries);
		}
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("crate.size", this.size);
		tag.put("crate.inventory", itemHandler.createTag());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("crate.inventory")) {
			itemHandler.fromTag(tag.getList("crate.inventory", 10));
		}
	}
	//?}

	@Override
	public Component getDisplayName() {
		return Component.translatable(size <= 9 ? "block.jurassicrevived.wood_crate" : "block.jurassicrevived.iron_crate");
	}

	@Override
	public void saveExtraData(FriendlyByteBuf buf) {
		buf.writeBlockPos(getBlockPos());
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		return new CrateMenu(id, inv, this);
	}
}