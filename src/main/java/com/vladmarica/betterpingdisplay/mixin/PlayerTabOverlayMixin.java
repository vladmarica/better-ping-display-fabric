package com.vladmarica.betterpingdisplay.mixin;

import com.vladmarica.betterpingdisplay.hud.CustomPlayerListHud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerTabOverlay.class)
public abstract class PlayerTabOverlayMixin {
	@Unique
	@Final
	private static final int PLAYER_SLOT_EXTRA_WIDTH = 45;

	@Shadow
	@Final
	private Minecraft minecraft;

	/**
	 * Increases the int constant {@code 13} in the {@link PlayerTabOverlay#extractRenderState(GuiGraphicsExtractor, int, Scoreboard, Objective)} method by
	 * {@value #PLAYER_SLOT_EXTRA_WIDTH}. This constant is used to define the width of the "slots" in the player list.
	 * In order to fit the ping text, this needs to be increased.
	 */
	@ModifyConstant(method = "extractRenderState", constant = @Constant(intValue = 13))
	private int modifySlotWidthConstant(int original) {
		return original + PLAYER_SLOT_EXTRA_WIDTH;
	}

	/**
	 * Redirects the call to {@link PlayerTabOverlay#extractRenderState(GuiGraphicsExtractor, int, Scoreboard, Objective)} in {@link PlayerTabOverlay#extractRenderState(GuiGraphicsExtractor, int, Scoreboard, Objective)} to instead call
	 * {@link CustomPlayerListHud#renderPingDisplay}.
	 */
	@Redirect(method = "extractRenderState",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/PlayerTabOverlay;extractPingIcon(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIILnet/minecraft/client/multiplayer/PlayerInfo;)V"))
	private void redirectRenderLatencyIconCall(
			PlayerTabOverlay instance, GuiGraphicsExtractor context, int width, int x, int y, @NotNull PlayerInfo entry) {
		CustomPlayerListHud.renderPingDisplay(minecraft, instance, context, width, x, y, entry);
	}
}
