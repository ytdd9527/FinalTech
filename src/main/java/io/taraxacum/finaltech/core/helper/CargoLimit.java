package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public final class CargoLimit {
    public static final String KEY = "cl";
    public static final String KEY_INPUT = "cli";
    public static final String KEY_OUTPUT = "clo";

    public static final String VALUE_ALL = "a";
    public static final String VALUE_TYPE = "t";
    public static final String VALUE_STACK = "s";
    public static final String VALUE_NONNULL = "n";
    public static final String VALUE_FIRST = "f";

    public static final ItemStack ALL_ICON = new CustomItemStack(Material.PUMPKIN, TextUtil.colorPseudorandomString("传输限制") + " " + TextUtil.colorRandomString("无"));
    public static final ItemStack TYPE_ICON = new CustomItemStack(Material.CARVED_PUMPKIN, TextUtil.colorPseudorandomString("传输限制") + " " + TextUtil.colorRandomString("种类限制"), TextUtil.colorRandomString("单次传输只会传输一种物品"));
    public static final ItemStack STACK_ICON = new CustomItemStack(Material.JACK_O_LANTERN, TextUtil.colorPseudorandomString("传输限制") + " " + TextUtil.colorRandomString("堆叠限制"), TextUtil.colorRandomString("单次传输只会传输一种且至多一组物品"));
    public static final ItemStack NONNULL_ICON = new CustomItemStack(Material.PAPER, TextUtil.colorPseudorandomString("传输限制") + " " + TextUtil.colorRandomString("非空"), TextUtil.colorRandomString("传输时无视不含有物品的格子"));
    public static final ItemStack FIRST_ICON = new CustomItemStack(Material.PAPER, TextUtil.colorPseudorandomString("传输限制") + " " + TextUtil.colorRandomString("首位阻断"), TextUtil.colorRandomString("单次传输时转移一次物品后即停止工作"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
        this.put(VALUE_FIRST, FIRST_ICON);
    }});
    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
        this.put(VALUE_FIRST, FIRST_ICON);
    }});
    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
        this.put(VALUE_FIRST, FIRST_ICON);
    }});

    public static boolean typeLimit(@Nonnull String value) {
        return VALUE_TYPE.equals(value) || VALUE_STACK.equals(value);
    }
}
