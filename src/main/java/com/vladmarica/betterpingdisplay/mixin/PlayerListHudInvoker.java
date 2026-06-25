package com.vladmarica.betterpingdisplay.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerTabOverlay.class)
public interface PlayerListHudInvoker {
  @Invoker("renderPingIcon")
  void invokeRenderLatencyIcon(GuiGraphics context, int width, int x, int y, PlayerInfo entry);
}