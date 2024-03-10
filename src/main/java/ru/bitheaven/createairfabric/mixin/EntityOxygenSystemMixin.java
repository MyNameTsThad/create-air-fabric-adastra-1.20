package ru.bitheaven.createairfabric.mixin;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.adastra.common.systems.OxygenApiImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OxygenApiImpl.class)
public abstract class EntityOxygenSystemMixin {
    /**
     * Make air exists if Create Air
     */
    @Redirect(at = @At(value = "INVOKE", target = "Learth/terrarium/adastra/common/items/armor/SpaceSuitItem;hasOxygen(Lnet/minecraft/world/entity/Entity;)Z"),
      method = "entityTick")
    private boolean redirectOxygenTick(Entity entity) {
        boolean createOxygen = true;
        createOxygen &= !DivingHelmetItem.getWornItem(entity).isEmpty();
        createOxygen &= !BacktankUtil.getAllWithAir((LivingEntity) entity).isEmpty();
        return SpaceSuitItem.hasOxygen(entity) || createOxygen;
    }
}
