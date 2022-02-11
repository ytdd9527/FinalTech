package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractStandardMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.menu.BasicFrameMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BasicFrameMachine extends AbstractStandardMachine {
    public BasicFrameMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new BasicFrameMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block block) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int[] inputSlots = this.getInputSlots();
        int[] outputSlots = this.getOutputSlots();
        for(int i = 0; i < inputSlots.length && i < outputSlots.length; i++) {
            ItemStack inputItem = blockMenu.getItemInSlot(inputSlots[i]);
            ItemStack outputItem = blockMenu.getItemInSlot(outputSlots[i]);
            if(inputItem == null) {
                continue;
            }
            if(outputItem == null) {
                blockMenu.toInventory().setItem(outputSlots[i], new CustomItemStack(inputItem, inputItem.getAmount()));
                blockMenu.consumeItem(inputSlots[i], inputItem.getAmount());
            } else if(outputItem.getAmount() < outputItem.getMaxStackSize() && SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                ItemStackUtil.stack(inputItem, outputItem);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() { }
}
