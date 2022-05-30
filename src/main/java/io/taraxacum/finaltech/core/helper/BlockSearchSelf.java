package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public final class BlockSearchSelf {
    public static final String KEY = "bss";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_BEGIN = "b";
    public static final String VALUE_LAST = "l";

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.MINECART, "&7搜索自身-否");
    public static final ItemStack BEGIN_ICON = new CustomItemStack(Material.CHEST_MINECART, "&7搜索自身-置于序列最前");
    public static final ItemStack LAST_ICON = new CustomItemStack(Material.CHEST_MINECART, "&7搜索自身-置于序列最后");

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_BEGIN, BEGIN_ICON);
        this.put(VALUE_LAST, LAST_ICON);
    }});
}
