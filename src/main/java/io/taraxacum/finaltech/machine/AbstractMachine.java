package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractMachine extends SlimefunItem {
    private final AbstractMachineMenu menu;

    public AbstractMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.menu = this.setMachineMenu();
        if(this instanceof RecipeItem) {
            ((RecipeItem) this).registerDefaultRecipes();
        }
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(this.onBlockBreak());
        this.addItemHandler(this.onBlockPlace());
        if(this instanceof AntiAccelerationMachine) {
            this.addItemHandler(new BlockTicker() {
                @Override
                public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
                    if(((AntiAccelerationMachine)AbstractMachine.this).isAccelerated(config)) {
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

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
    }

    public void register() {
        this.register(JavaPlugin.getPlugin(FinalTech.class));
    }

    @Nonnull
    protected abstract BlockPlaceHandler onBlockPlace();

    @Nonnull
    protected abstract BlockBreakHandler onBlockBreak();

    @Nonnull
    protected abstract AbstractMachineMenu setMachineMenu();

    @Nonnull
    public final int[] getInputSlots() {
        return this.menu.getInputSlots();
    }

    @Nonnull
    public final int[] getOutputSlots() {
        return this.menu.getOutputSlots();
    }

    protected abstract void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config);

    protected abstract boolean isSynchronized();
}
