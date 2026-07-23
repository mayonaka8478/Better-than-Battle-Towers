package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.primitives.AABBdc;
import org.lwjgl.opengl.GL11;

public class BlockModelCloaked<T extends BlockLogic> extends BlockModelStandard<T> {
	protected IconCoordinate nonCloak;
	protected Side[] sides = new Side[]{Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST};

	public BlockModelCloaked(Block<T> block, String coordinates) {
		super(block);
		this.nonCloak = TextureRegistry.getTexture(coordinates);
	}

	@Override
	public boolean render(@NotNull TessellatorGeneral tessellator, @NotNull WorldSource worldSource, @NotNull TilePosc tilePos) {
		AABBdc bounds = this.block.getBoundsFromState(worldSource, tilePos);
		this.renderCloak(tessellator, worldSource, tilePos, bounds);
		renderBlocks.overrideBlockTexture = this.nonCloak;
		this.renderNonCloak(tessellator, worldSource, bounds, tilePos, 1.0F, 1.0F, 1.0F);
		renderBlocks.overrideBlockTexture = null;
		return true;
	}

	private boolean renderCloak(TessellatorGeneral tessellator, @NotNull WorldSource worldSource, @NotNull TilePosc tilePos, AABBdc bounds) {
		int color = (BlockColorDispatcher.getInstance().getDispatch(this.block)).getWorldColor(worldSource, tilePos, 0);
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
		int meta = worldSource.getBlockData(tilePos);

		renderBlocks.enableAO = true;
		renderBlocks.cache.setupCache(this.block,worldSource, tilePos);
		boolean somethingRendered = false;

		for (Side side : Side.sides) {
			somethingRendered |= renderBlocks.renderSide(tessellator, worldSource, this, bounds, tilePos, r, g, b, side, meta);
		}
		renderBlocks.enableAO = false;
		return somethingRendered | this.renderNonCloak(tessellator, worldSource, bounds, tilePos, r, g, b);
	}

	public boolean renderNonCloak(
		TessellatorGeneral tessellator, @NotNull WorldSource worldSource,
		AABBdc bounds, @NotNull TilePosc tilePos,
		float r, float g, float b
	) {
		renderBlocks.enableAO = true;
		int meta = worldSource.getBlockData(tilePos);
		renderBlocks.cache.setupCache(this.block, worldSource, tilePos);
		boolean somethingRendered = false;

		for (Side side : sides) {
			somethingRendered |= renderBlocks.renderSide(tessellator, worldSource, this, bounds, tilePos, r, g, b, side, meta);
		}

		renderBlocks.enableAO = false;
		return somethingRendered;
	}

	@Override
	public void renderStandalone(@NotNull TessellatorGeneral tessellator, int metadata, byte lightIndex) {
		AABBdc bounds = this.block.getBounds();
		renderBlocks.useInventoryTint = true;
		int color = BlockColorDispatcher.getInstance().getDispatch(this.block).getFallbackColor(metadata, 0);
		//TOFO figure out how this now works and if this was needed
		float a = (float) (color >> 24 & 255) / 255.0F;
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		GLRenderer.pushFrame();
		GLRenderer.setLightmapCoord1i(lightIndex);
//		GLRenderer.setColor4f(r * brightness, g * brightness, b * brightness, alpha);
//		GLRenderer.setColor4f(r, g, b, a);
		super.renderStandalone(tessellator, metadata, lightIndex);
		renderBlocks.useInventoryTint = false;
//		GLRenderer.setColor4f(brightness, brightness, brightness, alpha);
		GLRenderer.modelM4f().translate(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderBlocks.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderBlocks.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderBlocks.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderBlocks.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		GLRenderer.modelM4f().translate(0.5F, 0.5F, 0.5F);
		GLRenderer.popFrame();
	}

}
