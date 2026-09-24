package com.example.parallaxpanorama.mixin;

import com.example.parallaxpanorama.MouseParallax;
import net.minecraft.client.renderer.CubeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CubeMap.class)
public abstract class PanoramaRendererMixin {
    @ModifyVariable(
            method = "render(FF)V",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float parallaxPanorama$modifyPitch(float rotX) {
        MouseParallax.INSTANCE.tick();
        return rotX + (float) MouseParallax.INSTANCE.getPitchOffset();
    }

    @ModifyVariable(
            method = "render(FF)V",
            at = @At("HEAD"),
            ordinal = 1,
            argsOnly = true
    )
    private float parallaxPanorama$modifyYaw(float rotY) {
        return rotY - (float) MouseParallax.INSTANCE.getYawOffset();
    }
}