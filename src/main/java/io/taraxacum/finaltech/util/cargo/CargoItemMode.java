package io.taraxacum.finaltech.util.cargo;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class CargoItemMode {
    public static final String KEY = "item-mode";
    public static final String KEY_INPUT = "input-mode";
    public static final String KEY_OUTPUT = "output-mode";

    public static final String VALUE_ALL = "all";
    public static final String VALUE_TYPE = "type";
    public static final String VALUE_STACK = "stack";

    public static final ItemStack ALL_ICON = new CustomItemStack(Material.PUMPKIN, "&7传输种类限制-否", "&7无效果");
    public static final ItemStack TYPE_ICON = new CustomItemStack(Material.CARVED_PUMPKIN, "&7传输种类限制-仅一种", "&7每次仅会传输一种物品");
    public static final ItemStack STACK_ICON = new CustomItemStack(Material.JACK_O_LANTERN, "&7传输种类限制-仅一组", "&7每次仅会传输一组物品，并至多改变一个格子的物品数量");
    public static String next(String itemMode) {
        if(itemMode == null) {
            return VALUE_ALL;
        }
        switch (itemMode) {
            case VALUE_ALL:
                return VALUE_TYPE;
            case VALUE_TYPE:
                return VALUE_STACK;
            case VALUE_STACK:
            default:
                return VALUE_ALL;
        }
    }

    public static ItemStack getIcon(String itemMode) {
        if(itemMode == null) {
            return Icon.ERROR_ICON;
        }
        switch (itemMode) {
            case VALUE_ALL:
                return ALL_ICON;
            case VALUE_TYPE:
                return TYPE_ICON;
            case VALUE_STACK:
                return STACK_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
