package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.LudodactylusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class LudodactylusRenderer extends GeoEntityRenderer<LudodactylusEntity> {
	public LudodactylusRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new LudodactylusModel());
	}

	@Override
	public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, LudodactylusEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
		float scale = animatable.getTotalModelScale();
		poseStack.scale(scale, scale, scale);
	}
}
