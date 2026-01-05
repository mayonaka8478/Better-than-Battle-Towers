package jamdoggie.betterbattletowers.worldgen.data.defaults;

import jamdoggie.betterbattletowers.worldgen.data.decoration.BlockData;
import jamdoggie.betterbattletowers.worldgen.data.decoration.TowerProperties;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Blocks;

import java.util.*;

import static jamdoggie.betterbattletowers.block.BattleTowerBlocks.*;
import static jamdoggie.betterbattletowers.worldgen.data.decoration.BlockData.bd;
import static jamdoggie.betterbattletowers.worldgen.data.decoration.TowerProperties.properties;
import static jamdoggie.betterbattletowers.worldgen.data.decoration.TowerProperty.deco;
import static jamdoggie.betterbattletowers.worldgen.data.defaults.DefaultTowerDecoration.BlockPallet.pallet;
import static net.minecraft.core.block.Blocks.*;
import static net.minecraft.core.world.biome.Biomes.*;


public class DefaultTowerDecoration {
	private DefaultTowerDecoration() {
	}

	public static class BlockPallet {
		protected BlockData data;
		protected float chance;

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

	@SafeVarargs
	@SuppressWarnings("varargs")
	private static <T> Set<T> asSet(T... elements) {
		return new HashSet<>(Arrays.asList(elements));
	}

	private static WeightedRandomBag<BlockData> asBag(BlockPallet... elements) {
		WeightedRandomBag<BlockData> pallet = new WeightedRandomBag<>();
		for (BlockPallet block : elements) {
			pallet.addEntry(block.data, block.chance);
		}
		return pallet;
	}

	@SuppressWarnings({"java:S117", "java:S1192"})
	public static List<TowerProperties> getDefaultTowers() {
		List<TowerProperties> listProperties = new ArrayList<>();

		WeightedRandomBag<BlockData> POLISH_STONE = asBag(pallet(BRICK_STONE_POLISHED.id(), 45.0f), pallet(STONE_CARVED.id(), 45.0f), pallet(STONE_POLISHED.id(), 10.0f));
		WeightedRandomBag<BlockData> BRICK_STONE = asBag(pallet(Blocks.BRICK_STONE.id(), 100.0f));

		WeightedRandomBag<BlockData> COBBLE_STONE = asBag(pallet(Blocks.COBBLE_STONE.id(), 100.0f));
		WeightedRandomBag<BlockData> MOSSY_COBBLE_STONE = asBag(pallet(Blocks.COBBLE_STONE.id(), 50.0f), pallet(COBBLE_STONE_MOSSY.id(), 50.0f));

		WeightedRandomBag<BlockData> BRICK_SANDSTONE = asBag(pallet(Blocks.SANDSTONE.id(), 50.0f), pallet(Blocks.BRICK_SANDSTONE.id(), 50.0f));
		WeightedRandomBag<BlockData> SANDSTONE = asBag(pallet(Blocks.BRICK_SANDSTONE.id(), 30.0f), pallet(Blocks.SANDSTONE.id(), 40.0f), pallet(SAND.id(), 30.0f));

		WeightedRandomBag<BlockData> POLISH_GRANITE = asBag(pallet(GRANITE_CARVED.id(), 50.0f), pallet(GRANITE_POLISHED.id(), 50.0f));
		WeightedRandomBag<BlockData> BRICK_GRANITE = asBag(pallet(Blocks.BRICK_GRANITE.id(), 50.0f), pallet(GRANITE.id(), 50.0f));
		WeightedRandomBag<BlockData> BACKED_MUD = asBag(pallet(MUD_BAKED.id(), 100));

		WeightedRandomBag<BlockData> LIMESTONE = asBag(pallet(Blocks.LIMESTONE.id(), 100.0f));
		WeightedRandomBag<BlockData> POLISH_LIMESTONE = asBag(pallet(LIMESTONE_POLISHED.id(), 50.0f), pallet(LIMESTONE_CARVED.id(), 50.0f));
		WeightedRandomBag<BlockData> BRICK_LIMESTONE = asBag(pallet(Blocks.BRICK_LIMESTONE.id(), 100.0f));
		WeightedRandomBag<BlockData> COBBLE_LIMESTONE = asBag(pallet(Blocks.BRICK_LIMESTONE.id(), 50.0f), pallet(Blocks.COBBLE_LIMESTONE.id(), 50.0f));

		WeightedRandomBag<BlockData> POLISH_BASALT = asBag(pallet(BASALT_POLISHED.id(), 50.0f), pallet(BASALT_CARVED.id(), 50.0f));
		WeightedRandomBag<BlockData> BRICK_BASALT = asBag(pallet(Blocks.BRICK_BASALT.id(), 100.0f));
		WeightedRandomBag<BlockData> COBBLE_BASALT = asBag(pallet(Blocks.COBBLE_BASALT.id(), 100.0f));

		WeightedRandomBag<BlockData> MOSSY_BRICK_STONE = asBag(pallet(BRICK_STONE_POLISHED_MOSSY.id(), 100.0f));
		WeightedRandomBag<BlockData> MOSSY_BLOOM_BRICK_STONE = asBag(pallet(BRICK_STONE_POLISHED_MOSSY.id(), 50.0f), pallet(MOSS_STONE.id(), 40.0f), pallet(MOSS_LIMESTONE.id(), 10.0f));

		WeightedRandomBag<BlockData> POLISH_PERMAFROST = asBag(pallet(BRICK_PERMAFROST.id(), 45.0f), pallet(PERMAFROST_CARVED.id(), 45.0f), pallet(PERMAFROST_POLISHED.id(), 10.0f));
		WeightedRandomBag<BlockData> PERMA_ICE = asBag(pallet(PERMAICE.id(), 100.0f));
		WeightedRandomBag<BlockData> COBBLE_PERMA = asBag(pallet(PERMAFROST.id(), 50.0f), pallet(COBBLE_PERMAFROST.id(), 50.0f));
		WeightedRandomBag<BlockData> SNOW = asBag(pallet(BLOCK_SNOW.id(), 100.0f));

		WeightedRandomBag<BlockData> BRICK_MARBLE = asBag(pallet(Blocks.BRICK_MARBLE.id(), 100.0f));
		WeightedRandomBag<BlockData> MARBLE = asBag(pallet(Blocks.MARBLE.id(), 100.0f));
		WeightedRandomBag<BlockData> PILLAR_MARBLE = asBag(pallet(Blocks.PILLAR_MARBLE.id(), 90.0f), pallet(CAPSTONE_MARBLE.id(), 10.0f));

		WeightedRandomBag<BlockData> BRICK_NETHER = asBag(pallet(BRICK_NETHERRACK.id(), 100.0f));
		WeightedRandomBag<BlockData> POLISH_NETHER = asBag(pallet(NETHERRACK_POLISHED.id(), 50.0f), pallet(NETHERRACK_CARVED.id(), 50.0f));
		WeightedRandomBag<BlockData> NETHER = asBag(pallet(NETHERRACK.id(), 100.0f));
		WeightedRandomBag<BlockData> COBBLE_NETHER = asBag(pallet(NETHERRACK.id(), 49.0f), pallet(COBBLE_NETHERRACK.id(), 49.0f), pallet(ORE_NETHERCOAL_NETHERRACK.id(), 1.6f), pallet(COBBLE_NETHERRACK_IGNEOUS.id(), 0.4f));

		WeightedRandomBag<BlockData> OBSIDIAN = asBag(pallet(Blocks.OBSIDIAN.id(), 100.0f));

		WeightedRandomBag<BlockData> SLATE = asBag(pallet(SLATE_POLISHED.id(), 50.0f), pallet(Blocks.SLATE.id(), 50.0f));

		WeightedRandomBag<BlockData> HARDCORE = asBag(pallet(RUNIC_STONE.id(), 60.0f), pallet(CHISELED_RUNIC_STONE.id(), 25.0f),
			pallet(bd(RUNIC_GLYPH_STONE.id(), 2), 3.75f),
			pallet(bd(RUNIC_GLYPH_STONE.id(), 3), 3.75f),
			pallet(bd(RUNIC_GLYPH_STONE.id(), 4), 3.75f),
			pallet(bd(RUNIC_GLYPH_STONE.id(), 5), 3.75f));

		/// basalt			wet		hot
		/// obsidian		wet		hot
		/// overgrow		wet 	hot
		/// limestone		wet		medium
		/// slate 			wet		medium
		/// overgrow		wet 	medium
		/// stone			wet 	medium
		/// marble			wet		cold


		/// nether			dry		veryhot
		/// granite 		dry		hot
		/// obsidian		dry		hot
		/// sandstone		dry 	hot
		/// stone			dry		medium
		/// perma			dry		cold
		/// ice				dry		cold


		listProperties.add(
			properties(OVERWORLD_RAINFOREST)
				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("slate", SLATE, 50.0))

				.addProperty(deco("obsidian", OBSIDIAN, 100.0))

				.addProperty(deco("overgrown", MOSSY_COBBLE_STONE, 100.0))
				.addProperty(deco("overgrown", MOSSY_BRICK_STONE, 100.0))
				.addProperty(deco("overgrown", MOSSY_BLOOM_BRICK_STONE, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_SWAMPLAND)
				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("slate", SLATE, 100.0))

				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("overgrown", MOSSY_COBBLE_STONE, 50.0))
				.addProperty(deco("overgrown", MOSSY_BRICK_STONE, 50.0))
				.addProperty(deco("overgrown", MOSSY_BLOOM_BRICK_STONE, 50.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		///
		listProperties.add(
			properties(OVERWORLD_SEASONAL_FOREST)
				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("stone", POLISH_STONE, 100.0))
				.addProperty(deco("stone", BRICK_STONE, 100.0))
				.addProperty(deco("stone", COBBLE_STONE, 100.0))

				.addProperty(deco("overgrown", MOSSY_COBBLE_STONE, 10.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		///
		listProperties.add(
			properties(OVERWORLD_FOREST)
				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("overgrown", MOSSY_COBBLE_STONE, 10.0))
				.addProperty(deco("overgrown", MOSSY_BRICK_STONE, 10.0))
				.addProperty(deco("overgrown", MOSSY_BLOOM_BRICK_STONE, 10.0))

				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("stone", POLISH_STONE, 100.0))
				.addProperty(deco("stone", BRICK_STONE, 100.0))
				.addProperty(deco("stone", COBBLE_STONE, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		///
		listProperties.add(
			properties(OVERWORLD_GRASSLANDS)
				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_OUTBACK)
				.addProperty(deco("obsidian", OBSIDIAN, 100.0))

				.addProperty(deco("granite", POLISH_GRANITE, 100.0))
				.addProperty(deco("granite", BRICK_GRANITE, 100.0))
				.addProperty(deco("granite", BACKED_MUD, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_SHRUBLAND)
				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_TAIGA)
				.addProperty(deco("permafrost", POLISH_PERMAFROST, 100.0))
				.addProperty(deco("permafrost", PERMA_ICE, 100.0))
				.addProperty(deco("permafrost", COBBLE_PERMA, 100.0))
				.addProperty(deco("permafrost", SNOW, 100.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_BOREAL_FOREST)
				.addProperty(deco("stone", POLISH_STONE, 100.0))
				.addProperty(deco("stone", BRICK_STONE, 100.0))
				.addProperty(deco("stone", COBBLE_STONE, 100.0))

				.addProperty(deco("permafrost", POLISH_PERMAFROST, 100.0))
				.addProperty(deco("permafrost", COBBLE_PERMA, 100.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_DESERT)
				.addProperty(deco("sandstone", BRICK_SANDSTONE, 100.0))
				.addProperty(deco("sandstone", SANDSTONE, 100.0))

				.addProperty(deco("granite", POLISH_GRANITE, 100.0))
				.addProperty(deco("granite", BRICK_GRANITE, 100.0))
				.addProperty(deco("granite", BACKED_MUD, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_PLAINS)
				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("stone", POLISH_STONE, 100.0))
				.addProperty(deco("stone", BRICK_STONE, 100.0))
				.addProperty(deco("stone", COBBLE_STONE, 100.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_GLACIER)
				.addProperty(deco("permafrost", POLISH_PERMAFROST, 100.0))
				.addProperty(deco("permafrost", PERMA_ICE, 100.0))
				.addProperty(deco("permafrost", COBBLE_PERMA, 100.0))
				.addProperty(deco("permafrost", SNOW, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_TUNDRA)
				.addProperty(deco("permafrost", POLISH_PERMAFROST, 100.0))
				.addProperty(deco("permafrost", PERMA_ICE, 100.0))
				.addProperty(deco("permafrost", COBBLE_PERMA, 100.0))
				.addProperty(deco("permafrost", SNOW, 100.0))

				.addProperty(deco("stone", POLISH_STONE, 100.0))
				.addProperty(deco("stone", BRICK_STONE, 100.0))
				.addProperty(deco("stone", COBBLE_STONE, 100.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))

				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_MEADOW)
				.addProperty(deco("stone", POLISH_STONE, 100.0))
				.addProperty(deco("stone", BRICK_STONE, 100.0))
				.addProperty(deco("stone", MOSSY_COBBLE_STONE, 10.0))
				.addProperty(deco("stone", COBBLE_STONE, 100.0))

				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(NETHER_NETHER)
		);

		listProperties.add(
			properties(PARADISE_PARADISE)
				.addProperty(deco("marble", BRICK_MARBLE, 1.67))
				.addProperty(deco("marble", MARBLE, 1.67))
				.addProperty(deco("marble", PILLAR_MARBLE, 1.67))
		);

		listProperties.add(
			properties(OVERWORLD_BIRCH_FOREST)
				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("overgrown", MOSSY_COBBLE_STONE, 10.0))
				.addProperty(deco("overgrown", MOSSY_BRICK_STONE, 10.0))
				.addProperty(deco("overgrown", MOSSY_BLOOM_BRICK_STONE, 10.0))

				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("stone", POLISH_STONE, 100.0))
				.addProperty(deco("stone", BRICK_STONE, 100.0))
				.addProperty(deco("stone", COBBLE_STONE, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_RETRO)
				.addProperty(deco("stone", COBBLE_STONE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_HELL)
				.addProperty(deco("obsidian", OBSIDIAN, 100.0))
				.addProperty(deco("netherrack", BRICK_NETHER, 100.0))
				.addProperty(deco("netherrack", COBBLE_NETHER, 100.0))
				.addProperty(deco("netherrack", NETHER, 100.0))
				.addProperty(deco("netherrack", POLISH_NETHER, 100.0))
				.addProperty(deco("slate", SLATE, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_SWAMPLAND_MUDDY)
				.addProperty(deco("limestone", COBBLE_LIMESTONE, 100.0))
				.addProperty(deco("limestone", LIMESTONE, 100.0))
				.addProperty(deco("limestone", POLISH_LIMESTONE, 100.0))
				.addProperty(deco("limestone", BRICK_LIMESTONE, 100.0))

				.addProperty(deco("slate", SLATE, 100.0))

				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("overgrown", MOSSY_COBBLE_STONE, 50.0))
				.addProperty(deco("overgrown", MOSSY_BRICK_STONE, 50.0))
				.addProperty(deco("overgrown", MOSSY_BLOOM_BRICK_STONE, 50.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_OUTBACK_GRASSY)
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_CAATINGA)
				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("sandstone", BRICK_SANDSTONE, 100.0))
				.addProperty(deco("sandstone", SANDSTONE, 100.0))
				.addProperty(deco("granite", POLISH_GRANITE, 100.0))
				.addProperty(deco("granite", BRICK_GRANITE, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		listProperties.add(
			properties(OVERWORLD_CAATINGA_PLAINS)
				.addProperty(deco("basalt", POLISH_BASALT, 100.0))
				.addProperty(deco("basalt", BRICK_BASALT, 100.0))
				.addProperty(deco("basalt", COBBLE_BASALT, 100.0))

				.addProperty(deco("granite", POLISH_GRANITE, 100.0))
				.addProperty(deco("granite", BRICK_GRANITE, 100.0))
				.addProperty(deco("slate", HARDCORE, 1.0))
		);

		return listProperties;
	}

}
