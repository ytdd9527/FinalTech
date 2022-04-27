package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BlockSearchCycle {
    public static final String KEY = "block-search-cycle";

    public static final String VALUE_FALSE = "false";
    public static final String VALUE_TRUE = "true";

    public static final String DEFAULT_VALUE = VALUE_FALSE;

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.MINECART, "&7首尾循环运输-否");
    public static final ItemStack TRUE_ICON = new CustomItemStack(Material.CHEST_MINECART, "&7首尾循环运输-是");

    public static String next(String cargoSelf) {
        if (cargoSelf == null) {
            return VALUE_FALSE;
        }
        switch (cargoSelf) {
            case VALUE_FALSE:
                return VALUE_TRUE;
            case VALUE_TRUE:
            default:
                return VALUE_FALSE;
        }
    }

    public static ItemStack getIcon(String cargoSelf) {
        if (cargoSelf == null) {
            return Icon.ERROR_ICON;
        }
        switch (cargoSelf) {
            case VALUE_FALSE:
                return FALSE_ICON;
            case VALUE_TRUE:
                return TRUE_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
