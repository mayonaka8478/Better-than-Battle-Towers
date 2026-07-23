package jamdoggie.betterbattletowers.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

public class BlockLogicGlyph extends BlockLogicRotatable {
	public BlockLogicGlyph(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public int getPistonPushReaction(@NotNull World world, @NotNull TilePosc tilePos) {
		return Material.PISTON_CANT_PUSH;
	}
}
