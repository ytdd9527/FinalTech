package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Entropy extends UnusableSlimefunItem{
    public Entropy(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static ItemStack newItem(@Nullable ItemStack Item) {
        return new ItemStack(FinalTechItems.ENTROPY);
    }

    public static boolean isValid(@Nullable ItemStack itemStack) {
        return ItemStackUtil.isItemSimilar(itemStack, FinalTechItems.ENTROPY);
    }
}
