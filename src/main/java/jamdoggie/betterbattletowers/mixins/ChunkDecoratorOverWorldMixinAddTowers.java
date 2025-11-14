package jamdoggie.betterbattletowers.mixins;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChunkDecoratorOverworld.class, remap = false)
public abstract class ChunkDecoratorOverWorldMixinAddTowers {
	@Shadow @Final private World world;

	@Inject(
		method = "decorate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/world/generate/feature/WorldFeatureLabyrinth;place(Lnet/minecraft/core/world/World;Ljava/util/Random;III)Z",
			shift = At.Shift.AFTER
		)
	)
	private void addBattleTowers(Chunk chunk, CallbackInfo ci){
//		Random random = new Random(0x544f574552L);
//		if(random.nextInt(BattleTowerConfig.getTowerCount()) == 0){
//			int x =  chunk.xPosition * 16 + 8;
//			int z =  chunk.zPosition * 16 + 8;
//			int y  = chunk.getHeightValue(chunk.xPosition, chunk.zPosition);
//			WorldFeatureBattleTower.tower().place(world, random, x, y, z);
//		}
	}
}
