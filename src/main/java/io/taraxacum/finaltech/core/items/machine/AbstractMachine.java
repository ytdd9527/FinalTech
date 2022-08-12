package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.api.interfaces.PerformanceLimitMachine;
import io.taraxacum.finaltech.core.items.AbstractMySlimefunItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.SetupUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractMachine extends AbstractMySlimefunItem {
    private final AbstractMachineMenu menu;


    public AbstractMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.menu = this.setMachineMenu();
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(this.onBlockBreak());
        this.addItemHandler(this.onBlockPlace());

        // TODO
        if (FinalTech.getMultiThreadLevel() == 2) {
            this.getAddon().getJavaPlugin().getLogger().info(this.getItemName() + "§f is optimized for multithreading！！！");
        } else if (!this.isSynchronized() && (FinalTech.getMultiThreadLevel() == 1 || FinalTech.isAsyncSlimefunItem(this.getId()))) {
            this.getAddon().getJavaPlugin().getLogger().info(this.getItemName() + "§f is optimized for multithreading！！！");
        }

        if(FinalTech.getMultiThreadLevel() == 2) {
            this.addItemHandler(SetupUtil.generateBlockTicker(new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    AbstractMachine.this.tick(b, item, data);
                }
            }, true, FinalTech.isAntiAccelerateSlimefunItem(this.getId()), FinalTech.isPerformanceLimitSlimefunItem(this.getId())));
        } else {
            this.addItemHandler(SetupUtil.generateBlockTicker(new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return AbstractMachine.this.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    AbstractMachine.this.tick(b, item, data);
                }
            }, !this.isSynchronized() && (FinalTech.getMultiThreadLevel() == 1 || FinalTech.isAsyncSlimefunItem(this.getId())), FinalTech.isAntiAccelerateSlimefunItem(this.getId()), FinalTech.isPerformanceLimitSlimefunItem(this.getId())));
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
}
