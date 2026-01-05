package jamdoggie.betterbattletowers.worldgen.properties.adapter;

import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.properties.decoration.BlockData;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;

import java.lang.reflect.Type;

import static jamdoggie.betterbattletowers.worldgen.properties.decoration.BlockData.bd;

public class BlockDataJsonAdapter implements JsonDeserializer<BlockData>, JsonSerializer<BlockData> {
	@Override
	public BlockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		int metadata = 0;
		if(obj.has("metadata")){
			metadata = obj.get("metadata").getAsInt();
		}
		Block<?> data;
		try {
			data = Blocks.blockMap.get(NamespaceID.getPermanent(obj.get("id").getAsString()));
		} catch (HardIllegalArgumentException e) {
			throw new RuntimeException("BlockID is not a valid id!", e);
		}
		return bd(data.id(), metadata);
	}

	@Override
	public JsonElement serialize(BlockData src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", Blocks.getBlock(src.id()).namespaceId().toString());
		if(src.metadata() != 0){
			obj.addProperty("metadata", src.metadata());
		}
		return obj;
	}
}
