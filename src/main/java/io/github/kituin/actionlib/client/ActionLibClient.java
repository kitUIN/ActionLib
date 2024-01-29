package io.github.kituin.actionlib.client;

import io.github.kituin.actionlib.ActionRegisterApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.kituin.actionlib.ActionRegister.HOVER_EVENT_ACTIONS;

public class ActionLibClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getEntrypointContainers("acitonlib", ActionRegisterApi.class).forEach(entrypoint -> {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            String modId = metadata.getId();
            try {
                ActionRegisterApi api = entrypoint.getEntrypoint();
                HOVER_EVENT_ACTIONS.addAll(api.registerHoverEventAction());
            } catch (Throwable e) {
                LOGGER.error("Mod {} provides a broken implementation of ActionRegisterApi", modId, e);
            }
        });
    }
}
