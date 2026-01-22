package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;

public class BlockModelOnTopOverlay<T extends BlockLogic> extends BlockModelStandard<T> {
	protected IconCoordinate overlay;

	public BlockModelOnTopOverlay(Block<T> block, String coordinates) {
		super(block);
		this.overlay = TextureRegistry.getTexture(coordinates);
	}


	public boolean render(Tessellator tessellator, int x, int y, int z) {
		AABB bounds = this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z);
		this.renderStandardBlock(tessellator, bounds, x, y, z);
		renderBlocks.overrideBlockTexture = this.overlay;
		this.renderStandardBlock(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F);
		renderBlocks.overrideBlockTexture = null;
		return true;
	}
}
