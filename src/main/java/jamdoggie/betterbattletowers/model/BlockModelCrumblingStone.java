package jamdoggie.betterbattletowers.model;

import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;

public class BlockModelCrumblingStone<T extends BlockLogic> extends BlockModelStandard<T> {
	private final IconCoordinate[] topTex = new IconCoordinate[3];
	private final IconCoordinate[] sideTex = new IconCoordinate[3];

	public BlockModelCrumblingStone(Block<T> block, String rootKey) {
		super(block);
		this.initCrumblingTex(rootKey);
	}

	public void initCrumblingTex(String rootKey){
		this.topTex[0] = TextureRegistry.getTexture(rootKey + "heavy");
		this.topTex[1] = TextureRegistry.getTexture(rootKey + "medium");
		this.topTex[2] = TextureRegistry.getTexture(rootKey + "light");

		this.sideTex[0] = TextureRegistry.getTexture(rootKey + "heavy");
		this.sideTex[2] = TextureRegistry.getTexture(rootKey + "light");
		this.sideTex[1] = TextureRegistry.getTexture(rootKey + "medium");
	}

	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int metadata) {
		int texID = BlockLogicCrumbling.getStageFromMetadata(metadata);
		if(texID >= 3){
			return super.getBlockTextureFromSideAndMetadata(side, metadata);
		}
		if(side.isVertical()){
			return this.topTex[texID];
		}
		return this.sideTex[texID];
	}
}
