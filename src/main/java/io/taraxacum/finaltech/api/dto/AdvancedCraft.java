package io.taraxacum.finaltech.api.dto;

import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedCraft {
    private List<ItemStackWithWrapperAmount> inputItemList;
    private List<ItemStackWithWrapperAmount> outputItemList;
    private List<List<Integer>> consumeSlotList;
    private int matchCount;
    private int offset;

    private AdvancedCraft(List<ItemStackWithWrapperAmount> inputItemList, List<ItemStackWithWrapperAmount> outputItemList, List<List<Integer>> consumeSlotList, int matchCount, int offset) {
        this.inputItemList = inputItemList;
        this.outputItemList = outputItemList;
        this.consumeSlotList = consumeSlotList;
        this.matchCount = matchCount;
        this.offset = offset;
    }

    public List<ItemStackWithWrapperAmount> getInputItemList() {
        return inputItemList;
    }

    public void setInputItemList(List<ItemStackWithWrapperAmount> inputItemList) {
        this.inputItemList = inputItemList;
    }

    public List<ItemStackWithWrapperAmount> getOutputItemList() {
        return outputItemList;
    }

    public void setOutputItemList(List<ItemStackWithWrapperAmount> outputItemList) {
        this.outputItemList = outputItemList;
    }

    public List<List<Integer>> getConsumeSlotList() {
        return consumeSlotList;
    }

    public void setConsumeSlotList(List<List<Integer>> consumeSlotList) {
        this.consumeSlotList = consumeSlotList;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Consume item after we know how a machine should work.
     * @param blockMenu
     */
    public void consumeItem(@Nonnull BlockMenu blockMenu) {
        for (int i = 0; i < this.inputItemList.size(); i++) {
            int consumeItemAmount = this.inputItemList.get(i).getAmount() * this.matchCount;
            for (int slot : this.consumeSlotList.get(i)) {
                ItemStack item = blockMenu.getItemInSlot(slot);
                int itemConsumeAmount = Math.min(consumeItemAmount, item.getAmount());
                item.setAmount(item.getAmount() - itemConsumeAmount);
                consumeItemAmount -= itemConsumeAmount;
                if (consumeItemAmount == 0) {
                    break;
                }
            }
        }
    }

    @Nonnull
    public MachineRecipe calMachineRecipe(int ticks) {
        if (this.matchCount > 1) {
            return new MachineRecipe(ticks, ItemStackUtil.calEnlargeItemArray(this.inputItemList, this.matchCount), ItemStackUtil.calEnlargeItemArray(this.outputItemList, this.matchCount));
        } else if (this.matchCount == 1) {
            return new MachineRecipe(ticks, ItemStackWithWrapperAmount.toItemArray(this.inputItemList), ItemStackWithWrapperAmount.toItemArray(this.outputItemList));
        } else {
            return new MachineRecipe(ticks, new ItemStack[0], new ItemStack[0]);
        }
    }

    /**
     * Cal the craft work a machine will do.
     * @param blockMenu the container that contains items and all operation will do here.
     * @param inputSlots where the items will be consumed to match the {@link MachineRecipe}.
     * @param advancedMachineRecipeList list of {@link AdvancedMachineRecipe} that a machine can work to.
     * @param quantityModule how many times a machine will work in max.
     * @param offset machine-recipe will begin in the given offset.
     * @return
     */
    @Nullable
    public static AdvancedCraft craftAsc(@Nonnull BlockMenu blockMenu, int[] inputSlots, @Nonnull List<AdvancedMachineRecipe> advancedMachineRecipeList, int quantityModule, int offset) {
        Map<Integer, ItemStackWithWrapper> inputItemWithWrapperMap = MachineUtil.calSlotItemWithWrapperMap(blockMenu, inputSlots);
        List<List<Integer>> consumeSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        List<Integer> skipSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        int matchCount;
        int matchAmount;
        List<Integer> slotList;
        for (int i = 0, length = advancedMachineRecipeList.size(); i < length; i++) {
            AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((i + offset) % length);
            List<ItemStackWithWrapperAmount> recipeInputItemList = advancedMachineRecipe.getInput();
            matchCount = quantityModule;
            for (ItemStackWithWrapperAmount recipeInputItem : recipeInputItemList) {
                matchAmount = 0;
                slotList = new ArrayList<>(inputItemWithWrapperMap.size() - skipSlotList.size());
                for (Map.Entry<Integer, ItemStackWithWrapper> inputItemEntry : inputItemWithWrapperMap.entrySet()) {
                    if (skipSlotList.contains(inputItemEntry.getKey())) {
                        continue;
                    }
                    if (ItemStackUtil.isItemSimilar(recipeInputItem, inputItemEntry.getValue())) {
                        matchAmount += inputItemEntry.getValue().getItemStack().getAmount();
                        skipSlotList.add(inputItemEntry.getKey());
                        slotList.add(inputItemEntry.getKey());
                    }
                    if (matchAmount / recipeInputItem.getAmount() >= matchCount) {
                        break;
                    }
                }
                matchCount = Math.min(matchCount, matchAmount / recipeInputItem.getAmount());
                if (matchCount == 0) {
                    break;
                }
                consumeSlotList.add(slotList);
            }

            if (matchCount > 0) {
                List<ItemStackWithWrapperAmount> recipeOutputItemList = advancedMachineRecipe.getOutput();
                return new AdvancedCraft(advancedMachineRecipe.getInput(), recipeOutputItemList, consumeSlotList, matchCount, (i + offset) % length);
            }
            skipSlotList.clear();
            consumeSlotList.clear();
        }
        return null;
    }
    @Nullable
    public static AdvancedCraft craftDesc(@Nonnull BlockMenu blockMenu, int[] inputSlots, @Nonnull List<AdvancedMachineRecipe> advancedMachineRecipeList, int quantityModule, int offset) {
        Map<Integer, ItemStackWithWrapper> inputItemWithWrapperMap = MachineUtil.calSlotItemWithWrapperMap(blockMenu, inputSlots);
        List<List<Integer>> consumeSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        List<Integer> skipSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        int matchCount;
        int matchAmount;
        List<Integer> slotList;
        for (int i = 0, length = advancedMachineRecipeList.size(); i < length; i++) {
            AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((offset + length - i) % length);
            List<ItemStackWithWrapperAmount> recipeInputItemList = advancedMachineRecipe.getInput();
            matchCount = quantityModule;
            for (ItemStackWithWrapperAmount recipeInputItem : recipeInputItemList) {
                matchAmount = 0;
                slotList = new ArrayList<>(inputItemWithWrapperMap.size() - skipSlotList.size());
                for (Map.Entry<Integer, ItemStackWithWrapper> inputItemEntry : inputItemWithWrapperMap.entrySet()) {
                    if (skipSlotList.contains(inputItemEntry.getKey())) {
                        continue;
                    }
                    if (ItemStackUtil.isItemSimilar(recipeInputItem, inputItemEntry.getValue())) {
                        matchAmount += inputItemEntry.getValue().getItemStack().getAmount();
                        skipSlotList.add(inputItemEntry.getKey());
                        slotList.add(inputItemEntry.getKey());
                    }
                    if (matchAmount / recipeInputItem.getAmount() >= matchCount) {
                        break;
                    }
                }
                matchCount = Math.min(matchCount, matchAmount / recipeInputItem.getAmount());
                if (matchCount == 0) {
                    break;
                }
                consumeSlotList.add(slotList);
            }

            if (matchCount > 0) {
                List<ItemStackWithWrapperAmount> recipeOutputItemList = advancedMachineRecipe.getOutput();
                return new AdvancedCraft(advancedMachineRecipe.getInput(), recipeOutputItemList, consumeSlotList, matchCount, (offset + length - i) % length);
            }
            skipSlotList.clear();
            consumeSlotList.clear();
        }
        return null;
    }
}
