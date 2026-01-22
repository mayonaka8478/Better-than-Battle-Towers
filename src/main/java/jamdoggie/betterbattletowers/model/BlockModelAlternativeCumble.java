package jamdoggie.betterbattletowers.model;

import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class BlockModelAlternativeCumble<T extends BlockLogic> extends BlockModelStandard<T> {
	private String path = MOD_ID + ":block/crumble/";
	public final IconCoordinate[] OVERLAY = {
		TextureRegistry.getTexture(path + "heavy_overlay"),
		TextureRegistry.getTexture(path + "medium_overlay"),
		TextureRegistry.getTexture(path + "light_overlay"),
	};

	public BlockModelAlternativeCumble(Block<T> block) {
		super(block);
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		int state = BlockLogicCrumbling.getStageFromMetadata(renderBlocks.blockAccess.getBlockMetadata(x,y,z));
		AABB bounds = this.block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z);
		this.renderStandardBlock(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F);
		if(state < 3){
			renderBlocks.overrideBlockTexture = this.OVERLAY[state];
			this.renderStandardBlock(tessellator, bounds, x, y, z);
			renderBlocks.overrideBlockTexture = null;
		}
		return true;
	}
}
