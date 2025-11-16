package jamdoggie.betterbattletowers.entity;

import com.mojang.nbt.tags.CompoundTag;
import jamdoggie.betterbattletowers.BattleTowerMod;
import jamdoggie.betterbattletowers.util.MathUtil;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static jamdoggie.betterbattletowers.entity.GolemManager.HELLWORLD;
import static jamdoggie.betterbattletowers.util.MathUtil.posGausssianInt;
import static net.minecraft.core.Global.TICKS_PER_SECOND;

public class MobGolem extends MobPathfinder {
	private static final float SIGHT_RADIUS = 16.0F;
	private static final float DEFAULT_SPEED = 0.35f;
	private static final int ATTACK_TIME = 2 * TICKS_PER_SECOND;
	private final int attackStrength;
	private WeightedRandomBag<WeightedRandomLootObject> drops;
	private boolean dormant;
	private boolean growl;
	private int timer;

	public static final int MAX_HEALTH = 450;

	public MobGolem(World world) {
		super(world);
		this.setSize(1.6F, 3.4F);
		this.scoreValue = 10000;
		this.moveSpeed = DEFAULT_SPEED;
		this.fireImmune = true;
		this.attackStrength = 8;
		this.textureIdentifier = NamespaceID.getPermanent("betterbattletowers", "golem");
		this.drops = GolemManager.getDropsFromVariant(this.getSkinVariant());
		this.mobDrops.add(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 1, 5));
		this.footSize = 2;
		this.dormant = false;
		this.growl = false;
		this.timer = 10 * TICKS_PER_SECOND;
		this.setHealthRaw(200 + posGausssianInt(this.random, 250));
	}

	@Override
	public int getMaxHealth() {
		return 450;
	}

	public boolean isDormant() {
		return dormant;
	}

	@Override
	protected void causeFallDamage(float distance) {
		int ix = MathHelper.floor(this.x);
		int iy = MathHelper.floor(this.y - 0.2 - this.heightOffset);
		int iz = MathHelper.floor(this.z);
		int fallDistanceForParticle = (int)Math.ceil((distance - 3.0F));
		if (fallDistanceForParticle > 0) {
			int blockID = this.world.getBlockId(ix, iy, iz);
			if (blockID > 0) {
				this.world.playBlockSoundEffect(this, this.x, this.y - this.heightOffset, this.z, Blocks.blocksList[blockID], EnumBlockSoundEffectType.ENTITY_LAND);
			}
		}

	}

	@Override
	protected List<WeightedRandomLootObject> getMobDrops() {
		List<WeightedRandomLootObject> loot = new ArrayList<>(this.mobDrops);
		for (int i = 0; i < 2; i++) {
			loot.add(this.drops.getRandom(this.random));
		}
		return this.mobDrops;
	}

	@Override
	public boolean canDespawn() {
		return false;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	private void wakeUp() {
		dormant = false;
		world.playSoundEffect(null, SoundCategory.CAVE_SOUNDS, x, y, z, "ambient.cave.cave", 0.7F, 1.0F);
		world.playSoundAtEntity(null, this, BattleTowerMod.MOD_ID + ":mob.golem.awaken", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		timer = 10 * TICKS_PER_SECOND;
	}

	private void gotoSleep() {
		this.dormant = true;
		this.growl = false;
		this.target = null;
	}

	@Override
	public void knockBack(Entity attacker, int damage, double distX, double distY) {
		float modifier = (this.getMaxHealth() - this.getHealth() * 1.0f) / this.getMaxHealth();
		float speed = MathHelper.lerp(DEFAULT_SPEED, DEFAULT_SPEED * 2, modifier);
		this.moveSpeed = Math.max(DEFAULT_SPEED, speed);
		if (random.nextInt(5) == 0) {
			super.knockBack(attacker, damage, distX, distY);
		}
		if(this.attackTime > 0){
			this.timer = Math.max(timer, 2 * TICKS_PER_SECOND);
		}
	}

	@Override
	protected void roamRandomPath() {
		// we don't want the golem to roam
	}

	@Override
	protected Player findPlayerToAttack() {
		if (dormant) return null;
		assert world != null;
		Player entityplayer = this.world.getClosestPlayerToEntity(this, SIGHT_RADIUS);
		return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
	}

	@Override
	public void onLivingUpdate() {
		if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
			this.gotoSleep();
			super.onLivingUpdate();
			return;
		}
		if (dormant) {
			Player player = this.world.getClosestPlayerToEntity(this, SIGHT_RADIUS / 2.0D);
			if (player != null) {
				this.wakeUp();
			}
		} else {
			Player player = this.world.getClosestPlayerToEntity(this, SIGHT_RADIUS * 4.0D);
			if (player == null) {
				this.gotoSleep();
				super.onLivingUpdate();
				return;
			}
			if (growl && onGround) {
				this.world.createExplosion(this, x, y, z, 4.5F + 3 / 4F);
				this.timer = 10 * TICKS_PER_SECOND;
				this.growl = false;
			}
			if (timer-- <= 0 && !growl && this.target != null && this.onGround) {
				this.world.playSoundAtEntity(null, this, BattleTowerMod.MOD_ID + ":mob.golem.special", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
				this.yd += 0.9D;
				this.growl = true;
				this.onGround = false;
				return;
			}
		}
		super.onLivingUpdate();
	}

	@Override
	protected void attackEntity(@NotNull Entity victim, float distance) {
		if (this.attackTime <= 0 && distance < 3.0F && victim.bb.maxY > this.bb.minY && victim.bb.minY < this.bb.maxY) {
			this.attackTime = ATTACK_TIME;
			victim.hurt(this, attackStrength, this.getSkinVariant() == HELLWORLD ? DamageType.FIRE : DamageType.COMBAT);
		}
	}

	@Override
	public boolean save(@NotNull CompoundTag nbttagcompound) {
		boolean canSave = super.save(nbttagcompound);
		if (!canSave) {
			return false;
		}
		nbttagcompound.putByte("isDormant", (byte) (dormant ? 1 : 0));
		nbttagcompound.putByte("hasGrowled", (byte) (growl ? 1 : 0));
		nbttagcompound.putByte("explosiveAttackTimer", (byte) timer);
		return true;
	}

	@Override
	public void load(@NotNull CompoundTag nbttagcompound) {
		super.load(nbttagcompound);
		dormant = nbttagcompound.getByte("isDormant") == 1;
		growl = nbttagcompound.getByte("hasGrowled") == 1;
		timer = nbttagcompound.getByte("explosiveAttackTimer") & 0xff;
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		float bias = 0.0F;
		Material material = this.world.getBlockMaterial(x, y, z);
		if (material != null && material.isLiquid()) bias -= 2;
		return bias;
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (this.dormant && attacker != null && type != null) return false;
		if (type == DamageType.BLAST) return false;
		return super.hurt(this, damage, type);
	}

	@Override
	public String getLivingSound() {
		return !dormant ? BattleTowerMod.MOD_ID + ":mob.golem" : "ambient.cave.cave";
	}

	@Override
	public int getAmbientSoundInterval() {
		return 40 * TICKS_PER_SECOND;
	}

	@Override
	protected String getHurtSound() {
		return BattleTowerMod.MOD_ID + ":mob.golem.hurt";
	}

	@Override
	protected String getDeathSound() {
		return BattleTowerMod.MOD_ID + ":mob.golem.death";
	}
}
