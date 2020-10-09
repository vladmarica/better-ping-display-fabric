package com.vladmarica.betterpingdisplay.hud;


// start: #00e676
// mid #d6cd30
// end #e53935
public class PingColors {
  public static int getColor(int ping) {
    if (ping < 0) {
      return 0x535353;
    }
    if (ping < 150) {
      return 0x00E676;
    }
    if (ping < 300) {
      return 0xC6FF00;
    }
  }
}
