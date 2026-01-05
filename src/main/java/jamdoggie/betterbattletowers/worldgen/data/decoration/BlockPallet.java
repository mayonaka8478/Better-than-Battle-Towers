package jamdoggie.betterbattletowers.worldgen.data.decoration;

public class BlockPallet{
	protected final BlockData data;
	protected final float chance;


	public float getChance() {
		return chance;
	}

	public BlockData getData() {
		return data;
	}

	public BlockPallet(BlockData data, float chance) {
		this.data = data;
		this.chance = chance;
	}

	public static BlockPallet pallet(int blockID, float chance) {
		return new BlockPallet(new BlockData(blockID), chance);
	}

	public static BlockPallet pallet(BlockData data, float chance) {
		return new BlockPallet(data, chance);
	}
}
