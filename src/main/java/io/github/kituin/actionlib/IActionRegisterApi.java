package io.github.kituin.actionlib;

import net.minecraft.text.HoverEvent;

import java.util.List;

/**
 * ActionRegisterApi
 *
 * @author kitUIN
 * @date  2024/01/29
 */
public interface IActionRegisterApi {

    /**
     * registerHoverEventAction
     *
     * @return {@link List<HoverEvent.Action>}
     */
    List<HoverEvent.Action> registerHoverEventAction();
}
