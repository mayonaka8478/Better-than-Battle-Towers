package jamdoggie.betterbattletowers.worldgen;

import java.util.Objects;

/**
 * @implNote This is a container for Block that hold their id and the weight of that Block to be drawn
 * */
public class BlockPallet {
	public int blockID;
	public float chance;

	public BlockPallet(int blockID, float chance) {
		this.blockID = blockID;
		this.chance = chance;
	}

	public static BlockPallet ipair(int first, float second) {
		return new BlockPallet(first, second);
	}

	@Override
	public String toString() {
		return "(" + blockID + ", " + chance + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(blockID, chance);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BlockPallet)) return false;
		BlockPallet that = (BlockPallet) o;
		return this.blockID == that.blockID && Float.compare(this.chance, that.chance) == 0;
	}
}
