package jamdoggie.betterbattletowers.mixins;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import jamdoggie.betterbattletowers.block.BattleTowerTriggerStandOn;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixinBlockStandOn{

	@Shadow
	public int id;

	@Definition(id = "muteStepSounds", field="Lnet/minecraft/core/entity/Entity;muteStepSounds:Z")
	@Expression("?.muteStepSounds")
	@ModifyExpressionValue(method = "move", at = @At("MIXINEXTRAS:EXPRESSION"))
	public boolean insureAlwaysTriggerEvent(
		boolean original,
		@Local(name ="blockWalkedOn", ordinal = 0) Block<?> blockWalkedOn,
		@Local(name = "blockX", ordinal = 0) int blockX,
		@Local(name = "blockY", ordinal = 1) int blockY,
		@Local(name = "blockZ", ordinal = 2) int blockZ
	){
		if(!original && blockWalkedOn != null && blockWalkedOn.getLogic() instanceof BattleTowerTriggerStandOn){
			Entity asThis = (Entity) (Object) this;
			blockWalkedOn.onEntityWalking(asThis.world, blockX, blockY, blockZ, asThis);
		}
		return original;
	}

	@Definition(id = "walkedSteps", local = @Local(type = int.class, ordinal = 4))
	@Expression("walkedSteps > 0")
	@ModifyExpressionValue(method = "move", at = @At("MIXINEXTRAS:EXPRESSION"))
	public boolean onEntityStand(
		boolean original,
		@Local(name ="blockWalkedOn", ordinal = 0) Block<?> blockWalkedOn,
		@Local(name = "blockX", ordinal = 0) int blockX,
		@Local(name = "blockY", ordinal = 1) int blockY,
		@Local(name = "blockZ", ordinal = 2) int blockZ
	){
		if(!original && blockWalkedOn != null && blockWalkedOn.getLogic() instanceof BattleTowerTriggerStandOn){
			Entity asThis = (Entity) (Object) this;
			BattleTowerTriggerStandOn triggeredBlock = (BattleTowerTriggerStandOn) blockWalkedOn.getLogic();
			triggeredBlock.onEntityStandOn(asThis.world, blockX, blockY, blockZ, asThis);
			return false;
		}
		return original;
	}
}
