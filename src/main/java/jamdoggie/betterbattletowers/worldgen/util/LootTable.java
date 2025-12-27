package jamdoggie.betterbattletowers.worldgen.util;

import jamdoggie.betterbattletowers.config.BattleTowerConfig;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.item.*;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;

public class LootTable {
	public static final int LAPIZ = DyeColor.BLUE.itemMeta;
	public static final double INC = 1.20f; // 1.29f
	public static final int MAX_TIER = 9;
	@SuppressWarnings("java:S1905")
	protected static final WeightedRandomBag<WeightedRandomLootObject>[] TOWER_LOOT_TABLE = (WeightedRandomBag<WeightedRandomLootObject>[]) new WeightedRandomBag[10];
	private static boolean init = false;

	private LootTable() {
	}

	public static void init() {
		if (init) return;
		init = true;
		LootTable.createTables(BattleTowerConfig.getTempTable());
	}

	public static class LootEntry {
		protected String namespaceID;
		protected int metadata;

		public String namespaceID() {
			return namespaceID;
		}

		public int metadata() {
			return metadata;
		}

		LootEntry(String namespaceID, int metadata) {
			this.namespaceID = namespaceID;
			this.metadata = metadata;
		}

		public LootEntry setNamespaceID(String namespaceID) {
			this.namespaceID = namespaceID;
			return this;
		}

		public static LootEntry loot(String namespaceID, int metadata) {
			return new LootEntry(namespaceID, metadata);
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
				IItemConvertible loot = BattleTowerConfig.getConvertible(entry.namespaceID());
				if (loot == null) {
					LOGGER.warn("Loot could not be added because the id {} could not be queried", entry.namespaceID());
					continue;
				}
				int minStacksize = Math.min(1, loot.getDefaultStack().getMaxStackSize());
				int maxStacksize = Math.min(3, loot.getDefaultStack().getMaxStackSize());
				TOWER_LOOT_TABLE[jndex].addEntry(new WeightedRandomLootObject(loot.getDefaultStack(), minStacksize, maxStacksize).setRandomMetadata(entry.metadata, entry.metadata), level * 30);
			}
			for (int i = high; i < lootEntryList.size(); i++) {
				LootEntry entry = lootEntryList.get(i);
				IItemConvertible loot = BattleTowerConfig.getConvertible(entry.namespaceID());
				if (loot == null) {
					LOGGER.warn("Loot could not be added because the id {} could not be queried", entry.namespaceID());
					continue;
				}
				int minStacksize = Math.min(1, loot.getDefaultStack().getMaxStackSize());
				int maxStacksize = Math.min(1, loot.getDefaultStack().getMaxStackSize());
				TOWER_LOOT_TABLE[jndex].addEntry(new WeightedRandomLootObject(loot.getDefaultStack(), minStacksize, maxStacksize).setRandomMetadata(entry.metadata, entry.metadata), level * 20);
			}
		}
	}

	public static void populateChest(World world, Random random, int x, int y, int z, int tier, int lootAmount) {
		Container inventory = BlockLogicChest.getInventory(world, x, y, z);
		if (inventory == null) return;
		List<ItemStack> stacks = generateLootList(random, tier, lootAmount);
		for (ItemStack stack : stacks) {
			LootTable.placeItemInChest(random, stack, inventory);
		}
	}

	private static List<ItemStack> generateLootList(Random random, int tier, int lootAmount) {
		List<ItemStack> itemStackList = new ArrayList<>();
		if (tier > MAX_TIER) tier = MAX_TIER;
		if (tier < 0) tier = 0;
		WeightedRandomBag<WeightedRandomLootObject> bag = TOWER_LOOT_TABLE[tier];
		for (int i = 0; i < lootAmount; i++) {
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
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/stick", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/seeds_wheat", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/ammo_pebble", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/sand", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/stick", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/seeds_wheat", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/ammo_pebble", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/sand", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/feather_chicken", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/food_bread", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/mushroom_brown", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/mushroom_red", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/feather_chicken", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/food_bread", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/mushroom_brown", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/mushroom_red", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/book", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/rope", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/brick_clay", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/ore_raw_iron", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/book", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/brick_clay", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/ore_raw_iron", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/dust_redstone", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/book", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/sulphur", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/olivine", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/quartz", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/book", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/sulphur", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/olivine", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/tnt", 0));
		table.get(tier).add(LootTable.LootEntry.loot("betterbattletowers:block/prison_bar", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/quartz", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/chainlink", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/dye", LAPIZ));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/bucket_lava", 0));
		table.get(tier).add(LootTable.LootEntry.loot("betterbattletowers:block/prison_bar", 0));
		tier++;

		table.put(tier, new ArrayList<>());
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/dust_redstone", 0));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:item/dye", LAPIZ));
		table.get(tier).add(LootTable.LootEntry.loot("minecraft:block/mesh_gold", 0));
		table.get(tier).add(LootTable.LootEntry.loot("betterbattletowers:block/prison_bar", 0));

		return table;
	}
}
