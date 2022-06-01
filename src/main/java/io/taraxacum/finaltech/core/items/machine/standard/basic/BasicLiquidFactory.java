package io.taraxacum.finaltech.core.items.machine.standard.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import io.taraxacum.finaltech.setup.FinalTechItems;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BasicLiquidFactory extends AbstractBasicMachine{
    public BasicLiquidFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(3);
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack(FinalTechItems.WATER_CARD), 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack(FinalTechItems.LAVA_CARD), 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack(FinalTechItems.MILK_CARD), 1));
        RandomMachineRecipe randomMachineRecipe = new RandomMachineRecipe(0, new ItemStack[0], randomOutputList);
        this.registerRecipe(randomMachineRecipe);
    }
}
