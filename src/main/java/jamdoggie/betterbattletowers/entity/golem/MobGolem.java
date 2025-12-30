package jamdoggie.betterbattletowers.entity.golem;

import com.mojang.nbt.tags.CompoundTag;
import jamdoggie.betterbattletowers.BattleTowerMod;
import jamdoggie.betterbattletowers.entity.MobUtil;
import jamdoggie.betterbattletowers.mixins.accessor.EntityVariantsAccessor;
import jamdoggie.betterbattletowers.mixins.accessor.SkinVariantAccessor;
import net.minecraft.client.entity.ClientSkinVariantList;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.SkinVariantList;
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

import static jamdoggie.betterbattletowers.entity.golem.GolemVariants.DEFAULT;
import static jamdoggie.betterbattletowers.util.DamageInstance.inst;
import static jamdoggie.betterbattletowers.util.MathUtil.posGausssianIntBounded;
import static jamdoggie.betterbattletowers.config.LootTable.LAPIZ;
import static net.minecraft.core.Global.TICKS_PER_SECOND;

@SuppressWarnings("java:S2160")
public class MobGolem extends MobPathfinder {
	public static final int DEFAULT_TIME = 10 * TICKS_PER_SECOND;
	private static final float SIGHT_RADIUS = 16.0F;
	private static final float DEFAULT_SPEED = 0.35f;
	private static final int ATTACK_TIME = 2 * TICKS_PER_SECOND;
	private boolean dormant;
	private boolean growl;
	private int timer;
	private int attackStrength;
	private final int pieceDamage;
	private int lootAmount;

	private static final WeightedRandomBag<WeightedRandomLootObject> DROPS = new WeightedRandomBag<>();
	static {
		DROPS.addEntry(new WeightedRandomLootObject(Items.ORE_RAW_IRON.getDefaultStack(), 1, 3), 20);
		DROPS.addEntry(new WeightedRandomLootObject(Items.DUST_REDSTONE.getDefaultStack(), 1, 5), 20);
		DROPS.addEntry(new WeightedRandomLootObject(Items.OLIVINE.getDefaultStack(), 1, 10), 3);
		DROPS.addEntry(new WeightedRandomLootObject(Items.DYE.getDefaultStack(), 1, 5).setRandomMetadata(LAPIZ, LAPIZ), 20);
		DROPS.addEntry(new WeightedRandomLootObject(Items.ORE_RAW_GOLD.getDefaultStack(), 1, 3), 20);
	}

	public static final int MAX_HEALTH = 500;

	public MobGolem(World world) {
		super(world);
		this.setSize(1.6F, 3.4F);
		this.setHealthRaw(200 + posGausssianIntBounded(this.random, 300, 0, 8801));
		this.moveSpeed = DEFAULT_SPEED;
		this.fireImmune = true;
		this.textureIdentifier = NamespaceID.getPermanent("betterbattletowers", "golem");
		this.footSize = 2;
		this.dormant = false;
		this.growl = false;
		this.timer = DEFAULT_TIME;
		this.attackStrength = 8 + (int)Math.floor((this.getHealth() - 200) / 733.416);
		this.pieceDamage = (int)Math.floor(3.0f / 8.0f * this.attackStrength);
		this.scoreValue = 10000 + Math.max(this.getHealth() - this.getMaxHealth(), 0);
		this.lootAmount = 2 + Math.max(this.getHealth() - this.getMaxHealth(), 0) / 50;
		this.mobDrops.add(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 3, this.getHealth() / 100));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(3, DEFAULT, String.class);
	}

	@Override
	public @NotNull String getDefaultEntityTexture() {
		return String.format("/assets/%s/textures/entity/%s/%s/0.png", this.textureIdentifier.namespace(), DEFAULT, this.textureIdentifier.value());
	}

	@Override
	public String getEntityTexture() {
		String basePath = String.format("/assets/%s/textures/entity/%s/%s/", this.textureIdentifier.namespace(), this.textureIdentifier.value(), this.entityData.getString(3));
		return basePath + this.getTextureReference() + ".png";
	}

	@Override
	public String getTextureReference() {
		SkinVariantList variantList = Global.accessor.getSkinVariantList();
		String basePath = String.format("/assets/%s/textures/entity/%s/%s/", this.textureIdentifier.namespace(), this.textureIdentifier.value(), this.entityData.getString(3));
		return variantList.getSkinReference(basePath + "variants.json", "0", this.getSkinVariant());
	}

	public final void setVariant(String index) {
		GolemVariants.addEntry(index);
		this.entityData.set(3, index);
	}

	@Override
	public boolean cycleVariant() {
		SkinVariantList variantList = Global.accessor.getSkinVariantList();
		String basePath = String.format("/assets/%s/textures/entity/%s/%s/", this.textureIdentifier.namespace(), this.textureIdentifier.value(), this.entityData.getString(3));
		ClientSkinVariantList.EntityVariants entityVariants = ((SkinVariantAccessor)variantList).invokeGetEntityVariants(basePath + "variants.json");
		int skinVar = this.getSkinVariant();
		if(((EntityVariantsAccessor)entityVariants).getIndexedSkins().length - 1 == this.getSkinVariant()) {
			String nextPath = GolemVariants.getNextValue(this.entityData.getString(3));
			this.setVariant(nextPath);
			skinVar = 0;
			basePath = String.format("/assets/%s/textures/entity/%s/%s/", this.textureIdentifier.namespace(), this.textureIdentifier.value(), nextPath);
		}
		this.setSkinVariant(variantList.nextSkinVariant(basePath + "variants.json", skinVar));
		return skinVar != this.getSkinVariant();
	}


	@Override
	public int getMaxHealth() {
		return 500;
	}

	public MobGolem setDormant(boolean value){
		this.dormant = value;
		return this;
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
		if (fallDistanceForParticle <= 0) {
			return;
		}
		int blockID = this.world.getBlockId(ix, iy, iz);
		if (blockID > 0) {
			this.world.playBlockSoundEffect(this, this.x, this.y - this.heightOffset, this.z, Blocks.blocksList[blockID], EnumBlockSoundEffectType.ENTITY_LAND);
		}
	}

	@Override
	protected List<WeightedRandomLootObject> getMobDrops() {
		List<WeightedRandomLootObject> loot = new ArrayList<>(this.mobDrops);
		for (int i = 0; i < this.lootAmount; i++) {
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
		if (dormant) {
			return null;
		}
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
			this.actWhileDormant();
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
			this.world.createExplosion(this, x, y, z, 4.5F + pieceDamage / 4F);
			this.timer = DEFAULT_TIME;
			this.growl = false;
		}
		if (timer <= -1 && !growl && this.onGround && world.getClosestPlayerToEntity(this, SIGHT_RADIUS / 2) != null) {
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

	private void actWhileDormant() {
		Player player = this.world.getClosestPlayerToEntity(this, SIGHT_RADIUS / 2.0D);
		if (player != null) {
			this.wakeUp();
		}
		super.onLivingUpdate();
	}

	@Override
	protected void attackEntity(@NotNull Entity victim, float distance) {
		if (this.attackTime > 0 || (distance >= 3.0F) || (victim.bb.maxY <= this.bb.minY) || victim.bb.minY >= this.bb.maxY) {
			return;
		}
		this.attackTime = ATTACK_TIME;
		MobUtil.multiHit(
			this, victim,
			inst(DamageType.GENERIC, pieceDamage, 1.0),
			inst(DamageType.COMBAT, this.attackStrength - pieceDamage)
		);
	}

	@Override
	public boolean save(@NotNull CompoundTag nbttagcompound) {
		boolean canSave = super.save(nbttagcompound);
		if (!canSave) {
			return false;
		}
		nbttagcompound.putByte("isDormant", (byte) (this.dormant ? 1 : 0));
		nbttagcompound.putByte("hasGrowled", (byte) (this.growl ? 1 : 0));
		nbttagcompound.putByte("attackStrength", (byte) this.attackStrength);
		nbttagcompound.putByte("score", (byte) this.scoreValue);
		nbttagcompound.putByte("lootAmount", (byte) this.lootAmount);
		nbttagcompound.putByte("timer", (byte) this.timer);
		nbttagcompound.putString("type", (String) this.entityData.getString(3));
		return true;
	}

	@Override
	public void load(@NotNull CompoundTag nbttagcompound) {
		super.load(nbttagcompound);
		this.dormant = nbttagcompound.getByte("isDormant") == 1;
		this.growl = nbttagcompound.getByte("hasGrowled") == 1;
		this.timer = nbttagcompound.getByte("explosiveAttackTimer") & 0xff;
		this.attackStrength = nbttagcompound.getByte("attackStrength");
		this.scoreValue = nbttagcompound.getByte("score");
		this.lootAmount = nbttagcompound.getByte("lootAmount");
		this.timer = nbttagcompound.getByte("timer");
		this.entityData.set(3, nbttagcompound.getString("type"));
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
		if(attacker == null && type == null && damage == 100){
			return MobUtil.killMob(this);
		}
		if(this.dormant || attacker instanceof MobGolem)return false;
		float modifier = (this.getMaxHealth() - this.getHealth() * 1.0f) / this.getMaxHealth();
		float speed = MathHelper.lerp(DEFAULT_SPEED, DEFAULT_SPEED * 2, modifier);
		this.moveSpeed = Math.max(DEFAULT_SPEED, speed);
		boolean hurt = super.hurt(attacker, damage, type);
		if (!hurt || this.passenger == attacker || this.vehicle == attacker || attacker == this) {
			return false;
		}
		this.target = attacker;
		return true;
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
