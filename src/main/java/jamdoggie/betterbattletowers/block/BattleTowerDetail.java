package jamdoggie.betterbattletowers.block;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.item.ItemStack;

import static jamdoggie.betterbattletowers.block.BattleTowerBlocks.*;
import static jamdoggie.betterbattletowers.block.BattleTowerBlocks.TOWER_CHEST;
import static net.minecraft.core.block.material.MaterialColor.registerManualBlockColor;

public class BattleTowerDetail {
	public static WeightedRandomBag<ItemStack> flowers = new WeightedRandomBag<>();
	private static boolean init = false;
	private BattleTowerDetail(){}

	public static void init(){
		if(init){
			return;
		}
		init = true;
		BattleTowerDetail.registerColors();
		BattleTowerDetail.registerSound();
		BattleTowerDetail.registerFlowerBag();
	}

	private static void registerSound() {
	}

	private static void registerColors() {
		registerManualBlockColor(TOWER_CHEST, 0, MaterialColor.paintedSilver);
		registerManualBlockColor(PRISON_BAR, 0, MaterialColor.paintedSilver);

		registerManualBlockColor(RUNIC_STONE, 0, MaterialColor.slate);
		registerManualBlockColor(CHISELED_RUNIC_STONE, 0, MaterialColor.slate);
		registerManualBlockColor(RUNIC_GLYPH_STONE, 0, MaterialColor.slate);

		for(int i = 3; i > 0; i--){
			registerManualBlockColor(CRUMBLING_STONE, i, MaterialColor.paintedGrey);
			registerManualBlockColor(SLAB_CRUMBLING_STONE, i, MaterialColor.paintedGrey);
			registerManualBlockColor(STAIRS_CRUMBLING_STONE, i, MaterialColor.paintedGrey);
		}

		registerManualBlockColor(SLAB_OBSIDIAN, 0, MaterialColor.paintedBlack);
		registerManualBlockColor(CARVED_OBSIDIAN, 0, MaterialColor.paintedBlack);
		registerManualBlockColor(STAIRS_OBSIDIAN, 0, MaterialColor.paintedBlack);

	}

	private static void registerFlowerBag() {
		flowers.addEntry(Blocks.FLOWER_ORANGE.getDefaultStack(), 1);
		flowers.addEntry(Blocks.FLOWER_PINK.getDefaultStack(), 1);
		flowers.addEntry(Blocks.FLOWER_PURPLE.getDefaultStack(), 1);
		flowers.addEntry(Blocks.FLOWER_RED.getDefaultStack(), 1);
		flowers.addEntry(Blocks.FLOWER_YELLOW.getDefaultStack(), 1);
		flowers.addEntry(Blocks.FLOWER_LIGHT_BLUE.getDefaultStack(), 1);
	}
}
