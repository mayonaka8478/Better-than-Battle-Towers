package jamdoggie.betterbattletowers.entity;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBiped;
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
			this.bindTexture(String.format("/assets/%s/textures/entity/golem/eyes/%s.png", MOD_ID, golem.getTextureReference()));
			if (LightmapHelper.isLightmapEnabled()) {
				LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
			}
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return true;
		}
		return false;
	}
}
