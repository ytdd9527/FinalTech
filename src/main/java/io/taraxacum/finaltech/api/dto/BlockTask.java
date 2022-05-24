package io.taraxacum.finaltech.api.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import javax.annotation.Nonnull;

public record BlockTask<T>(@Nonnull SlimefunItem slimefunItem, @Nonnull Boolean sync, @Nonnull Runnable runnable, @Nonnull T... objects) {
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
}
