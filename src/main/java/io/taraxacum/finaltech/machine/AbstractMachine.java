package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public abstract class AbstractMachine extends SlimefunItem {
    private final AbstractMachineMenu menu;

    public AbstractMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.menu = this.setMachineMenu();
    }

    @Nonnull
    protected abstract BlockPlaceHandler onBlockPlace();

    @Nonnull
    protected abstract BlockBreakHandler onBlockBreak();

    @Nonnull
    protected abstract AbstractMachineMenu setMachineMenu();

    /**
     * 获取机器容器中的输入槽位置
     * @return
     */
    @Nonnull
    protected final int[] getInputSlots() {
        return menu.getInputSlots();
    }

    /**
     * 获取机器容器中的输出槽位置
     * @return
     */
    @Nonnull
    protected final int[] getOutputSlots() {
        return menu.getOutputSlots();
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(this.onBlockBreak());
        this.addItemHandler(this.onBlockPlace());
        this.addItemHandler(new BlockTicker() {
            @Override
            public void tick(@Nonnull Block block, SlimefunItem slimefunItem, Config config) {
                AbstractMachine.this.tick(block, slimefunItem, config);
            }

            @Override
            public boolean isSynchronized() {
                return AbstractMachine.this.isSynchronized();
            }
        });
    }

    /**
     * 每粘液刻该机器调用的方法
     * @param block
     * @param slimefunItem
     * @param config
     */
    protected abstract void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config);

    /**
     * 是否是线程同步的
     * @return
     */
    protected abstract boolean isSynchronized();
}
