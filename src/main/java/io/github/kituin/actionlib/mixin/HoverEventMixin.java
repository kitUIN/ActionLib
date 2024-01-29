package io.github.kituin.actionlib.mixin;

import com.google.common.collect.Lists;
import io.github.kituin.actionlib.ActionLib;
import io.github.kituin.actionlib.ActionRegisterApi;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.text.HoverEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Arrays;
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

    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE",
                target = "Ljava/util/stream/Stream;of([Ljava/lang/Object;)Ljava/util/stream/Stream;",
                remap = false
            )
    )
    private static Object[] injectedHoverEvent(Object[] values) {
        ArrayList<Object> temp = new ArrayList<>(Arrays.asList(values));
        temp.addAll(registerAll());

        ActionLib.LOGGER.info("Register All HoverEvent Counts: {}", temp.size());
        return temp.toArray();
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
