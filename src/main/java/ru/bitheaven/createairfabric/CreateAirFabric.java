package ru.bitheaven.createairfabric;

import com.simibubi.create.Create;
import earth.terrarium.adastra.api.systems.OxygenApi;
import fuzs.thinair.helper.AirQualityHelperImpl;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
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

    public static boolean isOxygen(Entity entity) {
        boolean oxygen = true;
        if (FabricLoader.getInstance().isModLoaded("thinair")) {
            oxygen &= new AirQualityHelperImpl()
                    .getAirQualityAtLocation(entity.level(), entity.getEyePosition()).canBreathe;
        }
        if (FabricLoader.getInstance().isModLoaded("ad_astra")) {
            oxygen &= OxygenApi.API.hasOxygen(entity.level());
        }
        return oxygen;
    }
}