package net.cmr.jurassicrevived.entity.client;

import com.google.common.collect.Maps;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.entity.custom.LudodactylusEntity;
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

public class LudodactylusModel extends GeoModel<LudodactylusEntity> {

    private static final Map<LudodactylusVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(LudodactylusVariant.class), map -> {
                map.put(LudodactylusVariant.MALE, Constants.rl("textures/entity/ludodactylus.png"));
                map.put(LudodactylusVariant.FEMALE, Constants.rl("textures/entity/ludodactylus_female.png"));
            });

	private static final ResourceLocation PINWIN_LOCATION =
		Constants.rl("textures/entity/ludodactylus_pinwin.png");

    // Model-local "currently applied" offsets; cleared before each entity render
    private float[] appliedYaw = null;
    private float[] appliedRoll = null;

    @Override
    public ResourceLocation getModelResource(LudodactylusEntity animatable) {
        return Constants.rl("geo/ludodactylus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LudodactylusEntity animatable) {
		if (animatable.hasCustomName() && "pinwin".equalsIgnoreCase(animatable.getName().getString())) {
			return PINWIN_LOCATION;
		}

        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(LudodactylusEntity animatable) {
        return Constants.rl("animations/ludodactylus.animation.json");
    }

    @Override
    public void setCustomAnimations(LudodactylusEntity entity, long id, AnimationState<LudodactylusEntity> state) {
        super.setCustomAnimations(entity, id, state);

        String[] tailBones = { "Tail1", "Tail2", "Tail3" };
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
        float baseYaw = (sway * swayGain) * maxYawDeg * deg2rad;
        float baseRoll = -baseYaw * rollFraction;

        float[] weights = { 1.00f, 0.78f, 0.58f };

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
        
        GeoBone head = (GeoBone) getAnimationProcessor().getBone("Neck1");

        if (head != null) {
            var entityData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            float clampedYawDeg = Mth.clamp(entityData.netHeadYaw(), -20.0f, 20.0f);
            head.setRotY(clampedYawDeg * Mth.DEG_TO_RAD);
        }
    }
}
