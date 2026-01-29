package jamdoggie.betterbattletowers.mixins.accessor;

import net.minecraft.client.render.block.model.BlockModelTransparent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlockModelTransparent.class, remap = false)
public interface BlockModelTransparentAccessor {
	@Accessor
	boolean isRenderInside();
}
