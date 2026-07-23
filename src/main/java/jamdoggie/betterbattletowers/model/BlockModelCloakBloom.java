package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import net.minecraft.core.world.season.SeasonManager;
import org.jetbrains.annotations.NotNull;
import org.joml.primitives.AABBdc;

public class BlockModelCloakBloom<T extends BlockLogic> extends BlockModelCloaked<T>{
	IconCoordinate bloom;

	public BlockModelCloakBloom(Block<T> block, String coordinates, String bloom) {
		super(block, coordinates);
		this.bloom = TextureRegistry.getTexture(bloom);
	}

	@Override
	public boolean render(@NotNull TessellatorGeneral tessellator, @NotNull WorldSource worldSource, @NotNull TilePosc tilePos) {
		super.render(tessellator, worldSource, tilePos);
		AABBdc bounds = this.block.getBoundsFromState(worldSource, tilePos);
		SeasonManager seasonManager = worldSource.getSeasonManager();
		if(seasonManager.getCurrentSeason().growFlowers){
			renderBlocks.overrideBlockTexture = this.bloom;
			renderBlocks.renderStandardBlock(tessellator, worldSource, this, bounds, tilePos, 1.0F, 1.0F, 1.0F);
			renderBlocks.overrideBlockTexture = null;
		}
		return true;
	}

	@Override
	public void renderStandalone(@NotNull TessellatorGeneral tessellator, int metadata, byte lightIndex) {
		renderBlocks.useInventoryTint = true;
		super.renderStandalone(tessellator, metadata, lightIndex);
		renderBlocks.useInventoryTint = false;
		GLRenderer.pushFrame();
		GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GLRenderer.modelM4f().translate(-0.5F, -0.5F, -0.5F);
		AABBdc bounds = this.block.getBounds();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderBlocks.renderBottomFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderBlocks.renderTopFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderBlocks.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderBlocks.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderBlocks.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderBlocks.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.bloom);
		tessellator.draw();
		GLRenderer.modelM4f().translate(0.5F, 0.5F, 0.5F);
		GLRenderer.popFrame();
	}
}
