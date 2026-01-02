package jamdoggie.betterbattletowers.entity.golem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntry;
import net.minecraft.client.gui.modelviewer.elements.ListenerButtonElement;
import net.minecraft.client.gui.modelviewer.elements.TextCycleElement;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityEntryGolem extends EntityEntry<MobGolem> {
	@Override
	public void onTick(MobGolem mobGolem) {
		/*does not need to tick*/
	}

	@Override
	public List<ButtonElement> getEntryButtons(Minecraft minecraft, Screen parentScreen, MobGolem mobGolem) {
		List<ButtonElement> buttonList = new ArrayList<>();
		I18n translator = I18n.getInstance();
		ListenerButtonElement golemState = new ListenerButtonElement(-1, -120, 0, 120, 20, translator.translateKey("model.category.entity.golem.state.dormant"));
		golemState.setActionListener(() -> dormantState(mobGolem, golemState, translator));
		buttonList.add(golemState);
		TextCycleElement<String> type = new TextCycleElementGolem(parentScreen, minecraft.font, -120, golemState.yPosition + 21, 120, 20, "Stone");
		type.textField.setPrefaceText(translator.translateKey("model.category.entity.golem.placeholder.text"));
		type.textField.setPlaceholder(translator.translateKey("model.category.entity.golem.placeholder"));
		type.setOnValueChanged(() -> setNextType(type, mobGolem));
		buttonList.add(type);
		return buttonList;
	}

	private static void setNextType(TextCycleElement<String> type, MobGolem mobGolem) {
		mobGolem.setVariant(type.getCurrentElement());
	}

	private static void dormantState(MobGolem mobGolem, ListenerButtonElement button, I18n translator) {
		mobGolem.setDormant(!mobGolem.isDormant());
		button.displayString = translator.translateKey("model.category.entity.golem.state" + (mobGolem.isDormant() ? "dormant" : "awake"));
	}

	@Override
	public MobGolem getEntityInstance(Minecraft minecraft, World world) {
		return new MobGolem(world);
	}

	@Override
	public void onOpen() {
		/*does not need to onOpen*/
	}

	@Override
	public void onClose() {
		/*does not need to onClose*/
	}
}
