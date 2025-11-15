package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.mixins.accessor.TomlAccessor;
import jamdoggie.betterbattletowers.worldgen.LootTable;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Entry;
import turniplabs.halplibe.util.toml.Toml;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

import static jamdoggie.betterbattletowers.BattleTowerConfigOld.convertOldConfig;
import static jamdoggie.betterbattletowers.BattleTowerConfigOld.getOldProperties;
import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;
import static jamdoggie.betterbattletowers.worldgen.LootTable.getDefaultMap;
import static net.minecraft.core.util.collection.NamespaceID.getPermanent;

public class BattleTowerConfig {
	public static final String GENERAL = "GENERAL";
	public static final String LOOT = "LOOT";
	private static int towerCount = 200;
	private static int rarity = 10;

	private static int startingBlockId = 6340;
	private static int startingItemId = 26340;

	private static final String CONFIG_DIRECTORY = FabricLoader.getInstance().getGameDir().toString() + "/config/";
	private static boolean isInit = false;

	private BattleTowerConfig() {
	}

	private static String key(String category, String key) {
		return category + "." + key;
	}

	public static void init() {
		if (isInit) return;
		isInit = true;
		File configFile = new File(CONFIG_DIRECTORY + MOD_ID + ".cfg");
		if (configFile.exists()) {
			Properties properties = getOldProperties();
			ConfigHandler config = new ConfigHandler(MOD_ID, properties);
			towerCount = config.getInt("towercount");
			rarity = config.getInt("rarity");
			startingBlockId = config.getInt("starting_block_id");
			startingItemId = config.getInt("starting_item_id");

			File newFile = new File(CONFIG_DIRECTORY + "OLD_" + MOD_ID + ".cfg");
			if (!configFile.renameTo(newFile)) {
				LOGGER.error("Battle Towers Old Config file could not be renamed.");
			}
			loadConfig(createDefaultConfig(convertOldConfig(config)));
		} else {
			loadConfig(createDefaultConfig(getDefaultMap()));
		}
		LOGGER.info("Battle Towers Config initialized.");
	}

	private static void loadConfig(Toml properties) {
		TomlConfigHandler config = new TomlConfigHandler(MOD_ID + "4.0", properties, false);
		if (config.getConfigFile().exists()) {
			config.loadConfig();
		} else {
			try {
				if (!config.getConfigFile().createNewFile()) {
					LOGGER.warn("Battle Towers config file already exists, skipping creation.");
				}
			} catch (IOException e) {
				LOGGER.error("Battle Towers Config failed to generate, deleted the config and try again.");
				throw new UncheckedIOException("Failed to create Battle Towers config file", e);
			}
			config.writeConfig();
			config.loadConfig();
		}
		readConfigFile(config);
	}

	private static void readConfigFile(TomlConfigHandler config) {
		towerCount = config.getInt(key(GENERAL, "TOWER_COUNT"));
		rarity = config.getInt(key(GENERAL, "RARITY"));
		startingBlockId = config.getInt(key(GENERAL, "STARTING_BLOCK_ID"));
		startingItemId = config.getInt(key(GENERAL, "STARTING_ITEM_ID"));

		Map<Integer, List<LootTable.LootEntry>> table = new HashMap<>();
		Toml toml = config.getRawParsed();
		for(Map.Entry<String, Toml> category: ((TomlAccessor)toml).getCategories().entrySet()){
			addEntryToTable(category, table);
		}
		LootTable.createTables(table);
	}

	private static void addEntryToTable(Map.Entry<String, Toml> category, Map<Integer, List<LootTable.LootEntry>> table) {
		String categoryName = category.getKey();
		if (categoryName.equalsIgnoreCase(GENERAL) || !categoryName.contains(LOOT)) return;
		int index = Integer.parseInt(categoryName.substring(LOOT.length()));
		Toml value = category.getValue();
		if(!((TomlAccessor)value).getCategories().isEmpty()) return;
		List<LootTable.LootEntry> listLoot = new ArrayList<>();
		TomlAccessor item = (TomlAccessor)value;
		for(Map.Entry<String, Entry<?>>  entry: item.getEntries().entrySet()){
			IItemConvertible convertible = getConvertible(entry.getKey());
			if(convertible == null) continue;
			int metadata = (Integer) entry.getValue().getT();
			listLoot.add(LootTable.LootEntry.loot(convertible, metadata));
		}
		table.put(index, listLoot);
	}

	private static IItemConvertible getConvertible(String key) {
		NamespaceID id;
		try{
			id = getPermanent(key);
		}catch (HardIllegalArgumentException e) {
			return null;
		}
		Block<?> block = Blocks.blockMap.get(id);
		Item item = Item.itemsMap.get(id);
		if(block == null && item == null) return null;
		if(block == null) return item;
		return block;
	}

	private static Toml createDefaultConfig(Map<Integer, List<LootTable.LootEntry>> table) {
		Toml properties = new Toml("Battle Towers Config");
		properties.addCategory(GENERAL)
			.addEntry("TOWER_COUNT", towerCount)
			.addEntry("RARITY", rarity)
			.addEntry("STARTING_BLOCK_ID", startingBlockId)
			.addEntry("STARTING_ITEM_ID", startingItemId);

		for (int i = 0; i < table.size(); i++) {
			Toml tomlCategory = properties.addCategory(LOOT + i);
			List<LootTable.LootEntry> entries = table.get(i);
			for (LootTable.LootEntry entry : entries) {
				String name = entry.getValue().asItem().namespaceID.toString();
				tomlCategory.addEntry(name, entry.getMetadata());
			}
		}
		return properties;
	}

	public static int getStartingItemId() {
		return startingItemId;
	}

	public static int getStartingBlockId() {
		return startingBlockId;
	}

	public static int getRarity() {
		return rarity;
	}

	public static int getTowerCount() {
		return towerCount;
	}
}
