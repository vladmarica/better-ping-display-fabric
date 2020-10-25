package com.vladmarica.betterpingdisplay.hud;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.vladmarica.betterpingdisplay.BetterPingDisplayMod;
import com.vladmarica.betterpingdisplay.Config;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

public final class CustomPlayerListHud {

  private static final Ordering<PlayerListEntry> ENTRY_ORDERING = Ordering.from(new EntryOrderComparator());
  private static final int PING_TEXT_RENDER_OFFSET = -13;
  private static final int PLAYER_SLOT_EXTRA_WIDTH = 45;
  private static final int PLAYER_ICON_WIDTH = 9;
  private static final int PING_BARS_WIDTH = 11;

  public static void render(PlayerListHud hud, int width, Scoreboard scoreboard, ScoreboardObjective obj) {
    MinecraftClient mc = MinecraftClient.getInstance();
    TextRenderer textRenderer = mc.textRenderer;
    Text header = PlayerListHudUtil.getHeader(hud);
    Text footer = PlayerListHudUtil.getFooter(hud);
    Config config = BetterPingDisplayMod.instance().getConfig();

    ClientPlayNetworkHandler clientPlayNetworkHandler = mc.player.networkHandler;
    List<PlayerListEntry> playerList = ENTRY_ORDERING.sortedCopy(clientPlayNetworkHandler.getPlayerList());
    int i = 0;
    int j = 0;
    Iterator playerListIterator = playerList.iterator();

    int n;
    while(playerListIterator.hasNext()) {
      PlayerListEntry playerListEntry = (PlayerListEntry)playerListIterator.next();
      n = mc.textRenderer.getStringWidth(hud.getPlayerName(playerListEntry).asFormattedString());
      i = Math.max(i, n);
      if (obj != null && obj.getRenderType() != ScoreboardCriterion.RenderType.HEARTS) {
        n = textRenderer.getStringWidth(" " + scoreboard.getPlayerScore(playerListEntry.getProfile().getName(), obj).getScore());
        j = Math.max(j, n);
      }
    }

    playerList = playerList.subList(0, Math.min(playerList.size(), 80));
    int l = playerList.size();
    int m = l;

    for(n = 1; m > 20; m = (l + n - 1) / n) {
      ++n;
    }

    boolean displayPlayerIcons = mc.isInSingleplayer() || mc.getNetworkHandler().getConnection().isEncrypted();
    int q;
    if (obj != null) {
      if (obj.getRenderType() == ScoreboardCriterion.RenderType.HEARTS) {
        q = 90;
      } else {
        q = j;
      }
    } else {
      q = 0;
    }
    int r = Math.min(n * ((displayPlayerIcons ? PLAYER_ICON_WIDTH : 0) + i + q + 13 + PLAYER_SLOT_EXTRA_WIDTH), width - 50) / n;
    int s = width / 2 - (r * n + (n - 1) * 5) / 2;
    int t = 10;
    int u = r * n + (n - 1) * 5;
    List<String> list2 = null;
    if (header != null) {
      list2 = mc.textRenderer.wrapStringToWidthAsList(header.asFormattedString(), width - 50);

      String string;
      for(Iterator var18 = list2.iterator(); var18.hasNext(); u = Math.max(u, mc.textRenderer.getStringWidth(string))) {
        string = (String)var18.next();
      }
    }

    List<String> list3 = null;
    String string3;
    Iterator var36;
    if (footer != null) {
      list3 = mc.textRenderer.wrapStringToWidthAsList(footer.asFormattedString(), width - 50);

      for(var36 = list3.iterator(); var36.hasNext(); u = Math.max(u, mc.textRenderer.getStringWidth(string3))) {
        string3 = (String)var36.next();
      }
    }

    int var10000;
    int var10001;
    int var10002;
    int var10004;
    int y;
    if (list2 != null) {
      var10000 = width / 2 - u / 2 - 1;
      var10001 = t - 1;
      var10002 = width / 2 + u / 2 + 1;
      var10004 = list2.size();
      DrawableHelper.fill(var10000, var10001, var10002, t + var10004 * 9, Integer.MIN_VALUE);

      for(var36 = list2.iterator(); var36.hasNext(); t += 9) {
        string3 = (String)var36.next();
        y = mc.textRenderer.getStringWidth(string3);
        mc.textRenderer.drawWithShadow(string3, (float)(width / 2 - y / 2), (float)t, -1);
      }

      ++t;
    }

    DrawableHelper.fill(width / 2 - u / 2 - 1, t - 1, width / 2 + u / 2 + 1, t + m * 9, Integer.MIN_VALUE);
    int w = mc.options.getTextBackgroundColor(553648127);

    int ai;
    for(int x = 0; x < l; ++x) {
      y = x / m;
      ai = x % m;
      int aa = s + y * r + y * 5;
      int ab = t + ai * 9;
      DrawableHelper.fill(aa, ab, aa + r, ab + 8, w);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      if (x < playerList.size()) {
        PlayerListEntry player = playerList.get(x);
        GameProfile gameProfile = player.getProfile();
        int ah;
        if (displayPlayerIcons) {
          PlayerEntity playerEntity = mc.world.getPlayerByUuid(gameProfile.getId());
          boolean bl2 = playerEntity != null && playerEntity.isPartVisible(PlayerModelPart.CAPE) && ("Dinnerbone".equals(gameProfile.getName()) || "Grumm".equals(gameProfile.getName()));
          mc.getTextureManager().bindTexture(player.getSkinTexture());
          ah = 8 + (bl2 ? 8 : 0);
          int ad = 8 * (bl2 ? -1 : 1);
          DrawableHelper.blit(aa, ab, 8, 8, 8.0F, (float)ah, 8, ad, 64, 64);
          if (playerEntity != null && playerEntity.isPartVisible(PlayerModelPart.HAT)) {
            int ae = 8 + (bl2 ? 8 : 0);
            int af = 8 * (bl2 ? -1 : 1);
            DrawableHelper.blit(aa, ab, 8, 8, 40.0F, (float)ae, 8, af, 64, 64);
          }

          aa += 9;
        }

        String string4 = hud.getPlayerName(player).asFormattedString();
        if (player.getGameMode() == GameMode.SPECTATOR) {
          mc.textRenderer.drawWithShadow(Formatting.ITALIC + string4, (float)aa, (float)ab, -1862270977);
        } else {
          mc.textRenderer.drawWithShadow(string4, (float)aa, (float)ab, -1);
        }

        if (obj != null && player.getGameMode() != GameMode.SPECTATOR) {
          int ag = aa + i + 1;
          ah = ag + q;
          if (ah - ag > 5) {
            PlayerListHudUtil.renderScoreboardObjective(hud, obj, ab, gameProfile.getName(), ag, ah, player);
          }
        }

        // Here is the magic, rendering the ping text
        String pingString = String.format(config.getTextFormatString(), player.getLatency());
        int pingStringWidth = textRenderer.getStringWidth(pingString);
        int textX = r + aa - pingStringWidth + PING_TEXT_RENDER_OFFSET;

        if (displayPlayerIcons) {
          textX -= PLAYER_ICON_WIDTH;
        }

        if (!config.shouldRenderPingBars()) {
          textX += PING_BARS_WIDTH;
        }

        int pingTextColor = config.shouldAutoColorPingText()
            ? PingColors.getColor(player.getLatency())
            : config.getTextColor();

        textRenderer.drawWithShadow(pingString, (float) textX, (float) ab, pingTextColor);

        if (config.shouldRenderPingBars()) {
          PlayerListHudUtil.renderLatencyIcon(
              hud, r, aa - (displayPlayerIcons ? PLAYER_ICON_WIDTH : 0), ab, player);
        } else {
          // If we don't render ping bars, we need to reset the render system color so the rest
          // of the player list renders properly
          RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
      }
    }

    if (list3 != null) {
      t += m * 9 + 1;
      var10000 = width / 2 - u / 2 - 1;
      var10001 = t - 1;
      var10002 = width / 2 + u / 2 + 1;
      var10004 = list3.size();
      DrawableHelper.fill(var10000, var10001, var10002, t + var10004 * 9, Integer.MIN_VALUE);

      for(Iterator var39 = list3.iterator(); var39.hasNext(); t += 9) {
        String string5 = (String)var39.next();
        ai = textRenderer.getStringWidth(string5);
        textRenderer.drawWithShadow(string5, (float)(width / 2 - ai / 2), (float)t, -1);
      }
    }
  }

  @Environment(EnvType.CLIENT)
  static class EntryOrderComparator implements Comparator<PlayerListEntry> {
    public int compare(PlayerListEntry p1, PlayerListEntry p2) {
      Team team = p1.getScoreboardTeam();
      Team team2 = p2.getScoreboardTeam();
      return ComparisonChain.start()
          .compareTrueFirst(p1.getGameMode() != GameMode.SPECTATOR, p2.getGameMode() != GameMode.SPECTATOR)
          .compare(team != null ? team.getName() : "", team2 != null ? team2.getName() : "")
          .compare(p1.getProfile().getName(), p2.getProfile().getName(), String::compareToIgnoreCase)
          .result();
    }
  }
}
