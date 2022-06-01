package io.taraxacum.finaltech.core.items.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class ManualPressureChamber extends AbstractManualCraftMachine {
    public ManualPressureChamber(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerRecipeByRecipeType(this, RecipeType.PRESSURE_CHAMBER);
    }
}
