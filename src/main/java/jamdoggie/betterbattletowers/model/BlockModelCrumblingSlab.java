package jamdoggie.betterbattletowers.model;

import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSlab;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockModelCrumblingSlab<T extends BlockLogicSlab> extends BlockModelStandard<T> {
	protected BlockModel<?> parentModel;
	private final IconCoordinate[] fullBlock = new IconCoordinate[3];

	public BlockModelCrumblingSlab(Block<T> block, String rootKey) {
		super(block);
		this.parentModel = BlockModelDispatcher.getInstance().getDispatch(this.block.getLogic().modelBlock);
		this.withCustomItemBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		this.initCrumblingTexFullBlock(rootKey);
	}

	public void initCrumblingTexFullBlock(String rootKey) {
		this.fullBlock[0] = TextureRegistry.getTexture(rootKey + "heavy");
		this.fullBlock[1] = TextureRegistry.getTexture(rootKey + "medium");
		this.fullBlock[2] = TextureRegistry.getTexture(rootKey + "light");
	}

	@Override
	public @Nullable IconCoordinate getBlockTextureFromSideAndMetadata(@NotNull Side side, int data) {
		if ((data & 0b11) != 1) {
			return this.parentModel.getParticleTexture(side, data);
		}
		int stage = BlockLogicCrumbling.getStageFromMetadata(data);
		if(stage >= 3 && side.isHorizontal()){
			return this.blockTextures.get(side);
		}
		if (stage < 3 && side.isHorizontal()) {
			return this.fullBlock[stage];
		}
		return this.parentModel.getParticleTexture(side, data);
	}

	@Override
	public @Nullable IconCoordinate getBlockTexture(@NotNull WorldSource source, @NotNull TilePosc tilePos, @NotNull Side side) {
		return this.getBlockTextureFromSideAndMetadata(side, source.getBlockData(tilePos));
	}

	@Override
	public int renderLayer() {
		return this.parentModel.renderLayer();
	}
}
