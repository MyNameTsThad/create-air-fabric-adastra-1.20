package ru.bitheaven.createairfabric.mixin;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import ru.bitheaven.createairfabric.CreateAirFabric;

@Mixin(DivingHelmetItem.class)
public abstract class DivingHelmetItemMixin {
    /**
     * Activate helmet "if in water or lava" -> "if in water or bad air or lava"
     */
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"),
            method = "breatheUnderwater(Lnet/minecraft/world/entity/LivingEntity;)V")
    private static boolean redirectBreatheUnderwater(LivingEntity entity, TagKey<Fluid> fluidTagKey) {
        boolean oxygen = true;
        oxygen &= !entity.isEyeInFluid(fluidTagKey);
        oxygen &= !entity.isUnderWater();
        oxygen &= CreateAirFabric.isOxygen(entity);
        
        return !oxygen;
    }
}