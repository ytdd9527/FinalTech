package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
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

    public static final ItemStack INPUTS_ONLY_ICON = new CustomItemStack(Material.SOUL_TORCH, "&9仅搜索输入槽", TextUtil.colorRandomString("对箱子、桶、漏斗等原版容器，该设置无效"));
    public static final ItemStack OUTPUTS_ONLY_ICON = new CustomItemStack(Material.TORCH, "&6仅搜索输出槽", TextUtil.colorRandomString("&7对箱子、桶、漏斗等原版容器，该设置无效"));
    public static final ItemStack INPUTS_AND_OUTPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, "&d搜索输入槽输出槽", TextUtil.colorRandomString("对箱子、桶、漏斗等原版容器，该设置无效"));
    public static final ItemStack OUTPUTS_AND_INPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, "&d搜索输出槽输入槽", TextUtil.colorRandomString("对箱子、桶、漏斗等原版容器，该设置无效"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});

    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});

    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT, new LinkedHashMap<>() {{
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
    }});

    @Deprecated
    public static ChestMenu.MenuClickHandler getInputHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String size = INPUT_HELPER.getOrDefaultValue(block.getLocation());
            if(clickAction.isRightClicked()) {
                size = INPUT_HELPER.previousOrDefaultValue(size);
            } else {
                size = INPUT_HELPER.nextOrDefaultValue(size);
            }
            blockMenu.replaceExistingItem(slot, INPUT_HELPER.getOrErrorIcon(size));
            INPUT_HELPER.setOrClearValue(block.getLocation(), size);
            return false;
        };
    }

    @Deprecated
    public static ChestMenu.MenuClickHandler getOutputHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String size = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(block.getLocation());
            if(clickAction.isRightClicked()) {
                size = SlotSearchSize.OUTPUT_HELPER.previousOrDefaultValue(size);
            } else {
                size = SlotSearchSize.OUTPUT_HELPER.nextOrDefaultValue(size);
            }
            blockMenu.replaceExistingItem(slot, SlotSearchSize.OUTPUT_HELPER.getOrErrorIcon(size));
            SlotSearchSize.OUTPUT_HELPER.setOrClearValue(block.getLocation(), size);
            return false;
        };
    }

    @Deprecated
    public static String next(String size) {
        if (size == null) {
            return VALUE_INPUTS_ONLY;
        }
        switch (size) {
            case VALUE_INPUTS_ONLY:
                return VALUE_OUTPUTS_ONLY;
            case VALUE_OUTPUTS_ONLY:
                return VALUE_INPUTS_AND_OUTPUTS;
            case VALUE_INPUTS_AND_OUTPUTS:
                return VALUE_OUTPUTS_AND_INPUTS;
            case VALUE_OUTPUTS_AND_INPUTS:
            default:
                return VALUE_INPUTS_ONLY;
        }
    }

    @Deprecated
    public static ItemStack getIcon(String size) {
        if (size == null) {
            return Icon.ERROR_ICON;
        }
        switch (size) {
            case VALUE_INPUTS_ONLY:
                return INPUTS_ONLY_ICON;
            case VALUE_OUTPUTS_ONLY:
                return OUTPUTS_ONLY_ICON;
            case VALUE_INPUTS_AND_OUTPUTS:
                return INPUTS_AND_OUTPUTS_ICON;
            case VALUE_OUTPUTS_AND_INPUTS:
                return OUTPUTS_AND_INPUTS_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
