package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.block.ModBlocks;
import jamdoggie.betterbattletowers.entity.EntityGolem;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.type.overworld.WorldTypeOverworld;

import static jamdoggie.betterbattletowers.BetterBattleTowers.LOGGER;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.BlockPallets.getRandomCobbledBlockBag;
import static jamdoggie.betterbattletowers.worldgen.LootTable.generateRandomChestLoot;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithDirection;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithType;

public class WorldGenTower extends WorldFeature {
	private World world;
	private Random random;
	private int currentFloor = 1;
	private boolean isTopFloor = false;
	private WeightedRandomBag<Integer> buildingBlockBag;
	private int towerDecoBlockID;
	private int offset = 0;

	///  Constants to avoid having magic numbers
	public static final int FLOOR_HEIGHT = 7;
	public static final int FLOOR_LENGTH = 7;
	public static final int FLOOR_WIDTH = 7;
	public static final int LOOT_AMOUNT = 12;
	public static final int MAX_HEIGHT = 120;

	/// Important for the placement via commands
	public WorldGenTower() {}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		int blockId = world.getBlockId(x, y, z);
		if (blockId == Blocks.FLUID_WATER_STILL.id() || blockId == 0) {
			return false;
		}
		this.world = world;
		this.random = random;
		this.towerDecoBlockID = random.nextInt(11);
		this.buildingBlockBag = getRandomCobbledBlockBag(this.world.getBlockBiome(x, y, z), this.towerDecoBlockID);
		if (world.worldType instanceof WorldTypeOverworld) this.offset = 64;
		for (int currentHeight = y - 6; currentHeight < MAX_HEIGHT + this.offset; currentHeight += FLOOR_HEIGHT) {
			if (currentHeight + FLOOR_HEIGHT >= MAX_HEIGHT + this.offset) {
				this.isTopFloor = true;
			}
			placeCurrentFloor(x, currentHeight, z, y - 6);
			placeHolesInFloor(x, currentHeight + 5, z);
			placeHostileDecorations(x, currentHeight + 6, z);
			skipAFloor(currentHeight);
			placeChests(x, currentHeight + 7, z - 3);
//			mysteryFunction( x + 3, currentHeight, z - 5); // commented this out cause it didnt do anything
			this.currentFloor++;

		}
		return true;
	}

	/// Not sure what this does, as it seem to do nothing
	private void mysteryFunction(int x, int y, int z) {
		if (this.currentFloor == 2) {
			world.setBlock(x, y, z, this.buildingBlockBag.getRandom(random));
			world.setBlock(x, y - 1, z, this.buildingBlockBag.getRandom(random));
		}
	}

	///  Places the current floor, that includes wall and the actual floor
	private void placeCurrentFloor(int x, int y, int z, int floorDiv) {
		for (int height = 0; height < FLOOR_HEIGHT; height++) {
			if ((y == floorDiv) && (height < 4)) {
				height = 4;
			}
			for (int width = -FLOOR_WIDTH; width < FLOOR_WIDTH; width++) {
				for (int length = -FLOOR_LENGTH; length < FLOOR_LENGTH; length++) {
					placeCurrentFloor(x, y, z, width, height, length);
				}
			}
		}
	}

	///  This function places the individual blocks of a given floor.
	private void placeCurrentFloor(int x, int y, int z, int width, int height, int length) {
		int ix = width + x;
		int iy = height + y;
		int iz = length + z;

		if (length == -7) {
			if (width > -5 && width < 4) {
				this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
			}
			return;
		}

		if (length == -6 || length == -5) {
			if (width == -5 || width == 4) {
				this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
				return;
			}

			if (length == -6) {
				if (width == (height + 1) % 7 - 3) {
					this.world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());

					if (height == 5) {
						this.world.setBlock(ix - 7, iy, iz, Blocks.STONE_POLISHED.id());
					}

					if (height == 6 && this.isTopFloor) {
						this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
					}

					return;
				}

				if (width < 4 && width > -5) {
					this.world.setBlock(ix, iy, iz, 0);
				}

				return;
			}

			if (width <= -5 || width >= 5) {
				return;
			}

			if (height != 0 && height != 6 || width != -4 && width != 3) {
				if (height == 5 && (width == 3 || width == -4)) {
					this.world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
				} else {
					this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
				}
			} else {
				world.setBlock(ix, iy, iz, 0);
			}

			return;
		}
		if (length == -4 || length == -3 || length == 2 || length == 3) {
			if (width == -6 || width == 5) {
				this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
				return;
			}
			if (width <= -6 || width >= 5) {
				return;
			}
			if (height == 5) {
				this.world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
				return;
			}
			return;
		}
		if (length < 2) {
			if (width == -7 || width == 6) {
				if (height < 0 || height > 3 || length != -1 && length != 0) {
					this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
				} else {
					this.world.setBlock(ix, iy, iz, 0);
				}

				return;
			}

			if (height == 5) {
				this.world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
			} else {
				this.world.setBlock(ix, iy, iz, 0);
			}

			return;
		}

		if (length == 4) {
			if (width == -5 || width == 4) {
				this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
				return;
			}
			if (width <= -5 || width >= 4) {
				return;
			}
			if (height == 5) {
				this.world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
			} else {
				this.world.setBlock(ix, iy, iz, 0);
			}
			return;
		}

		if (length == 5) {
			if (width == -4 || width == -3 || width == 2 || width == 3) {
				this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
				return;
			}
			if (width <= -3 || width >= 2) {
				return;
			}
			if (height == 5) {
				this.world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
			} else {
				this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
			}
			return;
		}

		if (width <= -3 || width >= 2) {
			return;
		}
		this.world.setBlock(ix, iy, iz, this.buildingBlockBag.getRandom(this.random));
	}

	/// In case the tower spawn quite height to prevent the top to be cut off we skip the second floor.
	private void skipAFloor(int currentHight) {
		if (currentHight + 56 >= MAX_HEIGHT && this.currentFloor == 1) {
			this.currentFloor++;
		}
	}

	///  Create holes for mob to fall through to lower level
	private void placeHolesInFloor(int x, int y, int z) {
		if(this.isTopFloor){
			return;
		}
		for (int count = 0; count < (this.currentFloor * 4 + 3); count++) {
			int ix = 5 - this.random.nextInt(12);
			int iz = 5 - this.random.nextInt(10);

			/// lies outside the towers perimeter
			if (iz < -2 && ix < 4 && ix > -5 && ix != 1 && ix != -2) {
				continue;
			}
			ix += x;
			iz += z;
			if (this.world.getBlockId(ix, y, iz) == Blocks.STONE_POLISHED.id()) {
				this.world.setBlock(ix, y, iz, 0);
			}
		}
	}

	/// PLaces the spawner or golem if on the top floor
	private void placeHostileDecorations(int x, int y, int z) {
		if (this.isTopFloor) {
			this.spawnGolem(x, y, z + 0.5D);
		} else {
			this.setSpawner(x + 2, y, z + 2);
			this.setSpawner(x - 3, y, z + 2);
		}
	}

	///  Spawns and moves the golem to the top floor
	private void spawnGolem(double x, double y, double z) {
		EntityGolem entitygolem = new EntityGolem(world, towerDecoBlockID);
		entitygolem.spawnInit();
		entitygolem.moveTo(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
		world.entityJoinedWorld(entitygolem);
		LOGGER.info("Spawned golem at {} {} {}", x, y, z);
	}


	/// Places the individual spawners and sets them up
	private void setSpawner( int x, int y, int z) {
		world.setBlockWithNotify(x, y, z, Blocks.MOBSPAWNER.id());
		TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
		tileentitymobspawner.setMobId(this.getRandomSpawnerMob());
	}

	///  Picks a mob to spawn using the spawners in placeHostileDecorations
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

	/// Places the rewards chests and the plinth open the chest sits
	private void placeChests(int x, int y, int z) {
		placeChest(x, y, z);
		placeChest(x - 1, y, z);
		BlockLogicChest.setType(world, x, y, z, BlockLogicChest.Type.RIGHT);
		BlockLogicChest.setType(world, x - 1, y, z, BlockLogicChest.Type.LEFT);
		world.setBlock(x, y - 1, z, Blocks.STONE_POLISHED.id());
		world.setBlock(x - 1, y - 1, z, Blocks.STONE_POLISHED.id());
	}

	/// Places the chest, sets up its tile entity and populated the chest with loot.
	private void placeChest(int x, int y, int z) {
		world.setBlockWithNotify(x, y, z, ModBlocks.ChestTower.id());
		world.setBlockMetadataWithNotify(x, y, z, getMetaWithType(getMetaWithDirection(world.getBlockMetadata(x, y, z), Direction.SOUTH), BlockLogicChest.Type.SINGLE));
		TileEntity tile = world.getTileEntity(x, y, z);
		if(!(tile instanceof TileEntityChest)) return;
		TileEntityChest tileEntityChest = (TileEntityChest) tile;
		for (int i = 0; i < LOOT_AMOUNT; i++) {
			ItemStack itemstack = generateRandomChestLoot(currentFloor, random, isTopFloor);
			if (itemstack != null) {
				tileEntityChest.setItem(random.nextInt(tileEntityChest.getContainerSize()), itemstack);
			}
		}
	}
}
