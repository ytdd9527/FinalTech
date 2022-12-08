package io.taraxacum.finaltech.core.item.machine.template.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class BasicStoneFactory extends AbstractBasicMachine {
    public BasicStoneFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(new ItemStack(Material.GRANITE, 24), new ItemStack(Material.GRANITE, 32));
        this.registerRecipe(new ItemStack(Material.DIORITE, 24), new ItemStack(Material.DIORITE, 32));
        this.registerRecipe(new ItemStack(Material.ANDESITE, 24), new ItemStack(Material.ANDESITE, 32));
        this.registerRecipe(new ItemStack(Material.STONE, 24), new ItemStack(Material.STONE, 32));
    }
}
