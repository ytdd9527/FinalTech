package io.taraxacum.finaltech.core.items.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class WoolConversion extends AbstractConversionMachine{
    public WoolConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(Material.WHITE_WOOL, Material.WHITE_DYE);
        this.registerRecipe(Material.ORANGE_WOOL, Material.ORANGE_DYE);
        this.registerRecipe(Material.MAGENTA_WOOL, Material.MAGENTA_DYE);
        this.registerRecipe(Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_DYE);
        this.registerRecipe(Material.YELLOW_WOOL, Material.YELLOW_DYE);
        this.registerRecipe(Material.LIME_WOOL, Material.LIME_DYE);
        this.registerRecipe(Material.PINK_WOOL, Material.PINK_DYE);
        this.registerRecipe(Material.GRAY_WOOL, Material.GRAY_DYE);
        this.registerRecipe(Material.LIGHT_GRAY_WOOL, Material.LIGHT_GRAY_DYE);
        this.registerRecipe(Material.CYAN_WOOL, Material.CYAN_DYE);
        this.registerRecipe(Material.PURPLE_WOOL, Material.PURPLE_DYE);
        this.registerRecipe(Material.BLUE_WOOL, Material.BLUE_DYE);
        this.registerRecipe(Material.BROWN_WOOL, Material.BROWN_DYE);
        this.registerRecipe(Material.GREEN_WOOL, Material.GREEN_DYE);
        this.registerRecipe(Material.RED_WOOL, Material.RED_DYE);
        this.registerRecipe(Material.BLACK_WOOL, Material.BLACK_DYE);
    }
}
