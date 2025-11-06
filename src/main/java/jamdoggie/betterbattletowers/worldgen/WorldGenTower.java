package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.LootCofigUtils;
import jamdoggie.betterbattletowers.block.ModBlocks;
import jamdoggie.betterbattletowers.entity.EntityGolem;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.type.overworld.WorldTypeOverworld;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.BlockPaletts.getRandomCobbledBlockBag;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithDirection;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithType;

public class WorldGenTower extends WorldFeature {

	private int currentFloor;
	private int field_22237_field_20341_topFloor;
	private WeightedRandomBag<Integer> cobbleBag;
	private World world;
	private Random random;
	private int towerDecoBlockID;
	private int offset = 0;

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


		int k2 = y - 6;




		currentFloor = 1;
		field_22237_field_20341_topFloor = 0;

		for (; k2 < 120 + offset; k2 += 7) {
			if (k2 + 7 >= 120 + offset) {
				field_22237_field_20341_topFloor = 1;
			}
			for (int j3 = 0; j3 < 7; j3++) {
				if (k2 == y - 6 && j3 < 4) {
					j3 = 4;
				}

				for (int j4 = -7; j4 < 7; j4++) {
					for (int i5 = -7; i5 < 7; i5++) {
						int i6 = j4 + x;
						int k6 = j3 + k2;
						int l6 = i5 + z;

						if (i5 == -7) {
							if (j4 > -5 && j4 < 4) {
								world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
							}
							continue;
						}

						if (i5 == -6 || i5 == -5) {
							if (j4 == -5 || j4 == 4) {
								world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
								continue;
							}

							if (i5 == -6) {
								if (j4 == (j3 + 1) % 7 - 3) {
									world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());

									if (j3 == 5) {
										world.setBlock(i6 - 7, k6, l6, Blocks.STONE_POLISHED.id());
									}

									if (j3 == 6 && field_22237_field_20341_topFloor == 1) {
										world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
									}

									continue;
								}

								if (j4 < 4 && j4 > -5) {
									world.setBlock(i6, k6, l6, 0);
								}

								continue;
							}

							if (i5 != -5 || j4 <= -5 || j4 >= 5) {
								continue;
							}

							if (j3 != 0 && j3 != 6 || j4 != -4 && j4 != 3) {
								if (j3 == 5 && (j4 == 3 || j4 == -4)) {
									world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());
								} else {
									world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
								}
							} else {
								world.setBlock(i6, k6, l6, 0);
							}

							continue;
						}
						if (i5 == -4 || i5 == -3 || i5 == 2 || i5 == 3) {
							if (j4 == -6 || j4 == 5) {
								world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
								continue;
							}

							if (j4 <= -6 || j4 >= 5) {
								continue;
							}

							if (j3 == 5) {
								world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());
								continue;
							}

							if (world.getBlockId(i6, k6, l6) != 54) {
								//world.setBlock(i6, k6, l6, 0);
							}

							continue;
						}

						if (i5 > -3 && i5 < 2) {
							if (j4 == -7 || j4 == 6) {
								if (j3 < 0 || j3 > 3 || j4 != -7 && j4 != 6 || i5 != -1 && i5 != 0) {
									world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
								} else {
									world.setBlock(i6, k6, l6, 0);
								}

								continue;
							}

							if (j4 <= -7 || j4 >= 6) {
								continue;
							}

							if (j3 == 5) {
								world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());
							} else {
								world.setBlock(i6, k6, l6, 0);
							}

							continue;
						}

						if (i5 == 4) {
							if (j4 == -5 || j4 == 4) {
								world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
								continue;
							}
							if (j4 <= -5 || j4 >= 4) {
								continue;
							}
							if (j3 == 5) {
								world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());
							} else {
								world.setBlock(i6, k6, l6, 0);
							}
							continue;
						}

						if (i5 == 5) {
							if (j4 == -4 || j4 == -3 || j4 == 2 || j4 == 3) {
								world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
								continue;
							}
							if (j4 <= -3 || j4 >= 2) {
								continue;
							}
							if (j3 == 5) {
								world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());
							} else {
								world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
							}
							continue;
						}

						if (i5 != 6 || j4 <= -3 || j4 >= 2) {
							continue;
						}

						if (j3 < 0 || j3 > 3 || j4 != -1 && j4 != 0) {
							world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
						} else {
							world.setBlock(i6, k6, l6, this.cobbleBag.getRandom(random));
						}
					}

				}

			}

			if (currentFloor == 2) {
				world.setBlock(x + 3, k2, z - 5, this.cobbleBag.getRandom(random));
				world.setBlock(x + 3, k2 - 1, z - 5, this.cobbleBag.getRandom(random));
			}

			if (field_22237_field_20341_topFloor == 1) {
				double d = x;
				double d1 = k2 + 6;
				double d2 = (double) z + 0.5D;
				EntityGolem entitygolem = new EntityGolem(world, towerDecoBlockID);
				entitygolem.spawnInit();
				entitygolem.moveTo(d, d1, d2, world.rand.nextFloat() * 360F, 0.0F);
				world.entityJoinedWorld(entitygolem);
				System.out.println("Spawned golem at " + d + ", " + d1 + ", " + d2);
			} else {
				world.setBlockWithNotify(x + 2, k2 + 6, z + 2, Blocks.MOBSPAWNER.id());
				TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(x + 2, k2 + 6, z + 2);
				tileentitymobspawner.setMobId(getRandomSpawnerMob(random));
				world.setBlockWithNotify(x - 3, k2 + 6, z + 2, Blocks.MOBSPAWNER.id());
				TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner) world.getTileEntity(x - 3, k2 + 6, z + 2);
				tileentitymobspawner1.setMobId(getRandomSpawnerMob(random));
			}

			world.setBlock(x, k2 + 6, z - 3, Blocks.STONE_POLISHED.id());
			world.setBlock(x - 1, k2 + 6, z - 3, Blocks.STONE_POLISHED.id());

			if (k2 + 56 >= 120 && currentFloor == 1) {
				currentFloor = 2;
			}

			for (int k3 = 0; k3 < 2; k3++) {
				world.setBlockWithNotify(x - k3, k2 + 7, z - 3, ModBlocks.ChestTower.id());
				int adjX = x - k3;
				int adjY = k2 + 7;
				int adjZ = z - 3;
				world.setBlockMetadataWithNotify(adjX, adjY, adjZ, getMetaWithType(getMetaWithDirection(world.getBlockMetadata(adjX, adjY, adjZ), Direction.SOUTH), BlockLogicChest.Type.SINGLE));
				TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(x - k3, k2 + 7, z - 3);
				for (int j5 = 0; j5 < 1 + k3 + towerDecoBlockID; j5++) {
					ItemStack itemstack = generateRandomChestLoot(currentFloor, random);
					if (itemstack != null) {
						tileEntityChest.setItem(random.nextInt(tileEntityChest.getContainerSize()), itemstack);
					}
				}

			}
			BlockLogicChest.setType(world, x, k2 + 7, z - 3, BlockLogicChest.Type.RIGHT);
			BlockLogicChest.setType(world, x - 1, k2 + 7, z - 3, BlockLogicChest.Type.LEFT);

			for (int l3 = 0; l3 < (currentFloor * 4 + towerDecoBlockID) - 8 && field_22237_field_20341_topFloor != 1; l3++) {
				int k4 = 5 - random.nextInt(12);
				int k5 = k2 + 5;
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

	private ItemStack generateRandomChestLoot(int towerLevel, Random random) {
		int j = random.nextInt(4);
		if (field_22237_field_20341_topFloor == 1) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitemtop_0);
				item.stackSize = random.nextInt(2) + 1;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitemtop_1);
				item.stackSize = random.nextInt(2) + 1;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitemtop_2);
				item.stackSize = random.nextInt(2) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitemtop_3);
				item.stackSize = random.nextInt(1) + 1;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 1) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem1_0);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem1_1);
				item.stackSize = random.nextInt(2) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem1_2);
				item.stackSize = random.nextInt(3) + 3;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem1_3);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 2) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem2_0);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem2_1);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem2_2);
				item.stackSize = random.nextInt(3) + 4;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem2_3);
				item.stackSize = random.nextInt(3) + 4;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 3) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem3_0);
				item.stackSize = random.nextInt(2) + 6;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem3_1);
				item.stackSize = random.nextInt(1) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem3_2);
				item.stackSize = random.nextInt(2) + 5;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem3_3);
				item.stackSize = random.nextInt(3) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 4) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem4_0);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem4_1);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem4_2);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem4_3);
				item.stackSize = random.nextInt(3) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 5) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem5_0);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem5_1);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem5_2);
				item.stackSize = random.nextInt(2) + 1;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem5_3);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 6) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem6_0);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem6_1);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem6_2);
				item.stackSize = random.nextInt(1) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem6_3);
				item.stackSize = random.nextInt(1) + 1;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 7) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem7_0);
				item.stackSize = random.nextInt(3) + 4;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem7_1);
				item.stackSize = random.nextInt(5) + 6;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem7_2);
				item.stackSize = random.nextInt(2) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem7_3);
				item.stackSize = 1;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 8) {
			if (j == 0) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem8_0);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem8_1);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem8_2);
				item.stackSize = random.nextInt(1) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(LootCofigUtils.lootitem8_3);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			} else {
				return null;
			}
		}

		if (random.nextInt(4) == 0) {
			ItemStack item = getBlockByName(LootCofigUtils.lootitem9_0);
			item.stackSize = random.nextInt(2) + 3;
			return item;
		}

		if (random.nextInt(4) == 1) {
			ItemStack item = getBlockByName(LootCofigUtils.lootitem9_1);
			item.stackSize = random.nextInt(3) + 3;
			return item;
		}

		if (random.nextInt(4) == 2) {
			ItemStack item = getBlockByName(LootCofigUtils.lootitem9_2);
			item.stackSize = random.nextInt(2) + 2;
			return item;
		}

		if (random.nextInt(4) == 3) {
			ItemStack item = getBlockByName(LootCofigUtils.lootitem9_3);
			item.stackSize = random.nextInt(1) + 2;
			return item;
		} else {
			return null;
		}
	}

	private String getRandomSpawnerMob(Random random) {
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


	public static ItemStack getBlockByName(String name) {
		if (name.startsWith("Block.")) {
			for (Block<?> block : Blocks.blocksList) {
				if (block != null) {
					String otherName = block.getKey().substring(5);
					if (name.substring(6).equalsIgnoreCase(otherName)) {
						return new ItemStack(block);
					}
				}
			}
		} else if (name.startsWith("Item.")) {
			for (Item item : Item.itemsList) {
				if (item != null) {
					String otherName = item.getKey().substring(5);
					if (name.substring(5).equalsIgnoreCase(otherName)) {
						return new ItemStack(item);
					}
				}
			}
		}

		return null;
	}
}
