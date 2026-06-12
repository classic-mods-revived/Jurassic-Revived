package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.TyrannosaurusRexEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class TyrannosaurusRexRenderer extends GeoEntityRenderer<TyrannosaurusRexEntity> {
    public TyrannosaurusRexRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TyrannosaurusRexModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, TyrannosaurusRexEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
		super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
		float scale = animatable.getDinoScale();
        poseStack.scale(scale, scale, scale);
    }
}
