package net.cmr.jurassicrevived.block.custom;

import net.cmr.jurassicrevived.block.entity.custom.EggBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class IncubatedEggBlock extends Block implements EntityBlock, SimpleWaterloggedBlock
{

	private final Supplier<? extends EntityType<? extends Mob>> toSpawn;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private final int hatchSeconds = 60;

	public IncubatedEggBlock(Properties pProperties, Supplier<? extends EntityType<? extends Mob>> toSpawn) {
		super(pProperties);
		this.toSpawn = toSpawn;
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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