package jamdoggie.betterbattletowers.worldgen.properties.adapter;

import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.properties.decoration.TowerProperties;
import jamdoggie.betterbattletowers.worldgen.properties.decoration.TowerProperty;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.world.biome.Biome;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TowerPropertiesJsonAdapter implements JsonDeserializer<TowerProperties>, JsonSerializer<TowerProperties> {
	@Override
	public TowerProperties deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Biome biome = Registries.BIOMES.getItem(obj.get("biome").getAsString());
		JsonArray jsonArray = obj.getAsJsonArray("tower_properties");
		List<TowerProperty> towerProperties = new ArrayList<>();
		for (JsonElement element : jsonArray) {
			TowerProperty property =
				context.deserialize(element, TowerProperty.class);
			towerProperties.add(property);
		}
		return new TowerProperties(biome, towerProperties);
	}

	@Override
	public JsonElement serialize(TowerProperties src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.addProperty("biome", Registries.BIOMES.getKey(src.getBiome()));
		JsonElement towerPropsJson = context.serialize(src.getProperties());
		obj.add("tower_properties", towerPropsJson);
		return obj;
	}
}
