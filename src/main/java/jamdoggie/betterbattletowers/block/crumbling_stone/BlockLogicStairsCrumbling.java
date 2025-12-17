package jamdoggie.betterbattletowers.block.crumbling_stone;

import jamdoggie.betterbattletowers.block.BattleTowerTriggerStandOn;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicStairs;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockLogicStairsCrumbling extends BlockLogicStairs implements BattleTowerTriggerStandOn {

	public BlockLogicStairsCrumbling(Block<?> block, Block<BlockLogicCrumbling> modelBlock) {
		super(block, modelBlock);
	}

	public BlockLogicStairsCrumbling setDropBlock (Block<?> dropBlock){
		((BlockLogicCrumbling)this.modelBlock.getLogic()).setDropBlock(dropBlock);
		return this;
	}

	@Override
	public void onEntityStandOn(World world, int x, int y, int z, Entity entity) {
		this.modelBlock.onEntityWalking(world, x, y, z, entity);
	}

	@Override
	public float blockStrength(World world, int x, int y, int z, Side side, Player player) {
		return this.modelBlock.blockStrength(world, x, y, z, side, player);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		if(BlockLogicCrumbling.getStageFromMetadata(meta) <= 0) return null;
		Block<?> block = ((BlockLogicCrumbling)this.modelBlock.getLogic()).getDropBlock();
		ItemStack[] result = dropCause != EnumDropCause.IMPROPER_TOOL ? new ItemStack[]{new ItemStack(block)} : null;
		if (result != null) {
			for(ItemStack stack : result) {
				stack.setMetadata(meta & 240);
				stack.itemID = block.id();
			}
		}

		return result;
	}
}
