package io.taraxacum.finaltech.interfaces;

import io.taraxacum.finaltech.FinalTech;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

/**
 * @author Final_ROOT
 */
public interface PerformanceLimitMachine {
    String KEY = "mspt-charge";

    /**
     * If a machine can work
     * @param config
     * @return
     */
    default boolean canWork(Config config) {
        long charge = config.contains(KEY) ? Long.parseLong(config.getString(KEY)) : 0;
        charge += FinalTech.getMspt();
        if(charge >= 1000) {
            if(charge >= 2000) {
                charge -= 1000;
            }
            config.setValue(KEY, String.valueOf(charge - 1000));
            return true;
        } else {
            config.setValue(KEY, String.valueOf(charge));
            return false;
        }
    }
}
