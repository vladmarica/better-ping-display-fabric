package com.vladmarica.betterpingdisplay;

import com.vladmarica.betterpingdisplay.config.Config;
import com.vladmarica.betterpingdisplay.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterPingDisplayMod implements ModInitializer {
	public static final String MODID = "betterpingdisplay";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	private static BetterPingDisplayMod INSTANCE;

	private ConfigManager configManager = new ConfigManager();
	private Config config;

	@Override
	public void onInitialize() {
		INSTANCE = this;

		configManager = new ConfigManager();
		config = configManager.getConfig();

		LOGGER.info("BetterPingDisplay mod loaded");
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public Config getConfig() {
		return config;
	}

	public static BetterPingDisplayMod instance() {
		return INSTANCE;
	}
}
