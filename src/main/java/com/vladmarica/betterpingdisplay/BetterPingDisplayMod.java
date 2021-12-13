package com.vladmarica.betterpingdisplay;

import com.vladmarica.betterpingdisplay.Config.ConfigData;
import java.io.File;
import java.nio.file.Path;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterPingDisplayMod implements ModInitializer {
	public static final String MODID = "betterpingdisplay";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	private static final String CONFIG_FILE_NAME = MODID + ".json";
	private static BetterPingDisplayMod INSTANCE;

	private Config config = new Config();

	@Override
	public void onInitialize() {
		INSTANCE = this;

		Path configFilePath = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME);
		File configFile = configFilePath.toFile();
		if (configFile.exists()) {
			try {
				ConfigData data = Config.loadConfigFile(configFile);
				config = new Config(data);
				Config.writeConfigFile(configFile, data);
			} catch (Exception ex) {
				LOGGER.error("Failed to load config file, using default. Error: {}", ex.getMessage());
			}
		} else {
			try {
				LOGGER.warn("Could not find config file, creating a default one");
				Config.writeConfigFile(configFile, new ConfigData());
			} catch (Exception ex) {
				LOGGER.error("Failed to write default config file. Error: {}", ex.getMessage());
			}
		}

		LOGGER.info("BetterPingDisplay mod loaded");
	}

	public Config getConfig() {
		return config;
	}

	public static BetterPingDisplayMod instance() {
		return INSTANCE;
	}
}
