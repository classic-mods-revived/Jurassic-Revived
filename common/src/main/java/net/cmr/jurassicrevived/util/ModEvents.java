package net.cmr.jurassicrevived.util;

import dev.architectury.event.events.common.EntityEvent;
import net.cmr.jurassicrevived.entity.ai.DinoEntityBase;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ModEvents {
    public static void init() {
        EntityEvent.LIVING_DEATH.register((entity, source) -> {
            // Check if the killer is our Dino
            if (source.getEntity() instanceof DinoEntityBase attacker && attacker.isCarnivore()) {
                
                // 1. Feed the Dino
                if (attacker.getDinoData() != null) {
                    float amount = attacker.getHungerReplenishment(entity);
                    attacker.getDinoData().modifyHunger(amount);
                }

                // 2. Clear Drops Logic
                // Note: Clearing drops directly in LIVING_DEATH is tricky cross-platform.
                // It is better to use a Mixin to 'dropCustomDeathLoot' or 
                // handle it via a common LootCondition.
            }
            return dev.architectury.event.EventResult.pass();
        });
    }
}
