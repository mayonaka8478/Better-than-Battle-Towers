package jamdoggie.betterbattletowers.block.crumbling_stone;

import jamdoggie.betterbattletowers.mixin.interfaces.BattleTowerTriggerStandOn;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicStairs;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockLogicStairsCrumbling extends BlockLogicStairs implements BattleTowerTriggerStandOn {
	public Block<?> dropBlock;

	public BlockLogicStairsCrumbling(Block<?> block, Block<BlockLogicCrumbling> modelBlock, Block<?> dropBlock) {
		super(block, modelBlock);
		this.dropBlock = dropBlock;
	}

	@Override
	public void onEntityStandOn(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Entity entity) {
		BlockLogicCrumbling.updateBlock(world,tilePos, entity, this.block);
	}

	@Override
	public void onEntityWalkedOn(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Entity entity) {
		BlockLogicCrumbling.updateBlock(world, tilePos, entity, this.block);
	}


	@Override
	public float getStrength(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Side side, @NotNull Player player) {
		return this.modelBlock.getStrength(world, tilePos, side, player);
	}

	public Block<?> getDropBlock (){
		return this.dropBlock;
	}

	@Override
	public ItemStack[] getBreakResult(@NotNull World world, @NotNull EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		if(BlockLogicCrumbling.getStageFromMetadata(meta) <= 0) return null;
		Block<?> block = this.getDropBlock();
		ItemStack[] result = dropCause != EnumDropCause.IMPROPER_TOOL ? new ItemStack[]{new ItemStack(block)} : null;
		if (result != null) {
			for(ItemStack stack : result) {
				stack.setMetadata(meta & 240);
				stack.itemID = block.id();
			}
		}

		return result;
	}

	@Override
	public @NotNull String getLanguageKey(int meta) {
		String lang = super.getLanguageKey(meta);
		if (BlockLogicCrumbling.getStageFromMetadata(meta) != 0) {
			return lang;
		}
		return lang.replace("crumbling", "bridle");
	}

	@Override
	public int getPlacedData(@Nullable Player player, @NotNull ItemStack itemStack, @NotNull World world, @NotNull TilePosc tilePos, @NotNull Side side, double xHit, double yHit) {
		return itemStack.getMetadata();
	}
}
