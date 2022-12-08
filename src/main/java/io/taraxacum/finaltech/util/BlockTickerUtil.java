package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.common.api.RunnableLockFactory;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BlockTickerUtil {
    @SafeVarargs
    public static <T> void runTask(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, boolean async, @Nonnull Runnable runnable, T... locks) {
        if (async) {
            serverRunnableLockFactory.waitThenRun(runnable, locks);
        } else {
            runnable.run();
        }
    }

    public static void setSleep(@Nonnull Config config, @Nullable Double sleep) {
        config.setValue(ConstantTableUtil.CONFIG_SLEEP, sleep);
    }

    public static void setSleep(@Nonnull Config config, @Nullable String sleep) {
        config.setValue(ConstantTableUtil.CONFIG_SLEEP, sleep);
    }

    public static void setSleep(@Nonnull Location location, @Nullable String sleep) {
        BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_SLEEP, sleep);
    }

    public static boolean hasSleep(@Nonnull Config config) {
        return config.contains(ConstantTableUtil.CONFIG_SLEEP);
    }

    public static double getSleep(@Nonnull Config config) {
        return Double.parseDouble(config.getString(ConstantTableUtil.CONFIG_SLEEP));
    }

    public static void subSleep(@Nonnull Config config) {
        if (config.contains(ConstantTableUtil.CONFIG_SLEEP)) {
            double sleep = BlockTickerUtil.getSleep(config) - 1;
            if (sleep > 0) {
                config.setValue(ConstantTableUtil.CONFIG_SLEEP, String.valueOf(sleep));
            } else {
                config.setValue(ConstantTableUtil.CONFIG_SLEEP, null);
            }
        }
    }

    public static BlockTicker getGeneralIntervalBlockTicker(@Nonnull BlockTicker blockTicker, int interval) {
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return blockTicker.isSynchronized();
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                if(FinalTech.getSlimefunTickCount() % interval == 0) {
                    blockTicker.tick(b, item, data);
                }
            }
        };
    }

    public static BlockTicker getIndependentIntervalBlockTicker(@Nonnull BlockTicker blockTicker, int interval) {
        String i = String.valueOf(--interval);
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                if(BlockTickerUtil.hasSleep(data)) {
                    BlockTickerUtil.subSleep(data);
                }
                blockTicker.tick(b, item, data);
                BlockTickerUtil.setSleep(data, i);
            }
        };
    }

    @Nonnull
    public static BlockTicker getDebugModeBlockTicker(@Nonnull BlockTicker blockTicker, @Nonnull SlimefunItem slimefunItem) {
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return blockTicker.isSynchronized();
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                System.out.println("DEBUG MODE: " + slimefunItem.getId() + " | Location: " + b.getLocation());
                blockTicker.tick(b, item, data);
            }
        };
    }

    @Nonnull
    public static BlockTicker generateBlockTicker(@Nonnull BlockTicker blockTicker, boolean forceAsync, boolean antiAcceleration, boolean performanceLimit) {
        if (forceAsync && antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data) && PerformanceLimitUtil.charge(data)) {
                        this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }
            };
        } else if (forceAsync && antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data)) {
                        this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }
            };
        } else if (forceAsync && !antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (PerformanceLimitUtil.charge(data)) {
                        this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }
            };
        } else if (forceAsync && !antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTech.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                }
            };
        } else if (!forceAsync && antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data) && PerformanceLimitUtil.charge(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }
            };
        } else if (!forceAsync && antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }
            };
        } else if (!forceAsync && !antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (PerformanceLimitUtil.charge(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }
            };
        } else {
            return blockTicker;
        }
    }
}
