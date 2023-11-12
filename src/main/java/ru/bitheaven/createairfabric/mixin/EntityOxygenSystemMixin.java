package ru.bitheaven.createairfabric.mixin;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import earth.terrarium.ad_astra.common.entity.system.EntityOxygenSystem;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityOxygenSystem.class)
public abstract class EntityOxygenSystemMixin {
    /**
     * Make air exists if Create Air
     */
    @Redirect(at = @At(value = "INVOKE", target = "Learth/terrarium/ad_astra/common/util/ModUtils;armourIsOxygenated(Lnet/minecraft/world/entity/LivingEntity;)Z"),
            method = "oxygenTick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;)V")
    private static boolean redirectOxygenTick(LivingEntity entity) {
        boolean createOxygen = true;
        createOxygen &= !DivingHelmetItem.getWornItem(entity).isEmpty();
        createOxygen &= !BacktankUtil.getAllWithAir(entity).isEmpty();
        return ModUtils.armourIsOxygenated(entity) || createOxygen;
    }
}
