package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.*;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LootTable {
	public static final int LAPIZ = DyeColor.BLUE.itemMeta;
	public static final double INC = 1.29f;
	public static final int LOOT_AMOUNT = 9;
	public static final int MAX_TIER = 9;
	protected static final WeightedRandomBag<WeightedRandomLootObject>[] TOWER_LOOT_TABLE = (WeightedRandomBag<WeightedRandomLootObject>[]) new WeightedRandomBag[10];

	private LootTable(){}

	public static class LootEntry {
		protected IItemConvertible value;
		protected int metadata;

		public IItemConvertible getValue() {return value;}
		public int getMetadata() {return metadata;}

		LootEntry(IItemConvertible value, int metadata) {
			this.value = value;
			this.metadata = metadata;
		}

		public static LootEntry loot(IItemConvertible value, int metadata) {
			return new LootEntry(value, metadata);
		}
	}

	public static void createTables(Map<Integer, List<LootEntry>> lootEntries) {
		double level = 1;
		for (int jndex = 0; jndex < 10; jndex++, level *= INC) {
			TOWER_LOOT_TABLE[jndex] = new WeightedRandomBag<>();
			if (jndex > 0) {
				for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[jndex - 1].getEntriesWithWeights()) {
					TOWER_LOOT_TABLE[jndex].addEntry(entry.getObject(), entry.getWeight());
				}
			}
			List<LootEntry> lootEntryList = lootEntries.getOrDefault(jndex, new ArrayList<>());
			if (lootEntryList.isEmpty()) continue;
			int high = lootEntryList.size() / 2;
			for (int i = 0; i < high; i++) {
				LootEntry entry = lootEntryList.get(i);
				int minStacksize = Math.min(3, entry.value.getDefaultStack().getMaxStackSize());
				int maxStacksize = Math.min(5, entry.value.getDefaultStack().getMaxStackSize());
				TOWER_LOOT_TABLE[jndex].addEntry(new WeightedRandomLootObject(entry.value.getDefaultStack(), minStacksize, maxStacksize).setRandomMetadata(entry.metadata, entry.metadata), level * 30);
			}
			for (int i = high; i < lootEntryList.size(); i++) {
				LootEntry entry = lootEntryList.get(i);
				int minStacksize = Math.min(1, entry.value.getDefaultStack().getMaxStackSize());
				int maxStacksize = Math.min(3, entry.value.getDefaultStack().getMaxStackSize());
				TOWER_LOOT_TABLE[jndex].addEntry(new WeightedRandomLootObject(entry.value.getDefaultStack(), minStacksize, maxStacksize).setRandomMetadata(entry.metadata, entry.metadata), level * 20);
			}
		}
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

	public static Map<Integer, List<LootTable.LootEntry>> getDefaultMap() {
		Map<Integer, List<LootTable.LootEntry>> table = new HashMap<>();
		int tier = 0;
		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.STICK, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.SEEDS_WHEAT, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.AMMO_PEBBLE, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.SAND, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.STICK, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.AMMO_PEBBLE, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.PLANKS_OAK, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.WOOL, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.FEATHER_CHICKEN, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.FOOD_BREAD, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.MUSHROOM_BROWN, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.MUSHROOM_RED, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.FEATHER_CHICKEN, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.FOOD_BREAD, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.MUSHROOM_BROWN, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.MUSHROOM_RED, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.BOOK, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.ROPE, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.BRICK_CLAY, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.ORE_RAW_IRON, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.BOOK, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.FLINT, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.DUST_REDSTONE, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.ORE_RAW_GOLD, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.BOOK, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.INGOT_IRON, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.INGOT_GOLD, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.BUCKET_LAVA, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.INGOT_GOLD, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.QUARTZ, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.OLIVINE, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.TNT, 0));
		table.get(tier).add(LootTable.LootEntry.loot(BattleTowerBlocks.PRISON_BAR, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.QUARTZ, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.CHAINLINK, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.DYE, LAPIZ));
		table.get(tier).add(LootTable.LootEntry.loot(Items.DIAMOND, 0));
		table.get(tier).add(LootTable.LootEntry.loot(BattleTowerBlocks.PRISON_BAR, 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot(Items.DUST_REDSTONE, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Items.DYE, LAPIZ));
		table.get(tier).add(LootTable.LootEntry.loot(Items.DIAMOND, 0));
		table.get(tier).add(LootTable.LootEntry.loot(Blocks.MESH_GOLD, 0));
		table.get(tier).add(LootTable.LootEntry.loot(BattleTowerBlocks.PRISON_BAR, 0));

		return table;
	}
}
