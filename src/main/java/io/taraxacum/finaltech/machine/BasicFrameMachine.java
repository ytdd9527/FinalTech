package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.FinalMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;
import io.taraxacum.finaltech.menu.BasicFrameMachineMenu;
import io.taraxacum.finaltech.util.CargoUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class BasicFrameMachine extends FinalMachine {
    public BasicFrameMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected MachineMenu setMachineMenu() {
        return new BasicFrameMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_BASIC_FRAME_MACHINE";
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
                int count = Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount());
                CargoUtil.changeItemAmount(inputItem, outputItem, count);
            }
        }
    }
}
