package io.taraxacum.libs.plugin.dto;

import io.taraxacum.common.api.RunnableLockFactory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Final_ROOT
 * @since 2.0
 * @param <T>
 */
public class ServerRunnableLockFactory<T> implements RunnableLockFactory<T> {
    private final Object lock = new Object();
    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final Map<T, FutureTask<?>> map = new HashMap<>();
    private static final Map<Plugin, Map<Class<?>, ServerRunnableLockFactory<?>>> JAVA_PLUGIN_MAP = new HashMap<>();

    private ServerRunnableLockFactory(@Nonnull Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = this.plugin.getServer().getScheduler();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    @SafeVarargs
    public final FutureTask<Void> waitThenRun(long delay, @Nonnull Runnable runnable, @Nonnull T... objects) {
        FutureTask<Void> futureTask = new FutureTask<>(runnable, null);
        this.scheduler.runTaskLaterAsynchronously(this.plugin, () -> {
            boolean work = false;
            while (!work) {
                for (T object : objects) {
                    if (ServerRunnableLockFactory.this.map.containsKey(object)) {
                        FutureTask<?> task = ServerRunnableLockFactory.this.map.get(object);
                        if (task != null) {
                            try {
                                task.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        ServerRunnableLockFactory.this.map.remove(object);
                    }
                }
                work = true;
                synchronized (ServerRunnableLockFactory.this.lock) {
                    for (T object : objects) {
                        if (ServerRunnableLockFactory.this.map.containsKey(object)) {
                            work = false;
                            break;
                        }
                    }
                    if (work) {
                        ServerRunnableLockFactory.this.scheduler.runTaskAsynchronously(this.plugin, futureTask);
                        for (T object : objects) {
                            ServerRunnableLockFactory.this.map.put(object, futureTask);
                        }
                    }
                }
            }
        }, delay);
        return futureTask;
    }
    @Override
    @SafeVarargs
    public final FutureTask<Void> waitThenRun(@Nonnull Runnable runnable, @Nonnull T... objects) {
        return this.waitThenRun(0, runnable, objects);
    }

    @SafeVarargs
    public final <C> FutureTask<C> waitThenRun(long delay, @Nonnull Callable<C> callable, @Nonnull T... objects) {
        FutureTask<C> futureTask = new FutureTask<>(callable);
        scheduler.runTaskLaterAsynchronously(this.plugin, () -> {
            boolean work = false;
            while (!work) {
                for (T object : objects) {
                    if (ServerRunnableLockFactory.this.map.containsKey(object)) {
                        FutureTask<?> task = ServerRunnableLockFactory.this.map.get(object);
                        try {
                            task.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        ServerRunnableLockFactory.this.map.remove(object);
                    }
                }
                work = true;
                synchronized (ServerRunnableLockFactory.this.lock) {
                    for (T object : objects) {
                        if (ServerRunnableLockFactory.this.map.containsKey(object)) {
                            work = false;
                            break;
                        }
                    }
                    if (work) {
                        scheduler.runTaskAsynchronously(this.plugin, futureTask);
                        for (T object : objects) {
                            ServerRunnableLockFactory.this.map.put(object, futureTask);
                        }
                    }
                }
            }
        }, delay);
        return futureTask;
    }
    @Override
    @SafeVarargs
    public final <C> FutureTask<C> waitThenRun(@Nonnull Callable<C> callable, @Nonnull T... objects) {
        return this.waitThenRun(0, callable, objects);
    }

    public final int taskSize() {
        return this.map.size();
    }

    public final void waitAllTask() throws ExecutionException, InterruptedException {
        for (Map.Entry<T, FutureTask<?>> entry : map.entrySet()) {
            try {
                entry.getValue().get(5, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public static <C> ServerRunnableLockFactory<C> newInstance(@Nonnull Plugin plugin, @Nonnull Class<C> clazz) {
        return new ServerRunnableLockFactory<>(plugin);
    }

    public static <C> ServerRunnableLockFactory<C> getInstance(@Nonnull Plugin plugin, @Nonnull Class<C> clazz) {
        if (!JAVA_PLUGIN_MAP.containsKey(plugin)) {
            synchronized (JAVA_PLUGIN_MAP) {
                if (!JAVA_PLUGIN_MAP.containsKey(plugin)) {
                    final Map<Class<?>, ServerRunnableLockFactory<?>> classServerRunnableLockFactoryMap = new HashMap<>();
                    JAVA_PLUGIN_MAP.put(plugin, classServerRunnableLockFactoryMap);
                }
            }
        }
        Map<Class<?>, ServerRunnableLockFactory<?>> classServerRunnableLockFactoryMap = JAVA_PLUGIN_MAP.get(plugin);
        if (!classServerRunnableLockFactoryMap.containsKey(clazz)) {
            synchronized (classServerRunnableLockFactoryMap) {
                if (!classServerRunnableLockFactoryMap.containsKey(clazz)) {
                    ServerRunnableLockFactory<C> ServerRunnableLockFactory = newInstance(plugin, clazz);
                    classServerRunnableLockFactoryMap.put(clazz, ServerRunnableLockFactory);
                }
            }
        }
        return (ServerRunnableLockFactory<C>) classServerRunnableLockFactoryMap.get(clazz);
    }
}
