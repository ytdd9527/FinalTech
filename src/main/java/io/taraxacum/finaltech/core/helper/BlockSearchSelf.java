package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.factory.BlockStorageHelper;
import io.taraxacum.finaltech.api.factory.BlockStorageIconHelper;
import io.taraxacum.finaltech.util.TextUtil;
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

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.MINECART, FinalTech.getLanguageString("helper", "BLOCK_SEARCH_SELF", "false", "name"), FinalTech.getLanguageStringArray("helper", "BLOCK_SEARCH_SELF", "false", "lore"));
    public static final ItemStack BEGIN_ICON = new CustomItemStack(Material.CHEST_MINECART, FinalTech.getLanguageString("helper", "BLOCK_SEARCH_SELF", "begin", "name"), FinalTech.getLanguageStringArray("helper", "BLOCK_SEARCH_SELF", "begin", "lore"));
    public static final ItemStack LAST_ICON = new CustomItemStack(Material.CHEST_MINECART, FinalTech.getLanguageString("helper", "BLOCK_SEARCH_SELF", "last", "name"), FinalTech.getLanguageStringArray("helper", "BLOCK_SEARCH_SELF", "last", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_BEGIN, BEGIN_ICON);
        this.put(VALUE_LAST, LAST_ICON);
    }});
}
