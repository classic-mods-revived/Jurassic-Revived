package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

//? if >1.20.1 {
/*import net.minecraft.core.HolderLookup;
 *///?}

public class EggBlockEntity extends BlockEntity {
	private long placedAt = -1L;
	private int totalSeconds = 5; // default

	public EggBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.EGG_BE.get(), pos, state);
	}

	public void setPlacedAt(long gameTime) {
		this.placedAt = gameTime;
		setChanged();
	}

	public void resetForNewPlacement(Level level, int totalSeconds) {
		this.placedAt = level.getGameTime();
		this.totalSeconds = Math.max(1, totalSeconds);
		setChanged();
	}

	public void setTotalSeconds(int secs) {
		this.totalSeconds = Math.max(1, secs);
		setChanged();
	}

	public int getTotalSeconds() {
		return totalSeconds;
	}

	public int getSecondsRemaining(Level level) {
		if (placedAt < 0) return totalSeconds;
		long elapsed = level.getGameTime() - placedAt;
		long remainingTicks = Math.max(0, (20L * totalSeconds) - elapsed);
		return (int) Math.ceil(remainingTicks / 20.0);
	}

	//? if >1.20.1 {
	/*@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		saveCommonData(tag);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		loadCommonData(tag);
	}
	*///?} else {
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		saveCommonData(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		loadCommonData(tag);
	}
	//?}

	private void saveCommonData(CompoundTag tag) {
		tag.putLong("egg.placedAt", placedAt);
		tag.putInt("egg.totalSeconds", totalSeconds);
	}

	private void loadCommonData(CompoundTag tag) {
		if (tag.contains("egg.placedAt")) {
			placedAt = tag.getLong("egg.placedAt");
		}
		if (tag.contains("egg.totalSeconds")) {
			totalSeconds = Math.max(1, tag.getInt("egg.totalSeconds"));
		}
	}

	public void invalidateTimer() {
		this.placedAt = -1L;
		setChanged();
	}

	public Component getHatchTooltip(Level level, Player player) {
		int secs = getSecondsRemaining(level);
		return Component.translatable("tooltip.jurassicrevived.egg.hatches_in_seconds", secs);
	}
}