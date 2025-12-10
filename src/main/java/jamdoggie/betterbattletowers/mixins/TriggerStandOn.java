package jamdoggie.betterbattletowers.mixins;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

public interface TriggerStandOn {
	default void onEntityStandOn(World world, int x, int y, int z, Entity entity) {}
}
