package jamdoggie.betterbattletowers.entity;

import com.mojang.nbt.tags.CompoundTag;
import jamdoggie.betterbattletowers.BattleTowerMod;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static jamdoggie.betterbattletowers.damage.DamageInstance.inst;
import static jamdoggie.betterbattletowers.util.MathUtil.posGausssianInt;
import static jamdoggie.betterbattletowers.util.MathUtil.posGausssianIntBounded;
import static jamdoggie.betterbattletowers.worldgen.util.LootTable.LAPIZ;
import static net.minecraft.core.Global.TICKS_PER_SECOND;

@SuppressWarnings("java:S2160")
public class MobGolem extends MobPathfinder {
	public static final int DEFAULT_TIME = 10 * TICKS_PER_SECOND;
	private static final float SIGHT_RADIUS = 16.0F;
	private static final float DEFAULT_SPEED = 0.35f;
	private static final int ATTACK_TIME = 2 * TICKS_PER_SECOND;
	private final int attackStrength;
	private boolean dormant;
	private boolean growl;
	private int timer;

	private static final WeightedRandomBag<WeightedRandomLootObject> DROPS = new WeightedRandomBag<>();

	public static final int PIERCE_DAMAGE = 3;

	static {
		DROPS.addEntry(new WeightedRandomLootObject(Items.ORE_RAW_IRON.getDefaultStack(), 1, PIERCE_DAMAGE), 20);
		DROPS.addEntry(new WeightedRandomLootObject(Items.DUST_REDSTONE.getDefaultStack(), 1, 5), 20);
		DROPS.addEntry(new WeightedRandomLootObject(Items.OLIVINE.getDefaultStack(), 1, 10), PIERCE_DAMAGE);
		DROPS.addEntry(new WeightedRandomLootObject(Items.DYE.getDefaultStack(), 1, 5).setRandomMetadata(LAPIZ, LAPIZ), 20);
		DROPS.addEntry(new WeightedRandomLootObject(Items.ORE_RAW_GOLD.getDefaultStack(), 1, PIERCE_DAMAGE), 20);
	}

	public static final int MAX_HEALTH = 450;

	public MobGolem(World world) {
		super(world);
		this.setSize(1.6F, 3.4F);
		this.scoreValue = 10000;
		this.moveSpeed = DEFAULT_SPEED;
		this.fireImmune = true;
		this.attackStrength = 8;
		this.textureIdentifier = NamespaceID.getPermanent("betterbattletowers", "golem");
		this.footSize = 2;
		this.dormant = false;
		this.growl = false;
		this.timer = DEFAULT_TIME;
		this.setHealthRaw(200 + posGausssianIntBounded(this.random, 300, 0, 8801));
		this.mobDrops.add(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 3, this.getHealth() / 100));
		this.isElderly();
	}

	private void isElderly() {
		if(this.getHealth() > 450){
			this.scoreValue += 2500;
		}
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
			loot.add(DROPS.getRandom(this.random));
		}
		loot.addAll(this.mobDrops);
		return loot;
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
		world.playSoundAtEntity(null, this, BattleTowerMod.MOD_ID + ":mob.golem.awaken", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		timer = DEFAULT_TIME;
	}

	private void gotoSleep() {
		this.dormant = true;
		this.growl = false;
		this.target = null;
	}

	@Override
	public void knockBack(Entity attacker, int damage, double distX, double distY) {
		if (random.nextInt(5) == 0) {
			super.knockBack(attacker, damage, distX, distY);
		}
		resetTimer();
	}

	@Override
	public void fling(double xd, double yd, double zd, float pushTime) {
		resetTimer();
	}

	private void resetTimer() {
		if(this.attackTime > - 2 * TICKS_PER_SECOND ){
			this.timer = Math.max(timer, 4 * TICKS_PER_SECOND);
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
			this.actwhileDormant();
			return;
		}
		this.actWhileAwake();
	}

	private void actWhileAwake() {
		Player player = this.world.getClosestPlayerToEntity(this, SIGHT_RADIUS * 4.0D);
		if (player == null) {
			this.gotoSleep();
			return;
		}
		if (growl && onGround) {
			this.world.createExplosion(this, x, y, z, 4.5F + PIERCE_DAMAGE / 4F);
			this.timer = DEFAULT_TIME;
			this.growl = false;
		}
		if (timer <= 0 && !growl && this.onGround && world.getClosestPlayerToEntity(this, SIGHT_RADIUS / 2) != null) {
			this.world.playSoundAtEntity(null, this, BattleTowerMod.MOD_ID + ":mob.golem.special", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
			this.yd += 0.9D;
			this.growl = true;
			this.onGround = false;
			return;
		}
		if(this.target != null){
			timer--;
		}
		super.onLivingUpdate();
	}

	private void actwhileDormant() {
		Player player = this.world.getClosestPlayerToEntity(this, SIGHT_RADIUS / 2.0D);
		if (player != null) {
			this.wakeUp();
		}
		super.onLivingUpdate();
	}

	@Override
	protected void attackEntity(@NotNull Entity victim, float distance) {
		if (this.attackTime <= 0 && distance < 3.0F && victim.bb.maxY > this.bb.minY && victim.bb.minY < this.bb.maxY) {
			this.attackTime = ATTACK_TIME;
			MobUtil.multiHit(
				this, victim,
				inst(DamageType.GENERIC, PIERCE_DAMAGE, 1.0),
				inst(DamageType.COMBAT, this.attackStrength - PIERCE_DAMAGE)
			);
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
		if(this.dormant || (attacker != null && type != null && damage == 100) || attacker instanceof MobGolem) return false;
		float modifier = (this.getMaxHealth() - this.getHealth() * 1.0f) / this.getMaxHealth();
		float speed = MathHelper.lerp(DEFAULT_SPEED, DEFAULT_SPEED * 2, modifier);
		this.moveSpeed = Math.max(DEFAULT_SPEED, speed);
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

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public float getHeadHeight() {
		return this.bbHeight * 1.08F;
	}
}
