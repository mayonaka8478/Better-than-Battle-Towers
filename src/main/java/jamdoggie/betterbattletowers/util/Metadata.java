package jamdoggie.betterbattletowers.util;

public class Metadata {
	private static final int MAX_METAVALUE = 0b1111_1111;
	private static final int UPPER_MASK = 0b1111_0000;
	private static final int LOWER_MASK = 0b0000_1111;
	private static final int NIBBLE_LENGTH = 4;
	private static final int NOT_METADATA = -1;

	private Metadata(){}

	public static boolean isSet(int metadata, int index){
		return (Metadata.getBit(metadata, index) & 1) == 1;
	}

	public static int getBit(int metadata, int index){
		if(index >= 8 || index < 0) {
			return NOT_METADATA;
		}
		return ((metadata & MAX_METAVALUE) >>> index) & 1;
	}

	private static int rawGetBit(int metadata, int index){
		return (metadata >>> index) & 1;
	}

	public static int getBitBlock(int metadata, int startIndex, int endIndex){
		if(startIndex >= 8 || startIndex < 0 || endIndex >= 8 || endIndex < 0) {
			return NOT_METADATA;
		}
		int len = endIndex - startIndex;
		if(len < 0) {
			return NOT_METADATA;
		}
		if(len == 0) {
			return Metadata.rawGetBit(metadata & MAX_METAVALUE, startIndex);
		}
		return Metadata.getBits(metadata & MAX_METAVALUE, startIndex, len + 1);
	}

	private static int getBits(int metadata, int startIndex, int len){
		int mask = (1 << len) - 1;
		return ((metadata & MAX_METAVALUE) >>> startIndex) & mask;
	}

	public static int getUpperBlock(int metadata){
		return rawGetUpperBlock(metadata & MAX_METAVALUE);
	}
	private static int rawGetUpperBlock(int metadata){
		return (metadata & UPPER_MASK) >> NIBBLE_LENGTH;
	}

	public static int getLowerBlock(int metadata){
		return Metadata.rawGetLowerBlock(metadata);
	}

	private static int rawGetLowerBlock(int metadata){
		return metadata & LOWER_MASK;
	}

	public static int setBit(int metadata, int index){
		return Metadata.setBit(metadata, index, 1);
	}

	public static int setBit(int metadata, int index, int value){
		if(index >= 8 || index < 0){
			return NOT_METADATA;
		}
		return Metadata.rawSetBit(metadata & MAX_METAVALUE, index, value & 1);
	}

	private static int rawSetBit(int metadata, int index, int value) {
		if (value == 0) {
			return metadata & ~(1 << index);
		}
		return metadata | (1 << index);
	}

	public static int setBitBlock(int metadata, int startIndex, int endIndex, int value){
		if(value > (MAX_METAVALUE >>> startIndex)|| startIndex >= 8 || startIndex < 0 || startIndex > endIndex || endIndex > 8) {
			return NOT_METADATA;
		}
		return Metadata.rawSetBitBlock(metadata & MAX_METAVALUE, startIndex, endIndex - startIndex + 1, value);
	}

	private static int rawSetBitBlock(int metadata, int startIndex, int bitBlockLength, int value) {
		int mask = ((1 << bitBlockLength) - 1) << startIndex;
		metadata = (metadata & ~mask) | ((value << startIndex) & mask);
		return metadata;
	}



	public static int flipBit(int metadata, int index){
		if(index >= 8 || index < 0){
			return NOT_METADATA;
		}
		return Metadata.rawFlipBit(metadata & MAX_METAVALUE, index);
	}

	private static int rawFlipBit(int metadata, int index){
		return Metadata.rawSetBit(metadata, index, 1 - Metadata.rawGetBit(metadata, index));
	}
}
