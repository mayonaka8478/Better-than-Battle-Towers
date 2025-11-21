package jamdoggie.betterbattletowers.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import jamdoggie.betterbattletowers.entity.MobGolem;
import net.minecraft.client.gui.guidebook.mobs.GuidebookPageMob;
import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuidebookPageMob.class, remap = false)
public abstract class GuidebookPageMobMixin {


	@Shadow
	@Final
	private Mob example;

	@Inject(method = "renderMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Lighting;enableLight()V"))
	private void offset(int x, int y, int mouseX, int mouseY, float partialTicks, CallbackInfo ci, @Local(ordinal = 1) float heightFactor, @Local(ordinal = 4) LocalFloatRef f6) {
		//Guidebook entity mouse tracking fix
//		if (this.example instanceof MobGolem) {
//			f6.set(f6.get() - 2 * heightFactor);
//		}
	}
}
