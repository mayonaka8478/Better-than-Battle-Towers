package jamdoggie.betterbattletowers.worldgen.properties.decoration;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Blocks;

import static jamdoggie.betterbattletowers.worldgen.properties.decoration.BlockData.bd;

public class TowerProperty {
	String golemType;
	WeightedRandomBag<BlockData> towerDecorations;

	private TowerProperty(String golemType, WeightedRandomBag<BlockData> towerDecorations) {
		this.towerDecorations = towerDecorations;
		this.golemType = golemType;
	}

	public static TowerProperty tower(String golemType, BlockPallet ... palettes){
		WeightedRandomBag<BlockData> towerDecoration = new WeightedRandomBag<>();
		if(palettes.length == 0){
			towerDecoration.addEntry(bd(Blocks.COBBLE_STONE.id()), 1.0f);
			return new TowerProperty(golemType, towerDecoration);
		}
		for(BlockPallet pallet : palettes){
			towerDecoration.addEntry(pallet.data, pallet.chance);
		}
		return new TowerProperty(golemType, towerDecoration);
	}

	public WeightedRandomBag<BlockData> getTowerDecorations() {
		return this.towerDecorations;
	}

	public String getGolemType() {
		return this.golemType;
	}
}
