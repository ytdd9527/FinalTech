package io.taraxacum.finaltech.core.items.machine.template.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class AdvancedFoodFactory extends AbstractAdvanceMachine {
    public AdvancedFoodFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerRecipeBySlimefunId(this, SlimefunItems.FOOD_COMPOSTER.getItemId());
        SlimefunUtil.registerRecipeBySlimefunId(this, SlimefunItems.FOOD_FABRICATOR.getItemId());
    }
}
