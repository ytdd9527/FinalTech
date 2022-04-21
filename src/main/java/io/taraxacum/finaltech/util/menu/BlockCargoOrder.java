package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class BlockCargoOrder {
    public static final String KEY = "block-cargo-order";

    public static final String VALUE_POSITIVE = "positive";
    public static final String VALUE_REVERSE = "reverse";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, "&7传输顺序",
            "&f正向传输");
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, "&7传输顺序",
            "&f逆向传输");

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
