package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;

public class BlockModelCage<T extends BlockLogic> extends BlockModelStandard<T> {
	private final boolean renderInside;

	public BlockModelCage(Block<T> block, boolean renderInside) {
		super(block);
		this.renderInside = renderInside;
	}
}
