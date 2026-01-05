package jamdoggie.betterbattletowers.worldgen.structures;

import jamdoggie.betterbattletowers.config.BattleTowerConfig;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.data.loader.LootLoader.MAX_TIER;

public class WorldFeatureBattleTower extends WorldFeatureTower {
	public static final int MIN_HEIGHT = 10 * FLOOR_HEIGHT;

	/// Important for the placement via commands
	public WorldFeatureBattleTower() {
		// used by place command
	}

	public static WorldFeatureBattleTower tower() {
		return new WorldFeatureBattleTower();
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		int availableHeight = world.getHeightBlocks() - y;
		int blockID = world.getBlockId(x + 8, y, z + 8);
		if (availableHeight < MIN_HEIGHT || blockID == BLOCK_AIR) {
			return false;
		}
		this.world = world;
		this.random = random;
		this.setTowerProperties(this.world.getBlockBiome(x, y, z));
		this.placeTower(x, y, z, availableHeight);
		return true;
	}

	///  Places tower
	@Override
	protected void placeTower(int x, int y, int z, int availableHeight) {
		int addedFloors = MathHelper.ceilInt(availableHeight, FLOOR_HEIGHT) - MIN_HEIGHT / FLOOR_HEIGHT;
		int maxFloors = this.random.nextInt(addedFloors) + FLOOR_HEIGHT;
		int lootAmount = getLootAmount();
		this.placeFoundation(x, y - 1, z);
		for (this.currentFloor = 0; this.currentFloor < maxFloors - 1; this.currentFloor += 1) {
			int py = y + FLOOR_HEIGHT * this.currentFloor;
			this.placeFloorShell(x, py, z, false, FLOOR_HEIGHT);
			this.placeWindows(x + 1, py + 2, z + 7, py == y);
			this.placeStaircase(x + 4, py, z + 3);
			this.placeHostileDecorations(x + 5, py + 1, z + 10);
			this.placeChests(x + 7, py + 2, z + 5, this.currentFloor, lootAmount);
		}
		this.placeCapStaircase(x, y, z);
		this.placeCrown(x, y + FLOOR_HEIGHT * (maxFloors - 1), z);
	}

	@Override
	protected int getLootAmount() {
		return BattleTowerConfig.isTint() ? (int) Math.floor(LOOT_AMOUNT * 0.2) + LOOT_AMOUNT : LOOT_AMOUNT;
	}

	///  Places the top floor, that includes wall and the actual floor
	private void placeCrown(int x, int y, int z) {
		int lootAmount = BattleTowerConfig.isTint() ? LOOT_AMOUNT + 3 : LOOT_AMOUNT;
		this.placeFloorShell(x, y, z, true, 2);
		this.placeChests(x + 7, y + 2, z + 5, MAX_TIER, lootAmount);
		this.placeGolem(x + 7.5, y + 1D, z + 7.5);
		this.createCrenulations(x, y, z);
		this.world.setBlock(x + 11, y + 1, z + 3, BLOCK_AIR);
		this.world.setBlock(x + 11, y + 2, z + 3, BLOCK_AIR);
		this.world.setBlock(x + 11, y, z + 3, Blocks.STONE_POLISHED.id());
	}
}
