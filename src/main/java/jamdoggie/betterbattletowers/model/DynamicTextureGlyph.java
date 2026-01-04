package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class DynamicTextureGlyph extends DynamicTexture {
	private byte[] parentArray;
	private int tick = 0;


	public DynamicTextureGlyph(@NotNull IconCoordinate targetTexture) {
		super(targetTexture);
	}

	@Override
	public void postInit() {
		this.initTexture();
		parentArray = new byte[this.targetTexture.width * this.targetTexture.height];
		BufferedImage atlas = this.targetTexture.parentAtlas.atlas;
		for(int x = 0; x < this.targetTexture.width; ++x) {
			for(int y = 0; y < this.targetTexture.height; ++y) {
				int rgba = atlas.getRGB(this.targetTexture.iconX + x, this.targetTexture.iconY + y);
				parentArray[y * this.targetTexture.width + x] = (byte)((rgba >> 24 & 255) == 0 ? 0 : 1);
				putPixel(this.imageData, y * this.targetTexture.width + x, rgba);
			}
		}
	}

	@Override
	public void update() {
		this.tick++;
	}
}
