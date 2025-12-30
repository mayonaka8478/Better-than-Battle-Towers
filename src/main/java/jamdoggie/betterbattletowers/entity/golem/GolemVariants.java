package jamdoggie.betterbattletowers.entity.golem;

import java.util.ArrayList;
import java.util.List;

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
		this.cycleList.add("sandstone");
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

	public static String getNextValue(String type) {
		int index = 0;
		for(; index < instance.cycleList.size(); index++){
			if(type.equalsIgnoreCase(instance.cycleList.get(index))){
				return instance.cycleList.get((index + 1) % instance.cycleList.size());
			}
		}
		return DEFAULT;
	}
}
