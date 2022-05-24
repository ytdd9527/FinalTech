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
public final class BlockSearchOrder {
    public static final String KEY = "bso";

    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_REVERSE = "r";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, "&7搜索方向", "&f正向");
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, "&7搜索方向", "&f逆向");

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_POSITIVE, POSITIVE_ICON);
        this.put(VALUE_REVERSE, REVERSE_ICON);
    }});

    @Deprecated
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

    @Deprecated
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
