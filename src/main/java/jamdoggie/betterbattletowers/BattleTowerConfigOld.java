package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.worldgen.util.LootTable;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.util.ConfigHandler;

import java.util.*;

import static jamdoggie.betterbattletowers.worldgen.util.LootTable.LootEntry.loot;

public class BattleTowerConfigOld {

	private BattleTowerConfigOld(){}

	public static IItemConvertible getBlockByName(String name) {
		String correctedString = name.replace(':', '.');
		if (correctedString.startsWith("Block.")) {
			for (Block<?> block : Blocks.blocksList) {
				if (block != null) {
					String otherName = block.getKey().substring(5);
					if (name.substring(6).equalsIgnoreCase(otherName)) {
						return block;
					}
				}
			}
		} else if (correctedString.startsWith("Item.")) {
			for (Item item : Item.itemsList) {
				if (item != null) {
					String otherName = item.getKey().substring(5);
					if (name.substring(5).equalsIgnoreCase(otherName)) {
						return item;
					}
				}
			}
		}
		return null;
	}

	public static Map<Integer, List<LootTable.LootEntry>> convertOldConfig(ConfigHandler handler){
		Map<Integer, List<LootTable.LootEntry>> convertableItems = new HashMap<>();
		String prefix = "lootitem";
		for(int i = 1; i < 10; i++){
			List<LootTable.LootEntry> lootEnties = convertableItems.computeIfAbsent(i, k -> new ArrayList<>());
			for(int level = 0; level < 4; level++){
				String name = prefix + i + '_' + level;
				String lootItem = handler.getString(name);
				lootEnties.add(loot(lootItem, 0));
			}
		}
		List<LootTable.LootEntry> zero = convertableItems.get(1);
		convertableItems.put(0, zero);
		List<LootTable.LootEntry> topFloor = convertableItems.computeIfAbsent(10, k -> new ArrayList<>());
		for(int level = 0; level < 4; level++){
			String name = "lootitemtop_" +  level;
			String lootItem = handler.getString(name);
			topFloor.add(loot(lootItem, 0));
		}
		return convertableItems;
	}

	public static Properties getOldProperties(){
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "6340");
		prop.setProperty("starting_item_id", "26340");
		prop.setProperty("towercount", "200");
		prop.setProperty("rarity", "10");
		//Tower Loot
		//floor1
		prop.setProperty("lootitem1_0", "Item.stick");
		prop.setProperty("lootitem1_1", "Item.seeds.wheat");
		prop.setProperty("lootitem1_2", "Item.ammo.pebble");
		prop.setProperty("lootitem1_3", "Block.sand");
		//floor2
		prop.setProperty("lootitem2_0", "Item.coal");
		prop.setProperty("lootitem2_1", "Item.stick");
		prop.setProperty("lootitem2_2", "Block.planks.oak");
		prop.setProperty("lootitem2_3", "Block.wool");
		//floor3
		prop.setProperty("lootitem3_0", "Item.feather.chicken");
		prop.setProperty("lootitem3_1", "Item.food.bread");
		prop.setProperty("lootitem3_2", "Block.glass");
		prop.setProperty("lootitem3_3", "Block.mushroom.brown");
		//floor4
		prop.setProperty("lootitem4_0", "Item.feather.chicken");
		prop.setProperty("lootitem4_1", "Item.food.bread");
		prop.setProperty("lootitem4_2", "Block.glass");
		prop.setProperty("lootitem4_3", "Block.mushroom.brown");
		//floor5
		prop.setProperty("lootitem5_0", "Block.stairs.planks.oak");
		prop.setProperty("lootitem5_1", "Block.brick.clay");
		prop.setProperty("lootitem5_2", "Item.ingot.iron");
		prop.setProperty("lootitem5_3", "Item.rope");
		//floor6
		prop.setProperty("lootitem6_0", "Block.ladder.oak");
		prop.setProperty("lootitem6_1", "Item.flint");
		prop.setProperty("lootitem6_2", "Item.dust.redstone");
		prop.setProperty("lootitem6_3", "Item.ingot.gold");
		//floor7
		prop.setProperty("lootitem7_0", "Block.pumpkin.carved.active");
		prop.setProperty("lootitem7_1", "Block.rail");
		prop.setProperty("lootitem7_2", "Item.ore.raw.iron");
		prop.setProperty("lootitem7_3", "Item.bucket.lava");
		//floor8
		prop.setProperty("lootitem8_0", "Block.tnt");
		prop.setProperty("lootitem8_1", "Block.slate");
		prop.setProperty("lootitem8_2", "Item.ore.raw.gold");
		prop.setProperty("lootitem8_3", "Item.chainlink");
		//floor9
		prop.setProperty("lootitem9_0", "Item.quartz");
		prop.setProperty("lootitem9_1", "Item.olivine");
		prop.setProperty("lootitem9_2", "Item.dust.redstone");
		prop.setProperty("lootitem9_3", "Block.mesh");
		//floortop
		prop.setProperty("lootitemtop_0", "Block.mesh.gold");
		prop.setProperty("lootitemtop_1", "Item.ingot.gold");
		prop.setProperty("lootitemtop_2", "Item.ingot.iron");
		prop.setProperty("lootitemtop_3", "Item.diamond");

		return prop;
	}
}
