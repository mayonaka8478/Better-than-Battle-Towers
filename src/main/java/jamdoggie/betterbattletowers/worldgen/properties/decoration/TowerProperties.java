package jamdoggie.betterbattletowers.worldgen.properties.decoration;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class TowerProperties {
	Biome biome;
	List<TowerProperty> property;

	public Biome getBiome() {
		return biome;
	}

	public List<TowerProperty> getProperties() {
		return property;
	}

	public TowerProperties setProperty(List<TowerProperty> property) {
		this.property = property;
		return this;
	}

	public TowerProperties setBiome(Biome biome) {
		this.biome = biome;
		return this;
	}

	public TowerProperties(Biome biome, List<TowerProperty> property) {
		this.biome = biome;
		this.property = property;
	}

	private TowerProperties(Biome biome) {
		this.biome = biome;
		this.property = new ArrayList<>();
	}

	public static TowerProperties properties(Biome biome) {
		return new TowerProperties(biome);
	}

	public TowerProperties addProperty(TowerProperty property){
		this.property.add(property);
		return this;
	}
}
