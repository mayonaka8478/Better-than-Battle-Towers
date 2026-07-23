package jamdoggie.betterbattletowers.model;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;

public class BlockModelCage<T extends BlockLogic> extends BlockModelStandard<T> {
	private final boolean renderInside;
	public static final AABBd BOUNDS = new AABBd(0.0001, 0.0001, 0.0001, 0.9999, 0.9999, 0.9999);

	public BlockModelCage(Block<T> block, boolean renderInside) {
		super(block);
		this.renderInside = renderInside;
	}

	@Override
	public boolean render(@NotNull TessellatorGeneral tessellator, @NotNull WorldSource worldSource, @NotNull TilePosc tilePos) {
		renderBlocks.renderStandardBlock(tessellator, worldSource, this, BOUNDS, tilePos);
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(@NotNull WorldSource source, @NotNull AABBdc bounds, @NotNull TilePosc tilePos, @NotNull Side side) {
		Block<?> blockType = source.getBlockType(tilePos);
		return this.renderInside || blockType.id() != this.block.id();
	}
}
