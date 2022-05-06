package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 */
public class CargoMode {
    public static final String KEY = "cargo-mode";

    public static final String VALUE_INPUT_MAIN = "input-main";
    public static final String VALUE_OUTPUT_MAIN = "output-main";
    public static final String VALUE_STRONG_SYMMETRY = "strong-symmetry";
    public static final String VALUE_WEAK_SYMMETRY = "weak-symmetry";

    private static final ItemStack INPUT_MAIN_ICON = new CustomItemStack(Material.WATER_BUCKET, "&9主输入侧", "&7以输入侧物品为主要搜索顺序");
    private static final ItemStack OUTPUT_MAIN_ICON = new CustomItemStack(Material.LAVA_BUCKET, "&6主输出侧", "&7以输出侧物品为主要搜索顺序");
    private static final ItemStack STRONG_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, "&d强对称传输", "&7以对称的方式进行搜索与传输");
    private static final ItemStack WEAK_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, "&d弱对称传输", "&7以对称的方式进行搜索与传输");

    public static final BlockStorageIconHelper HELPER = new BlockStorageIconHelper("CARGO", new LinkedHashMap<>() {
        {
            this.put(VALUE_INPUT_MAIN, INPUT_MAIN_ICON);
            this.put(VALUE_OUTPUT_MAIN, OUTPUT_MAIN_ICON);
            this.put(VALUE_STRONG_SYMMETRY, STRONG_SYMMETRY_ICON);
            this.put(VALUE_WEAK_SYMMETRY, WEAK_SYMMETRY_ICON);
        }
    }) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY;
        }
    };
}
