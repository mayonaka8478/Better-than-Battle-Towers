package jamdoggie.betterbattletowers.entity.golem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GolemVariants {
	public static final String DEFAULT = "stone";
	private static GolemVariants instance;
	protected final List<String> cycleList = new ArrayList<>();

	private GolemVariants() {}

	public static void init() {
		if(instance == null){
			instance = new GolemVariants();
		}
		instance.load();
	}

	private void load() {
		this.cycleList.add("stone");
		this.cycleList.add("basalt");
		this.cycleList.add("granite");
		this.cycleList.add("limestone");
		this.cycleList.add("marble");
		this.cycleList.add("permafrost");
		this.cycleList.add("obsidian");
		this.cycleList.add("sandstone");
		this.cycleList.add("slate");
		this.cycleList.add("netherrack");
		this.cycleList.add("overgrown");
	}

	protected static void addEntry(String type){
		for(int index = 0; index < instance.cycleList.size(); index++){
			if(type.equalsIgnoreCase(instance.cycleList.get(index))){
				return;
			}
		}
		instance.cycleList.add(type);
	}

	public static String getPrevValue(String type) {
		return GolemVariants.getValue(type, -1);
	}

	public static String getNextValue(String type) {
		return GolemVariants.getValue(type, 1);
	}

	private static String getValue(String type, int i) {
		int index = 0;
		for(; index < instance.cycleList.size(); index++){
			if(type.equalsIgnoreCase(instance.cycleList.get(index))){
				return instance.cycleList.get(Math.floorMod(index + i, instance.cycleList.size()));
			}
		}
		return DEFAULT;
	}

	public static String getRandomEntry(Random random) {
		return instance.cycleList.get(random.nextInt(instance.cycleList.size()));
	}

	public static int getLength(){
		return instance.cycleList.size();
	}
}
