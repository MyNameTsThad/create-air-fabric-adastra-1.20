package ru.bitheaven.createairfabric.mixin;

import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.entity.system.EntityTemperatureSystem;
import earth.terrarium.ad_astra.common.item.armor.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityTemperatureSystem.class)
public abstract class EntityTemperatureSystemMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Learth/terrarium/ad_astra/common/entity/system/EntityTemperatureSystem;burnEntity(Lnet/minecraft/world/entity/LivingEntity;)V"),
            method = "temperatureTick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;)V")
    private static void redirectTemperatureTick(LivingEntity entity) {
        boolean createNetherite = true;
        createNetherite &= entity.getItemBySlot(EquipmentSlot.HEAD).getItem().isFireResistant();
        createNetherite &= entity.getItemBySlot(EquipmentSlot.CHEST).getItem().isFireResistant();
        createNetherite &= entity.getItemBySlot(EquipmentSlot.LEGS).is(ModTags.HEAT_RESISTANT)
                || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof NetheriteSpaceSuit
                || entity.getItemBySlot(EquipmentSlot.LEGS).getItem().isFireResistant();
        createNetherite &= entity.getItemBySlot(EquipmentSlot.FEET).is(ModTags.HEAT_RESISTANT)
                || entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof NetheriteSpaceSuit
                || entity.getItemBySlot(EquipmentSlot.FEET).getItem().isFireResistant();

        if(!createNetherite) {
            entity.hurt(entity.damageSources().onFire(), (float) AdAstraConfig.heatDamage);
            entity.setSecondsOnFire(10);
        }
    }
}
