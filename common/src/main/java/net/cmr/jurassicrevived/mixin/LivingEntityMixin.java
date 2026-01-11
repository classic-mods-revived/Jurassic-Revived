package net.cmr.jurassicrevived.mixin;

import net.cmr.jurassicrevived.entity.ai.DinoEntityBase;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	/*? if >1.20.1 {*/
	/*@Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
	private void jurassicrevived$preventDropsFromDinoKill(ServerLevel level, DamageSource damageSource, CallbackInfo ci) {
		jurassicrevived$handleDinoKill(damageSource, ci);
	}
	*//*?} else {*/
	@Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
	private void jurassicrevived$preventDropsFromDinoKill(DamageSource damageSource, CallbackInfo ci) {
		jurassicrevived$handleDinoKill(damageSource, ci);
	}
	/*?}*/

	private void jurassicrevived$handleDinoKill(DamageSource damageSource, CallbackInfo ci) {
		LivingEntity victim = (LivingEntity) (Object) this;
		if (damageSource.getEntity() instanceof DinoEntityBase attacker && attacker.isCarnivore()) {
			// If it's not a player, cancel the loot drop (Dino ate the body)
			if (!(victim instanceof Player)) {
				ci.cancel();
			}
		}
	}
}