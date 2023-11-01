package ru.bitheaven.createairfabric.mixin;


import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.bitheaven.createairfabric.CreateAirFabric;

import net.minecraft.client.player.LocalPlayer;

@Mixin(RemainingAirOverlay.class)
public class RemainingAirOverlayMixin {
    /**
     * Activate UI "if in water or lava" -> "if in water or bad air or lava"
     */
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"),
            method = "render(Lnet/minecraft/client/gui/GuiGraphics;II)V")
    private static boolean redirectRender(LocalPlayer player, TagKey<Fluid> fluidTagKey) {
        return player.isEyeInFluid(fluidTagKey) || CreateAirFabric.airQualityActivatesHelmet(player);
    }
}
