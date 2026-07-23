package jamdoggie.betterbattletowers.model;


import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.entity.golem.MobGolem;
import jamdoggie.betterbattletowers.entity.golem.MobRendererGolem;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.block.model.generic.BlockModelGenericChest;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.Side;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static net.minecraft.client.render.block.model.BlockModelDispatcher.loadDataModel;

public class BattleTowerModel {

	private BattleTowerModel(){}


	public static void initBlockModels(BlockModelDispatcher dispatcher) {
		dispatcher.addDispatch(new BlockModelGenericChest<>(BattleTowerBlocks.TOWER_CHEST,
			loadDataModel(MOD_ID + ":block/chest/single"),
			loadDataModel(MOD_ID + ":block/chest/left"),
			loadDataModel(MOD_ID + ":block/chest/right"))
		);

		dispatcher.addDispatch(new BlockModelCage<>(BattleTowerBlocks.PRISON_BAR, false)
			.onRenderLayer(1)
			.setTex(MOD_ID + ":block/tinted_bars/side", Side.EAST, Side.NORTH, Side.WEST, Side.SOUTH)
			.setTex(MOD_ID + ":block/tinted_bars/top", Side.TOP, Side.BOTTOM));

		dispatcher.addDispatch(
			(
				new BlockModelFenceThin<>(
					BattleTowerBlocks.PRISON_BAR_FENCE,
					TextureRegistry.getTexture(MOD_ID + ":block/tinted_bars_fence/center"),
					(IconCoordinate) null,
					TextureRegistry.getTexture(MOD_ID + ":block/tinted_bars_fence/top"),
					TextureRegistry.getTexture(MOD_ID + ":block/tinted_bars_fence/column")
				)
			).setAllTextures(MOD_ID + ":block/tinted_bars_fence/center")
		);

		dispatcher.addDispatch(new BlockModelCrumblingStone<>(
			BattleTowerBlocks.CRUMBLING_STONE,
			MOD_ID + ":block/crumble/crumble_",
			MOD_ID + ":block/crumble/crumble_"
		).setAllTextures("minecraft:block/polished_stone_top"));

		dispatcher.addDispatch(new BlockModelCrumblingStone<>(
			BattleTowerBlocks.CRUMBLING_CARVED_BLOCK,
			MOD_ID + ":block/crumble/crumble_",
			MOD_ID + ":block/crumble/carved_"
		)
			.setTex("minecraft:block/polished_stone_top", Side.TOP, Side.BOTTOM)
			.setTex("minecraft:block/carved_stone", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST));

		dispatcher.addDispatch(new BlockModelStairs<>(BattleTowerBlocks.STAIRS_CRUMBLING_STONE)
			.setAllTextures("minecraft:block/polished_stone_top"));

		dispatcher.addDispatch(new BlockModelCrumblingSlab<>(BattleTowerBlocks.SLAB_CRUMBLING_STONE, MOD_ID + ":block/crumble/carved_")
			.setTex("minecraft:block/polished_stone_top", Side.TOP, Side.BOTTOM)
			.setTex("minecraft:block/carved_stone", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST));

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.RUNIC_STONE)
			.setAllTextures( MOD_ID + ":block/runic/runic_stone"));

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.CHISELED_RUNIC_STONE)
			.setAllTextures( MOD_ID + ":block/runic/chiseled_runic"));

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.PILLAR_RUNIC)
			.setTex("minecraft:block/polished_slate_top", Side.TOP, Side.BOTTOM)
			.setTex(MOD_ID + ":block/runic/pillar_runic", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST));

		dispatcher.addDispatch(new BlockModelHorizontalRotation<>(BattleTowerBlocks.RUNIC_GLYPH_STONE)
			.setTex(MOD_ID + ":block/runic/runic_stone", Side.TOP, Side.BOTTOM)
			.setTex(MOD_ID + ":block/runic/glyph/glyph_runic_north", Side.EAST)
			.setTex(MOD_ID + ":block/runic/glyph/glyph_runic_east", Side.SOUTH)
			.setTex(MOD_ID + ":block/runic/glyph/glyph_runic_south", Side.WEST)
			.setTex(MOD_ID + ":block/runic/glyph/glyph_runic_west", Side.NORTH)
		);

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.CARVED_OBSIDIAN)
			.setTex("minecraft:block/obsidian", Side.TOP, Side.BOTTOM)
			.setTex(MOD_ID + ":block/carved_obsidian", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST));
		dispatcher.addDispatch(new BlockModelSlab<>(BattleTowerBlocks.SLAB_OBSIDIAN));
		dispatcher.addDispatch(new BlockModelStairs<>(BattleTowerBlocks.STAIRS_OBSIDIAN));

		dispatcher.addDispatch(new BlockModelCloaked<>(BattleTowerBlocks.OVERGROWN_BRICKS, MOD_ID + ":block/overgrown/side_brick")
			.setTex(MOD_ID + ":block/overgrown/top_moss", Side.TOP, Side.BOTTOM)
			.setTex(MOD_ID + ":block/overgrown/side_moss", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST)
		);

		dispatcher.addDispatch(new BlockModelCloakBloom<>(BattleTowerBlocks.OVERGROWN_BLOOM_BRICK, MOD_ID + ":block/overgrown/side_brick", MOD_ID + ":block/overgrown/flowers")
			.setTex(MOD_ID + ":block/overgrown/top_moss", Side.TOP, Side.BOTTOM)
			.setTex(MOD_ID + ":block/overgrown/side_moss", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST)
		);

		dispatcher.addDispatch(new BlockModelBlooming<>(BattleTowerBlocks.OVERGROWN, MOD_ID + ":block/overgrown/flowers")
			.setTex( MOD_ID + ":block/overgrown/top_moss", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST, Side.TOP, Side.BOTTOM)
		);
	}


	public static void initEntityModels(EntityRendererDispatcher dispatcher) {
		dispatcher.assignRenderer(MobGolem.class, new MobRendererGolem(1.0F, 2.0F));
	}


	public static void initBlockColors(BlockColorDispatcher dispatcher) {
		dispatcher.addDispatch(BattleTowerBlocks.OVERGROWN_BRICKS, new BlockColorCustom(Colorizers.grass));
		dispatcher.addDispatch(BattleTowerBlocks.OVERGROWN_BLOOM_BRICK, new BlockColorCustom(Colorizers.grass));
		dispatcher.addDispatch(BattleTowerBlocks.OVERGROWN, new BlockColorCustom(Colorizers.grass));
	}
}
