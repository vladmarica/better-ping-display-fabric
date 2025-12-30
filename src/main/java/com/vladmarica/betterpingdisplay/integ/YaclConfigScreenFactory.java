package com.vladmarica.betterpingdisplay.integ;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.vladmarica.betterpingdisplay.BetterPingDisplayMod;
import com.vladmarica.betterpingdisplay.Config;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.gui.controllers.string.StringController;
import net.minecraft.client.gui.screen.Screen;

import java.awt.Color;
import java.io.IOException;
import java.util.function.Predicate;

import static com.vladmarica.betterpingdisplay.BetterPingDisplayMod.LOGGER;
import static net.minecraft.text.Text.translatable;

public class YaclConfigScreenFactory implements ConfigScreenFactory<Screen> {

    @Override
    public Screen create(Screen parent) {
        BetterPingDisplayMod mod = BetterPingDisplayMod.instance();
        Config config = mod.getConfig();

        Option<Color> pingTextColorOption =  Option.<Color>createBuilder()
                .name(translatable("betterpingdisplay.settings.pingTextColor"))
                .description(OptionDescription.of(translatable("betterpingdisplay.settings.pingTextColor.description")))
                .binding(config.getTextColor(), config::getTextColor, config::setTextColor)
                .controller(o -> ColorControllerBuilder.create(o).allowAlpha(false))
                .available(!config.shouldAutoColorPingText())
                .build();

        Option<Boolean> autoColorPingTextOption = Option.<Boolean>createBuilder()
                .name(translatable("betterpingdisplay.settings.autoColorPingText"))
                .description(OptionDescription.of(translatable("betterpingdisplay.settings.autoColorPingText.description")))
                .binding(
                        config.shouldAutoColorPingText(),
                        config::shouldAutoColorPingText,
                        config::setShouldAutoColorPingText)
                .controller(o -> BooleanControllerBuilder.create(o).coloured(true))
                .addListener((option, event) -> pingTextColorOption.setAvailable(!option.pendingValue()))
                .build();

        Option<String> textFormatOption = Option.<String>createBuilder()
                .name(translatable("betterpingdisplay.settings.pingTextFormatString"))
                .description(OptionDescription.of(translatable("betterpingdisplay.settings.pingTextFormatString.description")))
                .binding(
                        config.getTextFormatString(),
                        config::getTextFormatString,
                        config::setTextFormatString)
                .controller(StringControllerBuilder::create)
                .customController((o) -> new ValidatedStringController(o, (s) -> s.contains("%d")))
                .build();

        Option<Boolean> renderPingBarsOption = Option.<Boolean>createBuilder()
                .name(translatable("betterpingdisplay.settings.renderPingBars"))
                .description(OptionDescription.of(translatable("betterpingdisplay.settings.renderPingBars.description")))
                .binding(
                        config.shouldRenderPingBars(),
                        config::shouldRenderPingBars,
                        config::setShouldRenderPingBars)
                .controller(o -> BooleanControllerBuilder.create(o).coloured(true))
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(translatable("betterpingdisplay.settings.title"))
                .category(ConfigCategory.createBuilder()
                        .name(translatable("betterpingdisplay.settings.title"))
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
