package io.taraxacum.finaltech.core.helper;

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
public final class SlotSearchSize {
    public static final String KEY = "sss";
    public static final String KEY_INPUT = "sssi";
    public static final String KEY_OUTPUT = "ssso";

    public static final String VALUE_INPUTS_ONLY = "io";
    public static final String VALUE_OUTPUTS_ONLY = "oo";
    public static final String VALUE_INPUTS_AND_OUTPUTS = "iao";
    public static final String VALUE_OUTPUTS_AND_INPUTS = "oai";

    public static final ItemStack INPUTS_ONLY_ICON = new CustomItemStack(Material.SOUL_TORCH, TextUtil.colorPseudorandomString("容器搜索范围") + " " + TextUtil.colorRandomString("输入槽"));
    public static final ItemStack OUTPUTS_ONLY_ICON = new CustomItemStack(Material.TORCH, TextUtil.colorPseudorandomString("容器搜索范围") + " " + TextUtil.colorRandomString("输出槽"));
    public static final ItemStack INPUTS_AND_OUTPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, TextUtil.colorPseudorandomString("容器搜索范围") + " " + TextUtil.colorRandomString("输入槽+输出槽"));
    public static final ItemStack OUTPUTS_AND_INPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, TextUtil.colorPseudorandomString("容器搜索范围") + " " + TextUtil.colorRandomString("输出槽+输入槽"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});
    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
    }});
    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});
}
