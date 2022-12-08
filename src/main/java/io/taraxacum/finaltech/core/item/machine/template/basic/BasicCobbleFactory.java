package io.taraxacum.finaltech.core.item.machine.template.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BasicCobbleFactory extends AbstractBasicMachine {
    public BasicCobbleFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(new ItemStack[] {new ItemStack(Material.COBBLESTONE, 4)}, new ItemStack[]{new ItemStack(Material.COBBLESTONE, 23)});
        this.registerRecipe(new ItemStack[] {new ItemStack(Material.COBBLESTONE, 3)}, new ItemStack[]{new ItemStack(Material.COBBLESTONE, 13)});
        this.registerRecipe(new ItemStack[] {new ItemStack(Material.COBBLESTONE, 2)}, new ItemStack[]{new ItemStack(Material.COBBLESTONE, 7)});
        this.registerRecipe(new ItemStack[] {new ItemStack(Material.COBBLESTONE, 1)}, new ItemStack[]{new ItemStack(Material.COBBLESTONE, 3)});
        this.registerRecipe(new ItemStack[0], new ItemStack[]{new ItemStack(Material.COBBLESTONE, 1)});
    }
}
