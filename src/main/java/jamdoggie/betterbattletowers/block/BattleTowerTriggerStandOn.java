package jamdoggie.betterbattletowers.block;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

public interface BattleTowerTriggerStandOn { /* exist to fix a bta bug*/
	default void onEntityStandOn(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Entity walker) {}
}
