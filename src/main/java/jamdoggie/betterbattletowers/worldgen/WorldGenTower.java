package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.LootCofigUtils;
import jamdoggie.betterbattletowers.block.ModBlocks;
import jamdoggie.betterbattletowers.entity.EntityGolem;
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
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.type.overworld.WorldTypeOverworld;
import org.lwjgl.Sys;

import java.util.Random;

import static net.minecraft.core.block.BlockLogicChest.getMetaWithDirection;
import static net.minecraft.core.block.BlockLogicChest.getMetaWithType;

public class WorldGenTower extends WorldFeature {

	private int currentFloor;
	private int field_22237_field_20341_topFloor;

	public WorldGenTower() {
	}

	@Override
	public boolean place(World world, Random random, int i, int j, int k) {
		boolean flag = false;
		if (world.getBlockId(i, j, k) == Blocks.FLUID_WATER_STILL.id()) {
			return false;
		}

		if (world.getBlockId(i, j, k) == 0) {
			return false;
		}

		int l = i;
		int i1 = j;
		int j1 = k;
		Biome biome = world.getBlockBiome(l, i1, j1);

		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		int k2 = i1 - 6;
		int l2 = random.nextInt(11);

		currentFloor = 1;
		field_22237_field_20341_topFloor = 0;

		int offset = 0;
		if (world.worldType instanceof WorldTypeOverworld) offset = 64;
		for (; k2 < 120 + offset; k2 += 7) {
			if (k2 + 7 >= 120 + offset) {
				field_22237_field_20341_topFloor = 1;
			}
			for (int j3 = 0; j3 < 7; j3++) {
				if (k2 == i1 - 6 && j3 < 4) {
					j3 = 4;
				}

				for (int j4 = -7; j4 < 7; j4++) {
					for (int i5 = -7; i5 < 7; i5++) {
						int i6 = j4 + l;
						int k6 = j3 + k2;
						int l6 = i5 + j1;

						if (i5 == -7) {
							if (j4 > -5 && j4 < 4) {
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
							}
							continue;
						}

						if (i5 == -6 || i5 == -5) {
							if (j4 == -5 || j4 == 4) {
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
								continue;
							}

							if (i5 == -6) {
								if (j4 == (j3 + 1) % 7 - 3) {
									world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());

									if (j3 == 5) {
										world.setBlock(i6 - 7, k6, l6, Blocks.STONE_POLISHED.id());
									}

									if (j3 == 6 && field_22237_field_20341_topFloor == 1) {
										world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
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
									world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
								}
							} else {
								world.setBlock(i6, k6, l6, 0);
							}

							continue;
						}
						if (i5 == -4 || i5 == -3 || i5 == 2 || i5 == 3) {
							if (j4 == -6 || j4 == 5) {
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
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
									world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
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
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
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
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
								continue;
							}
							if (j4 <= -3 || j4 >= 2) {
								continue;
							}
							if (j3 == 5) {
								world.setBlock(i6, k6, l6, Blocks.STONE_POLISHED.id());
							} else {
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
							}
							continue;
						}

						if (i5 != 6 || j4 <= -3 || j4 >= 2) {
							continue;
						}

						if (j3 < 0 || j3 > 3 || j4 != -1 && j4 != 0) {
							world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
						} else {
							world.setBlock(i6, k6, l6, getRandomCobbledBlock(biome, l2, random));
						}
					}

				}

			}

			if (currentFloor == 2) {
				world.setBlock(l + 3, k2, j1 - 5, getRandomCobbledBlock(biome, l2, random));
				world.setBlock(l + 3, k2 - 1, j1 - 5, getRandomCobbledBlock(biome, l2, random));
			}

			if (field_22237_field_20341_topFloor == 1) {
				double d = l;
				double d1 = k2 + 6;
				double d2 = (double) j1 + 0.5D;
				EntityGolem entitygolem = new EntityGolem(world, l2);
				entitygolem.spawnInit();
				entitygolem.moveTo(d, d1, d2, world.rand.nextFloat() * 360F, 0.0F);
				world.entityJoinedWorld(entitygolem);
				System.out.println("Spawned golem at " + d + ", " + d1 + ", " + d2);
			} else {
				world.setBlockWithNotify(l + 2, k2 + 6, j1 + 2, Blocks.MOBSPAWNER.id());
				TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(l + 2, k2 + 6, j1 + 2);
				tileentitymobspawner.setMobId(getRandomSpawnerMob(biome, random));
				world.setBlockWithNotify(l - 3, k2 + 6, j1 + 2, Blocks.MOBSPAWNER.id());
				TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner) world.getTileEntity(l - 3, k2 + 6, j1 + 2);
				tileentitymobspawner1.setMobId(getRandomSpawnerMob(biome, random));
			}

			world.setBlock(l, k2 + 6, j1 - 3, Blocks.STONE_POLISHED.id());
			world.setBlock(l - 1, k2 + 6, j1 - 3, Blocks.STONE_POLISHED.id());

			if (k2 + 56 >= 120 && currentFloor == 1) {
				currentFloor = 2;
			}

			for (int k3 = 0; k3 < 2; k3++) {
				world.setBlockWithNotify(l - k3, k2 + 7, j1 - 3, ModBlocks.ChestTower.id());
				int x = l - k3;
				int y = k2 + 7;
				int z = j1 - 3;
				world.setBlockMetadataWithNotify(x, y, z, getMetaWithType(getMetaWithDirection(world.getBlockMetadata(x, y, z), Direction.SOUTH), BlockLogicChest.Type.SINGLE));
				TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(l - k3, k2 + 7, j1 - 3);
				for (int j5 = 0; j5 < 1 + k3 + l2; j5++) {
					ItemStack itemstack = generateRandomChestLoot(currentFloor, random);
					if (itemstack != null) {
						tileEntityChest.setItem(random.nextInt(tileEntityChest.getContainerSize()), itemstack);
					}
				}

			}
			BlockLogicChest.setType(world, l, k2 + 7, j1 - 3, BlockLogicChest.Type.RIGHT);
			BlockLogicChest.setType(world, l - 1, k2 + 7, j1 - 3, BlockLogicChest.Type.LEFT);

			for (int l3 = 0; l3 < (currentFloor * 4 + l2) - 8 && field_22237_field_20341_topFloor != 1; l3++) {
				int k4 = 5 - random.nextInt(12);
				int k5 = k2 + 5;
				int j6 = 5 - random.nextInt(10);
				if (j6 < -2 && k4 < 4 && k4 > -5 && k4 != 1 && k4 != -2) {
					continue;
				}
				k4 += l;
				j6 += j1;
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

	private String getRandomSpawnerMob(Biome biome, Random random) {
		int i = random.nextInt(5);

		if (i == 0) {
			return "Skeleton";
		}
		if (i == 1) {
			return "Zombie";
		}
		if (i == 2) {
			return "Spider";
		}
		if (i == 3) {
			return "Spider";
		}
		if (i == 4) {
			return "Zombie";
		} else {
			return "Scorpion";
		}
	}

	private int getRandomCobbledBlock(Biome biome, int i, Random random) {
		if (biome.hasSurfaceSnow()) {
			if (i % 2 == 0) {
				return Blocks.BRICK_PERMAFROST.id();
			} else {
				return Blocks.COBBLE_PERMAFROST.id();
			}
		}

		if (biome == Biomes.OVERWORLD_DESERT) {
			if (i % 2 == 0) {
				return Blocks.BRICK_SANDSTONE.id();
			} else {
				return Blocks.SANDSTONE.id();
			}
		}

		if (biome == Biomes.OVERWORLD_BIRCH_FOREST) {
			if (i % 2 == 0) {
				if (random.nextInt(3) == 0) {
					return Blocks.BRICK_STONE_POLISHED.id();
				} else {
					return Blocks.BRICK_STONE_POLISHED_MOSSY.id();
				}
			}
			return Blocks.BRICK_STONE_POLISHED.id();
		}
		if (biome == Biomes.OVERWORLD_FOREST) {
			if (i % 2 == 0) {
				if (random.nextInt(3) == 0) {
					return Blocks.COBBLE_STONE.id();
				} else {
					return Blocks.COBBLE_STONE_MOSSY.id();
				}
			}
			return Blocks.COBBLE_STONE_MOSSY.id();
		}

		if (i == 0) {
			return Blocks.COBBLE_STONE.id();
		}
		if (i == 1) {
			if (random.nextInt(3) == 0) {
				return Blocks.COBBLE_STONE.id();
			} else {
				return Blocks.COBBLE_STONE_MOSSY.id();
			}
		}
		if (i == 2) {
			return Blocks.BRICK_STONE.id();
		}
		if (i == 3) {
			return Blocks.BRICK_STONE_POLISHED.id();
		}
		if (i == 4) {
			return Blocks.BRICK_STONE_POLISHED_MOSSY.id();
		}
		if (i == 5) {
			return Blocks.COBBLE_BASALT.id();
		}
		if (i == 6) {
			return Blocks.BRICK_BASALT.id();
		}
		if (i == 7) {
			return Blocks.COBBLE_LIMESTONE.id();
		}
		if (i == 8) {
			return Blocks.BRICK_LIMESTONE.id();
		}
		if (i == 9) {
			return Blocks.COBBLE_GRANITE.id();
		}
		if (i == 10) {
			return Blocks.BRICK_GRANITE.id();
		}

		return Blocks.COBBLE_STONE.id();
	}

	public static ItemStack getBlockByName(String name) {
		if (name.startsWith("Block.")) {
			for (Block block : Blocks.blocksList) {
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
