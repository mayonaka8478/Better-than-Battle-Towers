package jamdoggie.betterbattletowers.block;

import jamdoggie.betterbattletowers.BattleTower;
import jamdoggie.betterbattletowers.IDUtils;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public class ModBlocks {
	//ChestTower
	public static Block ChestTower = new BlockBuilder(BattleTower.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.FENCES_CONNECT, BlockTags.MINEABLE_BY_PICKAXE)
		.build("chest_tower", IDUtils.getCurrBlockId(), b -> new BlockLogicChestTower(b, Material.metal));

	public static void createBlocks() {

	}
}
