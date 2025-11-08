package jamdoggie.betterbattletowers.worldgen.component;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootTable {
	public static final int LAPIZ = DyeColor.BLUE.itemMeta;
	public static final double INC = 1.29f;
	public static final int LOOT_AMOUNT = 7;
	public static int MAX_TIER;
	public static WeightedRandomBag<WeightedRandomLootObject>[] TOWER_LOOT_TABLE = (WeightedRandomBag<WeightedRandomLootObject>[]) new WeightedRandomBag[10];

	static {
		int index = 0;
		double tier = 1;
		//floor1
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.STICK.getDefaultStack(), 3, 8), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.SEEDS_WHEAT.getDefaultStack(), 3, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.AMMO_PEBBLE.getDefaultStack(), 3, 5), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.SAND.getDefaultStack(), 1, 3), 20.0f * tier);

		//floor2
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.STICK.getDefaultStack(), 3, 10), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.AMMO_PEBBLE.getDefaultStack(), 3, 8), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.PLANKS_OAK_PAINTED.getDefaultStack(), 1, 3).setRandomMetadata(0, 15), 10.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.PLANKS_OAK.getDefaultStack(), 1, 3), 10.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.WOOL.getDefaultStack(), 1, 3).setRandomMetadata(0, 15), 20.0f * tier);


		//floor3
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 3, 8), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.FOOD_BREAD.getDefaultStack()), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.MUSHROOM_BROWN.getDefaultStack(), 1, 3), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.MUSHROOM_RED.getDefaultStack(), 1, 3), 20.0f * tier);

		//floor4
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 3, 10), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.FOOD_BREAD.getDefaultStack(), 1, 3), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.MUSHROOM_BROWN.getDefaultStack(), 3, 5), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.MUSHROOM_RED.getDefaultStack(), 3, 5), 20.0f * tier);

		//floor5
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.BOOK.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.ROPE.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.BRICK_CLAY.getDefaultStack(), 1, 3), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.ORE_RAW_IRON.getDefaultStack(), 1, 3), 20.0f * tier);

		//floor6
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.BOOK.getDefaultStack(), 3, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.FLINT.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.DUST_REDSTONE.getDefaultStack(), 1, 3), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.ORE_RAW_GOLD.getDefaultStack(), 1, 3), 20.0f * tier);

		//floor7
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.BOOK.getDefaultStack(), 3, 8), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.INGOT_IRON.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.INGOT_GOLD.getDefaultStack(), 1, 3), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.BUCKET_LAVA.getDefaultStack()), 20.0f * tier);

		//floor8
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.INGOT_GOLD.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.QUARTZ.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.OLIVINE.getDefaultStack(), 1, 3), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.TNT.getDefaultStack(), 1, 3), 20.0f * tier);

		//floor9
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.QUARTZ.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.CHAINLINK.getDefaultStack(), 1, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.DYE.getDefaultStack(), 1, 3).setRandomMetadata(LAPIZ, LAPIZ), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 1, 3), 20.0f * tier);

		//floor10
		index++;
		tier *= INC;
		TOWER_LOOT_TABLE[index] = new WeightedRandomBag<>();
		for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[index - 1].getEntriesWithWeights()) {
			TOWER_LOOT_TABLE[index].addEntry(entry.getObject(), entry.getWeight());
		}
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.DUST_REDSTONE.getDefaultStack(), 3, 5), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.DYE.getDefaultStack(), 3, 5).setRandomMetadata(LAPIZ, LAPIZ), 30.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 3, 5), 20.0f * tier);
		TOWER_LOOT_TABLE[index].addEntry(new WeightedRandomLootObject(Blocks.MESH_GOLD.getDefaultStack(), 1, 3), 20.0f * tier);

		MAX_TIER = index;
	}

	public static void populateChest(World world, Random random, int x, int y, int z, int tier
	) {
		Container inventory = BlockLogicChest.getInventory(world, x, y, z);
		if (inventory == null) return;
		List<ItemStack> stacks = generateLootList(random, tier);
		for (ItemStack stack : stacks) {
			LootTable.placeItemInChest(random, stack, inventory);
		}
	}

	private static List<ItemStack> generateLootList(Random random, int tier) {
		List<ItemStack> itemStackList = new ArrayList<>();
		if (tier > MAX_TIER) tier = MAX_TIER;
		if (tier < 0) tier = 0;
		WeightedRandomBag<WeightedRandomLootObject> bag = TOWER_LOOT_TABLE[tier];
		for (int i = 0; i < LOOT_AMOUNT; i++) {
			itemStackList.add(bag.getRandom(random).getItemStack(random));
		}
		return itemStackList;
	}

	public static void placeItemInChest(Random random, @Nullable ItemStack itemstack, @NotNull Container inventory) {
		if (itemstack == null) return;
		int invSize = inventory.getContainerSize();
		int index = random.nextInt(invSize);
		int count = invSize;
		while (count-- > 0) {
			ItemStack stack = inventory.getItem(index);
			if (stack == null) {
				break;
			}
			if (stack.itemID == itemstack.itemID) {
				if (stack.getMaxStackSize() >= stack.stackSize + itemstack.stackSize) {
					itemstack.stackSize += stack.stackSize;
					break;
				} else {
					itemstack.stackSize = itemstack.stackSize - stack.getMaxStackSize() + stack.stackSize;
					stack.stackSize = stack.getMaxStackSize();
				}
			}
			index++;
			if (index >= invSize) {
				index = 0;
			}
		}
		inventory.setItem(index, itemstack);
	}
}
