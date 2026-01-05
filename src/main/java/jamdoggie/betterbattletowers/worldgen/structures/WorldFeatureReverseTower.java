package jamdoggie.betterbattletowers.worldgen.structures;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.data.loot.LootTables.MAX_TIER;

public class WorldFeatureReverseTower extends WorldFeatureTower{
	public static final int MIN_HEIGHT = 7 * FLOOR_HEIGHT;

	public static WorldFeatureReverseTower tower() {
		return new WorldFeatureReverseTower();
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		int blockID = world.getBlockId(x + 8, y, z + 8);
		if (y <= MIN_HEIGHT || blockID == 0) {
			return false;
		}
		this.world = world;
		this.random = random;
		this.runicChance = 60.0f;
		this.carvedChance = 25.0f;
		this.glyphChance = 15.0f;
		this.setTowerProperties(this.world.getBlockBiome(x, y, z));
		this.placeTower(x, y, z, y);
		return true;
	}

	@Override
	protected void placeTower(int x, int y, int z, int availableHeight) {
		int addedFloors = Math.floorDiv(availableHeight, FLOOR_HEIGHT) - MIN_HEIGHT / FLOOR_HEIGHT;
		int maxFloors = this.random.nextInt(addedFloors) + FLOOR_HEIGHT;
		int lootAmount = getLootAmount();
		this.placeCrown(x, y, z);
		this.currentFloor++;
		for (; this.currentFloor < maxFloors - 1; this.currentFloor += 1) {
			int py = y - FLOOR_HEIGHT * this.currentFloor;
			this.placeFloorShell(x, py, z, false, FLOOR_HEIGHT);
			this.placeStaircase(x + 4, py, z + 3);
			this.placeHostileDecorations(x + 5, py + 1, z + 10);
			this.placeChests(x + 7, py + 2, z + 5, this.currentFloor, lootAmount);
		}
		this.placePrison(x, y - FLOOR_HEIGHT * (maxFloors - 1), z);
		this.placeCapStaircase(x, y - FLOOR_HEIGHT * (maxFloors - 1), z);
	}

	@Override
	protected int getLootAmount() {
		return  LOOT_AMOUNT;
	}

	private void placePrison(int x, int y, int z) {
		int lootAmount = LOOT_AMOUNT + 3;
		this.placeFloorShell(x, y, z, true, FLOOR_HEIGHT);
		this.placeChests(x + 7, y + 2, z + 5, MAX_TIER, lootAmount);
		this.placeGolem(x + 7.5, y + 1D, z + 7.5);
		this.placeStaircase(x + 4, y, z + 3);
	}

	///  Places the top floor, that includes wall and the actual floor
	private void placeCrown(int x, int y, int z) {
		this.placeFloorShell(x, y, z, true, 2);
		this.placeChests(x + 7, y + 2, z + 5, 0, LOOT_AMOUNT);
		this.createCrenulations(x, y, z);
		this.world.setBlock(x + 11, y + 1, z + 3, 0);
		this.world.setBlock(x + 11, y + 2, z + 3, 0);
		this.world.setBlock(x + 11, y, z + 3, Blocks.STONE_POLISHED.id());
	}
}
