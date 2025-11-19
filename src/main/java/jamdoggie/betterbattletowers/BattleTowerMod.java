package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.entity.MobGolem;
import jamdoggie.betterbattletowers.block.TileEntityChestTower;
import jamdoggie.betterbattletowers.worldgen.WorldFeatureReverseTower;
import jamdoggie.betterbattletowers.worldgen.WorldFeatureVanquishedTower;
import jamdoggie.betterbattletowers.worldgen.util.TowerProperties;
import jamdoggie.betterbattletowers.worldgen.WorldFeatureBattleTower;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.block.Blocks;
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

import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;


public class BattleTowerMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientModInitializer, ClientStartEntrypoint {
	public static final String MOD_ID = "betterbattletowers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		LOGGER.info("Better than Battle Towers initialized.");
	}

	@Override
	public void beforeGameStart() {
		BattleTowerConfig.init();
		BattleTowerBlocks.init();
		registerWorldFeatureClass(WorldFeatureBattleTower.class, "BattleTower");
		EntityHelper.createEntity(MobGolem.class, NamespaceID.getPermanent(MOD_ID, "golem"), "TowerGolem");
		EntityHelper.createTileEntity(TileEntityChestTower.class, NamespaceID.getPermanent(MOD_ID, "tile_tower_chest"), "TileTowerChest");
	}

	@Override
	public void beforeClientStart() {
		SoundRepository.registerNamespace(MOD_ID);

	}

	@Override
	public void afterClientStart() {
		MobInfoRegistry.register(MobGolem.class, "betterbattletowers.golem.name", "betterbattletowers.golem.desc", MobGolem.MAX_HEALTH, 10000,
			new MobInfoRegistry.MobDrop[]{
				new MobInfoRegistry.MobDrop(new ItemStack(Blocks.SLAB_STONE_POLISHED), 1.0f, 9, 12),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.DIAMOND), 1.0f, 1, 6)
			});
	}

	@Override
	public void afterGameStart() {
		BattleTowerConfig.processOldConfig();
		TowerProperties.init();
	}

	@Override
	public void onRecipesReady() {
		RecipeBuilder.Shaped(MOD_ID, "IO", "OI")
			.addInput('O', Items.OLIVINE)
			.addInput('I', Items.INGOT_IRON)
			.create("tinted_iron_bar_block", new ItemStack((BattleTowerBlocks.PRISON_BAR)));

		RecipeBuilder.Shaped(MOD_ID, "TIT", "TIT")
			.addInput('T', BattleTowerBlocks.PRISON_BAR)
			.addInput('I', Items.INGOT_IRON)
			.create("tinted_iron_bar_fence", new ItemStack((BattleTowerBlocks.PRISON_BAR_FENCE)));
	}

	@Override
	public void initNamespaces() {
		// no need
	}

	@Override
	public void onInitializeClient() {
		// no need
	}

	public static void generateTower(World world, Chunk chunk) {
		Random random = chunk.getChunkRandom(0x544f574552L);
//		int randomNum = random.nextInt(BattleTowerConfig.getTowerrarity());
		int randomNum = random.nextInt(5);
		if(randomNum == 0){
			int x =  chunk.xPosition * 16;
			int z =  chunk.zPosition * 16;
			int y  = world.getHeightValue(x, z) - 1;
//			WorldFeatureBattleTower.tower().place(world, random, x, y, z);
//			WorldFeatureReverseTower.tower().place(world, random, x, y, z);
			WorldFeatureVanquishedTower.tower().place(world, random, x, y, z);
		}
	}
}
