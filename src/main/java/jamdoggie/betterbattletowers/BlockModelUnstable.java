package jamdoggie.betterbattletowers;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class BlockModelUnstable<T extends BlockLogic> extends BlockModelStandard<T> {
	private static final IconCoordinate[] TOP = new IconCoordinate[2];
	private static final IconCoordinate[] SIDE = new IconCoordinate[2];

	static {
		String rootKey = MOD_ID + ":block/crumble/";
		TOP[0] = TextureRegistry.getTexture(rootKey + "polished_stone_top_0");
		TOP[1] = TextureRegistry.getTexture(rootKey + "polished_stone_top_1");
		SIDE[0] = TextureRegistry.getTexture(rootKey + "polished_stone_side_0");
		SIDE[1] = TextureRegistry.getTexture(rootKey + "polished_stone_side_1");
	}

	public BlockModelUnstable(Block<T> block) {
		super(block);
	}


	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int metadata) {
		int texID = metadata & 0b0000_0011;
		if(texID >= 2){
			return super.getBlockTextureFromSideAndMetadata(side, metadata);
		}
		if(side.isVertical()){
			return TOP[texID];
		}
		return SIDE[texID];
	}
}
