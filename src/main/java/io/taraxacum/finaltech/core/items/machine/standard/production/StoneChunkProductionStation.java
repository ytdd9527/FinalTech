package io.taraxacum.finaltech.core.items.machine.standard.production;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class StoneChunkProductionStation extends AbstractProductionStationMachine {
    public StoneChunkProductionStation(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    void registerRandomOutputRecipes() {
        this.registerRecipe(SlimefunItems.STONE_CHUNK);
    }
}
