package jamdoggie.betterbattletowers.mixin.mixins.accessor;

import net.minecraft.client.entity.ClientSkinVariantList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ClientSkinVariantList.class, remap = false)
public interface SkinVariantAccessor {

	@Invoker(value = "getEntityVariants")
	ClientSkinVariantList.EntityVariants invokeGetEntityVariants(String jsonPath);
}
