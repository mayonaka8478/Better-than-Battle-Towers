package jamdoggie.betterbattletowers.model;

import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockModelCrumblingStone<T extends BlockLogic> extends BlockModelStandard<T> {
	private final IconCoordinate[] vertical = new IconCoordinate[3];
	private final IconCoordinate[] horizontal = new IconCoordinate[3];

	public BlockModelCrumblingStone(Block<T> block, String vertical, String horizontal) {
		super(block);
		this.initCrumblingTex(vertical, this.vertical);
		this.initCrumblingTex(horizontal, this.horizontal);
	}

	public void initCrumblingTex(String rootKey, IconCoordinate[] array) {
		array[0] = TextureRegistry.getTexture(rootKey + "heavy");
		array[1] = TextureRegistry.getTexture(rootKey + "medium");
		array[2] = TextureRegistry.getTexture(rootKey + "light");
	}


	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(@NotNull Side side, int metadata) {
		int texID = BlockLogicCrumbling.getStageFromMetadata(metadata);
		if (texID >= 3) {
			return super.getBlockTextureFromSideAndMetadata(side, metadata);
		}
		if(side.isHorizontal()){
			return this.horizontal[texID];
		}
		return this.vertical[texID];
	}

	@Override
	public @Nullable IconCoordinate getParticleTexture(@NotNull Side side, int metadata) {
		return this.getBlockTextureFromSideAndMetadata(side, metadata);
	}

}
