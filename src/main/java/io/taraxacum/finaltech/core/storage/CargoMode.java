package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
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

    private static final ItemStack INPUT_MAIN_ICON = new CustomItemStack(Material.WATER_BUCKET, "&9输入侧优先", "&7以输入侧物品为主要搜索顺序");
    private static final ItemStack OUTPUT_MAIN_ICON = new CustomItemStack(Material.LAVA_BUCKET, "&6输出侧优先", "&7以输出侧物品为主要搜索顺序");
    private static final ItemStack STRONG_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, "&d强对称传输", "&7以对称的方式进行搜索与传输");
    private static final ItemStack WEAK_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, "&d弱对称传输", "&7以对称的方式进行搜索与传输");

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_INPUT_MAIN, INPUT_MAIN_ICON);
        this.put(VALUE_OUTPUT_MAIN, OUTPUT_MAIN_ICON);
        this.put(VALUE_STRONG_SYMMETRY, STRONG_SYMMETRY_ICON);
        this.put(VALUE_WEAK_SYMMETRY, WEAK_SYMMETRY_ICON);
    }});
}
