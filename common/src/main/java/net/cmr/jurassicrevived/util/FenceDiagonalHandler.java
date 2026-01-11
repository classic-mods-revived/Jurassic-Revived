package net.cmr.jurassicrevived.util;

import dev.architectury.event.events.common.BlockEvent;
import net.cmr.jurassicrevived.block.custom.FencePoleBlock;
import net.cmr.jurassicrevived.block.custom.FenceWireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class FenceDiagonalHandler {

	public static void init() {
		// Register for block placement
		BlockEvent.PLACE.register((level, pos, state, entity) -> {
			if (level instanceof Level) {
				notifyDiagonalFences((Level) level, pos);
			}
			return dev.architectury.event.EventResult.pass();
		});

		// Register for block breaking
		BlockEvent.BREAK.register((level, pos, state, player, xp) -> {
			if (level instanceof Level) {
				notifyDiagonalFences((Level) level, pos);
			}
			return dev.architectury.event.EventResult.pass();
		});
	}

	private static void notifyDiagonalFences(Level level, BlockPos changedPos) {
		BlockPos[] diagonals = new BlockPos[] {
			changedPos.north().east(),
			changedPos.south().east(),
			changedPos.south().west(),
			changedPos.north().west()
		};

		for (BlockPos p : diagonals) {
			BlockState bs = level.getBlockState(p);

			if (bs.getBlock() instanceof FenceWireBlock) {
				BlockState updated = bs
					.setValue(FenceWireBlock.NE, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST))
					.setValue(FenceWireBlock.SE, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST))
					.setValue(FenceWireBlock.SW, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST))
					.setValue(FenceWireBlock.NW, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST));
				if (updated != bs) {
					level.setBlock(p, updated, Block.UPDATE_CLIENTS);
				}
			} else if (bs.getBlock() instanceof FencePoleBlock) {
				BlockState updated = bs
					.setValue(FencePoleBlock.NE, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST))
					.setValue(FencePoleBlock.SE, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST))
					.setValue(FencePoleBlock.SW, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST))
					.setValue(FencePoleBlock.NW, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST));
				if (updated != bs) {
					level.setBlock(p, updated, Block.UPDATE_CLIENTS);
				}
			}
		}
	}
}