package com.vladmarica.betterpingdisplay;

import com.google.gson.*;
import com.google.gson.annotations.Expose;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

public class Config {
  private static final int DEFAULT_PING_TEXT_COLOR = 0xFFA0A0A0;
  private static final String DEFAULT_PING_TEXT_FORMAT = "%dms";

  private static final Gson gson = new GsonBuilder()
          .setPrettyPrinting()
          .registerTypeAdapter(Color.class, new ColorJsonAdapter())
          .create();

  private final ConfigData data;

  private Config(ConfigData configData) {
    data = configData;

    if (!data.pingTextFormatString.contains("%d")) {
      data.pingTextFormatString = DEFAULT_PING_TEXT_FORMAT;
    }
  }

  public Color getTextColor() {
    return data.pingTextColor;
  }

  public void setTextColor(Color color) {
    data.pingTextColor = color;
  }

  public String getTextFormatString() {
    return data.pingTextFormatString;
  }

  public void setTextFormatString(String textFormatString) {
    data.pingTextFormatString = textFormatString;
  }

  public boolean shouldAutoColorPingText() {
    return data.autoColorPingText;
  }

  public void setShouldAutoColorPingText(boolean shouldAutoColorPingText) {
    data.autoColorPingText = shouldAutoColorPingText;
  }

  public boolean shouldRenderPingBars() {
    return data.renderPingBars;
  }

  public void setShouldRenderPingBars(boolean shouldRenderPingBars) {
    data.renderPingBars = shouldRenderPingBars;
  }

  public void writeToFile(File file) throws IOException {
    try (FileWriter writer = new FileWriter(file)) {
      writer.write(gson.toJson(data));
    }
  }

  public static Config fromDefault() {
    return new Config(new ConfigData());
  }

  public static Config fromFile(File file) throws IOException {
    try (FileReader reader = new FileReader(file)) {
      return new Config(gson.fromJson(reader, ConfigData.class));
    }
  }

  private static class ConfigData implements Serializable {
    @Expose
    private boolean autoColorPingText = true;

    @Expose
    private boolean renderPingBars = false;

    @Expose
    private Color pingTextColor = new Color(DEFAULT_PING_TEXT_COLOR);

    @Expose
    private String pingTextFormatString = DEFAULT_PING_TEXT_FORMAT;
  }

  private static class ColorJsonAdapter implements JsonDeserializer<Color>, JsonSerializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
      String str = json.getAsJsonPrimitive().getAsString();
      return new Color(Integer.parseInt(str.substring(1), 16));
    }

    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(String.format("#%02x%02x%02x", src.getRed(), src.getGreen(), src.getBlue()));
    }
  }
}
