package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.CeratosaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class CeratosaurusRenderer extends GeoEntityRenderer<CeratosaurusEntity> {
	public CeratosaurusRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new CeratosaurusModel());
	}

	@Override
	public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, CeratosaurusEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
		super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
		float scale = animatable.getDinoScale();
		poseStack.scale(scale, scale, scale);
	}
}
