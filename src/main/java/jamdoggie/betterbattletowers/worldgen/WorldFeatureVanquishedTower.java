package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.util.BlockData;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldFeatureVanquishedTower extends WorldFeatureTower {
	private static final int MIN_HEIGHT = 7 * FLOOR_HEIGHT;
	private static final float BASE_VALUE = 1.10f;
	private float ruinFactor;
	private double maxHeight;
	private final List<Entry> doors = new ArrayList<>();

	private static class Entry {
		int x;
		int y;
		int z;
		int blockID;

		public Entry(int x, int y, int z, int blockID) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.blockID = blockID;
		}
	}

	public static WorldFeatureVanquishedTower tower() {
		return new WorldFeatureVanquishedTower();
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
		this.ruinFactor = 1.0f;
		this.runicChance = 80.0f;
		this.carvedChance = 19.0f;
		this.glyphChance = 1f;
		this.maxHeight = y;
		this.setTowerProperties(this.world.getBlockBiome(x, y, z));
		placeTower(x, y, z, availableHeight);
		return true;
	}

	///  Places tower
	@Override
	protected void placeTower(int x, int y, int z, int availableHeight) {
		int addedFloors = MathHelper.ceilInt(availableHeight, FLOOR_HEIGHT) - MIN_HEIGHT / FLOOR_HEIGHT;
		int maxFloors = this.random.nextInt(addedFloors) + FLOOR_HEIGHT;
		int lootAmount = getLootAmount();
		this.placeFoundation(x, y - 1, z);
		this.calcHeight(y, maxFloors);
		for (this.currentFloor = 0; this.currentFloor < maxFloors - 1; this.currentFloor += 1) {
			int py = y + FLOOR_HEIGHT * this.currentFloor;
			this.ruinFactor = ruinFactor * BASE_VALUE;
			this.placeFloorShell(x, py, z, false, FLOOR_HEIGHT);
			this.placeWindows(x + 1, py + 2, z + 7, py == y);
			if (maxHeight > py + 7) {
				this.placeStaircase(x + 4, py, z + 3);
				this.placeHostileDecorations(x + 5, py + 1, z + 10);
				this.placeChests(x + 7, py + 2, z + 5, this.currentFloor, lootAmount);
			}
		}
		this.placeCapStaircase(x, y, z);
		for (Entry e : this.doors) {
			world.setBlock(e.x, e.y, e.z, e.blockID);
		}
	}

	@Override
	protected int getLootAmount() {
		return MathHelper.ceilInt(LOOT_AMOUNT, 3);
	}

	private void calcHeight(int y, int maxFloors) {
		double height = 0;
		double ruinFactorLocal = 1.0;
		for(int f = 0; f < maxFloors; f++){
			height += 7 * 1.0f/ruinFactorLocal;
			ruinFactorLocal = ruinFactorLocal * BASE_VALUE;
		}
		this.maxHeight = y + height;
	}

	///  Places the current floor, that includes wall and the actual floor
	@Override
	protected void placeFloorShell(int x, int y, int z, boolean topFloor, int height) {
		for (int iy = 0; iy < height; iy++) {
			for (int ix = 0; ix < Chunk.CHUNK_SIZE_X; ix++) {
				for (int iz = 0; iz < Chunk.CHUNK_SIZE_Z; iz++) {
					placeFloor(x, ix, y, iy, z, iz, topFloor);
				}
			}
		}
	}

	///  This function places the individual blocks of a given floor.
	@Override
	protected void placeFloor(int x, int addX, int y, int addY, int z, int addZ, boolean topFloor) {
		if (isInPerimeter(addX, addZ)) {
			int px = x + addX;
			int py = y + addY;
			int pz = z + addZ;
			if (isWall(addX, addZ)) {
				this.stackWall(px, py, pz);
				return;
			}
			if (((addX > 3 && addX < 12) && addZ == 2 && addX != 11) || addY != 0) {
				this.canReplace(px, py, pz, BLOCK_AIR);
				return;
			}
			if (this.currentFloor == 0 || topFloor) {
				this.canReplace(px, py, pz, Blocks.STONE_POLISHED.id());
				return;
			}
			if (maxHeight > py + 7) {
				///  Create holes for mob to fall through to lower level
				BlockData data = this.floorBlockBag.getRandom(random);
				this.canReplace(px, py, pz, data.id(), data.metadata());
			}
		}
	}

	private void stackWall(int px, int py, int pz) {
		if (this.random.nextFloat() < (1.0f / this.ruinFactor)) {
			int iy = py;
			while (world.getBlockId(px, iy - 1, pz) == BLOCK_AIR) {
				iy--;
			}
			this.placeBlock(this.buildingBlockBag.getRandom(random), px, iy, pz);
		} else {
			this.world.setBlock(px, py, pz, BLOCK_AIR);
		}
	}

	///  Places the windows
	@Override
	protected void placeWindows(int x, int y, int z, boolean groundFloor) {
		for (int c = 0; c < 2; c++) {
			for (int iz = 0; iz < 2; iz++) {
				for (int iy = 0; iy < 4; iy++) {
					int px = c * 13 + x;
					int py = iy + y;
					int pz = iz + z;
					this.doors.add(new Entry(px, py, pz, BLOCK_AIR));
				}
			}
		}
	}

	///  Places the staircase connecting 2 floors
	@Override
	protected void placeStaircase(int x, int y, int z) {
		/// placing doors for
		for (int c = 0; c < 2; c++) {
			int px = c * 7 + x;
			for (int iy = 0; iy < 2; iy++) {
				int py = iy + y + 1;
				this.doors.add(new Entry(px, py, z, BLOCK_AIR));
			}
		}
		int pz = z - 1;
		this.world.setBlock(x, y, pz, Blocks.STONE_POLISHED.id());
		for (int ix = 0, iy = 0; ix < FLOOR_HEIGHT; ix++, iy++) {
			int px = ix + x + 1;
			int py = iy + y + 1;
			this.placeBlock(this.buildingBlockBag.getRandom(random), px, py - 1, pz);
			this.world.setBlockAndMetadata(px, py, pz, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 0);
		}
	}

	@Override
	protected void placeHostileDecorations(int x, int y, int z) {
		WeightedRandomBag<Integer> spawnerBag = new WeightedRandomBag<>();
		spawnerBag.addEntry(BLOCK_AIR, this.currentFloor);
		spawnerBag.addEntry(Blocks.MOBSPAWNER.id(), 4);
		setRandomSpawner(x, y, z, spawnerBag);
		setRandomSpawner(x + 5, y, z, spawnerBag);

	}

	private void setRandomSpawner(int x, int y, int z, WeightedRandomBag<Integer> spawnerBag) {
		world.setBlockWithNotify(x, y, z, spawnerBag.getRandom(this.random));
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityMobSpawner) {
			((TileEntityMobSpawner) tileEntity).setMobId(this.getRandomSpawnerMob());
		}
		world.setBlock(x, y - 1, z, Blocks.STONE_POLISHED.id());
	}


}
