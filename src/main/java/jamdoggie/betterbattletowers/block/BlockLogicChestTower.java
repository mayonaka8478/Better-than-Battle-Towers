package jamdoggie.betterbattletowers.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.material.Material;

public class BlockLogicChestTower extends BlockLogicChest {

	public BlockLogicChestTower(Block<?> block, Material material) {
		super(block, material);
		block.withEntity(TileEntityChestTower::new);
	}

}
