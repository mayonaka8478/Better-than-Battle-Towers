package jamdoggie.betterbattletowers.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jamdoggie.betterbattletowers.config.util.DefaultTable;
import jamdoggie.betterbattletowers.config.util.LootEntry;
import jamdoggie.betterbattletowers.config.util.LootEntryJsonAdapter;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.data.registry.recipe.adapter.WeightedRandomLootObjectJsonAdapter;
import net.minecraft.core.item.*;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class LootTable {
	public static final int LAPIZ = DyeColor.BLUE.itemMeta;
	public static final double INC = 1.20f; // 1.29f
	public static final int MAX_TIER = 9;
	@SuppressWarnings("java:S1905")
	protected static final WeightedRandomBag<WeightedRandomLootObject>[] TOWER_LOOT_TABLE = (WeightedRandomBag<WeightedRandomLootObject>[]) new WeightedRandomBag[10];
	private static boolean init = false;

	private LootTable(){}

	public static void init() {
		if (init) {
			return;
		}
		init = true;
		Path path = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + "_loot.json");
		Gson gson = new GsonBuilder().registerTypeAdapter(LootEntry.class, new LootEntryJsonAdapter())
			.registerTypeAdapter(WeightedRandomLootObject.class, new WeightedRandomLootObjectJsonAdapter())
			.registerTypeAdapter(ItemStack.class, new ItemStackJsonAdapter())
			.setPrettyPrinting()
			.create();
		Type type = new TypeToken<List<List<LootEntry>>>() {}.getType();

		if(Files.notExists(path)){
			try{
				List<List<LootEntry>> defaultTable = DefaultTable.getDefaultTable();
				String json = gson.toJson(defaultTable, type);
				Files.write(path, json.getBytes(StandardCharsets.UTF_8));
			}catch (IOException e){
				throw new RuntimeException("Failed to create default loot config at: "
					+ path.toAbsolutePath(), e);
			}
		}

		try {
			String jsonString;
			byte[] bytes = Files.readAllBytes(path);
			jsonString = new String(bytes, StandardCharsets.UTF_8);
			createTables(gson.fromJson(jsonString, type));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read loot configuration file at:" + path.toAbsolutePath(), e);
		}
	}

	private static void createTables(List<List<LootEntry>> lootEntries) {
		double level = 1;
		for (int jndex = 0; jndex < 10; jndex++, level *= INC) {
			TOWER_LOOT_TABLE[jndex] = new WeightedRandomBag<>();
			if (jndex > 0) {
				for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[jndex - 1].getEntriesWithWeights()) {
					TOWER_LOOT_TABLE[jndex].addEntry(entry.getObject(), entry.getWeight());
				}
			}
			List<LootEntry> lootEntryList = lootEntries.get(jndex);
			if (lootEntryList.isEmpty()) continue;
			for (LootEntry entry : lootEntryList) {
				TOWER_LOOT_TABLE[jndex].addEntry(entry.getLootObj(), level * entry.getWeight());
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

	private static void placeItemInChest(Random random, @Nullable ItemStack itemstack, @NotNull Container inventory) {
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
