package jamdoggie.betterbattletowers.block.crumbling_stone;

import jamdoggie.betterbattletowers.block.BattleTowerTriggerStandOn;
import jamdoggie.betterbattletowers.util.Metadata;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
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

import static jamdoggie.betterbattletowers.worldgen.structures.WorldFeatureTower.BLOCK_AIR;

public class BlockLogicCrumbling extends BlockLogic implements BattleTowerTriggerStandOn {
	public Block<?> dropBlock;
	private final int coolDown;

	public enum BreakingStage{
		HEAVY, LIGHT, MEDIUM, UNDAMAGED;
	}

	public BlockLogicCrumbling(Block<?> block, Block<?> dropBlock, Material material, float coolDown) {
		super(block, material);
		this.dropBlock = dropBlock;
		this.coolDown = (int) Math.floor(Math.max(coolDown * Global.TICKS_PER_SECOND, 1));
	}

	public static int getStageFromMetadata(int metadata){
		return Metadata.getBitBlock(metadata, 4, 5);
	}

	public static int setMetadataFromStage(int value) {
		return Metadata.setBitBlock(0, 4, 5, value);
	}

	public static int setMetadataFromStage(int metadata, int value) {
		return Metadata.setBitBlock(metadata, 4, 5, value);
	}


	public BlockLogicCrumbling setDropBlock (Block<?> dropBlock){
		this.dropBlock = dropBlock;
		return this;
	}

	public Block<?> getDropBlock (){
		return this.dropBlock;
	}

	@Override
	public int tickDelay() {
		return coolDown;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int metadata = world.getBlockMetadata(x, y, z);
		if (Metadata.isSet(metadata, 7)) {
			world.setBlockMetadataWithNotify(x, y, z, Metadata.setBit(metadata, 7, 0));
		}
	}

	@Override
	public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		return getStageFromMetadata(meta) > 0 ? new ItemStack[]{new ItemStack(this.dropBlock)} : null;
	}

	@Override
	public void onEntityStandOn(World world, int x, int y, int z, Entity entity) {
		updateBlock(world, x, y, z, entity, this.block);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		updateBlock(world, x, y, z, entity, this.block);
	}

	public static void updateBlock(World world, int x, int y, int z, Entity entity, Block<?> block) {
		if (!(entity instanceof Player)) {
			return;
		}
		int metadata = world.getBlockMetadata(x, y, z);
		if (Metadata.isSet(metadata, 7)) {
			return;
		}
		int lowerBits = getStageFromMetadata(metadata);
		if (lowerBits == 0) {
			spawnParticle(world, x, y, z, block.id());
			world.setBlockWithNotify(x, y, z, BLOCK_AIR);
			world.playBlockSoundEffect(null, x, y + 0.5F, z, block, EnumBlockSoundEffectType.MINE);
			return;
		}
		world.setBlockMetadataWithNotify(x, y, z, Metadata.setBitBlock(Metadata.setBit(metadata, 7), 4, 5 ,lowerBits - 1));
		world.playBlockSoundEffect(null, x, y + 0.5F, z, block, EnumBlockSoundEffectType.DIG);
		world.scheduleBlockUpdate(x, y, z, block.id(), block.getLogic().tickDelay());
	}

	@Override
	public float blockStrength(World world, int x, int y, int z, Side side, Player player) {
		int metadata = getStageFromMetadata(world.getBlockMetadata(x, y, z));
		float blockHardness = this.block.blockHardness / Math.max(2 - metadata, 1);
		if (!player.canHarvestBlock(this.block)) {
			return 1.0F / blockHardness / 100.0F;
		}
		return player.getCurrentPlayerStrVsBlock(this.block) / blockHardness / 30.0F;
	}

	public static void spawnParticle(World world, int x, int y, int z, int blockID) {
		Random random = world.rand;
		for (int i = 0; i < 16; ++i) {
			double fx = x + (random.nextDouble() * 0.6 - 0.3);
			double fy = y + 0.5;
			double fz = z + (random.nextDouble() * 0.6 - 0.3);

			double dx = fx * 0.3 + (random.nextDouble() - 0.5) * 0.2;
			double dy = fy * 0.3 + (random.nextDouble() - 0.5) * 0.2;
			double dz = fz * 0.3 + (random.nextDouble() - 0.5) * 0.2;

			world.spawnParticle("block", fx, fy, fz, dx, dy, dz, blockID);
		}
	}

	@Override
	public String getLanguageKey(int meta) {
		String lang = super.getLanguageKey(meta);
		if (BlockLogicCrumbling.getStageFromMetadata(meta) != 0) {
			return lang;
		}
		return lang.replace("crumbling", "bridle");
	}
}
