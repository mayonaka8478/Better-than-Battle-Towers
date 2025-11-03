package jamdoggie.betterbattletowers.entity;

import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

public class TileEntityChestTower extends TileEntityChest {
	@Override
	public boolean canBeCarried(World world, Entity potentialHolder) {
		return false;
	}
}
