package io.taraxacum.finaltech.interfaces;

import io.taraxacum.finaltech.FinalTech;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

/**
 * @author Final_ROOT
 */
public interface AntiAccelerationMachine {
    String KEY = "anti-acceleration";

    /**
     * If it is accelerated in one sf tick
     * @param config
     * @return
     */
    default boolean isAccelerated(Config config) {
        if (config.contains(KEY) && Integer.parseInt(config.getValue(KEY).toString()) == FinalTech.getTimeCount()) {
            return true;
        }
        config.setValue(KEY, String.valueOf(FinalTech.getTimeCount()));
        return false;
    }
}
