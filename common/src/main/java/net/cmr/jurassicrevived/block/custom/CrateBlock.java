package net.cmr.jurassicrevived.block.custom;

//? if >1.20.1 {
/*import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.ItemInteractionResult;
*///?}

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.cmr.jurassicrevived.block.entity.custom.CrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import org.jetbrains.annotations.Nullable;

public class CrateBlock extends BaseEntityBlock {
	private final int slots;

	//? if >1.20.1 {
	/*@Override protected MapCodec<? extends BaseEntityBlock> codec() { return null; }
	 *///?}

	public CrateBlock(Properties properties, int slots) {
		super(properties);
		this.slots = slots;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CrateBlockEntity(pos, state, this.slots);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return null;
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
			if (be instanceof CrateBlockEntity fbe) {
				ItemStack stack = new ItemStack(this.asItem());

				if (!fbe.isEmptyForDrop()) {
					//? if >1.20.1 {
					/*CompoundTag tag = fbe.saveWithoutMetadata(level.registryAccess());
					var beTypeKey = level.registryAccess()
						.registryOrThrow(Registries.BLOCK_ENTITY_TYPE)
						.getKey(fbe.getType());
					if (beTypeKey != null) {
						tag.putString("id", beTypeKey.toString());
					}
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

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be instanceof CrateBlockEntity crate) {
			return crate.redstoneSignal();
		}
		return 0;
	}

	@Override
	public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
		super.triggerEvent(state, level, pos, id, param);
		BlockEntity be = level.getBlockEntity(pos);
		return be != null && be.triggerEvent(id, param);
	}

	//? if >1.20.1 {
	/*@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide()) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof CrateBlockEntity crate) {
				MenuRegistry.openExtendedMenu((ServerPlayer) player, crate);
			}
		}
		return ItemInteractionResult.sidedSuccess(level.isClientSide());
	}
	*///?} else {
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide()) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof CrateBlockEntity crate) {
				MenuRegistry.openExtendedMenu((ServerPlayer) player, crate);
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
	//?}
}