package jamdoggie.betterbattletowers.worldgen.properties.adapter;

import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.properties.decoration.TowerProperty;

import java.lang.reflect.Type;

public class TowerPropertyJsonAdapter implements JsonDeserializer<TowerProperty>, JsonSerializer<TowerProperty> {
	@Override
	public TowerProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return null;
	}

	@Override
	public JsonElement serialize(TowerProperty src, Type typeOfSrc, JsonSerializationContext context) {
		return null;
	}
}
