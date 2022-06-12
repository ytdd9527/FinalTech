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
public final class BlockSearchOrder {
    public static final String KEY = "bso";

    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_REVERSE = "re";
    public static final String VALUE_RANDOM = "ra";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, TextUtil.colorPseudorandomString("搜索方向") + " " + TextUtil.colorRandomString("正向"));
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, TextUtil.colorPseudorandomString("搜索方向") + " " + TextUtil.colorRandomString("逆向"));
    public static final ItemStack RANDOM_ICON = new CustomItemStack(Material.CRIMSON_DOOR, TextUtil.colorPseudorandomString("搜索方向") + " " + TextUtil.colorRandomString("随机"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_POSITIVE, POSITIVE_ICON);
        this.put(VALUE_REVERSE, REVERSE_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
}
