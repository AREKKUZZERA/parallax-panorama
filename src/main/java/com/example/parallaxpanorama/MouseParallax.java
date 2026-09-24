package com.example.parallaxpanorama;

import com.example.parallaxpanorama.config.ParallaxConfig;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public final class MouseParallax {

    public static final MouseParallax INSTANCE = new MouseParallax();

    private static final double MAX_YAW_OFFSET_DEG = 4.8;
    private static final double MAX_PITCH_OFFSET_DEG = 2.7;

    private static final double SMOOTHING_SPEED = 7.0;

    private double currentYawOffset = 0.0;
    private double currentPitchOffset = 0.0;

    private long lastNanoTime = -1L;

    private MouseParallax() {
    }

    public double getYawOffset() {
        return currentYawOffset;
    }

    public double getPitchOffset() {
        return currentPitchOffset;
    }

    public void tick() {
        long now = System.nanoTime();

        double deltaSeconds;

        if (lastNanoTime < 0) {
            deltaSeconds = 0.0;
        } else {
            deltaSeconds = (now - lastNanoTime) / 1_000_000_000.0;
        }

        lastNanoTime = now;

        deltaSeconds = Mth.clamp(deltaSeconds, 0.0, 0.25);

        double[] normalized = normalizedMouseOffset();

        double targetYaw =
                normalized[0]
                        * MAX_YAW_OFFSET_DEG
                        * ParallaxConfig.getStrength()
                        * ParallaxConfig.getXDirection();

        double targetPitch =
                normalized[1]
                        * MAX_PITCH_OFFSET_DEG
                        * ParallaxConfig.getStrength()
                        * ParallaxConfig.getYDirection();

        double factor =
                1.0 - Math.exp(-SMOOTHING_SPEED * deltaSeconds);

        currentYawOffset = Mth.lerp(
                factor,
                currentYawOffset,
                targetYaw
        );

        currentPitchOffset = Mth.lerp(
                factor,
                currentPitchOffset,
                targetPitch
        );
    }

    private double[] normalizedMouseOffset() {
        Minecraft client = Minecraft.getInstance();
        Window window = client.getWindow();

        double scaledWidth = window.getGuiScaledWidth();
        double scaledHeight = window.getGuiScaledHeight();

        double mouseX =
                client.mouseHandler.xpos()
                        * scaledWidth
                        / window.getScreenWidth();

        double mouseY =
                client.mouseHandler.ypos()
                        * scaledHeight
                        / window.getScreenHeight();

        double centerX = scaledWidth / 2.0;
        double centerY = scaledHeight / 2.0;

        double normX =
                centerX == 0
                        ? 0.0
                        : (mouseX - centerX) / centerX;

        double normY =
                centerY == 0
                        ? 0.0
                        : (mouseY - centerY) / centerY;

        return new double[] {
                Mth.clamp(normX, -1.0, 1.0),
                Mth.clamp(normY, -1.0, 1.0)
        };
    }
}