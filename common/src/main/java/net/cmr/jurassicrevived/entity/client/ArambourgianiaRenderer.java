package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.ArambourgianiaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class ArambourgianiaRenderer extends GeoEntityRenderer<ArambourgianiaEntity> {
    private final float animalScale = 1.75F;
    public ArambourgianiaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ArambourgianiaModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, ArambourgianiaEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        poseStack.scale(animalScale, animalScale, animalScale);
        if(animatable.isBaby()) {
            float growthProgress = Mth.clamp((24000.0F + animatable.getSyncedAge()) / 24000.0F, 0.0F, 1.0F);
            float scale = Mth.lerp(growthProgress, 0.2F, 1.0F);
            poseStack.scale(scale, scale, scale);
        }
    }
}
