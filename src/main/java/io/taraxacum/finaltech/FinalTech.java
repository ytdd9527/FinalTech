package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.taraxacum.finaltech.command.GetItemFake;
import io.taraxacum.finaltech.factory.ItemValueMap;
import io.taraxacum.finaltech.setup.SetupUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Final_ROOT
 */
public class FinalTech extends JavaPlugin implements SlimefunAddon {
    private static int timeCount = 0;
    private static long mspt = 1;
    public static final String name = "FinalTech";

    @Override
    public void onEnable() {
        super.onEnable();
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // todo
            // You could start an Auto-Updater for example
        }

        SetupUtil.init(this);
        SetupUtil.setupMenus(this);
        SetupUtil.setupItems(this);

        this.init();

        ItemValueMap.init();

        this.getCommand("test").setExecutor(new GetItemFake());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getBugTrackerURL() {
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    private void init() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        AtomicLong lastTimeMillis = new AtomicLong(System.currentTimeMillis());
        int tickRate = Slimefun.getTickerTask().getTickRate();
        long t = 50000L * tickRate;
        AtomicLong currentTimeMillis = new AtomicLong();
        scheduler.runTaskTimerAsynchronously(this, () -> {
            currentTimeMillis.set(System.currentTimeMillis());
            FinalTech.mspt = t / (currentTimeMillis.get() - lastTimeMillis.get());
            lastTimeMillis.set(currentTimeMillis.get());
            FinalTech.timeCount++;
        }, 0, tickRate);
    }

    public static int getTimeCount() {
        return timeCount;
    }

    public static long getMspt() {
        return mspt;
    }
}
