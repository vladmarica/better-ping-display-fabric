package com.vladmarica.betterpingdisplay.hud;

import net.minecraft.util.math.MathHelper;

public class PingColors {
  public static final int PING_START = 0;
  public static final int PING_MID = 150;
  public static final int PING_END = 300;

  public static final int COLOR_GREY = 0x535353;
  public static final int COLOR_START = 0x00E676;
  public static final int COLOR_MID = 0xD6CD30;
  public static final int COLOR_END = 0xE53935;

  public static int getColor(int ping) {
    if (ping < PING_START) {
      return COLOR_GREY;
    }

    if (ping < PING_MID) {
      return ColorUtil.interpolate(
          COLOR_START,
          COLOR_MID,
          computeOffset(PING_START, PING_MID, ping));
    }

    return ColorUtil.interpolate(
        COLOR_MID,
        COLOR_END,
        computeOffset(PING_MID, PING_END, Math.min(ping, PING_END)));
  }

  static float computeOffset(int start, int end, int value) {
    float offset =  (value - start) / (float) ( end - start);
    return MathHelper.clamp(offset, 0.0F, 1.0F);
  }
}
