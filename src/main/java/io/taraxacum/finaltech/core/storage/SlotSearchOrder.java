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
 * @since 2.0
 */
public final class SlotSearchOrder {
    public static final String KEY = "sso";
    public static final String KEY_INPUT = "ssoi";
    public static final String KEY_OUTPUT = "ssoo";

    public static final String VALUE_ASCENT = "a";
    public static final String VALUE_DESCEND = "d";
    public static final String VALUE_FIRST_ONLY = "f";
    public static final String VALUE_LAST_ONLY = "l";
    public static final String VALUE_RANDOM = "r";

    public static final ItemStack ASCENT_ICON = new CustomItemStack(Material.BLUE_WOOL, "&9顺序搜索", TextUtil.colorRandomString("按照正向顺序搜索物品"));
    public static final ItemStack DESCEND_ICON = new CustomItemStack(Material.ORANGE_WOOL, "&6逆序搜索", TextUtil.colorRandomString("按照逆向顺序搜索物品"));
    public static final ItemStack FIRST_ONLY_ICON = new CustomItemStack(Material.BLUE_CARPET, "&9仅搜索第一格");
    public static final ItemStack LAST_ONLY_ICON = new CustomItemStack(Material.ORANGE_CARPET, "&6仅搜索最后一格");
    public static final ItemStack RANDOM_ICON = new CustomItemStack(Material.PAPER, "&a随机");

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});

    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});

    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});

    @Deprecated
    public static String next(String order) {
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

    @Deprecated
    public static ItemStack getIcon(String order) {
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
