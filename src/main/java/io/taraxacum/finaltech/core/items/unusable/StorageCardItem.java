package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.finaltech.util.TextUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class StorageCardItem extends UnusableSlimefunItem implements RecipeItem {
    public static final String ITEM_LORE_WITHOUT_COLOR = "⌫⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌦";
    public static final String ITEM_LORE = TextUtil.colorRandomString(ITEM_LORE_WITHOUT_COLOR);

    public StorageCardItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    public static ItemStack newItem() {
        return new ItemStack(FinalTechItems.STORAGE_CARD);
    }

    public static boolean isValid(@Nonnull ItemStack item) {
        if (item.hasItemMeta()) {
            return StorageCardItem.isValid(item.getItemMeta());
        }
        return false;
    }
    public static boolean isValid(@Nonnull ItemMeta itemMeta) {
        if (!itemMeta.hasLore()) {
            return false;
        }
        List<String> lore = itemMeta.getLore();
        return !lore.isEmpty() && StorageCardItem.ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(lore.get(0)));
    }

    public static boolean storableItem(@Nonnull ItemStack item) {
        Material material = item.getType();
        return !Tag.SHULKER_BOXES.isTagged(material) && !Material.BUNDLE.equals(material) && ItemStackUtil.itemStackToString(item).length() < 6000;
    }

    public static void updateLore(@Nonnull ItemStack cardItem) {
        if (!cardItem.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        StorageCardItem.updateLore(itemMeta);
        cardItem.setItemMeta(itemMeta);
    }
    /**
     * Use ItemStack.setItemMeta() after using this
     * @param cardItemMeta
     */
    public static void updateLore(@Nonnull ItemMeta cardItemMeta) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        ItemStack stringItem = null;
        if (persistentDataContainer.has(StringItemUtil.ITEM_KEY, PersistentDataType.STRING)) {
            String itemString = persistentDataContainer.get(StringItemUtil.ITEM_KEY, PersistentDataType.STRING);
            stringItem = ItemStackUtil.stringToItemStack(itemString);
        }
        StorageCardItem.updateLore(cardItemMeta, stringItem);
    }
    public static void updateLore(@Nonnull ItemMeta cardItemMeta, @Nullable ItemStack stringItem) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        List<String> lore;
        if (persistentDataContainer.has(StringItemUtil.AMOUNT_KEY, PersistentDataType.STRING)) {
            String amount = persistentDataContainer.get(StringItemUtil.AMOUNT_KEY, PersistentDataType.STRING);
            lore = cardItemMeta.getLore();
            if (lore == null || lore.isEmpty()) {
                lore = new ArrayList<>(4);
                lore.add(StorageCardItem.ITEM_LORE);
            } else {
                lore.set(0, StorageCardItem.ITEM_LORE);
            }
            String name = ItemStackUtil.getItemName(stringItem);
            if (lore.size() == 1) {
                lore.add(TextUtil.getRandomColor() + name + "  " + TextUtil.colorRandomString(String.valueOf(amount)));
            } else {
                lore.set(1, TextUtil.getRandomColor() + name + "  " + TextUtil.colorRandomString(String.valueOf(amount)));
            }
        } else {
            lore = new ArrayList<>(1);
            lore.add(StorageCardItem.ITEM_LORE);
        }
        cardItemMeta.setLore(lore);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
