package com.vladmarica.betterpingdisplay.hud;

import com.vladmarica.betterpingdisplay.mixin.PlayerListHudAccessor;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;

public class PlayerListHudUtil {
  static void renderLatencyIcon(PlayerListHud hud, int x, int offsetX, int y, PlayerListEntry player) {
    ((PlayerListHudAccessor) hud).invokeRenderLatencyIcon(x, offsetX, y, player);
  }

  static void renderScoreboardObjective(PlayerListHud hud, ScoreboardObjective obj, int i, String str, int j, int k, PlayerListEntry player) {
    ((PlayerListHudAccessor) hud).invokeRenderScoreboardObjective(obj, i, str, j, k, player);
  }

  static Text getHeader(PlayerListHud hud) {
    return ((PlayerListHudAccessor) hud).getHeader();
  }

  static Text getFooter(PlayerListHud hud) {
    return ((PlayerListHudAccessor) hud).getFooter();
  }
}
