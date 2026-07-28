package jamdoggie.betterbattletowers.mixin.mixins.accessor;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.CreativeMenuContents;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(CreativeMenuContents.class)
public interface CreativeMenuContentsAccessor {
	@Invoker
	static void callAddBlock(List<ItemStack> list, @NotNull Block<?>... blocks) {
		throw new UnsupportedOperationException();
	}
}
