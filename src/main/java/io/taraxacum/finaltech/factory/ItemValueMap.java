package io.taraxacum.finaltech.factory;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class ItemValueMap {
    private static boolean INIT = false;
    /**
     * Uesd to cal an item's value for exchange other item
     * @param key the id of a slimefun item
     * @param value the value of this slimefun item
     */
    private static final Map<String, String> itemInputValueMap = new HashMap<>();
    /**
     * Used to get an item's value which will be given to player
     * @param key the id of a slimefun item
     * @param value the value of this slimefun item
     */
    private static final Map<String, String> itemOutputValueMap = new HashMap<>();
    /**
     * @param key value of one of the items
     * @param value the slimefun items that have the same value in output.
     */
    public static final Map<String, List<String>> valueItemListOutputMap = new HashMap<>();

    public static void init() {
        if(INIT) {
            return;
        }
        //todo
        // load from file

        List<SlimefunItem> allSlimefunItems = Slimefun.getRegistry().getAllSlimefunItems();
        for(SlimefunItem slimefunItem : allSlimefunItems) {
            ItemValueMap.getOrCalItemInputValue(slimefunItem);
            ItemValueMap.getOrCalItemOutputValue(slimefunItem);
        }
        itemOutputValueMap.put(FinalTechItems.BUG.getItemId(), "0");
        List<String> list = valueItemListOutputMap.get("0");
        if(list == null) {
            list = new ArrayList<>();
        }
        list.add(FinalTechItems.BUG.getItemId());
        valueItemListOutputMap.put("0", list);
        INIT = true;
    }

    public static String getOrCalItemInputValue(@Nonnull ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if(slimefunItem == null) {
            return StringNumberUtil.mul(StringNumberUtil.ONE, String.valueOf(item.getAmount()));
        }
        return ItemValueMap.getOrCalItemInputValue(slimefunItem);
    }

    public static String getOrCalItemInputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if(itemInputValueMap.containsKey(id)) {
            return itemInputValueMap.get(id);
        }
        String value = StringNumberUtil.ZERO;
        for(ItemStack recipeItem : slimefunItem.getRecipe()) {
            if(ItemStackUtil.isItemNull(recipeItem)) {
                continue;
            }
            int amount = recipeItem.getAmount();
            if(SlimefunItem.getByItem(recipeItem) != null) {
                amount /= SlimefunItem.getByItem(recipeItem).getRecipeOutput().getAmount();
            }
            value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueMap.getOrCalItemInputValue(recipeItem), String.valueOf(amount)));
        }
        value = StringNumberUtil.add(value, String.valueOf(slimefunItem.getRecipe().length));
        SlimefunItem machineItem = slimefunItem.getRecipeType().getMachine();
        if(machineItem == null) {
            value = StringNumberUtil.add(value);
        } else {
            value = StringNumberUtil.add(value, ItemValueMap.getOrCalItemInputValue(slimefunItem.getRecipeType().getMachine()));
        }
        itemInputValueMap.put(id, value);
        return value;
    }

    public static String getOrCalItemOutputValue(@Nonnull ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if(slimefunItem == null) {
            return StringNumberUtil.mul("16", String.valueOf(item.getAmount()));
        }
        return ItemValueMap.getOrCalItemOutputValue(slimefunItem);
    }

    public static String getOrCalItemOutputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if(itemOutputValueMap.containsKey(id)) {
            return itemOutputValueMap.get(id);
        }
        String value = StringNumberUtil.ZERO;
        for(ItemStack recipeItem : slimefunItem.getRecipe()) {
            if(ItemStackUtil.isItemNull(recipeItem)) {
                continue;
            }
            int amount = recipeItem.getAmount();
            if(SlimefunItem.getByItem(recipeItem) != null) {
                amount /= SlimefunItem.getByItem(recipeItem).getRecipeOutput().getAmount();
            }
            value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueMap.getOrCalItemOutputValue(recipeItem), String.valueOf(amount)));
        }
        value = StringNumberUtil.add(value, value);
        value = StringNumberUtil.add(value, "64");
        SlimefunItem machineItem = slimefunItem.getRecipeType().getMachine();
        if(machineItem == null) {
            value = StringNumberUtil.VALUE_MAX;
        } else {
            value = StringNumberUtil.add(value, ItemValueMap.getOrCalItemOutputValue(slimefunItem.getRecipeType().getMachine()));
        }
        itemOutputValueMap.put(id, value);
        List<String> list = valueItemListOutputMap.get(value);
        if(list == null) {
            list = new ArrayList<>();
        }
        list.add(id);
        valueItemListOutputMap.put(value, list);
        return value;
    }
}
