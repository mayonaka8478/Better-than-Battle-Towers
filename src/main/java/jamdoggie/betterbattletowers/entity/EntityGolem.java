package jamdoggie.betterbattletowers.entity;

import com.mojang.nbt.tags.CompoundTag;
import jamdoggie.betterbattletowers.BetterBattleTowers;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityGolem extends MobMonster {
	private String texture;

	public EntityGolem(World world, int i) {
		super(world);
		this.scoreValue = 10000;
		texture = "/entity/golem/golemdormant.png";
		moveSpeed = 0.35F;
		attackStrength = 8;
		this.setHealthRaw(150 + 15 * i);
		setSize(1.6F, 3.4F);
		xRot = 0.0F;
		dormant = 1;
		pathToEntity = 0;
		growl = 0;
		fireImmune = true;
		drops = 2 + 2 * i;
		moveTo(x, y, z, 0.0F, 0.0F);
	}

	public EntityGolem(World world) {
		super(world);
		this.scoreValue = 10000;
		texture = "/entity/golem/golem.png";
		moveSpeed = 0.35F;
		attackStrength = 8;
		this.setHealthRaw(300);
		setSize(1.6F, 3.4F);
		xRot = 0.0F;
		dormant = 0;
		pathToEntity = 0;
		growl = 0;
		fireImmune = true;
		drops = 1;
		moveTo(x, y, z, 0.0F, 0.0F);
	}

	@Override
	public int getMaxHealth() {
		return 450;
	}

	@Override
	public String getDefaultEntityTexture() {
		return getEntityTexture();
	}

	@Override
	public String getEntityTexture() {
		return "/assets/" + BetterBattleTowers.MOD_ID + "/textures" + texture;
	}

	@Override
	public void remove() {
		if (getHealth() <= 0 || world.getDifficulty().id() == 0) {
			super.remove();
		}
	}

	@Override
	public void onDeath(Entity entity) {
		if (scoreValue > 0 && entity != null) {
			entity.awardKillScore(this, scoreValue);
		}

		if (!world.isClientSide) {
			int i = drops - random.nextInt(2);
			for (int j = 0; j < i; j++) {
				dropItem(Items.DIAMOND.id, 1);
			}

			i = random.nextInt(4) + 9;
			for (int k = 0; k < i; k++) {
				dropItem(Blocks.SLAB_STONE_POLISHED.id(), 1);
			}

		}

		world.sendTrackedEntityStatusUpdatePacket(this, (byte) 3);
	}

	@Override
	public void knockBack(Entity entity, int i, double d, double d1) {
		moveSpeed = 0.35F + (float) ((double) (450 - getHealth()) / 1750D);
		if (random.nextInt(5) == 0) {
			// ORIGINAL: motionX, motionZ, motionY
			xd *= 1.5D;
			zd *= 1.5D;
			yd += 0.60000002384185791D;
		}
		pathToEntity = 150;
	}

	protected void lookForPlayer() {
		if (dormant == 1) {
			Player entityplayer = world.getClosestPlayerToEntity(this, 6D);
			if (entityplayer != null && canEntityBeSeen(entityplayer)) {
				dormant = 0;
				world.playSoundEffect(null, SoundCategory.CAVE_SOUNDS, x, y, z, "ambient.cave.cave", 0.7F, 1.0F);
				world.playSoundAtEntity(null, this, BetterBattleTowers.MOD_ID + ":mob.golem.awaken", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
				texture = "/entity/golem/golem.png";
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
				pathToEntity--;
			} else {
				pathToEntity = 175;
			}
		}
	}

	@Override
	public void tick() {
		if (dormant == 0) {
			if (pathToEntity <= 0 && growl == 0) {
				if (target instanceof Player && world.getClosestPlayerToEntity(this, 24D) == null) {
					target = null;
				} else if (!isHappy()) {
					world.playSoundAtEntity(null, this, BetterBattleTowers.MOD_ID + ":mob.golem.special", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
					yd += 0.90000000000000002D;
					growl = 1;
				} else {
					pathToEntity = 150;
				}
			} else if ((pathToEntity <= -30 || onGround) && growl == 1) {
				if (getHealth() <= 425) {
					prevHealth += 25;
				}
				world.createExplosion(this, x, y, z, 4.5F + (float) (drops - 2) / 4F);
				pathToEntity = 125;
				growl = 0;
			}
			super.tick();
		}
		lookForPlayer();
	}

	@Override
	public boolean hurt(Entity attacker, int i, DamageType type) {
		return dormant != 1 && super.hurt(attacker, i, type);
	}

	@Override
	public boolean save(CompoundTag nbttagcompound) {
		boolean canSave = super.save(nbttagcompound);

		if (canSave) {
			nbttagcompound.putByte("isDormant", (byte) dormant);
			nbttagcompound.putByte("hasGrowled", (byte) growl);
			nbttagcompound.putByte("rageCounter", (byte) pathToEntity);
			nbttagcompound.putByte("Drops", (byte) drops);
		}

		return canSave;
	}

	@Override
	public void load(CompoundTag nbttagcompound) {
		super.load(nbttagcompound);
		dormant = nbttagcompound.getByte("isDormant") & 0xff;
		growl = nbttagcompound.getByte("hasGrowled") & 0xff;
		pathToEntity = nbttagcompound.getByte("rageCounter") & 0xff;
		drops = nbttagcompound.getByte("Drops") & 0xff;
		moveSpeed = 0.35F + (float) ((double) (450 - getHealth()) / 1750D);
		if (dormant == 1) {
			texture = "/entity/golem/golemdormant.png";
		} else {
			texture = "/entity/golem/golem.png";
		}
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
		if ((double) f < 3D && entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY) {
			entity.hurt(this, attackStrength, DamageType.COMBAT);
		}
		if (onGround) {
			double d = entity.x - x;
			double d1 = entity.z - z;
			float f1 = MathHelper.sqrt(d * d + d1 * d1);
			xd = (d / (double) f1) * 0.5D * 0.20000000192092895D + xd * 0.20000000098023224D;
			zd = (d1 / (double) f1) * 0.5D * 0.10000000192092896D + zd * 0.20000000098023224D;
		} else {
			super.attackEntity(entity, f);
		}
	}

	@Override
	public String getLivingSound() {
		if (dormant == 0) {
			return BetterBattleTowers.MOD_ID + ":mob.golem";
		} else {
			return null;
		}
	}

	@Override
	protected String getHurtSound() {
		return BetterBattleTowers.MOD_ID + ":mob.golem.hurt";
	}

	@Override
	protected String getDeathSound() {
		return BetterBattleTowers.MOD_ID + ":mob.golem.death";
	}

	@Override
	protected void dropDeathItems() {
		this.dropItem(new ItemStack(Items.BRICK_CLAY, 1, 0), 0.0f);
	}

	private int dormant;
	private int pathToEntity;
	private int growl;
	private int drops;
}
