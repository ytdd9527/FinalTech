package io.taraxacum.finaltech.items.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

public class ManualGrindStone extends AbstractManualCraftMachine {
    public ManualGrindStone(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
//        SlimefunUtil.registerRecipeByRecipeType(this, RecipeType.GRIND_STONE);
        SlimefunUtil.registerRecipeBySlimefunId(this, SlimefunItems.GRIND_STONE.getItemId());
    }
}
