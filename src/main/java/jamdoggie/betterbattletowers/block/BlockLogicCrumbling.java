package jamdoggie.betterbattletowers.block;

import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.WorldFeatureTower.BLOCK_AIR;

public class BlockLogicCrumbling extends BlockLogic implements BattleTowerTriggerStandOn {
	public static final int UPPER_BIT_MASK = 0b1000_0000;
	public static final int LOWER_BIT_MASK = 0b0000_0011;
	private final int coolDown;

	public BlockLogicCrumbling(Block<?> block, Material material, float coolDown) {
		super(block, material);
		this.coolDown = (int) Math.floor(Math.max(coolDown * Global.TICKS_PER_SECOND, 1));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata & UPPER_BIT_MASK) == UPPER_BIT_MASK) {
			world.setBlockMetadataWithNotify(x, y, z, (metadata & LOWER_BIT_MASK));
		}
	}

	@Override
	public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		return meta > 0 ? new ItemStack[]{new ItemStack(Blocks.STONE_POLISHED)} : null;
	}

	@Override
	public void onEntityStandOn(World world, int x, int y, int z, Entity entity) {
		this.updateBlock(world, x, y, z, entity);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		this.updateBlock(world, x, y, z, entity);
	}

	private void updateBlock(World world, int x, int y, int z, Entity entity) {
		if (!(entity instanceof Player)) {
			return;
		}
		int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata & UPPER_BIT_MASK) == UPPER_BIT_MASK) {
			return;
		}
		int lowerBits = metadata & LOWER_BIT_MASK;
		if (lowerBits == 0) {
			this.spawnParticle(world, x, y, z);
			world.setBlockWithNotify(x, y, z, BLOCK_AIR);
			world.playBlockSoundEffect(null, x, y + 0.5F, z, Blocks.PERMAFROST, EnumBlockSoundEffectType.MINE);
			return;
		}
		world.setBlockMetadataWithNotify(x, y, z, UPPER_BIT_MASK + (lowerBits - 1));
		world.playBlockSoundEffect(null, x, y + 0.5F, z, Blocks.PERMAFROST, EnumBlockSoundEffectType.DIG);
		world.scheduleBlockUpdate(x, y, z, this.id(), coolDown);
	}

	@Override
	public float blockStrength(World world, int x, int y, int z, Side side, Player player) {
		int metadata = world.getBlockMetadata(x, y, z) & LOWER_BIT_MASK;
		float blockHardness = this.block.blockHardness / Math.max(2 - (metadata & LOWER_BIT_MASK), 1);
		if (!player.canHarvestBlock(this.block)) {
			return 1.0F / blockHardness / 100.0F;
		}
		return player.getCurrentPlayerStrVsBlock(this.block) / blockHardness / 30.0F;
	}

	private void spawnParticle(World world, int x, int y, int z) {
		Random random = world.rand;
		for (int i = 0; i < 16; ++i) {
			double fx = x + (random.nextDouble() * 0.6 - 0.3);
			double fy = y + 0.5;
			double fz = z + (random.nextDouble() * 0.6 - 0.3);

			double dx = fx * 0.3 + (random.nextDouble() - 0.5) * 0.2;
			double dy = fy * 0.3 + (random.nextDouble() - 0.5) * 0.2;
			double dz = fz * 0.3 + (random.nextDouble() - 0.5) * 0.2;

			world.spawnParticle("block", fx, fy, fz, dx, dy, dz, this.id());
		}
	}

}
