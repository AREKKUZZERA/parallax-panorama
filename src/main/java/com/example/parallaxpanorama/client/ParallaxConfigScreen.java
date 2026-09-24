package com.example.parallaxpanorama.client;

import com.example.parallaxpanorama.config.ParallaxConfig;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ParallaxConfigScreen extends Screen {

    private final Screen parent;

    public ParallaxConfigScreen(Screen parent) {
        super(Component.translatable("screen.parallaxpanorama.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int center = width / 2;
        int centerY = height / 2;

        int sliderWidth = 220;
        int buttonWidth = 200;

        int sliderX = center - sliderWidth / 2;
        int buttonX = center - buttonWidth / 2;

        addRenderableWidget(
                new StrengthSlider(
                        sliderX,
                        centerY - 65,
                        sliderWidth,
                        20
                )
        );

        addRenderableWidget(
                Button.builder(
                        getXDirectionText(),
                        button -> {
                            ParallaxConfig.toggleXDirection();
                            button.setMessage(getXDirectionText());
                        }
                ).bounds(
                        buttonX,
                        centerY - 35,
                        buttonWidth,
                        20
                ).build()
        );

        addRenderableWidget(
                Button.builder(
                        getYDirectionText(),
                        button -> {
                            ParallaxConfig.toggleYDirection();
                            button.setMessage(getYDirectionText());
                        }
                ).bounds(
                        buttonX,
                        centerY - 10,
                        buttonWidth,
                        20
                ).build()
        );

        addRenderableWidget(
                Button.builder(
                        Component.translatable("option.parallaxpanorama.reset"),
                        button -> {
                            ParallaxConfig.resetToDefaults();
                            minecraft.gui.setScreen(
                                    new ParallaxConfigScreen(parent)
                            );
                        }
                ).bounds(
                        buttonX,
                        centerY + 20,
                        buttonWidth,
                        20
                ).build()
        );

        addRenderableWidget(
                Button.builder(
                        Component.translatable("gui.done"),
                        button -> onClose()
                ).bounds(
                        buttonX,
                        centerY + 50,
                        buttonWidth,
                        20
                ).build()
        );
    }

    private Component getXDirectionText() {
        String direction = ParallaxConfig.getXDirection() > 0 ? "+" : "-";

        return Component.translatable(
                "option.parallaxpanorama.x_direction",
                direction
        );
    }

    private Component getYDirectionText() {
        String direction = ParallaxConfig.getYDirection() > 0 ? "+" : "-";

        return Component.translatable(
                "option.parallaxpanorama.y_direction",
                direction
        );
    }

    @Override
    public void onClose() {
        minecraft.gui.setScreen(parent);
    }

    private static final class StrengthSlider extends AbstractSliderButton {

        private StrengthSlider(int x, int y, int width, int height) {
            super(
                    x,
                    y,
                    width,
                    height,
                    Component.empty(),
                    (ParallaxConfig.getStrength() - ParallaxConfig.getMinStrength())
                            / (ParallaxConfig.getMaxStrength() - ParallaxConfig.getMinStrength())
            );

            updateMessage();
        }

        @Override
        protected void updateMessage() {
            int percent = (int) Math.round(getStrength() * 100.0);

            setMessage(
                    Component.translatable(
                            "option.parallaxpanorama.strength",
                            percent
                    )
            );
        }

        @Override
        protected void applyValue() {
            ParallaxConfig.setStrength(getStrength());
        }

        private double getStrength() {
            return ParallaxConfig.getMinStrength()
                    + value * (
                    ParallaxConfig.getMaxStrength()
                            - ParallaxConfig.getMinStrength()
            );
        }
    }
}