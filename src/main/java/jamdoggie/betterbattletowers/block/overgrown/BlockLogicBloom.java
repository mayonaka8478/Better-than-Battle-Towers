package jamdoggie.betterbattletowers.block.overgrown;

import jamdoggie.betterbattletowers.util.ParticleHelper;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import net.minecraft.core.world.season.SeasonManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static jamdoggie.betterbattletowers.block.BattleTowerDetail.flowers;

public class BlockLogicBloom extends BlockLogic {
	public BlockLogicBloom(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public boolean onInteracted(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Player player, @Nullable Side side, double xHit, double yHit) {
		if (player.getHeldItem() == null) {
			return super.onInteracted(world, tilePos, player, side, xHit, yHit);
		}
		SeasonManager manager = world.getSeasonManager();
		ItemStack stack = player.getHeldItem();
		if (stack.getItem() instanceof ItemToolShears) {
			if (world.rand.nextBoolean()) {
				world.dropItem(tilePos, Blocks.ALGAE.getDefaultStack());
			}
			if(manager.getCurrentSeason().growFlowers){
				ItemStack flower = flowers.getRandom(world.rand);
				world.dropItem(tilePos, flower);
				this.spawnParticles(world, tilePos, flower.getItem().id);
			}
			this.spawnParticles(world, tilePos, Blocks.ALGAE.id());
			stack.damageItem(1, player);
			if (stack.stackSize <= 0) {
				player.setHeldObject(null);
			}
			if (!world.setBlockDataNotify(tilePos, Blocks.BRICK_STONE_POLISHED_MOSSY.id())) {
				return super.onInteracted(world, tilePos, player, side, xHit, yHit);
			}
			return true;
		}
		return super.onInteracted(world, tilePos, player, side, xHit, yHit);
	}

	private void spawnParticles(World world, TilePosc tilePosc, int id) {
		int x = tilePosc.x();
		int y = tilePosc.y();
		int z = tilePosc.z();
		for (int i = 0; i < 32; i++) {
			double angle = world.rand.nextInt(360);
			double vx = Math.sin(MathHelper.toRadians((float) angle));
			double vy = world.rand.nextDouble() * 0.1;
			double vz = Math.cos(MathHelper.toRadians((float) angle));
			ParticleHelper.spawnParticle(world, "block", x + 0.5F, y + 0.5F, z + 0.5F, vx, vy, vz, id);
		}
	}
}
