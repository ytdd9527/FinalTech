package io.taraxacum.finaltech.api.factory;

import io.taraxacum.finaltech.api.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

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
        } else if(this.recipeMap.containsKey(clazz)) {
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
            ItemStack[] inputItems = machineRecipe.getInput();
            List<ItemAmountWrapper> inputItemList = ItemStackUtil.calItemListWithAmount(inputItems);
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
        this.advancedRecipeMap.put(clazz, advancedMachineRecipeList);
    }

    @Nonnull
    public static MachineRecipeFactory getInstance() {
        if(instance == null) {
            synchronized (MachineRecipeFactory.class) {
                if(instance == null) {
                    instance = new MachineRecipeFactory();
                }
            }
        }
        return instance;
    }
}
