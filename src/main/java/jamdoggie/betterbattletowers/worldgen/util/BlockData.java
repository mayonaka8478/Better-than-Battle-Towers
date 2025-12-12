package jamdoggie.betterbattletowers.worldgen.util;

import java.util.Objects;

public class BlockData {
	private int id;

	private int metadata;

	public static BlockData bd(int id){
		return bd(id, 0);
	}

	public static BlockData bd(int id, int metadata) {
		return new BlockData(id, metadata);
	}

	public BlockData(int id, int metadata){
		this.id = id;
		this.metadata = metadata;
	}

	public BlockData(int id){
		this(id, 0);
	}

	public int id() {
		return id;
	}

	public int metadata() {
		return metadata;
	}

	public BlockData setId(int id) {
		this.id = id;
		return this;
	}

	public BlockData setMetadata(int metadata) {
		this.metadata = metadata;
		return this;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BlockData)) return false;
		BlockData blockData = (BlockData) object;
		return id == blockData.id && metadata == blockData.metadata;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, metadata);
	}

	public BlockData copy(){
		return new BlockData(this.id, this.metadata);
	}
}
