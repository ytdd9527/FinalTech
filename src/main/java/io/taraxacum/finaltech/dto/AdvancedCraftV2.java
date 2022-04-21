package io.taraxacum.finaltech.dto;

import io.taraxacum.finaltech.factory.MachineRecipeFactory;
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

public class AdvancedCraftV2 {

    private List<ItemStackWithWrapper> inputItemList;

    private List<ItemStackWithWrapper> outputItemList;

    private List<List<Integer>> consumeSlotList;

    private int matchCount;

    private int offset;

    private AdvancedCraftV2(List<ItemStackWithWrapper> inputItemList, List<ItemStackWithWrapper> outputItemList, List<List<Integer>> consumeSlotList, int matchCount, int offset) {
        this.inputItemList = inputItemList;
        this.outputItemList = outputItemList;
        this.consumeSlotList = consumeSlotList;
        this.matchCount = matchCount;
        this.offset = offset;
    }

    public List<ItemStackWithWrapper> getInputItemList() {
        return inputItemList;
    }

    public void setInputItemList(List<ItemStackWithWrapper> inputItemList) {
        this.inputItemList = inputItemList;
    }

    public List<ItemStackWithWrapper> getOutputItemList() {
        return outputItemList;
    }

    public void setOutputItemList(List<ItemStackWithWrapper> outputItemList) {
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

    public void consumeItem(@Nonnull BlockMenu blockMenu) {
        for(int i = 0; i < this.inputItemList.size(); i++) {
            int consumeItemAmount = this.inputItemList.get(i).getAmount() * this.matchCount;
            for(int slot : this.consumeSlotList.get(i)) {
                ItemStack item = blockMenu.getItemInSlot(slot);
                int itemConsumeAmount = Math.min(consumeItemAmount, item.getAmount());
                item.setAmount(item.getAmount() - itemConsumeAmount);
                consumeItemAmount -= itemConsumeAmount;
                if(consumeItemAmount == 0) {
                    break;
                }
            }
        }
    }

    public MachineRecipe calMachineRecipe(int ticks) {
        return new MachineRecipe(ticks, ItemStackUtil.calEnlargeItemArray(this.inputItemList, this.matchCount), ItemStackUtil.calEnlargeItemArray(this.outputItemList, this.matchCount));
    }

    @Nullable
    public static AdvancedCraftV2 craftAsc(@Nonnull BlockMenu blockMenu, int[] inputSlots, @Nonnull List<AdvancedMachineRecipe> advancedMachineRecipeList, int quantityModule, int offset) {
        Map<Integer, ItemStackWithWrapper> inputItemWithWrapperMap = MachineUtil.calSlotItemWithWrapperMap(blockMenu, inputSlots);
        List<List<Integer>> consumeSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        List<Integer> skipSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        int matchCount;
        for(int i = 0, length = advancedMachineRecipeList.size(); i < length; i++) {
            AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((i + offset) % length);
            List<ItemStackWithWrapper> recipeInputItemList = advancedMachineRecipe.getInput();
            matchCount = quantityModule;
            for (ItemStackWithWrapper recipeInputItem : recipeInputItemList) {
                int matchAmount = 0;
                List<Integer> slotList = new ArrayList<>(inputItemWithWrapperMap.size() - skipSlotList.size());
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

            if(matchCount > 0) {
                List<ItemStackWithWrapper> recipeOutputItemList = advancedMachineRecipe.getOutput();
                return new AdvancedCraftV2(advancedMachineRecipe.getInput(), recipeOutputItemList, consumeSlotList, matchCount, (i + offset) % length);
            }
            skipSlotList.clear();
            consumeSlotList.clear();
        }
        return null;
    }

    @Nullable
    public static AdvancedCraftV2 craftDesc(@Nonnull BlockMenu blockMenu, int[] inputSlots, @Nonnull List<AdvancedMachineRecipe> advancedMachineRecipeList, int quantityModule, int offset) {
        Map<Integer, ItemStackWithWrapper> inputItemWithWrapperMap = MachineUtil.calSlotItemWithWrapperMap(blockMenu, inputSlots);
        List<List<Integer>> consumeSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        List<Integer> skipSlotList = new ArrayList<>(inputItemWithWrapperMap.size());
        int matchCount;
        for(int i = 0, length = advancedMachineRecipeList.size(); i < length; i++) {
            AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((offset - i + length) % length);
            List<ItemStackWithWrapper> recipeInputItemList = advancedMachineRecipe.getInput();
            matchCount = quantityModule;
            for (ItemStackWithWrapper recipeInputItem : recipeInputItemList) {
                int matchAmount = 0;
                List<Integer> slotList = new ArrayList<>(inputItemWithWrapperMap.size() - skipSlotList.size());
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

            if(matchCount > 0) {
                List<ItemStackWithWrapper> recipeOutputItemList = advancedMachineRecipe.getOutput();
                return new AdvancedCraftV2(advancedMachineRecipe.getInput(), recipeOutputItemList, consumeSlotList, matchCount, (offset - i + length) % length);
            }
            skipSlotList.clear();
            consumeSlotList.clear();
        }
        return null;
    }
}
