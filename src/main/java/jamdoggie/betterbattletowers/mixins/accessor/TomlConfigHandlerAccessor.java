package jamdoggie.betterbattletowers.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Entry;
import turniplabs.halplibe.util.toml.Toml;

import java.util.HashMap;

@Mixin(value = TomlConfigHandler.class, remap = false)
public interface TomlConfigHandlerAccessor {
	@Accessor
	Toml getDefaults();
}
