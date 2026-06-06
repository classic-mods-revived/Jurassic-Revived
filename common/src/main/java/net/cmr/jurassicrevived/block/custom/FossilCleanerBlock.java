package net.cmr.jurassicrevived.block.custom;

//? if >1.20.1 {
/*import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.ItemInteractionResult;
*///?}

import dev.architectury.registry.menu.MenuRegistry;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.custom.FossilCleanerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FossilCleanerBlock extends BaseEntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	//? if >1.20.1 {
	/*public static final MapCodec<FossilCleanerBlock> CODEC = simpleCodec(FossilCleanerBlock::new);
	@Override protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }
	*///?}

	public FossilCleanerBlock(Properties properties) {
		super(properties);
	}

	private static final VoxelShape SHAPE_NORTH = Shapes.box(
		2.0 / 16.0, 0.0 / 16.0, 2.0 / 16.0,
		14.0 / 16.0, 14.0 / 16.0, 15.0 / 16.0
	);

	private static final VoxelShape SHAPE_SOUTH = rotateShapeY(SHAPE_NORTH, 180);
	private static final VoxelShape SHAPE_WEST  = rotateShapeY(SHAPE_NORTH, 270);
	private static final VoxelShape SHAPE_EAST  = rotateShapeY(SHAPE_NORTH, 90);

	private static VoxelShape rotateShapeY(VoxelShape shape, int degrees) {
		double rad = Math.toRadians(((degrees % 360) + 360) % 360);
		int turns = (int) Math.round(rad / (Math.PI / 2));
		turns = ((turns % 4) + 4) % 4;

		VoxelShape current = shape;
		for (int i = 0; i < turns; i++) {
			VoxelShape[] buffer = new VoxelShape[]{Shapes.empty()};
			current.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
				double nMinX = 1.0 - maxZ;
				double nMinZ = minX;
				double nMaxX = 1.0 - minZ;
				double nMaxZ = maxX;
				buffer[0] = Shapes.or(buffer[0], Shapes.box(nMinX, minY, nMinZ, nMaxX, maxY, nMaxZ));
			});
			current = buffer[0];
		}
		return current;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite().getOpposite()).setValue(LIT, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction dir = state.getValue(FACING);
		return switch (dir) {
			case NORTH -> SHAPE_NORTH;
			case SOUTH -> SHAPE_SOUTH;
			case WEST  -> SHAPE_WEST;
			case EAST  -> SHAPE_EAST;
			default    -> SHAPE_NORTH;
		};
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new FossilCleanerBlockEntity(blockPos, blockState);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
		//? if >1.20.1 {
	/*public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
	 *///?} else {
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		//?}
		if (!level.isClientSide) {
			if (player.getAbilities().instabuild) {
				level.removeBlockEntity(pos);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				//? if >1.20.1 {
				/*return state;
				 *///?} else {
				return;
				//?}
			}

			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof FossilCleanerBlockEntity fbe) {
				ItemStack stack = new ItemStack(this.asItem());

				if (!fbe.isEmptyForDrop()) {
					//? if >1.20.1 {
					/*CompoundTag tag = fbe.saveWithoutMetadata(level.registryAccess());
					var beTypeKey = level.registryAccess().registryOrThrow(Registries.BLOCK_ENTITY_TYPE).getKey(fbe.getType());
					if (beTypeKey != null) tag.putString("id", beTypeKey.toString());
					stack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(tag));
					*///?} else {
					CompoundTag tag = fbe.saveWithoutMetadata();
					stack.getOrCreateTagElement("BlockEntityTag").merge(tag);
					//?}
				}

				popResource(level, pos, stack);
				level.removeBlockEntity(pos);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				//? if >1.20.1 {
				/*return state;
				 *///?} else {
				return;
				//?}
			}
		}
		super.playerWillDestroy(level, pos, state, player);
		//? if >1.20.1 {
		/*return state;
		 *///?} else {
		return;
		//?}
	}

	//? if >1.20.1 {
	/*@Override
	protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
		BlockEntity entity = pLevel.getBlockEntity(pPos);
		if (entity instanceof FossilCleanerBlockEntity fossilCleanerBlockEntity) {
			if (!pLevel.isClientSide()) {
				if (pStack.is(Items.WATER_BUCKET)) {
					long currentAmount = fossilCleanerBlockEntity.getFluid().getAmount();
					if (16000 - currentAmount >= 1000) {
						if (!pPlayer.getAbilities().instabuild) {
							pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
						}
						fossilCleanerBlockEntity.getFluidHandler(null).fill(dev.architectury.fluid.FluidStack.create(net.minecraft.world.level.material.Fluids.WATER, 1000), false);
						return ItemInteractionResult.SUCCESS;
					}
				}
				MenuRegistry.openExtendedMenu((ServerPlayer) pPlayer, fossilCleanerBlockEntity);
			}
			return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
		}
		return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
	}
	*///?} else {
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockEntity entity = level.getBlockEntity(pos);
		if (entity instanceof FossilCleanerBlockEntity fossilCleanerBlockEntity) {
			if (!level.isClientSide()) {
				ItemStack stack = player.getItemInHand(hand);
				if (stack.is(Items.WATER_BUCKET)) {
					long currentAmount = fossilCleanerBlockEntity.getFluid().getAmount();
					if (16000 - currentAmount >= 1000) {
						if (!player.getAbilities().instabuild) {
							player.setItemInHand(hand, new ItemStack(Items.BUCKET));
						}
						fossilCleanerBlockEntity.getFluidHandler(null).fill(dev.architectury.fluid.FluidStack.create(net.minecraft.world.level.material.Fluids.WATER, 1000), false);
						return InteractionResult.SUCCESS;
					}
				}
				MenuRegistry.openExtendedMenu((ServerPlayer) player, fossilCleanerBlockEntity);
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
	//?}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (blockEntityType != ModBlockEntities.FOSSIL_CLEANER_BE.get()) return null;

		if (level.isClientSide) {
			return null;
		} else {
			return createTickerHelper(blockEntityType, ModBlockEntities.FOSSIL_CLEANER_BE.get(),
				(level1, blockPos, blockState, dnaExtractorBlockEntity) -> dnaExtractorBlockEntity.tick(level1, blockPos, blockState));
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (!state.getValue(LIT)) {
			return;
		}

		double xPos = (double)pos.getX() + 0.5;
		double yPos = pos.getY();
		double zPos = (double)pos.getZ() + 0.5;

		Direction direction = state.getValue(FACING).getOpposite();
		Direction.Axis axis = direction.getAxis();

		double defaultOffset = random.nextDouble() * 0.6 - 0.3;
		double xOffsets = axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : defaultOffset;
		double yOffset = random.nextDouble() * 6.0 / 8.0;
		double zOffset = axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : defaultOffset;

		level.addParticle(ParticleTypes.SMOKE, xPos + xOffsets, yPos + yOffset, zPos + zOffset, 0.0, 0.0, 0.0);

		BlockEntity be = level.getBlockEntity(pos);
		if(be instanceof FossilCleanerBlockEntity fossilCleanerBlockEntity && !fossilCleanerBlockEntity.itemHandler.getItem(1).isEmpty()) {
			level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, fossilCleanerBlockEntity.itemHandler.getItem(1)),
				xPos + xOffsets, yPos + yOffset, zPos + zOffset, 0.0, 0.0, 0.0);
		}
	}
}