package jamdoggie.betterbattletowers.entity;

import com.mojang.nbt.tags.CompoundTag;
import jamdoggie.betterbattletowers.BattleTowerMod;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.core.Global.TICKS_PER_SECOND;

public class MobGolem extends MobMonster {
	private WeightedRandomBag<WeightedRandomLootObject> drops = new WeightedRandomBag<>();
	private boolean dormant;

	private int pathToEntity;
	private int growl;

	public MobGolem(World world) {
		super(world);
		this.setSize(1.6F, 3.4F);
		this.scoreValue = 10000;
		this.moveSpeed = 0.35F;
		this.fireImmune = true;
		this.attackStrength = 8;
		this.textureIdentifier = NamespaceID.getPermanent("betterbattletowers", "golem");
		this.drops = GolemManager.getDropsFromVariant(this.getSkinVariant());
		this.mobDrops.add(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 1, 5));
		this.dormant = true;
		// new attributes
		this.pathToEntity = 0;
		this.growl = 0;
	}

	@Override public int getMaxHealth() {
		return 450;
	}
	public static int getMaxHP() {
		return 450;
	}
	@Override public String getTextureReference() {
		String skin = super.getTextureReference();
		return skin + (this.dormant ? "_dormant" : "_awake");
	}
	@Override protected List<WeightedRandomLootObject> getMobDrops() {
		List<WeightedRandomLootObject> loot = new ArrayList<>(this.mobDrops);
		for(int i = 0; i < 2; i++){
			loot.add(this.drops.getRandom(this.random));
		}
		return this.mobDrops;
	}
	@Override public boolean canDespawn() {
		return false;
	}

	@Override
	public void knockBack(Entity entity, int i, double d, double d1) {
		moveSpeed = 0.35F + (float) ((450 - getHealth()) / 1750D);
		if (random.nextInt(5) == 0) {
			// ORIGINAL: motionX, motionZ, motionY
			xd *= 1.5D;
			zd *= 1.5D;
			yd += 0.60000002384185791D;
		}
		pathToEntity = 150;
	}

	protected void lookForPlayer() {
		if (dormant) {
			Player entityplayer = world.getClosestPlayerToEntity(this, 6D);
			if (entityplayer != null && canEntityBeSeen(entityplayer)) {
				dormant = false;
				world.playSoundEffect(null, SoundCategory.CAVE_SOUNDS, x, y, z, "ambient.cave.cave", 0.7F, 1.0F);
				world.playSoundAtEntity(null, this, BattleTowerMod.MOD_ID + ":mob.golem.awaken", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
//				texture = "/entity/golem/0_awake.png";
				pathToEntity = 175;
			}
		} else {
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, bb.expand(6D, 6D, 6D));
			boolean flag = false;
			int i = 0;
			do {
				if (i >= list.size()) {
					break;
				}
				Entity entity = list.get(i);
				if (entity == target) {
					flag = true;
					break;
				}
				i++;
			} while (true);
			if (!flag && target != null || growl == 1) {
				pathToEntity = pathToEntity - 3;
			} else {
				pathToEntity = 175;
			}
		}
	}

	@Override
	public void tick() {
		if (!dormant) {
			if (pathToEntity <= 0 && growl == 0) {
				if (target instanceof Player && world.getClosestPlayerToEntity(this, 24D) == null) {
					target = null;
				} else if (!isHappy()) {
					world.playSoundAtEntity(null, this, BattleTowerMod.MOD_ID + ":mob.golem.special", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
					yd += 0.90000000000000002D;
					growl = 1;
				} else {
					pathToEntity = 150;
				}
			} else if ((pathToEntity <= -30 || onGround) && growl == 1) {
				if (getHealth() <= 425) {
					prevHealth += 25;
				}
				world.createExplosion(this, x, y, z, 4.5F + (float) 3 / 4F);
				pathToEntity = 125;
				growl = 0;
			}
			super.tick();
		}
		lookForPlayer();
	}

	@Override
	public boolean hurt(Entity attacker, int i, DamageType type) {
		return !dormant && super.hurt(attacker, i, type);
	}

	@Override
	public boolean save(@NotNull CompoundTag nbttagcompound) {
		boolean canSave = super.save(nbttagcompound);
		if (!canSave) {
			return false;
		}
		nbttagcompound.putByte("isDormant", (byte) (dormant ? 1 : 0));

		nbttagcompound.putByte("hasGrowled", (byte) growl);
		nbttagcompound.putByte("rageCounter", (byte) pathToEntity);
		return true;
	}

	@Override
	public void load(@NotNull CompoundTag nbttagcompound) {
		super.load(nbttagcompound);
		dormant = nbttagcompound.getByte("isDormant") == 1;

		growl = nbttagcompound.getByte("hasGrowled") & 0xff;
		pathToEntity = nbttagcompound.getByte("rageCounter") & 0xff;
		moveSpeed = 0.35F + (float) ((450 - getHealth()) / 1750D);
		attackStrength = 8;
	}

	protected boolean isHappy() {
		return false; // IDK wtf this code was for, so he's just going to be ANGRY ALL THE TIME!!!!!!
		// My assumption is this was for compatibility with some mod.

		/*int i = -1;
		int j = 0;
		try
		{
			Field field = (net.minecraft.src.EntityLiving.class).getDeclaredField("team");
			i = field.getInt(this);
			Field field1 = (net.minecraft.src.EntityLiving.class).getDeclaredField("team");
			j = field1.getInt(super.playerToAttack);
		}
		catch(Exception exception)
		{
			if(!(exception instanceof SecurityException) && !(exception instanceof NoSuchFieldException))
			{
				if(!(exception instanceof IllegalAccessException));
			}
		}
		return i == j;*/
	}

	@Override
	protected void attackEntity(Entity entity, float f) {
		if (f < 3D && entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY) {
			entity.hurt(this, attackStrength, DamageType.COMBAT);
		}
		if (onGround) {
			double d = entity.x - x;
			double d1 = entity.z - z;
			float f1 = MathHelper.sqrt(d * d + d1 * d1);
			xd = (d / f1) * 0.5D * 0.20000000192092895D + xd * 0.20000000098023224D;
			zd = (d1 / f1) * 0.5D * 0.10000000192092896D + zd * 0.20000000098023224D;
		} else {
			super.attackEntity(entity, f);
		}
	}

	@Override
	public String getLivingSound() {
		if (!dormant) {
			return BattleTowerMod.MOD_ID + ":mob.golem";
		} else {
			return "ambient.cave.cave";
		}
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
		return BattleTowerMod.MOD_ID + ":mob.golem.death";}
}
