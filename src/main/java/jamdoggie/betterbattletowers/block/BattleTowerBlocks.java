package jamdoggie.betterbattletowers.block;

import jamdoggie.betterbattletowers.BattleTowerMod;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceThin;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

import static jamdoggie.betterbattletowers.BattleTowerConfig.*;

public class BattleTowerBlocks {
	//ChestTower
	public static Block<?> TOWER_CHEST = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.MINEABLE_BY_PICKAXE)
		.build("chest_tower", STARTING_BLOCK_ID++, b -> new BlockLogicChestTower(b, Material.metal));

	public static Block<?> PRISON_BAR = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.build("prison_bar", STARTING_BLOCK_ID++, b -> new BlockLogicNonSolid(b, Material.metal));


	public static Block<BlockLogicFenceThin> PRISON_BAR_FENCE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(1200.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.build("prison_fence", STARTING_BLOCK_ID++, b -> new BlockLogicNonSolidFence(b, Material.metal));

	public static void createBlocks() {

	}
}
