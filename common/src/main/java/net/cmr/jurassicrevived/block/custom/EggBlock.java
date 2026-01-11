package net.cmr.jurassicrevived.block.custom;

import net.cmr.jurassicrevived.block.entity.custom.EggBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class EggBlock extends Block implements EntityBlock {

	private final Supplier<? extends EntityType<? extends Mob>> toSpawn;
	private final int hatchSeconds = 1200;

	public EggBlock(Properties pProperties, Supplier<? extends EntityType<? extends Mob>> toSpawn) {
		super(pProperties);
		this.toSpawn = toSpawn;
	}

	private static final VoxelShape EGG_SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 4.0D, 9.5D);

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return EGG_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return EGG_SHAPE;
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, level, pos, oldState, isMoving);
		if (!level.isClientSide) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof EggBlockEntity eggBE) {
				eggBE.resetForNewPlacement(level, hatchSeconds);
			}
			level.scheduleTick(pos, this, hatchSeconds * 20);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		BlockEntity be = level.getBlockEntity(pos);
		if (!(be instanceof EggBlockEntity eggBE)) return;
		if (eggBE.getSecondsRemaining(level) <= 0) {
			super.tick(state, level, pos, random);
		} else {
			level.scheduleTick(pos, this, eggBE.getSecondsRemaining(level) * 20);
			return;
		}
		EntityType<? extends Mob> type = toSpawn.get();
		if (type != null) {
			Mob mob = type.create(level);
			if (mob != null) {
				Vec3 spawn = Vec3.atCenterOf(pos);
				mob.moveTo(spawn.x, spawn.y + 0.1, spawn.z, level.random.nextFloat() * 360F, 0.0F);

				//? if >1.20.1 {
				/*mob.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.TRIGGERED, null);
				 *///?} else {
				mob.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.TRIGGERED, null, null);
				//?}

				if (mob instanceof AgeableMob ageable) {
					ageable.setBaby(true);
				}

				level.addFreshEntity(mob);
			}
		}
		level.levelEvent(2001, pos, Block.getId(state));
		level.removeBlock(pos, false);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!level.isClientSide && state.getBlock() != newState.getBlock()) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof EggBlockEntity eggBE) {
				eggBE.invalidateTimer();
			}
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EggBlockEntity(pos, state);
	}

	//? if >1.20.1 {
    /*@Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTootipComponents, TooltipFlag pTooltipFlag) {
        pTootipComponents.add(Component.translatable("tooltip.jurassicrevived.egg.hatches_in", hatchSeconds));
        super.appendHoverText(pStack, pContext, pTootipComponents, pTooltipFlag);
    }
    *///?} else {
	@Override
	public void appendHoverText(ItemStack pStack, @Nullable BlockGetter level, List<Component> pTootipComponents, TooltipFlag pTooltipFlag) {
		pTootipComponents.add(Component.translatable("tooltip.jurassicrevived.egg.hatches_in", hatchSeconds));
		super.appendHoverText(pStack, level, pTootipComponents, pTooltipFlag);
	}
	//?}
}