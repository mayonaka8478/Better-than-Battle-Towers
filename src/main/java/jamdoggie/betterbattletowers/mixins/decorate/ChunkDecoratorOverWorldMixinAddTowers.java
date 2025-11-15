package jamdoggie.betterbattletowers.mixins.decorate;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static jamdoggie.betterbattletowers.BattleTowerMod.generateTower;

@Mixin(value = ChunkDecoratorOverworld.class, remap = false)
public abstract class ChunkDecoratorOverWorldMixinAddTowers {
	@Shadow @Final private World world;

	@Inject(method = "decorate", at = @At("TAIL"))
	private void addBattleTowers(Chunk chunk, CallbackInfo ci) {
		generateTower(this.world, chunk);
	}
}
