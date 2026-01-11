package net.cmr.jurassicrevived.entity.client;

import com.google.common.collect.Maps;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.entity.custom.InostranceviaEntity;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
/*? if <=1.20.1 {*/
import software.bernie.geckolib.core.animation.AnimationState;
/*?} else {*/
/*import software.bernie.geckolib.animation.AnimationState;
 *//*?}*/
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class InostranceviaModel extends GeoModel<InostranceviaEntity> {

    private static final Map<InostranceviaVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(InostranceviaVariant.class), map -> {
                map.put(InostranceviaVariant.MALE, Constants.rl("textures/entity/inostrancevia.png"));
                map.put(InostranceviaVariant.FEMALE, Constants.rl("textures/entity/inostrancevia_female.png"));
            });

    // Model-local "currently applied" offsets; cleared before each entity render
    private float[] appliedYaw = null;
    private float[] appliedRoll = null;

    @Override
    public ResourceLocation getModelResource(InostranceviaEntity animatable) {
        return Constants.rl("geo/inostrancevia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InostranceviaEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(InostranceviaEntity animatable) {
        return Constants.rl("animations/inostrancevia.animation.json");
    }

    @Override
    public void setCustomAnimations(InostranceviaEntity entity, long id, AnimationState<InostranceviaEntity> state) {
        super.setCustomAnimations(entity, id, state);

        String[] tailBones = { "tail1", "tail2", "tail3", "tail4", "tail5" };
        int n = tailBones.length;

        if (appliedYaw == null || appliedYaw.length != n) {
            appliedYaw = new float[n];
            appliedRoll = new float[n];
        }

        // 1) Clear previous offsets (from the last entity rendered with this model instance)
        for (int i = 0; i < n; i++) {
            if (appliedYaw[i] == 0.0f && appliedRoll[i] == 0.0f) continue;
            GeoBone bone = (GeoBone) getAnimationProcessor().getBone(tailBones[i]);
            if (bone == null) continue;
            if (appliedYaw[i] != 0.0f) bone.setRotY(bone.getRotY() - appliedYaw[i]);
            if (appliedRoll[i] != 0.0f) bone.setRotZ(bone.getRotZ() - appliedRoll[i]);
            appliedYaw[i] = 0.0f;
            appliedRoll[i] = 0.0f;
        }

        // 2) Interpolated sway for extra smoothness between ticks
        float sway = entity.getTailSwayOffset(state.getPartialTick()); // [-1, 1]

        // Tuning
        float maxYawDeg    = 22.0f;  // increased max sweep
        float swayGain     = 1.35f;  // amplifies overall power
        float rollFraction = 0.40f;  // slightly stronger roll for heft

        float deg2rad = (float)Math.PI / 180f;

        // Direction: positive sway (left turn) -> tail swings right (negative yaw)
        // Flip the sign here if the sway feels inverted
        float baseYaw = sway * maxYawDeg * deg2rad;
        float baseRoll = -baseYaw * rollFraction;

        float[] weights = { 1.00f, 0.78f, 0.58f, 0.42f, 0.30f };

        for (int i = 0; i < n; i++) {
            GeoBone bone = (GeoBone) getAnimationProcessor().getBone(tailBones[i]);
            if (bone == null) continue;

            float w = weights[i];
            float yaw  = baseYaw  * w;
            float roll = baseRoll * w;

            // OVERRIDE animations on Y/Z only: keep the model's predefined X bend intact
            // Do NOT reset rotX here, so the upward bend stays
            bone.setRotY(yaw);
            bone.setRotZ(roll);

            appliedYaw[i] = yaw;
            appliedRoll[i] = roll;
        }
        
        GeoBone head = (GeoBone) getAnimationProcessor().getBone("neck1");

        if (head != null) {
            var entityData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            float clampedYawDeg = Mth.clamp(entityData.netHeadYaw(), -30.0f, 30.0f);
            head.setRotY(clampedYawDeg * Mth.DEG_TO_RAD);
        }
    }
}
