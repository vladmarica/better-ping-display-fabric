/*
 * Copyright (c) 2022 DenaryDev
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package com.vladmarica.betterpingdisplay.gui;

import com.vladmarica.betterpingdisplay.BetterPingDisplayMod;
import com.vladmarica.betterpingdisplay.config.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

public class ConfigScreenBuilder {

    private static final Function<Boolean, Text> yesNoSupplier = bool -> {
        if (bool) return Text.translatable("label.betterpingdisplay.on");
        else return Text.translatable("label.betterpingdisplay.off");
    };

    public static Screen build(Screen parent) {
        final var defaults = new Config();
        final var current = BetterPingDisplayMod.instance().getConfig();

        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.betterpingdisplay.config"))
                .transparentBackground()
                .setDoesConfirmSave(true)
                .setSavingRunnable(() -> BetterPingDisplayMod.instance().getConfigManager().writeConfig(true));

        final var entryBuilder = builder.entryBuilder();
        final var category = builder.getOrCreateCategory(Text.translatable("category.betterpingdisplay.main"));

        final var toggleAutoColorPingText = entryBuilder.startBooleanToggle(Text.translatable("options.betterpingdisplay.autoColorPingText"), current.shouldAutoColorPingText())
                .setDefaultValue(defaults.shouldAutoColorPingText())
                .setYesNoTextSupplier(yesNoSupplier)
                .setTooltip(getTooltip("autoColorPingText"))
                .setSaveConsumer(current::setAutoColorPingText)
                .build();

        final var toggleRenderPingBars = entryBuilder.startBooleanToggle(Text.translatable("options.betterpingdisplay.renderPingBars"), current.shouldRenderPingBars())
                .setDefaultValue(defaults.shouldRenderPingBars())
                .setYesNoTextSupplier(yesNoSupplier)
                .setTooltip(getTooltip("renderPingBars"))
                .setSaveConsumer(current::setRenderPingBars)
                .build();

        final var setPingTextColor = entryBuilder.startColorField(Text.translatable("options.betterpingdisplay.pingTextColor"), current.getTextColor())
                .setDefaultValue(defaults.getTextColor())
                .setTooltip(getTooltip("pingTextColor"))
                .setSaveConsumer(current::setTextColor)
                .build();

        final var setPingTextFormat = entryBuilder.startStrField(Text.translatable("options.betterpingdisplay.pingTextFormat"), current.getPingTextFormat())
                .setDefaultValue(defaults.getPingTextFormat())
                .setTooltip(getTooltip("pingTextFormat"))
                .setErrorSupplier(val -> {
                    if (!val.contains("%d")) {
                        return Optional.of(Text.translatable("options.betterpingdisplay.pingTextFormat.error"));
                    }
                    return Optional.empty();
                })
                .setSaveConsumer(current::setPingTextFormat)
                .build();

        category.addEntry(toggleAutoColorPingText);
        category.addEntry(toggleRenderPingBars);
        category.addEntry(setPingTextColor);
        category.addEntry(setPingTextFormat);

        return builder.build();
    }

    private static Text[] getTooltip(String key) {
        final var list = new ArrayList<Text>();

        for (int i = 0; i < 10; i++) {
            String finalKey = key + ".tooltip." + (i + 1);
            String value = I18n.translate(finalKey);

            if (value.equals(finalKey)) break;

            list.add(Text.of(value));
        }

        return list.size() == 0 ? null : list.toArray(new Text[0]);
    }
}
