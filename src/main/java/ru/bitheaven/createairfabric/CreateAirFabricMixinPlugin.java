package ru.bitheaven.createairfabric;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class CreateAirFabricMixinPlugin implements IMixinConfigPlugin {
    private static final Supplier<Boolean> TRUE = () -> true;

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "ru.bitheaven.createairfabric.mixin.DivingHelmetItemMixin",
                () -> FabricLoader.getInstance().isModLoaded("thinair")
                        || FabricLoader.getInstance().isModLoaded("ad_astra"),

            "ru.bitheaven.createairfabric.mixin.RemainingAirOverlayMixin",
               () -> FabricLoader.getInstance().isModLoaded("thinair")
                        || FabricLoader.getInstance().isModLoaded("ad_astra"),

            "ru.bitheaven.createairfabric.mixin.EntityOxygenSystemMixin",
               () -> FabricLoader.getInstance().isModLoaded("ad_astra"),

            "ru.bitheaven.createairfabric.mixin.EntityTemperatureSystemMixin",
                () -> FabricLoader.getInstance().isModLoaded("ad_astra")
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
    }

    // Boilerplate

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}