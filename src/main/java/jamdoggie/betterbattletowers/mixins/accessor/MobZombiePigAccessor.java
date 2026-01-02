package jamdoggie.betterbattletowers.mixins.accessor;

import net.minecraft.core.entity.monster.MobZombiePig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MobZombiePig.class, remap = false)
public interface MobZombiePigAccessor {

	@Accessor
	int getRandomSoundDelay();

	@Accessor
	void setRandomSoundDelay(int delay);
}
