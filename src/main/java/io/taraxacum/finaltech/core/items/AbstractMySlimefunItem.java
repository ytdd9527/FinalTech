package io.taraxacum.finaltech.core.items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.factory.MachineRecipeFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * We may add something soon
 */
public class AbstractMySlimefunItem extends SlimefunItem {
    public AbstractMySlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public AbstractMySlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, @Nullable ItemStack recipeOutput) {
        super(itemGroup, item, recipeType, recipe, recipeOutput);
    }

    protected AbstractMySlimefunItem(ItemGroup itemGroup, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, id, recipeType, recipe);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        if (this instanceof RecipeItem) {
            this.getAddon().getJavaPlugin().getServer().getScheduler().runTask((Plugin)addon, () -> {
                ((RecipeItem) AbstractMySlimefunItem.this).registerDefaultRecipes();
                MachineRecipeFactory.initAdvancedRecipeMap(AbstractMySlimefunItem.this.getClass());
            });
        }
    }

    public void register() {
        this.register(JavaPlugin.getPlugin(FinalTech.class));
    }
}
