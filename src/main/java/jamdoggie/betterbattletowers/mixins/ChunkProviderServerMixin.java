package jamdoggie.betterbattletowers.mixins;

import jamdoggie.betterbattletowers.BetterBattleTowers;
import net.minecraft.core.world.chunk.provider.IChunkProvider;
import net.minecraft.server.world.WorldServer;
import net.minecraft.server.world.chunk.provider.ChunkProviderServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = ChunkProviderServer.class, remap = false)
public abstract class ChunkProviderServerMixin
{

	@Shadow
	@Final
	private WorldServer world;

	@Inject(method = "populate", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/core/world/generate/chunk/ChunkGenerator;decorate(Lnet/minecraft/core/world/chunk/Chunk;)V",
	shift = At.Shift.AFTER))
	private void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ, CallbackInfo ci)
	{
		Random random = new Random(world.getRandomSeed());
		long l = (random.nextLong() / 2L) * 2L + 1L;
		long l1 = (random.nextLong() / 2L) * 2L + 1L;
		random.setSeed((long)chunkX * l + (long)chunkZ * l1 ^ world.getRandomSeed());

		BetterBattleTowers.instance.GenerateSurface(world, random, chunkX << 4, chunkZ << 4);
	}
}
