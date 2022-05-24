package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public final class BlockSearchCycle {
    public static final String KEY = "bsc";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_TRUE = "t";

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.MINECART, "&7首尾循环搜索-否");
    public static final ItemStack TRUE_ICON = new CustomItemStack(Material.CHEST_MINECART, "&7首尾循环搜索-是");

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_TRUE, TRUE_ICON);
    }});

    @Deprecated
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

    @Deprecated
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
