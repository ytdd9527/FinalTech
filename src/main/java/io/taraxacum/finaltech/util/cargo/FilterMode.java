package io.taraxacum.finaltech.util.cargo;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class FilterMode {
    public static final String KEY = "filter-mode";

    public static final String VALUE_WHITE = "white";
    public static final String VALUE_BLACK = "black";

    public static final ItemStack FILTER_MODE_WHITE_ICON = new CustomItemStack(Material.WHITE_WOOL, "&f白名单模式", "&7仅当物品匹配下方9个格子的物品时", "&7才会进行运输");
    public static final ItemStack FILTER_MODE_BLACK_ICON = new CustomItemStack(Material.BLACK_WOOL, "&7黑名单模式", "&7仅当物品不匹配下方9个格子的物品时", "&7才会进行运输");
    public static final String next(String filterMode) {
        if(filterMode == null) {
            return VALUE_WHITE;
        }
        switch (filterMode) {
            case VALUE_WHITE:
                return VALUE_BLACK;
            case VALUE_BLACK:
            default:
                return VALUE_WHITE;
        }
    }
    public static final ItemStack getIcon(String filterMode) {
        if(filterMode == null) {
            return Icon.ERROR_ICON;
        }
        switch (filterMode) {
            case VALUE_WHITE:
                return FILTER_MODE_WHITE_ICON;
            case VALUE_BLACK:
                return FILTER_MODE_BLACK_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }

    public static boolean ifMatch(@Nonnull ItemStack itemStack, BlockMenu blockMenu, String filterMode, int[] filterSlots) {
        return ifMatch(itemStack, blockMenu.toInventory(), filterMode, filterSlots);
    }

    public static boolean ifMatch(@Nonnull ItemStack itemStack, Inventory inv, String filterMode, int[] filterSlots) {
        for(int slot : filterSlots) {
            boolean itemSimilar = !ItemStackUtil.isItemNull(inv.getItem(slot)) && SlimefunUtils.isItemSimilar(itemStack, inv.getItem(slot), true, false);
            if(itemSimilar == true) {
                return VALUE_WHITE.equals(filterMode);
            }
        }
        return VALUE_BLACK.equals(filterMode);
    }
}
