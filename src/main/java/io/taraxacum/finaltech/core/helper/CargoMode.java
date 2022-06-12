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
 */
public final class CargoMode {
    public static final String KEY = "cm";

    public static final String VALUE_INPUT_MAIN = "im";
    public static final String VALUE_OUTPUT_MAIN = "om";
    public static final String VALUE_STRONG_SYMMETRY = "ss";
    public static final String VALUE_WEAK_SYMMETRY = "ws";

    private static final ItemStack INPUT_MAIN_ICON = new CustomItemStack(Material.WATER_BUCKET, TextUtil.colorPseudorandomString("传输模式") + " " + TextUtil.colorRandomString("输入侧优先"), TextUtil.colorRandomString("以输入侧为主要搜索顺序"));
    private static final ItemStack OUTPUT_MAIN_ICON = new CustomItemStack(Material.LAVA_BUCKET, TextUtil.colorPseudorandomString("传输模式") + " " + TextUtil.colorRandomString("输出侧优先"), TextUtil.colorRandomString("以输出侧为主要搜索顺序"));
    private static final ItemStack STRONG_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, TextUtil.colorPseudorandomString("传输模式") + " " + TextUtil.colorRandomString("强对称模式"), TextUtil.colorRandomString("以强制完全对称的方式进行搜索与传输"));
    private static final ItemStack WEAK_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, TextUtil.colorPseudorandomString("传输模式") + " " + TextUtil.colorRandomString("弱对称模式"), TextUtil.colorRandomString("以非完全对称的方式进行搜索与传输"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_STRONG_SYMMETRY, STRONG_SYMMETRY_ICON);
        this.put(VALUE_WEAK_SYMMETRY, WEAK_SYMMETRY_ICON);
        this.put(VALUE_INPUT_MAIN, INPUT_MAIN_ICON);
        this.put(VALUE_OUTPUT_MAIN, OUTPUT_MAIN_ICON);
    }});
}
