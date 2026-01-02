package jamdoggie.betterbattletowers.worldgen.properties;

import java.util.List;

public class LootTable {
	int level;
	List<LootEntry> lootEntries;

	public LootTable(int level, List<LootEntry> lootEntries) {
		this.level = level;
		this.lootEntries = lootEntries;
	}

	public static LootTable table(int level, List<LootEntry> lootEntries){
		return new LootTable(level, lootEntries);
	}

	public int getLevel() {
		return level;
	}

	public List<LootEntry> getLootEntries() {
		return lootEntries;
	}
}
