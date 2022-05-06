package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class FilterMode {
    public static final String KEY = "filter-mode";

    public static final String VALUE_WHITE = "white";
    public static final String VALUE_BLACK = "black";

    public static final ItemStack FILTER_MODE_WHITE_ICON = new CustomItemStack(Material.WHITE_WOOL, "&f白名单模式", "&7仅当物品匹配下方9个格子的物品时", "&7才会进行运输");
    public static final ItemStack FILTER_MODE_BLACK_ICON = new CustomItemStack(Material.BLACK_WOOL, "&7黑名单模式", "&7仅当物品不匹配下方9个格子的物品时", "&7才会进行运输");
    public static String next(String filterMode) {
        if (filterMode == null) {
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
    public static ItemStack getIcon(String filterMode) {
        if (filterMode == null) {
            return Icon.ERROR_ICON;
        }
        return switch (filterMode) {
            case VALUE_WHITE -> FILTER_MODE_WHITE_ICON;
            case VALUE_BLACK -> FILTER_MODE_BLACK_ICON;
            default -> Icon.ERROR_ICON;
        };
    }
}
