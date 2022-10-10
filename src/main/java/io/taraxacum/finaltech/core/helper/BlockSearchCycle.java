package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.factory.BlockStorageHelper;
import io.taraxacum.finaltech.api.factory.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public final class BlockSearchCycle {
    public static final String KEY = "bsc";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_TRUE = "t";

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.MINECART, FinalTech.getLanguageString("helper", "BLOCK_SEARCH_CYCLE", "false", "name"), FinalTech.getLanguageStringArray("helper", "BLOCK_SEARCH_CYCLE", "false", "lore"));
    public static final ItemStack TRUE_ICON = new CustomItemStack(Material.CHEST_MINECART, FinalTech.getLanguageString("helper", "BLOCK_SEARCH_CYCLE", "true", "name"), FinalTech.getLanguageStringArray("helper", "BLOCK_SEARCH_CYCLE", "true", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_TRUE, TRUE_ICON);
    }});
}
