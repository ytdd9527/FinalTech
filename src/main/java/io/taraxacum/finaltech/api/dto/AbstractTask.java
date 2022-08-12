package io.taraxacum.finaltech.api.dto;

import io.taraxacum.finaltech.api.factory.TickerTaskRunner;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @see AbstractSingleTickerTask
 * @see TickerTaskRunner
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractTask implements Runnable {
    private final JavaPlugin javaPlugin;

    protected AbstractTask(@Nonnull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public abstract void run();

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }
}
