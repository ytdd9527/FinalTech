package io.taraxacum.finaltech.core.factory;

import io.taraxacum.common.api.RunnableLockFactory;
import io.taraxacum.finaltech.FinalTech;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ServerRunnableLockFactory<T> implements RunnableLockFactory<T> {
    private final Map<T, FutureTask<Object>> MAP = new HashMap<>();
    private final Object lock = new Object();

    private ServerRunnableLockFactory() {

    }

    @Override
    @SafeVarargs
    public final FutureTask<Object> waitThenRun(@Nonnull Runnable runnable, @Nonnull T... objects) {
        final JavaPlugin javaPlugin = FinalTech.getInstance().getJavaPlugin();
        FutureTask<Object> futureTask = new FutureTask<>(runnable, new Object());
        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> {
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
                        for(T object : objects) {
                            ServerRunnableLockFactory.this.MAP.put(object, futureTask);
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, futureTask);
                        }
                    }
                }
            }
        });
        return futureTask;
    }

    public static <T> ServerRunnableLockFactory<T> getInstance() {
        return new ServerRunnableLockFactory<>();
    }
}
