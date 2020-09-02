package com.vladmarica.betterpingdisplay;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterPingDisplayMod implements ModInitializer {
	public static final String MODID = "betterpingdisplay";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("BetterPingDisplay mod loaded");
	}
}
