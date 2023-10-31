package ru.bitheaven.createairfabric;

import com.simibubi.create.Create;
import fuzs.thinair.helper.AirHelper;
import fuzs.thinair.helper.AirQualityLevel;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAirFabric implements ModInitializer {
    public static final String ID = "createairfabric";
    public static final String NAME = "Create Air Fabric";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
        LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib from the client!",
                () -> () -> "{} is accessing Porting Lib from the server!"
        ), NAME);
    }

    public static boolean airQualityActivatesHelmet(LivingEntity entity) {
        final AirQualityLevel air = AirHelper.getO2LevelFromLocation(entity.getEyePosition(), entity.level()).getFirst();
        return air == AirQualityLevel.RED || air == AirQualityLevel.YELLOW;
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }
}