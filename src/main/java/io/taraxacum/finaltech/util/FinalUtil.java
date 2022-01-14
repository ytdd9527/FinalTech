package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.abstractItem.FinalBasicMachine;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FinalUtil {

    public static void registerRecipeBySlimefunId(FinalBasicMachine finalBasicMachine, String SlimefunId) {

        final SlimefunItem slimefunItem = SlimefunItem.getById(SlimefunId);
        try {
            Method method = slimefunItem.getClass().getMethod("getMachineRecipes");
            List<MachineRecipe> recipes = (List<MachineRecipe>)method.invoke(slimefunItem);
            if(recipes != null) {
                for(MachineRecipe machineRecipe : recipes) {
                    finalBasicMachine.registerRecipe(0, machineRecipe.getInput(), machineRecipe.getOutput());
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
