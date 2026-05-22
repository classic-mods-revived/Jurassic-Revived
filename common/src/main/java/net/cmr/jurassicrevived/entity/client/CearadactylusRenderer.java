package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.CearadactylusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class CearadactylusRenderer extends GeoEntityRenderer<CearadactylusEntity> {
    private final float animalScale = 0.9F;
    public CearadactylusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CearadactylusModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, CearadactylusEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        poseStack.scale(animalScale, animalScale, animalScale);
        if(animatable.isBaby()) {
            float growthProgress = Mth.clamp((24000.0F + animatable.getSyncedAge()) / 24000.0F, 0.0F, 1.0F);
            float scale = Mth.lerp(growthProgress, 0.2F, 1.0F);
            poseStack.scale(scale, scale, scale);
        }
    }
}
