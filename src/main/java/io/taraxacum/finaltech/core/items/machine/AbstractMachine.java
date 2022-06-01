package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.api.interfaces.PerformanceLimitMachine;
import io.taraxacum.finaltech.core.items.AbstractMySlimefunItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractMachine extends AbstractMySlimefunItem {
    private final AbstractMachineMenu menu;

    /**
     * 0: nothing change, all task will run at slimefun #{@link io.github.thebusybiscuit.slimefun4.implementation.tasks.TickerTask}
     * 1: async task will be put in #{@link BlockTaskFactory}, so they will be really async
     * 2: sync task will be run as async, so all task will be put in #{@link BlockTaskFactory}
     */
    public static int MULTI_THREAD_LEVEL = 0;

    public AbstractMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.menu = this.setMachineMenu();
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(this.onBlockBreak());
        this.addItemHandler(this.onBlockPlace());

        if(AbstractMachine.MULTI_THREAD_LEVEL == 0) {
            this.getAddon().getJavaPlugin().getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + ItemStackUtil.getItemName(this.getItem()) + TextUtil.COLOR_NEGATIVE + "已禁用多线程优化");
        } else if(AbstractMachine.MULTI_THREAD_LEVEL == 1) {
            this.getAddon().getJavaPlugin().getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + ItemStackUtil.getItemName(this.getItem()) + TextUtil.COLOR_NEGATIVE + "已启用一级多线程优化");
        } else if(AbstractMachine.MULTI_THREAD_LEVEL == 2) {
            this.getAddon().getJavaPlugin().getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + ItemStackUtil.getItemName(this.getItem()) + TextUtil.COLOR_NEGATIVE + "二级多线程优化暂不支持");
        } else {
            AbstractMachine.MULTI_THREAD_LEVEL = 0;
            this.getAddon().getJavaPlugin().getServer().getLogger().info(TextUtil.COLOR_STRESS + "[FINALTECH]" + ItemStackUtil.getItemName(this.getItem()) + TextUtil.COLOR_NEGATIVE + "已禁用多线程优化");
        }

        if(MULTI_THREAD_LEVEL == 2) {
            if (this instanceof AntiAccelerationMachine) {
                if (this instanceof PerformanceLimitMachine) {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                                return;
                            }
                            if (((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                                return;
                            }
                            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                        }

                        @Override
                        public boolean isSynchronized() {
                            return false;
                        }
                    });
                } else {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            if (((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                                return;
                            }
                            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                        }

                        @Override
                        public boolean isSynchronized() {
                            return false;
                        }
                    });
                }
            } else {
                if (this instanceof PerformanceLimitMachine) {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                                return;
                            }
                            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                        }

                        @Override
                        public boolean isSynchronized() {
                            return false;
                        }
                    });
                } else {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                        }

                        @Override
                        public boolean isSynchronized() {
                            return false;
                        }
                    });
                }
            }
        } else if(MULTI_THREAD_LEVEL == 1) {
            if(this instanceof AntiAccelerationMachine && this instanceof PerformanceLimitMachine && this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        if (!((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                            return;
                        }
                        if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                            return;
                        }
                        AbstractMachine.this.tick(block, slimefunItem, config);
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            } else if(this instanceof AntiAccelerationMachine && this instanceof PerformanceLimitMachine && !this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        if (!((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                            return;
                        }
                        if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                            return;
                        }
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            } else if(this instanceof AntiAccelerationMachine && !(this instanceof PerformanceLimitMachine) && this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        if (!((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                            return;
                        }
                        AbstractMachine.this.tick(block, slimefunItem, config);
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            } else if(this instanceof AntiAccelerationMachine && !(this instanceof PerformanceLimitMachine) && !this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        if (!((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                            return;
                        }
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            } else if(!(this instanceof AntiAccelerationMachine) && this instanceof PerformanceLimitMachine && this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                            return;
                        }
                        AbstractMachine.this.tick(block, slimefunItem, config);
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            } else if(!(this instanceof AntiAccelerationMachine) && this instanceof PerformanceLimitMachine && !this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                            return;
                        }
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            } else if(!(this instanceof AntiAccelerationMachine) && !(this instanceof PerformanceLimitMachine) && this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        AbstractMachine.this.tick(block, slimefunItem, config);
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            } else if(!(this instanceof AntiAccelerationMachine) && !(this instanceof PerformanceLimitMachine) && !this.isSynchronized()) {
                this.addItemHandler(new BlockTicker() {
                    @Override
                    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
                    }

                    @Override
                    public boolean isSynchronized() {
                        return true;
                    }
                });
            }
        } else {
            if (this instanceof AntiAccelerationMachine) {
                if (this instanceof PerformanceLimitMachine) {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                                return;
                            }
                            if (((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                                return;
                            }
                            AbstractMachine.this.tick(block, slimefunItem, config);
                        }

                        @Override
                        public boolean isSynchronized() {
                            return AbstractMachine.this.isSynchronized();
                        }
                    });
                } else {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            if (((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
                                return;
                            }
                            AbstractMachine.this.tick(block, slimefunItem, config);
                        }

                        @Override
                        public boolean isSynchronized() {
                            return AbstractMachine.this.isSynchronized();
                        }
                    });
                }
            } else {
                if (this instanceof PerformanceLimitMachine) {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            if (!((PerformanceLimitMachine)AbstractMachine.this).charge(config)) {
                                return;
                            }
                            AbstractMachine.this.tick(block, slimefunItem, config);
                        }

                        @Override
                        public boolean isSynchronized() {
                            return AbstractMachine.this.isSynchronized();
                        }
                    });
                } else {
                    this.addItemHandler(new BlockTicker() {
                        @Override
                        public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                            AbstractMachine.this.tick(block, slimefunItem, config);
                        }

                        @Override
                        public boolean isSynchronized() {
                            return AbstractMachine.this.isSynchronized();
                        }
                    });
                }
            }
        }
    }

    @Nonnull
    public final int[] getInputSlot() {
        return this.menu.getInputSlot();
    }

    @Nonnull
    public final int[] getOutputSlot() {
        return this.menu.getOutputSlot();
    }

    @Nonnull
    protected abstract BlockPlaceHandler onBlockPlace();

    @Nonnull
    protected abstract BlockBreakHandler onBlockBreak();

    @Nonnull
    protected abstract AbstractMachineMenu setMachineMenu();

    protected abstract void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config);

    protected abstract boolean isSynchronized();

    protected BlockTicker blockTicker() {
        return null;
    }
}
