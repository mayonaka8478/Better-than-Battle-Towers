package jamdoggie.betterbattletowers.entity;

import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DamageInstance implements Comparable<DamageInstance> {

	int damage;
	DamageType type;

	public static DamageInstance inst(DamageType type, int damage) {
		return new DamageInstance(type, damage);
	}

	public DamageInstance(DamageType type, int damage) {
		this.damage = damage;
		this.type = type;
	}

	public int getDamage() {
		return damage;
	}

	public DamageType getType() {
		return type;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DamageInstance)) return false;
		DamageInstance damageInstance1 = (DamageInstance) object;
		return damage == damageInstance1.damage && Objects.equals(type, damageInstance1.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(damage, type);
	}

	@Override
	public int compareTo(@NotNull DamageInstance that) {
		return this.damage - that.damage;
	}
}
