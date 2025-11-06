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

	public static final int LOOT_AMOUNT = 12;
	private int currentFloor = 1;
	private boolean isTopFloor = false;

	private WeightedRandomBag<Integer> cobbleBag;
	private World world;
	private Random random;
	private int towerDecoBlockID;
	private int offset = 0;

	public static final int FLOOR_HEIGHT = 7;
	public static final int FLOOR_LENGTH = 7;
	public static final int FLOOR_WIDTH = 7;

	public WorldGenTower() {
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		int blockId = world.getBlockId(x, y, z);
		if (blockId == Blocks.FLUID_WATER_STILL.id() || blockId == 0) {
			return false;
		}
		this.world = world;
		this.random = random;
		this.towerDecoBlockID = random.nextInt(11);
		this.cobbleBag = getRandomCobbledBlockBag(this.world.getBlockBiome(x, y, z), this.towerDecoBlockID);
		if (world.worldType instanceof WorldTypeOverworld) this.offset = 64;
		int currentHight = y - 6;
		for (; currentHight < 120 + this.offset; currentHight += FLOOR_HEIGHT) {
			if (currentHight + FLOOR_HEIGHT >= 120 + this.offset) {
				this.isTopFloor = true;
			}
			for (int height = 0; height < FLOOR_HEIGHT; height++) {
				if (currentHight == y - 6 && height < 4) {
					height = 4;
				}

				for (int width = -FLOOR_WIDTH; width < FLOOR_WIDTH; width++) {
					for (int length = -FLOOR_LENGTH; length < FLOOR_LENGTH; length++) {
						int ix = width + x;
						int iy = height + currentHight;
						int iz = length + z;

						if (length == -7) {
							if (width > -5 && width < 4) {
								world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
							}
							continue;
						}

						if (length == -6 || length == -5) {
							if (width == -5 || width == 4) {
								world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
								continue;
							}

							if (length == -6) {
								if (width == (height + 1) % 7 - 3) {
									world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());

									if (height == 5) {
										world.setBlock(ix - 7, iy, iz, Blocks.STONE_POLISHED.id());
									}

									if (height == 6 && this.isTopFloor) {
										world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
									}

									continue;
								}

								if (width < 4 && width > -5) {
									world.setBlock(ix, iy, iz, 0);
								}

								continue;
							}

							if (length != -5 || width <= -5 || width >= 5) {
								continue;
							}

							if (height != 0 && height != 6 || width != -4 && width != 3) {
								if (height == 5 && (width == 3 || width == -4)) {
									world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
								} else {
									world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
								}
							} else {
								world.setBlock(ix, iy, iz, 0);
							}

							continue;
						}
						if (length == -4 || length == -3 || length == 2 || length == 3) {
							if (width == -6 || width == 5) {
								world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
								continue;
							}

							if (width <= -6 || width >= 5) {
								continue;
							}

							if (height == 5) {
								world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
								continue;
							}

							if (world.getBlockId(ix, iy, iz) != 54) {
								//world.setBlock(ix, k6, iz, 0);
							}

							continue;
						}

						if (length > -3 && length < 2) {
							if (width == -7 || width == 6) {
								if (height < 0 || height > 3 || width != -7 && width != 6 || length != -1 && length != 0) {
									world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
								} else {
									world.setBlock(ix, iy, iz, 0);
								}

								continue;
							}

							if (width <= -7 || width >= 6) {
								continue;
							}

							if (height == 5) {
								world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
							} else {
								world.setBlock(ix, iy, iz, 0);
							}

							continue;
						}

						if (length == 4) {
							if (width == -5 || width == 4) {
								world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
								continue;
							}
							if (width <= -5 || width >= 4) {
								continue;
							}
							if (height == 5) {
								world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
							} else {
								world.setBlock(ix, iy, iz, 0);
							}
							continue;
						}

						if (length == 5) {
							if (width == -4 || width == -3 || width == 2 || width == 3) {
								world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
								continue;
							}
							if (width <= -3 || width >= 2) {
								continue;
							}
							if (height == 5) {
								world.setBlock(ix, iy, iz, Blocks.STONE_POLISHED.id());
							} else {
								world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
							}
							continue;
						}

						if (length != 6 || width <= -3 || width >= 2) {
							continue;
						}

						if (height < 0 || height > 3 || width != -1 && width != 0) {
							world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
						} else {
							world.setBlock(ix, iy, iz, this.cobbleBag.getRandom(random));
						}
					}

				}

			}

			if (currentFloor == 2) {
				world.setBlock(x + 3, currentHight, z - 5, this.cobbleBag.getRandom(random));
				world.setBlock(x + 3, currentHight - 1, z - 5, this.cobbleBag.getRandom(random));
			}

			if (this.isTopFloor) {
				spawnGolem(x, currentHight + 6, z + 0.5D);
			} else {
				setSpawners(x, currentHight, z);
			}

			world.setBlock(x, currentHight + 6, z - 3, Blocks.STONE_POLISHED.id());
			world.setBlock(x - 1, currentHight + 6, z - 3, Blocks.STONE_POLISHED.id());

			if (currentHight + 56 >= 120 && currentFloor == 1) {
				currentFloor = 2;
			}

			placeChests(x, currentHight, z);

			for (int l3 = 0; l3 < (currentFloor * 4 + towerDecoBlockID) - 8 && !this.isTopFloor; l3++) {
				int k4 = 5 - random.nextInt(LOOT_AMOUNT);
				int k5 = currentHight + 5;
				int j6 = 5 - random.nextInt(10);
				if (j6 < -2 && k4 < 4 && k4 > -5 && k4 != 1 && k4 != -2) {
					continue;
				}
				k4 += x;
				j6 += z;
				if (world.getBlockId(k4, k5, j6) == Blocks.STONE_POLISHED.id() && world.getBlockId(k4, k5 + 1, j6) != Blocks.MOBSPAWNER.id()) {
					world.setBlock(k4, k5, j6, 0);
				}
			}

			currentFloor++;
		}

		return true;
	}

	private void placeChests(int x, int y, int z) {
		placeChest(x, y + 7, z - 3);
		placeChest(x - 1, y + 7, z - 3);
		BlockLogicChest.setType(world, x, y + 7, z - 3, BlockLogicChest.Type.RIGHT);
		BlockLogicChest.setType(world, x - 1, y + 7, z - 3, BlockLogicChest.Type.LEFT);
	}

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

	/// ############################## no touchy zone ##################################################################

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

	private void spawnGolem(double x, double y, double z) {
		EntityGolem entitygolem = new EntityGolem(world, towerDecoBlockID);
		entitygolem.spawnInit();
		entitygolem.moveTo(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
		world.entityJoinedWorld(entitygolem);
		LOGGER.info("Spawned golem at {} {} {}", x, y, z);
	}

	private void setSpawners(int x, int y, int z) {
		this.setSpawner(x + 2, y + 6, z + 2);
		this.setSpawner(x - 3, y + 6, z + 2);
	}

	private void setSpawner( int x, int y, int z) {
		world.setBlockWithNotify(x, y, z, Blocks.MOBSPAWNER.id());
		TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
		tileentitymobspawner.setMobId(getRandomSpawnerMob());
	}
}
