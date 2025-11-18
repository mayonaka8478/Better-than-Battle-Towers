package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.BattleTowerConfig;
import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.entity.MobGolem;
import jamdoggie.betterbattletowers.worldgen.util.LootTable;
import jamdoggie.betterbattletowers.worldgen.util.TowerProperties;
import jamdoggie.betterbattletowers.worldgen.util.TowerProperty;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.feature.WorldFeature;

import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.util.LootTable.MAX_TIER;
import static jamdoggie.betterbattletowers.worldgen.util.LootTable.populateChest;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithDirection;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithType;

public class WorldFeatureBattleTower extends WorldFeature {
	private World world;
	private Random random;
	private int currentFloor = 0;
	private WeightedRandomBag<Integer> buildingBlockBag;
	private int golemVariant;

	///  Constants to avoid having magic numbers
	public static final int FLOOR_HEIGHT = 7;
	public static final int MIN_HEIGHT = 70;
	public static final int LOOT_AMOUNT = 9;

	/// Important for the placement via commands
	public WorldFeatureBattleTower() {
		// used by place command
	}

	public static WorldFeatureBattleTower tower() {
		return new WorldFeatureBattleTower();
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		LootTable.init();
		int availableHeight = world.getHeightBlocks() - y;
		int blockID = world.getBlockId(x + 8, y, z + 8);
		if (availableHeight <= MIN_HEIGHT || blockID == 0) {
			return false;
		}
		this.world = world;
		this.random = random;
		this.setTowerProperties(this.world.getBlockBiome(x, y, z));
		placeTower(x, y, z, availableHeight);
		return true;
	}

	///  Sets the golem type as well as the tower decorations
	private void setTowerProperties(Biome biome) {
		TowerProperty towerProperty = TowerProperties.getTowerProperties(biome, this.random);
		this.golemVariant = towerProperty.getSkinVariant();
		this.buildingBlockBag = towerProperty.getTowerDecorations();
	}

	///  Places tower
	private void placeTower(int x, int y, int z, int availableHeight) {
		int addedFloors = Math.floorDiv(availableHeight, FLOOR_HEIGHT) - FLOOR_HEIGHT;
		int maxFloors = this.random.nextInt(addedFloors) + FLOOR_HEIGHT;
		this.placeFoundation(x, y - 1, z);
		for (this.currentFloor = 0; this.currentFloor < maxFloors - 1; this.currentFloor += 1) {
			int py = y + FLOOR_HEIGHT * this.currentFloor;
			this.placeFloorShell(x, py, z, false, FLOOR_HEIGHT);
			this.placeWindows(x + 1, py + 2, z + 7, py == y);
			this.placeStaircase(x + 4, py, z + 3);
			this.placeHostileDecorations(x + 5, py + 1, z + 10);
			this.placeChests(x + 7, py + 2, z + 5, this.currentFloor);
		}
		this.placeCrown(x, y + FLOOR_HEIGHT * (maxFloors - 1), z);
	}

	///  Place the tower on a plinth
	private void placeFoundation(int x, int y, int z) {
		int sy = y;
		while (sy - 30 < y) {
			int count = 0;
			for (int ix = 0; ix < Chunk.CHUNK_SIZE_X; ix++) {
				for (int iz = 0; iz < Chunk.CHUNK_SIZE_Z; iz++) {
					int px = ix + x;
					int pz = iz + z;
					Material material = world.getBlockMaterial(px, y, pz);
					if (!this.isInPerimeter(ix, iz) || (!material.isLiquid() && material.isSolid())) {
						continue;
					}
					world.setBlock(px, y, pz, this.buildingBlockBag.getRandom(this.random));
					count++;
				}
			}
			if (count == 0) {
				return;
			}
			y--;
		}
	}

	///  Places the current floor, that includes wall and the actual floor
	private void placeFloorShell(int x, int y, int z, boolean topFloor, int height) {
		for (int iy = 0; iy < height; iy++) {
			for (int ix = 0; ix < Chunk.CHUNK_SIZE_X; ix++) {
				for (int iz = 0; iz < Chunk.CHUNK_SIZE_Z; iz++) {
					placeFloor(x, ix, y, iy, z, iz, topFloor);
				}
			}
		}
	}

	///  This function places the individual blocks of a given floor.
	private void placeFloor(int x, int addX, int y, int addY, int z, int addZ, boolean topFloor) {
		if (isInPerimeter(addX, addZ)) {
			int px = x + addX;
			int py = y + addY;
			int pz = z + addZ;
			if (isWall(addX, addZ)) {
				this.world.setBlock(px, py, pz, this.buildingBlockBag.getRandom(this.random));
				return;
			}
			if (((addX > 3 && addX < 12) && addZ == 2) && (this.currentFloor == 0 || topFloor)) {
				return;
			}
			if (addY != 0) {
				this.world.setBlock(px, py, pz, 0);
				return;
			}
			if (this.currentFloor == 0 || topFloor) {
				this.world.setBlock(px, py, pz, Blocks.STONE_POLISHED.id());
				return;
			}
			///  Create holes for mob to fall through to lower level
			int id = this.random.nextInt(6) == 0 ? 0 : Blocks.STONE_POLISHED.id();
			this.world.setBlock(px, py, pz, id);
		}
	}

	///  Checks if a block is part of the wall
	private final boolean isWall(int ix, int iz) {
		boolean wall0 = (iz == 1 || iz == 3 || iz == 14 || ix == 1 || ix == 14);
		boolean wall1 = (ix == 2 || ix == 13) && ((iz > 3 && iz < 6) || (iz > 9 && iz < 12));
		boolean wall2 = (ix == 3 || ix == 12) && ((iz > 1 && iz < 4) || (iz > 11 && iz < 14));
		return wall0 || wall1 || wall2;
	}

	/// Checks if a block is within the tower (including walls)
	private final boolean isInPerimeter(int ix, int iz) {
		boolean perimeter0 = (iz > 0 && iz < 15) && (ix > 3 && ix < 12);
		boolean perimeter1 = (ix == 3 || ix == 12) && (iz > 1 && iz < 14);
		boolean perimeter2 = (ix == 2 || ix == 13) && (iz > 3 && iz < 12);
		boolean perimeter3 = (ix == 1 || ix == 14) && (iz > 5 && iz < 10);
		return perimeter0 || perimeter1 || perimeter2 || perimeter3;
	}

	///  Places the windows
	private void placeWindows(int x, int y, int z, boolean groundFloor) {
		int windowID = BattleTowerConfig.isTint() ? BattleTowerBlocks.PRISON_BAR.id() : 0;
		int placeID = groundFloor ? 0 : windowID;
		for (int c = 0; c < 2; c++) {
			for (int iz = 0; iz < 2; iz++) {
				for (int iy = 0; iy < 4; iy++) {
					int px = c * 13 + x;
					int py = iy + y;
					int pz = iz + z;
					world.setBlock(px, py, pz, placeID);
				}
			}
		}
	}

	///  Places the staircase connecting 2 floors
	private final void placeStaircase(int x, int y, int z) {
		/// placing doors for
		for (int c = 0; c < 2; c++) {
			int px = c * 7 + x;
			for (int iy = 0; iy < 2; iy++) {
				int py = iy + y + 1;
				world.setBlock(px, py, z, 0);
			}
			world.setBlock(px, y, z, Blocks.STONE_POLISHED.id());
		}
		int pz = z - 1;
		world.setBlock(x, y, pz, Blocks.STONE_POLISHED.id());
		for (int ix = 0, iy = 0; ix < FLOOR_HEIGHT; ix++, iy++) {
			int px = ix + x + 1;
			int py = iy + y + 1;
			world.setBlockAndMetadata(px, py, pz, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 0);
		}
	}

	/// PLaces the spawners on the floor
	private final void placeHostileDecorations(int x, int y, int z) {
		this.setSpawner(x, y, z);
		this.setSpawner(x + 5, y, z);
	}

	/// Places the individual spawners and sets them up
	private void setSpawner(int x, int y, int z) {
		if (world.setBlockWithNotify(x, y, z, Blocks.MOBSPAWNER.id())) {
			TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
			tileentitymobspawner.setMobId(this.getRandomSpawnerMob());
		}
		world.setBlock(x, y - 1, z, Blocks.STONE_POLISHED.id());
	}

	///  Picks a mob to spawn for spawner
	private String getRandomSpawnerMob() {
		int i = random.nextInt(5);
		switch (i) {
			case 0:
				return "Skeleton";
			case 1:
			case 4:
				return "Zombie";
			case 2:
			case 3:
				return "Spider";
			default:
				return "Scorpion";
		}
	}

	/// Places the rewards chests and the plinth
	private void placeChests(int x, int y, int z, int currentTier) {
		int tier = Math.min(this.currentFloor, currentTier);
		placeChest(x, y, z, tier);
		placeChest(x + 1, y, z, tier);
		BlockLogicChest.setType(world, x, y, z, BlockLogicChest.Type.LEFT);
		BlockLogicChest.setType(world, x + 1, y, z, BlockLogicChest.Type.RIGHT);
		/// so the chest won't fly
		world.setBlock(x, y - 1, z, Blocks.STONE_POLISHED.id());
		world.setBlock(x + 1, y - 1, z, Blocks.STONE_POLISHED.id());
		/// fix the holes underneath the plinth
		world.setBlock(x, y - 2, z, Blocks.STONE_POLISHED.id());
		world.setBlock(x + 1, y - 2, z, Blocks.STONE_POLISHED.id());
	}

	/// Sets up the chest and fills it with loot
	private void placeChest(int x, int y, int z, int tier) {
		world.setBlockWithNotify(x, y, z, BattleTowerBlocks.TOWER_CHEST.id());
		world.setBlockMetadataWithNotify(x, y, z, getMetaWithType(getMetaWithDirection(world.getBlockMetadata(x, y, z), Direction.SOUTH), BlockLogicChest.Type.SINGLE));
		int lootAmount = BattleTowerConfig.isTint() ? LOOT_AMOUNT + 5 : LOOT_AMOUNT;
		populateChest(this.world, this.random, x, y, z, tier, lootAmount);
	}

	///  Places the top floor, that includes wall and the actual floor
	private void placeCrown(int x, int y, int z) {
		this.placeFloorShell(x, y, z, true, 2);
		this.placeChests(x + 7, y + 2, z + 5, MAX_TIER);
		this.placeGolem(x + 7.5, y + 1D, z + 7.5);
		createCrenulations(x, y, z);
		this.world.setBlock(x + 11, y + 1, z + 3, 0);
		this.world.setBlock(x + 11, y + 2, z + 3, 0);
		this.world.setBlock(x + 11, y, z + 3, Blocks.STONE_POLISHED.id());
	}

	///  Spawns and moves the golem to the top floor
	private void placeGolem(double x, double y, double z) {
		MobGolem golem = new MobGolem(world);
		golem.setSkinVariant(this.golemVariant);
		golem.spawnInit();
		golem.moveTo(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
		world.entityJoinedWorld(golem);
		LOGGER.info("Spawned golem at {} {} {}", x, y, z);
	}

	///  Decorate the crown with grenulations
	private void createCrenulations(int x, int y, int z) {
		int crenulation = 0;
		for (int ix = 0; ix < Chunk.CHUNK_SIZE_X; ix++) {
			for (int iz = 0; iz < Chunk.CHUNK_SIZE_X; iz++) {
				if(isInPerimeter(ix, iz) && isWall(ix, iz) && (crenulation++ & 1)== 0){
					this.world.setBlock(ix + x, y + 2, iz + z, this.buildingBlockBag.getRandom(this.random));
				}
			}
		}
	}

}
