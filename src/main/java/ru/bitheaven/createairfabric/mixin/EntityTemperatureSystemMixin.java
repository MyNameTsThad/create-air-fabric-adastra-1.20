package ru.bitheaven.createairfabric.mixin;

import earth.terrarium.adastra.common.items.armor.NetheriteSpaceSuitItem;
import earth.terrarium.adastra.common.systems.TemperatureApiImpl;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TemperatureApiImpl.class)
public abstract class EntityTemperatureSystemMixin {
    @Redirect(at = @At(value = "INVOKE",
      target = "Learth/terrarium/adastra/common/systems/TemperatureApiImpl;burnEntity(Lnet/minecraft/world/entity/LivingEntity;)V"),
            method = "entityTick")
    private void redirectTemperatureTick(TemperatureApiImpl instance, LivingEntity entity) {
        boolean createNetherite = true;
        createNetherite &= entity.getItemBySlot(EquipmentSlot.HEAD).getItem().isFireResistant();
        createNetherite &= entity.getItemBySlot(EquipmentSlot.CHEST).getItem().isFireResistant();
        createNetherite &= entity.getItemBySlot(EquipmentSlot.LEGS).is(ModItemTags.HEAT_RESISTANT_ARMOR)
                || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof NetheriteSpaceSuitItem
                || entity.getItemBySlot(EquipmentSlot.LEGS).getItem().isFireResistant();
        createNetherite &= entity.getItemBySlot(EquipmentSlot.FEET).is(ModItemTags.HEAT_RESISTANT_ARMOR)
                || entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof NetheriteSpaceSuitItem
                || entity.getItemBySlot(EquipmentSlot.FEET).getItem().isFireResistant();

        if(!createNetherite) {
            entity.hurt(entity.damageSources().onFire(), 6);
            entity.setSecondsOnFire(10);
        }
    }
}
