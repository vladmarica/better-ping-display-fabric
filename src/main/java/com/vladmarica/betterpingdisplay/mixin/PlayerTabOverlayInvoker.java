package com.vladmarica.betterpingdisplay.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerTabOverlay.class)
public interface PlayerTabOverlayInvoker {
  @Invoker("extractPingIcon")
  void invokeRenderLatencyIcon(GuiGraphicsExtractor context, int width, int x, int y, PlayerInfo entry);
}
