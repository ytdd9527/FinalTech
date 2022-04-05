package io.taraxacum.finaltech.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class ManualSmeltery extends AbstractCraftManualMachine {
    public ManualSmeltery(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
//        SlimefunUtil.registerRecipeByRecipeType(this, RecipeType.SMELTERY);
        SlimefunUtil.registerRecipeBySlimefunId(this, SlimefunItems.ELECTRIC_SMELTERY.getItemId());
        SlimefunUtil.registerRecipeBySlimefunId(this, SlimefunItems.ELECTRIC_INGOT_FACTORY.getItemId());
    }
}
