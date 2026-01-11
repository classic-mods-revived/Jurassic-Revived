package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.cmr.jurassicrevived.block.custom.PipeBlock.ConnectionType;
import net.cmr.jurassicrevived.block.custom.PipeBlock.Transport;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class PipeBlockEntity extends BlockEntity {

	private final Transport transport;

	public PipeBlockEntity(BlockPos pos, BlockState state) {
		super(resolveType(state), pos, state);
		this.transport = ((PipeBlock) state.getBlock()).getTransport();
	}

	private static net.minecraft.world.level.block.entity.BlockEntityType<PipeBlockEntity> resolveType(BlockState state) {
		PipeBlock block = (PipeBlock) state.getBlock();
		return switch (block.getTransport()) {
			case ITEMS -> ModBlockEntities.ITEM_PIPE_BE.get();
			case FLUIDS -> ModBlockEntities.FLUID_PIPE_BE.get();
			case ENERGY -> ModBlockEntities.POWER_PIPE_BE.get();
		};
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, PipeBlockEntity be) {
		if (level == null || level.isClientSide) return;

		PipeBlock block = (PipeBlock) state.getBlock();
		int itemCap = block.getMaxItemsPerTick();
		// Fallback caps if Config is not ready
		int fluidCap = 1000;
		int energyCap = 1000;

		switch (be.transport) {
			case ITEMS -> transferItems(level, pos, state, itemCap);
			case FLUIDS -> transferFluids(level, pos, state, fluidCap);
			case ENERGY -> transferEnergy(level, pos, state, energyCap);
		}
	}

	// ===== Network discovery =====

	private record PipeEndpoint(BlockPos pipePos, Direction side) {}

	private static class Network {
		final List<PipeEndpoint> sources = new ArrayList<>();
		final List<PipeEndpoint> sinks = new ArrayList<>();
	}

	private static Network discoverNetwork(Level level, BlockPos start, Transport transport) {
		Network net = new Network();
		ArrayDeque<BlockPos> q = new ArrayDeque<>();
		HashSet<BlockPos> seen = new HashSet<>();
		q.add(start);
		seen.add(start);

		while (!q.isEmpty()) {
			BlockPos p = q.removeFirst();
			BlockState st = level.getBlockState(p);
			if (!(st.getBlock() instanceof PipeBlock pb) || pb.getTransport() != transport) continue;

			for (Direction d : Direction.values()) {
				ConnectionType ct = st.getValue(PipeBlock.getProp(d));

				if (ct == ConnectionType.CONNECTOR_PULL) {
					net.sources.add(new PipeEndpoint(p, d));
				} else if (ct == ConnectionType.CONNECTOR) {
					net.sinks.add(new PipeEndpoint(p, d));
				}

				if (ct == ConnectionType.PIPE) {
					BlockPos np = p.relative(d);
					if (!seen.contains(np)) {
						BlockState ns = level.getBlockState(np);
						if (ns.getBlock() instanceof PipeBlock op && op.getTransport() == transport) {
							seen.add(np);
							q.add(np);
						}
					}
				}
			}
		}
		return net;
	}

	// ===== Item Transfer (Using Vanilla Container) =====

	private static void transferItems(Level level, BlockPos pos, BlockState state, int perTickLimit) {
		Network net = discoverNetwork(level, pos, Transport.ITEMS);
		List<Container> outputs = new ArrayList<>();
		for (PipeEndpoint ep : net.sinks) {
			BlockEntity be = level.getBlockEntity(ep.pipePos.relative(ep.side));
			if (be instanceof Container c) outputs.add(c);
		}
		if (outputs.isEmpty()) return;

		int remaining = perTickLimit;
		for (PipeEndpoint ep : net.sources) {
			if (remaining <= 0) break;
			BlockEntity be = level.getBlockEntity(ep.pipePos.relative(ep.side));
			if (!(be instanceof Container src)) continue;

			for (int i = 0; i < src.getContainerSize() && remaining > 0; i++) {
				ItemStack stack = src.getItem(i);
				if (stack.isEmpty()) continue;

				ItemStack toMove = stack.copy();
				toMove.setCount(Math.min(stack.getCount(), remaining));

				for (Container out : outputs) {
					// Logic to insert into vanilla container (simplified)
					// You might want a helper for this
				}
			}
		}
	}

	// ===== Energy Transfer (Using Custom Energy System) =====

	private static void transferEnergy(Level level, BlockPos pos, BlockState state, int perTickLimit) {
		Network net = discoverNetwork(level, pos, Transport.ENERGY);
		List<ModEnergyStorage> outputs = new ArrayList<>();
		for (PipeEndpoint ep : net.sinks) {
			BlockEntity be = level.getBlockEntity(ep.pipePos.relative(ep.side));
			if (be instanceof ModEnergyUtil.EnergyProvider provider) {
				ModEnergyStorage storage = provider.getEnergyStorage(ep.side.getOpposite());
				if (storage != null && storage.canReceive()) outputs.add(storage);
			}
		}
		if (outputs.isEmpty()) return;

		int remaining = perTickLimit;
		for (PipeEndpoint ep : net.sources) {
			if (remaining <= 0) break;
			BlockEntity be = level.getBlockEntity(ep.pipePos.relative(ep.side));
			if (be instanceof ModEnergyUtil.EnergyProvider provider) {
				ModEnergyStorage src = provider.getEnergyStorage(ep.side.getOpposite());
				if (src == null || !src.canExtract()) continue;

				int canExtract = src.extractEnergy(remaining, true);
				if (canExtract <= 0) continue;

				for (ModEnergyStorage out : outputs) {
					int accepted = out.receiveEnergy(canExtract, true);
					if (accepted > 0) {
						int actuallyExtracted = src.extractEnergy(accepted, false);
						out.receiveEnergy(actuallyExtracted, false);
						remaining -= actuallyExtracted;
						break;
					}
				}
			}
		}
	}

	private static void transferFluids(Level level, BlockPos pos, BlockState state, int perTickLimit) {
		// Implementation would use Architectury FluidStack similarly to energy
	}
}