package net.cmr.jurassicrevived.sound;

import dev.architectury.event.events.client.ClientTickEvent;
import net.cmr.jurassicrevived.block.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class MachineHumSoundHandler {
	private static final Map<BlockPos, MachineHumLoopSound> ACTIVE_SOUNDS = new HashMap<>();

	public static void init() {
		ClientTickEvent.CLIENT_POST.register(MachineHumSoundHandler::onClientTick);
	}

	private static void onClientTick(Minecraft mc) {
		if (mc.isPaused()) return;

		ClientLevel level = mc.level;
		if (level == null) {
			stopAllSounds();
			return;
		}

		// Clean up sounds
		Iterator<Map.Entry<BlockPos, MachineHumLoopSound>> it = ACTIVE_SOUNDS.entrySet().iterator();
		while (it.hasNext()) {
			var entry = it.next();
			BlockPos pos = entry.getKey();
			MachineHumLoopSound sound = entry.getValue();

			BlockState state = level.getBlockState(pos);
			if (shouldStopHum(state) || sound.isStopped()) {
				sound.stopPlaying();
				it.remove();
			}
		}

		// Start sounds near player
		if (mc.player != null) {
			int radius = 16;
			BlockPos center = mc.player.blockPosition();

			for (BlockPos pos : BlockPos.betweenClosed(
				center.offset(-radius, -radius, -radius),
				center.offset(radius, radius, radius))) {

				BlockPos immutablePos = pos.immutable();
				if (ACTIVE_SOUNDS.containsKey(immutablePos)) continue;

				BlockState state = level.getBlockState(immutablePos);
				if (shouldStartHum(state)) {
					MachineHumLoopSound sound = new MachineHumLoopSound(level, immutablePos);
					ACTIVE_SOUNDS.put(immutablePos, sound);
					mc.getSoundManager().play(sound);
				}
			}
		}
	}

	private static boolean shouldStartHum(BlockState state) {
		Block block = state.getBlock();
		// Check if the block is one of our machines
		boolean isMachine = block instanceof DNAAnalyzerBlock
							|| block instanceof DNAExtractorBlock
							|| block instanceof DNAHybridizerBlock
							|| block instanceof EmbryoCalcificationMachineBlock
							|| block instanceof EmbryonicMachineBlock
							|| block instanceof FossilCleanerBlock
							|| block instanceof FossilGrinderBlock
							|| block instanceof GeneratorBlock
							|| block instanceof IncubatorBlock;

		if (!isMachine) return false;

		return state.hasProperty(BlockStateProperties.LIT) && state.getValue(BlockStateProperties.LIT);
	}

	private static boolean shouldStopHum(BlockState state) {
		if (state.isAir()) return true;
		return !state.hasProperty(BlockStateProperties.LIT) || !state.getValue(BlockStateProperties.LIT);
	}

	private static void stopAllSounds() {
		ACTIVE_SOUNDS.values().forEach(MachineHumLoopSound::stopPlaying);
		ACTIVE_SOUNDS.clear();
	}
}