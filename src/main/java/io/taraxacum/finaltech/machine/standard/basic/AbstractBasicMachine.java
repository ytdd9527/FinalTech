package io.taraxacum.finaltech.machine.standard.basic;

import io.github.thebusybiscuit.slimefun4.api.items.*;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.standard.BasicMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ItemStackWithWrapper;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * @author Final_ROOT
 */
public abstract class AbstractBasicMachine extends AbstractStandardMachine {
    private static final String OFFSET_KEY = "offset";
    @ParametersAreNonnullByDefault
    protected AbstractBasicMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    @Nonnull
    @Override
    protected final AbstractMachineMenu setMachineMenu() {
        return new BasicMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected final void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int offset = config.contains(OFFSET_KEY) ? Integer.parseInt(config.getString(OFFSET_KEY)) : 0;

        CraftingOperation currentOperation = (CraftingOperation) this.getMachineProcessor().getOperation(block);

        if(currentOperation == null) {
            MachineUtil.stockSlots(blockMenu, this.getInputSlots());
            MachineRecipe machineRecipe = this.matchRecipe(blockMenu, offset);
            if (machineRecipe != null) {
                if(machineRecipe.getTicks() == 0) {
                    ItemStack[] outputItems = machineRecipe.getOutput();
                    for(ItemStack output : outputItems) {
                        blockMenu.pushItem(ItemStackUtil.cloneItem(output), this.getOutputSlots());
                    }
                } else {
                    this.getMachineProcessor().startOperation(block, new CraftingOperation(machineRecipe));
                }
            }
            MachineUtil.stockSlots(blockMenu, this.getOutputSlots());
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                if(InvUtils.fitAll(blockMenu.toInventory(), outputItems, this.getOutputSlots())) {
                    for(ItemStack output : outputItems) {
                        if(output instanceof ItemStackWrapper) {
                            blockMenu.pushItem(new ItemStack(output), this.getOutputSlots());
                        } else {
                            blockMenu.pushItem(output.clone(), this.getOutputSlots());
                        }
                    }
                    this.getMachineProcessor().endOperation(block);
                }
                MachineUtil.stockSlots(blockMenu, this.getOutputSlots());
            } else {
                currentOperation.addProgress(1);
            }
        }
    }

    protected final MachineRecipe matchRecipe(BlockMenu blockMenu, int offset) {
        Map<Integer, ItemStackWithWrapper> inputItemWithWrapperMap = new HashMap<>(getInputSlots().length);
        for (int slot : this.getInputSlots()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                inputItemWithWrapperMap.put(slot, new ItemStackWithWrapper(item));
            }
        }

        Map<Integer, Integer> matchItemMap = new HashMap<>(inputItemWithWrapperMap.size());

        int length = this.getMachineRecipes().size();
        for(int i = 0; i < length; i++) {
            MachineRecipe machineRecipe = this.getMachineRecipes().get((i + offset) % length);
            ItemStack[] recipeInputItems = machineRecipe.getInput();
            List<ItemStackWithWrapper> recipeInputItemWithWrapperList = new ArrayList<>(recipeInputItems.length);

            for (Map.Entry<Integer, ItemStackWithWrapper> inputItemWithWrapper : inputItemWithWrapperMap.entrySet()) {
                int match = 0;
                for(int j = match; j < recipeInputItems.length; j++) {
                    ItemStackWithWrapper recipeInputItemWithWrapper;
                    if(j < recipeInputItemWithWrapperList.size()) {
                        recipeInputItemWithWrapper = recipeInputItemWithWrapperList.get(j);
                    } else {
                        recipeInputItemWithWrapper = new ItemStackWithWrapper(recipeInputItems[j]);
                        recipeInputItemWithWrapperList.add(recipeInputItemWithWrapper);
                    }
                    if(inputItemWithWrapper.getValue().getItemStack().getAmount() >= recipeInputItemWithWrapper.getItemStack().getAmount() && ItemStackUtil.isItemSimilar(recipeInputItemWithWrapper.getItemStackWrapper(), inputItemWithWrapper.getValue().getItemStackWrapper())) {
                        match++;
                        recipeInputItemWithWrapperList.set(j, ItemStackWithWrapper.NULL_ITEM);
                        matchItemMap.put(inputItemWithWrapper.getKey(), recipeInputItemWithWrapper.getItemStack().getAmount());
                    }
                }
            }
            if (matchItemMap.size() == machineRecipe.getInput().length) {
                if (!InvUtils.fitAll(blockMenu.toInventory(), machineRecipe.getOutput(), this.getOutputSlots())) {
                    matchItemMap.clear();
                    continue;
                }
                for (Map.Entry<Integer, Integer> integerIntegerEntry : matchItemMap.entrySet()) {
                    blockMenu.consumeItem(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                }
                BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, String.valueOf((i + offset) % length));
                return machineRecipe;
            }
            matchItemMap.clear();
        }
        return null;
    }
}
