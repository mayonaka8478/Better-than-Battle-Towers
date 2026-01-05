package jamdoggie.betterbattletowers.compat.waila;

import jamdoggie.betterbattletowers.entity.MobAgressiveZombiePig;
import jamdoggie.betterbattletowers.entity.golem.MobGolem;
import net.minecraft.core.item.Items;
import org.slf4j.Logger;
import toufoumaster.btwaila.entryplugins.waila.BTWailaCustomTooltipPlugin;
import toufoumaster.btwaila.tooltips.TooltipRegistry;

import static toufoumaster.btwaila.gui.components.WailaTextComponent.addEntityIcon;

public class BattleTowerBTWCompat implements BTWailaCustomTooltipPlugin {
    @Override
    public void initializePlugin(TooltipRegistry tooltipRegistry, Logger logger) {
        addEntityIcon(MobGolem.class, Items.DIAMOND);
        addEntityIcon(MobAgressiveZombiePig.class, Items.INGOT_GOLD);
    }
}
