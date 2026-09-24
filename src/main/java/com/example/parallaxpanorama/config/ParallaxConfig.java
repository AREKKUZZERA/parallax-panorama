package com.example.parallaxpanorama.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ParallaxConfig {

    private static final String KEY_STRENGTH = "strength";
    private static final String KEY_X_DIRECTION = "x_direction";
    private static final String KEY_Y_DIRECTION = "y_direction";

    private static final double DEFAULT_STRENGTH = 1.0;
    private static final double MIN_STRENGTH = 0.0;
    private static final double MAX_STRENGTH = 2.0;

    private static final int DEFAULT_X_DIRECTION = -1;
    private static final int DEFAULT_Y_DIRECTION = 1;

    private static double strength = DEFAULT_STRENGTH;
    private static int xDirection = DEFAULT_X_DIRECTION;
    private static int yDirection = DEFAULT_Y_DIRECTION;

    private static Path file;

    private ParallaxConfig() {
    }

    public static void resetToDefaults() {
    strength = DEFAULT_STRENGTH;
    xDirection = DEFAULT_X_DIRECTION;
    yDirection = DEFAULT_Y_DIRECTION;
    save();
    }

    public static void load(Path configDir) {
        file = configDir.resolve("parallaxpanorama.properties");

        if (!Files.exists(file)) {
            save();
            return;
        }

        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream(file)) {
            properties.load(input);

            strength = clamp(Double.parseDouble(
                    properties.getProperty(
                            KEY_STRENGTH,
                            Double.toString(DEFAULT_STRENGTH)
                    )
            ));

            xDirection = parseDirection(
                    properties.getProperty(
                            KEY_X_DIRECTION,
                            Integer.toString(DEFAULT_X_DIRECTION)
                    ),
                    DEFAULT_X_DIRECTION
            );

            yDirection = parseDirection(
                    properties.getProperty(
                            KEY_Y_DIRECTION,
                            Integer.toString(DEFAULT_Y_DIRECTION)
                    ),
                    DEFAULT_Y_DIRECTION
            );

        } catch (IOException | NumberFormatException ignored) {
            strength = DEFAULT_STRENGTH;
            xDirection = DEFAULT_X_DIRECTION;
            yDirection = DEFAULT_Y_DIRECTION;
        }
    }

    public static void save() {
        if (file == null) {
            return;
        }

        Properties properties = new Properties();

        properties.setProperty(
                KEY_STRENGTH,
                Double.toString(strength)
        );

        properties.setProperty(
                KEY_X_DIRECTION,
                Integer.toString(xDirection)
        );

        properties.setProperty(
                KEY_Y_DIRECTION,
                Integer.toString(yDirection)
        );

        try {
            Files.createDirectories(file.getParent());

            try (OutputStream output = Files.newOutputStream(file)) {
                properties.store(
                        output,
                        "Parallax Panorama configuration"
                );
            }

        } catch (IOException ignored) {
        }
    }

    public static double getStrength() {
        return strength;
    }

    public static void setStrength(double value) {
        strength = clamp(value);
        save();
    }

    public static double getDefaultStrength() {
        return DEFAULT_STRENGTH;
    }

    public static double getMinStrength() {
        return MIN_STRENGTH;
    }

    public static double getMaxStrength() {
        return MAX_STRENGTH;
    }

    public static int getXDirection() {
        return xDirection;
    }

    public static int getYDirection() {
        return yDirection;
    }

    public static void toggleXDirection() {
        xDirection *= -1;
        save();
    }

    public static void toggleYDirection() {
        yDirection *= -1;
        save();
    }

    private static int parseDirection(String value, int fallback) {
        try {
            return Integer.parseInt(value) < 0 ? -1 : 1;
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    private static double clamp(double value) {
        return Math.max(
                MIN_STRENGTH,
                Math.min(MAX_STRENGTH, value)
        );
    }
}