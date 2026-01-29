package jamdoggie.betterbattletowers.worldgen.data.decoration;

import net.minecraft.core.WeightedRandomBag;

import java.util.Objects;

public class TowerProperty {
	String golemType;
	WeightedRandomBag<BlockData> towerDecorations;
	double chance;

	public TowerProperty(String golemType, WeightedRandomBag<BlockData> towerDecorations, double chance) {
		this.towerDecorations = towerDecorations;
		this.golemType = golemType;
		this.chance = chance;
	}

	private TowerProperty(String golemType, double chance) {
		this.golemType = golemType;
		this.chance = chance;
		this.towerDecorations = new WeightedRandomBag<>();
	}

	public static TowerProperty deco(String golemType, WeightedRandomBag<BlockData> towerDecorations, double chance) {
		return new TowerProperty(golemType, towerDecorations, chance);
	}

	public WeightedRandomBag<BlockData> getTowerDecorations() {
		return this.towerDecorations;
	}

	public String getGolemType() {
		return this.golemType;
	}

	public double getChance() {
		return chance;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TowerProperty)) return false;
		TowerProperty that = (TowerProperty) object;
		return Double.compare(chance, that.chance) == 0
			&& Objects.equals(golemType, that.golemType)
			&& Objects.equals(towerDecorations, that.towerDecorations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(golemType, towerDecorations, chance);
	}
}
