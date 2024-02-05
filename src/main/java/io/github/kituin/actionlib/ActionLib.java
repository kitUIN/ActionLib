package io.github.kituin.actionlib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Mod("actionlib")
public class ActionLib {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ActionLib() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
