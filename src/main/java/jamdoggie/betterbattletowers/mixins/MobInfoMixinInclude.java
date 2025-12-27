package jamdoggie.betterbattletowers.mixins;

import jamdoggie.betterbattletowers.entity.MobGolem;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;

import static jamdoggie.betterbattletowers.worldgen.util.LootTable.LAPIZ;

@Mixin(value = MobInfoRegistry.class, remap = false)
public abstract class MobInfoMixinInclude {
	static {
//		ItemStack lapiz = new ItemStack(Items.DYE);
//		lapiz.setMetadata(LAPIZ);
//		MobInfoRegistry.register(MobGolem.class, "betterbattletowers.golem.name", "betterbattletowers.golem.desc", MobGolem.MAX_HEALTH, 10000,
//			new MobInfoRegistry.MobDrop[]{
//				new MobInfoRegistry.MobDrop(new ItemStack(Items.DIAMOND), 1.0f, 1, 5),
//				new MobInfoRegistry.MobDrop(new ItemStack(Items.ORE_RAW_IRON), 1.0f, 0, 3),
//				new MobInfoRegistry.MobDrop(new ItemStack(Items.DUST_REDSTONE), 1.0f, 0, 5),
//				new MobInfoRegistry.MobDrop(new ItemStack(Items.OLIVINE), 1.0f, 0, 3),
//				new MobInfoRegistry.MobDrop(lapiz, 1.0f, 0, 5),
//				new MobInfoRegistry.MobDrop(new ItemStack(Items.ORE_RAW_GOLD), 1.0f, 0, 3)
//			});
	}
}
