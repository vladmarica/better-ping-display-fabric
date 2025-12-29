package com.vladmarica.betterpingdisplay.integ;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.vladmarica.betterpingdisplay.BetterPingDisplayMod;
import com.vladmarica.betterpingdisplay.Config;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.gui.controllers.string.IStringController;
import dev.isxander.yacl3.gui.controllers.string.StringController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.vladmarica.betterpingdisplay.BetterPingDisplayMod.LOGGER;

public class YaclConfigScreenFactory implements ConfigScreenFactory {

    @Override
    public Screen create(Screen parent) {
        BetterPingDisplayMod mod = BetterPingDisplayMod.instance();
        Config config = mod.getConfig();

        Option<Color> pingTextColorOption =  Option.<Color>createBuilder()
                .name(Text.literal("pingTextColor"))
                .description(OptionDescription.of(Text.literal("Has no effect if \"autoColorPingText\" is enabled")))
                .binding(config.getTextColor(), config::getTextColor, config::setTextColor)
                .controller(o -> ColorControllerBuilder.create(o).allowAlpha(false))
                .available(!config.shouldAutoColorPingText())
                .build();

        Option<Boolean> autoColorPingTextOption = Option.<Boolean>createBuilder()
                .name(Text.literal("autoColorPingText"))
                .binding(
                        config.shouldAutoColorPingText(),
                        config::shouldAutoColorPingText,
                        config::setShouldAutoColorPingText)
                .controller(o -> BooleanControllerBuilder.create(o).coloured(true))
                .addListener((option, event) -> pingTextColorOption.setAvailable(!option.pendingValue()))
                .build();

        Option<String> textFormatOption = Option.<String>createBuilder()
                .name(Text.literal("pingTextFormat"))
                .binding(
                        config.getTextFormatString(),
                        config::getTextFormatString,
                        config::setTextFormatString)
                .controller(StringControllerBuilder::create)
                .customController((o) -> new ValidatedStringController(o, (s) -> s.contains("%d")))
                .build();

        Option<Boolean> renderPingBarsOption = Option.<Boolean>createBuilder()
                .name(Text.literal("renderPingBars"))
                .binding(
                        config.shouldRenderPingBars(),
                        config::shouldRenderPingBars,
                        config::setShouldRenderPingBars)
                .controller(o -> BooleanControllerBuilder.create(o).coloured(true))
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Better Ping Display"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Better Ping Display"))
                        .option(autoColorPingTextOption)
                        .option(pingTextColorOption)
                        .option(textFormatOption)
                        .option(renderPingBarsOption)
                        .build())
                .save(() -> {
                    try {
                        config.writeToFile(mod.getConfigFilePath().toFile());
                    } catch (IOException ex) {
                        LOGGER.warn("Failed to write config file", ex);
                    }
                })
                .build()
                .generateScreen(parent);
    }

    private static class ValidatedStringController extends StringController {
        private final Predicate<String> validator;

        public ValidatedStringController(Option<String> option, Predicate<String> validator) {
            super(option);
            this.validator = validator;
        }

        @Override
        public boolean isInputValid(String input) {
            return validator.test(input);
        }
    }
}
