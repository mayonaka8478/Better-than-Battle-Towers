package jamdoggie.betterbattletowers.config;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;
import java.io.UncheckedIOException;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;

public class BattleTowerConfig {
	public static final String GENERAL = "GENERAL";
	private static int towerrarity = 300;
	private static int startingBlockId = 6340;
	private static int startingItemId = 26340;
	private static int lootamount = 8;
	private static String version = "4.1";
	private static boolean tint = false;
	private static boolean hardcore = false;
	private static boolean isInit = false;

	private BattleTowerConfig() {}

	private static String key(String category, String key) {
		return category + "." + key;
	}

	public static void init() {
		if (isInit) return;
		isInit = true;
		Toml properties = createDefaultConfig();
		TomlConfigHandler config = new TomlConfigHandler(MOD_ID, properties, false);
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
		version = config.getString(key(GENERAL, "VERSION"));
		towerrarity = config.getInt(key(GENERAL, "RARITY"));
		lootamount = config.getInt(key(GENERAL, "LOOT_AMOUNT"));
		startingBlockId = config.getInt(key(GENERAL, "STARTING_BLOCK_ID"));
		startingItemId = config.getInt(key(GENERAL, "STARTING_ITEM_ID"));
		tint = config.getBoolean(key(GENERAL, "DARKEN_FLOORS"));
		hardcore = config.getBoolean(key(GENERAL, "HARDCORE"));
	}

	private static Toml createDefaultConfig() {
		Toml properties = new Toml(
			new StringBuilder("Battle Towers Config 4.1!\n")
			.append("Older Config are not supported!\n")
			.append("Loot can now be found in the betterbattletowersloot.json as well an comprehensive guide to build your own!\n")
			.toString()
		);
		properties.addCategory(GENERAL)
			.addEntry("VERSION", "Config version.", version)
			.addEntry("RARITY", "Chance to spawn per chunk.", towerrarity)
			.addEntry("STARTING_BLOCK_ID", startingBlockId)
			.addEntry("STARTING_ITEM_ID", startingItemId)
			.addEntry("DARKEN_FLOORS", "Adds tainted cages that darken the floors. Greatly increases the difficulty and rewards of newer tower.", tint)
			.addEntry("LOOT_AMOUNT", "Base amount of loot for all tree structures.", lootamount)
			.addEntry("HARDCORE", "Significantly increases the tower difficulty, does not increase drops.", hardcore);
		return properties;
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

	public static boolean isHardcore() {
		return hardcore;
	}

	public static int getLootAmount() {
		return lootamount;
	}
}
