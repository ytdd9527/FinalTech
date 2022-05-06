package io.taraxacum.finaltech.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class ItemPhony extends UnusableSlimefunItem{
    public ItemPhony(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static boolean isSingularity(@Nullable ItemStack item) {
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.SINGULARITY);
    }

    public static boolean isSpirochete(@Nullable ItemStack item) {
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.SPIROCHETE);
    }

    public static boolean isValid(@Nullable ItemStack item) {
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY);
    }

    public static ItemStack newItem(@Nonnull ItemStack item1, @Nonnull ItemStack item2, @Nullable Player player) {
        return new ItemStack(FinalTechItems.PHONY);
    }

    public static ItemStack newShell(@Nonnull ItemStack item, @Nullable Player player) {
        return new ItemStack(FinalTechItems.SHELL);
    }

    public static ItemStack newAnnular(@Nonnull ItemStack item, @Nullable Player player) {
        return new ItemStack(FinalTechItems.ANNULAR);
    }
}
