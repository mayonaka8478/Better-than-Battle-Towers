package jamdoggie.betterbattletowers.block.crumbling_stone;

import jamdoggie.betterbattletowers.block.BattleTowerTriggerStandOn;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSlab;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockLogicSlabCrumbling extends BlockLogicSlab implements BattleTowerTriggerStandOn{
	public Block<?> dropBlock;
	public enum SLAB_STATE{
		LOWER, FULL, UPPER;
	}

	public BlockLogicSlabCrumbling(Block<?> block, Block<BlockLogicCrumbling> modelBlock, Block<?> dropBlock) {
		super(block, modelBlock);
		this.dropBlock = dropBlock;
	}

	public BlockLogicSlabCrumbling(Block<?> block, Block<BlockLogicCrumbling> modelBlock, int modelBlockMetadata, Block<?> dropBlock) {
		super(block, modelBlock, modelBlockMetadata);
		this.dropBlock = dropBlock;
	}

	public Block<?> getDropBlock (){
		return this.dropBlock;
	}


	@Override
	public void onEntityStandOn(World world, int x, int y, int z, Entity entity) {
		BlockLogicCrumbling.updateBlock(world, x, y, z, entity, this.block);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		BlockLogicCrumbling.updateBlock(world, x, y, z, entity, this.block);
	}

	@Override
	public float blockStrength(World world, int x, int y, int z, Side side, Player player) {
		return this.modelBlock.blockStrength(world, x, y, z, side, player);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		if(BlockLogicCrumbling.getStageFromMetadata(meta) <= 0) return null;
		Block<?> block = this.getDropBlock();
		ItemStack[] result = dropCause != EnumDropCause.IMPROPER_TOOL ? new ItemStack[]{new ItemStack(block)} : null;
		if (result != null) {
			for(ItemStack stack : result) {
				stack.setMetadata(meta & 240);
				stack.itemID = block.id();
				if ((meta & 3) == 1 && dropCause != EnumDropCause.PICK_BLOCK) {
					stack.stackSize = 2;
				} else {
					stack.stackSize = 1;
				}
			}
		}
		return result;
	}

	@Override
	public String getLanguageKey(int meta) {
		String lang = super.getLanguageKey(meta);
		if (BlockLogicCrumbling.getStageFromMetadata(meta) != 0) {
			return lang;
		}
		return lang.replace("crumbling", "bridle");
	}
}
