package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class SlotSearchOrder {
    public static final String KEY_INPUT = "input-order";
    public static final String KEY_OUTPUT = "output-order";

    public static final String VALUE_ASCENT = "asc";
    public static final String VALUE_DESCEND = "desc";
    public static final String VALUE_FIRST_ONLY = "first-only";
    public static final String VALUE_LAST_ONLY = "last-only";


    public static final ItemStack ASCENT_ICON = new CustomItemStack(Material.BLUE_WOOL, "&9顺序搜索","&7按照正向顺序搜索物品");
    public static final ItemStack DESCEND_ICON = new CustomItemStack(Material.ORANGE_WOOL, "&6逆序搜索","&7按照逆向顺序搜索物品");
    public static final ItemStack FIRST_ONLY_ICON = new CustomItemStack(Material.BLUE_CARPET, "&9仅搜索第一格");
    public static final ItemStack LAST_ONLY_ICON = new CustomItemStack(Material.ORANGE_CARPET, "&6仅搜索最后一格");


    public static final String next(String order) {
        if (order == null) {
            return VALUE_ASCENT;
        }
        switch (order) {
            case VALUE_ASCENT:
                return VALUE_DESCEND;
            case VALUE_DESCEND:
                return VALUE_FIRST_ONLY;
            case VALUE_FIRST_ONLY:
                return VALUE_LAST_ONLY;
            case VALUE_LAST_ONLY:
            default:
                return VALUE_ASCENT;
        }
    }

    public static final ItemStack getIcon(String order) {
        if (order == null) {
            return Icon.ERROR_ICON;
        }
        switch (order) {
            case VALUE_ASCENT:
                return ASCENT_ICON;
            case VALUE_DESCEND:
                return DESCEND_ICON;
            case VALUE_FIRST_ONLY:
                return FIRST_ONLY_ICON;
            case VALUE_LAST_ONLY:
                return LAST_ONLY_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
