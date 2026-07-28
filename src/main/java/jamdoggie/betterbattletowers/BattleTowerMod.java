package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.config.BattleTowerConfig;
import jamdoggie.betterbattletowers.entity.MobAgressiveZombiePig;
import jamdoggie.betterbattletowers.entity.golem.GolemVariants;
import jamdoggie.betterbattletowers.entity.golem.MobGolem;
import jamdoggie.betterbattletowers.block.TileEntityChestTower;
import jamdoggie.betterbattletowers.worldgen.data.loader.LootDataLoader;
import jamdoggie.betterbattletowers.worldgen.data.loader.TowerDataDefault;
import jamdoggie.betterbattletowers.worldgen.data.loader.TowerDataLoader;
import jamdoggie.betterbattletowers.worldgen.structures.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.entity.TileEntityDispatcher;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.sound.SoundTypes;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.pos.ChunkPosc;
import net.minecraft.core.world.pos.TilePos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.dependency.Key;

import java.util.Random;
import java.util.function.Supplier;

import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;


public class 	BattleTowerMod implements ModInitializer{
	public static final String MOD_ID = HalpLibe.registerMod("betterbattletowers");
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
		CommonEvents.BEFORE_GAME_START.listen(Key.of(MOD_ID), BattleTowerMod::beforeGameStart);
		CommonEvents.AFTER_ITEM_INIT.listen(Key.of(MOD_ID), BattleTowerMod::loadBlockItemDependencies);
		CommonEvents.RECIPES_NAMESPACE_INIT.listen(Key.of(MOD_ID), BattleTowerMod::initNamespaces);
		CommonEvents.RECIPES_READY.listen(Key.of(MOD_ID), BattleTowerMod::onRecipesReady);
	}

	public static void beforeGameStart() {
		BattleTowerConfig.init();
		BattleTowerBlocks.init();
		GolemVariants.init();
		registerWorldFeatureClass(WorldFeatureBattleTower.class, "BattleTower");
		registerWorldFeatureClass(WorldFeatureReverseTower.class, "ReverseBattleTower");
		registerWorldFeatureClass(WorldFeatureVanquishedTower.class, "VanquishedBattleTower");
		registerWorldFeatureClass(WorldFeatureSquareTower.class, "BastionTower");
		registerWorldFeatureClass(WorldFeatureVanquishedSquareTower.class, "VanquishedBastionTower");
		EntityDispatcher.getInstance().addMapping(MobGolem.class, NamespaceID.getPermanent(MOD_ID, "golem"), MobGolem::new); // "betterbattletowers.golem.name"
		EntityDispatcher.getInstance().addMapping(MobAgressiveZombiePig.class, NamespaceID.getPermanent(MOD_ID, "zombie_pigman"), MobAgressiveZombiePig::new); //"betterbattletowers.aggro_zombie_pigman.name"
		TileEntityDispatcher.addMapping(TileEntityChestTower.class, NamespaceID.getPermanent(MOD_ID, "tile_tower_chest")); //"betterbattletowers.ironchest"
		SoundTypes.loadSoundsJson(MOD_ID);
	}


	public static void loadBlockItemDependencies(){
		LootDataLoader.init();
		TowerDataLoader.init();
		TowerDataDefault.init();
	}

	public static void onRecipesReady() {
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/workbench.json");
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/blast_furnace.json");
		DataLoader.loadRecipesFromFile("/assets/" + MOD_ID + "/recipes/furnace.json");
	}

	public static void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	public static void generateTower(World world, Chunk chunk) {
		Random random = chunk.getChunkRandom(0x544f574552L);
		int randomNum = random.nextInt(BattleTowerConfig.getTowerrarity());
		if (randomNum == 0) {
			ChunkPosc posc = chunk.pos;
			int x = posc.x() * 16;
			int z = posc.z() * 16;
			int y = world.getHeightValue(x, z) - 1;
			Supplier<? extends WorldFeatureTower> supply = TOWER.getRandom(random);
			supply.get().place(world, random, new TilePos(x, y, z));
		}
	}
}
