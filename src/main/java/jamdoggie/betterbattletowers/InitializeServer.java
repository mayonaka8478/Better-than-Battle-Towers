package jamdoggie.betterbattletowers;

import net.fabricmc.api.DedicatedServerModInitializer;

import static jamdoggie.betterbattletowers.BetterBattleTowers.MOD_ID;
import static net.minecraft.core.sound.SoundTypes.register;

public class InitializeServer implements DedicatedServerModInitializer {
	@Override
	public void onInitializeServer() {
		// Register sounds for the server
		register(MOD_ID + ":mob.golem");
		register(MOD_ID + ":mob.golem.awaken");
		register(MOD_ID + ":mob.golem.death");
		register(MOD_ID + ":mob.golem.hurt");
		register(MOD_ID + ":mob.golem.special");
	}
}
