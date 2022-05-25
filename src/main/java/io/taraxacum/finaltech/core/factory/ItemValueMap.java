package io.taraxacum.finaltech.core.factory;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapperAmount;
import io.taraxacum.finaltech.core.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.core.items.unusable.Singularity;
import io.taraxacum.finaltech.core.items.unusable.Spirochete;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
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
        if (INIT) {
            return;
        }

        ItemValueMap.initId(FinalTechItems.SINGULARITY.getItemId(), StringNumberUtil.mul(String.valueOf(Singularity.SINGULARITY_DIFFICULTY), String.valueOf(CopyCardItem.DIFFICULTY)));
        ItemValueMap.initId(FinalTechItems.SPIROCHETE.getItemId(), StringNumberUtil.mul(String.valueOf(Singularity.SINGULARITY_DIFFICULTY), String.valueOf(CopyCardItem.DIFFICULTY)));
        ItemValueMap.initId(FinalTechItems.PHONY.getItemId(), StringNumberUtil.mul(StringNumberUtil.mul(String.valueOf(Singularity.SINGULARITY_DIFFICULTY), String.valueOf(Spirochete.SPIROCHETE_DIFFICULTY)), String.valueOf(CopyCardItem.DIFFICULTY)));
        ItemValueMap.initId(FinalTechItems.BUG.getItemId(), StringNumberUtil.ZERO, StringNumberUtil.ZERO);
        ItemValueMap.initRecipe(RecipeType.ENHANCED_CRAFTING_TABLE, StringNumberUtil.ZERO);
        ItemValueMap.initRecipe(RecipeType.SMELTERY, "1");
        ItemValueMap.initRecipe(RecipeType.PRESSURE_CHAMBER, "1");
        ItemValueMap.initRecipe(RecipeType.ORE_CRUSHER, "1");
        ItemValueMap.initRecipe(RecipeType.MAGIC_WORKBENCH, "2");
        ItemValueMap.initRecipe(RecipeType.JUICER, "1");
        ItemValueMap.initRecipe(RecipeType.GRIND_STONE, "1");
        ItemValueMap.initRecipe(RecipeType.COMPRESSOR, "1");
        ItemValueMap.initRecipe(RecipeType.ARMOR_FORGE, "1");
        ItemValueMap.initRecipe(RecipeType.ANCIENT_ALTAR, "5");
        ItemValueMap.initRecipe(RecipeType.GOLD_PAN, "3");
        ItemValueMap.initRecipe(RecipeType.ORE_WASHER, "2");

        itemInputValueMap.put("ENHANCED_CRAFTING_TABLE", StringNumberUtil.ZERO);
        itemOutputValueMap.put("ENHANCED_CRAFTING_TABLE", StringNumberUtil.ZERO);
        ItemValueMap.addToOutputMap("ENHANCED_CRAFTING_TABLE", StringNumberUtil.ZERO);


        Config value = JavaPlugin.getPlugin(FinalTech.class).getValueFile();
        for (String key : value.getKeys("input")) {
            itemInputValueMap.put(key, value.getString("input." + key));
        }
        for (String key : value.getKeys("output")) {
            itemOutputValueMap.put(key, value.getString("output." + key));
            ItemValueMap.addToOutputMap(key, value.getString("output." + key));
        }

        List<SlimefunItem> allSlimefunItems = Slimefun.getRegistry().getAllSlimefunItems();
        for (SlimefunItem slimefunItem : allSlimefunItems) {
            if (!slimefunItem.isDisabled()) {
                ItemValueMap.getOrCalItemInputValue(slimefunItem);
                ItemValueMap.getOrCalItemOutputValue(slimefunItem);
            }
        }
        INIT = true;
    }

    public static String getOrCalItemInputValue(@Nonnull ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul(StringNumberUtil.ONE, String.valueOf(item.getAmount()));
        }
        return ItemValueMap.getOrCalItemInputValue(slimefunItem);
    }

    public static String getOrCalItemInputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if (itemInputValueMap.containsKey(id)) {
            return itemInputValueMap.get(id);
        } else if (slimefunItem.isDisabled()) {
            return StringNumberUtil.ZERO;
        }
        String value = StringNumberUtil.ZERO;
        List<ItemStackWithWrapperAmount> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemStackWithWrapperAmount recipeItem : ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe())) {
            int amount = recipeItem.getAmount();
            if (SlimefunItem.getByItem(recipeItem.getItemStack()) != null) {
                amount /= SlimefunItem.getByItem(recipeItem.getItemStack()).getRecipeOutput().getAmount();
                if (amount == 0) {
                    amount = 1;
                }
            }
            value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueMap.getOrCalItemInputValue(recipeItem.getItemStack()), String.valueOf(amount)));
        }
        value = StringNumberUtil.add(value, String.valueOf(recipeList.size()));
        SlimefunItem machineItem = slimefunItem.getRecipeType().getMachine();
        if (machineItem == null) {
            value = StringNumberUtil.add(value);
        } else if (machineItem.equals(slimefunItem)) {
            itemInputValueMap.put(id, value);
            return value;
        } else {
            value = StringNumberUtil.add(value, ItemValueMap.getOrCalItemInputValue(slimefunItem.getRecipeType().getMachine()));
        }
        itemInputValueMap.put(id, value);
        return value;
    }

    public static String getOrCalItemOutputValue(@Nonnull ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul("16", String.valueOf(item.getAmount()));
        }
        return ItemValueMap.getOrCalItemOutputValue(slimefunItem);
    }

    public static String getOrCalItemOutputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if (itemOutputValueMap.containsKey(id)) {
            return itemOutputValueMap.get(id);
        } else if (slimefunItem.isDisabled()) {
            return StringNumberUtil.VALUE_INFINITY;
        }
        String value = StringNumberUtil.ZERO;
        List<ItemStackWithWrapperAmount> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemStackWithWrapperAmount recipeItem : recipeList) {
            int amount = recipeItem.getAmount();
            if (SlimefunItem.getByItem(recipeItem.getItemStack()) != null) {
                amount /= SlimefunItem.getByItem(recipeItem.getItemStack()).getRecipeOutput().getAmount();
                if (amount == 0) {
                    amount = 1;
                }
            }
            value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueMap.getOrCalItemOutputValue(recipeItem.getItemStack()), String.valueOf(amount)));
        }
        value = StringNumberUtil.mul(value, String.valueOf(recipeList.size()));
        SlimefunItem machineItem = slimefunItem.getRecipeType().getMachine();
        if (machineItem == null || recipeList.isEmpty() || StringNumberUtil.ZERO.equals(value)) {
            value = StringNumberUtil.VALUE_INFINITY;
        } else if (machineItem.equals(slimefunItem)) {
            itemOutputValueMap.put(id, value);
            ItemValueMap.addToOutputMap(id, value);
            return value;
        } else {
            value = StringNumberUtil.add(value, ItemValueMap.getOrCalItemOutputValue(machineItem));
        }
        itemOutputValueMap.put(id, value);
        ItemValueMap.addToOutputMap(id, value);
        return value;
    }

    private static void initId(String id, String inputValue, String outputValue) {
        itemInputValueMap.put(id, inputValue);
        itemOutputValueMap.put(id, outputValue);
        ItemValueMap.addToOutputMap(id, outputValue);
    }

    private static void initId(String id, String value) {
        ItemValueMap.initId(id, value, value);
    }

    private static void initRecipe(RecipeType recipeType, String value) {
        itemInputValueMap.put(recipeType.getMachine().getId(), value);
        itemOutputValueMap.put(recipeType.getMachine().getId(), value);
    }

    private static void addToOutputMap(String id, String value) {
        List<String> list = valueItemListOutputMap.get(value);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(id);
        valueItemListOutputMap.put(value, list);
    }
}
