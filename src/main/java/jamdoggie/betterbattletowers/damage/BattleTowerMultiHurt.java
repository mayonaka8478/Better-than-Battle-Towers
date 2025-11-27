package jamdoggie.betterbattletowers.damage;

import net.minecraft.core.entity.Entity;

public interface BattleTowerMultiHurt {

	boolean multiHurt(Entity attacker, DamageInstance ... instances);
}
