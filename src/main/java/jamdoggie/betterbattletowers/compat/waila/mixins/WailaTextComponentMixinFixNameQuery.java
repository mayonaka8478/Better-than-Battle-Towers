//package jamdoggie.betterbattletowers.compat.waila.mixins;
//
//import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//import net.minecraft.core.entity.Entity;
//import net.minecraft.core.entity.Mob;
//import net.minecraft.core.util.collection.NamespaceID;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import toufoumaster.btwaila.gui.components.WailaTextComponent;
//
//import static toufoumaster.btwaila.BTWaila.translator;
//
//@Mixin(value = WailaTextComponent.class, remap = false)
//public abstract class WailaTextComponentMixinFixNameQuery {
//
//	@WrapOperation(method = "getEntityName", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/collection/NamespaceID;toString()Ljava/lang/String;"))
//	private static String correctStuff(NamespaceID instance, Operation<String> original, Entity entity){
//		if(entity instanceof Mob){
//			return translator.translateKey(Entity.getNameFromEntity(entity, false));
//		}
//		return original.call(instance);
//	}
//}
