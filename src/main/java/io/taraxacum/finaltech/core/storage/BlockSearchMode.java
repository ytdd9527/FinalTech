package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import io.taraxacum.finaltech.setup.FinalTechItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public final class BlockSearchMode {
    public static final String KEY = "bsm";
    public static final String KEY_INPUT = "bsmi";
    public static final String KEY_OUTPUT = "bsmo";

    public static final String SUB_KEY_PIPE = "-p";
    public static final String SUB_KEY_LINE = "-l";
    public static final String SUB_KEY_STATION = "-s";

    public static final String VALUE_ZERO = "z";
    public static final String VALUE_INHERIT = "ih";
    public static final String VALUE_PENETRATE = "p";
    public static final String VALUE_RESPECT = "r";
    public static final String VALUE_INTERRUPT = "it";

    public static final ItemStack ZERO_ICON = new CustomItemStack((Material.PURPLE_STAINED_GLASS), "&7搜索模式",
            "&f零模式",
            "",
            "&7不进行远距离搜索");
    public static final ItemStack INHERIT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f继承模式",
            "",
            "&7继承相同类型方块的方向",
            "&7同时会延长最大搜索范围");
    public static final ItemStack PENETRATE_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f穿透模式",
            "",
            "&7尝试穿透相同类型的方块",
            "&7同时会延长最大搜索范围");
    public static final ItemStack RESPECT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f链接模式",
            "",
            "&7允许用锁链延长搜索范围",
            "&7搜索到相同类型的方块时停止搜索");
    public static final ItemStack INTERRUPT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, "&7搜索模式",
            "&f中断模式",
            "",
            "&7搜索到相同类型方块时",
            "&7中断远程搜索");

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ZERO, ZERO_ICON);
        this.put(VALUE_INHERIT, INHERIT_ICON);
        this.put(VALUE_PENETRATE, PENETRATE_ICON);
        this.put(VALUE_RESPECT, RESPECT_ICON);
        this.put(VALUE_INTERRUPT, INTERRUPT_ICON);
    }});

    public static final BlockStorageIconHelper PIPE_INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT + SUB_KEY_PIPE, new LinkedHashMap<>() {{
            this.put(VALUE_ZERO, ZERO_ICON);
            this.put(VALUE_INHERIT, INHERIT_ICON);
            this.put(VALUE_PENETRATE, PENETRATE_ICON);
    }});

    public static final BlockStorageIconHelper PIPE_OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT + SUB_KEY_PIPE, new LinkedHashMap<>() {{
            this.put(VALUE_ZERO, ZERO_ICON);
            this.put(VALUE_INHERIT, INHERIT_ICON);
            this.put(VALUE_PENETRATE, PENETRATE_ICON);
    }});

    public static final BlockStorageIconHelper LINE_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY + SUB_KEY_LINE, new LinkedHashMap<>() {{
            this.put(VALUE_ZERO, ZERO_ICON);
            this.put(VALUE_PENETRATE, PENETRATE_ICON);
            this.put(VALUE_RESPECT, RESPECT_ICON);
    }});

    public static final BlockStorageIconHelper STATION_INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT + SUB_KEY_STATION, new LinkedHashMap<>() {{
            this.put(VALUE_ZERO, ZERO_ICON);
            this.put(VALUE_PENETRATE, PENETRATE_ICON);
            this.put(VALUE_INTERRUPT, INTERRUPT_ICON);
    }});

    public static final BlockStorageIconHelper STATION_OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT + SUB_KEY_STATION, new LinkedHashMap<>() {{
            this.put(VALUE_ZERO, ZERO_ICON);
            this.put(VALUE_PENETRATE, PENETRATE_ICON);
            this.put(VALUE_INTERRUPT, INTERRUPT_ICON);
    }});

    @Deprecated
    public static final String next(String blockSearchMode, String id) {
        if (blockSearchMode == null || id == null) {
            return VALUE_ZERO;
        }
        if (id.equals(FinalTechItems.TRANSFER_PIPE.getItemId())) {
            switch (blockSearchMode) {
                case VALUE_ZERO:
                    return VALUE_INHERIT;
                case VALUE_INHERIT:
                    return VALUE_PENETRATE;
                case VALUE_PENETRATE:
                default:
                    return VALUE_ZERO;
            }
        } else if (id.equals(FinalTechItems.TRANSFER_STATION.getItemId())) {
            switch (blockSearchMode) {
                case VALUE_ZERO:
                    return VALUE_PENETRATE;
                case VALUE_PENETRATE:
                    return VALUE_RESPECT;
                case VALUE_RESPECT:
                default:
                    return VALUE_ZERO;
            }
        } else if (id.equals(FinalTechItems.TRANSFER_LINE.getItemId())) {
            switch (blockSearchMode) {
                case VALUE_ZERO:
                    return VALUE_PENETRATE;
                case VALUE_PENETRATE:
                    return VALUE_INTERRUPT;
                case VALUE_INHERIT:
                default:
                    return VALUE_ZERO;
            }
        }
        return VALUE_ZERO;
    }

    @Deprecated
    public static final ItemStack getIcon(String blockSearchMode) {
        if (blockSearchMode == null) {
            return Icon.ERROR_ICON;
        }
        switch (blockSearchMode) {
            case VALUE_ZERO:
                return ZERO_ICON;
            case VALUE_INHERIT:
                return INHERIT_ICON;
            case VALUE_PENETRATE:
                return PENETRATE_ICON;
            case VALUE_RESPECT:
                return RESPECT_ICON;
            case VALUE_INTERRUPT:
                return INTERRUPT_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
