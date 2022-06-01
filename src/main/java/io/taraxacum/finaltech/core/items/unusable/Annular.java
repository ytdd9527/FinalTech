package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Annular extends UnusableSlimefunItem implements RecipeItem {
    public Annular(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static boolean isValid(@Nonnull ItemStack item) {
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.ANNULAR);
    }

    public static ItemStack newItem(@Nonnull ItemStack item, @Nonnull Player player) {
        return new ItemStack(FinalTechItems.ANNULAR);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "说明",
                "",
                TextUtil.COLOR_NORMAL + "通过在 " + FinalTechItems.CARD_OPERATION_PORT.getDisplayName() + TextUtil.COLOR_NORMAL + " 中合成获得");
    }
}
