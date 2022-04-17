package io.taraxacum.finaltech.factory;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public class MachineRecipeFactory {
    private static final Map<Class<?>, List<MachineRecipe>> RECIPE_MAP = new HashMap<>();

    public static List<MachineRecipe> getRecipe(Class<?> clazz) {
        if(RECIPE_MAP.containsKey(clazz)) {
            return RECIPE_MAP.get(clazz);
        }
        List<MachineRecipe> machineRecipeList = new ArrayList<>();
        RECIPE_MAP.put(clazz, machineRecipeList);
        return machineRecipeList;
    }
}
