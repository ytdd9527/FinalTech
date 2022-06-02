package io.taraxacum.finaltech.core.factory;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
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
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemValueTable {
    private boolean init = false;
    private final Map<String, String> itemInputValueMap = new HashMap<>();
    private final Map<String, String> itemOutputValueMap = new HashMap<>();
    private final Map<String, List<String>> valueItemListOutputMap = new HashMap<>();
    public static final String BASE_OUTPUT_VALUE = "16";
    private static volatile ItemValueTable instance;

    private ItemValueTable() {

    }

    public void init() {
        if (init) {
            return;
        }

        this.manualInitId(FinalTechItems.SINGULARITY.getItemId(), StringNumberUtil.mul(String.valueOf(Singularity.SINGULARITY_DIFFICULTY), String.valueOf(CopyCardItem.DIFFICULTY)), true);
        this.manualInitId(FinalTechItems.SPIROCHETE.getItemId(), StringNumberUtil.mul(String.valueOf(Singularity.SINGULARITY_DIFFICULTY), String.valueOf(CopyCardItem.DIFFICULTY)), true);
        this.manualInitId(FinalTechItems.PHONY.getItemId(), StringNumberUtil.mul(StringNumberUtil.mul(String.valueOf(Singularity.SINGULARITY_DIFFICULTY), String.valueOf(Spirochete.SPIROCHETE_DIFFICULTY)), String.valueOf(CopyCardItem.DIFFICULTY)), true);
        this.manualInitId(FinalTechItems.BUG.getItemId(), StringNumberUtil.ZERO, StringNumberUtil.ZERO, true);
        this.manualInitId(RecipeType.ENHANCED_CRAFTING_TABLE.getMachine().getId(), StringNumberUtil.ZERO, false);
        this.manualInitId(RecipeType.SMELTERY.getMachine().getId(), "1", false);
        this.manualInitId(RecipeType.PRESSURE_CHAMBER.getMachine().getId(), "1", false);
        this.manualInitId(RecipeType.ORE_CRUSHER.getMachine().getId(), "1", false);
        this.manualInitId(RecipeType.MAGIC_WORKBENCH.getMachine().getId(), "2", false);
        this.manualInitId(RecipeType.JUICER.getMachine().getId(), "1", false);
        this.manualInitId(RecipeType.GRIND_STONE.getMachine().getId(), "1", false);
        this.manualInitId(RecipeType.COMPRESSOR.getMachine().getId(), "1", false);
        this.manualInitId(RecipeType.ARMOR_FORGE.getMachine().getId(), "1", false);
        this.manualInitId(RecipeType.ANCIENT_ALTAR.getMachine().getId(), "5", false);
        this.manualInitId(RecipeType.GOLD_PAN.getMachine().getId(), "3", false);
        this.manualInitId(RecipeType.ORE_WASHER.getMachine().getId(), "2", false);

        Config valueFile = JavaPlugin.getPlugin(FinalTech.class).getValueFile();
        for (String key : valueFile.getKeys("input")) {
            this.itemInputValueMap.put(key, valueFile.getString("input." + key));
        }
        for (String key : valueFile.getKeys("output")) {
            this.itemOutputValueMap.put(key, valueFile.getString("output." + key));
            this.addToOutputMap(key, valueFile.getString("output." + key));
        }

        List<SlimefunItem> allSlimefunItems = Slimefun.getRegistry().getAllSlimefunItems();
        for (SlimefunItem slimefunItem : allSlimefunItems) {
            if (!slimefunItem.isDisabled()) {
                this.getOrCalItemInputValue(slimefunItem);
                this.getOrCalItemOutputValue(slimefunItem);
            }
        }

        List<String> idList = valueFile.getStringList("no-output");
        for(String id : idList) {
            this.removeFromOutputMap(id);
        }

        init = true;
    }

    public String getOrCalItemInputValue(@Nullable ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul(StringNumberUtil.ONE, String.valueOf(item.getAmount()));
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
        String value = StringNumberUtil.ZERO;
        List<ItemStackWithWrapperAmount> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemStackWithWrapperAmount recipeItem : ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe())) {
            int amount = recipeItem.getAmount();
            SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem.getItemStack());
            if (recipeSlimefunItem != null) {
                amount /= recipeSlimefunItem.getRecipeOutput().getAmount();
                if (amount == 0) {
                    amount = 1;
                }
            }
            value = StringNumberUtil.add(value, StringNumberUtil.mul(this.getOrCalItemInputValue(recipeItem.getItemStack()), String.valueOf(amount)));
        }
        value = StringNumberUtil.add(value, String.valueOf(recipeList.size()));
        SlimefunItem machineItem = slimefunItem.getRecipeType().getMachine();
        if (machineItem == null) {
            value = StringNumberUtil.add(value);
        } else if (machineItem.equals(slimefunItem)) {
            itemInputValueMap.put(id, value);
            return value;
        } else {
            value = StringNumberUtil.add(value, this.getOrCalItemInputValue(slimefunItem.getRecipeType().getMachine()));
        }
        this.itemInputValueMap.put(id, value);
        return value;
    }

    public String getOrCalItemOutputValue(@Nullable ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul(BASE_OUTPUT_VALUE, String.valueOf(item.getAmount()));
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
        String value = StringNumberUtil.ZERO;
        List<ItemStackWithWrapperAmount> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemStackWithWrapperAmount recipeItem : recipeList) {
            int amount = recipeItem.getAmount();
            SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem.getItemStack());
            if (recipeSlimefunItem != null) {
                amount /= recipeSlimefunItem.getRecipeOutput().getAmount();
                if (amount == 0) {
                    amount = 1;
                }
            }
            value = StringNumberUtil.add(value, StringNumberUtil.mul(this.getOrCalItemOutputValue(recipeItem.getItemStack()), String.valueOf(amount)));
        }
        value = StringNumberUtil.mul(value, String.valueOf(recipeList.size()));
        SlimefunItem machineItem = slimefunItem.getRecipeType().getMachine();
        if (machineItem == null || recipeList.isEmpty() || StringNumberUtil.ZERO.equals(value)) {
            value = StringNumberUtil.VALUE_INFINITY;
        } else if (machineItem.equals(slimefunItem)) {
            this.itemOutputValueMap.put(id, value);
            this.addToOutputMap(id, value);
            return value;
        } else {
            value = StringNumberUtil.add(value, this.getOrCalItemOutputValue(machineItem));
        }
        this.itemOutputValueMap.put(id, value);
        if(!(slimefunItem instanceof MultiBlockMachine)) {
            this.addToOutputMap(id, value);
        }
        return value;
    }

    private void manualInitId(@Nonnull String id, @Nonnull String inputValue, @Nonnull String outputValue, boolean canOutput) {
        this.itemInputValueMap.put(id, inputValue);
        this.itemOutputValueMap.put(id, outputValue);
        if(canOutput) {
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
        return valueItemListOutputMap;
    }

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
