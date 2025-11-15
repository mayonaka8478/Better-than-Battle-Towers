package jamdoggie.betterbattletowers.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import turniplabs.halplibe.util.toml.Entry;
import turniplabs.halplibe.util.toml.Toml;

import java.util.HashMap;

@Mixin(value = Toml.class, remap = false)
public interface TomlAccessor {
	@Accessor("categories")
	HashMap<String, Toml> getCategories();

	@Accessor("entries")
	HashMap<String, Entry<?>> getEntries();
}
