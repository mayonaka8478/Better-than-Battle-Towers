package jamdoggie.betterbattletowers.entity.render;

import jamdoggie.betterbattletowers.entity.MobGolem;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBiped;
import org.lwjgl.opengl.GL11;

public class RenderGolem extends MobRenderer<MobGolem> {
	public RenderGolem() {
		super(new ModelBiped(), 1.0F);
		setArmorModel(new ModelBiped());
	}

	protected void func_15310_scalegolem(MobGolem entitygolem, float f) {
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	@Override
	protected void setupScale(MobGolem entityliving, float f) {
		func_15310_scalegolem(entityliving, f);
		super.setupScale(entityliving, f);
	}
}
