package jamdoggie.betterbattletowers.mixin.mixins.creative;


import jamdoggie.betterbattletowers.block.crumbling_stone.BlockLogicCrumbling;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.CreativeMenuContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static jamdoggie.betterbattletowers.block.BattleTowerBlocks.*;
import static jamdoggie.betterbattletowers.mixin.mixins.accessor.CreativeMenuContentsAccessor.callAddBlock;

@Mixin(value = CreativeMenuContents.class)
public abstract class CreativeMenuContentMixin {

	@Inject(
		method = "addNaturalTypes",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/player/inventory/CreativeMenuContents;addBlock(Ljava/util/List;[Lnet/minecraft/core/block/Block;)V",
			ordinal = 29
		)
	)
	private static void obsidianBlock(List<ItemStack> list, CallbackInfo ci){
		callAddBlock(list, SLAB_OBSIDIAN);
		callAddBlock(list, STAIRS_OBSIDIAN);
	}

	@Inject(method = "addStoneTypes", at = @At("TAIL"))
	private static void addStoneTypes(List<ItemStack> list, CallbackInfo ci){
		callAddBlock(list, RUNIC_STONE);
		callAddBlock(list, CHISELED_RUNIC_STONE);
		callAddBlock(list, RUNIC_GLYPH_STONE);
		callAddBlock(list, PILLAR_RUNIC);

		ItemStack stack = null;
		stack = new ItemStack(CRUMBLING_STONE);
		stack.setMetadata(BlockLogicCrumbling.setMetadataFromStage(3));
		list.add(stack);
		stack = new ItemStack(SLAB_CRUMBLING_STONE);
		stack.setMetadata(BlockLogicCrumbling.setMetadataFromStage(3));
		list.add(stack);
		stack = new ItemStack(STAIRS_CRUMBLING_STONE);
		stack.setMetadata(BlockLogicCrumbling.setMetadataFromStage(3));
		list.add(stack);
		stack = new ItemStack(CRUMBLING_CARVED_BLOCK);
		stack.setMetadata(BlockLogicCrumbling.setMetadataFromStage(3));
		list.add(stack);

		callAddBlock(list, OVERGROWN_BRICKS);
		callAddBlock(list, OVERGROWN_BLOOM_BRICK);
		callAddBlock(list, OVERGROWN);
	}

	@Inject(method = "addPlaceables", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.BEFORE))
	private static void addPlaceables(List<ItemStack> list, CallbackInfo ci){
		callAddBlock(list, PRISON_BAR);
		callAddBlock(list, PRISON_BAR_FENCE);
	}
}
