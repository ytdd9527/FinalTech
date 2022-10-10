package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.finaltech.util.slimefun.TextUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import io.taraxacum.finaltech.util.slimefun.SfItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class CopyCard extends UnusableSlimefunItem implements RecipeItem {
    public static final String ITEM_LORE_WITHOUT_COLOR = "⌫⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌦";
    public static final String ITEM_LORE = TextUtil.colorPseudorandomString(ITEM_LORE_WITHOUT_COLOR);

    public CopyCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static ItemStack newItem(@Nonnull ItemStack stringItem, @Nonnull String amount) {
        ItemStack result = ItemStackUtil.cloneItem(FinalTechItems.COPY_CARD);
        result.setAmount(1);
        StringItemUtil.setItemInCard(result, stringItem, amount);
        List<String> loreList = JavaUtil.toList(ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), SfItemUtil.getIdFormatName(CopyCard.class),
                ItemStackUtil.getItemName(stringItem),
                amount));
        loreList.add(0, ITEM_LORE);
        ItemStackUtil.setLore(result, loreList);
        return result;
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
            if (CopyCard.ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(l))) {
                return true;
            }
        }
        return false;
    }

    public static boolean copiableItem(@Nonnull ItemStack itemStack) {
        if(Tag.SHULKER_BOXES.isTagged(itemStack.getType()) || Material.BUNDLE.equals(itemStack.getType()) && ItemStackUtil.itemStackToString(itemStack).length() > 7000) {
            return false;
        }

        return !Singularity.isValid(itemStack) && !Spirochete.isValid(itemStack) && !ItemPhony.isValid(itemStack);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }
}
