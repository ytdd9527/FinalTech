package io.taraxacum.finaltech.core.items.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class DustConversion extends AbstractConversionMachine {
    public DustConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        RandomMachineRecipe.RandomOutput randomOutput1 = new RandomMachineRecipe.RandomOutput(SlimefunItems.IRON_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput2 = new RandomMachineRecipe.RandomOutput(SlimefunItems.GOLD_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput3 = new RandomMachineRecipe.RandomOutput(SlimefunItems.COPPER_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput4 = new RandomMachineRecipe.RandomOutput(SlimefunItems.TIN_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput5 = new RandomMachineRecipe.RandomOutput(SlimefunItems.LEAD_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput6 = new RandomMachineRecipe.RandomOutput(SlimefunItems.SILVER_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput7 = new RandomMachineRecipe.RandomOutput(SlimefunItems.ALUMINUM_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput8 = new RandomMachineRecipe.RandomOutput(SlimefunItems.ZINC_DUST, 1);
        RandomMachineRecipe.RandomOutput randomOutput9 = new RandomMachineRecipe.RandomOutput(SlimefunItems.MAGNESIUM_DUST, 1);
        RandomMachineRecipe randomMachineRecipe = new RandomMachineRecipe(new ItemStack[] {new ItemStack(Material.COBBLESTONE)}, List.of(randomOutput1, randomOutput2, randomOutput3, randomOutput4, randomOutput5, randomOutput6, randomOutput7, randomOutput8, randomOutput9));
        this.registerRecipe(randomMachineRecipe);
    }
}
