package io.github.kituin.actionlib.mixin;

import io.github.kituin.actionlib.ActionLib;
import net.minecraft.text.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Arrays;

import static io.github.kituin.actionlib.ActionRegister.HOVER_EVENT_ACTIONS;


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
        temp.addAll(HOVER_EVENT_ACTIONS);
        ActionLib.LOGGER.info("register hoverEvent counts:"+ temp.size());
        return temp.toArray();
    }
}
