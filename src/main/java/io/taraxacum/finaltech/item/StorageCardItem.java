package io.taraxacum.finaltech.item;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class StorageCardItem extends UnusableSlimefunItem implements RecipeItem {
    public static final String ITEM_LORE = "§x§f§f§0§0§0§0已" +
            "§x§f§f§3§3§0§0经" +
            "§x§f§f§6§6§0§0由" +
            "§x§f§f§a§a§0§0Fi" +
            "§x§f§f§b§b§0§0na" +
            "§x§f§f§d§d§0§0l_" +
            "§x§f§f§f§f§0§0R" +
            "§x§c§c§f§f§0§0O" +
            "§x§8§8§f§f§0§0O" +
            "§x§4§4§f§f§0§0T" +
            "§x§0§0§f§f§0§0签" +
            "§x§0§0§d§d§4§4名" +
            "§x§0§0§b§b§8§8认" +
            "§x§0§0§a§a§c§c证" +
            "§x§0§0§8§8§f§f并" +
            "§x§0§0§5§5§f§f允" +
            "§x§0§0§2§2§f§f许" +
            "§x§0§0§0§0§f§f进" +
            "§x§3§3§0§0§f§f行" +
            "§x§6§6§0§0§f§f存" +
            "§x§9§9§0§0§f§f储";
    public static final String ITEM_LORE_WITHOUT_COLOR = "已经由Final_ROOT签名认证并允许进行存储";

    public StorageCardItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f介绍",
                "",
                "&f需通过存储交互接口",
                "&f存入或取出物品",
                "",
                "&f一个存储卡只能存入一种物品",
                "&f但是可存入物品的数量是无限的",
                "&f各色存储卡之间并无区别");
    }

    public static boolean isStorageCardItem(@Nonnull ItemStack item) {
        if(item.hasItemMeta()) {
            return isStorageCardItem(item.getItemMeta());
        }
        return false;
    }

    public static boolean isStorageCardItem(@Nonnull ItemMeta itemMeta) {
        if(!itemMeta.hasLore()) {
            return false;
        }
        List<String> lore = itemMeta.getLore();
        return !lore.isEmpty() && StorageCardItem.ITEM_LORE_WITHOUT_COLOR.equals(ChatColor.stripColor(lore.get(0)));
    }
}
