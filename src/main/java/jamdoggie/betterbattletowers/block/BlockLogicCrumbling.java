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
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static jamdoggie.betterbattletowers.worldgen.WorldFeatureTower.BLOCK_AIR;

public class BlockLogicCrumbling extends BlockLogic implements BattleTowerTriggerStandOn {
	public static final int UPPER_BIT_MASK = 0b1000_0000;
	public static final int LOWER_BIT_MASK = 0b0000_0011;
	private static final float BASE_VALUE_BLAST = 15.0f;
	private static final float BASE_VALUE_HARDNESS = 2000.0f;
	private final int coolDown;

	public BlockLogicCrumbling(Block<?> block, Material material, float coolDown) {
		super(block, material);
		this.coolDown = (int)Math.floor(Math.max(coolDown * Global.TICKS_PER_SECOND, 1));
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
		this.block.blastResistance = BASE_VALUE_BLAST / (float)Math.pow(4, (LOWER_BIT_MASK - lowerBits));
		this.block.blockHardness = BASE_VALUE_HARDNESS / (float)Math.pow(4, (LOWER_BIT_MASK - lowerBits));
		world.playBlockSoundEffect(null, x, y + 0.5F, z, Blocks.PERMAFROST, EnumBlockSoundEffectType.DIG);
		world.scheduleBlockUpdate(x, y, z, this.id(), coolDown);
	}


	@Override
	public void onBlockPlacedByWorld(World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		this.block.blastResistance = BASE_VALUE_BLAST / (float)Math.pow(4, (LOWER_BIT_MASK - (metadata & LOWER_BIT_MASK)));
		this.block.blockHardness = BASE_VALUE_HARDNESS / (float)Math.pow(4, (LOWER_BIT_MASK - (metadata & LOWER_BIT_MASK)));
		world.setBlockMetadata(x, y, z, this.getRandomMetadata(world));
	}

	private int getRandomMetadata(World world){
		int rand = world.rand.nextInt(10);
		if(rand >= 5) return 2;
		if(rand >= 2) return 1;
		return 0;
	}

	private void spawnParticle(World world, int x, int y, int z) {
		Random random = world.rand;
		for (int i = 0; i < 16; ++i) {
			Direction face = Direction.values()[random.nextInt(6)];

			double faceX = x + (random.nextDouble() * 0.6 - 0.3);
			double faceY = y + 0.5 + (random.nextDouble() * 0.6 - 0.3);
			double faceZ = z + (random.nextDouble() * 0.6 - 0.3);

			double offX = face.getOffsetX() * (random.nextDouble() * 0.3);
			double offY = face.getOffsetY() * (random.nextDouble() * 0.3);
			double offZ = face.getOffsetZ() * (random.nextDouble() * 0.3);

			double spawnX = faceX + offX;
			double spawnY = faceY + offY;
			double spawnZ = faceZ + offZ;

			double vx = offX * 0.3 + (random.nextDouble() - 0.5) * 0.2;
			double vy = offY * 0.3 + (random.nextDouble() - 0.5) * 0.2;
			double vz = offZ * 0.3 + (random.nextDouble() - 0.5) * 0.2;

			world.spawnParticle("block", spawnX, spawnY, spawnZ, vx, vy, vz, this.id());
		}
	}

}
