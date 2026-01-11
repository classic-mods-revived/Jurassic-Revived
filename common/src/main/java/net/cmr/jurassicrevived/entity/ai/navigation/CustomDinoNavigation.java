package net.cmr.jurassicrevived.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class CustomDinoNavigation extends GroundPathNavigation {

    public CustomDinoNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new LargeEntityNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    // Fix for large entities getting stuck: Ensure we don't try to path to the exact center of a block if our hitbox is huge
    @Override
    protected Vec3 getTempMobPos() {
        return this.mob.position();
    }
}
