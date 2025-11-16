package jamdoggie.betterbattletowers.block;

import jamdoggie.betterbattletowers.BattleTowerConfig;
import jamdoggie.betterbattletowers.BattleTowerMod;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceThin;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public class BattleTowerBlocks {
	private static int id = BattleTowerConfig.getStartingBlockId();
	//ChestTower
	public static final Block<?> TOWER_CHEST = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.MINEABLE_BY_PICKAXE)
		.build("chest_tower", id++, b -> new BlockLogicChestTower(b, Material.metal));

	public static final Block<?> PRISON_BAR = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.build("prison_bar", id++, b -> new BlockLogicTainedCage(b, Material.metal));


	public static final Block<BlockLogicFenceThin> PRISON_BAR_FENCE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.build("prison_fence", id++, b -> new BlockLogicTaintedFence(b, Material.metal));

	public static void init() {
		// to make sure the constant are initialized
	}
	private BattleTowerBlocks(){}
}
