package jamdoggie.betterbattletowers;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;

import static jamdoggie.betterbattletowers.OLDBattleTowerConfig.StrBuilder.str;
import static jamdoggie.betterbattletowers.BattleTower.MOD_ID;
import static jamdoggie.betterbattletowers.BattleTower.LOGGER;

public class OLDBattleTowerConfig {
	public static int TOWER_COUNT = 200;
	public static int RARITY = 10;

	public static int STARTING_BLOCK_ID = 6340;
	public static int STARTING_ITEM_ID = 26340;

	private static boolean isInit = false;

	public static class StrBuilder{
		public static StringBuilder str(){
			return new StringBuilder();
		}
		public static StringBuilder str(String string){
			return new StringBuilder(string);
		}
	}

	private static String key(String category, String key) {
		return str(category).append(".").append(key).toString();
	}


	public static void init(){
		if(isInit) return;
		isInit = true;

		Toml properties = new Toml("Battle Towers Config");
		properties.addCategory("General")
			.addEntry("TOWER_COUNT", TOWER_COUNT)
			.addEntry("RARITY", RARITY)
			.addEntry("STARTING_BLOCK_ID", STARTING_BLOCK_ID)
			.addEntry("STARTING_ITEM_ID", STARTING_ITEM_ID);
//		writeLootToConfig(properties);

		TomlConfigHandler config = new TomlConfigHandler(MOD_ID, properties);
		if(config.getConfigFile().exists()){
			config.loadConfig();
		}else{
			try {
				if (config.getConfigFile().createNewFile()) {
					LOGGER.info("Battle Towers Config initialized.");
				}
			}catch (IOException e){
				LOGGER.error("Battle Towers Config failed to generate, deleted the config and try again.");
				throw new RuntimeException(e);
			}
		}
		config.writeConfig();
		TOWER_COUNT = config.getInt(key("General", "TOWER_COUNT"));
		RARITY = config.getInt(key("General", "RARITY"));
		STARTING_BLOCK_ID = config.getInt(key("General", "STARTING_BLOCK_ID"));
		STARTING_ITEM_ID = config.getInt(key("General", "STARTING_ITEM_ID"));
//		readLootFromConfig(config);
	}

	public static String getNamespace(Object object){
		if (((!(object instanceof Item)) && (!(object instanceof Block)))) {
			throw new IllegalArgumentException("Expected a Item or Block, but got " + object.getClass().getSimpleName());
		}
		if(object instanceof Item){
			return ((Item) object).namespaceID.toString();
		}
		return ((Block<?>)object).namespaceId().toString();

	}

	private static void writeLootToConfig(Toml properties) {
		properties.addCategory("LOOT");


		//Tower Loot
		//floor1
		properties.addEntry("lootitem1_0", getNamespace(Items.STICK));
		properties.addEntry("lootitem1_1", getNamespace(Items.SEEDS_WHEAT));
		properties.addEntry("lootitem1_2", getNamespace(Items.AMMO_PEBBLE));
		properties.addEntry("lootitem1_3", getNamespace(Blocks.SAND));
		//floor2
		properties.addEntry("lootitem2_0", getNamespace(Items.COAL));
		properties.addEntry("lootitem2_1", getNamespace(Items.STICK));
		properties.addEntry("lootitem2_2", getNamespace(Blocks.PLANKS_OAK));
		properties.addEntry("lootitem2_3", getNamespace(Blocks.WOOL));
		//floor3
		properties.addEntry("lootitem3_0", getNamespace(Items.FEATHER_CHICKEN));
		properties.addEntry("lootitem3_1", getNamespace(Items.FOOD_BREAD));
		properties.addEntry("lootitem3_2", getNamespace(Blocks.GLASS));
		properties.addEntry("lootitem3_3", getNamespace(Blocks.MUSHROOM_BROWN));
		//floor4
		properties.addEntry("lootitem4_0", getNamespace(Items.FEATHER_CHICKEN));
		properties.addEntry("lootitem4_1", getNamespace(Items.FOOD_BREAD));
		properties.addEntry("lootitem4_2", getNamespace(Blocks.GLASS));
		properties.addEntry("lootitem4_3", getNamespace(Blocks.MUSHROOM_BROWN));
		//floor5
		properties.addEntry("lootitem5_0", getNamespace(Items.BOOK));
		properties.addEntry("lootitem5_1", getNamespace(Items.BRICK_CLAY));
		properties.addEntry("lootitem5_2", getNamespace(Items.ORE_RAW_IRON));
		properties.addEntry("lootitem5_3", getNamespace(Items.ROPE));
		//floor6
		properties.addEntry("lootitem6_0", getNamespace(Items.BOOK));
		properties.addEntry("lootitem6_1", getNamespace(Items.FLINT));
		properties.addEntry("lootitem6_2", getNamespace(Items.DUST_REDSTONE));
		properties.addEntry("lootitem6_3", getNamespace(Items.ORE_RAW_GOLD));
		//floor7
		properties.addEntry("lootitem7_0", getNamespace(Items.BOOK));
		properties.addEntry("lootitem7_1", getNamespace(Items.INGOT_GOLD));
		properties.addEntry("lootitem7_2", getNamespace(Items.INGOT_IRON));
		properties.addEntry("lootitem7_3", getNamespace(Items.BUCKET_LAVA));
		//floor8
		properties.addEntry("lootitem8_0", getNamespace(Blocks.TNT));
		properties.addEntry("lootitem8_1", getNamespace(Items.OLIVINE));
		properties.addEntry("lootitem8_2", getNamespace(Items.INGOT_GOLD));
		properties.addEntry("lootitem8_3", getNamespace(Items.CHAINLINK));
		//floor9
		properties.addEntry("lootitem9_0", getNamespace(Items.QUARTZ));
		properties.addEntry("lootitem9_1", getNamespace(Items.DYE));
		properties.addEntry("lootitem9_2", getNamespace(Items.DUST_REDSTONE));
		properties.addEntry("lootitem9_3", getNamespace(Items.BONE));
		//floortop
		properties.addEntry("lootitemtop_0", getNamespace(Blocks.MESH_GOLD));
		properties.addEntry("lootitemtop_1", getNamespace(Items.INGOT_IRON));
		properties.addEntry("lootitemtop_2", getNamespace(Items.DUST_REDSTONE));
		properties.addEntry("lootitemtop_3", getNamespace(Items.DIAMOND));
	}

	private static void readLootFromConfig(TomlConfigHandler config) {


	}


	public static String lootitem1_0 = BattleTower.config.getString("lootitem1_0");
	public static String lootitem1_1 = BattleTower.config.getString("lootitem1_1");
	public static String lootitem1_2 = BattleTower.config.getString("lootitem1_2");
	public static String lootitem1_3 = BattleTower.config.getString("lootitem1_3");

	public static String lootitem2_0 = BattleTower.config.getString("lootitem2_0");
	public static String lootitem2_1 = BattleTower.config.getString("lootitem2_1");
	public static String lootitem2_2 = BattleTower.config.getString("lootitem2_2");
	public static String lootitem2_3 = BattleTower.config.getString("lootitem2_3");

	public static String lootitem3_0 = BattleTower.config.getString("lootitem3_0");
	public static String lootitem3_1 = BattleTower.config.getString("lootitem3_1");
	public static String lootitem3_2 = BattleTower.config.getString("lootitem3_2");
	public static String lootitem3_3 = BattleTower.config.getString("lootitem3_3");

	public static String lootitem4_0 = BattleTower.config.getString("lootitem4_0");
	public static String lootitem4_1 = BattleTower.config.getString("lootitem4_1");
	public static String lootitem4_2 = BattleTower.config.getString("lootitem4_2");
	public static String lootitem4_3 = BattleTower.config.getString("lootitem4_3");

	public static String lootitem5_0 = BattleTower.config.getString("lootitem5_0");
	public static String lootitem5_1 = BattleTower.config.getString("lootitem5_1");
	public static String lootitem5_2 = BattleTower.config.getString("lootitem5_2");
	public static String lootitem5_3 = BattleTower.config.getString("lootitem5_3");

	public static String lootitem6_0 = BattleTower.config.getString("lootitem6_0");
	public static String lootitem6_1 = BattleTower.config.getString("lootitem6_1");
	public static String lootitem6_2 = BattleTower.config.getString("lootitem6_2");
	public static String lootitem6_3 = BattleTower.config.getString("lootitem6_3");

	public static String lootitem7_0 = BattleTower.config.getString("lootitem7_0");
	public static String lootitem7_1 = BattleTower.config.getString("lootitem7_1");
	public static String lootitem7_2 = BattleTower.config.getString("lootitem7_2");
	public static String lootitem7_3 = BattleTower.config.getString("lootitem7_3");

	public static String lootitem8_0 = BattleTower.config.getString("lootitem8_0");
	public static String lootitem8_1 = BattleTower.config.getString("lootitem8_1");
	public static String lootitem8_2 = BattleTower.config.getString("lootitem8_2");
	public static String lootitem8_3 = BattleTower.config.getString("lootitem8_3");

	public static String lootitem9_0 = BattleTower.config.getString("lootitem9_0");
	public static String lootitem9_1 = BattleTower.config.getString("lootitem9_1");
	public static String lootitem9_2 = BattleTower.config.getString("lootitem9_2");
	public static String lootitem9_3 = BattleTower.config.getString("lootitem9_3");

	public static String lootitemtop_0 = BattleTower.config.getString("lootitemtop_0");
	public static String lootitemtop_1 = BattleTower.config.getString("lootitemtop_1");
	public static String lootitemtop_2 = BattleTower.config.getString("lootitemtop_2");
	public static String lootitemtop_3 = BattleTower.config.getString("lootitemtop_3");
	}
