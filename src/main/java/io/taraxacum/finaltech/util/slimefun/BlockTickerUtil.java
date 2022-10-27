package io.taraxacum.finaltech.util.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.api.factory.ServerRunnableLockFactory;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Future;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BlockTickerUtil {

    public static void runBlockTickerLocal(@Nonnull JavaPlugin javaPlugin, @Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        if (!blockTicker.isSynchronized() || Bukkit.isPrimaryThread()) {
            blockTicker.tick(block, slimefunItem, config);
        } else {
            Bukkit.getScheduler().runTask(javaPlugin, () -> blockTicker.tick(block, slimefunItem, config));
        }
    }

    @SafeVarargs
    public static <T> void runBlockTicker(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, @Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config, @Nonnull T... locks) {
        if (!blockTicker.isSynchronized() || Bukkit.isPrimaryThread()) {
            blockTicker.tick(block, slimefunItem, config);
        } else {
            serverRunnableLockFactory.waitThenRun(() -> blockTicker.tick(block, slimefunItem, config), locks);
        }
    }

    @Nonnull
    @SafeVarargs
    public static <T> Future<Void> runBlockTickerCallable(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, @Nonnull BlockTicker blockTicker, @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config, @Nonnull T... locks) {
        if (blockTicker.isSynchronized()) {
            return Bukkit.getScheduler().callSyncMethod(serverRunnableLockFactory.getPlugin(), () -> {
                blockTicker.tick(block, slimefunItem, config);
                return null;
            });
        } else {
            return serverRunnableLockFactory.waitThenRun(() -> blockTicker.tick(block, slimefunItem, config), locks);
        }
    }

    @SafeVarargs
    public static <T> void runTask(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, boolean async, @Nonnull Runnable runnable, T... locks) {
        if (async) {
            serverRunnableLockFactory.waitThenRun(runnable, locks);
        } else {
            runnable.run();
        }
    }

    public static void setSleep(@Nonnull Config config, Double sleep) {
        config.setValue(ConstantTableUtil.CONFIG_SLEEP, sleep);
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
