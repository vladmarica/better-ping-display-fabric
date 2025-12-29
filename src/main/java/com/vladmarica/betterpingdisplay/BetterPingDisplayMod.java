package com.vladmarica.betterpingdisplay;

import java.io.File;
import java.io.IOException;
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

	private Path configFilePath;
	private Config config = Config.fromDefault();

	@Override
	public void onInitialize() {
		INSTANCE = this;

		configFilePath = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME);
		File configFile = configFilePath.toFile();
		if (configFile.exists()) {
			try {
				config = Config.fromFile(configFile);
				config.writeToFile(configFile); // Re-write to file to add new fields
			} catch (IOException ex) {
				LOGGER.error("Failed to load config file", ex);
			}
		} else {
			try {
				LOGGER.warn("Could not find config file, creating a default one");
				config.writeToFile(configFile);
			} catch (IOException ex) {
				LOGGER.error("Failed to write default config file", ex);
			}
		}

		LOGGER.info("BetterPingDisplay mod loaded");
	}

	public Config getConfig() {
		return config;
	}

	public Path getConfigFilePath() {
		return configFilePath;
	}

	public static BetterPingDisplayMod instance() {
		return INSTANCE;
	}
}
