package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.setup.Updater;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.setup.SetupUtil;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutionException;
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
    private double tps = 20;
    private boolean debugMode = false;
    private CustomLogger logger;
    private ServerRunnableLockFactory<Location> locationRunnableFactory;
    private ServerRunnableLockFactory<Entity> entityRunnableFactory;
    private ConfigFileManager config;
    private ConfigFileManager value;
    private ConfigFileManager item;
    private LanguageManager languageManager;
    private Set<String> asyncSlimefunIdSet = new HashSet<>();
    private Set<String> antiAccelerateSlimefunIdSet = new HashSet<>();
    private Set<String> performanceLimitSlimefunIdSet = new HashSet<>();
    private Set<String> noBlockTickerSlimefunIdSet = new HashSet<>();
    private Set<String> asyncSlimefunPluginSet = new HashSet<>();
    private Set<String> antiAccelerateSlimefunPluginSet = new HashSet<>();
    private Set<String> performanceLimitSlimefunPluginSet = new HashSet<>();
    private Set<String> noBlockTickerSlimefunPluginSet = new HashSet<>();
    private Random random;
    private long seed;
    private BukkitTask bukkitTask;
    private final String version = Updater.LATEST_VERSION;
    private static FinalTech instance;

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

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

        /* set language manager */
        SetupUtil.setupLanguageManager(this.languageManager);

        /* set logger */
        this.logger = CustomLogger.newInstance(this.getJavaPlugin().getServer().getLogger());
        this.logger.setBanner("[" + this.languageManager.getOrDefault("FinalTECH", "FinalTech") + "] ");

        /* set version */
        if (!this.config.containPath("version")) {
            this.config.setValue(version, "version");
            if(!this.config.containPath("enable", "item")) {
                this.config.setValue(true, "enable", "item");
            }
        }

        /* update the config file */
        if(this.config.getOrDefault(true, "update", "enable")) {
            this.logger.info("You have enabled the config updater.");
            Updater updater = Updater.getInstance();
            try {
                updater.update(this);
            } catch (Exception e) {
                e.printStackTrace();
                this.logger.warning("Some error occurred while doing update..");
            }
        } else {
            this.logger.info("You have disabled the config updater.");
        }

        /* set random seed */
        this.seed = this.config.getOrDefault(new Random().nextLong(Long.MAX_VALUE), "seed");
        this.random = new Random(this.seed);

        /* set debug mode */
        this.debugMode = this.config.getOrDefault(false, "debug-mode");
        if(this.debugMode) {
            this.logger.warning("You have debug mode on!");
        }

        /* set runnable factory */
        this.locationRunnableFactory = ServerRunnableLockFactory.getInstance(this, Location.class);
        this.entityRunnableFactory = ServerRunnableLockFactory.getInstance(this, Entity.class);

        /* configure multi thread level */
        this.multiThreadLevel = this.config.getOrDefault(0, "multi-thread", "level");
        if (this.multiThreadLevel > 2 || this.multiThreadLevel < 0) {
            this.multiThreadLevel = 0;
        }
        if (this.multiThreadLevel >= 1 && !this.config.getOrDefault(false, "multi-thread", "warn-I_know_what_I'm_doing")) {
            this.logger.warning("It seems you don't know what you are doing. So multi thread level is set to 0");
            this.multiThreadLevel = 0;
        }
        if (this.multiThreadLevel >= 2 && !this.config.getOrDefault(false, "multi-thread", "warn-I_really_know_what_I'm_doing")) {
            this.logger.warning("It seems you don't know what you are doing. So multi thread level is set to 0");
            this.multiThreadLevel = 0;
        }

        /* configure whether to force slimefun items to run async */
        this.forceSlimefunMultiThread = this.config.getOrDefault(false, "force-slimefun-multi-thread", "enable");
        if (this.forceSlimefunMultiThread && !this.config.getOrDefault(false, "force-slimefun-multi-thread", "warn-I_know_what_I'm_doing_and_I_will_be_responsible_for_it")) {
            this.logger.warning("It seems you don't know what you are doing. So force-slimefun-multi-thread.enable is set to false!");
            this.forceSlimefunMultiThread = false;
        }

        /* read tweak for machine */
        this.antiAccelerateSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "anti-accelerate", "item"));
        this.performanceLimitSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "performance-limit", "item"));
        this.noBlockTickerSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "no-blockTicker", "item"));
        this.asyncSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "force-async", "item"));
        this.antiAccelerateSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "anti-accelerate", "addon"));
        this.performanceLimitSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "performance-limit", "addon"));
        this.noBlockTickerSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "no-blockTicker", "addon"));
        this.asyncSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "force-async", "addon"));

        if (this.asyncSlimefunPluginSet.size() > 0 || this.asyncSlimefunIdSet.size() > 0) {
            this.logger.warning("You set force-async for some SlimefunItems! It's ok but you should be aware that this may cause some strange error.");
        }

        /* run task timer to do some function */
        int tickRate = Slimefun.getTickerTask().getTickRate();
        this.bukkitTask = this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            private long currentNanoTime = System.nanoTime();
            private long lastNanoTime = System.nanoTime();
            private final BigDecimal FULL_SLIMEFUN_TICK = new BigDecimal(StringNumberUtil.mul("1000000000", String.valueOf(tickRate)));

            @Override
            public void run() {
                currentNanoTime = System.nanoTime();
                FinalTech.instance.tps = Math.min(FULL_SLIMEFUN_TICK.divide(BigDecimal.valueOf(Math.max(1, currentNanoTime - lastNanoTime)), 10, RoundingMode.FLOOR).doubleValue(), 20);
                lastNanoTime = currentNanoTime;

                FinalTech.instance.slimefunTickCount++;
            }
        }, 0, tickRate);

        /* set up my items and menus and... */
        SetupUtil.init();

        this.performanceLimitSlimefunIdSet.add(FinalTechItems.ORDERED_DUST_FACTORY_DIRT.getItemId());
        this.performanceLimitSlimefunIdSet.add(FinalTechItems.ORDERED_DUST_FACTORY_STONE.getItemId());

        /* mark for some machines */
        this.antiAccelerateSlimefunIdSet.add(FinalTechItems.ENERGIZED_ACCELERATOR.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItems.OVERLOADED_ACCELERATOR.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItems.ITEM_DESERIALIZE_PARSER.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItems.ENTROPY_SEED.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItems.EQUIVALENT_CONCEPT.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItems.MATRIX_ACCELERATOR.getItemId());

        /* setup template machine */
        int templateMachineDelay = this.config.getOrDefault(0, "setups", "template-machine", "delay");
        if(templateMachineDelay > 0) {
            this.getServer().getScheduler().runTaskLater(this, () -> new TemplateParser(FinalTech.this.template, false, false).registerMachine(), templateMachineDelay);
        } else {
            new TemplateParser(this.template, false, false).registerMachine();

        }

        /* setup item value table */
        int itemValueTableDelay = this.config.getOrDefault(10, "setups", "item-value-table", "delay");
        if(itemValueTableDelay > 0) {
            this.getServer().getScheduler().runTaskLater(this, () -> ItemValueTable.getInstance().init(), itemValueTableDelay);
        } else {
            ItemValueTable.getInstance().init();
        }

        /* setup slimefun machine block ticker */
        int blockTickerRegisterDelay = this.config.getOrDefault(20, "setups", "slimefun-machine", "delay");
        if (blockTickerRegisterDelay > 0) {
            this.getServer().getScheduler().runTaskLater(this, SetupUtil::registerBlockTicker, blockTickerRegisterDelay);
        } else {
            SetupUtil.registerBlockTicker();
        }

        /* setup bstats */
        Metrics metrics = new Metrics(this, 16920);
        metrics.addCustomChart(new AdvancedPie("multi_thread_level", () -> {
            Map<String, Integer> result = new LinkedHashMap<>();
            result.put(String.valueOf(FinalTech.getMultiThreadLevel()), 1);
            return result;
        }));
        metrics.addCustomChart(new AdvancedPie("languages", () -> {
            Map<String, Integer> result = new HashMap<>();
            result.put(FinalTech.getConfigManager().getString("language"), 1);
            return result;
        }));
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        // TODO reload more...
    }

    @Override
    public void onDisable() {
        ServerRunnableLockFactory.stop();
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        }
        BlockStorage.saveChunks();
        try {
            FinalTech.logger().info("Waiting all task to end.(" + FinalTech.getLocationRunnableFactory().taskSize() + ")");
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

    public static boolean debugMode() {
        return instance.debugMode;
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

    public static double getTps() {
        return instance.tps;
    }

    public static long getSeed() {
        return instance.seed;
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

    public static boolean addAntiAccelerateSlimefunItem(@Nonnull String id) {
        return instance.antiAccelerateSlimefunIdSet.add(id);
    }

    public static boolean isPerformanceLimitSlimefunItem(@Nonnull String id) {
        // TODO config GUI
        return instance.performanceLimitSlimefunIdSet.contains(id);
    }

    public static boolean addPerformanceLimitSlimefunItem(@Nonnull String id) {
        return instance.performanceLimitSlimefunIdSet.add(id);
    }

    public static boolean isNoBlockTickerSlimefunItem(@Nonnull String id) {
        // TODO config GUI
        return instance.noBlockTickerSlimefunIdSet.contains(id);
    }

    public static boolean addNoBlockTickerSlimefunItem(@Nonnull String id) {
        return instance.noBlockTickerSlimefunIdSet.add(id);
    }

    public static Set<String> getAsyncSlimefunPluginSet() {
        return instance.asyncSlimefunPluginSet;
    }

    public static Set<String> getAntiAccelerateSlimefunPluginSet() {
        return instance.antiAccelerateSlimefunPluginSet;
    }

    public static Set<String> getPerformanceLimitSlimefunPluginSet() {
        return instance.performanceLimitSlimefunPluginSet;
    }

    public static Set<String> getNoBlockTickerSlimefunPluginSet() {
        return instance.noBlockTickerSlimefunPluginSet;
    }
}
