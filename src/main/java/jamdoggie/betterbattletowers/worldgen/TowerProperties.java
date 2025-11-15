package jamdoggie.betterbattletowers.worldgen;

import net.minecraft.core.world.biome.Biome;

import java.util.*;

import static jamdoggie.betterbattletowers.worldgen.BlockPallet.ipair;
import static jamdoggie.betterbattletowers.worldgen.TowerProperty.tower;
import static net.minecraft.core.block.Blocks.*;
import static net.minecraft.core.world.biome.Biomes.*;

public class TowerProperties {
	private static final Map<Biome, Set<TowerProperty>> biomeToTowerProperties = new HashMap<>();
	private static final Map<TowerProperty, Set<Biome>> towerPropertiesToBiome = new HashMap<>();
	private static final Map<Integer, Set<TowerProperty>> skinVariantToTowerProperties = new HashMap<>();
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

	public static TowerProperty register(Set<Biome> biomeList, TowerProperty tower) {
		for (Biome biome : biomeList) {
			biomeToTowerProperties.computeIfAbsent(biome, key -> new HashSet<>()).add(tower);
			skinVariantToTowerProperties.computeIfAbsent(tower.getSkinVariant(), key -> new HashSet<>()).add(tower);
		}
		towerPropertiesToBiome.put(tower, biomeList);
		LIST_TOWER_PROPERTIES.add(tower);
		return tower;
	}

	private static Set<Biome> asSet(Biome... biomes) {
		return new HashSet<>(Arrays.asList(biomes));
	}


	public static TowerProperty getRandomTowerProperty(Random random){
		return LIST_TOWER_PROPERTIES.get(random.nextInt(LIST_TOWER_PROPERTIES.size()));
	}

	public static TowerProperty getTowerProperties(Biome biome, Random random){
		if(!biomeToTowerProperties.containsKey(biome)){
			return getRandomTowerProperty(random);
		}
		List<TowerProperty> towerPropertyList = new ArrayList<>(biomeToTowerProperties.get(biome));
		return towerPropertyList.get(random.nextInt(towerPropertyList.size()));
	}


	static {
		///✅
		DESERT_TOWER = register(asSet(OVERWORLD_DESERT, OVERWORLD_CAATINGA),
			tower()
				.addDecorationBlocks(ipair(BRICK_SANDSTONE.id(), 50.0f), ipair(SANDSTONE.id(), 50.0f))
				.setSkinVariant(0)
		);
		///✅
		DILAPIDATED_DESERT_TOWER = register(asSet(OVERWORLD_DESERT, OVERWORLD_CAATINGA),
			tower()
				.addDecorationBlocks(ipair(BRICK_SANDSTONE.id(), 30.0f), ipair(SANDSTONE.id(), 40.0f), ipair(SAND.id(), 30.0f))
				.setSkinVariant(0)
		);

		///✅
		GRANITE_TOWER = register(asSet(
			OVERWORLD_OUTBACK, OVERWORLD_SHRUBLAND, OVERWORLD_DESERT, OVERWORLD_CAATINGA, OVERWORLD_CAATINGA_PLAINS
			),
			tower()
				.addDecorationBlocks(ipair(GRANITE_CARVED.id(), 50.0f), ipair(GRANITE_POLISHED.id(), 50.0f))
				.setSkinVariant(1)
		);
		///✅
		DILAPIDATED_GRANITE_TOWER = register(asSet(
			OVERWORLD_OUTBACK, OVERWORLD_SHRUBLAND, OVERWORLD_DESERT, OVERWORLD_CAATINGA, OVERWORLD_CAATINGA_PLAINS
			),
			tower()
				.addDecorationBlocks(ipair(BRICK_GRANITE.id(), 45.0f), ipair(GRANITE.id(), 45.0f), ipair(COBBLE_GRANITE.id(), 10.0f))
				.setSkinVariant(1)
		);

		///✅
		LIMESTONE_TOWER = register(asSet(
			OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_PLAINS, OVERWORLD_MEADOW
			),
			tower()
				.addDecorationBlocks(ipair(LIMESTONE_POLISHED.id(), 50.0f), ipair(LIMESTONE_CARVED.id(), 50.0f))
				.setSkinVariant(2)
		);
		///✅
		DILAPIDATED_LIMESTONE_TOWER = register(asSet(
			OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_PLAINS, OVERWORLD_MEADOW
			),
			tower()
				.addDecorationBlocks(ipair(BRICK_LIMESTONE.id(), 50.0f), ipair(COBBLE_LIMESTONE.id(), 50.0f))
				.setSkinVariant(2));
		///✅
		BASALT_TOWER = register(asSet(
				OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_BOREAL_FOREST,
				OVERWORLD_PLAINS, OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST, OVERWORLD_HELL
			),
			tower()
				.addDecorationBlocks(ipair(BASALT_POLISHED.id(), 50.0f), ipair(BASALT_CARVED.id(), 50.0f))
				.setSkinVariant(3));

		DILAPIDATED_BASALT_TOWER = register(asSet(
				OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_SHRUBLAND, OVERWORLD_BOREAL_FOREST,
				OVERWORLD_PLAINS, OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST, OVERWORLD_HELL
			),
			tower()
				.addDecorationBlocks(ipair(BASALT_POLISHED.id(), 45.0f), ipair(BASALT_CARVED.id(), 45.0f), ipair(BASALT.id(), 10.0f))
				.setSkinVariant(3)
		);
		///✅
		STONE_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_BOREAL_FOREST, OVERWORLD_PLAINS,
				OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST
			),
			tower()
				.addDecorationBlocks(ipair(BRICK_STONE_POLISHED.id(), 45.0f), ipair(STONE_CARVED.id(), 45.0f), ipair(STONE_POLISHED.id(), 10.0f))
				.setSkinVariant(4)
		);
		///✅
		BRICK_STONE_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_BOREAL_FOREST, OVERWORLD_PLAINS,
				OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST
			),
			tower()
				.addDecorationBlocks(ipair(BRICK_STONE.id(), 50.0f), ipair(STONE.id(), 50.0f))
				.setSkinVariant(4)
		);
		///✅
		DILAPIDATED_STONE_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SEASONAL_FOREST, OVERWORLD_FOREST, OVERWORLD_GRASSLANDS, OVERWORLD_BOREAL_FOREST, OVERWORLD_PLAINS,
				OVERWORLD_MEADOW, OVERWORLD_BIRCH_FOREST
			),
			tower()
				.addDecorationBlocks(ipair(COBBLE_STONE.id(), 50.0f), ipair(COBBLE_STONE_MOSSY.id(), 50.0f))
				.setSkinVariant(4)
		);

		OVERGROWN_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SWAMPLAND, OVERWORLD_FOREST, OVERWORLD_BIRCH_FOREST, OVERWORLD_SWAMPLAND_MUDDY
			),
			tower()
				.addDecorationBlocks(ipair(BRICK_STONE_POLISHED_MOSSY.id(), 70.0f), ipair(BRICK_STONE_POLISHED.id(), 15.0f), ipair(STONE_CARVED.id(), 15.0f))
				.setSkinVariant(5)
		);
		DILAPIDATED_OVERGROWN_TOWER = register(asSet(
				OVERWORLD_RAINFOREST, OVERWORLD_SWAMPLAND, OVERWORLD_FOREST, OVERWORLD_BIRCH_FOREST, OVERWORLD_SWAMPLAND_MUDDY
			),
			tower()
				.addDecorationBlocks(ipair(BRICK_STONE_POLISHED_MOSSY.id(), 50.0f), ipair(MOSS_STONE.id(), 40.0f), ipair(MOSS_LIMESTONE.id(), 10.0f))
				.setSkinVariant(5)
		);
		///✅
		FROZEN_TOWER = register(asSet(
				OVERWORLD_TAIGA, OVERWORLD_BOREAL_FOREST, OVERWORLD_GLACIER, OVERWORLD_TUNDRA
			),
			tower()
				.addDecorationBlocks(ipair(BRICK_PERMAFROST.id(), 45.0f), ipair(PERMAFROST_CARVED.id(), 45.0f), ipair(PERMAFROST_POLISHED.id(), 10.0f))
				.setSkinVariant(6)
		);
		DILAPIDATED_FROZEN_TOWER = register(asSet(
				OVERWORLD_TAIGA, OVERWORLD_BOREAL_FOREST, OVERWORLD_GLACIER, OVERWORLD_TUNDRA
			),
			tower()
				.addDecorationBlocks(ipair(PERMAFROST.id(), 45.0f), ipair(COBBLE_PERMAFROST.id(), 45.0f), ipair(PERMAICE.id(), 10.0f))
				.setSkinVariant(6)
		);
		///✅
		SNOW_TOWER = register(asSet(
				OVERWORLD_GLACIER, OVERWORLD_TUNDRA
			),
			tower()
				.addDecorationBlocks(ipair(BLOCK_SNOW.id(), 100.0f))
				.setSkinVariant(6)
		);

		///✅
		RETRO_TOWER = register(asSet(OVERWORLD_RETRO),
			tower()
				.addDecorationBlocks(ipair(COBBLE_STONE.id(), 100.0f))
				.setSkinVariant(4)
		);
		///✅
		PARADISE_TOWER = register(asSet(PARADISE_PARADISE),
			tower()
				.addDecorationBlocks(ipair(BRICK_MARBLE.id(), 100.0f))
				.setSkinVariant(7)
		);
		///✅
		HELLFIRE_TOWER = register(asSet(OVERWORLD_HELL),
			tower()
				.addDecorationBlocks(ipair(NETHERRACK_POLISHED.id(), 50.0f), ipair(NETHERRACK_CARVED.id(), 50.0f))
				.setSkinVariant(8)
		);
		///✅
		OBSIDIAN_TOWER = register(asSet(OVERWORLD_HELL),
			tower()
				.addDecorationBlocks(ipair(OBSIDIAN.id(), 100.0f))
				.setSkinVariant(8)
		);
		///✅
		SLAVERS_TOWER = register(asSet(OVERWORLD_HELL),
			tower()
				.addDecorationBlocks(ipair(BRICK_NETHERRACK.id(), 100.0f))
				.setSkinVariant(8)
		);
		///✅
		DILAPIDATED_NETHERRACK_TOWER = register(asSet(OVERWORLD_HELL),
			tower()
				.addDecorationBlocks(ipair(NETHERRACK.id(), 49.94f), ipair(COBBLE_NETHERRACK.id(), 49.94f), ipair(ORE_NETHERCOAL_NETHERRACK.id(), 0.02f))
				.setSkinVariant(8)
		);
		SLATE_TOWER = register(asSet(OVERWORLD_HELL),
			tower()
				.addDecorationBlocks(ipair(SLATE_POLISHED.id(), 50.0f), ipair(SLATE.id(), 50.0f))
				.setSkinVariant(8)
		);

	}

	public static void init() {
		//make sure it loaded
	}

}
