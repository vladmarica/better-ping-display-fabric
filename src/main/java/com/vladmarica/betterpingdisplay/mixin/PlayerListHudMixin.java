package com.vladmarica.betterpingdisplay.mixin;

import com.vladmarica.betterpingdisplay.hud.CustomPlayerListHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
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
	 * Increases the int constant {@code 13} in the {@link PlayerListHud#render} method by
	 * {@value #PLAYER_SLOT_EXTRA_WIDTH}. This constant is used to define the width of the "slots" in the player list.
	 * In order to fit the ping text, this needs to be increased.
	 */
	@ModifyConstant(method = "render", constant = @Constant(intValue = 13))
	private int modifySlotWidthConstant(int original) {
		return original + PLAYER_SLOT_EXTRA_WIDTH;
	}

	/**
	 * Redirects the call to {@code renderLatencyIcon} in {@link PlayerListHud#render} to instead call
	 * {@link CustomPlayerListHud#renderPingDisplay}.
	 */
	@Redirect(method = "render",
			at = @At(value = "INVOKE", target = "net/minecraft/client/gui/hud/PlayerListHud.renderLatencyIcon(Lnet/minecraft/client/util/math/MatrixStack;IIILnet/minecraft/client/network/PlayerListEntry;)V"))
	private void redirectRenderLatencyIconCall(
			PlayerListHud instance, MatrixStack matrices, int width, int x, int y, @NotNull PlayerListEntry entry) {
		CustomPlayerListHud.renderPingDisplay(client, instance, matrices, width, x, y, entry);
	}
}
