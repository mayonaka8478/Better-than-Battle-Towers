package jamdoggie.betterbattletowers.entity.golem;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Blocks;
import org.lwjgl.opengl.GL11;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class MobRendererGolem extends MobRenderer<MobGolem> {

	public MobRendererGolem() {
		super(new ModelBiped(), 1.0F);
		setArmorModel(new ModelBiped(0.01F));
	}

	@Override
	protected void setupScale(MobGolem entityliving, float f) {
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		super.setupScale(entityliving, f);
	}

	@Override
	protected boolean prepareArmor(MobGolem golem, int renderPass, float partialTick) {
		if (renderPass == 0 && !golem.isDormant()) {
			this.bindTexture(String.format("/assets/%s/textures/entity/golem/%s/eyes/%s.png", MOD_ID, golem.getEntityData().getString(3), golem.getTextureReference()));
			enableFullBright();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return true;
		}
		if(renderPass == 1 && golem.getEntityData().getString(3).equalsIgnoreCase("overgrown")){
			disableFullBrigth(golem, partialTick);
			this.bindTexture(String.format("/assets/%s/textures/entity/golem/%s/leaves.png", MOD_ID, golem.getEntityData().getString(3)));
			int color = BlockColorDispatcher.getInstance().getDispatch(Blocks.GRASS).getWorldColor(golem.world, (int)Math.floor(golem.x), (int)Math.floor(golem.y), (int)Math.floor(golem.z));
			float r = (color >> 16 & 255) / 255.0F;
			float g = (color >> 8 & 255) / 255.0F;
			float b = (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
			return true;
		}
		return false;
	}

	private static void enableFullBright() {
		if (LightmapHelper.isLightmapEnabled()) {
			LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
		}
	}

	private static void disableFullBrigth(MobGolem golem, float partialTick) {
		if (LightmapHelper.isLightmapEnabled()) {
			LightmapHelper.setLightmapCoord(golem.getLightmapCoord(partialTick));
		}
	}

	@Override
	public void renderPreview(Tessellator tessellator, MobGolem golem, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		super.renderPreview(tessellator, golem, x, y - 2.0, z, yaw, partialTick);
		GL11.glPopMatrix();
	}
}
