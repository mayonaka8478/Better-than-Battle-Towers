package jamdoggie.betterbattletowers.worldgen.properties.adapter;

import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.properties.loot.LootEntry;
import jamdoggie.betterbattletowers.worldgen.properties.loot.LootTable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LootTableJsonAdapter implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
	@Override
	public LootTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		int level = obj.get("floor").getAsInt();
		List<LootEntry> lootEntries = new ArrayList<>();
		JsonArray entriesArray = obj.getAsJsonArray("table");
		for (JsonElement element : entriesArray) {
			LootEntry entry = context.deserialize(element, LootEntry.class);
			lootEntries.add(entry);
		}
		return new LootTable(level, lootEntries);
	}

	@Override
	public JsonElement serialize(LootTable src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.addProperty("floor", src.getLevel());
		JsonArray entriesArray = new JsonArray();
		src.getLootEntries().stream().map(context::serialize).forEach(entriesArray::add);
		obj.add("table", entriesArray);
		return obj;
	}
}
