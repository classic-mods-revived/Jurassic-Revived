package net.cmr.jurassicrevived.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class FabricEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider implements ModEntityTagProvider.EntityTagHelper {

	public FabricEntityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		ModEntityTagProvider.registerEntityTags(this);
	}

	@Override
	public void tag(TagKey<EntityType<?>> tag, EntityType<?>... entityTypes) {
		getOrCreateTagBuilder(tag).add(entityTypes);
	}
}
