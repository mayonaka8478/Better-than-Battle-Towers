package jamdoggie.betterbattletowers.entity;

import jamdoggie.betterbattletowers.mixins.accessor.MobZombiePigAccessor;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobZombiePig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;

public class MobAgressiveZombiePig extends MobZombiePig {

	public MobAgressiveZombiePig(World world) {
		super(world);
		((MobZombiePigAccessor)this).setRandomSoundDelay(this.random.nextInt(40));
	}

	@Override
	protected Entity findPlayerToAttack() {
		Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0F);
		return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().hasHostileMobs() ? entityplayer : null;
	}

	@Override
	public boolean canSpawnHere() {
		int x = MathHelper.floor(this.x);
		int y = MathHelper.floor(this.y);
		int z = MathHelper.floor(this.z);
		int blockLight = this.world.getSavedLightValue(LightLayer.Block, new TilePos(x, y, z));
		if (this.world.getBlockType(new TilePos(x, y, z)).id() != 0 || blockLight > 7) {
			return false;
		}
		return this.world.getDifficulty().canHostileMobsSpawn()
			&& this.world.checkIfAABBIsClear(this.bb)
			&& this.world.getCubes(this, this.bb).isEmpty()
			&& !this.world.getIsAnyLiquid(this.bb);
	}
}
