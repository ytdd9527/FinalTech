package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class CopyCardItem extends UnusableSlimefunItem {
    public static final String ITEM_LORE_WITHOUT_COLOR = "⌫⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌦";
    public static final String ITEM_LORE = TextUtil.colorRandomString(ITEM_LORE_WITHOUT_COLOR);

    public static int DEFAULT_DIFFICULTY = 16777216;
    public static int DIFFICULTY = DEFAULT_DIFFICULTY;

    public CopyCardItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static boolean isValid(@Nullable ItemStack itemStack) {
        if (ItemStackUtil.isItemNull(itemStack) || !itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            return false;
        }
        for (String l : lore) {
            if (CopyCardItem.ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(l))) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack newItem(@Nonnull ItemStack stringItem, @Nonnull String amount) {
        ItemStack result = new ItemStack(FinalTechItems.COPY_CARD);
        result.setAmount(1);
        StringItemUtil.setItemInCard(result, stringItem, amount);
        ItemStackUtil.setLore(result, CopyCardItem.ITEM_LORE, "§7物品= " + ItemStackUtil.getItemName(stringItem), "§7数量= " + amount);
        return result;
    }
}
