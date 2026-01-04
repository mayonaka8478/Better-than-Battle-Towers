package jamdoggie.betterbattletowers.worldgen.properties.decoration;

import net.minecraft.core.world.biome.Biome;

public class TowerProperties {
	Biome biome;
	TowerProperty property;

	public Biome getBiome() {
		return biome;
	}

	public TowerProperty getProperty() {
		return property;
	}

	public TowerProperties setProperty(TowerProperty property) {
		this.property = property;
		return this;
	}

	public TowerProperties setBiome(Biome biome) {
		this.biome = biome;
		return this;
	}

	public TowerProperties(Biome biome, TowerProperty property) {
		this.biome = biome;
		this.property = property;
	}
}
