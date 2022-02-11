package io.taraxacum.finaltech.util.cargo;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class SlotSearchSize {
    public static final String KEY_INPUT = "input-size";
    public static final String KEY_OUTPUT = "output-size";

    public static final String VALUE_INPUTS_ONLY = "inputs-only";
    public static final String VALUE_OUTPUTS_ONLY = "outputs-only";
    public static final String VALUE_INPUTS_AND_OUTPUTS = "inputs-and-outputs";

    public static final ItemStack INPUTS_ONLY_ICON = new CustomItemStack(Material.SOUL_TORCH, "&9仅搜索输入槽", "&7对箱子、桶、漏斗等原版容器，该设置无效");
    public static final ItemStack OUTPUTS_ONLY_ICON = new CustomItemStack(Material.TORCH, "&6仅搜索输出槽", "&7对箱子、桶、漏斗等原版容器，该设置无效");
    public static final ItemStack INPUTS_AND_OUTPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, "&d搜索输入槽输出槽", "&7对箱子、桶、漏斗等原版容器，该设置无效");

    public static final String next(String size) {
        if(size == null) {
            return VALUE_INPUTS_ONLY;
        }
        switch (size) {
            case VALUE_INPUTS_ONLY:
                return VALUE_OUTPUTS_ONLY;
            case VALUE_OUTPUTS_ONLY:
                return VALUE_INPUTS_AND_OUTPUTS;
            case VALUE_INPUTS_AND_OUTPUTS:
            default:
                return VALUE_INPUTS_ONLY;
        }
    }

    public static final ItemStack getIcon(String size) {
        if(size == null) {
            return Icon.ERROR_ICON;
        }
        switch (size) {
            case VALUE_INPUTS_ONLY:
                return INPUTS_ONLY_ICON;
            case VALUE_OUTPUTS_ONLY:
                return OUTPUTS_ONLY_ICON;
            case VALUE_INPUTS_AND_OUTPUTS:
                return INPUTS_AND_OUTPUTS_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
