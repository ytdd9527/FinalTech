package io.taraxacum.finaltech.api.interfaces;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

/**
 * {@link AbstractMachine} implements this interface will work fewer times if the server is in low performance
 * @author Final_ROOT
 * @since 2.0
 */
public interface PerformanceLimitMachine {
    String KEY = "msps-charge";

    default boolean charge(Config config) {
        int charge = config.contains(KEY) ? Integer.parseInt(config.getString(KEY)) : 0;
        charge += 1000000.0 / FinalTech.getMSPS();
        if (charge >= 1000) {
            if (charge >= 2000) {
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
