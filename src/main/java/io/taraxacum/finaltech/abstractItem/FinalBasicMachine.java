package io.taraxacum.finaltech.abstractItem;

import io.github.thebusybiscuit.slimefun4.api.items.*;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.menu.BasicMachineMenu;
import io.taraxacum.finaltech.util.FinalUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

public abstract class FinalBasicMachine extends FinalMachine {

    @ParametersAreNonnullByDefault
    protected FinalBasicMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(onBlockBreak());
    }

    protected final MachineMenu setMachineMenu() {
        return new BasicMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = this.getMachineProcessor().getOperation(b);

        if(currentOperation == null) {
            FinalUtil.stock(inv, this.getInputSlots());
            MachineRecipe next = this.findNextRecipe(inv);
            if (next != null) {
                if(next.getTicks() == 0) {
                    ItemStack[] outputItems = next.getOutput();
                    int outputItemLength = outputItems.length;
                    for(int i = 0; i < outputItemLength; ++i) {
                        ItemStack output = outputItems[i];
                        inv.pushItem(output.clone(), this.getOutputSlots());
                    }
                    FinalUtil.stock(inv, this.getOutputSlots());
                } else {
                    this.getMachineProcessor().startOperation(b, new CraftingOperation(next));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                int outputItemLength = outputItems.length;

                for(int i = 0; i < outputItemLength; ++i) {
                    ItemStack output = outputItems[i];
                    inv.pushItem(output.clone(), this.getOutputSlots());
                }

                this.getMachineProcessor().endOperation(b);
            } else {
                currentOperation.addProgress(1);
            }
        }
    }

    protected final MachineRecipe findNextRecipe(BlockMenu inv) {
        Map<Integer, ItemStack> inventory = new HashMap();
        int[] inputSlots = this.getInputSlots();
        int inputSlotLength = inputSlots.length;

        for(int i = 0; i < inputSlotLength; ++i) {
            int slot = inputSlots[i];
            ItemStack item = inv.getItemInSlot(slot);
            if (item != null) {
                inventory.put(slot, ItemStackWrapper.wrap(item));
            }
        }
        Map<Integer, Integer> found = new HashMap();
        Iterator iterator = this.recipes.iterator();

        while(iterator.hasNext()) {
            MachineRecipe recipe = (MachineRecipe)iterator.next();
            ItemStack[] inputItem = recipe.getInput();
            int inputLength = inputItem.length;

            for(int i = 0; i < inputLength; ++i) {
                ItemStack input = inputItem[i];
                for(int j = 0; j < inputSlotLength; ++j) {
                    int slot = inputSlots[j];
                    if (SlimefunUtils.isItemSimilar(inventory.get(slot), input, true)) {
                        found.put(slot, input.getAmount());
                        break;
                    }
                }
            }

            if (found.size() == recipe.getInput().length) {
                if (!InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    return null;
                }
                Iterator iterator2 = found.entrySet().iterator();

                while(iterator2.hasNext()) {
                    Map.Entry<Integer, Integer> entry = (Map.Entry)iterator2.next();
                    inv.consumeItem(entry.getKey(), entry.getValue());
                }
                return recipe;
            }
            found.clear();
        }
        return null;
    }
}
