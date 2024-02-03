package io.github.kituin.actionlib.mixin;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import io.github.kituin.actionlib.ActionLib;
import io.github.kituin.actionlib.ActionRegisterApi;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static io.github.kituin.actionlib.ActionLib.MOD_ID;


/**
 * HoverEvent Mixin
 *
 * @author kitUIN
 * @date  2024/01/29
 */
@Mixin(HoverEvent.Action.class)
public abstract class HoverEventMixin {
    @Final
    @Mutable
    @Shadow
    public static Codec<HoverEvent.Action<?>> CODEC;
    @Shadow @Final public static HoverEvent.Action<HoverEvent.ItemStackContent> SHOW_ITEM;
    @Shadow @Final public static HoverEvent.Action<HoverEvent.EntityContent> SHOW_ENTITY;
    @Shadow @Final public static HoverEvent.Action<Text> SHOW_TEXT;

    @Unique
    private static DataResult<HoverEvent.Action<?>> validate(@Nullable HoverEvent.Action<?> action) {
        if (action == null) {
            return DataResult.error(() -> "Unknown action");
        } else {
            return !action.isParsable() ? DataResult.error(() -> "Action not allowed: " + action) : DataResult.success(action, Lifecycle.stable());
        }
    }
    @Inject(
            method = "<clinit>",
            at = @At(value = "RETURN"
                    )
    )
    private static void injectedHoverEvent(CallbackInfo ci) {
        CODEC = Codecs.validate(StringIdentifiable.createBasicCodec(() ->
        {
            List<HoverEvent.Action> temp = Lists.newArrayList(SHOW_TEXT,SHOW_ITEM,SHOW_ENTITY);
            temp.addAll(registerAll());
            ActionLib.LOGGER.info("Register All HoverEvent Counts: {}", temp.size());
            return temp.toArray(new HoverEvent.Action[temp.size() - 1]);
        }), (HoverEventMixin::validate));

    }

    /**
     * registerAll From Mod
     *
     * @return {@link List}<{@link HoverEvent.Action}>
     */
    @Unique
    private static List<HoverEvent.Action> registerAll()
    {
        List<HoverEvent.Action> hoverEventActions = Lists.newArrayList();
        FabricLoader.getInstance().getEntrypointContainers(MOD_ID, ActionRegisterApi.class).forEach(entrypoint -> {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            String modId = metadata.getId();
            try {
                ActionRegisterApi api = entrypoint.getEntrypoint();
                List<HoverEvent.Action> actions = api.registerHoverEventAction();
                hoverEventActions.addAll(actions);
                ActionLib.LOGGER.info("Mod {} Add {} New HoverEvent",modId,actions.size());
            } catch (Throwable e) {
                ActionLib.LOGGER.error("Mod {} provides a broken implementation of ActionRegisterApi", modId, e);
            }
        });
        return hoverEventActions;
    }
}
