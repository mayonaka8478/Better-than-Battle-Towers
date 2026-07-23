package jamdoggie.betterbattletowers.block;

import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.ICarriable;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityChestTower extends TileEntityChest {

	@Override
	public @Nullable ICarriable pickup(@NotNull World world, @NotNull Entity holder, @NotNull TilePosc tilePos) {
		return null;
	}
}
