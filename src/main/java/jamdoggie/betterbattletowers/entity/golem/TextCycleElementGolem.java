package jamdoggie.betterbattletowers.entity.golem;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.elements.TextCycleElement;
import net.minecraft.client.render.font.FontRenderer;

public class TextCycleElementGolem extends TextCycleElement<String> {
	public TextCycleElementGolem(Screen parent, FontRenderer font, int xPosition, int yPosition, int width, int height, String initialElement) {
		super(parent, font, xPosition, yPosition, width, height, initialElement);
	}

	@Override
	public String cycleElement(String string, int i) {
		if(i == -1) return GolemVariants.getPrevValue(string);
		return GolemVariants.getNextValue(string);
	}

	@Override
	public String getElementFromString(String s) {
		if (s.isEmpty()) {
			return "stone";
		}
		return GolemVariants.getNextValue(s);
	}


	@Override
	public String getNameFromElement(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
}
