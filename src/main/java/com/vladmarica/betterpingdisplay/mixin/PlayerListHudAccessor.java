package com.vladmarica.betterpingdisplay.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerListHud.class)
public interface PlayerListHudAccessor {
  @Invoker
  void invokeRenderLatencyIcon(MatrixStack stack, int x, int offsetX, int y, PlayerListEntry player);

  @Invoker
  void invokeRenderScoreboardObjective(ScoreboardObjective obj, int i, String str, int j, int k, PlayerListEntry player, MatrixStack stack);

  @Accessor("header")
  Text getHeader();

  @Accessor("footer")
  Text getFooter();
}
