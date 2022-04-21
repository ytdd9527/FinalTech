package io.taraxacum.finaltech.factory;

import io.taraxacum.finaltech.api.RandomMachineRecipe;
import io.taraxacum.finaltech.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author Final_ROOT
 */
public class MachineRecipeFactory {
    private static final Map<Class<?>, List<MachineRecipe>> RECIPE_MAP = new HashMap<>();
    private static final Map<Class<?>, List<AdvancedMachineRecipe>> ADVANCED_RECIPE_MAP = new HashMap<>();

    public static List<MachineRecipe> getRecipe(Class<?> clazz) {
        if (RECIPE_MAP.containsKey(clazz)) {
            return RECIPE_MAP.get(clazz);
        }
        List<MachineRecipe> machineRecipeList = new ArrayList<>();
        RECIPE_MAP.put(clazz, machineRecipeList);
        return machineRecipeList;
    }

    public static List<AdvancedMachineRecipe> getAdvancedRecipe(Class<?> clazz) {
        if (ADVANCED_RECIPE_MAP.containsKey(clazz)) {
            return ADVANCED_RECIPE_MAP.get(clazz);
        }
        return new ArrayList<>();
    }

    public static void initAdvancedRecipeMap(Class<?> clazz) {
        List<MachineRecipe> machineRecipeList = RECIPE_MAP.get(clazz);
        if(machineRecipeList == null) {
            return;
        }
        List<AdvancedMachineRecipe> advancedMachineRecipeList = new ArrayList<>(machineRecipeList.size());
        for (MachineRecipe machineRecipe : machineRecipeList) {
            ItemStack[] inputItems = machineRecipe.getInput();
            List<ItemStackWithWrapper> inputItemList = ItemStackUtil.calItemListWithAmount(inputItems);
            List<AdvancedMachineRecipe.AdvancedRandomOutput> outputItemList;
            if (machineRecipe instanceof RandomMachineRecipe) {
                outputItemList = new ArrayList<>(((RandomMachineRecipe) machineRecipe).getRandomOutputList().size());
                List<RandomMachineRecipe.RandomOutput> randomOutputList = ((RandomMachineRecipe) machineRecipe).getRandomOutputList();
                for (RandomMachineRecipe.RandomOutput randomOutput : randomOutputList) {
                    outputItemList.add(new AdvancedMachineRecipe.AdvancedRandomOutput(ItemStackUtil.calItemListWithAmount(randomOutput.getOutputItem()), randomOutput.getWeight()));
                }
            } else {
                outputItemList = new ArrayList<>(1);
                outputItemList.add(new AdvancedMachineRecipe.AdvancedRandomOutput(ItemStackUtil.calItemListWithAmount(machineRecipe.getOutput()), 1));
            }
            advancedMachineRecipeList.add(new AdvancedMachineRecipe(inputItemList, outputItemList));
        }
        ADVANCED_RECIPE_MAP.put(clazz, advancedMachineRecipeList);
    }

    public static void initAdvancedRecipeMap() {
        for (Class clazz : RECIPE_MAP.keySet()) {
            initAdvancedRecipeMap(clazz);
        }
    }
}
