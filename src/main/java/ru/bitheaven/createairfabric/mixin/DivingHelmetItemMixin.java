package ru.bitheaven.createairfabric.mixin;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.bitheaven.createairfabric.CreateAirFabric;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;

@Mixin(DivingHelmetItem.class)
public abstract class DivingHelmetItemMixin {
    /**
     * Activate helmet "if in water or lava" -> "if in water or bad air or lava"
     */
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"),
            method = "breatheUnderwater(Lnet/minecraft/world/entity/LivingEntity;)V")
    private static boolean redirectBreatheUnderwater(LivingEntity entity, TagKey<Fluid> fluidTagKey) {
        return entity.isEyeInFluid(fluidTagKey) || CreateAirFabric.airQualityActivatesHelmet(entity);
    }
}