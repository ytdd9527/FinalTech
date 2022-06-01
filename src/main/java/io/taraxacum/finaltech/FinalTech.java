package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.core.factory.ItemValueTable;
import io.taraxacum.finaltech.setup.SetupUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Final_ROOT
 */
public class FinalTech extends JavaPlugin implements SlimefunAddon {
    private BukkitTask bukkitTask = null;
    public final Config configFile = new Config(this);
    public final Config valueFile = new Config(this, "value.yml");
    public static FinalTech instance;
    public static int SLIMEFUN_TICK_TIME_MILLIS = 50 * 1000 * 10;
    private static int TIME_COUNT = 0;
    private static long MILLISECONDS_PER_20_TICK = 1000;

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        this.init();
    }

    @Override
    public void onDisable() {
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        }
    }

    @Override
    public String getBugTrackerURL() {
        return null;
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public Config getConfigFile() {
        return this.configFile;
    }

    public Config getValueFile() {
        return valueFile;
    }

    private void init() {
        BukkitScheduler scheduler = FinalTech.getInstance().getServer().getScheduler();
        AtomicLong lastTimeMillis = new AtomicLong(System.currentTimeMillis());
        int tickRate = Slimefun.getTickerTask().getTickRate();
        SLIMEFUN_TICK_TIME_MILLIS = 50 * 1000 * tickRate;
        AtomicLong currentTimeMillis = new AtomicLong();
        scheduler.runTaskTimerAsynchronously(this, () -> {
            currentTimeMillis.set(System.currentTimeMillis());
            FinalTech.MILLISECONDS_PER_20_TICK = SLIMEFUN_TICK_TIME_MILLIS / (currentTimeMillis.get() - lastTimeMillis.get());
            lastTimeMillis.set(currentTimeMillis.get());
            FinalTech.TIME_COUNT++;
        }, 0, tickRate);

        try {
            if (!this.configFile.getFile().exists()) {
                Files.copy(Objects.requireNonNull(this.getClass().getResourceAsStream("/config.yml")), this.configFile.getFile().toPath());
            }
            if (!this.valueFile.getFile().exists()) {
                Files.copy(Objects.requireNonNull(this.getClass().getResourceAsStream("/value.yml")), this.valueFile.getFile().toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SetupUtil.init();
        SetupUtil.setupItems(this);

        ItemValueTable.getInstance().init();

        this.bukkitTask = scheduler.runTaskTimerAsynchronously(this, () -> BlockTaskFactory.getInstance().tick(), 0, tickRate);
    }

    public static FinalTech getInstance() {
        return instance;
    }

    public static int getTimeCount() {
        return TIME_COUNT;
    }

    public static long getMSPS() {
        return MILLISECONDS_PER_20_TICK;
    }
}
