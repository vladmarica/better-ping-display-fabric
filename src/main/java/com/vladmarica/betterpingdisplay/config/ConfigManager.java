package com.vladmarica.betterpingdisplay.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vladmarica.betterpingdisplay.BetterPingDisplayMod;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConfigManager {

    private Config config;

    private final Gson gson;
    private final File configFile;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public ConfigManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        this.configFile = new File(FabricLoader.getInstance().getConfigDir().toString(), BetterPingDisplayMod.MODID + ".json");
        readConfig(false);
    }

    public Config getConfig() {
        return config;
    }

    public void readConfig(boolean async) {
        Runnable task = () -> {
            try {
                if (configFile.exists()) {
                    String content = FileUtils.readFileToString(configFile, Charset.defaultCharset());
                    config = gson.fromJson(content, Config.class);

                    if (config.getPingTextColor().startsWith("#")) {
                        try {
                            config.setTextColor(Integer.parseInt(config.getPingTextColor().substring(1), 16));
                        } catch (NumberFormatException ex) {
                            BetterPingDisplayMod.LOGGER.error("Config option 'pingTextColor' is invalid - it must be a hex color code");
                        }
                    } else {
                        BetterPingDisplayMod.LOGGER.error("Config option 'pingTextColor' is invalid - it must be a hex color code");
                    }

                    if (!config.getPingTextFormat().contains("%d")) {
                        config.setPingTextFormat("");
                        BetterPingDisplayMod.LOGGER.error("Config option 'pingTextFormatString' is invalid - it needs to contain %d");
                    }
                } else {
                    writeNewConfig();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                writeNewConfig();
            }
        };

        if (async) executor.execute(task);
        else task.run();
    }

    public void writeNewConfig() {
        config = new Config();
        writeConfig(false);
    }

    public void writeConfig(boolean async) {
        Runnable task = () -> {
            try {
                if (config != null) {
                    String serialized = gson.toJson(config);
                    FileUtils.writeStringToFile(configFile, serialized, Charset.defaultCharset());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (async) executor.execute(task);
        else task.run();
    }
}
