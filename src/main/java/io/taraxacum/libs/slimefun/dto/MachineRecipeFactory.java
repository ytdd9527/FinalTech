package io.taraxacum.libs.slimefun.dto;

import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MachineRecipeFactory {
    private final Map<Class<?>, List<MachineRecipe>> recipeMap = new HashMap<>();
    private final Map<Class<?>, List<AdvancedMachineRecipe>> advancedRecipeMap = new HashMap<>();
    private static volatile MachineRecipeFactory instance;

    private MachineRecipeFactory() {

    }

    @Nonnull
    public List<MachineRecipe> getRecipe(@Nonnull Class<?> clazz) {
        if (this.recipeMap.containsKey(clazz)) {
            return this.recipeMap.get(clazz);
        }
        List<MachineRecipe> machineRecipeList = new ArrayList<>();
        this.recipeMap.put(clazz, machineRecipeList);
        return machineRecipeList;
    }

    @Nonnull
    public List<AdvancedMachineRecipe> getAdvancedRecipe(@Nonnull Class<?> clazz) {
        if (this.advancedRecipeMap.containsKey(clazz)) {
            return this.advancedRecipeMap.get(clazz);
        } else if (this.recipeMap.containsKey(clazz)) {
            this.initAdvancedRecipeMap(clazz);
            return this.advancedRecipeMap.get(clazz);
        }
        return new ArrayList<>();
    }

    public void initAdvancedRecipeMap(@Nonnull Class<?> clazz) {
        List<MachineRecipe> machineRecipeList = this.recipeMap.get(clazz);
        if (machineRecipeList == null) {
            return;
        }
        List<AdvancedMachineRecipe> advancedMachineRecipeList = new ArrayList<>(machineRecipeList.size());
        for (MachineRecipe machineRecipe : machineRecipeList) {
            ItemAmountWrapper[] inputItems = ItemStackUtil.calItemArrayWithAmount(machineRecipe.getInput());

            AdvancedMachineRecipe.AdvancedRandomOutput[] advancedRandomOutputs;
            if (machineRecipe instanceof RandomMachineRecipe) {
                advancedRandomOutputs = new AdvancedMachineRecipe.AdvancedRandomOutput[((RandomMachineRecipe) machineRecipe).getRandomOutputs().length];
                RandomMachineRecipe.RandomOutput[] randomOutputs = ((RandomMachineRecipe) machineRecipe).getRandomOutputs();
                for (int i = 0; i < randomOutputs.length; i++) {
                    advancedRandomOutputs[i] = new AdvancedMachineRecipe.AdvancedRandomOutput(ItemStackUtil.calItemArrayWithAmount(randomOutputs[i].getOutputItem()), randomOutputs[i].getWeight());
                }
            } else {
                advancedRandomOutputs = new AdvancedMachineRecipe.AdvancedRandomOutput[1];
                advancedRandomOutputs[0] = new AdvancedMachineRecipe.AdvancedRandomOutput(ItemStackUtil.calItemArrayWithAmount(machineRecipe.getOutput()), 1);
            }

            advancedMachineRecipeList.add(new AdvancedMachineRecipe(inputItems, advancedRandomOutputs));
        }
        this.advancedRecipeMap.put(clazz, advancedMachineRecipeList);
    }

    @Nonnull
    public static MachineRecipeFactory getInstance() {
        if (instance == null) {
            synchronized (MachineRecipeFactory.class) {
                if (instance == null) {
                    instance = new MachineRecipeFactory();
                }
            }
        }
        return instance;
    }
}
