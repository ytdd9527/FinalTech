package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.setup.SetupUtil;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * @author Final_ROOT
 */
public class FinalTech extends JavaPlugin implements SlimefunAddon {
    /**
     * Force other slimefun machine to run async.
     */
    private boolean forceSlimefunMultiThread = false;
    /**
     * 0: nothing change, all task will run at slimefun #{@link io.github.thebusybiscuit.slimefun4.implementation.tasks.TickerTask}
     * 1: async task will be put in #{@link ServerRunnableLockFactory}, so they will be really async
     * 2: sync task will be run as async, so all (FinalTech's machines') task will be put in #{@link ServerRunnableLockFactory}
     */
    private int multiThreadLevel = 0;
    /**
     * Add by 1 every slimefun tick.
     */
    private int slimefunTickCount = 0;
    private long tps = 20;
    private Logger logger;
    private ServerRunnableLockFactory<Location> locationRunnableFactory;
    private ServerRunnableLockFactory<Entity> entityRunnableFactory;
    private ConfigFileManager config;
    private ConfigFileManager value;
    private ConfigFileManager item;
    private LanguageManager languageManager;
    private Set<String> asyncSlimefunIdSet = new HashSet<>();
    private Set<String> antiAccelerateSlimefunIdSet = new HashSet<>();
    private Set<String> performanceLimitSlimefunIdSet = new HashSet<>();
    private Random random = new Random();
    private BukkitTask bukkitTask;
    private final int version = 20220811;
    private static FinalTech instance;

    @Override
    public void onEnable() {
        super.onEnable();

        if (false) {
            this.onDisable();
            return;
        }

        instance = this;
        this.logger = this.getJavaPlugin().getServer().getLogger();

        /* read config file */
        try {
            this.config = ConfigFileManager.getOrNewInstance(this, "config");
            this.value = ConfigFileManager.getOrNewInstance(this, "value");
            this.item = ConfigFileManager.getOrNewInstance(this, "item");

            String language = this.config.getOrDefault("en-US", "language");
            this.languageManager = LanguageManager.getOrNewInstance(this, language);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        /* set runnable factory */
        this.locationRunnableFactory = ServerRunnableLockFactory.getInstance(this, Location.class);
        this.entityRunnableFactory = ServerRunnableLockFactory.getInstance(this, Entity.class);

        /* TODO: should be deleted after being published */
        if (FinalTech.getConfigManager().getOrDefault(false, "I'm_testing_it!")) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        /* Disabled if you installed this plugin just for slimefun addon quantity. Not disable it as being loaded before. */
        if (!FinalTech.getConfigManager().containPath("version") && Slimefun.getInstalledAddons().size() >= 70) {
            this.getLogger().warning("You have installed so many slimefun addons. Change the source code to load this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!FinalTech.getConfigManager().containPath("version") && Slimefun.getInstalledAddons().size() >= 20 && !FinalTech.getConfigManager().getOrDefault(false, "I'm_not_just_for_quantity_of_slimefun_addon_to_install_this_plugin")) {
            this.getLogger().warning("It seems you are just for quantity of slimefun addon to install this plugin.");
            this.getLogger().warning("So this plugin is disabled now.(Open config file and set true of 'I'm_not_just_for_quantity_of_slimefun_addon_to_install_this_plugin', as you want to enable this plugin.)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        } else if (!FinalTech.getConfigManager().containPath("version") && Slimefun.getInstalledAddons().size() >= 20 && !FinalTech.getConfigManager().getOrDefault(false, "I_know_there_may_be_incompatibilities_between_slimefun_addons_and_I_will_be_responsible_for_it")) {
            this.getLogger().warning("It seems you don't know there may be incompatibilities between slimefun addons or you don't want to be responsible for it.");
            this.getLogger().warning("So this plugin is disabled now due to possible incompatibilities.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // TODO: version update.(Now this is the first version being recorded and will be supported to update)
        if (!FinalTech.getConfigManager().containPath("version")) {
            FinalTech.getConfigManager().setValue(version, "version");
        }

        /* configure multi thread level */
        this.multiThreadLevel = FinalTech.getConfigManager().getOrDefault(0, "multi-thread", "level");
        if (this.multiThreadLevel > 2 || this.multiThreadLevel < 0) {
            this.multiThreadLevel = 0;
        }
        if (this.multiThreadLevel >= 1 && !FinalTech.getConfigManager().getOrDefault(false, "multi-thread", "warn-I_know_what_I'm_doing")) {
            this.getLogger().warning("It seems you don't know what you are doing. So multi thread level is set to 0");
            this.multiThreadLevel = 0;
        }
        if (this.multiThreadLevel >= 2 && !FinalTech.getConfigManager().getOrDefault(false, "multi-thread", "warn-I_really_know_what_I'm_doing")) {
            this.getLogger().warning("It seems you don't know what you are doing. So multi thread level is set to 0");
            this.multiThreadLevel = 0;
        }

        /* configure whether to force slimefun items to run async */
        this.forceSlimefunMultiThread = FinalTech.getConfigManager().getOrDefault(false, "force-slimefun-multi-thread", "enable");
        if (this.forceSlimefunMultiThread && !FinalTech.getConfigManager().getOrDefault(false, "force-slimefun-multi-thread", "warn-I_know_what_I'm_doing_and_I_will_be_responsible_for_it")) {
            this.getLogger().warning("It seems you don't know what you are doing. So force-slimefun-multi-thread.enable is set to false!");
            this.forceSlimefunMultiThread = false;
        }

        /* read tweak for machine */
        this.antiAccelerateSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "anti-accelerate"));
        this.performanceLimitSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "performance-limit"));
        this.asyncSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "force-async"));
        if (this.asyncSlimefunIdSet.size() > 0) {
            this.getLogger().warning("You set force-async for some SlimefunItems! It's ok but you should be aware that this may cause some strange error.");
        }

        /* run task timer to do some function */
        int tickRate = Slimefun.getTickerTask().getTickRate();
        this.bukkitTask = this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            private final AtomicLong currentTimeMillis = new AtomicLong();
            private final AtomicLong lastTimeMillis = new AtomicLong(System.currentTimeMillis());
            private final int FULL_SLIMEFUN_TICK = 20 * 50 * tickRate;

            @Override
            public void run() {
                this.currentTimeMillis.set(System.currentTimeMillis());
                FinalTech.instance.tps = Math.max(FULL_SLIMEFUN_TICK / (currentTimeMillis.get() - lastTimeMillis.get()), 20);
                this.lastTimeMillis.set(currentTimeMillis.get());
                FinalTech.instance.slimefunTickCount++;
            }
        }, 0, tickRate);

        SetupUtil.initLanguageManager(instance.languageManager);

        /* set up my items and menus and... */
        SetupUtil.init();

        /* setup item value table */
        this.getServer().getScheduler().runTaskLater(this, () -> ItemValueTable.getInstance().init(), FinalTech.getConfigManager().getOrDefault(10, "setups", "item-value-table", "delay"));

        /* setup slimefun machine block ticker */
        int blockTickerRegisterDelay = FinalTech.getConfigManager().getOrDefault(20, "setups", "slimefun-machine", "delay");
        if (blockTickerRegisterDelay > 0) {
            this.getServer().getScheduler().runTask(this, () -> SetupUtil.registerBlockTicker(0));
        } else {
            SetupUtil.registerBlockTicker(0);
        }
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        // TODO reload more...
    }

    @Override
    public void onDisable() {
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        }
        try {
            FinalTech.getLocationRunnableFactory().waitAllTask();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            BlockStorage.saveChunks();
            try {
                for (World world : Bukkit.getWorlds()) {
                    BlockStorage storage = BlockStorage.getStorage(world);
                    if (storage != null) {
                        storage.save();
                    }
                }
                BlockStorage.saveChunks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FinalTech.getEntityRunnableFactory().waitAllTask();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            BlockStorage.saveChunks();
            try {
                for (World world : Bukkit.getWorlds()) {
                    BlockStorage storage = BlockStorage.getStorage(world);
                    if (storage != null) {
                        storage.save();
                    }
                }
                BlockStorage.saveChunks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/ecro-fun/FinalTECH/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static FinalTech getInstance() {
        return instance;
    }

    public static Logger logger() {
        return instance.logger;
    }

    public static int getMultiThreadLevel() {
        return instance.multiThreadLevel;
    }

    public static boolean getForceSlimefunMultiThread() {
        return instance.forceSlimefunMultiThread;
    }

    public static int getSlimefunTickCount() {
        return instance.slimefunTickCount;
    }

    public static long getTps() {
        return instance.tps;
    }

    public static Random getRandom() {
        return instance.random;
    }

    public static ServerRunnableLockFactory<Location> getLocationRunnableFactory() {
        return instance.locationRunnableFactory;
    }

    public static ServerRunnableLockFactory<Entity> getEntityRunnableFactory() {
        return instance.entityRunnableFactory;
    }

    public static ConfigFileManager getConfigManager() {
        return instance.config;
    }

    public static ConfigFileManager getValueManager() {
        return instance.value;
    }

    public static ConfigFileManager getItemManager() {
        return instance.item;
    }

    public static LanguageManager getLanguageManager() {
        return instance.languageManager;
    }

    public static String getLanguageString(@Nonnull String... paths) {
        return instance.languageManager.getString(paths);
    }

    @Nonnull
    public static List<String> getLanguageStringList(@Nonnull String... paths) {
        return instance.languageManager.getStringList(paths);
    }

    @Nonnull
    public static String[] getLanguageStringArray(@Nonnull String... paths) {
        return instance.languageManager.getStringList(paths).toArray(new String[0]);
    }

    public static boolean isAsyncSlimefunItem(@Nonnull String id) {
        return instance.asyncSlimefunIdSet.contains(id);
    }

    public static boolean addAsyncSlimefunItem(@Nonnull String id) {
        return instance.asyncSlimefunIdSet.add(id);
    }

    public static boolean isAntiAccelerateSlimefunItem(@Nonnull String id) {
        // TODO config GUI
        return instance.antiAccelerateSlimefunIdSet.contains(id);
    }

    public static boolean isPerformanceLimitSlimefunItem(@Nonnull String id) {
        // TODO config GUI
        return instance.performanceLimitSlimefunIdSet.contains(id);
    }
}
