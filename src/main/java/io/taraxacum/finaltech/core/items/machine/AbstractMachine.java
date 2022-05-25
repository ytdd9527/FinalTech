package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.api.interfaces.PerformanceLimitMachine;
import io.taraxacum.finaltech.core.items.AbstractMySlimefunItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractMachine extends AbstractMySlimefunItem {
    private final AbstractMachineMenu menu;

    public AbstractMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.menu = this.setMachineMenu();
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(this.onBlockBreak());
        this.addItemHandler(this.onBlockPlace());
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
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, AbstractMachine.this.isSynchronized(), () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
//                        AbstractMachine.this.tick(block, slimefunItem, config);
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
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, AbstractMachine.this.isSynchronized(), () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
//                        AbstractMachine.this.tick(block, slimefunItem, config);
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
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, AbstractMachine.this.isSynchronized(), () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
//                        AbstractMachine.this.tick(block, slimefunItem, config);
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
                        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, AbstractMachine.this.isSynchronized(), () -> AbstractMachine.this.tick(block, slimefunItem, config), block.getLocation());
//                        AbstractMachine.this.tick(block, slimefunItem, config);
                    }

                    @Override
                    public boolean isSynchronized() {
                        return false;
                    }
                });
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
