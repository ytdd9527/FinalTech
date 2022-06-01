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
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Spirochete extends UnusableSlimefunItem implements RecipeItem {
    public static int DEFAULT_SPIROCHETE_DIFFICULTY = 64;
    public static int SPIROCHETE_DIFFICULTY = DEFAULT_SPIROCHETE_DIFFICULTY;
    public Spirochete(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static boolean isValid(@Nullable ItemStack item) {
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.SPIROCHETE);
    }

    public static ItemStack newItem(@Nonnull ItemStack item, @Nullable Player player) {
        return new ItemStack(FinalTechItems.SPIROCHETE);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "说明",
                "",
                TextUtil.COLOR_NORMAL + "通过在 " + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getDisplayName() + TextUtil.COLOR_NORMAL + " 中",
                TextUtil.COLOR_NORMAL + "消耗 " + TextUtil.COLOR_NUMBER + SPIROCHETE_DIFFICULTY + "种" + TextUtil.COLOR_NORMAL + " 不同物品的复制卡获取");
    }
}
