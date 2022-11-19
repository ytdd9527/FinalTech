package io.taraxacum.libs.plugin.dto;

import io.taraxacum.common.api.RunnableLockFactory;
import org.bukkit.Location;
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
    private boolean serverStop = false;
    private final Object lock = new Object();
    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final Map<T, FutureTask<?>> map = new HashMap<>();
    private static final Map<Plugin, Map<Class<?>, ServerRunnableLockFactory<?>>> JAVA_PLUGIN_MAP = new HashMap<>();
    public static final FutureTask<Void> VOID_FUTURE_TASK = new FutureTask<>(() -> null);

    private ServerRunnableLockFactory(@Nonnull Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = this.plugin.getServer().getScheduler();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    @SafeVarargs
    public final FutureTask<Void> waitThenRun(long delay, @Nonnull Runnable runnable, @Nonnull T... objects) {
        FutureTask<Void> futureTask = new FutureTask<>(() -> {
            try {
                if(!this.serverStop) {
                    runnable.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (T object : objects) {
                    ServerRunnableLockFactory.this.map.remove(object);
                }
            }
            return null;
        });
        if(this.serverStop) {
            return VOID_FUTURE_TASK;
        }
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
                    if (work && !this.serverStop) {
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
        FutureTask<C> futureTask = new FutureTask<>(() -> {
            try {
                if(!this.serverStop) {
                    return callable.call();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (T object : objects) {
                    ServerRunnableLockFactory.this.map.remove(object);
                }
            }
            return null;
        });
        if(this.serverStop) {
            return new FutureTask<>(() -> null);
        }
        this.scheduler.runTaskLaterAsynchronously(this.plugin, () -> {
            boolean work = false;
            while (!work) {
                for (T object : objects) {
                    if (ServerRunnableLockFactory.this.map.containsKey(object)) {
                        FutureTask<?> task = ServerRunnableLockFactory.this.map.get(object);
                        if(task != null) {
                            try {
                                task.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
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
                    if (work && !this.serverStop) {
                        this.scheduler.runTaskAsynchronously(this.plugin, futureTask);
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
                if(entry.getKey() instanceof Location) {
                    this.plugin.getLogger().warning("An error occurred in location: " + entry.getKey().toString());
                } else {
                    this.plugin.getLogger().warning("An error occurred in object: " + entry.getKey().toString());
                }
                e.printStackTrace();
            }
        }
    }

    public final void stop() {
        this.serverStop = true;
    }

    public static <C> ServerRunnableLockFactory<C> newInstance(@Nonnull Plugin plugin) {
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
                    ServerRunnableLockFactory<C> ServerRunnableLockFactory = newInstance(plugin);
                    classServerRunnableLockFactoryMap.put(clazz, ServerRunnableLockFactory);
                }
            }
        }
        return (ServerRunnableLockFactory<C>) classServerRunnableLockFactoryMap.get(clazz);
    }
}
