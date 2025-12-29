package com.vladmarica.betterpingdisplay.integ;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.util.NullScreenFactory;
import net.fabricmc.loader.api.FabricLoader;
import static com.vladmarica.betterpingdisplay.BetterPingDisplayMod.LOGGER;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return new YaclConfigScreenFactory();
        }

        LOGGER.info("YACL is not installed, GUI config will not be available");
        return new NullScreenFactory<>();
    }
}
