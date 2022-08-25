package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.AbstractMySlimefunItem;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Gearwheel extends UnusableSlimefunItem implements RecipeItem {
    public Gearwheel(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack output) {
        super(itemGroup, item, recipeType, recipe, output);
    }

    @Override
    public void registerDefaultRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this.getAddon().getJavaPlugin(), this.getId()), this.getItem());
        recipe.shape("ggg","ddd","aaa");
        recipe.setIngredient('g', Material.GRANITE);
        recipe.setIngredient('d', Material.DIORITE);
        recipe.setIngredient('a', Material.ANDESITE);
        Bukkit.addRecipe(recipe);
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
