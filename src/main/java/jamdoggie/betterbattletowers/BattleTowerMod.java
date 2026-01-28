package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.block.BattleTowerDetail;
import jamdoggie.betterbattletowers.config.BattleTowerConfig;
import jamdoggie.betterbattletowers.entity.MobAgressiveZombiePig;
import jamdoggie.betterbattletowers.entity.golem.GolemVariants;
import jamdoggie.betterbattletowers.entity.golem.MobGolem;
import jamdoggie.betterbattletowers.block.TileEntityChestTower;
import jamdoggie.betterbattletowers.worldgen.data.loader.LootDataLoader;
import jamdoggie.betterbattletowers.worldgen.data.loader.TowerDataDefault;
import jamdoggie.betterbattletowers.worldgen.data.loader.TowerDataLoader;
import jamdoggie.betterbattletowers.worldgen.structures.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.*;

import java.util.Random;
import java.util.function.Supplier;

import static jamdoggie.betterbattletowers.worldgen.data.loader.LootDataLoader.LAPIZ;
import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;


public class BattleTowerMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientModInitializer, ClientStartEntrypoint {
	public static final String MOD_ID = "betterbattletowers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static WeightedRandomBag<Supplier<? extends WorldFeatureTower>> TOWER = new WeightedRandomBag();
	{
		TOWER.addEntry(WorldFeatureSquareTower::new, 7);
		TOWER.addEntry(WorldFeatureBattleTower::new, 7);
		TOWER.addEntry(WorldFeatureReverseTower::new, 4);
		TOWER.addEntry(WorldFeatureVanquishedTower::new, 2);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Better than Battle Towers initialized.");
	}

	@Override
	public void beforeGameStart() {
		BattleTowerConfig.init();
		BattleTowerBlocks.init();
		registerWorldFeatureClass(WorldFeatureBattleTower.class, "BattleTower");
		registerWorldFeatureClass(WorldFeatureReverseTower.class, "ReverseBattleTower");
		registerWorldFeatureClass(WorldFeatureVanquishedTower.class, "VanquishedBattleTower");
		registerWorldFeatureClass(WorldFeatureSquareTower.class, "BastionTower");
		registerWorldFeatureClass(WorldFeatureVanquishedSquareTower.class, "VanquishedBastionTower");
		EntityHelper.createEntity(MobGolem.class, NamespaceID.getPermanent(MOD_ID, "golem"), "betterbattletowers.golem.name");
		EntityHelper.createEntity(MobAgressiveZombiePig.class, NamespaceID.getPermanent(MOD_ID, "zombie_pigman"), "betterbattletowers.aggro_zombie_pigman.name");
		EntityHelper.createTileEntity(TileEntityChestTower.class, NamespaceID.getPermanent(MOD_ID, "tile_tower_chest"), "betterbattletowers.ironchest");
	}

	@Override
	public void beforeClientStart() {
		LOGGER.info("");
		SoundRepository.registerNamespace(MOD_ID);
	}

	@Override
	public void afterClientStart() {
		BattleTowerAchievements.init();
		BattleTowerDetail.init();
		ItemStack lapiz = new ItemStack(Items.DYE);
		lapiz.setMetadata(LAPIZ);
		MobInfoRegistry.register(MobGolem.class, "betterbattletowers.golem.name", "betterbattletowers.golem.desc", MobGolem.MAX_HEALTH, 10000,
			new MobInfoRegistry.MobDrop[]{
				new MobInfoRegistry.MobDrop(new ItemStack(Items.DIAMOND), 1.0f, 1, 5),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.ORE_RAW_IRON), 1.0f, 0, 3),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.DUST_REDSTONE), 1.0f, 0, 5),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.OLIVINE), 1.0f, 0, 3),
				new MobInfoRegistry.MobDrop(lapiz, 1.0f, 0, 5),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.ORE_RAW_GOLD), 1.0f, 0, 3)
			});
	}

	@Override
	public void afterGameStart() {
		GolemVariants.init();
		LootDataLoader.init();
		TowerDataLoader.init();
		TowerDataDefault.init();
	}

	@Override
	public void onRecipesReady() {
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/workbench.json");
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/blast_furnace.json");
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/furnace.json");
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	@Override
	public void onInitializeClient() {
		// no need
	}

	public static void generateTower(World world, Chunk chunk) {
		Random random = chunk.getChunkRandom(0x544f574552L);
		int randomNum = random.nextInt(BattleTowerConfig.getTowerrarity());
		if (randomNum == 0) {
			int x = chunk.xPosition * 16;
			int z = chunk.zPosition * 16;
			int y = world.getHeightValue(x, z) - 1;
			Supplier<? extends WorldFeatureTower> supply = TOWER.getRandom(random);
			supply.get().place(world, random, x, y, z);
		}
	}
}
