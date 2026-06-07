package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.OviraptorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class OviraptorRenderer extends GeoEntityRenderer<OviraptorEntity> {
	public OviraptorRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OviraptorModel());
	}

	@Override
	public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, OviraptorEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
		float scale = animatable.getTotalModelScale();
		poseStack.scale(scale, scale, scale);
	}
}
