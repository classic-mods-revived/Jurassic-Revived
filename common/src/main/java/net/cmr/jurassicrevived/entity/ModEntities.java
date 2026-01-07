package net.cmr.jurassicrevived.entity;

import dev.architectury.registry.registries.DeferredRegister;
import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES =
		DeferredRegister.create(Constants.MOD_ID, Registries.ENTITY_TYPE);

	// --- Example (Generic Entity) ---
	// You will need your own Entity class (e.g., VelociraptorEntity::new)
    /*
    public static final RegistrySupplier<EntityType<VelociraptorEntity>> VELOCIRAPTOR = ENTITIES.register("velociraptor",
            () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 2.0f)
                    .build(Constants.MOD_ID + ":velociraptor"));
    */

	public static void register() {
		ENTITIES.register();
	}
}