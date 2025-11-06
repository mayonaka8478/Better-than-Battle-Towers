package jamdoggie.betterbattletowers.worldgen;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;

import java.util.ArrayList;
import java.util.List;

import static jamdoggie.betterbattletowers.worldgen.BlockPallet.ipair;


public class BlockPaletts {

	/// bag based on biome
	static WeightedRandomBag<Integer> iceBiom = new WeightedRandomBag<>();
	static WeightedRandomBag<Integer> desertBiom = new WeightedRandomBag<>();
	static WeightedRandomBag<Integer> birchBiom = new WeightedRandomBag<>();
	static WeightedRandomBag<Integer> forestBiom = new WeightedRandomBag<>();

	/// random bags for when none of the biomes apply
	static List<WeightedRandomBag<Integer>> randomBiom = new ArrayList<>();

	/* Each entry consist of the block to be placed followed by it chance to be picked.
	 * To keep the chance simple make sure that all the values in each given bag adds up to 100.
	 * If done so each blocks weight will represent the chance of the given block to be picked
	 */
	static {
		iceBiom.addEntry(Blocks.BRICK_PERMAFROST.id(), 50.0f);
		iceBiom.addEntry(Blocks.COBBLE_PERMAFROST.id(), 50.0f);

		desertBiom.addEntry(Blocks.BRICK_SANDSTONE.id(), 50.0f);
		desertBiom.addEntry(Blocks.SANDSTONE.id(), 50.0f);

		birchBiom.addEntry(Blocks.BRICK_STONE_POLISHED.id(), 67.0f);
		birchBiom.addEntry(Blocks.BRICK_STONE_POLISHED_MOSSY.id(), 33.0f);

		forestBiom.addEntry(Blocks.COBBLE_STONE.id(), 67.0f);
		forestBiom.addEntry(Blocks.COBBLE_STONE_MOSSY.id(), 33.0f);

		/// all other tower are going to pick a random blockpallet.
		addBiomes(randomBiom, ipair(Blocks.COBBLE_STONE.id(), 67.0f), ipair(Blocks.COBBLE_STONE_MOSSY.id(), 33.0f));
		addBiomes(randomBiom, ipair(Blocks.COBBLE_BASALT.id(), 100.0f));
		addBiomes(randomBiom, ipair(Blocks.COBBLE_LIMESTONE.id(), 100.0f));
		addBiomes(randomBiom, ipair(Blocks.COBBLE_GRANITE.id(), 100.0f));
		addBiomes(randomBiom, ipair(Blocks.BRICK_STONE.id(), 100.0f));
		addBiomes(randomBiom, ipair(Blocks.BRICK_STONE_POLISHED.id(), 67.0f), ipair(Blocks.BRICK_STONE_POLISHED_MOSSY.id(), 33.0f));
		addBiomes(randomBiom, ipair(Blocks.BRICK_LIMESTONE.id(), 100.0f));
		addBiomes(randomBiom, ipair(Blocks.BRICK_GRANITE.id(), 100.0f));
	}

	/**
	 * @implNote Add idAndChances, containing id and chances for the block to roll, to a WeightedRandomBag and put the bag
	 * at the end of the list.
	 * */
	private static void addBiomes(List<WeightedRandomBag<Integer>> randomBiom, BlockPallet... idAndChances) {
		WeightedRandomBag<Integer> pallet = new WeightedRandomBag<>();
		for(BlockPallet idAndChance : idAndChances){
			pallet.addEntry(idAndChance.blockID, idAndChance.chance);
		}
		randomBiom.add(pallet);
	}

	/**
	 * @implNote Gets the WeightedRandomBag at the index, if not present returns the first bag.
	 * */
	private static WeightedRandomBag<Integer> getBiomesBag(int index) {
		if(index >= randomBiom.size() || index < 0){
			return randomBiom.getFirst();
		}
		return randomBiom.get(index);
	}

	/**
	 * @implNote Get a WeightedRandomBag containing the Blocks for the BattleTowers.
	 * The bag can then be used to draw the next block to place.
	 * */
	public static WeightedRandomBag<Integer> getRandomCobbledBlockBag(Biome biome, int index) {
		if (biome.hasSurfaceSnow()) {
			return iceBiom;
		}
		if (biome == Biomes.OVERWORLD_DESERT) {
			return desertBiom;
		}
		if (biome == Biomes.OVERWORLD_BIRCH_FOREST) {
			return birchBiom;
		}
		if (biome == Biomes.OVERWORLD_FOREST) {
			return forestBiom;
		}
		return getBiomesBag(index);
	}
}
