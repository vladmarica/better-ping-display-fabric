package com.vladmarica.betterpingdisplay.mixin;

import com.mojang.authlib.GameProfile;
import com.vladmarica.betterpingdisplay.BetterPingDisplayMod;
import com.vladmarica.betterpingdisplay.Config;
import com.vladmarica.betterpingdisplay.hud.CustomPlayerListHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Arrays;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
	@Unique
	@Final
	private static final int PLAYER_SLOT_EXTRA_WIDTH = 45;

	@Unique
	@Final
	private final Config config = BetterPingDisplayMod.instance().getConfig();

	@Shadow
	@Final
	private MinecraftClient client;

	/**
		* Increases the int constant {@code 13} in the {@link PlayerListHud#render} method by
		* {@value #PLAYER_SLOT_EXTRA_WIDTH}. This constant is used to define the width of the "slots" in the player list.
		* In order to fit the ping text, this needs to be increased.
		*/
	@ModifyConstant(method = "render", constant = @Constant(intValue = 13))
	private int modifySlotWidthConstant(int original) {
		ServerInfo d = MinecraftClient.getInstance().getCurrentServerEntry();
		String serverIp = d == null ? "*" : d.address;
		if (Arrays.asList(config.getBlacklistServers()).contains(serverIp)) return original;
		return original + PLAYER_SLOT_EXTRA_WIDTH;
	}

	/**
		* Redirects the call to {@code renderLatencyIcon} in {@link PlayerListHud#render} to instead call
		* {@link CustomPlayerListHud#renderPingDisplay}.
		*/
	@Inject(method = "renderLatencyIcon", at = @At(value = "HEAD"), cancellable = true)
	private void redirectRenderLatencyIconCall(DrawContext context, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci) {
		GameProfile userProfile = entry.getProfile();
		String username = userProfile == null ? "*" : userProfile.getName();
		if (Arrays.asList(config.getBlacklistPlayers()).contains(username)) return;

		ServerInfo d = MinecraftClient.getInstance().getCurrentServerEntry();
		String serverIp = d == null ? "*" : d.address;
		if (Arrays.asList(config.getBlacklistServers()).contains(serverIp)) return;

		CustomPlayerListHud.renderPingDisplay(client, (PlayerListHud) (Object) this, context, width, x, y, entry);
		ci.cancel();
	}
}
