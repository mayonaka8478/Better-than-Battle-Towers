package jamdoggie.betterbattletowers.block.overgrown;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.util.ParticleHelper;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicFlowerStackable;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockLogicOvergrown extends BlockLogic {
	public BlockLogicOvergrown(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
		if (player.getHeldItem() == null) {
			return super.onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
		}
		ItemStack stack = player.getHeldItem();
		if (stack.getItem() instanceof ItemToolShears) {
			if (world.rand.nextBoolean()) {
				world.dropItem(x, y, z, Blocks.ALGAE.getDefaultStack());
			}
			this.spawnParticles(world, x, y, z, Blocks.ALGAE.id());
			stack.damageItem(1, player);
			if (stack.stackSize <= 0) {
				player.setHeldObject(null);
			}
			if (!world.setBlockWithNotify(x, y, z, Blocks.BRICK_STONE_POLISHED_MOSSY.id())) {
				return super.onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
			}
			return true;
		} else if (stack.getItem() instanceof ItemBlock) {
			ItemBlock<?> itemBlock = (ItemBlock<?>) stack.getItem();
			if (!(itemBlock.getBlock().getLogic() instanceof BlockLogicFlowerStackable)) {
				return super.onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
			}
			if (!world.setBlockWithNotify(x, y, z, BattleTowerBlocks.OVERGROWN_BLOOM_BRICK.id())) {
				return super.onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
			}
			stack.consumeItem(player);
			return true;
		}
		return super.onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
	}

	private void spawnParticles(World world, int x, int y, int z, int id) {
		for (int i = 0; i < 32; i++) {
			double angle = world.rand.nextInt(360);
			double vx = Math.sin(MathHelper.toRadians((float) angle));
			double vy = world.rand.nextDouble() * 0.1;
			double vz = Math.cos(MathHelper.toRadians((float) angle));
			ParticleHelper.spawnParticle(world, "block", x + 0.5F, y + 0.5F, z + 0.5F, vx, vy, vz, id);
		}
	}


}
