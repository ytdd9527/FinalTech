package io.taraxacum.common.api;

import javax.annotation.Nonnull;
import java.util.concurrent.FutureTask;

public interface RunnableLockFactory<T> {

    /**
     * @param runnable logic you want to do
     * @param objects wait for #{@link FutureTask} triggered to the objects be finished, than run the runnable
     * @return
     */
    FutureTask waitThenRun(@Nonnull Runnable runnable, @Nonnull T... objects);
}
