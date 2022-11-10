package io.taraxacum.finaltech.core.items.machine.template.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItems;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BasicLogicFactory extends AbstractBasicMachine {
    public BasicLogicFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(new ItemStack[]{FinalTechItems.LOGIC_FALSE, FinalTechItems.LOGIC_TRUE, FinalTechItems.BUG}, new ItemStack[]{FinalTechItems.ENTROPY});
    }
}
