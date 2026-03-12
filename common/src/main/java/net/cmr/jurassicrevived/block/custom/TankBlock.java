package net.cmr.jurassicrevived.block.custom;

//? if >1.20.1 {
/*import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.ItemInteractionResult;
*///?}

import dev.architectury.registry.menu.MenuRegistry;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.custom.TankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TankBlock extends BaseEntityBlock {
	public TankBlock(Properties properties) {
		super(properties);
	}

	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

	//? if >1.20.1 {
	/*@Override protected MapCodec<? extends BaseEntityBlock> codec() { return null; }
	 *///?}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
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
			if (be instanceof TankBlockEntity fbe) {
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

	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
	}

	//? if >1.20.1 {
	/*@Override
	protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
		if (!pLevel.isClientSide()) {
			BlockEntity entity = pLevel.getBlockEntity(pPos);
			if (entity instanceof TankBlockEntity tankBlockEntity) {
				MenuRegistry.openExtendedMenu((ServerPlayer) pPlayer, tankBlockEntity);
			}
		}
		return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
	}
	*///?} else {
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide()) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof TankBlockEntity tankBlockEntity) {
				MenuRegistry.openExtendedMenu((ServerPlayer) player, tankBlockEntity);
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
	//?}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TankBlockEntity(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		if(pLevel.isClientSide()) {
			return null;
		}

		return createTickerHelper(pBlockEntityType, ModBlockEntities.TANK_BE.get(),
			((level, blockPos, blockState, tankBlockEntity) -> tankBlockEntity.tick(level, blockPos, blockState)));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}