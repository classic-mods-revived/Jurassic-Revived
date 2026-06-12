package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.MoganopterusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class MoganopterusRenderer extends GeoEntityRenderer<MoganopterusEntity> {
    public MoganopterusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MoganopterusModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, MoganopterusEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
		super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
		float scale = animatable.getDinoScale();
        poseStack.scale(scale, scale, scale);
    }
}
