package jamdoggie.betterbattletowers.block;

import jamdoggie.betterbattletowers.block.tainted.BlockLogicTainedCage;
import jamdoggie.betterbattletowers.block.tainted.BlockLogicTaintedFence;
import jamdoggie.betterbattletowers.config.BattleTowerConfig;
import jamdoggie.betterbattletowers.BattleTowerMod;
import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicSlabCrumbling;
import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicStairsCrumbling;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public class BattleTowerBlocks {
	private static int id = BattleTowerConfig.getStartingBlockId();
	//ChestTower
	public static final Block<?> TOWER_CHEST = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.MINEABLE_BY_PICKAXE)
		.build("chest_tower", id++, b -> new BlockLogicChestTower(b, Material.metal));

	public static final Block<?> PRISON_BAR = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.build("prison_bar", id++, b -> new BlockLogicTainedCage(b, Material.metal));


	public static final Block<BlockLogicFenceThin> PRISON_BAR_FENCE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(2.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.build("prison_fence", id++, b -> new BlockLogicTaintedFence(b, Material.metal));

	/// HARDCORE
	public static final Block<?> RUNIC_STONE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(25.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.setImmovable()
		.build("runic_stone", id++, b -> new BlockLogic(b, Material.metal));

	public static final Block<?> CHISELED_RUNIC_STONE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(25.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.setImmovable()
		.build("runic_chiseled_stone", id++, b -> new BlockLogic(b, Material.metal));

	public static final Block<?> RUNIC_GLYPH_STONE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(25.0f)
		.setBlockSound(BlockSounds.METAL)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.setImmovable()
		.build("runic_glyph_stone", id++, b -> new BlockLogicGlyph(b, Material.metal));

	public static final Block<?> PILLAR_RUNIC = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
		.build("pillar_runic", id++, b -> new BlockLogic(b, Material.metal));

	public static final Block<BlockLogicCrumbling> CRUMBLING_STONE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(10.0f)
		.setHardness(1.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF, BlockTags.NOT_IN_CREATIVE_MENU)
		.build("crumbling_stone", id++, b -> new BlockLogicCrumbling(b, Blocks.STONE_POLISHED, Material.stone, 3.0f));

	public static final Block<BlockLogicSlabCrumbling> SLAB_CRUMBLING_STONE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(10.0f)
		.setHardness(1.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF, BlockTags.NOT_IN_CREATIVE_MENU)
		.build("slab_crumbling_stone", id++, b -> new BlockLogicSlabCrumbling(b, CRUMBLING_STONE)
			.setDropBlock(Blocks.SLAB_STONE_POLISHED)
		);

	public static final Block<BlockLogicStairsCrumbling> STAIRS_CRUMBLING_STONE = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(10.0f)
		.setHardness(1.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF, BlockTags.NOT_IN_CREATIVE_MENU)
		.build("stairs_crumbling_stone", id++, b -> new BlockLogicStairsCrumbling(b, CRUMBLING_STONE)
			.setDropBlock(Blocks.SLAB_STONE_POLISHED)
		);

	public static final Block<?> CARVED_OBSIDIAN = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF, BlockTags.PISTON_CRUSHING)
		.build("carved_obsidian", id++, b -> new BlockLogic(b, Material.stone));

	public static final Block<BlockLogicSlab> SLAB_OBSIDIAN = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF, BlockTags.PISTON_CRUSHING)
		.build("slab_obsidian", id++, b -> new BlockLogicSlab(b, CARVED_OBSIDIAN));

	public static final Block<BlockLogicStairs> STAIRS_OBSIDIAN = new BlockBuilder(BattleTowerMod.MOD_ID)
		.setResistance(2000.0f)
		.setHardness(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAN_HANG_OFF, BlockTags.PISTON_CRUSHING)
		.build("stairs_obsidian", id++, b -> new BlockLogicStairs(b, CARVED_OBSIDIAN));




	public static void init() {
		// to make sure the constant are initialized
	}
	private BattleTowerBlocks(){}
}
