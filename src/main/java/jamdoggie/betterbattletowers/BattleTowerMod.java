package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.entity.EntityGolem;
import jamdoggie.betterbattletowers.entity.TileEntityChestTower;
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
	public static final BattleTowerMod INSTANCE = new BattleTowerMod();
	private int towercount;


	@Override
	public void onInitialize() {
		LOGGER.info("Better than Battle Towers initialized.");
	}

	@Override
	public void beforeGameStart() {
		BattleTowerBlocks.init();
		registerWorldFeatureClass(WorldFeatureBattleTower.class, "BattleTower");
		EntityHelper.createEntity(EntityGolem.class, NamespaceID.getPermanent(MOD_ID, "golem"), "TowerGolem");
		EntityHelper.createTileEntity(TileEntityChestTower.class, NamespaceID.getPermanent(MOD_ID, "tile_tower_chest"), "TileTowerChest");
	}

	@Override
	public void beforeClientStart() {
		SoundRepository.registerNamespace(MOD_ID);

	}

	@Override
	public void afterClientStart() {
		MobInfoRegistry.register(EntityGolem.class, "betterbattletowers.golem.name", "betterbattletowers.golem.desc", 300, 10000,
			new MobInfoRegistry.MobDrop[]{
				new MobInfoRegistry.MobDrop(new ItemStack(Blocks.SLAB_STONE_POLISHED), 1.0f, 9, 12),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.DIAMOND), 1.0f, 1, 6)
			});
	}

	@Override
	public void afterGameStart() {
		BattleTowerConfig.init();
		this.towercount = BattleTowerConfig.getTowerCount();
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

	public void generateTower(World world, Random random, int chunkX, int chunkZ) {
		if (towercount >= BattleTowerConfig.getRARITY() * 100) {
			if (random.nextInt(2) == 0) {
				int ix = chunkX + 8;
				int iy = chunkZ + 8;
				int iz = world.getHeightValue(ix, iy);
				if ((new WorldFeatureBattleTower()).place(world, random, ix, iz, iy)) {
					towercount = 0;
				}
			}
		} else {
			towercount++;
		}
	}
}
