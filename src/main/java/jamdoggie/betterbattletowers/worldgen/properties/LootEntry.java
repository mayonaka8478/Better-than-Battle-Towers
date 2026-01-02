package jamdoggie.betterbattletowers.worldgen.properties;

import net.minecraft.core.WeightedRandomLootObject;

public class LootEntry {
	protected WeightedRandomLootObject lootObj;
	protected double weight;

	public static LootEntry loot(WeightedRandomLootObject lootObj, double weight){
		return new LootEntry(lootObj, weight);
	}

	LootEntry(WeightedRandomLootObject lootObj, double weight) {
		this.lootObj = lootObj;
		this.weight = Math.abs(weight);
	}

	public WeightedRandomLootObject getLootObj(){
		return this.lootObj;
	}

	public double getWeight(){
		return this.weight;
	}
}
