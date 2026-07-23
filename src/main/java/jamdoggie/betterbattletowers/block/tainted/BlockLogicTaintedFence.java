package jamdoggie.betterbattletowers.block.tainted;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceThin;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;

import java.util.List;

public class BlockLogicTaintedFence extends BlockLogicFenceThin {
	public BlockLogicTaintedFence(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public void getCollisionAABBs(@NotNull World world, @NotNull TilePosc t, @NotNull AABBdc aabb, @NotNull List<@NotNull AABBdc> aabbList) {
		boolean connectXPos = this.canConnectTo(world, t, Side.EAST);
		boolean connectXNeg = this.canConnectTo(world, t, Side.WEST);
		boolean connectZPos = this.canConnectTo(world, t, Side.SOUTH);
		boolean connectZNeg = this.canConnectTo(world, t, Side.NORTH);

		if (connectXPos) {
			this.addIntersectingBoundingBox(aabb, new AABBd(0.375F, 0.0F, 0.375F, 1.0F, 0.85F, 0.625F).translate(t.x(), t.y(), t.z()), aabbList);
		}
		if (connectXNeg) {
			this.addIntersectingBoundingBox(aabb, new AABBd(0.0F, 0.0F, 0.375F, 0.625F, 0.85F, 0.625F).translate(t.x(), t.y(), t.z()), aabbList);
		}
		if (connectZPos) {
			this.addIntersectingBoundingBox(aabb, new AABBd(0.375F, 0.0F, 0.375F, 0.625F, 0.85F, 1.0F).translate(t.x(), t.y(), t.z()), aabbList);
		}
		if (connectZNeg) {
			this.addIntersectingBoundingBox(aabb, new AABBd(0.375F, 0.0F, 0.0F, 0.625F, 0.85F, 0.625F).translate(t.x(), t.y(), t.z()), aabbList);
		}
		if (!connectXPos && !connectXNeg && !connectZPos && !connectZNeg) {
			this.addIntersectingBoundingBox(aabb, new AABBd(0.375F, 0.0F, 0.375F, 0.625F, 0.85F, 0.625F).translate(t.x(), t.y(), t.z()), aabbList);
		}
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean canConnectTo(@NotNull WorldSource worldSource, @NotNull TilePosc tilePosc, @NotNull Side side) {
		TilePos queryPos = new TilePos();
		Block<?> b = worldSource.getBlockType(tilePosc.add(side.direction(), queryPos));
		return BlockTags.CHAINLINK_FENCES_CONNECT.appliesTo(b) || b.getMaterial().isStone() || b.getMaterial().isMetal();
	}

	@Override
	public boolean renderAsNormalBlockOnCondition(@NotNull WorldSource source, @NotNull TilePosc tilePos) {
		return false;
	}

	@Override
	public boolean blocksLight() {
		return true;
	}

}
