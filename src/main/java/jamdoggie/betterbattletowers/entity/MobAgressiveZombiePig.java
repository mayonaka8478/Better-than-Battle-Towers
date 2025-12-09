package jamdoggie.betterbattletowers.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobZombiePig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

public class MobAgressiveZombiePig extends MobZombiePig {

	public MobAgressiveZombiePig(World world) {
		super(world);
	}

	@Override
	protected Entity findPlayerToAttack() {
		Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0F);
		return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
	}

	@Override
	public boolean canSpawnHere() {
		int x = MathHelper.floor(this.x);
		int y = MathHelper.floor(this.y);
		int z = MathHelper.floor(this.z);
		int blockLight = this.world.getSavedLightValue(LightLayer.Block, x, y, z);
		if (this.world.getBlockId(x, y, z) != 0 || blockLight > 7) {
			return false;
		}
		return this.world.getDifficulty().canHostileMobsSpawn()
			&& this.world.checkIfAABBIsClear(this.bb)
			&& this.world.getCubes(this, this.bb).isEmpty()
			&& !this.world.getIsAnyLiquid(this.bb);
	}
}
