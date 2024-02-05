package io.github.kituin.actionlib;


import net.minecraft.util.text.event.HoverEvent;

import java.util.List;

/**
 * ActionRegisterApi
 *
 * @author kitUIN
 */
public interface IActionRegisterApi {

    /**
     * registerHoverEventAction
     *
     * @return {@link List<HoverEvent.Action>}
     */
    List<HoverEvent.Action> registerHoverEventAction();
}
