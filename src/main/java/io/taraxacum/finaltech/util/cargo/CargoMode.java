package io.taraxacum.finaltech.util.cargo;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class CargoMode {
    public static final String KEY = "cargo-mode";

    public static final String VALUE_INPUT_MAIN = "input-main";
    public static final String VALUE_OUTPUT_MAIN = "output-main";

    public static final ItemStack INPUT_MAIN_ICON = new CustomItemStack(Material.WATER_BUCKET, "&9主输入侧", "&7以输入侧物品为主要搜索顺序");
    public static final ItemStack OUTPUT_MAIN_ICON = new CustomItemStack(Material.LAVA_BUCKET, "&6主输出侧", "&7以输出侧物品为主要搜索顺序");

    public static final String next(String cargoMode) {
        if(cargoMode == null) {
            return VALUE_INPUT_MAIN;
        }
        switch (cargoMode) {
            case VALUE_INPUT_MAIN:
                return VALUE_OUTPUT_MAIN;
            case VALUE_OUTPUT_MAIN:
            default:
                return VALUE_INPUT_MAIN;
        }
    }

    public static final ItemStack getIcon(String cargoMode) {
        if(cargoMode == null) {
            return Icon.ERROR_ICON;
        }
        switch (cargoMode) {
            case VALUE_INPUT_MAIN:
                return INPUT_MAIN_ICON;
            case VALUE_OUTPUT_MAIN:
                return OUTPUT_MAIN_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
