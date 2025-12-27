package jamdoggie.betterbattletowers.entity;

import jamdoggie.betterbattletowers.util.BlockData;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.world.biome.Biome;

import java.util.*;

import static jamdoggie.betterbattletowers.entity.TowerProperties.BlockPallet.pallet;
import static net.minecraft.core.block.Blocks.*;
import static net.minecraft.core.world.biome.Biomes.*;

public class TowerProperties {
	private static final Map<Biome, Set<TowerProperty>> biomeToTowerProperties = new HashMap<>();
	private static final List<TowerProperty> LIST_TOWER_PROPERTIES = new ArrayList<>();
	// normal towers
	public static final TowerProperty DESERT_TOWER;
	public static final TowerProperty DILAPIDATED_DESERT_TOWER;
	public static final TowerProperty GRANITE_TOWER;
	public static final TowerProperty DILAPIDATED_GRANITE_TOWER;
	public static final TowerProperty LIMESTONE_TOWER;
	public static final TowerProperty DILAPIDATED_LIMESTONE_TOWER;
	public static final TowerProperty BASALT_TOWER;
	public static final TowerProperty DILAPIDATED_BASALT_TOWER;
	public static final TowerProperty STONE_TOWER;
	public static final TowerProperty BRICK_STONE_TOWER;
	public static final TowerProperty DILAPIDATED_STONE_TOWER;
	public static final TowerProperty OVERGROWN_TOWER;
	public static final TowerProperty DILAPIDATED_OVERGROWN_TOWER;
	public static final TowerProperty FROZEN_TOWER;
	public static final TowerProperty DILAPIDATED_FROZEN_TOWER;
	public static final TowerProperty SNOW_TOWER;
	// retro world
	public static final TowerProperty RETRO_TOWER;
	// paradise
	public static final TowerProperty PARADISE_TOWER;
	// hell world ann nether
	public static final TowerProperty HELLFIRE_TOWER;
	public static final TowerProperty OBSIDIAN_TOWER;
	public static final TowerProperty SLATE_TOWER;
	public static final TowerProperty DILAPIDATED_NETHERRACK_TOWER;
	public static final TowerProperty SLAVERS_TOWER;

	private TowerProperties() {
	}

	public static class BlockPallet{
		protected final BlockData data;
		protected final float chance;

		public BlockPallet(BlockData data, float chance) {
			this.data = data;
			this.chance = chance;
		}

		public static BlockPallet pallet(int blockID, float chance) {
			return new BlockPallet(new BlockData(blockID), chance);
		}

		public static BlockPallet pallet(BlockData data, float chance) {
			return new BlockPallet(data, chance);
		}
	}

	public static class TowerProperty {
		WeightedRandomBag<BlockData> towerDecorations;
		int skinVariant;
		private TowerProperty(WeightedRandomBag<BlockData> towerDecorations, int skinVariant){
			this.skinVariant = skinVariant;
			this.towerDecorations = towerDecorations;
		}
		public WeightedRandomBag<BlockData> getTowerDecorations() {return towerDecorations;}
		public int getSkinVariant() {return skinVariant;}
	}

	public static TowerProperty register(Set<Biome> biomeList, WeightedRandomBag<BlockData> blockBag, int variant) {
		TowerProperty tower = new TowerProperty(blockBag, variant);
		for (Biome biome : biomeList) {
			biomeToTowerProperties.computeIfAbsent(biome, key -> new HashSet<>()).add(tower);
		}
		LIST_TOWER_PROPERTIES.add(tower);
		return tower;
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	private static <T> Set<T> asSet(T... elements) {
		return new HashSet<>(Arrays.asList(elements));
	}

	private static WeightedRandomBag<BlockData> asBag(BlockPallet... elements) {
		WeightedRandomBag<BlockData> pallet = new WeightedRandomBag<>();
		for(BlockPallet block : elements){
			pallet.addEntry(block.data, block.chance);
		}
		return pallet;
	}

	public static TowerProperty getRandomTowerProperty(Random random) {
		return LIST_TOWER_PROPERTIES.get(random.nextInt(LIST_TOWER_PROPERTIES.size()));
	}

	public static TowerProperty getTowerProperties(Biome biome, Random random) {
		if (!biomeToTowerProperties.containsKey(biome)) {
			return getRandomTowerProperty(random);
		}
		List<TowerProperty> towerPropertyList = new ArrayList<>(biomeToTowerProperties.get(biome));
		return towerPropertyList.get(random.nextInt(towerPropertyList.size()));
	}

	///✅
	static {
		STONE_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_BOREAL_FOREST, OVERWORLD_PLAINS,
				OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST
			),
			asBag(BlockPallet.pallet(BRICK_STONE_POLISHED.id(), 45.0f), BlockPallet.pallet(STONE_CARVED.id(), 45.0f), BlockPallet.pallet(STONE_POLISHED.id(), 10.0f))
			, 0
		);
		BRICK_STONE_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_BOREAL_FOREST, OVERWORLD_PLAINS,
				OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST
			),
			asBag(BlockPallet.pallet(BRICK_STONE.id(), 50.0f), BlockPallet.pallet(STONE.id(), 50.0f))
			, 0
		);
		DILAPIDATED_STONE_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_BOREAL_FOREST, OVERWORLD_PLAINS,
				OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST
			),
			asBag(BlockPallet.pallet(COBBLE_STONE.id(), 50.0f), BlockPallet.pallet(COBBLE_STONE_MOSSY.id(), 50.0f))
			, 0
		);

		RETRO_TOWER = register(asSet(
			OVERWORLD_RETRO,OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_BOREAL_FOREST,
				OVERWORLD_PLAINS, OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST
			),
			asBag(BlockPallet.pallet(COBBLE_STONE.id(), 100.0f))
			, 0
		);

		DESERT_TOWER = register(asSet(OVERWORLD_DESERT, OVERWORLD_CAATINGA),
			asBag(BlockPallet.pallet(BRICK_SANDSTONE.id(), 50.0f), BlockPallet.pallet(SANDSTONE.id(), 50.0f))
			, 1
		);
		DILAPIDATED_DESERT_TOWER = register(asSet(OVERWORLD_DESERT, OVERWORLD_CAATINGA),
			asBag(BlockPallet.pallet(BRICK_SANDSTONE.id(), 30.0f), BlockPallet.pallet(SANDSTONE.id(), 40.0f), BlockPallet.pallet(SAND.id(), 30.0f))
			, 1
		);

		GRANITE_TOWER = register(asSet(
				OVERWORLD_OUTBACK, OVERWORLD_SHRUBLAND, OVERWORLD_DESERT, OVERWORLD_CAATINGA, OVERWORLD_CAATINGA_PLAINS
			),
			asBag(BlockPallet.pallet(GRANITE_CARVED.id(), 50.0f), BlockPallet.pallet(GRANITE_POLISHED.id(), 50.0f))
			, 2
		);
		DILAPIDATED_GRANITE_TOWER = register(asSet(
				OVERWORLD_OUTBACK, OVERWORLD_SHRUBLAND, OVERWORLD_DESERT, OVERWORLD_CAATINGA, OVERWORLD_CAATINGA_PLAINS
			),
			asBag(BlockPallet.pallet(BRICK_GRANITE.id(), 45.0f), BlockPallet.pallet(GRANITE.id(), 45.0f), BlockPallet.pallet(COBBLE_GRANITE.id(), 10.0f))
			, 2
		);

		LIMESTONE_TOWER = register(asSet(
				OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_PLAINS, OVERWORLD_MEADOW
			),
			asBag(BlockPallet.pallet(LIMESTONE_POLISHED.id(), 50.0f), BlockPallet.pallet(LIMESTONE_CARVED.id(), 50.0f))
			, 3
		);
		DILAPIDATED_LIMESTONE_TOWER = register(asSet(
				OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_PLAINS, OVERWORLD_MEADOW
			),
			asBag(BlockPallet.pallet(BRICK_LIMESTONE.id(), 50.0f), BlockPallet.pallet(COBBLE_LIMESTONE.id(), 50.0f))
			, 3
		);
		BASALT_TOWER = register(asSet(
				OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_BOREAL_FOREST,
				OVERWORLD_PLAINS, OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST, OVERWORLD_HELL
			),
			asBag(BlockPallet.pallet(BASALT_POLISHED.id(), 50.0f), BlockPallet.pallet(BASALT_CARVED.id(), 50.0f))
			, 4
		);

		DILAPIDATED_BASALT_TOWER = register(asSet(
				OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_BOREAL_FOREST,
				OVERWORLD_PLAINS, OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST, OVERWORLD_HELL
			),
			asBag(BlockPallet.pallet(BASALT_POLISHED.id(), 45.0f), BlockPallet.pallet(BASALT_CARVED.id(), 45.0f), BlockPallet.pallet(BASALT.id(), 10.0f))
			, 4
		);

		OVERGROWN_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SWAMPLAND, OVERWORLD_FOREST, OVERWORLD_BIRCH_FOREST, OVERWORLD_SWAMPLAND_MUDDY
			),
			asBag(BlockPallet.pallet(BRICK_STONE_POLISHED_MOSSY.id(), 70.0f), BlockPallet.pallet(BRICK_STONE_POLISHED.id(), 15.0f), BlockPallet.pallet(STONE_CARVED.id(), 15.0f))
			, 5
		);
		DILAPIDATED_OVERGROWN_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SWAMPLAND, OVERWORLD_FOREST, OVERWORLD_BIRCH_FOREST, OVERWORLD_SWAMPLAND_MUDDY
			),
			asBag(BlockPallet.pallet(BRICK_STONE_POLISHED_MOSSY.id(), 50.0f), BlockPallet.pallet(MOSS_STONE.id(), 40.0f), BlockPallet.pallet(MOSS_LIMESTONE.id(), 10.0f))
			, 5
		);
		FROZEN_TOWER = register(asSet(
				OVERWORLD_TAIGA, OVERWORLD_BOREAL_FOREST, OVERWORLD_GLACIER, OVERWORLD_TUNDRA
			),
			asBag(BlockPallet.pallet(BRICK_PERMAFROST.id(), 45.0f), BlockPallet.pallet(PERMAFROST_CARVED.id(), 45.0f), BlockPallet.pallet(PERMAFROST_POLISHED.id(), 10.0f))
			, 6
		);
		DILAPIDATED_FROZEN_TOWER = register(asSet(
				OVERWORLD_TAIGA, OVERWORLD_BOREAL_FOREST, OVERWORLD_GLACIER, OVERWORLD_TUNDRA
			),
			asBag(BlockPallet.pallet(PERMAFROST.id(), 45.0f), BlockPallet.pallet(COBBLE_PERMAFROST.id(), 45.0f), BlockPallet.pallet(PERMAICE.id(), 10.0f))
			, 6
		);
		SNOW_TOWER = register(asSet(
				OVERWORLD_GLACIER, OVERWORLD_TUNDRA
			),
			asBag(BlockPallet.pallet(BLOCK_SNOW.id(), 100.0f))
			, 6
		);

		PARADISE_TOWER = register(asSet(
			PARADISE_PARADISE, OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND,
				OVERWORLD_BOREAL_FOREST, OVERWORLD_PLAINS, OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST, OVERWORLD_HELL
			),
			asBag(BlockPallet.pallet(BRICK_MARBLE.id(), 100.0f))
			, 7
		);
		HELLFIRE_TOWER = register(asSet(OVERWORLD_HELL),
			asBag(BlockPallet.pallet(NETHERRACK_POLISHED.id(), 50.0f), BlockPallet.pallet(NETHERRACK_CARVED.id(), 50.0f))
			, 8
		);
		OBSIDIAN_TOWER = register(asSet(OVERWORLD_HELL),
			asBag(BlockPallet.pallet(OBSIDIAN.id(), 100.0f))
			, 8
		);
		SLAVERS_TOWER = register(asSet(OVERWORLD_HELL),
			asBag(BlockPallet.pallet(BRICK_NETHERRACK.id(), 100.0f))
			, 8
		);
		DILAPIDATED_NETHERRACK_TOWER = register(asSet(OVERWORLD_HELL),
			asBag(BlockPallet.pallet(NETHERRACK.id(), 49.94f), BlockPallet.pallet(COBBLE_NETHERRACK.id(), 49.94f), BlockPallet.pallet(ORE_NETHERCOAL_NETHERRACK.id(), 0.02f))
			, 8
		);
		SLATE_TOWER = register(asSet(OVERWORLD_HELL),
			asBag(BlockPallet.pallet(SLATE_POLISHED.id(), 50.0f), BlockPallet.pallet(SLATE.id(), 50.0f))
			, 8
		);

	}

	public static void init() {
		//make sure it loaded
	}

}
