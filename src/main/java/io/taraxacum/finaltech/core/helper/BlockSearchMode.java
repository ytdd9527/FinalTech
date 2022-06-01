package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.w3c.dom.Text;

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

    public static final ItemStack ZERO_ICON = new CustomItemStack((Material.PURPLE_STAINED_GLASS), TextUtil.colorPseudorandomString("搜索模式") + " " + TextUtil.colorRandomString("零模式"),
            TextUtil.colorRandomString("不进行远距离搜索"));
    public static final ItemStack INHERIT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, TextUtil.colorPseudorandomString("搜索模式") + " " + TextUtil.colorRandomString("继承模式"),
            TextUtil.colorRandomString("继承相同类型方块的方向"),
            TextUtil.colorRandomString("同时会延长最大搜索范围"));
    public static final ItemStack PENETRATE_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, TextUtil.colorPseudorandomString("搜索模式") + " " + TextUtil.colorRandomString("穿透模式"),
            TextUtil.colorRandomString("尝试穿透相同类型的方块"),
            TextUtil.colorRandomString("同时会延长最大搜索范围"));
    public static final ItemStack RESPECT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, TextUtil.colorPseudorandomString("搜索模式") + " " + TextUtil.colorRandomString("链接模式"),
            TextUtil.colorRandomString("允许用锁链延长搜索范围"),
            TextUtil.colorRandomString("搜索到相同类型的方块时停止搜索"));
    public static final ItemStack INTERRUPT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, TextUtil.colorPseudorandomString("搜索模式") + " " + TextUtil.colorRandomString("中断模式"),
            TextUtil.colorRandomString("搜索到相同类型方块时"),
            TextUtil.colorRandomString("中断远程搜索"));

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
}
