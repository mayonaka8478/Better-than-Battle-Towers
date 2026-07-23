package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.BattleTowerDetail;
import jamdoggie.betterbattletowers.entity.golem.MobGolem;
import jamdoggie.betterbattletowers.model.BattleTowerModel;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.util.dependency.Key;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;
import static jamdoggie.betterbattletowers.worldgen.data.loader.LootDataLoader.LAPIZ;

public class BattleTowerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(){
		ClientEvents.BEFORE_CLIENT_START.listen(Key.of(MOD_ID), BattleTowerClient::beforeClientStart);
		ClientEvents.AFTER_CLIENT_START.listen(Key.of(MOD_ID), BattleTowerClient::afterClientStart);
		ClientEvents.BLOCK_COLOR_RELOAD.listen(Key.of(MOD_ID), BattleTowerModel::initBlockColors);
		ClientEvents.BLOCK_MODEL_RELOAD.listen(Key.of(MOD_ID), BattleTowerModel::initBlockModels);
		ClientEvents.ENTITY_RENDERER_RELOAD.listen(Key.of(MOD_ID), BattleTowerModel::initEntityModels);
	}

	public static void afterClientStart() {
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



	public static void beforeClientStart() {
		LOGGER.info("");
		SoundRepository.namespaceAdded(MOD_ID);
	}
}
