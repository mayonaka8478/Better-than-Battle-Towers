package jamdoggie.betterbattletowers.worldgen;

import net.minecraft.core.WeightedRandomBag;

public class TowerProperty {
	WeightedRandomBag<Integer> towerDecorations = new WeightedRandomBag<>();
	int skinVariant;
	private TowerProperty() {
	}

	public TowerProperty(WeightedRandomBag<Integer> towerDecorations, int skinVariant) {
		this.towerDecorations = towerDecorations;
		this.skinVariant = skinVariant;
	}

	public static TowerProperty tower(){
		return new TowerProperty();
	}


	public WeightedRandomBag<Integer> getTowerDecorations() {
		return towerDecorations;
	}

	public int getSkinVariant() {
		return skinVariant;
	}

	public TowerProperty addDecorationBlocks(BlockPallet... idAndChances) {
		for(BlockPallet idAndChance : idAndChances){
			this.towerDecorations.addEntry(idAndChance.blockID, idAndChance.chance);
		}
		return this;
	}

	public TowerProperty addDecorationBlock(BlockPallet idAndChance) {
		this.towerDecorations.addEntry(idAndChance.blockID, idAndChance.chance);
		return this;
	}

	public TowerProperty setSkinVariant(int i){
		this.skinVariant = i;
		return this;
	}

}
