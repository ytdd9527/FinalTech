package io.taraxacum.finaltech.machine.standard.basic;

import io.github.thebusybiscuit.slimefun4.api.items.*;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.standard.BasicMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
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
    }

    @Nonnull
    @Override
    protected final AbstractMachineMenu setMachineMenu() {
        return new BasicMachineMenu(this);
    }

    @Override
    protected final void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int offset = config.contains(OFFSET_KEY) ? Integer.parseInt(config.getString(OFFSET_KEY)) : 0;

        CraftingOperation currentOperation = (CraftingOperation) this.getMachineProcessor().getOperation(block);

        if (currentOperation == null) {
            MachineUtil.stockSlots(blockMenu, this.getInputSlots());
            MachineRecipe machineRecipe = this.matchRecipe(blockMenu, offset);
            if (machineRecipe != null) {
                if (machineRecipe.getTicks() == 0) {
                    ItemStack[] outputItems = machineRecipe.getOutput();
                    for (ItemStack output : outputItems) {
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
                if (InvUtils.fitAll(blockMenu.toInventory(), outputItems, this.getOutputSlots())) {
                    for (ItemStack output : outputItems) {
                        if (output instanceof ItemStackWrapper) {
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
        Map<Integer, ItemStackWithWrapper> inputItemWithWrapperMap = MachineUtil.calSlotItemWithWrapperMap(blockMenu, this.getInputSlots());
        Map<Integer, Integer> matchItemMap = new HashMap<>(inputItemWithWrapperMap.size());
        List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getAdvancedRecipe(this.getClass());

        for(int i = 0, length = advancedMachineRecipeList.size(); i < length; i++) {
            AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((i + offset) % length);
            List<ItemStackWithWrapper> recipeInputItemList = advancedMachineRecipe.getInput();

            boolean work = true;
            List<Integer> skipSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
            for(ItemStackWithWrapper recipeInputItem : recipeInputItemList) {
                int matchAmount = 0;
                for(Map.Entry<Integer, ItemStackWithWrapper> inputItemEntry : inputItemWithWrapperMap.entrySet()) {
                    if(skipSlotList.contains(inputItemEntry.getKey())) {
                        continue;
                    }
                    if(ItemStackUtil.isItemSimilar(recipeInputItem, inputItemEntry.getValue())) {
                        skipSlotList.add(inputItemEntry.getKey());
                        int match = Math.min(recipeInputItem.getAmount() - matchAmount, inputItemEntry.getValue().getItemStack().getAmount());
                        matchItemMap.put(inputItemEntry.getKey(), match);
                        matchAmount += match;
                        if(matchAmount == recipeInputItem.getAmount()) {
                            break;
                        }
                    }
                }
                if(matchAmount != recipeInputItem.getAmount()) {
                    work = false;
                    break;
                }
            }

            if(work) {
                List<ItemStackWithWrapper> recipeOutputItemList = advancedMachineRecipe.getOutput();
                if (MachineUtil.calMaxMatch(blockMenu, this.getOutputSlots(), recipeOutputItemList) >= 1) {
                    for(Map.Entry<Integer, Integer> matchItemEntry : matchItemMap.entrySet()) {
                        blockMenu.consumeItem(matchItemEntry.getKey(), matchItemEntry.getValue());
                    }
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, String.valueOf((i + offset) % length));
                    return this.getMachineRecipes().get((i + offset) % length);
                }
            }

            matchItemMap.clear();
        }
        return null;
    }
}
