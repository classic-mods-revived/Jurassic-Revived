package net.cmr.jurassicrevived.block.entity.custom;

import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.cmr.jurassicrevived.block.custom.PipeBlock.ConnectionType;
import net.cmr.jurassicrevived.block.custom.PipeBlock.Transport;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.platform.Services;
import net.cmr.jurassicrevived.platform.transfer.PlatformEnergyHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformFluidHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class PipeBlockEntity extends BlockEntity
{

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
		int fluidCap = block.getMaxFluidPerTick();
		int energyCap = block.getMaxEnergyPerTick();

		switch (be.transport) {
			case ITEMS -> transferItems(level, pos, state, itemCap);
			case FLUIDS -> transferFluids(level, pos, state, fluidCap);
			case ENERGY -> transferEnergy(level, pos, state, energyCap);
		}
	}

	// ===== Network discovery =====

	private record PipeEndpoint(BlockPos pipePos, Direction side)
	{
	}

	private static class Network
	{
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
		if (net.sources.isEmpty() || net.sinks.isEmpty()) return;

		Map<BlockPos, List<PipeEndpoint>> sinksByPipe = indexSinksByPipe(net.sinks);

		int remaining = perTickLimit;
		for (PipeEndpoint srcEp : net.sources) {
			if (remaining <= 0) break;

			BlockPos srcPos = srcEp.pipePos.relative(srcEp.side);
			Direction srcSide = srcEp.side.getOpposite();

			PlatformItemHandler src = Services.TRANSFER
				.getItemHandler(level, srcPos, srcSide)
				.orElse(null);
			if (src == null) continue;

			PipeEndpoint sinkEp = findNearestSink(level, srcEp.pipePos, sinksByPipe, Transport.ITEMS);
			if (sinkEp == null) continue;

			BlockPos dstPos = sinkEp.pipePos.relative(sinkEp.side);
			Direction dstSide = sinkEp.side.getOpposite();

			PlatformItemHandler dst = Services.TRANSFER
				.getItemHandler(level, dstPos, dstSide)
				.orElse(null);
			if (dst == null) continue;

			remaining = moveFromSourceToSingleTarget(src, dst, remaining);
		}
	}

	private static PipeEndpoint findNearestSink(
		Level level,
		BlockPos startPipe,
		Map<BlockPos, List<PipeEndpoint>> sinksByPipe,
		Transport transport
	) {
		ArrayDeque<BlockPos> q = new ArrayDeque<>();
		HashSet<BlockPos> seen = new HashSet<>();
		q.add(startPipe);
		seen.add(startPipe);

		while (!q.isEmpty()) {
			BlockPos p = q.removeFirst();

			List<PipeEndpoint> sinksHere = sinksByPipe.get(p);
			if (sinksHere != null && !sinksHere.isEmpty()) {
				return sinksHere.get(0);
			}

			BlockState st = level.getBlockState(p);
			if (!(st.getBlock() instanceof PipeBlock pb) || pb.getTransport() != transport) continue;

			for (Direction d : Direction.values()) {
				if (st.getValue(PipeBlock.getProp(d)) == ConnectionType.PIPE) {
					BlockPos np = p.relative(d);
					if (seen.add(np)) q.add(np);
				}
			}
		}
		return null;
	}

	private static void transferEnergy(Level level, BlockPos pos, BlockState state, int perTickLimit) {
		Network net = discoverNetwork(level, pos, Transport.ENERGY);
		if (net.sources.isEmpty() || net.sinks.isEmpty()) return;

		Map<BlockPos, List<PipeEndpoint>> sinksByPipe = indexSinksByPipe(net.sinks);

		int remaining = perTickLimit;
		for (PipeEndpoint srcEp : net.sources) {
			if (remaining <= 0) break;

			BlockPos srcPos = srcEp.pipePos.relative(srcEp.side);
			Direction srcSide = srcEp.side.getOpposite();

			PlatformEnergyHandler src = Services.TRANSFER
				.getEnergyHandler(level, srcPos, srcSide)
				.orElse(null);
			if (src == null) continue;

			PipeEndpoint sinkEp = findNearestSink(level, srcEp.pipePos, sinksByPipe, Transport.ENERGY);
			if (sinkEp == null) continue;

			BlockPos dstPos = sinkEp.pipePos.relative(sinkEp.side);
			Direction dstSide = sinkEp.side.getOpposite();

			PlatformEnergyHandler dst = Services.TRANSFER
				.getEnergyHandler(level, dstPos, dstSide)
				.orElse(null);
			if (dst == null) continue;

			int available = src.extract(remaining, true);
			if (available <= 0) continue;

			int accepted = dst.insert(available, true);
			if (accepted <= 0) continue;

			int extracted = src.extract(accepted, false);
			int inserted = dst.insert(extracted, false);
			remaining -= inserted;
		}
	}

	private static void transferFluids(Level level, BlockPos pos, BlockState state, int perTickLimit) {
		Network net = discoverNetwork(level, pos, Transport.FLUIDS);
		if (net.sources.isEmpty() || net.sinks.isEmpty()) return;

		Map<BlockPos, List<PipeEndpoint>> sinksByPipe = indexSinksByPipe(net.sinks);

		long remaining = perTickLimit;
		for (PipeEndpoint srcEp : net.sources) {
			if (remaining <= 0) break;

			BlockPos srcPos = srcEp.pipePos.relative(srcEp.side);
			Direction srcSide = srcEp.side.getOpposite();

			PlatformFluidHandler src = Services.TRANSFER
				.getFluidHandler(level, srcPos, srcSide)
				.orElse(null);
			if (src == null) continue;

			PipeEndpoint sinkEp = findNearestSink(level, srcEp.pipePos, sinksByPipe, Transport.FLUIDS);
			if (sinkEp == null) continue;

			BlockPos dstPos = sinkEp.pipePos.relative(sinkEp.side);
			Direction dstSide = sinkEp.side.getOpposite();

			PlatformFluidHandler dst = Services.TRANSFER
				.getFluidHandler(level, dstPos, dstSide)
				.orElse(null);
			if (dst == null) continue;

			for (FluidStack candidate : src.getExtractableFluids()) {
				if (candidate.isEmpty()) continue;

				long available = src.extract(candidate, remaining, true);
				if (available <= 0) continue;

				long accepted = dst.insert(candidate, available, true);
				if (accepted <= 0) continue;

				FluidStack toMove = candidate.copy();
				toMove.setAmount(accepted);

				long extracted = src.extract(toMove, accepted, false);
				long inserted = dst.insert(toMove, extracted, false);
				remaining -= inserted;

				if (remaining <= 0) break;
			}
		}
	}

	private static Map<BlockPos, List<PipeEndpoint>> indexSinksByPipe(List<PipeEndpoint> sinks) {
		Map<BlockPos, List<PipeEndpoint>> map = new HashMap<>();
		for (PipeEndpoint ep : sinks) {
			map.computeIfAbsent(ep.pipePos, k -> new ArrayList<>()).add(ep);
		}
		return map;
	}

	private static int moveFromSourceToSingleTarget(PlatformItemHandler src, PlatformItemHandler dst, int limit) {
		int remaining = limit;

		for (ItemStack candidate : src.getExtractableStacks()) {
			if (candidate.isEmpty()) continue;

			int available = src.extract(candidate, remaining, true);
			if (available <= 0) continue;

			int accepted = dst.insert(candidate, available, true);
			if (accepted <= 0) continue;

			int extracted = src.extract(candidate, accepted, false);
			if (extracted <= 0) continue;

			int inserted = dst.insert(candidate, extracted, false);
			remaining -= inserted;

			if (remaining <= 0) break;
		}
		return remaining;
	}
}