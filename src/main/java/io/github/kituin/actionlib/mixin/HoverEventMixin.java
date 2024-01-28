package io.github.kituin.actionlib.mixin;

import net.minecraft.text.HoverEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Arrays;

import static io.github.kituin.actionlib.ActionRegister.getHoverActions;

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
        temp.addAll(getHoverActions());
        return temp.toArray();
    }
}
