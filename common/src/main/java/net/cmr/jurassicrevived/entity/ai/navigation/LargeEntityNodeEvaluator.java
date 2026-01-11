package net.cmr.jurassicrevived.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.state.BlockState;
/*? if <=1.20.1 {*/
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*?} else {*/
/*import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
*//*?}*/
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class LargeEntityNodeEvaluator extends WalkNodeEvaluator {
	/*? if <=1.20.1 {*/
	public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
		Mob entity = this.mob;
		BlockPos pos = new BlockPos(x, y, z);
		BlockState state = level.getBlockState(pos);

		if (entity != null && entity.getBbWidth() > 1.5f && state.is(BlockTags.LEAVES)) {
			return BlockPathTypes.OPEN;
		}
	/*?} else {*/
    /*@Override
    public PathType getPathType(PathfindingContext context, int x, int y, int z) {
        Mob entity = this.mob;
        BlockPos pos = new BlockPos(x, y, z);
        BlockState state = context.level().getBlockState(pos);

        if (entity != null && entity.getBbWidth() > 1.5f && state.is(BlockTags.LEAVES)) {
            return PathType.OPEN;
        }
		*//*?}*/

        if (entity != null && entity.getBbWidth() >= 2.0f) {
        }
		/*? if <=1.20.1 {*/
		return super.getBlockPathType(level, x, y, z);
		/*?} else {*/
        /*return super.getPathType(context, x, y, z);
		*//*?}*/
    }
}