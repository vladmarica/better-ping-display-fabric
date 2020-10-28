package com.vladmarica.betterpingdisplay.hud;

public class ColorUtil {

  public static int interpolate(int colorStart, int colorEnd, float offset) {
    if (offset < 0 || offset > 1) {
      throw new IllegalArgumentException("Offset must be between 0.0 and 1.0");
    }

    int redDiff = getRed(colorEnd) - getRed(colorStart);
    int greenDiff = getGreen(colorEnd) - getGreen(colorStart);
    int blueDiff = getBlue(colorEnd) - getBlue(colorStart);

    int newRed = Math.round(getRed(colorStart) + (redDiff * offset));
    int newGreen = Math.round(getGreen(colorStart) + (greenDiff * offset));
    int newBlue = Math.round(getBlue(colorStart) + (blueDiff * offset));

    return (newRed << 16) | (newGreen << 8) | newBlue;
  }

  static int getRed(int color) {
    return (color >> 16) & 0xFF;
  }

  static int getGreen(int color) {
    return (color >> 8) & 0xFF;
  }

  static int getBlue(int color) {
    return color & 0xFF;
  }
}
