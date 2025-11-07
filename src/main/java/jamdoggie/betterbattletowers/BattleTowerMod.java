package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.ModBlocks;
import jamdoggie.betterbattletowers.entity.EntityGolem;
import jamdoggie.betterbattletowers.entity.TileEntityChestTower;
import jamdoggie.betterbattletowers.worldgen.WorldGenTower;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.*;

import java.util.Properties;
import java.util.Random;

import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;


public class BattleTowerMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientModInitializer, ClientStartEntrypoint {
	public static final String MOD_ID = "betterbattletowers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ConfigHandler config;

	static {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "6340");
		prop.setProperty("starting_item_id", "26340");
		prop.setProperty("towercount", "200");
		prop.setProperty("rarity", "10");


		//Tower Loot
		//floor1
//		prop.setProperty("level1", Items.STICK.namespaceID.toString());
//		prop.setProperty("level1", Items.SEEDS_WHEAT.namespaceID.toString());
//		prop.setProperty("level1", Items.AMMO_PEBBLE.namespaceID.toString());
//		prop.setProperty("level1", Blocks.SAND.namespaceId().toString());

		prop.setProperty("lootitem1_0", "Item.stick");
		prop.setProperty("lootitem1_1", "Item.seeds.wheat");
		prop.setProperty("lootitem1_2", "Item.ammo.pebble");
		prop.setProperty("lootitem1_3", "Block.sand");
		//floor2
//		prop.setProperty("lootitem2_0", Items.COAL.namespaceID.toString());
//		prop.setProperty("lootitem2_1", Items.SEEDS_WHEAT.namespaceID.toString());
//		prop.setProperty("lootitem2_2", Blocks.PLANKS_OAK.namespaceId().toString());
//		prop.setProperty("lootitem2_3", Blocks.WOOL.namespaceId().toString());

		prop.setProperty("lootitem2_0", "Item.coal");
		prop.setProperty("lootitem2_1", "Item.stick");
		prop.setProperty("lootitem2_2", "Block.planks.oak");
		prop.setProperty("lootitem2_3", "Block.wool");
		//floor3
		prop.setProperty("lootitem3_0", "Item.feather.chicken");
		prop.setProperty("lootitem3_1", "Item.food.bread");
		prop.setProperty("lootitem3_2", "Block.glass");
		prop.setProperty("lootitem3_3", "Block.mushroom.brown");
		//floor4
		prop.setProperty("lootitem4_0", "Item.feather.chicken");
		prop.setProperty("lootitem4_1", "Item.food.bread");
		prop.setProperty("lootitem4_2", "Block.glass");
		prop.setProperty("lootitem4_3", "Block.mushroom.brown");
		//floor5
		prop.setProperty("lootitem5_0", "Block.stairs.planks.oak");
		prop.setProperty("lootitem5_1", "Block.brick.clay");
		prop.setProperty("lootitem5_2", "Item.ingot.iron");
		prop.setProperty("lootitem5_3", "Item.rope");
		//floor6
		prop.setProperty("lootitem6_0", "Block.ladder.oak");
		prop.setProperty("lootitem6_1", "Item.flint");
		prop.setProperty("lootitem6_2", "Item.dust.redstone");
		prop.setProperty("lootitem6_3", "Item.ingot.gold");
		//floor7
		prop.setProperty("lootitem7_0", "Block.pumpkin.carved.active");
		prop.setProperty("lootitem7_1", "Block.rail");
		prop.setProperty("lootitem7_2", "Item.ore.raw.iron");
		prop.setProperty("lootitem7_3", "Item.bucket.lava");
		//floor8
		prop.setProperty("lootitem8_0", "Block.tnt");
		prop.setProperty("lootitem8_1", "Block.slate");
		prop.setProperty("lootitem8_2", "Item.ore.raw.gold");
		prop.setProperty("lootitem8_3", "Item.chainlink");
		//floor9
		prop.setProperty("lootitem9_0", "Item.quartz");
		prop.setProperty("lootitem9_1", "Item.olivine");
		prop.setProperty("lootitem9_2", "Item.dust.redstone");
		prop.setProperty("lootitem9_3", "Block.mesh");
		//floortop
		prop.setProperty("lootitemtop_0", "Block.mesh.gold");
		prop.setProperty("lootitemtop_1", "Item.ingot.gold");
		prop.setProperty("lootitemtop_2", "Item.ingot.iron");
		prop.setProperty("lootitemtop_3", "Item.diamond");


		config = new ConfigHandler(BattleTowerMod.MOD_ID, prop);
		towercount = config.getInt("towercount");
		rarity = config.getInt("rarity");
		config.updateConfig();
	}

	public static BattleTowerMod instance;
	public static int towercount;
	public static int rarity;
	private final int DEFAULT_RARITY = 3;

	public BattleTowerMod() {
		instance = this;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Better than Battle Towers initialized.");
		registerWorldFeatureClass(WorldGenTower.class, "BattleTower");
	}

	@Override
	public void beforeGameStart() {
		ModBlocks.createBlocks();
		EntityHelper.createEntity(EntityGolem.class, NamespaceID.getPermanent(MOD_ID, "golem"), "TowerGolem");
		EntityHelper.createTileEntity(TileEntityChestTower.class, NamespaceID.getPermanent(MOD_ID, "tile_tower_chest"), "TileTowerChest");
	}

	@Override
	public void beforeClientStart() {
		SoundRepository.registerNamespace(MOD_ID);
	}

	@Override
	public void afterClientStart() {

	}

	@Override
	public void afterGameStart() {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			// This doesn't work in afterClientStart for some reason
			MobInfoRegistry.register(EntityGolem.class, "betterbattletowers.golem.name", "betterbattletowers.golem.desc", 300, 10000,
				new MobInfoRegistry.MobDrop[]{
					new MobInfoRegistry.MobDrop(new ItemStack(Blocks.SLAB_STONE_POLISHED), 1.0f, 9, 12),
					new MobInfoRegistry.MobDrop(new ItemStack(Items.DIAMOND), 1.0f, 1, 6)
			});
		}

	}

	@Override
	public void onRecipesReady() {
	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void onInitializeClient() {

	}

	public void GenerateSurface(World world, Random random, int chunkX, int chunkZ) {
		if (towercount >= rarity * 100) {
			if (random.nextInt(2) == 0) {
				int k = chunkX + random.nextInt(16) + 8;
				int i1 = chunkZ + random.nextInt(16) + 8;
				int l = world.getHeightValue(k, i1);


				if ((new WorldGenTower()).place(world, random, k, l, i1)) {
					towercount = 0;
				}
			}
		} else {
			towercount++;
		}
	}
}
