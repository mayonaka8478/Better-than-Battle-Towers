package jamdoggie.betterbattletowers.entity;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.core.block.Blocks.*;
import static net.minecraft.core.block.Blocks.GRANITE;

public class GolemManager {
	private static final Map<Integer, WeightedRandomBag<WeightedRandomLootObject>> biomeToTowerProperties = new HashMap<>();

	public static Builder register(int skinVariant) {
		return new Builder(skinVariant);
	}

	public static WeightedRandomBag<WeightedRandomLootObject> getDropsFromVariant(int skinVariant){
		if(biomeToTowerProperties.containsKey(skinVariant)){
			return biomeToTowerProperties.get(skinVariant);
		}
		return biomeToTowerProperties.get(4);
	}

	public static class Builder {
		private final int skinVariant;

		private Builder(int skinVariant) {
			this.skinVariant = skinVariant;
		}

		public Builder addDrop(WeightedRandomLootObject drop, double chance) {
			biomeToTowerProperties.computeIfAbsent(skinVariant, k -> new WeightedRandomBag<>())
				.addEntry(drop, chance);
			return this;
		}
	}

	static {
		register(0)
			.addDrop(new WeightedRandomLootObject(BRICK_SANDSTONE.getDefaultStack(), 0, 4), 50.0)
			.addDrop(new WeightedRandomLootObject(SANDSTONE.getDefaultStack(), 0, 4), 50.0);

		register(1)
			.addDrop(new WeightedRandomLootObject(BRICK_GRANITE.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(GRANITE.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(SLAB_GRANITE_POLISHED.getDefaultStack(), 0, 4), 33.3f);

		register(2)
			.addDrop(new WeightedRandomLootObject(BRICK_LIMESTONE.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(LIMESTONE.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(SLAB_LIMESTONE_POLISHED.getDefaultStack(), 0, 4), 33.3f);

		register(3)
			.addDrop(new WeightedRandomLootObject(BRICK_BASALT.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(BASALT.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(SLAB_BASALT_POLISHED.getDefaultStack(), 0, 4), 33.3f);

		register(4)
			.addDrop(new WeightedRandomLootObject(BRICK_STONE_POLISHED.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(STONE_POLISHED.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(SLAB_BRICK_STONE_POLISHED.getDefaultStack(), 0, 2), 33.3f)
			.addDrop(new WeightedRandomLootObject(SLAB_BRICK_STONE.getDefaultStack(), 0, 2), 33.3f);

		register(5)
			.addDrop(new WeightedRandomLootObject(MOSS_STONE.getDefaultStack(), 0, 5), 33.3f)
			.addDrop(new WeightedRandomLootObject(BRICK_STONE_POLISHED_MOSSY.getDefaultStack(), 0, 2), 33.3f)
			.addDrop(new WeightedRandomLootObject(MOSS_LIMESTONE.getDefaultStack(), 0, 1), 33.3f);

		register(6)
			.addDrop(new WeightedRandomLootObject(BRICK_PERMAFROST.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(PERMAFROST_POLISHED.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(SLAB_PERMAFROST_POLISHED.getDefaultStack(), 0, 4), 33.3f);

		register(7)
			.addDrop(new WeightedRandomLootObject(BRICK_MARBLE.getDefaultStack(), 0, 4), 50.0f)
			.addDrop(new WeightedRandomLootObject(SLAB_BRICK_MARBLE.getDefaultStack(), 0, 8), 50.0f);

		register(8)
			.addDrop(new WeightedRandomLootObject(BRICK_NETHERRACK.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(NETHERRACK_POLISHED.getDefaultStack(), 0, 3), 33.3f)
			.addDrop(new WeightedRandomLootObject(NETHERRACK_CARVED.getDefaultStack(), 0, 4), 33.3f);
	}

}
