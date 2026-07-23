package jamdoggie.betterbattletowers.entity.golem;

import net.minecraft.client.render.block.color.BlockColorDispatcher;

import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.StaticEntityModel;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class MobRendererGolem extends MobRendererBiped<MobGolem> {
	private final float scale;

	public MobRendererGolem(float shadowSizeUnscaled, float scale) {
		super(shadowSizeUnscaled * scale);
		this.scale = scale;
	}

	@Override
	protected void preRenderTransform(@NotNull MobGolem entity, double x, double y, double z, float yaw, float partialTick) {
		super.preRenderTransform(entity, x, y, z, yaw, partialTick);
		GLRenderer.modelM4f().scale(this.scale, this.scale, this.scale);
	}

	@Override
	protected @Nullable StaticEntityModel getActiveModel(@NotNull MobGolem golem) {
		return null;
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull MobGolem golem, float brightness, float partialTick, int layer) {
		if (layer == 0 && !golem.isDormant()) {
			this.bindTexture(String.format("/assets/%s/textures/entity/golem/%s/eyes/%s.png", MOD_ID, golem.getEntityData().getString(3), golem.getTextureReference()));
			enableFullBright();
			GLRenderer.disableState(State.BLEND);
			GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
			GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return super.getAndSetupModelForLayer(golem, brightness, partialTick, layer);
		}
		if(layer == 1 && golem.getEntityData().getString(3).equalsIgnoreCase("overgrown")){
			disableFullBrigth(golem, partialTick);
			this.bindTexture(String.format("/assets/%s/textures/entity/golem/%s/leaves.png", MOD_ID, golem.getEntityData().getString(3)));
			int color = BlockColorDispatcher.getInstance()
					.getDispatch(Blocks.GRASS)
					.getWorldColor(golem.world, new TilePos((int)Math.floor(golem.x), (int)Math.floor(golem.y), (int)Math.floor(golem.z)), 0);
			float r = (color >> 16 & 255) / 255.0F;
			float g = (color >> 8 & 255) / 255.0F;
			float b = (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
			return super.getAndSetupModelForLayer(golem, brightness, partialTick, layer);
		}
		return null;
	}

	private static void enableFullBright() {
//		if (LightmapHelper.isLightmapEnabled()) {
			GLRenderer.setLightmapCoord2i(15, 15);
//		}
	}

	private static void disableFullBrigth(MobGolem golem, float partialTick) {
//		if (LightmapHelper.isLightmapEnabled()) {
			GLRenderer.setLightmapCoord1i(golem.getLightIndex(partialTick));
//		}
	}

	@Override
	public void renderPreview(@NotNull TessellatorGeneral tessellator, @NotNull MobGolem golem, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		super.renderPreview(tessellator, golem, x, y - 2.0, z, yaw, partialTick);
		GL11.glPopMatrix();
	}
}
