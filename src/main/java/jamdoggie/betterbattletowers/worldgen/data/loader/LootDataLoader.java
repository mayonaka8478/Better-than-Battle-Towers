package jamdoggie.betterbattletowers.worldgen.data.loader;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import jamdoggie.betterbattletowers.worldgen.data.adapter.LootEntryJsonAdapter;
import jamdoggie.betterbattletowers.worldgen.data.adapter.LootTableJsonAdapter;
import jamdoggie.betterbattletowers.worldgen.data.loot.LootEntry;
import jamdoggie.betterbattletowers.worldgen.data.loot.LootTable;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.data.registry.recipe.adapter.WeightedRandomLootObjectJsonAdapter;
import net.minecraft.core.item.*;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;
import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class LootDataLoader {
	public static final int LAPIZ = DyeColor.BLUE.itemMeta;
	public static final double INC = 1.20f; // 1.29f
	public static final int MAX_TIER = 9;
	public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + "loot.conf");
	@SuppressWarnings("java:S1905")
	protected static final WeightedRandomBag<WeightedRandomLootObject>[] TOWER_LOOT_TABLE = (WeightedRandomBag<WeightedRandomLootObject>[]) new WeightedRandomBag[10];
	public static final String ASSETS_JAR_PATH = String.format("/assets/%s/tower/loot.conf", MOD_ID);
	private static boolean init = false;

	private LootDataLoader() {
	}


	public static void init() {
		if (init) {
			return;
		}
		LOGGER.info("Loading tower loot.");
		init = true;
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LootTable.class, new LootTableJsonAdapter())
			.registerTypeAdapter(LootEntry.class, new LootEntryJsonAdapter())
			.registerTypeAdapter(WeightedRandomLootObject.class, new WeightedRandomLootObjectJsonAdapter())
			.registerTypeAdapter(ItemStack.class, new ItemStackJsonAdapter())
			.setPrettyPrinting()
			.create();
		Type type = new TypeToken<List<LootTable>>() {}.getType();

		if (Files.notExists(LootDataLoader.CONFIG_PATH)) {
			Path devPath;
			try {
				if(FabricLoader.getInstance().isDevelopmentEnvironment() && DataLoader.class.getResourceAsStream(LootDataLoader.ASSETS_JAR_PATH) == null){
					List<LootTable> defaultTable = LootDataDefault.getDefaultTable();
					String json = gson.toJson(defaultTable, type);
					Config config = ConfigFactory.parseString("{ loot_tables = " + json + " }");
					Path projectRoot = FabricLoader.getInstance().getGameDir().getParent();
					devPath = projectRoot.resolve("src/main/resources" + ASSETS_JAR_PATH);
					Files.createDirectories(devPath.getParent());
					Files.write(devPath, config.root()
						.render(ConfigRenderOptions.defaults()
							.setJson(false)
							.setOriginComments(false))
						.getBytes(StandardCharsets.UTF_8));
					Files.copy(devPath, CONFIG_PATH, StandardCopyOption.REPLACE_EXISTING);
				}else{
					try (InputStream stream = DataLoader.class.getResourceAsStream(LootDataLoader.ASSETS_JAR_PATH)) {
						if (stream == null) {
							LOGGER.error("LootTable could not be regenerated, betterbattletowers.jar is corrupted.");
							throw new RuntimeException("Asset not found in JAR: " + LootDataLoader.ASSETS_JAR_PATH);
						}
						Files.copy(stream, LootDataLoader.CONFIG_PATH);
					}
				}
			} catch (IOException e) {
				throw new RuntimeException("Failed to create default loot config at: "
					+ LootDataLoader.CONFIG_PATH.toAbsolutePath(), e);
			}
		}

		try {
			Config config = ConfigFactory.parseFile(LootDataLoader.CONFIG_PATH.toFile());
			String jsonString = config.getValue("loot_tables")
				.render(ConfigRenderOptions.concise());
			List<LootTable> table = gson.fromJson(jsonString, type);
			LootDataLoader.createTables(table);
		} catch (ConfigException e) {
			throw new RuntimeException("Failed to parse HOCON config: " + LootDataLoader.CONFIG_PATH.toAbsolutePath(), e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException("Loot configuration file is malformed: " + LootDataLoader.CONFIG_PATH.toAbsolutePath(), e);
		}
	}

	private static void createTables(List<LootTable> lootEntries) {
		Map<Integer, List<LootEntry>> table = new HashMap<>();
		for (LootTable subTable : lootEntries) {
			table.put(subTable.getLevel(), subTable.getLootEntries());
		}

		double level = 1;
		List<LootEntry> lastNonEmpty = null;
		for (int jndex = 0; jndex < 10; jndex++, level *= INC) {
			TOWER_LOOT_TABLE[jndex] = new WeightedRandomBag<>();
			if (jndex > 0) {
				for (WeightedRandomBag<WeightedRandomLootObject>.Entry entry : TOWER_LOOT_TABLE[jndex - 1].getEntriesWithWeights()) {
					TOWER_LOOT_TABLE[jndex].addEntry(entry.getObject(), entry.getWeight());
				}
			}
			List<LootEntry> lootEntryList = table.get(jndex);
			if (lootEntryList.isEmpty()) {
				if(lastNonEmpty == null){
					continue;
				}
				lootEntryList = lastNonEmpty;
			}
			lastNonEmpty = lootEntryList;
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
			LootDataLoader.placeItemInChest(random, stack, inventory);
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
