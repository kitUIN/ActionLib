package io.github.kituin.actionlib.mixin;

import com.mojang.serialization.DataResult;
import net.minecraft.text.HoverEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HoverEvent.Action.class)
public interface HoverEventAccessor {
    @Invoker("validate")
    static DataResult<HoverEvent.Action<?>>invokeValidate(@Nullable HoverEvent.Action<?> action) {
        throw new AssertionError();
    };
}
