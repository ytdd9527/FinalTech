package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.api.factory.BlockStorageHelper;
import io.taraxacum.finaltech.api.factory.BlockStorageIconHelper;
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

    public static final ItemStack ASCENT_ICON = new CustomItemStack(Material.BLUE_WOOL, TextUtil.colorPseudorandomString("容器搜索模式") + " " + TextUtil.colorRandomString("顺序搜索"));
    public static final ItemStack DESCEND_ICON = new CustomItemStack(Material.ORANGE_WOOL, TextUtil.colorPseudorandomString("容器搜索模式") + " " + TextUtil.colorRandomString("逆序搜索"));
    public static final ItemStack FIRST_ONLY_ICON = new CustomItemStack(Material.BLUE_CARPET, TextUtil.colorPseudorandomString("容器搜索模式") + " " + TextUtil.colorRandomString("仅首格"));
    public static final ItemStack LAST_ONLY_ICON = new CustomItemStack(Material.ORANGE_CARPET, TextUtil.colorPseudorandomString("容器搜索模式") + " " + TextUtil.colorRandomString("仅末格"));
    public static final ItemStack RANDOM_ICON = new CustomItemStack(Material.PAPER, TextUtil.colorPseudorandomString("容器搜索模式") + " " + TextUtil.colorRandomString("随机"));

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
}
