package io.taraxacum.finaltech.dto;

import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
@Deprecated
public class AdvancedCraftUtil {
    private MachineRecipe machineRecipe;
    private List<List<Integer>> matchItemSlot;
    private int matchCount;

    public AdvancedCraftUtil() {

    }

    public AdvancedCraftUtil(@Nonnull MachineRecipe machineRecipe, @Nonnull List<List<Integer>> matchItemSlot, int count) {
        this.machineRecipe = machineRecipe;
        this.matchItemSlot = matchItemSlot;
        this.matchCount = count;
    }

    public MachineRecipe getMachineRecipe() {
        return machineRecipe;
    }

    public void setMachineRecipe(MachineRecipe machineRecipe) {
        this.machineRecipe = machineRecipe;
    }

    public List<List<Integer>> getMatchItemSlot() {
        return matchItemSlot;
    }

    public void setMatchItemSlot(List<List<Integer>> matchItemSlot) {
        this.matchItemSlot = matchItemSlot;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public void consumeItem(@Nonnull Inventory inventory) {
        for (int i = 0, length = this.machineRecipe.getInput().length; i < length; i++) {
            int amount = this.machineRecipe.getInput()[i].getAmount() * matchCount;
            for (int slot : this.matchItemSlot.get(i)) {
                ItemStack item = inventory.getItem(slot);
                int count = Math.min(item.getAmount(), amount);
                item.setAmount(item.getAmount() - count);
                amount -= count;
                if (amount == 0) {
                    break;
                }
            }
        }
    }

    public MachineRecipe calEnlargeMachineRecipe() {
        if (this.matchCount == 1) {
            return new MachineRecipe(this.machineRecipe.getTicks(), this.machineRecipe.getInput(), this.machineRecipe.getOutput());
        }
        ItemStack[] inputItems = ItemStackUtil.calEnlargeItemArray(this.machineRecipe.getInput(), this.matchCount);
        ItemStack[] outputItems = ItemStackUtil.calEnlargeItemArray(this.machineRecipe.getOutput(), this.matchCount);
        return new MachineRecipe(this.machineRecipe.getTicks(), inputItems, outputItems);
    }

    public static AdvancedCraftUtil calCraft(BlockMenu blockMenu, int[] slots, List<MachineRecipe> machineRecipeList, int quantityModule, int offset) {
        Map<Integer, ItemStackWithWrapper> itemSlotMap = MachineUtil.calSlotItemWithWrapperMap(blockMenu.toInventory(), slots);
        List<List<Integer>> searchList = new ArrayList<>(slots.length);
        List<Integer> allSlot = new ArrayList<>(slots.length);

        for (int i = 0, length = machineRecipeList.size(); i < length; i++) {
            MachineRecipe machineRecipe = machineRecipeList.get((i + offset) % length);
            int count = quantityModule;
            for (ItemStack recipeInputItem : machineRecipe.getInput()) {
                ItemStackWithWrapper recipeInputItemWithWrapper = new ItemStackWithWrapper(recipeInputItem);
                int itemMatchAmount = 0;
                List<Integer> slotList = new ArrayList<>(slots.length);
                for (Map.Entry<Integer, ItemStackWithWrapper> entry : itemSlotMap.entrySet()) {
                    if (allSlot.contains(entry.getKey())) {
                        continue;
                    }
                    if (ItemStackUtil.isItemSimilar(entry.getValue().getItemStackWrapper(), recipeInputItemWithWrapper.getItemStackWrapper())) {
                        itemMatchAmount += entry.getValue().getItemStack().getAmount();
                        slotList.add(entry.getKey());
                        allSlot.add(entry.getKey());
                    }
                    if (itemMatchAmount / recipeInputItem.getAmount() > count) {
                        break;
                    }
                }
                searchList.add(slotList);
                count = Math.min(count, itemMatchAmount / recipeInputItem.getAmount());
                if (count == 0) {
                    break;
                }
            }
            if (count > 0) {
                return new AdvancedCraftUtil(machineRecipe, searchList, count);
            }
            searchList.clear();
            allSlot.clear();
        }
        return null;
    }
}
