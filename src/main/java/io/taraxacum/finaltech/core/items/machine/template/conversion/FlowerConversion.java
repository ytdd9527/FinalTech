package io.taraxacum.finaltech.core.items.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FlowerConversion extends AbstractConversionMachine {
    public FlowerConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(Tag.SMALL_FLOWERS.getValues().size() + Tag.TALL_FLOWERS.getValues().size());
        for (Material material : Tag.SMALL_FLOWERS.getValues()) {
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(material, 1));
        }
        for (Material material : Tag.TALL_FLOWERS.getValues()) {
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(material, 1));
        }

        for (Material material : Tag.SMALL_FLOWERS.getValues()) {
            this.registerRecipe(new RandomMachineRecipe(new ItemStack(material), randomOutputList));
        }
        for (Material material : Tag.TALL_FLOWERS.getValues()) {
            this.registerRecipe(new RandomMachineRecipe(new ItemStack(material), randomOutputList));
        }
    }
}
