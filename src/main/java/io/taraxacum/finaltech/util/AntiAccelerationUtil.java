package io.taraxacum.finaltech.util;

import io.taraxacum.finaltech.FinalTech;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import javax.annotation.Nonnull;

public class AntiAccelerationUtil {
    public static String KEY = "anti-acceleration";

    /**
     * This method will determine if the given machine is accelerated.
     * @param config The storage info in the machine location
     * @return whether a machine can work
     */
    public static boolean isAccelerated(@Nonnull Config config) {
        if (config.contains(KEY) && Integer.parseInt(config.getValue(KEY).toString()) == FinalTech.getSlimefunTickCount()) {
            return true;
        }
        config.setValue(KEY, String.valueOf(FinalTech.getSlimefunTickCount()));
        return false;
    }
}
