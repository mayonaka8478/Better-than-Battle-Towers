package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class BlockModelCloaked<T extends BlockLogic> extends BlockModelStandard<T> {
	protected IconCoordinate nonCloak;
	protected Side[] sides = new Side[]{Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST};

	public BlockModelCloaked(Block<T> block, String coordinates) {
		super(block);
		this.nonCloak = TextureRegistry.getTexture(coordinates);
	}

	public boolean render(Tessellator tessellator, int x, int y, int z) {
		AABB bounds = this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z);
		this.renderCloak(tessellator, x, y, z, bounds);
		renderBlocks.overrideBlockTexture = this.nonCloak;
		this.renderNonCloak(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F);
		renderBlocks.overrideBlockTexture = null;
		return true;
	}

	private boolean renderCloak(Tessellator tessellator, int x, int y, int z, AABB bounds) {
		int color = (BlockColorDispatcher.getInstance().getDispatch(this.block)).getWorldColor(renderBlocks.blockAccess, x, y, z);
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
		int meta = renderBlocks.blockAccess.getBlockMetadata(x, y, z);

		renderBlocks.enableAO = true;
		renderBlocks.cache.setupCache(this.block, renderBlocks.blockAccess, x, y, z);
		boolean somethingRendered = false;

		for (Side side : Side.sides) {
			somethingRendered |= renderBlocks.renderSide(tessellator, this, bounds, x, y, z, r, g, b, side, meta);
		}
		renderBlocks.enableAO = false;
		return somethingRendered | this.renderNonCloak(tessellator, bounds, x, y, z, r, g, b);
	}

	public boolean renderNonCloak(
		Tessellator tessellator,
		AABB bounds, int x, int y, int z,
		float r, float g, float b
	) {
		renderBlocks.enableAO = true;
		int meta = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
		renderBlocks.cache.setupCache(this.block, renderBlocks.blockAccess, x, y, z);
		boolean somethingRendered = false;

		for (Side side : sides) {
			somethingRendered |= renderBlocks.renderSide(tessellator, this, bounds, x, y, z, r, g, b, side, meta);
		}

		renderBlocks.enableAO = false;
		return somethingRendered;
	}

	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		AABB bounds = this.block.getBounds();
		renderBlocks.useInventoryTint = true;
		int color = BlockColorDispatcher.getInstance().getDispatch(this.block).getFallbackColor(metadata);
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
		super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);
		renderBlocks.useInventoryTint = false;
		GL11.glColor4f(brightness, brightness, brightness, alpha);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		this.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		this.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		this.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		this.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.nonCloak);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

}
