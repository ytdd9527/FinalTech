package io.taraxacum.finaltech.api.dto;

import io.taraxacum.finaltech.api.factory.TickerTaskRunner;

import javax.annotation.Nonnull;

/**
 * This task will be run every tick after being started.
 * #{@link TickerTaskRunner}
 * @author Final_ROOT
 * @since 2.0
 * @param <T>
 */
public abstract class AbstractSingleTickerTask<T> {
    private int time;

    public AbstractSingleTickerTask(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public abstract String getId();

    public abstract int getPriority();

    public abstract void tick(@Nonnull T t);

    public abstract void startTick(@Nonnull T t);

    public abstract void endTick(@Nonnull T t);

    public abstract void addTick(@Nonnull T t);

    public abstract boolean isSync();
}
