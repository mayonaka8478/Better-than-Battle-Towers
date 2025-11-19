package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.mixins.accessor.TomlAccessor;
import jamdoggie.betterbattletowers.worldgen.util.LootTable;
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

import static jamdoggie.betterbattletowers.BattleTowerConfigOld.*;
import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;
import static jamdoggie.betterbattletowers.worldgen.util.LootTable.getDefaultMap;
import static net.minecraft.core.util.collection.NamespaceID.getPermanent;

public class BattleTowerConfig {
	public static final String GENERAL = "GENERAL";
	public static final String LOOT = "LOOT";
	private static int towerrarity = 200;
	private static int startingBlockId = 6340;
	private static int startingItemId = 26340;
	private static int lootamount = 26340;
	private static String version = "4.0.0";
	private static boolean tint = false;
	private static Map<Integer, List<LootTable.LootEntry>> tempTable;
	private static Toml postProcessing;
	private static boolean isOldConfig = false;

	private static final String CONFIG_DIRECTORY = FabricLoader.getInstance().getGameDir().toString() + "/config/";
	private static boolean isInit = false;

	private BattleTowerConfig() {
	}

	public static Map<Integer, List<LootTable.LootEntry>> getTempTable() {
		return tempTable;
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
			towerrarity = config.getInt("towercount");
			startingBlockId = config.getInt("starting_block_id");
			startingItemId = config.getInt("starting_item_id");
			isOldConfig = true;
			File newFile = new File(CONFIG_DIRECTORY + "old_" + MOD_ID + ".cfg");
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
		version = config.getString(key(GENERAL, "VERSION"));
		towerrarity = config.getInt(key(GENERAL, "RARITY"));
		lootamount = config.getInt(key(GENERAL, "LOOT_AMOUNT"));
		startingBlockId = config.getInt(key(GENERAL, "STARTING_BLOCK_ID"));
		startingItemId = config.getInt(key(GENERAL, "STARTING_ITEM_ID"));
		tint = config.getBoolean(key(GENERAL, "DARKEN_FLOORS"));

		Map<Integer, List<LootTable.LootEntry>> table = new HashMap<>();
		Toml toml = config.getRawParsed();
		for (Map.Entry<String, Toml> category : ((TomlAccessor) toml).getCategories().entrySet()) {
			addEntryToTable(category, table);
		}
		tempTable = table;
	}

	private static void addEntryToTable(Map.Entry<String, Toml> category, Map<Integer, List<LootTable.LootEntry>> table) {
		String categoryName = category.getKey();
		if (categoryName.equalsIgnoreCase(GENERAL) || !categoryName.contains(LOOT)) return;
		int index = Integer.parseInt(categoryName.substring(LOOT.length()));
		Toml value = category.getValue();
		if (!((TomlAccessor) value).getCategories().isEmpty()) return;
		List<LootTable.LootEntry> listLoot = new ArrayList<>();
		TomlAccessor item = (TomlAccessor) value;
		for (Map.Entry<String, Entry<?>> entry : item.getEntries().entrySet()) {
			int metadata = (Integer) entry.getValue().getT();
			listLoot.add(LootTable.LootEntry.loot(entry.getKey(), metadata));
		}
		table.put(index, listLoot);
	}

	public static IItemConvertible getConvertible(String key) {
		NamespaceID id;
		try {
			id = getPermanent(key);
		} catch (HardIllegalArgumentException e) {
			return null;
		}
		Block<?> block = Blocks.blockMap.get(id);
		Item item = Item.itemsMap.get(id);
		if (block == null && item == null) {
			return getBlockByName(key);
		}
		if (block == null) return item;
		return block;
	}

	private static Toml createDefaultConfig(Map<Integer, List<LootTable.LootEntry>> table) {
		Toml properties = new Toml(new StringBuilder("Battle Towers Config 4.0.0!\n")
			.append("Older config file can be found in the same directory under the name of old_betterbattletowers.cfg\n")
			.append("Older config, will produce a different formated and should not be imitated for loot editing!\n")
			.append("If you want to edit the loot use the namespace id for the items.\n")
			.append("The numerical value is the metadata that is used to populate the chest. For most item the metadata is 0.\n")
			.toString()
		);
		properties.addCategory(GENERAL)
			.addEntry("VERSION", "Mod version.", version)
			.addEntry("RARITY", "Chance to spawn per chunk.", towerrarity)
			.addEntry("STARTING_BLOCK_ID", startingBlockId)
			.addEntry("STARTING_ITEM_ID", startingItemId)
			.addEntry("DARKEN_FLOORS", "Adds tainted cages that darken the floors. Greatly increases the difficulty and rewards of newer tower.", tint)
			.addEntry("LOOT_AMOUNT", "Base amount of loot for all tree structures.", lootamount);

		for (int i = 0; i < table.size(); i++) {
			Toml tomlCategory = properties.addCategory(String.format("Loot for %d Floor", i), LOOT + i);
			List<LootTable.LootEntry> entries = table.get(i);
			for (LootTable.LootEntry entry : entries) {
				String key = entry.namespaceID().replace('.', ':');
				tomlCategory.addEntry(key, entry.metadata());
			}
		}
		postProcessing = properties;
		return properties;
	}

	public static void processOldConfig() {
		if (!isOldConfig) return;
		Toml newProperties;
		if (postProcessing.getComment().isPresent()) {
			newProperties = new Toml(postProcessing.getComment().get());
		} else {
			newProperties = new Toml();
		}
		newProperties.addCategory(GENERAL)
			.addEntry("VERSION", "Mod version.", version)
			.addEntry("RARITY", "Chance to spawn per chunk.", towerrarity)
			.addEntry("STARTING_BLOCK_ID", startingBlockId)
			.addEntry("STARTING_ITEM_ID", startingItemId)
			.addEntry("DARKEN_FLOORS", "Adds tainted cages that darken the floors. Greatly increases the difficulty and rewards of newer tower.", tint)
			.addEntry("LOOT_AMOUNT", "Base amount of loot for all tree structures.", lootamount);

		Map<Integer, List<LootTable.LootEntry>> table = new HashMap<>();
		for (Map.Entry<String, Toml> category : ((TomlAccessor)postProcessing).getCategories().entrySet()) {
			addEntryToTable(category, table);
		}
		LOGGER.info("Starting to convert the old config into new format");
		for(List<LootTable.LootEntry> entryList : table.values()){
			for(LootTable.LootEntry entry : entryList){
				String namescape = entry.namespaceID();
				IItemConvertible convertible = getBlockByName(namescape);
				if(convertible == null) continue;
				NamespaceID id = convertible.asItem().namespaceID;
				entry.setNamespaceID(id.toString());
			}
		}

		for (int i = 0; i < table.size(); i++) {
			Toml tomlCategory = newProperties.addCategory(String.format("Loot for %d Floor", i), LOOT + i);
			List<LootTable.LootEntry> entries = table.get(i);
			for (LootTable.LootEntry entry : entries) {
				String key = entry.namespaceID().replace('.', ':');
				tomlCategory.addEntry(key, entry.metadata());
			}
		}

		TomlConfigHandler config = new TomlConfigHandler(MOD_ID + "4.0", newProperties, false);
		if (config.getConfigFile().exists()) {
			File file = new File(CONFIG_DIRECTORY + MOD_ID + "4.0" + ".cfg");
			if(file.delete()){
				LOGGER.warn("Old Battle Tower Config could not be deleted!");
			}
		}
		config.writeConfig();
		LOGGER.info("Converted old namspace to new.");
	}

	public static int getStartingItemId() {
		return startingItemId;
	}

	public static int getStartingBlockId() {
		return startingBlockId;
	}

	public static int getTowerrarity() {
		return towerrarity;
	}

	public static boolean isTint() {
		return tint;
	}


}
