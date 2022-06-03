package io.taraxacum.finaltech.core.items.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.menu.standard.BasicFrameMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class BasicFrameMachine extends AbstractStandardMachine {
    public BasicFrameMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new BasicFrameMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        MachineUtil.stockSlots(blockMenu, this.getInputSlot());
        for (int i = 0; i < this.getInputSlot().length && i < this.getOutputSlot().length; i++) {
            ItemStack inputItem = blockMenu.getItemInSlot(this.getInputSlot()[i]);
            ItemStack outputItem = blockMenu.getItemInSlot(this.getOutputSlot()[i]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if (ItemStackUtil.isItemNull(outputItem)) {
                blockMenu.toInventory().setItem(this.getOutputSlot()[i], new CustomItemStack(inputItem, inputItem.getAmount()));
                blockMenu.consumeItem(this.getInputSlot()[i], inputItem.getAmount());
            } else {
                ItemStackUtil.stack(inputItem, outputItem);
            }
        }
        MachineUtil.stockSlots(blockMenu, this.getOutputSlot());
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "将自身左侧的物品",
                TextUtil.COLOR_NORMAL + "移动到自身右侧");
    }
}
