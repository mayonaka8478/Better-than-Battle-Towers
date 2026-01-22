package jamdoggie.betterbattletowers.worldgen.structures;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.config.BattleTowerConfig;
import jamdoggie.betterbattletowers.worldgen.data.decoration.BlockData;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.data.loader.LootDataLoader.MAX_TIER;
import static jamdoggie.betterbattletowers.worldgen.data.loader.LootDataLoader.populateChest;
import static jamdoggie.betterbattletowers.worldgen.structures.WorldFeatureBattleTower.MIN_HEIGHT;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithDirection;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithType;

public class WorldFeatureSquareTower extends WorldFeatureTower {

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

	@Override
	protected void placeTower(int x, int y, int z, int availableHeight) {
		int addedFloors = MathHelper.ceilInt(availableHeight, FLOOR_HEIGHT) - MIN_HEIGHT / FLOOR_HEIGHT;
		int maxFloors = this.random.nextInt(addedFloors) + FLOOR_HEIGHT;
		int lootAmount = getLootAmount();
		this.placeFoundation(x, y - 1, z);
		this.placeCapStaircase(x, y, z);
		for (this.currentFloor = 0; this.currentFloor < maxFloors - 1; this.currentFloor += 1) {
			int py = y + FLOOR_HEIGHT * this.currentFloor;
			this.placeFloorShell(x, py, z, false, FLOOR_HEIGHT);
			this.placeWindows(x, py, z, py == y);
			this.placeStaircase(x + 4, py, z + 2);
			this.placeHostileDecorations(x + 5, py + 1, z + 10);
			this.placeChests(x, py + 2, z, this.currentFloor, lootAmount);
		}
		this.placeCrown(x, y + FLOOR_HEIGHT * (maxFloors - 1), z);
	}

	///  Places the windows
	@Override
	protected void placeWindows(int x, int y, int z, boolean groundFloor) {
		int windowID = (BattleTowerConfig.isTint() || BattleTowerConfig.isHardcore() )? BattleTowerBlocks.PRISON_BAR.id() : BattleTowerBlocks.PRISON_BAR_FENCE.id();
		int placeID = groundFloor ? BLOCK_AIR : windowID;
		if (groundFloor) {
			this.placeBottomDoors(x, y, z, placeID);
		} else {
			this.placeWindowOnFloor(x, y, z, placeID);
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
						world.setBlock(px, ey, pz, placeID);
						if (iy < 3) {
							world.setBlock(px, ey, z + 4, placeID);
							world.setBlock(px, ey, z + 11, placeID);
						}
					} else {
						world.setBlock(rx, ey, rz, placeID);
						if (iy < 3) {
							world.setBlock(x + 4, ey, rz, placeID);
							world.setBlock(x + 11, ey, rz, placeID);
						}
					}
				}
				int wx = c * 15 + x;
				int wz = c * 15 + z;
				this.placeWindowOutterDecoration(x, y, z, wx, wz);
			}
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
					world.setBlock(px, iy + y + 1, pz, placeID);
					world.setBlock(rx, iy + y + 1, rz, placeID);
				}
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

	@Override
	protected void placeFloor(int x, int addX, int y, int addY, int z, int addZ, boolean topFloor) {
		if (this.isInPerimeter(addX, addZ)) {
			int px = x + addX;
			int py = y + addY;
			int pz = z + addZ;
			if (this.isWall(addX, addZ)) {
				this.placeBlock(this.buildingBlockBag.getRandom(random), px, py, pz);
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
			// Create holes for mob to fall through to lower level
			BlockData data = this.floorBlockBag.getRandom(random);
			this.canReplace(px, py, pz, data.id(), data.metadata());
		}
	}

	@Override
	protected int getLootAmount() {
		return BattleTowerConfig.isTint() ? (int) Math.floor(LOOT_AMOUNT * 0.2) + LOOT_AMOUNT : LOOT_AMOUNT;
	}

	private void placeCrown(int x, int y, int z) {
		int lootAmount = BattleTowerConfig.isTint() ? LOOT_AMOUNT + 3 : LOOT_AMOUNT;
		this.placeFloorShell(x, y, z, true, 2);
		this.placeChests(x, y + 2, z, MAX_TIER, lootAmount);
		this.placeGolem(x + 7.5, y + 1D, z + 7.5);
		this.createCrenulations(x, y, z);

		for (int iy = 0; iy < 2; iy++) {
			world.setBlock(x + 5, y + 1 + iy, z + 2, BLOCK_AIR);
			world.setBlock(x + 5, y + 1 + iy, z + 3, BLOCK_AIR);
			world.setBlock(x + 5, y + 1 + iy, z + 4, BLOCK_AIR);
		}
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

	@Override
	protected void createCrenulations(int x, int y, int z) {
		for (int ix = 0; ix < Chunk.CHUNK_SIZE_X; ix++) {
			for (int iz = 0; iz < Chunk.CHUNK_SIZE_X; iz++) {
				if (this.isInPerimeter(ix, iz) && this.isWall(ix, iz)) {
					boolean top = (ix & 1) == 0 && iz == 1;
					boolean bot = (ix & 1) == 1 && iz == 14;
					boolean sidewest = (iz & 1) == 0 && ix == 1;
					boolean sideeast = (iz & 1) == 1 && ix == 14;
					boolean topsecond = iz == 5 && (ix > 0 && ix < 6) && (ix & 1) == 0;
					boolean westsecond = ix == 5 && (iz > 0 && iz < 6) && (iz & 1) == 0;
					if (top || bot || sidewest || sideeast || topsecond || westsecond) {
						this.placeBlock(this.buildingBlockBag.getRandom(random), x + ix, y + 2, z + iz);
					}
				}
			}
		}
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
			BlockLogicChest.setType(world, ix, y, iz, BlockLogicChest.Type.RIGHT);
			BlockLogicChest.setType(world, ix + 1, y, iz, BlockLogicChest.Type.LEFT);
		} else {
			BlockLogicChest.setType(world, ix, y, iz, BlockLogicChest.Type.LEFT);
			BlockLogicChest.setType(world, ix, y, iz + 1, BlockLogicChest.Type.RIGHT);
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
				this.world.setBlock(x + 1, y + 1 + iy, z + iz, BLOCK_AIR);
			}
		}
		this.world.setBlock(x + 1, y + 4, z + 1, BLOCK_AIR);

		/// placing doors
		this.world.setBlockAndMetadata(x, y + 1, z, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 1);
		this.world.setBlockAndMetadata(x - 1, y + 2, z, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 1);
		this.world.setBlockAndMetadata(x - 2, y + 3, z, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 1);
		this.world.setBlockAndMetadata(x - 2, y + 4, z + 1, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 2);
		this.world.setBlockAndMetadata(x - 2, y + 5, z + 2, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 2);
		this.world.setBlockAndMetadata(x - 1, y + 6, z + 2, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 0);
		this.world.setBlockAndMetadata(x, y + 7, z + 2, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 0);
		this.world.setBlock(x, y + 7, z + 1, Blocks.STONE_POLISHED.id());

	}


	///  Fill the hole at the bottom of the staircase
	protected void placeCapStaircase(int x, int y, int z) {
		for (int ix = 0; ix < 3; ix++) {
			for (int iz = 0; iz < 3; iz++) {
				this.world.setBlock(x + ix + 2, y, z + iz + 2, Blocks.STONE_POLISHED.id());
			}
		}
	}
}
