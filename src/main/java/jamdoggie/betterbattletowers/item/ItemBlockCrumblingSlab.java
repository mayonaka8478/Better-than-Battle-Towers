package jamdoggie.betterbattletowers.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlockSlab;
import net.minecraft.core.util.helper.Side;
import org.jetbrains.annotations.NotNull;

public class ItemBlockCrumblingSlab<T extends BlockLogic> extends ItemBlockSlab<T> {


	public ItemBlockCrumblingSlab(@NotNull Block<T> block) {
		super(block);
	}

	@Override
	public boolean canAccumulateInto(
		@NotNull ItemStack selfStack, @NotNull Block<?> block,
		int data, @NotNull Side side, double xHit, double yHit
	) {

		int placedData = data & -4;
		int stackData = selfStack.getMetadata() & -4;
		if (this.block != block || placedData != stackData) {
			return false;
		}
		return switch (data & 3) {
			case 0 -> yHit - 0.5F > -2.44140625E-4;
			case 2 -> yHit - 0.5F < 2.44140625E-4;
			default -> false;
		};

	}
}
