package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class BlockSearchMode {
    public static final String KEY = "block-search-mode";
    public static final String KEY_INPUT = "input-block-search-mode";
    public static final String KEY_OUTPUT = "output-block-search-mode";

    public static final String VALUE_ZERO = "zero";
    public static final String VALUE_INHERIT = "inherit";
    public static final String VALUE_PENETRATE = "penetrate";
    public static final String VALUE_RESPECT = "respect";
    public static final String VALUE_INTERRUPT = "interrupt";

    public static final ItemStack ZERO_ICON = new CustomItemStack((Material.PURPLE_STAINED_GLASS), "&7搜索模式",
            "&f零模式",
            "",
            "&7不进行远距离搜索");
    public static final ItemStack INHERIT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f继承模式",
            "",
            "&7继承相同类型方块的方向",
            "&7同时会延长最大搜索范围");
    public static final ItemStack PENETRATE_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f穿透模式",
            "",
            "&7尝试穿透相同类型的方块",
            "&7同时会延长最大搜索范围");
    public static final ItemStack RESPECT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f链接模式",
            "",
            "&7允许用锁链延长搜索范围",
            "&7搜索到相同类型的方块时停止搜索");
    public static final ItemStack INTERRUPT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f中断模式",
            "",
            "&7搜索到相同类型方块时",
            "&7中断远程搜索");


    public static final String next(String blockSearchMode, String id) {
        if (blockSearchMode == null || id == null) {
            return VALUE_ZERO;
        }
        if (id.equals(FinalTechItems.TRANSFER_PIPE.getItemId())) {
            switch (blockSearchMode) {
                case VALUE_ZERO:
                    return VALUE_INHERIT;
                case VALUE_INHERIT:
                    return VALUE_PENETRATE;
                case VALUE_PENETRATE:
                default:
                    return VALUE_ZERO;
            }
        } else if (id.equals(FinalTechItems.TRANSFER_STATION.getItemId())) {
            switch (blockSearchMode) {
                case VALUE_ZERO:
                    return VALUE_PENETRATE;
                case VALUE_PENETRATE:
                    return VALUE_RESPECT;
                case VALUE_RESPECT:
                default:
                    return VALUE_ZERO;
            }
        } else if (id.equals(FinalTechItems.TRANSFER_LINE.getItemId())) {
            switch (blockSearchMode) {
                case VALUE_ZERO:
                    return VALUE_PENETRATE;
                case VALUE_PENETRATE:
                    return VALUE_INTERRUPT;
                case VALUE_INHERIT:
                default:
                    return VALUE_ZERO;
            }
        }
        return VALUE_ZERO;
    }

    public static final ItemStack getIcon(String blockSearchMode) {
        if (blockSearchMode == null) {
            return Icon.ERROR_ICON;
        }
        switch (blockSearchMode) {
            case VALUE_ZERO:
                return ZERO_ICON;
            case VALUE_INHERIT:
                return INHERIT_ICON;
            case VALUE_PENETRATE:
                return PENETRATE_ICON;
            case VALUE_RESPECT:
                return RESPECT_ICON;
            case VALUE_INTERRUPT:
                return INTERRUPT_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
