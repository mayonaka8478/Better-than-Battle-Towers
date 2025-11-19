package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.entity.MobGolem;
import jamdoggie.betterbattletowers.entity.MobRendererGolem;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelChest;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelFenceThin;
import net.minecraft.client.render.block.model.BlockModelTransparent;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.Side;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static net.minecraft.client.render.block.model.BlockModelStandard.BLOCK_TEXTURES;

public class BattleTowerModel implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		dispatcher.addDispatch(new BlockModelChest<>(BattleTowerBlocks.TOWER_CHEST, "betterbattletowers:block/chest_tower_")
			.setAllTextures(BLOCK_TEXTURES, "betterbattletowers:block/chest_tower_top"));

		dispatcher.addDispatch(new BlockModelTransparent<>(BattleTowerBlocks.PRISON_BAR, false)
			.onRenderLayer(1)
			.setTex(0, MOD_ID + ":block/prison_side_alt", Side.EAST, Side.NORTH, Side.WEST, Side.SOUTH)
			.setTex(0, MOD_ID + ":block/prison_top_alt", Side.TOP, Side.BOTTOM));

		dispatcher.addDispatch(
			(
				new BlockModelFenceThin<>(
					BattleTowerBlocks.PRISON_BAR_FENCE,
					TextureRegistry.getTexture(MOD_ID + ":block/fence_prison/center"),
					(IconCoordinate)null,
					TextureRegistry.getTexture(MOD_ID + ":block/fence_prison/top"),
					TextureRegistry.getTexture(MOD_ID + ":block/fence_prison/column")
				)
			).setAllTextures(0, MOD_ID + ":block/fence_prison/center")
		);
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		// no items
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		ModelHelper.setEntityModel(MobGolem.class, MobRendererGolem::new);
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {
		// no tile entity models
	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {
		// no map colors
	}
}
