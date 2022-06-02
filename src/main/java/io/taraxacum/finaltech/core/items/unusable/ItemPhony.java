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
public class ItemPhony extends UnusableSlimefunItem implements RecipeItem {
    public ItemPhony(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static boolean isValid(@Nullable ItemStack item) {
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY);
    }

    public static ItemStack newItem(@Nullable ItemStack item1, @Nullable ItemStack item2, @Nullable Player player) {
        return new ItemStack(FinalTechItems.PHONY);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "说明",
                "",
                TextUtil.COLOR_NORMAL + "通过在 " + FinalTechItems.CARD_OPERATION_PORT.getDisplayName() + TextUtil.COLOR_NORMAL + " 中合成获得",
                TextUtil.COLOR_NORMAL + "通过在 " + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getDisplayName() + TextUtil.COLOR_NORMAL + " 中压缩获得",
                TextUtil.COLOR_NORMAL + "通过使用 " + FinalTechItems.SHELL.getDisplayName() + TextUtil.COLOR_NORMAL + " 合成获得");
        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "功能",
                "",
                TextUtil.COLOR_NORMAL + "可以在 " + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getDisplayName() + TextUtil.COLOR_NORMAL + " 中",
                TextUtil.COLOR_NORMAL + "代替任意物品制造复制卡");
    }
}
