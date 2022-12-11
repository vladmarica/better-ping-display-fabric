/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package com.vladmarica.betterpingdisplay.config;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Config implements Serializable {
    @Expose
    private boolean autoColorPingText = true;

    @Expose
    private boolean renderPingBars = false;

    @Expose
    private String pingTextColor = "#A0A0A0";

    @Expose
    private String pingTextFormatString = "%dms";

    private int textColor = Integer.parseInt("A0A0A0", 16);

    public boolean shouldAutoColorPingText() {
        return autoColorPingText;
    }

    public void setAutoColorPingText(boolean autoColorPingText) {
        this.autoColorPingText = autoColorPingText;
    }

    public boolean shouldRenderPingBars() {
        return renderPingBars;
    }

    public void setRenderPingBars(boolean renderPingBars) {
        this.renderPingBars = renderPingBars;
    }

    public String getPingTextColor() {
        return pingTextColor;
    }

    public String getPingTextFormat() {
        return pingTextFormatString;
    }

    public void setPingTextFormat(String pingTextFormat) {
        this.pingTextFormatString = pingTextFormat;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.pingTextColor = "#" + Integer.toHexString(textColor);
        this.textColor = textColor;
    }
}
