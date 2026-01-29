package jamdoggie.betterbattletowers.model;

import jamdoggie.betterbattletowers.block.BattleTowerBlocks;
import jamdoggie.betterbattletowers.entity.golem.MobGolem;
import jamdoggie.betterbattletowers.entity.golem.MobRendererGolem;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.Side;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;
import static net.minecraft.client.render.block.model.BlockModelStandard.BLOCK_TEXTURES;
import static net.minecraft.client.render.block.model.BlockModelStandard.OVERBRIGHT_TEXTURES;

public class BattleTowerModel implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		dispatcher.addDispatch(new BlockModelChest<>(BattleTowerBlocks.TOWER_CHEST, "betterbattletowers:block/tower_chest/chest_tower_")
			.setAllTextures(BLOCK_TEXTURES, "betterbattletowers:block/tower_chest/chest_tower_top"));

		dispatcher.addDispatch(new BlockModelCage<>(BattleTowerBlocks.PRISON_BAR, false)
			.onRenderLayer(1)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/tinted_bars/side", Side.EAST, Side.NORTH, Side.WEST, Side.SOUTH)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/tinted_bars/top", Side.TOP, Side.BOTTOM));

		dispatcher.addDispatch(
			(
				new BlockModelFenceThin<>(
					BattleTowerBlocks.PRISON_BAR_FENCE,
					TextureRegistry.getTexture(MOD_ID + ":block/tinted_bars_fence/center"),
					(IconCoordinate) null,
					TextureRegistry.getTexture(MOD_ID + ":block/tinted_bars_fence/top"),
					TextureRegistry.getTexture(MOD_ID + ":block/tinted_bars_fence/column")
				)
			).setAllTextures(BLOCK_TEXTURES, MOD_ID + ":block/tinted_bars_fence/center")
		);

		dispatcher.addDispatch(new BlockModelCrumblingStone<>(BattleTowerBlocks.CRUMBLING_STONE, MOD_ID + ":block/crumble/crumble_")
			.setAllTextures(BLOCK_TEXTURES, "minecraft:block/polished_stone_top"));

		dispatcher.addDispatch(new BlockModelStairs<>(BattleTowerBlocks.STAIRS_CRUMBLING_STONE)
			.setAllTextures(BLOCK_TEXTURES, "minecraft:block/polished_stone_top"));

		dispatcher.addDispatch(new BlockModelSlab<>(BattleTowerBlocks.SLAB_CRUMBLING_STONE)
			.setAllTextures(BLOCK_TEXTURES, "minecraft:block/polished_stone_top"));

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.RUNIC_STONE)
			.setAllTextures(BLOCK_TEXTURES, MOD_ID + ":block/runic/runic_stone"));

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.CHISELED_RUNIC_STONE)
			.setAllTextures(BLOCK_TEXTURES, MOD_ID + ":block/runic/chiseled_runic"));

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.PILLAR_RUNIC)
			.setTex(0, "minecraft:block/polished_slate_top", Side.TOP, Side.BOTTOM)
			.setTex(0, MOD_ID + ":block/runic/pillar_runic", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST));

		dispatcher.addDispatch(new BlockModelHorizontalRotation<>(BattleTowerBlocks.RUNIC_GLYPH_STONE)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/runic/runic_stone", Side.TOP, Side.BOTTOM)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/runic/glyph/unlit_glyph_north", Side.EAST)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/runic/glyph/unlit_glyph_east", Side.SOUTH)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/runic/glyph/unlit_glyph_south", Side.WEST)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/runic/glyph/unlit_glyph_west", Side.NORTH)
			.setTex(OVERBRIGHT_TEXTURES, MOD_ID + ":block/runic/glyph/glyph_runic_glow_north", Side.EAST)
			.setTex(OVERBRIGHT_TEXTURES, MOD_ID + ":block/runic/glyph/glyph_runic_glow_east", Side.SOUTH)
			.setTex(OVERBRIGHT_TEXTURES, MOD_ID + ":block/runic/glyph/glyph_runic_glow_south", Side.WEST)
			.setTex(OVERBRIGHT_TEXTURES, MOD_ID + ":block/runic/glyph/glyph_runic_glow_west", Side.NORTH)
		);

		dispatcher.addDispatch(new BlockModelStandard<>(BattleTowerBlocks.CARVED_OBSIDIAN)
			.setTex(0, "minecraft:block/obsidian", Side.TOP, Side.BOTTOM)
			.setTex(0, MOD_ID + ":block/carved_obsidian", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST));
		dispatcher.addDispatch(new BlockModelSlab<>(BattleTowerBlocks.SLAB_OBSIDIAN));
		dispatcher.addDispatch(new BlockModelStairs<>(BattleTowerBlocks.STAIRS_OBSIDIAN));

		dispatcher.addDispatch(new BlockModelCloaked<>(BattleTowerBlocks.OVERGROWN_BRICKS, MOD_ID + ":block/overgrown/side_brick")
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/overgrown/top_moss", Side.TOP, Side.BOTTOM)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/overgrown/side_moss", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST)
		);

		dispatcher.addDispatch(new BlockModelCloakBloom<>(BattleTowerBlocks.OVERGROWN_BLOOM_BRICK, MOD_ID + ":block/overgrown/side_brick", MOD_ID + ":block/overgrown/flowers")
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/overgrown/top_moss", Side.TOP, Side.BOTTOM)
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/overgrown/side_moss", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST)
		);

		dispatcher.addDispatch(new BlockModelBlooming<>(BattleTowerBlocks.OVERGROWN, MOD_ID + ":block/overgrown/flowers")
			.setTex(BLOCK_TEXTURES, MOD_ID + ":block/overgrown/top_moss", Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST, Side.TOP, Side.BOTTOM)
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
		dispatcher.addDispatch(BattleTowerBlocks.OVERGROWN_BRICKS, new BlockColorCustom(Colorizers.grass));
		dispatcher.addDispatch(BattleTowerBlocks.OVERGROWN_BLOOM_BRICK, new BlockColorCustom(Colorizers.grass));
		dispatcher.addDispatch(BattleTowerBlocks.OVERGROWN, new BlockColorCustom(Colorizers.grass));
	}
}
