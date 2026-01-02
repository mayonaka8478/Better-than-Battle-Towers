package jamdoggie.betterbattletowers.worldgen.properties;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;

import java.util.ArrayList;
import java.util.List;

import static jamdoggie.betterbattletowers.worldgen.properties.LootTable.table;

public class LootTableDefault {
	private LootTableDefault(){}
	public static LootEntry looObj(IItemConvertible item, int metadata, double weight, int fixedField) {
		ItemStack stack = item.getDefaultStack();
		stack.setMetadata(metadata);
		WeightedRandomLootObject lootObj = new WeightedRandomLootObject(stack, fixedField);
		return LootEntry.loot(lootObj, weight);
	}

	public static LootEntry looObj(IItemConvertible item, int metadata, double weight, int min, int max) {
		ItemStack stack = item.getDefaultStack();
		stack.setMetadata(metadata);
		WeightedRandomLootObject lootObj = new WeightedRandomLootObject(stack, min, max);
		return LootEntry.loot(lootObj, weight);
	}

	public static List<LootTable> getDefaultTable() {
		List<LootTable> lootTables = new ArrayList<>();
		List<LootEntry> l0 = new ArrayList<>();
		int index = 0;
		l0.add(looObj(Items.STICK, 0, 1.0f, 3, 5));
		l0.add(looObj(Items.SEEDS_WHEAT, 0, 1.0f, 3, 5));
		l0.add(looObj(Items.AMMO_PEBBLE, 0, 1.0f, 1, 3));
		l0.add(looObj(Blocks.SAND, 0, 1.0f, 1, 3));
		lootTables.add(table(index++, l0));

		List<LootEntry> l1 = new ArrayList<>();
		l1.add(looObj(Items.STICK, 0, 1.0f, 3, 5));
		l1.add(looObj(Items.SEEDS_WHEAT, 0, 1.0f, 3, 5));
		l1.add(looObj(Items.AMMO_PEBBLE, 0, 1.0f, 1, 3));
		l1.add(looObj(Blocks.SAND, 0, 1.0f, 1, 3));
		lootTables.add(table(index++, l1));

		List<LootEntry> l2 = new ArrayList<>();
		l2.add(looObj(Items.FEATHER_CHICKEN, 0, 1.0f, 3, 5));
		l2.add(looObj(Items.FOOD_BREAD, 0, 1.0f, 3, 5));
		l2.add(looObj(Blocks.MUSHROOM_BROWN, 0, 1.0f, 1, 3));
		l2.add(looObj(Blocks.MUSHROOM_RED, 0, 1.0f, 1, 3));
		lootTables.add(table(index++, l2));

		List<LootEntry> l3 = new ArrayList<>();
		l3.add(looObj(Items.FEATHER_CHICKEN, 0, 1.0f, 3, 5));
		l3.add(looObj(Items.FOOD_BREAD, 0, 1.0f, 3, 5));
		l3.add(looObj(Blocks.MUSHROOM_BROWN, 0, 1.0f, 1, 3));
		l3.add(looObj(Blocks.MUSHROOM_RED, 0, 1.0f, 1, 3));
		lootTables.add(table(index++, l3));

		List<LootEntry> l4 = new ArrayList<>();
		l4.add(looObj(Items.BOOK, 0, 1.0f, 1, 3));
		l4.add(looObj(Items.ROPE, 0, 1.0f, 2, 5));
		l4.add(looObj(Items.BRICK_CLAY, 0, 1.0f, 3, 5));
		l4.add(looObj(Items.ORE_RAW_IRON, 0, 1.0f, 1, 3));
		lootTables.add(table(index++, l4));

		List<LootEntry> l5 = new ArrayList<>();
		l5.add(looObj(Items.BOOK, 0, 1.0f, 1, 3));
		l5.add(looObj(Items.ROPE, 0, 1.0f, 2, 5));
		l5.add(looObj(Items.BRICK_CLAY, 0, 1.0f, 2, 5));
		l5.add(looObj(Items.ORE_RAW_IRON, 0, 1.0f, 1, 3));
		l5.add(looObj(Items.DUST_REDSTONE, 0, 1.0f, 3, 5));
		lootTables.add(table(index++, l5));

		List<LootEntry> l6 = new ArrayList<>();
		l6.add(looObj(Items.BOOK, 0, 1.0f, 1, 3));
		l6.add(looObj(Items.ROPE, 0, 1.0f, 2, 5));
		l6.add(looObj(Items.ORE_RAW_IRON, 0, 1.0f, 1, 3));
		l6.add(looObj(Items.DUST_REDSTONE, 0, 1.0f, 3, 5));
		l6.add(looObj(Items.SULPHUR, 0, 1.0f, 1, 2));
		lootTables.add(table(index++, l6));

		List<LootEntry> l7 = new ArrayList<>();
		l7.add(looObj(Items.DUST_REDSTONE, 0, 1.0f, 3, 5));
		l7.add(looObj(Items.OLIVINE, 0, 1.0f, 2, 4));
		l7.add(looObj(Items.DYE, LootTables.LAPIZ, 1.0f, 1, 2));
		l7.add(looObj(Items.QUARTZ, 0, 1.0f, 1, 2));
		lootTables.add(table(index++, l7));

		List<LootEntry> l8 = new ArrayList<>();
		l8.add(looObj(Items.OLIVINE, 0, 1.0f, 2, 4));
		l8.add(looObj(Items.DYE, LootTables.LAPIZ, 1.0f, 1, 2));
		l8.add(looObj(Items.QUARTZ, 0, 1.0f, 1, 2));
		l8.add(looObj(Items.CHAINLINK, 0, 1.0f, 2, 3));
		l8.add(looObj(Items.BUCKET_LAVA, 0, 1.0f, 1));
		l8.add(looObj(BattleTowerBlocks.PRISON_BAR, 0, 1.0f, 2, 3));
		lootTables.add(table(index++, l8));

		List<LootEntry> l9 = new ArrayList<>();
		l9.add(looObj(Items.DYE, LootTables.LAPIZ, 1.0f, 1, 2));
		l9.add(looObj(Items.CHAINLINK, 0, 1.0f, 2, 3));
		l9.add(looObj(BattleTowerBlocks.PRISON_BAR, 0, 1.0f, 2, 3));
		l9.add(looObj(Items.DIAMOND, 0, 1.0f, 1));
		lootTables.add(table(index, l9));

		return lootTables;
	}
}
