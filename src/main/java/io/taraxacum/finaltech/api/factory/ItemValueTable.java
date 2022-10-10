package io.taraxacum.finaltech.api.factory;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemValueTable {
    private boolean init = false;
    private final Map<String, String> itemInputValueMap = new HashMap<>(Slimefun.getRegistry().getAllSlimefunItems().size());
    private final Map<String, String> itemOutputValueMap = new HashMap<>(Slimefun.getRegistry().getAllSlimefunItems().size());
    private final Map<String, List<String>> valueItemListOutputMap = new HashMap<>();
    public final String BASE_OUTPUT_VALUE = FinalTech.getValueManager().getOrDefault("64","itemValueTable","baseOutputValue");
    public final String BASE_INPUT_VALUE = FinalTech.getValueManager().getOrDefault("1","itemValueTable","baseInputValue");
    private static volatile ItemValueTable instance;

    private ItemValueTable() {

    }

    public void init() {
        if (this.init) {
            return;
        }

        this.init = true;

        ConfigFileManager valueFile = FinalTech.getValueManager();

        if(valueFile.containPath("input", "base")) {
            for (String key : valueFile.getStringList("input", "base")) {
                this.itemInputValueMap.put(key, valueFile.getString("input", "base", key));
            }
        }
        if(valueFile.containPath("output", "base")) {
            for (String key : valueFile.getStringList("output", "base")) {
                String value = valueFile.getString("output", "base", key);
                this.itemOutputValueMap.put(key, value);
                if(!StringNumberUtil.VALUE_INFINITY.equals(value)) {
                    this.addToOutputMap(key, value);
                }
            }
        }

        this.manualInitId(FinalTechItems.SINGULARITY.getItemId(), StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)), false);
        this.manualInitId(FinalTechItems.SPIROCHETE.getItemId(), StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)), false);
        this.manualInitId(FinalTechItems.PHONY.getItemId(), StringNumberUtil.mul(StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT), String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT)), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)), false);
        this.manualInitId(FinalTechItems.BUG.getItemId(), StringNumberUtil.ZERO, StringNumberUtil.ZERO, false);

        List<SlimefunItem> allSlimefunItems = Slimefun.getRegistry().getAllSlimefunItems();
        for (SlimefunItem slimefunItem : allSlimefunItems) {
            if (!slimefunItem.isDisabled()) {
                try {
                    this.getOrCalItemInputValue(slimefunItem);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.itemInputValueMap.put(slimefunItem.getId(), StringNumberUtil.ZERO);
                }
                try {
                    this.getOrCalItemOutputValue(slimefunItem);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.itemOutputValueMap.put(slimefunItem.getId(), StringNumberUtil.VALUE_INFINITY);
                }
            }
        }

        if(valueFile.containPath("input", "result")) {
            for (String key : valueFile.getStringList("input", "result")) {
                this.itemInputValueMap.put(key, valueFile.getString("input", "result", key));
            }
        }
        if(valueFile.containPath("output", "result")) {
            for (String key : valueFile.getStringList("output", "result")) {
                String value = valueFile.getString("output", "result", key);
                this.itemOutputValueMap.put(key, value);
                if(!StringNumberUtil.VALUE_INFINITY.equals(value)) {
                    this.addToOutputMap(key, value);
                }
            }
        }

        if(valueFile.containPath("output", "remove")) {
            for(String id : valueFile.getStringList("output", "remove")) {
                this.removeFromOutputMap(id);
            }
        }
    }

    public String getOrCalItemInputValue(@Nullable ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul(this.BASE_INPUT_VALUE, String.valueOf(item.getAmount()));
        }
        return this.getOrCalItemInputValue(slimefunItem);
    }
    public String getOrCalItemInputValue(@Nonnull String id) {
        if(this.itemInputValueMap.containsKey(id)) {
            return this.itemInputValueMap.get(id);
        }
        SlimefunItem slimefunItem = SlimefunItem.getById(id);
        if(slimefunItem == null) {
            return StringNumberUtil.ZERO;
        }
        return this.getOrCalItemInputValue(slimefunItem);
    }
    public String getOrCalItemInputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if (this.itemInputValueMap.containsKey(id)) {
            return this.itemInputValueMap.get(id);
        } else if (slimefunItem.isDisabled()) {
            this.itemInputValueMap.put(id, StringNumberUtil.ZERO);
            return StringNumberUtil.ZERO;
        }
        this.itemInputValueMap.put(id, StringNumberUtil.ZERO);
        String value = StringNumberUtil.ZERO;
        List<ItemAmountWrapper> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemAmountWrapper recipeItem : recipeList) {
            ItemStack item = recipeItem.getItemStack();
            item.setAmount(1);
            value = StringNumberUtil.add(value, this.getOrCalItemInputValue(item));
        }
        value = StringNumberUtil.add(value, String.valueOf(recipeList.size()));
        value = StringNumberUtil.add(value, this.BASE_INPUT_VALUE);
        this.itemInputValueMap.put(id, value);
        return value;
    }

    public String getOrCalItemOutputValue(@Nullable ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul(this.BASE_OUTPUT_VALUE, String.valueOf(item.getAmount()));
        }
        return this.getOrCalItemOutputValue(slimefunItem);
    }
    public String getOrCalItemOutputValue(@Nonnull String id) {
        if(this.itemOutputValueMap.containsKey(id)) {
            return this.itemOutputValueMap.get(id);
        }
        SlimefunItem slimefunItem = SlimefunItem.getById(id);
        if(slimefunItem == null) {
            return StringNumberUtil.VALUE_INFINITY;
        }
        return this.getOrCalItemOutputValue(slimefunItem);
    }
    public String getOrCalItemOutputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if (this.itemOutputValueMap.containsKey(id)) {
            return this.itemOutputValueMap.get(id);
        } else if (slimefunItem.isDisabled()) {
            this.itemOutputValueMap.put(id, StringNumberUtil.VALUE_INFINITY);
            return StringNumberUtil.VALUE_INFINITY;
        }
        this.itemOutputValueMap.put(id, StringNumberUtil.VALUE_INFINITY);
        String value = StringNumberUtil.ZERO;
        List<ItemAmountWrapper> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemAmountWrapper recipeItem : recipeList) {
            int amount = recipeItem.getAmount();
            SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem.getItemStack());
            if (recipeSlimefunItem != null) {
                amount /= recipeSlimefunItem.getRecipeOutput().getAmount();
                if (amount == 0) {
                    amount = 1;
                }
            }
            ItemStack item = ItemStackUtil.cloneItem(recipeItem.getItemStack());
            item.setAmount(1);
            value = StringNumberUtil.add(value, StringNumberUtil.mul(this.getOrCalItemOutputValue(item), String.valueOf(amount)));
        }
        value = StringNumberUtil.mul(value, String.valueOf(recipeList.size()));
        SlimefunItem machineItem = slimefunItem.getRecipeType().getMachine();
        if (machineItem == null || recipeList.isEmpty() || StringNumberUtil.ZERO.equals(value)) {
            value = StringNumberUtil.VALUE_INFINITY;
        } else if(slimefunItem instanceof MultiBlockMachine || ItemStackUtil.isItemSimilar(machineItem.getItem(), RecipeType.MULTIBLOCK.toItem())) {
            value = StringNumberUtil.add(value);
        } else if (machineItem.equals(slimefunItem)) {
            value = StringNumberUtil.add(value, value);
        } else {
            value = StringNumberUtil.add(value, this.getOrCalItemOutputValue(machineItem));
        }
        this.itemOutputValueMap.put(id, value);
        if(!StringNumberUtil.VALUE_INFINITY.equals(value) && !(slimefunItem instanceof MultiBlockMachine) && !ItemStackUtil.isItemSimilar(machineItem.getItem(), RecipeType.MULTIBLOCK.toItem())) {
            this.addToOutputMap(id, value);
        }
        return value;
    }

    private void manualInitId(@Nonnull String id, @Nonnull String inputValue, @Nonnull String outputValue, boolean canOutput) {
        this.itemInputValueMap.put(id, inputValue);
        this.itemOutputValueMap.put(id, outputValue);
        if(canOutput && !outputValue.contains(StringNumberUtil.VALUE_INFINITY)) {
            this.addToOutputMap(id, outputValue);
        }
    }
    private void manualInitId(@Nonnull String id, @Nonnull String value, boolean canOutput) {
        this.manualInitId(id, value, value, canOutput);
    }

    private void addToOutputMap(@Nonnull String id, @Nonnull String value) {
        List<String> list = this.valueItemListOutputMap.get(value);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(id);
        this.valueItemListOutputMap.put(value, list);
    }

    private void removeFromOutputMap(@Nullable String id) {
        if(this.itemOutputValueMap.containsKey(id)) {
            String value = this.itemOutputValueMap.get(id);
            if(this.valueItemListOutputMap.containsKey(value)) {
                List<String> idList = this.valueItemListOutputMap.get(value);
                idList.remove(id);
            }
        }
    }

    @Nonnull
    public Map<String, List<String>> getValueItemListOutputMap() {
        return this.valueItemListOutputMap;
    }

    @Nonnull
    public static ItemValueTable getInstance() {
        if(instance == null) {
            synchronized (ItemValueTable.class) {
                if(instance == null) {
                    instance = new ItemValueTable();
                }
            }
        }
        return instance;
    }
}
