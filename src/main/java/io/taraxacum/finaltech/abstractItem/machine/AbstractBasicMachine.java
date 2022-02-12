package io.taraxacum.finaltech.abstractItem.machine;

import io.github.thebusybiscuit.slimefun4.api.items.*;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.BasicMachineMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * @author Final_ROOT
 */
public abstract class AbstractBasicMachine extends AbstractStandardMachine {
    @ParametersAreNonnullByDefault
    protected AbstractBasicMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(onBlockBreak());
        this.registerDefaultRecipes();
    }

    @Override
    protected final AbstractMachineMenu setMachineMenu() {
        return new BasicMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = this.getMachineProcessor().getOperation(b);

        if(currentOperation == null) {
            MachineUtil.stockSlots(inv, this.getInputSlots());
            MachineRecipe next = this.findNextRecipe(inv);
            if (next != null) {
                if(next.getTicks() == 0) {
                    ItemStack[] outputItems = next.getOutput();
                    if(InvUtils.fitAll(inv.toInventory(), outputItems, this.getOutputSlots())) {
                        for(ItemStack item : outputItems) {
                            inv.pushItem(item.clone(), this.getOutputSlots());
                        }
                    }
                    MachineUtil.stockSlots(inv, this.getOutputSlots());
                } else {
                    this.getMachineProcessor().startOperation(b, new CraftingOperation(next));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                if(InvUtils.fitAll(inv.toInventory(), outputItems, this.getOutputSlots())) {
                    for(ItemStack item : outputItems) {
                        inv.pushItem(item.clone(), this.getOutputSlots());
                    }
                    this.getMachineProcessor().endOperation(b);
                }
                MachineUtil.stockSlots(inv, this.getOutputSlots());
            } else {
                currentOperation.addProgress(1);
            }
        }
    }

    protected final MachineRecipe findNextRecipe(BlockMenu inv) {
        Map<Integer, ItemStack> inventory = new HashMap<>();
        int[] inputSlots = this.getInputSlots();

        for (int slot : inputSlots) {
            ItemStack item = inv.getItemInSlot(slot);
            if (item != null) {
                inventory.put(slot, ItemStackWrapper.wrap(item));
            }
        }
        Map<Integer, Integer> found = new HashMap<>();

        for (MachineRecipe recipe : this.recipes) {
            ItemStack[] inputItem = recipe.getInput();
            for (ItemStack input : inputItem) {
                for (int slot : inputSlots) {
                    if (SlimefunUtils.isItemSimilar(inventory.get(slot), input, true, true)) {
                        found.put(slot, input.getAmount());
                        break;
                    }
                }
            }
            if (found.size() == recipe.getInput().length) {
                if (!InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    return null;
                }
                for (Map.Entry<Integer, Integer> integerIntegerEntry : found.entrySet()) {
                    inv.consumeItem(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                }
                return recipe;
            }
            found.clear();
        }
        return null;
    }
}
