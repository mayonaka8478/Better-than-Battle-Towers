package jamdoggie.betterbattletowers.mixin.mixins.modelViewer;

import jamdoggie.betterbattletowers.entity.golem.EntityEntryGolem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.modelviewer.ScreenModelViewer;
import net.minecraft.client.gui.modelviewer.categories.ViewerCategoryEntity;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = ViewerCategoryEntity.class, remap = false)
public abstract class ViewerCategoryEntityMixin {
	@Shadow
	public abstract void addEntry(EntityEntry<?> entry);
	@Inject(method = "<init>", at = @At("TAIL"))
	private void injectModelViewer(ScreenModelViewer modelViewer, CallbackInfo ci) {
		addEntry(new EntityEntryGolem());
	}
}
