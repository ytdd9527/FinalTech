package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Shell extends UnusableSlimefunItem {
    public Shell(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static boolean isValid(@Nullable ItemStack item) {
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.SHELL);
    }

    public static ItemStack newItem(@Nonnull ItemStack item, @Nullable Player player) {
        return new ItemStack(FinalTechItems.SHELL);
    }
}
