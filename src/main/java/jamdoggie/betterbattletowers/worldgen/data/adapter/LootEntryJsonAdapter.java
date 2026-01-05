package jamdoggie.betterbattletowers.worldgen.data.adapter;

import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.data.loot.LootEntry;
import net.minecraft.core.WeightedRandomLootObject;

import java.lang.reflect.Type;

public class LootEntryJsonAdapter implements JsonDeserializer<LootEntry>, JsonSerializer<LootEntry> {
	@Override
	public LootEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		WeightedRandomLootObject lootObject = context.deserialize(obj.get("loot"), WeightedRandomLootObject.class);
		return LootEntry.loot(lootObject, obj.get("weight").getAsDouble());
	}

	@Override
	public JsonElement serialize(LootEntry src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		object.add("loot", context.serialize(src.getLootObj()));
		object.addProperty("weight", src.getWeight());
		return object;
	}
}
