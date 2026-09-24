package com.example.parallaxpanorama;

import com.example.parallaxpanorama.config.ParallaxConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ParallaxPanoramaMod implements ClientModInitializer {
    public void onInitializeClient() {
        ParallaxConfig.load(FabricLoader.getInstance().getConfigDir());
    }
}
