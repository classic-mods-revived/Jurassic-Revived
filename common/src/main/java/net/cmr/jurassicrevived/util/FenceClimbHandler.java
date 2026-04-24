package net.cmr.jurassicrevived.util;

import dev.architectury.event.events.common.TickEvent;
import net.cmr.jurassicrevived.block.custom.FencePoleBlock;
import net.cmr.jurassicrevived.block.custom.FenceWireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public final class FenceClimbHandler {

	public static void register() {
		// Architectury replacement for ServerTickEvent.Post
		TickEvent.SERVER_POST.register(server -> {
			for (ServerLevel level : server.getAllLevels()) {
				for (ServerPlayer player : level.players()) {
					if (!player.isAlive()) continue;

					boolean touching = false;
					AABB bb = player.getBoundingBox().inflate(0.05, 0.05, 0.05);
					int minX = Mth.floor(bb.minX), maxX = Mth.floor(bb.maxX);
					int minY = Mth.floor(bb.minY), maxY = Mth.floor(Math.min(bb.minY + 1.0, bb.maxY));
					int minZ = Mth.floor(bb.minZ), maxZ = Mth.floor(bb.maxZ);

					outer:
					for (int x = minX; x <= maxX; x++) {
						for (int y = minY; y <= maxY; y++) {
							for (int z = minZ; z <= maxZ; z++) {
								BlockPos pos = new BlockPos(x, y, z);
								var state = level.getBlockState(pos);

								boolean isWire = state.getBlock() instanceof FenceWireBlock;
								boolean isPole = state.getBlock() instanceof FencePoleBlock;
								if (!isWire && !isPole) continue;

								var shape = state.getCollisionShape(level, pos, CollisionContext.of(player));
								if (shape.isEmpty()) continue;

								if (isPole) {
									AABB postAabb = new AABB(6 / 16.0, 0.0, 6 / 16.0, 10 / 16.0, 1.0, 10 / 16.0).move(pos.getX(), pos.getY(), pos.getZ());
									for (AABB aabb : shape.toAabbs()) {
										AABB moved = aabb.move(pos.getX(), pos.getY(), pos.getZ());
										if (approximatelySame(moved, postAabb)) continue;
										if (bb.intersects(moved) && notStandingOnTop(bb, moved)) {
											touching = true;
											break outer;
										}
									}
								} else {
									for (AABB aabb : shape.toAabbs()) {
										AABB moved = aabb.move(pos.getX(), pos.getY(), pos.getZ());
										if (bb.intersects(moved) && notStandingOnTop(bb, moved)) {
											touching = true;
											break outer;
										}
									}
								}
							}
						}
					}

					if (!touching) continue;

					Vec3 v = player.getDeltaMovement();
					boolean sneaking = player.isShiftKeyDown();

					double maxHoriz = 0.15;
					double vx = Mth.clamp(v.x, -maxHoriz, maxHoriz);
					double vz = Mth.clamp(v.z, -maxHoriz, maxHoriz);

					double vy = v.y;
					if (sneaking) {
						vy = Math.max(vy, 0.0);
					} else {
						vy = Math.max(vy, -0.15);
						vy = Math.max(vy, 0.01);
					}

					player.setOnGround(false);
					player.fallDistance = 0.0F;
					player.setDeltaMovement(vx, vy, vz);
					player.hasImpulse = true;
				}
			}
		});
	}

	private static boolean approximatelySame(AABB a, AABB b) {
		double eps = 1e-6;
		return Math.abs(a.minX - b.minX) < eps &&
			   Math.abs(a.minY - b.minY) < eps &&
			   Math.abs(a.minZ - b.minZ) < eps &&
			   Math.abs(a.maxX - b.maxX) < eps &&
			   Math.abs(a.maxY - b.maxY) < eps &&
			   Math.abs(a.maxZ - b.maxZ) < eps;
	}

	private static boolean notStandingOnTop(AABB player, AABB block) {
		double eps = 0.05;
		return !(player.minY >= block.maxY - eps && player.minY <= block.maxY + eps);
	}
}