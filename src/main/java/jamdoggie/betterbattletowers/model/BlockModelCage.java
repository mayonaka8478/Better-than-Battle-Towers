package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;

public class BlockModelCage<T extends BlockLogic> extends BlockModelStandard<T> {
	private final boolean renderInside;

	public BlockModelCage(Block<T> block, boolean renderInside) {
		super(block);
		this.renderInside = renderInside;
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		AABB bounds = this.block.getBounds();
		double offset = 0.0001;
		double oneOffset = 1 - offset;
		bounds.set(offset, offset, offset, oneOffset, oneOffset, oneOffset);
		this.renderStandardBlock(tessellator, bounds, x, y, z);
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(WorldSource blockAccess, AABB bounds, int x, int y, int z, int side) {
		return this.renderInside || blockAccess.getBlockId(x, y, z) != this.block.id();
	}

}
