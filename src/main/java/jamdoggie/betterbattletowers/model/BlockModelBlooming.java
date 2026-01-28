package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.season.SeasonManager;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class BlockModelBlooming<T extends BlockLogic> extends BlockModelStandard<T> {
	IconCoordinate bloom;

	public BlockModelBlooming(Block<T> block, String bloom) {
		super(block);
		this.bloom = TextureRegistry.getTexture(bloom);
	}

	public boolean render(Tessellator tessellator, int x, int y, int z) {
		AABB bounds = this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z);
		this.renderStandardBlock(tessellator, bounds, x, y, z);
		SeasonManager seasonManager = renderBlocks.blockAccess.getSeasonManager();
		if(seasonManager.getCurrentSeason().growFlowers){
			renderBlocks.overrideBlockTexture = this.bloom;
			this.renderStandardBlock(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F);
			renderBlocks.overrideBlockTexture = null;
		}
		return true;
	}

	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		renderBlocks.useInventoryTint = true;
		super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);
		renderBlocks.useInventoryTint = false;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		AABB bounds = this.block.getBounds();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		this.renderBottomFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		this.renderTopFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		this.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		this.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		this.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		this.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
