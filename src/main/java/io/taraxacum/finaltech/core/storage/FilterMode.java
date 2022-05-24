package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 */
public final class FilterMode {
    public static final String KEY = "fm";

    public static final String VALUE_BLACK = "b";
    public static final String VALUE_WHITE = "w";

    public static final ItemStack FILTER_MODE_BLACK_ICON = new CustomItemStack(Material.BLACK_WOOL, "&7黑名单模式", TextUtil.colorRandomString("仅当物品不匹配下方9个格子的物品时"), TextUtil.colorRandomString("才会进行运输"));
    public static final ItemStack FILTER_MODE_WHITE_ICON = new CustomItemStack(Material.WHITE_WOOL, "&f白名单模式", TextUtil.colorRandomString("仅当物品匹配下方9个格子的物品时"), TextUtil.colorRandomString("才会进行运输"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_BLACK, FILTER_MODE_BLACK_ICON);
        this.put(VALUE_WHITE, FILTER_MODE_WHITE_ICON);
    }});

    @Deprecated
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

    @Deprecated
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
