package io.taraxacum.finaltech.api.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 * @param <T>
 */
public record BlockTask<T>(@Nonnull SlimefunItem slimefunItem, @Nonnull Boolean sync, @Nonnull Runnable runnable, @Nonnull T... objects) {
    @SafeVarargs
    public BlockTask {
    }

    @Nonnull
    @Override
    public SlimefunItem slimefunItem() {
        return slimefunItem;
    }

    @Override
    @Nonnull
    public Boolean sync() {
        return sync;
    }

    @Nonnull
    @Override
    public Runnable runnable() {
        return runnable;
    }

    @Nonnull
    @Override
    public T[] objects() {
        return objects;
    }

    @Nullable
    @SafeVarargs
    public final BlockTask<T> newInstance(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config, @Nonnull T... objects) {
        if(slimefunItem.getBlockTicker() != null) {
            return new BlockTask<>(slimefunItem, slimefunItem.getBlockTicker().isSynchronized(), () -> slimefunItem.getBlockTicker().tick(block, slimefunItem, config), objects);
        }
        return null;
    }
}
