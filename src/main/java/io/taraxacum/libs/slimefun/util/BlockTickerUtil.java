package io.taraxacum.libs.slimefun.util;

import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BlockTickerUtil {
    @SafeVarargs
    public static <T> void runTask(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, boolean async, @Nonnull Runnable runnable, T... locks) {
        if (async) {
            serverRunnableLockFactory.waitThenRun(runnable, locks);
        } else {
            runnable.run();
        }
    }

    public static void setSleep(@Nonnull Config config, Double sleep) {
        config.setValue(ConstantTableUtil.CONFIG_SLEEP, String.valueOf(sleep));
    }

    public static void setSleep(@Nonnull Location location, @Nullable String sleep) {
        BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_SLEEP, sleep);
    }

    public static boolean hasSleep(@Nonnull Config config) {
        return config.contains(ConstantTableUtil.CONFIG_SLEEP);
    }

    public static double getSleep(@Nonnull Config config) {
        return Double.parseDouble(config.getString(ConstantTableUtil.CONFIG_SLEEP));
    }

    public static void subSleep(@Nonnull Config config) {
        if (config.contains(ConstantTableUtil.CONFIG_SLEEP)) {
            double sleep = BlockTickerUtil.getSleep(config) - 1;
            if (sleep > 0) {
                config.setValue(ConstantTableUtil.CONFIG_SLEEP, String.valueOf(sleep));
            } else {
                config.setValue(ConstantTableUtil.CONFIG_SLEEP, null);
            }
        }
    }
}
