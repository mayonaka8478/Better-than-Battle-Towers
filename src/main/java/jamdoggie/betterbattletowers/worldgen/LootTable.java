package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.OLDBattleTowerConfig;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;


import java.util.Random;

public class LootTable {

	public static ItemStack generateRandomChestLoot(int towerLevel, Random random, boolean isTopFloor) {
		int j = random.nextInt(4);
		if (isTopFloor) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitemtop_0);
				item.stackSize = random.nextInt(2) + 1;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitemtop_1);
				item.stackSize = random.nextInt(2) + 1;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitemtop_2);
				item.stackSize = random.nextInt(2) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitemtop_3);
				item.stackSize = random.nextInt(1) + 1;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 1) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem1_0);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem1_1);
				item.stackSize = random.nextInt(2) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem1_2);
				item.stackSize = random.nextInt(3) + 3;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem1_3);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 2) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem2_0);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem2_1);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem2_2);
				item.stackSize = random.nextInt(3) + 4;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem2_3);
				item.stackSize = random.nextInt(3) + 4;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 3) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem3_0);
				item.stackSize = random.nextInt(2) + 6;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem3_1);
				item.stackSize = random.nextInt(1) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem3_2);
				item.stackSize = random.nextInt(2) + 5;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem3_3);
				item.stackSize = random.nextInt(3) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 4) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem4_0);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem4_1);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem4_2);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem4_3);
				item.stackSize = random.nextInt(3) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 5) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem5_0);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem5_1);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem5_2);
				item.stackSize = random.nextInt(2) + 1;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem5_3);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 6) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem6_0);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem6_1);
				item.stackSize = random.nextInt(3) + 2;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem6_2);
				item.stackSize = random.nextInt(1) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem6_3);
				item.stackSize = random.nextInt(1) + 1;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 7) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem7_0);
				item.stackSize = random.nextInt(3) + 4;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem7_1);
				item.stackSize = random.nextInt(5) + 6;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem7_2);
				item.stackSize = random.nextInt(2) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem7_3);
				item.stackSize = 1;
				return item;
			} else {
				return null;
			}
		}

		if (towerLevel == 8) {
			if (j == 0) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem8_0);
				item.stackSize = random.nextInt(2) + 3;
				return item;
			}
			if (j == 1) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem8_1);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			}
			if (j == 2) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem8_2);
				item.stackSize = random.nextInt(1) + 2;
				return item;
			}
			if (j == 3) {
				ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem8_3);
				item.stackSize = random.nextInt(3) + 5;
				return item;
			} else {
				return null;
			}
		}

		if (random.nextInt(4) == 0) {
			ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem9_0);
			item.stackSize = random.nextInt(2) + 3;
			return item;
		}

		if (random.nextInt(4) == 1) {
			ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem9_1);
			item.stackSize = random.nextInt(3) + 3;
			return item;
		}

		if (random.nextInt(4) == 2) {
			ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem9_2);
			item.stackSize = random.nextInt(2) + 2;
			return item;
		}

		if (random.nextInt(4) == 3) {
			ItemStack item = getBlockByName(OLDBattleTowerConfig.lootitem9_3);
			item.stackSize = random.nextInt(1) + 2;
			return item;
		} else {
			return null;
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
