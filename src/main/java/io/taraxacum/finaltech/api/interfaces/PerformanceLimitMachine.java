package io.taraxacum.finaltech.api.interfaces;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import javax.annotation.Nonnull;

/**
 * {@link AbstractMachine} implements this interface will work fewer times if the server is in low performance
 * @author Final_ROOT
 * @since 2.0
 */
public interface PerformanceLimitMachine {
    String KEY = "tps-charge";

    default boolean charge(@Nonnull Config config) {
        int charge = config.contains(KEY) ? Integer.parseInt(config.getString(KEY)) : 0;
        charge += FinalTech.getTps();
        if (charge >= 20) {
            if (charge >= 40) {
                charge -= 20;
            }
            config.setValue(KEY, String.valueOf(charge - 1000));
            return true;
        } else {
            config.setValue(KEY, String.valueOf(charge));
            return false;
        }
    }
}
