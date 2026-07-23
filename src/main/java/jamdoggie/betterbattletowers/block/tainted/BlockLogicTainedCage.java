package jamdoggie.betterbattletowers.block.tainted;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockLogicTainedCage extends BlockLogic {
	public BlockLogicTainedCage(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlockOnCondition(@NotNull WorldSource source, @NotNull TilePosc tilePos) {
		return false;
	}

	@Override
	public boolean blocksLight() {
		return true;
	}

	@Override
	public ItemStack @Nullable [] getBreakResult(@NotNull World world, @NotNull EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		if (dropCause == EnumDropCause.PICK_BLOCK || dropCause== EnumDropCause.SILK_TOUCH){
			return new ItemStack[]{new ItemStack(this.block)};
		}
		return new ItemStack[]{new ItemStack(BattleTowerBlocks.PRISON_BAR_FENCE, world.rand.nextInt(3) + 3)};
	}
}
