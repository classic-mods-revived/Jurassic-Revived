package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.TitanosaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class TitanosaurusRenderer extends GeoEntityRenderer<TitanosaurusEntity> {
    public TitanosaurusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TitanosaurusModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, TitanosaurusEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
		float scale = animatable.getTotalModelScale();
		poseStack.scale(scale, scale, scale);
	}
}
