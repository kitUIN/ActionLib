package io.github.kituin.actionlib.mixin;

import com.google.common.collect.Lists;
import io.github.kituin.actionlib.ActionLib;
import io.github.kituin.actionlib.IActionRegisterApi;
import io.github.kituin.actionlib.ForgePluginFinder;
import net.minecraft.util.text.event.HoverEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * HoverEvent Mixin
 *
 * @author kitUIN
 */
@Mixin(HoverEvent.Action.class)
public abstract class HoverEventMixin {

    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE",
                target = "Ljava/util/stream/Stream;of([Ljava/lang/Object;)Ljava/util/stream/Stream;",
                remap = false
            )
    )
    private static Object[] injectedHoverEvent(Object[] values) {
        ArrayList<Object> temp = new ArrayList<>(Arrays.asList(values));
        temp.addAll(actionlib$registerAll());

        ActionLib.LOGGER.info("Register All HoverEvent Counts: {}", temp.size());
        return temp.toArray();
    }

    /**
     * registerAll From Mod
     *
     * @return {@link List}<{@link HoverEvent.Action}>
     */
    @Unique
    private static List<HoverEvent.Action> actionlib$registerAll()
    {
        List<HoverEvent.Action> hoverEventActions = Lists.newArrayList();
        for (IActionRegisterApi api: ForgePluginFinder.getModPlugins()) {
            try {
                List<HoverEvent.Action> actions = api.registerHoverEventAction();
                hoverEventActions.addAll(actions);

                //ActionLib.LOGGER.info("Add New HoverEvent: {}",actions.size());
            } catch (Throwable e) {
                ActionLib.LOGGER.error("provides a broken implementation of ActionRegisterApi", e);
            }
        }

        return hoverEventActions;
    }
}
