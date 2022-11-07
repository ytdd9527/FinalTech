package io.taraxacum.finaltech.core.items.machine.template.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItems;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class DigitalGenerator extends AbstractGeneratorMachine{
    public DigitalGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    void registerRandomOutputRecipes() {
        this.registerRecipe(FinalTechItems.DIGITAL_ONE);
        this.registerRecipe(FinalTechItems.DIGITAL_TWO);
        this.registerRecipe(FinalTechItems.DIGITAL_THREE);
        this.registerRecipe(FinalTechItems.DIGITAL_FOUR);
    }
}
