package jamdoggie.betterbattletowers.worldgen.data.adapter;

import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.data.decoration.BlockData;
import jamdoggie.betterbattletowers.worldgen.data.decoration.TowerProperty;
import net.minecraft.core.WeightedRandomBag;

import java.lang.reflect.Type;

public class TowerPropertyJsonAdapter implements JsonDeserializer<TowerProperty>, JsonSerializer<TowerProperty> {
	@Override
	public TowerProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		String golemType = obj.get("golem_type").getAsString();
		double decorationWeight = obj.get("decoration_weight").getAsDouble();
		JsonArray array = obj.get("tower_decoration").getAsJsonArray();
		WeightedRandomBag<BlockData> decoration = new WeightedRandomBag<>();
		for (JsonElement jsonElement : array.asList()) {
			JsonObject jsonObj = jsonElement.getAsJsonObject();
			BlockData data = context.deserialize(jsonObj.get("decoration"), BlockData.class);
			decoration.addEntry(data, jsonObj.get("weight").getAsDouble());
		}
		return new TowerProperty(golemType, decoration, decorationWeight);
	}

	@Override
	public JsonElement serialize(TowerProperty src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.addProperty("golem_type", src.getGolemType());
		obj.addProperty("decoration_weight", src.getChance());
		JsonArray array = new JsonArray();
		for (WeightedRandomBag<BlockData>.Entry data : src.getTowerDecorations().getEntriesWithWeights()) {
			JsonObject entryObj = new JsonObject();
			JsonElement blockJson = context.serialize(data.getObject(), BlockData.class);
			entryObj.add("decoration", blockJson);
			entryObj.addProperty("weight", data.getWeight());
			array.add(entryObj);
		}
		obj.add("tower_decoration", array);
		return obj;
	}
}
