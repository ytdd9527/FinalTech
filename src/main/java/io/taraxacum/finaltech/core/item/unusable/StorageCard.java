package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.item.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.StringItemUtil;
import io.taraxacum.libs.plugin.util.TextUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
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
public class StorageCard extends UnusableSlimefunItem implements RecipeItem {
    public static final String ITEM_LORE_WITHOUT_COLOR = "⌫⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌧⌦";
    public static final String ITEM_LORE = TextUtil.colorRandomString(ITEM_LORE_WITHOUT_COLOR);

    public StorageCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    public static ItemStack newItem() {
        return new ItemStack(FinalTechItems.STORAGE_CARD);
    }

    public static ItemStack newItem(@Nonnull ItemStack stringItem, @Nonnull String amount) {
        ItemStack result = ItemStackUtil.cloneItem(FinalTechItems.STORAGE_CARD);
        result.setAmount(1);
        StringItemUtil.setItemInCard(result, stringItem, amount);
        StorageCard.updateLore(result);
        return result;
    }

    public static boolean isValid(@Nonnull ItemStack item) {
        if (item.hasItemMeta()) {
            return StorageCard.isValid(item.getItemMeta());
        }
        return false;
    }
    public static boolean isValid(@Nonnull ItemMeta itemMeta) {
        if (!itemMeta.hasLore()) {
            return false;
        }
        List<String> lore = itemMeta.getLore();
        return !lore.isEmpty() && StorageCard.ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(lore.get(0)));
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
        StorageCard.updateLore(itemMeta);
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
        StorageCard.updateLore(cardItemMeta, stringItem);
    }
    public static void updateLore(@Nonnull ItemMeta cardItemMeta, @Nullable ItemStack stringItem) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        List<String> lore;
        if (persistentDataContainer.has(StringItemUtil.AMOUNT_KEY, PersistentDataType.STRING)) {
            String amount = persistentDataContainer.get(StringItemUtil.AMOUNT_KEY, PersistentDataType.STRING);
            lore = cardItemMeta.getLore();
            if (lore == null || lore.isEmpty()) {
                lore = new ArrayList<>(4);
                lore.add(StorageCard.ITEM_LORE);
            } else {
                lore.set(0, StorageCard.ITEM_LORE);
            }
            String name = ItemStackUtil.getItemName(stringItem);
            if (lore.size() == 1) {
                lore.add(TextUtil.getRandomColor() + name + "  " + TextUtil.colorRandomString(String.valueOf(amount)));
            } else {
                lore.set(1, TextUtil.getRandomColor() + name + "  " + TextUtil.colorRandomString(String.valueOf(amount)));
            }
        } else {
            lore = new ArrayList<>(1);
            lore.add(StorageCard.ITEM_LORE);
        }
        cardItemMeta.setLore(lore);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
