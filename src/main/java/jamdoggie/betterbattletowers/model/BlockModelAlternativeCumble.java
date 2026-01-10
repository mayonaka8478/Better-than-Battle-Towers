package jamdoggie.betterbattletowers.model;

import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.Nullable;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class BlockModelAlternativeCumble<T extends BlockLogic> extends BlockModelStandard<T> {
	private String path = MOD_ID + ":block/crumble/";
	public final IconCoordinate[] OVERLAY = {
		TextureRegistry.getTexture(path + "heavy_overlay"),
		TextureRegistry.getTexture(path + "medium_overlay"),
		TextureRegistry.getTexture(path + "light_overlay"),
	};

	public BlockModelAlternativeCumble(Block<T> block, String rootKey) {
		super(block);
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		boolean rendered = super.render(tessellator, x, y, z);
		WorldSource world = renderBlocks.blockAccess;
		int texID = BlockLogicCrumbling.getStageFromMetadata(world.getBlockMetadata(x, y, z));
		if (texID > 2) {
			return rendered;
		}
		renderBlocks.enableAO = false;
		IconCoordinate tex = OVERLAY[texID];
		this.renderBottomFace(tessellator, block.getBounds(), x + Direction.DOWN.getOffsetX() / 1000F, y + Direction.DOWN.getOffsetY() / 1000F, z + Direction.DOWN.getOffsetZ() / 1000F, tex);
		this.renderTopFace(tessellator, block.getBounds(), x + Direction.UP.getOffsetX() / 1000F, y + Direction.UP.getOffsetY() / 1000F, z + Direction.UP.getOffsetZ() / 1000F, tex);
		this.renderNorthFace(tessellator, block.getBounds(), x + Direction.NORTH.getOffsetX() / 1000F, y + Direction.NORTH.getOffsetY() / 1000F, z + Direction.NORTH.getOffsetZ() / 1000F, tex);
		this.renderSouthFace(tessellator, block.getBounds(), x + Direction.SOUTH.getOffsetX() / 1000F, y + Direction.SOUTH.getOffsetY() / 1000F, z + Direction.SOUTH.getOffsetZ() / 1000F, tex);
		this.renderWestFace(tessellator, block.getBounds(), x + Direction.WEST.getOffsetX() / 1000F, y + Direction.WEST.getOffsetY() / 1000F, z + Direction.WEST.getOffsetZ() / 1000F, tex);
		this.renderEastFace(tessellator, block.getBounds(), x + Direction.EAST.getOffsetX() / 1000F, y + Direction.EAST.getOffsetY() / 1000F, z + Direction.EAST.getOffsetZ() / 1000F, tex);
		return rendered;
	}

	@Override
	public void renderBlockWithBounds(Tessellator tessellator, AABB bounds, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		super.renderBlockWithBounds(tessellator, bounds, metadata, brightness, alpha, lightmapCoordinate);
		int texID = BlockLogicCrumbling.getStageFromMetadata(metadata);
		if (texID > 2) {
			return;
		}
		IconCoordinate tex = OVERLAY[texID];
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, -1.0f, 0.0f);
		renderBottomFace(tessellator, bounds, 0.0, 0.0, 0.0, tex);
		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		renderTopFace(tessellator, bounds, 0.0, 0.0, 0.0, tex);
		tessellator.setNormal(0.0f, 0.0f, -1.0f);
		renderNorthFace(tessellator, bounds, 0.0, 0.0, 0.0, tex);
		tessellator.setNormal(0.0f, 0.0f, 1.0f);
		renderSouthFace(tessellator, bounds, 0.0, 0.0, 0.0, tex);
		tessellator.setNormal(-1.0f, 0.0f, 0.0f);
		renderWestFace(tessellator, bounds, 0.0, 0.0, 0.0, tex);
		tessellator.setNormal(1.0f, 0.0f, 0.0f);
		renderEastFace(tessellator, bounds, 0.0, 0.0, 0.0, tex);
		tessellator.draw();
	}
}
