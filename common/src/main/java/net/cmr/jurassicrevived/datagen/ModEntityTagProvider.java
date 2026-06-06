package net.cmr.jurassicrevived.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTagProvider {

	public interface EntityTagHelper {
		void tag(TagKey<EntityType<?>> tag, EntityType<?>... entityTypes);
	}

	public static void registerEntityTags(EntityTagHelper helper) {
		ModEntities.ENTITIES.forEach(entitySupplier -> {
			EntityType<?> entityType = entitySupplier.get();
			ResourceLocation id = EntityType.getKey(entityType);

			if (id == null || id.equals(EntityType.getKey(ModEntities.SEAT.get()))) {
				return;
			}

			String path = id.getPath();
			helper.tag(ModTags.EntityTypes.forgeDino(path), entityType);
			helper.tag(ModTags.EntityTypes.neoforgeDino(path), entityType);
			helper.tag(ModTags.EntityTypes.fabricDino(path), entityType);
		});
	}
}
