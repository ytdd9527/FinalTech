package io.taraxacum.finaltech.core.items.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

public class ConcreteConversion extends AbstractConversionMachine{
    public ConcreteConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(Material.WHITE_CONCRETE_POWDER, Material.WHITE_CONCRETE);
        this.registerRecipe(Material.ORANGE_CONCRETE_POWDER, Material.ORANGE_CONCRETE);
        this.registerRecipe(Material.MAGENTA_CONCRETE_POWDER, Material.MAGENTA_CONCRETE);
        this.registerRecipe(Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE);
        this.registerRecipe(Material.YELLOW_CONCRETE_POWDER, Material.YELLOW_CONCRETE);
        this.registerRecipe(Material.LIME_CONCRETE_POWDER, Material.LIME_CONCRETE);
        this.registerRecipe(Material.PINK_CONCRETE_POWDER, Material.PINK_CONCRETE);
        this.registerRecipe(Material.GRAY_CONCRETE_POWDER, Material.GRAY_CONCRETE);
        this.registerRecipe(Material.LIGHT_GRAY_CONCRETE_POWDER, Material.LIGHT_GRAY_CONCRETE);
        this.registerRecipe(Material.CYAN_CONCRETE_POWDER, Material.CYAN_CONCRETE);
        this.registerRecipe(Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_CONCRETE);
        this.registerRecipe(Material.BLUE_CONCRETE_POWDER, Material.BLUE_CONCRETE);
        this.registerRecipe(Material.BROWN_CONCRETE_POWDER, Material.BROWN_CONCRETE);
        this.registerRecipe(Material.GREEN_CONCRETE_POWDER, Material.GREEN_CONCRETE);
        this.registerRecipe(Material.RED_CONCRETE_POWDER, Material.RED_CONCRETE);
        this.registerRecipe(Material.BLACK_CONCRETE_POWDER, Material.BLACK_CONCRETE);
    }
}
