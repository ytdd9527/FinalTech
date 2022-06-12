package io.taraxacum.finaltech.api.factory;

import io.taraxacum.common.api.RunnableLockFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Final_ROOT
 * @since 2.0
 * @param <T>
 */
public class ServerRunnableLockFactory<T> implements RunnableLockFactory<T> {
    private final Map<T, FutureTask<Object>> MAP = new HashMap<>();
    private final Object lock = new Object();
    private final JavaPlugin javaPlugin;

    private ServerRunnableLockFactory(@Nonnull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    @SafeVarargs
    public final FutureTask<Object> waitThenRun(@Nonnull Runnable runnable, @Nonnull T... objects) {
        FutureTask<Object> futureTask = new FutureTask<>(runnable, new Object());
        final BukkitScheduler scheduler = this.javaPlugin.getServer().getScheduler();
        scheduler.runTaskAsynchronously(this.javaPlugin, () -> {
            boolean work = false;
            while (!work) {
                for(T object : objects) {
                    if(ServerRunnableLockFactory.this.MAP.containsKey(object)) {
                        FutureTask<Object> task = ServerRunnableLockFactory.this.MAP.get(object);
                        try {
                            task.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        ServerRunnableLockFactory.this.MAP.remove(object);
                    }
                }
                work = true;
                synchronized (ServerRunnableLockFactory.this.lock) {
                    for(T object : objects) {
                        if(ServerRunnableLockFactory.this.MAP.containsKey(object)) {
                            work = false;
                            break;
                        }
                    }
                    if(work) {
                        scheduler.runTaskAsynchronously(this.javaPlugin, futureTask);
                        for(T object : objects) {
                            ServerRunnableLockFactory.this.MAP.put(object, futureTask);
                        }
                    }
                }
            }
        });
        return futureTask;
    }

    public static <T> ServerRunnableLockFactory<T> getInstance(@Nonnull JavaPlugin javaPlugin) {
        return new ServerRunnableLockFactory<>(javaPlugin);
    }
}
