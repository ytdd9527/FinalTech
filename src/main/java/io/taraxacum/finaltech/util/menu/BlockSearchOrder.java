package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BlockSearchOrder {
    public static final String KEY = "block-search-order";

    public static final String VALUE_POSITIVE = "positive";
    public static final String VALUE_REVERSE = "reverse";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, "&7搜索模式",
            "&f正向搜索");
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, "&7搜索模式",
            "&f逆向搜索");

    public static String next(String blockSearchOrder) {
        if (blockSearchOrder == null) {
            return VALUE_POSITIVE;
        }
        switch (blockSearchOrder) {
            case VALUE_POSITIVE:
                return VALUE_REVERSE;
            case VALUE_REVERSE:
            default:
                return VALUE_POSITIVE;
        }
    }

    public static ItemStack getIcon(String blockSearchOrder) {
        if (blockSearchOrder == null) {
            return Icon.ERROR_ICON;
        }
        switch (blockSearchOrder) {
            case VALUE_POSITIVE:
                return POSITIVE_ICON;
            case VALUE_REVERSE:
                return REVERSE_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
