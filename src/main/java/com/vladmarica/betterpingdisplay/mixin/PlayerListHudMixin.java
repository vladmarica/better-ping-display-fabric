package com.vladmarica.betterpingdisplay.mixin;

import com.vladmarica.betterpingdisplay.hud.CustomPlayerListHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
	@Unique
	@Final
	private static final int PLAYER_SLOT_EXTRA_WIDTH = 45;

	@Shadow
	@Final
	private MinecraftClient client;

	/**
	 * Adds {@value #PLAYER_SLOT_EXTRA_WIDTH} to the scoreboard column width calculated inside
	 * {@link PlayerListHud#render}. The scoreboard column width participates in the overall slot width even when no
	 * scoreboard is shown, so this keeps our ping text area without rewriting literal constants that other mods rely
	 * on.
	 */
	@ModifyVariable(method = "render", at = @At(value = "STORE"), index = 14, require = 0)
	private int expandScoreboardColumnWidth(int original) {
		return original + PLAYER_SLOT_EXTRA_WIDTH;
	}

	/**
	 * Redirects the call to {@code renderLatencyIcon} in {@link PlayerListHud#render} to instead call
	 * {@link CustomPlayerListHud#renderPingDisplay}.
	 */
	@Redirect(method = "render",
			at = @At(value = "INVOKE", target = "net/minecraft/client/gui/hud/PlayerListHud.renderLatencyIcon(Lnet/minecraft/client/gui/DrawContext;IIILnet/minecraft/client/network/PlayerListEntry;)V"))
	private void redirectRenderLatencyIconCall(
			PlayerListHud instance, DrawContext context, int width, int x, int y, @NotNull PlayerListEntry entry) {
		CustomPlayerListHud.renderPingDisplay(client, instance, context, width, x, y, entry);
	}
}
