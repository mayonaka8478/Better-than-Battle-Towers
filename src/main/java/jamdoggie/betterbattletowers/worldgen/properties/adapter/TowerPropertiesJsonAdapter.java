package jamdoggie.betterbattletowers.worldgen.properties.adapter;

import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.properties.decoration.TowerProperties;

import java.lang.reflect.Type;

public class TowerPropertiesJsonAdapter implements JsonDeserializer<TowerProperties>, JsonSerializer<TowerProperties> {
	@Override
	public TowerProperties deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return null;
	}

	@Override
	public JsonElement serialize(TowerProperties src, Type typeOfSrc, JsonSerializationContext context) {
		return null;
	}
}
