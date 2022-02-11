package io.taraxacum.finaltech.abstractItem.machine;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractMachine extends SlimefunItem {
    private final AbstractMachineMenu menu;
    public AbstractMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.menu = this.setMachineMenu();
        this.addItemHandler(this.onBlockBreak());
    }

    /**
     * 用于注册当方块被破坏时的事件
     * 通常会使得方块对应容器内的物品掉落
     * 可能需要重写
     * @return
     */
    @Nonnull
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            @Override
            public void onBlockBreak(Block block) {
                BlockMenu inv = BlockStorage.getInventory(block);
                if (inv != null) {
                    inv.dropItems(block.getLocation(), getInputSlots());
                    inv.dropItems(block.getLocation(), getOutputSlots());
                }
            }
        };
    }

    /**
     * 设置机器的菜单
     * @return
     */
    protected abstract AbstractMachineMenu setMachineMenu();

    /**
     * 获取机器容器中的输入槽位置
     * @return
     */
    protected final int[] getInputSlots() {
        return menu.getInputSlots();
    }

    /**
     * 获取机器容器中的输出槽位置
     * @return
     */
    protected final int[] getOutputSlots() {
        return menu.getOutputSlots();
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        this.addon = addon;
        super.register(addon);
    }

    @Override
    public void preRegister() {
        super.preRegister();
    }

    /**
     * 每粘液刻，该机器调用的方法
     * @param block
     */
    protected abstract void tick(Block block);
}
