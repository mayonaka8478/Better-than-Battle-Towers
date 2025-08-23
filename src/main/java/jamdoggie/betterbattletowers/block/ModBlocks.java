package jamdoggie.betterbattletowers.block;

import jamdoggie.betterbattletowers.BetterBattleTowers;
import jamdoggie.betterbattletowers.IDUtils;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public class ModBlocks {
	//ChestTower
	public static final Block ChestTower = new BlockBuilder(BetterBattleTowers.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.FENCES_CONNECT, BlockTags.MINEABLE_BY_PICKAXE)
		.build("tower_chest", IDUtils.getCurrBlockId(), b -> new BlockLogicChest(b, Material.stone));

	public static void createBlocks() {

	}
}
