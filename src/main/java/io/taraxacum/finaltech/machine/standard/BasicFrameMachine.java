package io.taraxacum.finaltech.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.menu.standard.BasicFrameMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
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
        int[] inputSlots = this.getInputSlots();
        int[] outputSlots = this.getOutputSlots();
        for (int i = 0; i < inputSlots.length && i < outputSlots.length; i++) {
            ItemStack inputItem = blockMenu.getItemInSlot(inputSlots[i]);
            ItemStack outputItem = blockMenu.getItemInSlot(outputSlots[i]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if (ItemStackUtil.isItemNull(outputItem)) {
                blockMenu.toInventory().setItem(outputSlots[i], new CustomItemStack(inputItem, inputItem.getAmount()));
                blockMenu.consumeItem(inputSlots[i], inputItem.getAmount());
            } else {
                ItemStackUtil.stack(inputItem, outputItem);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {

    }
}
