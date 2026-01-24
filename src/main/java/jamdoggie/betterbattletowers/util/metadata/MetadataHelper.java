package jamdoggie.betterbattletowers.util.metadata;

import net.minecraft.core.util.helper.Direction;

public class MetadataHelper {

	private MetadataHelper() {}

	public static class Stairs {

		public static int setMetadata(int metadata, Direction direction, boolean flipped){
			if(flipped)Metadata.flipBit(0, 3);
			return Metadata.setBitBlock(metadata, 0, 1, MetadataHelper.Stairs.getMetadataFromDirection(direction));
		}

		public static int getMetadataFromDirection(Direction direction) {
			switch (direction) {
				case NORTH:
					return 3;
				case SOUTH:
					return 2;
				case WEST:
					return 1;
				case EAST:
				default:
					return 0;
			}
		}

		public static Direction getDirectionFromMetadata(int metadata) {
			int direction = Metadata.getBitBlock(metadata, 0, 1);
			switch (direction) {
				case 3:
					return Direction.NORTH;
				case 2:
					return Direction.SOUTH;
				case 1:
					return Direction.WEST;
				case 0:
				default:
					return Direction.EAST;
			}
		}
	}
}
