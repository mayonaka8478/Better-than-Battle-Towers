package jamdoggie.betterbattletowers.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;

public class MobUtil {

    private MobUtil(){}

    public static boolean multiHit(Entity attacker, Entity victim, DamageInstance... instances){
        if(instances == null){
            return false;
        }
        if(instances.length < 2){
            DamageInstance instance = instances[0];
            return victim.hurt(attacker, instance.getDamage(), instance.getType());
        }
        boolean cumulativeAccept = true;
        int cumulativeDamage = 0;
		for (DamageInstance instance : instances) {
			cumulativeDamage += instance.getDamage();
			cumulativeAccept = victim.hurt(attacker, cumulativeDamage, instance.getType());
		}
        return cumulativeAccept;
    }

    public static boolean killMob(Mob mob) {
        return MobUtil.killMob(mob, null);
    }

    public static boolean killMob(Mob mob, Entity attack) {
        mob.setHealthRaw(0);
        mob.playDeathSound();
        mob.onDeath(attack);
        return true;
    }
}
