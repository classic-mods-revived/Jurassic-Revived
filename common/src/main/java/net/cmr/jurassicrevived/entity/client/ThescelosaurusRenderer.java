package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.ThescelosaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class ThescelosaurusRenderer extends GeoEntityRenderer<ThescelosaurusEntity> {
    public ThescelosaurusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThescelosaurusModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, ThescelosaurusEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scale = animatable.getTotalModelScale();
        poseStack.scale(scale, scale, scale);
    }
}
