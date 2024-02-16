package io.github.kituin.actionlib.mixin;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import io.github.kituin.actionlib.ActionLib;
import io.github.kituin.actionlib.ForgePluginFinder;
import io.github.kituin.actionlib.IActionRegisterApi;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;



/**
 * HoverEvent Mixin
 *
 * @author kitUIN
 */
@Mixin(HoverEvent.Action.class)
public abstract class HoverEventMixin {

    @Final
    @Mutable
    @Shadow
    public static Codec<HoverEvent.Action<?>> CODEC;
    @Shadow @Final public static HoverEvent.Action<HoverEvent.ItemStackInfo> SHOW_ITEM;
    @Shadow @Final public static HoverEvent.Action<HoverEvent.EntityTooltipInfo> SHOW_ENTITY;
    @Shadow @Final public static HoverEvent.Action<Component> SHOW_TEXT;

    @Unique
    private static DataResult<HoverEvent.Action<?>> actionlib$validate(@Nullable HoverEvent.Action<?> action) {
        if (action == null) {
            return DataResult.error(() -> "Unknown action");
        } else {
            return !action.isAllowedFromServer() ? DataResult.error(() -> "Action not allowed: " + action) : DataResult.success(action, Lifecycle.stable());
        }
    }
    @Inject(
            method = "<clinit>",
            at = @At(value = "RETURN"
            )
    )
    private static void injectedHoverEvent(CallbackInfo ci) {
        CODEC = ExtraCodecs.validate(StringRepresentable.fromValues(() ->
        {
            List<HoverEvent.Action> temp = Lists.newArrayList(SHOW_TEXT,SHOW_ITEM,SHOW_ENTITY);
            temp.addAll(actionlib$registerAll());
            ActionLib.LOGGER.info("Register All HoverEvent Counts: {}", temp.size());
            return temp.toArray(new HoverEvent.Action[temp.size() - 1]);
        }), (HoverEventMixin::actionlib$validate));

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