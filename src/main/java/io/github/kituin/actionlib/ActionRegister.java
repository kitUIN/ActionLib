package io.github.kituin.actionlib;

import com.google.common.collect.Lists;
import net.minecraft.text.HoverEvent;

import java.util.List;

public class ActionRegister {
    private static List<HoverEvent.Action> hoverActions = Lists.newArrayList();
    public static void AddHoverAction(HoverEvent.Action action) {
        hoverActions.add(action);
    }
    public static List<HoverEvent.Action> getHoverActions() {
        return hoverActions;
    }
}
