package jamdoggie.betterbattletowers.worldgen.data.decoration;

import jamdoggie.betterbattletowers.entity.golem.GolemVariants;
import net.minecraft.core.WeightedRandomBag;

import java.util.Random;

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

	public static TowerProperty deco(WeightedRandomBag<BlockData> towerDecorations, double chance, Random random) {
		return new TowerProperty(GolemVariants.getRandomEntry(random), towerDecorations, chance);
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
}
