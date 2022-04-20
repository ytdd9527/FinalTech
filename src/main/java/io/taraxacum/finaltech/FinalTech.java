package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.taraxacum.finaltech.command.GetItemFake;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.setup.SetupUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * @author Final_ROOT
 */
public class FinalTech extends JavaPlugin implements SlimefunAddon {
    private static int timeCount = 0;

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

        Bukkit.getLogger().info("[FinalTECH]开始压入配方缓存");
        MachineRecipeFactory.initAdvancedRecipeMap();
        Bukkit.getLogger().info("[FinalTECH]配方缓存压入完毕");

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
        scheduler.runTaskTimerAsynchronously(this, () -> FinalTech.timeCount++, 0, Slimefun.getTickerTask().getTickRate());
    }

    public static int getTimeCount() {
        return timeCount;
    }
}
