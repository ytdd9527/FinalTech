package io.taraxacum.finaltech.api.interfaces;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import javax.annotation.Nonnull;

/**
 * {@link AbstractMachine} implements this interface will not work more than one times in one sf tick.
 * @author Final_ROOT
 * @since 2.0
 */
public interface AntiAccelerationMachine {
    String KEY = "anti-acceleration";

    /**
     * This method will determine if the given machine is accelerated.
     * @param config The storage info in the machine location
     * @return whether a machine can work
     */
    default boolean isAccelerated(@Nonnull Config config) {
        if (config.contains(KEY) && Integer.parseInt(config.getValue(KEY).toString()) == FinalTech.getSlimefunTickCount()) {
            return true;
        }
        config.setValue(KEY, String.valueOf(FinalTech.getSlimefunTickCount()));
        return false;
    }
}
