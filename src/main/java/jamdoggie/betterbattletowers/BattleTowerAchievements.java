package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.util.collection.NamespaceID;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static net.minecraft.client.gui.achievements.data.AchievementPages.overworldPage;

public class BattleTowerAchievements {
	public static Achievement DEFEAT_GOLEM;
	private static boolean init = false;

	private BattleTowerAchievements(){}

	public static void init(){
		if(init) return;
		init = true;
		DEFEAT_GOLEM = (new Achievement(NamespaceID.getPermanent(MOD_ID, "defeate_golem"), "kill.golem", BattleTowerBlocks.RUNIC_GLYPH_STONE, null)
			.setClientsideAchievement()
			.setType(Achievement.TYPE_SECRET)
			.registerAchievement());

		overworldPage.addAchievement(BattleTowerAchievements.DEFEAT_GOLEM, 6, -8);
	}
}
