package io.taraxacum.finaltech.api.interfaces;

import io.taraxacum.finaltech.FinalTech;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public interface AntiAccelerationMachine {
    String KEY = "anti-acceleration";
    default boolean isAccelerated(@Nonnull Config config) {
        if (config.contains(KEY) && Integer.parseInt(config.getValue(KEY).toString()) == FinalTech.getTimeCount()) {
            return true;
        }
        config.setValue(KEY, String.valueOf(FinalTech.getTimeCount()));
        return false;
    }
}
