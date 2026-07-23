package jamdoggie.betterbattletowers.worldgen.structures;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import jamdoggie.betterbattletowers.config.BattleTowerConfig;
import jamdoggie.betterbattletowers.util.metadata.MetadataHelper;
import jamdoggie.betterbattletowers.worldgen.data.decoration.BlockData;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.pos.TilePos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.data.decoration.BlockData.bd;
import static jamdoggie.betterbattletowers.worldgen.data.loader.LootDataLoader.populateChest;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithDirection;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithType;

public class WorldFeatureVanquishedSquareTower extends WorldFeatureTower {
	private static final int MIN_HEIGHT = 7 * FLOOR_HEIGHT;
	private static final float BASE_VALUE = 1.10f;
	private float ruinFactor;
	private double maxHeight;
	private final List<Entry> doors = new ArrayList<>();
	public static WeightedRandomBag<BlockData> stairs = new WeightedRandomBag<>();

	static {
		stairs.addEntry(bd(Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 0), 20.0f);
		stairs.addEntry(bd(BattleTowerBlocks.STAIRS_CRUMBLING_STONE.id(), BlockLogicCrumbling.setMetadataFromStage(3)), 35.0f);
		stairs.addEntry(bd(BattleTowerBlocks.STAIRS_CRUMBLING_STONE.id(), BlockLogicCrumbling.setMetadataFromStage(2)), 25.0f);
		stairs.addEntry(bd(BattleTowerBlocks.STAIRS_CRUMBLING_STONE.id(), BlockLogicCrumbling.setMetadataFromStage(1)), 15.0f);
		stairs.addEntry(bd(BattleTowerBlocks.STAIRS_CRUMBLING_STONE.id(), BlockLogicCrumbling.setMetadataFromStage(0)), 10.0f);
	}

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
		this.maxHeight = y;
		this.setTowerProperties(this.world.getBlockBiome(x, y, z));
		placeTower(x, y, z, availableHeight);
		return true;
	}

	///  Checks if a block is part of the wall
	@Override
	protected boolean isWall(int ix, int iz) {
		boolean outerWall = (iz == 1 || ix == 14) || (ix == 1 || iz == 14);
		boolean column = (ix == 3 && iz == 3);
		boolean staircaseWalls = (((iz == 5) && (ix > 1 && ix < 6)) || ((ix == 5) && (iz > 1 && iz < 6))) && !BattleTowerConfig.isHardcore();
		return outerWall || column || staircaseWalls;
	}

	/// Checks if a block is within the tower (including walls)
	@Override
	protected boolean isInPerimeter(int ix, int iz) {
		boolean groove = (iz == 1 || ix == 14) || (ix == 1 || iz == 14);
		boolean inner = (ix > 1 && ix < 15) && (iz > 0 && iz < 15);
		return groove || inner;
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
			if (maxHeight > py + 7) {
				this.placeWindows(x, py, z, py == y);
				this.placeStaircase(x + 4, py, z + 2);
				this.placeHostileDecorations(x + 5, py + 1, z + 10);
				this.placeChests(x, py + 2, z, this.currentFloor, lootAmount);
			}
		}
		for (WorldFeatureVanquishedSquareTower.Entry e : this.doors) {
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
		for (int f = 0; f < maxFloors; f++) {
			height += 7 * 1.0f / ruinFactorLocal;
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
			if ((((addX > 0 && addX < 5) && (addZ > 0 && addZ < 5)) || addY != 0)) {
				this.canReplace(px, py, pz, BLOCK_AIR);
				return;
			}
			if (this.currentFloor == 0 || topFloor) {
				this.canReplace(px, py, pz, Blocks.STONE_POLISHED.id());
				return;
			}
			if (maxHeight > py + 7) {
				// Create holes for mob to fall through to lower level
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
		int windowID = (BattleTowerConfig.isTint() || BattleTowerConfig.isHardcore()) ? BattleTowerBlocks.PRISON_BAR_FENCE.id() : BLOCK_AIR;
		if (groundFloor) {
			this.placeBottomDoors(x, y, z, BLOCK_AIR);
		} else {
			this.placeWindowOnFloor(x, y, z, windowID);
		}
	}

	private void placeBottomDoors(int x, int y, int z, int placeID) {
		for (int c = 0; c < 2; c++) {
			int px = c * 13 + x + 1;
			int rz = c * 13 + z + 1;
			for (int iz = 0; iz < 2; iz++) {
				int pz = iz + z + 7;
				int rx = iz + x + 7;
				for (int iy = 0; iy < 4; iy++) {
					this.doors.add(new Entry(px, iy + y + 1, pz, placeID));
					this.doors.add(new Entry(rx, iy + y + 1, rz, placeID));
				}
			}
		}
	}

	private void placeWindowOnFloor(int x, int y, int z, int placeID) {
		for (int c = 0; c < 2; c++) {
			int px = c * 13 + x + 1;
			int rz = c * 13 + z + 1;
			for (int iz = 0; iz < 2; iz++) {
				int pz = iz + z + 7;
				int rx = iz + x + 7;
				for (int iy = 0; iy < 4; iy++) {
					int ey = iy + y + 2;
					if ((this.currentFloor & 1) == 0) {
						this.doors.add(new Entry(px, ey, pz, placeID));
						if (iy < 3) {
							this.doors.add(new Entry(px, ey, z + 4, placeID));
							this.doors.add(new Entry(px, ey, z + 11, placeID));
						}
					} else {
						this.doors.add(new Entry(rx, ey, rz, placeID));
						if (iy < 3) {
							this.doors.add(new Entry(x + 4, ey, rz, placeID));
							this.doors.add(new Entry(x + 11, ey, rz, placeID));
						}
					}
				}
				int wx = c * 15 + x;
				int wz = c * 15 + z;
				this.placeWindowOutterDecoration(x, y, z, wx, wz);
			}
		}
	}

	private void placeWindowOutterDecoration(int x, int y, int z, int wx, int wz) {
		if ((this.currentFloor & 1) == 0) {
			this.canReplace(wx, y + 1, z + 4, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(wx, y + 1, z + 7, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(wx, y + 1, z + 8, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(wx, y + 1, z + 11, this.buildingBlockBag.getRandom(this.random).id());

			this.canReplace(wx, y + 5, z + 4, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(wx, y + 6, z + 7, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(wx, y + 6, z + 8, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(wx, y + 5, z + 11, this.buildingBlockBag.getRandom(this.random).id());
		} else {
			this.canReplace(x + 4, y + 1, wz, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(x + 7, y + 1, wz, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(x + 8, y + 1, wz, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(x + 11, y + 1, wz, this.buildingBlockBag.getRandom(this.random).id());

			this.canReplace(x + 4, y + 5, wz, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(x + 7, y + 6, wz, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(x + 8, y + 6, wz, this.buildingBlockBag.getRandom(this.random).id());
			this.canReplace(x + 11, y + 5, wz, this.buildingBlockBag.getRandom(this.random).id());
		}
	}

	protected void placeStaircase(int x, int y, int z) {
		int barID = BattleTowerConfig.isHardcore() ? BLOCK_AIR : BattleTowerBlocks.PRISON_BAR_FENCE.id();
		/// placing bars
		for (int ix = 0; ix < 2; ix++) {
			for (int iy = 2; iy < 5; iy++) {
				this.world.setBlock(x - 2 + ix * 2, y + iy, z + 3, barID);
			}
		}

		for (int iz = 0; iz < 3; iz++) {
			for (int iy = 0; iy < 3; iy++) {
				this.doors.add(new Entry(x + 1, y + 1 + iy, z + iz, BLOCK_AIR));
			}
		}
		this.doors.add(new Entry(x + 1, y + 4, z + 1, BLOCK_AIR));

		/// placing doors
		this.placeStairsBlock(stairs.getRandom(random), x, y + 1, z, Direction.WEST);
		this.placeStairsBlock(stairs.getRandom(random), x - 1, y + 2, z, Direction.WEST);
		this.placeStairsBlock(stairs.getRandom(random), x - 2, y + 3, z, Direction.WEST);
		this.placeStairsBlock(stairs.getRandom(random), x - 2, y + 4, z + 1, Direction.SOUTH);
		this.placeStairsBlock(stairs.getRandom(random), x - 2, y + 5, z + 2, Direction.SOUTH);
		this.placeStairsBlock(stairs.getRandom(random), x - 1, y + 6, z + 2, Direction.EAST);
		this.placeStairsBlock(stairs.getRandom(random), x, y + 7, z + 2, Direction.EAST);
		this.world.setBlock(x, y + 7, z + 1, Blocks.STONE_POLISHED.id());

	}

	protected void placeStairsBlock(BlockData blockData, int x, int y, int z, Direction direction) {
		int metadata = MetadataHelper.Stairs.setMetadata(blockData.metadata(), direction, false);
		this.world.setBlockAndMetadata(x, y, z, blockData.id(), metadata);
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

	/// Places the rewards chests and the plinth
	@Override
	protected void placeChests(int x, int y, int z, int currentTier, int lootAmount) {
		boolean northSide = (this.currentFloor & 1) == 1;
		int tier = Math.min(this.currentFloor, currentTier);
		Direction dir = northSide ? Direction.NORTH : Direction.WEST;

		int ix = x + (northSide ? 7 : 10);
		int iz = z + (northSide ? 10 : 7);

		// Offsets for the second chest
		int dx = northSide ? 1 : 0;
		int dz = northSide ? 0 : 1;

		// Place both chests as SINGLE first
		for (int i = 0; i < 2; i++) {
			int cx = ix + dx * i;
			int cz = iz + dz * i;

			world.setBlockWithNotify(cx, y, cz, BattleTowerBlocks.TOWER_CHEST.id());
			world.setBlockMetadataWithNotify(cx, y, cz, getMetaWithType(getMetaWithDirection(world.getBlockMetadata(x, y, z), dir), BlockLogicChest.Type.SINGLE));
			populateChest(this.world, this.random, cx, y, cz, tier, lootAmount);
			world.setBlock(cx, y - 1, cz, Blocks.STONE_POLISHED.id());
			world.setBlock(cx, y - 2, cz, Blocks.STONE_POLISHED.id());
		}

		// Convert to double chest
		if (northSide) {
			BlockLogicChest.setType(world, new TilePos(ix, y, iz), BlockLogicChest.Type.RIGHT);
			BlockLogicChest.setType(world, new TilePos(ix + 1, y, iz), BlockLogicChest.Type.LEFT);
		} else {
			BlockLogicChest.setType(world, new TilePos(ix, y, iz), BlockLogicChest.Type.LEFT);
			BlockLogicChest.setType(world, new TilePos(ix, y, iz + 1), BlockLogicChest.Type.RIGHT);
		}
	}


}
