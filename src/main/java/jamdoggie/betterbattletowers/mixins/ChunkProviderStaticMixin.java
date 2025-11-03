package jamdoggie.betterbattletowers.mixins;

import jamdoggie.betterbattletowers.BetterBattleTowers;
import net.minecraft.client.world.chunk.provider.ChunkProviderStatic;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.provider.IChunkProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = ChunkProviderStatic.class, remap = false)
public abstract class ChunkProviderStaticMixin
{
	@Shadow
	private World worldObj;

	@Inject(method = "populate", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/core/world/generate/chunk/ChunkGenerator;decorate(Lnet/minecraft/core/world/chunk/Chunk;)V",
	shift = At.Shift.AFTER))
	private void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ, CallbackInfo ci)
	{
		Random random = new Random(worldObj.getRandomSeed());
		long l = (random.nextLong() / 2L) * 2L + 1L;
		long l1 = (random.nextLong() / 2L) * 2L + 1L;
		random.setSeed((long)chunkX * l + (long)chunkZ * l1 ^ worldObj.getRandomSeed());

		BetterBattleTowers.instance.GenerateSurface(worldObj, random, chunkX << 4, chunkZ << 4);
	}
}
