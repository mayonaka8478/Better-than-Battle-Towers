package jamdoggie.betterbattletowers.block.tainted;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceThin;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

import java.util.ArrayList;

public class BlockLogicTaintedFence extends BlockLogicFenceThin {
	public BlockLogicTaintedFence(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public void getCollidingBoundingBoxes(World world, int x, int y, int z, AABB aabb, ArrayList<AABB> aabbList) {
		boolean connectXPos = this.canConnectTo(world, x + 1, y, z);
		boolean connectXNeg = this.canConnectTo(world, x - 1, y, z);
		boolean connectZPos = this.canConnectTo(world, x, y, z + 1);
		boolean connectZNeg = this.canConnectTo(world, x, y, z - 1);
		if (connectXPos) {
			this.addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(0.375F, 0.0F, 0.375F, 1.0F, 0.85F, 0.625F).move(x, y, z), aabbList);
		}
		if (connectXNeg) {
			this.addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(0.0F, 0.0F, 0.375F, 0.625F, 0.85F, 0.625F).move(x, y, z), aabbList);
		}
		if (connectZPos) {
			this.addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(0.375F, 0.0F, 0.375F, 0.625F, 0.85F, 1.0F).move(x, y, z), aabbList);
		}
		if (connectZNeg) {
			this.addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(0.375F, 0.0F, 0.0F, 0.625F, 0.85F, 0.625F).move(x, y, z), aabbList);
		}
		if (!connectXPos && !connectXNeg && !connectZPos && !connectZNeg) {
			this.addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(0.375F, 0.0F, 0.375F, 0.625F, 0.85F, 0.625F).move(x, y, z), aabbList);
		}
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean canConnectTo(WorldSource world, int x, int y, int z) {
		Block<?> b = world.getBlock(x, y, z);
		return BlockTags.CHAINLINK_FENCES_CONNECT.appliesTo(b) || b != null && (b.getMaterial().isStone() || b.getMaterial().isMetal());
	}

	@Override
	public boolean renderAsNormalBlockOnCondition(WorldSource world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean blocksLight() {
		return false;
	}

}
