package net.cmr.jurassicrevived.block.custom;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.custom.GeneratorBlockEntity;
import net.cmr.jurassicrevived.block.entity.custom.PipeBlockEntity;
import net.cmr.jurassicrevived.block.entity.custom.PowerCellBlockEntity;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyUtil;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//? if >1.20.1 {
/*import net.minecraft.world.ItemInteractionResult;
 *///?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PipeBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

	public enum Transport {
		ITEMS, FLUIDS, ENERGY
	}

	public enum ConnectionType implements StringRepresentable {
		NONE, PIPE, CONNECTOR, CONNECTOR_PULL;

		@Override
		public String getSerializedName() {
			return switch (this) {
				case NONE -> "none";
				case PIPE -> "pipe";
				case CONNECTOR -> "connector";
				case CONNECTOR_PULL -> "connector_pull";
			};
		}
	}

	public static final EnumProperty<ConnectionType> DOWN = EnumProperty.create("down", ConnectionType.class);
	public static final EnumProperty<ConnectionType> UP = EnumProperty.create("up", ConnectionType.class);
	public static final EnumProperty<ConnectionType> NORTH = EnumProperty.create("north", ConnectionType.class);
	public static final EnumProperty<ConnectionType> SOUTH = EnumProperty.create("south", ConnectionType.class);
	public static final EnumProperty<ConnectionType> WEST = EnumProperty.create("west", ConnectionType.class);
	public static final EnumProperty<ConnectionType> EAST = EnumProperty.create("east", ConnectionType.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private final Transport transport;

	private static final VoxelShape CORE = box(6, 6, 6, 10, 10, 10);
	private static final VoxelShape ARM_DOWN = box(6, 0, 6, 10, 6, 10);
	private static final VoxelShape ARM_UP = box(6, 10, 6, 10, 16, 10);
	private static final VoxelShape ARM_NORTH = box(6, 6, 0, 10, 10, 6);
	private static final VoxelShape ARM_SOUTH = box(6, 6, 10, 10, 10, 16);
	private static final VoxelShape ARM_WEST = box(0, 6, 6, 6, 10, 10);
	private static final VoxelShape ARM_EAST = box(10, 6, 6, 16, 10, 10);
	private static final VoxelShape CAP_DOWN = box(6, 0, 6, 10, 1, 10);
	private static final VoxelShape CAP_UP = box(6, 15, 6, 10, 16, 10);
	private static final VoxelShape CAP_NORTH = box(6, 6, 0, 10, 10, 1);
	private static final VoxelShape CAP_SOUTH = box(6, 6, 15, 10, 10, 16);
	private static final VoxelShape CAP_WEST = box(0, 6, 6, 1, 10, 10);
	private static final VoxelShape CAP_EAST = box(15, 6, 6, 16, 10, 10);

	private static final VoxelShape[] SHAPES = new VoxelShape[64];

	static {
		for (int mask = 0; mask < SHAPES.length; mask++) {
			VoxelShape s = CORE;
			if ((mask & (1 << 0)) != 0) s = Shapes.or(s, ARM_DOWN,  CAP_DOWN);
			if ((mask & (1 << 1)) != 0) s = Shapes.or(s, ARM_UP,    CAP_UP);
			if ((mask & (1 << 2)) != 0) s = Shapes.or(s, ARM_NORTH, CAP_NORTH);
			if ((mask & (1 << 3)) != 0) s = Shapes.or(s, ARM_SOUTH, CAP_SOUTH);
			if ((mask & (1 << 4)) != 0) s = Shapes.or(s, ARM_WEST,  CAP_WEST);
			if ((mask & (1 << 5)) != 0) s = Shapes.or(s, ARM_EAST,  CAP_EAST);
			SHAPES[mask] = s;
		}
	}

	public PipeBlock(Properties properties, Transport transport) {
		super(properties.noOcclusion());
		this.transport = transport;
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(DOWN, ConnectionType.NONE)
				.setValue(UP, ConnectionType.NONE)
				.setValue(NORTH, ConnectionType.NONE)
				.setValue(SOUTH, ConnectionType.NONE)
				.setValue(WEST, ConnectionType.NONE)
				.setValue(EAST, ConnectionType.NONE)
				.setValue(WATERLOGGED, false)
		);
	}

	public Transport getTransport() {
		return transport;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DOWN, UP, NORTH, SOUTH, WEST, EAST, WATERLOGGED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		BlockState state = this.defaultBlockState()
			.setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
		for (Direction dir : Direction.values()) {
			state = setConnectionForDirection(level, pos, state, dir);
		}
		return state;
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return setConnectionForDirection(level, pos, state, direction);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PipeBlockEntity(pos, state);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if (level.isClientSide) return null;
		return (lvl, pos, st, be) -> {
			if (be instanceof PipeBlockEntity pbe) {
				PipeBlockEntity.serverTick(lvl, pos, st, pbe);
			}
		};
	}

	private BlockState setConnectionForDirection(LevelAccessor level, BlockPos pos, BlockState state, Direction dir) {
		EnumProperty<ConnectionType> prop = getProp(dir);
		ConnectionType existing = state.getValue(prop);
		ConnectionType connection = determineConnection(level, pos, dir, existing);
		return state.setValue(prop, connection);
	}

	private ConnectionType determineConnection(LevelAccessor level, BlockPos pos, Direction dir, ConnectionType existing) {
		BlockPos neighborPos = pos.relative(dir);
		BlockState neighborState = level.getBlockState(neighborPos);
		Block neighbor = neighborState.getBlock();

		if (neighbor instanceof PipeBlock otherPipe) {
			if (otherPipe.transport == this.transport) {
				return ConnectionType.PIPE;
			}
		}

		BlockEntity be = level.getBlockEntity(neighborPos);
		boolean hasCommonConnection = false;

		if (be != null) {
			hasCommonConnection = switch (this.transport) {
				case ITEMS -> be instanceof Container;
				case FLUIDS -> false;
				case ENERGY -> be instanceof ModEnergyUtil.EnergyProvider && canEnergyPipeConnectTo(be);
			};
		}

		if (level instanceof Level lvl) {
			boolean platformHasHandler = switch (this.transport) {
				case ITEMS -> Services.TRANSFER.getItemHandler(lvl, neighborPos, dir.getOpposite()).isPresent();
				case FLUIDS -> Services.TRANSFER.getFluidHandler(lvl, neighborPos, dir.getOpposite()).isPresent();
				case ENERGY -> canEnergyPipeConnectTo(be) && Services.TRANSFER.getEnergyHandler(lvl, neighborPos, dir.getOpposite()).isPresent();
			};
			hasCommonConnection = hasCommonConnection || platformHasHandler;
		}

		if (hasCommonConnection) {
			if (existing == ConnectionType.CONNECTOR_PULL) {
				return ConnectionType.CONNECTOR_PULL;
			}
			return ConnectionType.CONNECTOR;
		}

		return ConnectionType.NONE;
	}

	private boolean canEnergyPipeConnectTo(@Nullable BlockEntity be) {
		if (JRConfigManager.get().requirePower) {
			return true;
		}

		return !(be instanceof ModEnergyUtil.EnergyProvider)
			|| be instanceof GeneratorBlockEntity
			|| be instanceof PowerCellBlockEntity
			|| be instanceof PipeBlockEntity;
	}

	public static EnumProperty<ConnectionType> getProp(Direction dir) {
		return switch (dir) {
			case DOWN -> DOWN;
			case UP -> UP;
			case NORTH -> NORTH;
			case SOUTH -> SOUTH;
			case WEST -> WEST;
			case EAST -> EAST;
		};
	}

	//? if >1.20.1 {
	/*@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (stack.is(ModItems.WRENCH.get())) {
			BlockState newState = toggleConnection(state, hit, pos);
			if (newState == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

			if (!level.isClientSide) {
				level.setBlock(pos, newState, Block.UPDATE_ALL);
				level.sendBlockUpdated(pos, state, newState, Block.UPDATE_ALL);
			}
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}
	*///?} else {
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(ModItems.WRENCH.get())) {
			BlockState newState = toggleConnection(state, hit, pos);
			if (newState == null) return InteractionResult.PASS;

			if (!level.isClientSide) {
				level.setBlock(pos, newState, Block.UPDATE_ALL);
				level.sendBlockUpdated(pos, state, newState, Block.UPDATE_ALL);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.use(state, level, pos, player, hand, hit);
	}
	//?}

	private @Nullable BlockState toggleConnection(BlockState state, BlockHitResult hit, BlockPos pos) {
		Direction target = getInteractionFace(state, hit, pos);
		if (target == null) return null;

		EnumProperty<ConnectionType> prop = getProp(target);
		ConnectionType val = state.getValue(prop);

		if (val == ConnectionType.CONNECTOR) {
			return state.setValue(prop, ConnectionType.CONNECTOR_PULL);
		} else if (val == ConnectionType.CONNECTOR_PULL) {
			return state.setValue(prop, ConnectionType.CONNECTOR);
		} else {
			Direction nearest = nearestConnectedDirection(state, hit, pos);
			if (nearest == null) return null;
			prop = getProp(nearest);
			val = state.getValue(prop);
			if (val == ConnectionType.CONNECTOR) {
				return state.setValue(prop, ConnectionType.CONNECTOR_PULL);
			} else if (val == ConnectionType.CONNECTOR_PULL) {
				return state.setValue(prop, ConnectionType.CONNECTOR);
			}
		}
		return null;
	}

	private Direction getInteractionFace(BlockState state, BlockHitResult hit, BlockPos pos) {
		Vec3 local = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
		double x = Mth.clamp(local.x * 16.0, 0.0, 16.0);
		double y = Mth.clamp(local.y * 16.0, 0.0, 16.0);
		double z = Mth.clamp(local.z * 16.0, 0.0, 16.0);

		if (intersects(CAP_UP, x, y, z)     || intersects(ARM_UP, x, y, z))     { if (state.getValue(UP) != ConnectionType.NONE) return Direction.UP; }
		if (intersects(CAP_DOWN, x, y, z)   || intersects(ARM_DOWN, x, y, z))   { if (state.getValue(DOWN) != ConnectionType.NONE) return Direction.DOWN; }
		if (intersects(CAP_NORTH, x, y, z)  || intersects(ARM_NORTH, x, y, z))  { if (state.getValue(NORTH) != ConnectionType.NONE) return Direction.NORTH; }
		if (intersects(CAP_SOUTH, x, y, z)  || intersects(ARM_SOUTH, x, y, z))  { if (state.getValue(SOUTH) != ConnectionType.NONE) return Direction.SOUTH; }
		if (intersects(CAP_WEST, x, y, z)   || intersects(ARM_WEST, x, y, z))   { if (state.getValue(WEST) != ConnectionType.NONE) return Direction.WEST; }
		if (intersects(CAP_EAST, x, y, z)   || intersects(ARM_EAST, x, y, z))   { if (state.getValue(EAST) != ConnectionType.NONE) return Direction.EAST; }

		return nearestConnectedDirection(state, x, y, z);
	}

	private static Direction nearestConnectedDirection(BlockState state, BlockHitResult hit, BlockPos pos) {
		Vec3 local = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
		double x = Mth.clamp(local.x * 16.0, 0.0, 16.0);
		double y = Mth.clamp(local.y * 16.0, 0.0, 16.0);
		double z = Mth.clamp(local.z * 16.0, 0.0, 16.0);
		return nearestConnectedDirection(state, x, y, z);
	}

	private static Direction nearestConnectedDirection(BlockState state, double x, double y, double z) {
		Direction best = null;
		double bestScore = Double.NEGATIVE_INFINITY;

		for (Direction d : Direction.values()) {
			ConnectionType t = state.getValue(getProp(d));
			if (t == ConnectionType.NONE) continue;

			double score = switch (d) {
				case UP    -> y;
				case DOWN  -> 16 - y;
				case NORTH -> 16 - z;
				case SOUTH -> z;
				case WEST  -> 16 - x;
				case EAST  -> x;
			};
			if (score > bestScore) {
				bestScore = score;
				best = d;
			}
		}
		return best;
	}

	private static boolean intersects(VoxelShape shape, double x, double y, double z) {
		var it = shape.toAabbs().iterator();
		if (!it.hasNext()) return false;
		var bb = it.next();
		return x >= bb.minX && x <= bb.maxX &&
			   y >= bb.minY && y <= bb.maxY &&
			   z >= bb.minZ && z <= bb.maxZ;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		int mask = 0;
		if (state.getValue(DOWN)  != ConnectionType.NONE) mask |= (1 << 0);
		if (state.getValue(UP)    != ConnectionType.NONE) mask |= (1 << 1);
		if (state.getValue(NORTH) != ConnectionType.NONE) mask |= (1 << 2);
		if (state.getValue(SOUTH) != ConnectionType.NONE) mask |= (1 << 3);
		if (state.getValue(WEST)  != ConnectionType.NONE) mask |= (1 << 4);
		if (state.getValue(EAST)  != ConnectionType.NONE) mask |= (1 << 5);
		return SHAPES[mask];
	}

	public int getMaxItemsPerTick() {
		return Math.max(1, JRConfigManager.get().itemsPerSecond / 20);
	}

	public int getMaxFluidPerTick() {
		return Math.max(1, JRConfigManager.get().milliBucketsPerSecond / 20);
	}

	public int getMaxEnergyPerTick() {
		return Math.max(1, JRConfigManager.get().fePerSecond / 20);
	}
}