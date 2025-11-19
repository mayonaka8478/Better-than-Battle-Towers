package jamdoggie.betterbattletowers.worldgen;


import jamdoggie.betterbattletowers.BattleTowerConfig;
import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.entity.MobGolem;
import jamdoggie.betterbattletowers.worldgen.util.TowerProperties;
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

import java.util.Random;

import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;
import static jamdoggie.betterbattletowers.worldgen.util.LootTable.populateChest;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithDirection;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithType;

/// Base class for tower
public abstract class WorldFeatureTower extends WorldFeature {
	protected World world;
	protected Random random;
	protected int currentFloor = 0;
	protected WeightedRandomBag<Integer> buildingBlockBag;
	protected int golemVariant;

	///  Constants to avoid having magic numbers
	public static final int FLOOR_HEIGHT = 7;
	public static final int LOOT_AMOUNT = 9;
	public static final int BLOCK_AIR = 0;

	///  Sets the golem type as well as the tower decorations
	protected final void setTowerProperties(Biome biome) {
		TowerProperties.TowerProperty towerProperty = TowerProperties.getTowerProperties(biome, this.random);
		this.golemVariant = towerProperty.getSkinVariant();
		this.buildingBlockBag = towerProperty.getTowerDecorations();
	}

	///  Places tower
	protected abstract void placeTower(int x, int y, int z, int availableHeight);

	protected abstract int getLootAmount();

	///  Place the tower on a plinth
	protected void placeFoundation(int x, int y, int z) {
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

	///  Checks if a block is part of the wall
	protected final boolean isWall(int ix, int iz) {
		boolean wall0 = (iz == 1 || iz == 3 || iz == 14 || ix == 1 || ix == 14);
		boolean wall1 = (ix == 2 || ix == 13) && ((iz > 3 && iz < 6) || (iz > 9 && iz < 12));
		boolean wall2 = (ix == 3 || ix == 12) && ((iz > 1 && iz < 4) || (iz > 11 && iz < 14));
		return wall0 || wall1 || wall2;
	}

	/// Checks if a block is within the tower (including walls)
	protected final boolean isInPerimeter(int ix, int iz) {
		boolean perimeter0 = (iz > 0 && iz < 15) && (ix > 3 && ix < 12);
		boolean perimeter1 = (ix == 3 || ix == 12) && (iz > 1 && iz < 14);
		boolean perimeter2 = (ix == 2 || ix == 13) && (iz > 3 && iz < 12);
		boolean perimeter3 = (ix == 1 || ix == 14) && (iz > 5 && iz < 10);
		return perimeter0 || perimeter1 || perimeter2 || perimeter3;
	}

	///  Places the current floor, that includes wall and the actual floor
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
	protected void placeFloor(int x, int addX, int y, int addY, int z, int addZ, boolean topFloor) {
		if (isInPerimeter(addX, addZ)) {
			int px = x + addX;
			int py = y + addY;
			int pz = z + addZ;
			if (isWall(addX, addZ)) {
				this.world.setBlock(px, py, pz, this.buildingBlockBag.getRandom(this.random));
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
			///  Create holes for mob to fall through to lower level
			int id = this.random.nextInt(6) == 0 ? BLOCK_AIR : Blocks.STONE_POLISHED.id();
			this.canReplace(px, py, pz, id);
		}
	}

	protected final void canReplace(int x, int y, int z, int placeID) {
		int blockID = this.world.getBlockId(x,y,z);
		if(blockID == Blocks.STONE_POLISHED.id() || blockID == Blocks.STAIRS_BRICK_STONE_POLISHED.id()){
			return;
		}
		this.world.setBlock(x,y,z, placeID);
	}

	///  Places the windows
	protected void placeWindows(int x, int y, int z, boolean groundFloor) {
		int windowID = BattleTowerConfig.isTint() ? BattleTowerBlocks.PRISON_BAR.id() : BLOCK_AIR;
		int placeID = groundFloor ? BLOCK_AIR : windowID;
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

	/// PLaces the spawners on the floor
	protected void placeHostileDecorations(int x, int y, int z) {
		this.setSpawner(x, y, z);
		this.setSpawner(x + 5, y, z);
	}

	/// Places the individual spawners and sets them up
	protected final void setSpawner(int x, int y, int z) {
		if (world.setBlockWithNotify(x, y, z, Blocks.MOBSPAWNER.id())) {
			TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
			tileentitymobspawner.setMobId(this.getRandomSpawnerMob());
		}
		world.setBlock(x, y - 1, z, Blocks.STONE_POLISHED.id());
	}

	///  Picks a mob to spawn for spawner
	protected String getRandomSpawnerMob() {
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

	///  Places the staircase connecting 2 floors
	protected void placeStaircase(int x, int y, int z) {
		/// placing doors for
		for (int c = 0; c < 2; c++) {
			int px = c * 7 + x;
			for (int iy = 0; iy < 2; iy++) {
				int py = iy + y + 1;
				this.world.setBlock(px, py, z, BLOCK_AIR);
			}
			this.world.setBlock(px, y, z, Blocks.STONE_POLISHED.id());
		}
		int pz = z - 1;
		this.world.setBlock(x, y, pz, Blocks.STONE_POLISHED.id());
		for (int ix = 0, iy = 0; ix < FLOOR_HEIGHT; ix++, iy++) {
			int px = ix + x + 1;
			int py = iy + y + 1;
			this.world.setBlockAndMetadata(px, py, pz, Blocks.STAIRS_BRICK_STONE_POLISHED.id(), 0);
		}
	}

	/// Places the rewards chests and the plinth
	protected void placeChests(int x, int y, int z, int currentTier, int lootAmount) {
		int tier = Math.min(this.currentFloor, currentTier);
		this.placeChest(x, y, z, tier, lootAmount);
		this.placeChest(x + 1, y, z, tier, lootAmount);
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
	protected final void placeChest(int x, int y, int z, int tier, int lootAmount) {
		world.setBlockWithNotify(x, y, z, BattleTowerBlocks.TOWER_CHEST.id());
		world.setBlockMetadataWithNotify(x, y, z, getMetaWithType(getMetaWithDirection(world.getBlockMetadata(x, y, z), Direction.SOUTH), BlockLogicChest.Type.SINGLE));
		populateChest(this.world, this.random, x, y, z, tier, lootAmount);
	}

	///  Fill the hole at the bottom of the staircase
	protected void placeCapStaircase(int x, int y, int z){
		for(int ix = 0; ix < Chunk.CHUNK_SIZE_X; ix++){
			if ((ix > 3 && ix < 12)) {
				int iy = ix - 4;
				if (iy > 2) {
					for(int cy = 0; cy < iy; cy++){
						this.world.setBlock(x + ix, y + cy, z + 2, Blocks.STONE_POLISHED.id());
					}
				}
				this.world.setBlock(x + ix, y, z + 2, Blocks.STONE_POLISHED.id());
			}
		}
		this.world.setBlock(x + 10, y + 1, z + 3, this.buildingBlockBag.getRandom(this.random));
		this.world.setBlock(x + 10, y + 2, z + 3, this.buildingBlockBag.getRandom(this.random));
	}

	///  Spawns and moves the golem to the top floor
	protected final void placeGolem(double x, double y, double z) {
		MobGolem golem = new MobGolem(world);
		golem.setSkinVariant(this.golemVariant);
		golem.spawnInit();
		golem.moveTo(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
		world.entityJoinedWorld(golem);
		LOGGER.info("Spawned golem at {} {} {}", x, y, z);
	}

	///  Decorate the crown with grenulations
	protected void createCrenulations(int x, int y, int z) {
		int crenulation = 0;
		for (int ix = 0; ix < Chunk.CHUNK_SIZE_X; ix++) {
			for (int iz = 0; iz < Chunk.CHUNK_SIZE_X; iz++) {
				if (isInPerimeter(ix, iz) && isWall(ix, iz) && (crenulation++ & 1) == 0) {
					this.world.setBlock(ix + x, y + 2, iz + z, this.buildingBlockBag.getRandom(this.random));
				}
			}
		}
	}

}
