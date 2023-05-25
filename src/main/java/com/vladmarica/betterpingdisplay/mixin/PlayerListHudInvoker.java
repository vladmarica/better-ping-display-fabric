package com.vladmarica.betterpingdisplay.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerListHud.class)
public interface PlayerListHudInvoker {
  @Invoker("renderLatencyIcon")
  void invokeRenderLatencyIcon(DrawContext context, int width, int x, int y, PlayerListEntry entry);
}
