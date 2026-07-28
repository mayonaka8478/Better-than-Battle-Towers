package jamdoggie.betterbattletowers.worldgen.data.loader;

import com.b100.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.*;
import jamdoggie.betterbattletowers.worldgen.data.adapter.*;
import jamdoggie.betterbattletowers.worldgen.data.decoration.BlockData;
import jamdoggie.betterbattletowers.worldgen.data.decoration.TowerProperties;
import jamdoggie.betterbattletowers.worldgen.data.decoration.TowerProperty;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.recipe.adapter.WeightedRandomBagJsonAdapter;
import net.minecraft.core.world.biome.Biome;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static jamdoggie.betterbattletowers.BattleTowerMod.LOGGER;
import static jamdoggie.betterbattletowers.BattleTowerMod.MOD_ID;

public class TowerDataLoader {
	private static final Map<Biome, Set<TowerProperty>> BIOME_TO_TOWER_PROPERTIES = new HashMap<>();
	private static final List<TowerProperty> LIST_PROPERTY = new ArrayList<>();
	private static final String ASSETS_JAR_PATH = String.format("/assets/%s/tower/tower_properties.json", MOD_ID);
	private static boolean init = false;
	private TowerDataLoader(){}

	public static TowerProperty getRandomTowerProperty(Random random) {
		return LIST_PROPERTY.get(random.nextInt(LIST_PROPERTY.size()));
	}

	public static TowerProperty getTowerProperties(Biome biome, Random random) {if (!BIOME_TO_TOWER_PROPERTIES.containsKey(biome)) {
			return getRandomTowerProperty(random);
		}
		List<TowerProperty> towerPropertyList = new ArrayList<>(BIOME_TO_TOWER_PROPERTIES.get(biome));
		return towerPropertyList.get(random.nextInt(towerPropertyList.size()));
	}

	public static void init() {
		if (init) {
			return;
		}
		LOGGER.info("Loading tower properties.");
		init = true;
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(BlockData.class, new BlockDataJsonAdapter())
			.registerTypeAdapter(TowerProperty.class, new TowerPropertyJsonAdapter())
			.registerTypeAdapter(TowerProperties.class, new TowerPropertiesJsonAdapter())
			.registerTypeAdapter(WeightedRandomBag.class, new WeightedRandomBagJsonAdapter())
			.setPrettyPrinting()
			.create();

		Type type = new TypeToken<List<TowerProperties>>() {
		}.getType();

		if (FabricLoader.getInstance().isDevelopmentEnvironment() && DataLoader.class.getResourceAsStream(TowerDataLoader.ASSETS_JAR_PATH) == null) {
			List<TowerProperties> defaultTable = TowerDataDefault.getDefaultTowers();
			String json = gson.toJson(defaultTable, type);
			Path projectRoot = FabricLoader.getInstance().getGameDir().getParent();
			Path devPath = projectRoot.resolve("src/main/resources" + ASSETS_JAR_PATH);
			try {
				Files.createDirectories(devPath.getParent());
				Files.write(devPath, json.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				throw new RuntimeException("Failed to create default tower properties at: "
					+ devPath.toAbsolutePath(), e);
			}
		}

		List<TowerProperties> towerProperties = null;
		try (InputStream stream = DataLoader.class.getResourceAsStream(TowerDataLoader.ASSETS_JAR_PATH)) {
			if (stream == null) {
				LOGGER.error("LootTable could not be regenerated, betterbattletowers.jar is corrupted.");
				throw new RuntimeException("Asset not found in JAR: " + TowerDataLoader.ASSETS_JAR_PATH);
			}
			String jsonString = StringUtils.readInputString(stream);
			towerProperties = gson.fromJson(jsonString, type);
		} catch (IOException e) {
			throw new RuntimeException("Tower properties could not be read from file!", e);
		} catch (JsonSyntaxException | JsonIOException e) {
			throw new RuntimeException("Failed to parse tower properties json at: " + TowerDataLoader.ASSETS_JAR_PATH, e);
		}

		Set<TowerProperty> setProperties = new HashSet<>();
		for (TowerProperties properties : towerProperties) {
			BIOME_TO_TOWER_PROPERTIES.put(properties.getBiome(), new HashSet<>(properties.getProperties()));
			setProperties.addAll(properties.getProperties());
		}
		LIST_PROPERTY.addAll(setProperties);
	}
}
