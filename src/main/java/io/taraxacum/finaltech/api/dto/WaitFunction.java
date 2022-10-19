//package io.taraxacum.finaltech.api.dto;
//
//import org.bukkit.plugin.Plugin;
//
//import java.util.function.Consumer;
//import java.util.function.Function;
//
//public class WaitFunction<T> {
//    int maxTime;
//
//    int internal;
//
//    boolean async;
//
//    Plugin plugin;
//
//    Function<T, Boolean> canWork;
//
//    Function<T, Boolean> stopWork;
//
//    Runnable runnable;
//
//    T t;
//
//    public void start() {
//        final WaitFunction<T> waitFunction = this;
//        Runnable runnable = () -> {
//            if (waitFunction.stopWork.apply(waitFunction.t)) {
//                return;
//            }
//            if (!waitFunction.canWork.apply(waitFunction.t)) {
//                plugin.getServer().getScheduler().runTaskLater(plugin, runnable, waitFunction.internal);
//                return;
//            }
//            waitFunction.runnable.run();
//        };
//        plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, this.internal);
//    }
//}
