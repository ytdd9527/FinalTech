package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public final class CargoOrder {
    public static final String KEY = "bco";

    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_REVERSE = "r";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTech.getLanguageString("helper", "CARGO_ORDER", "positive", "name"), FinalTech.getLanguageStringArray("helper", "CARGO_ORDER", "positive", "lore"));
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTech.getLanguageString("helper", "CARGO_ORDER", "reverse", "name"), FinalTech.getLanguageStringArray("helper", "CARGO_ORDER", "reverse", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_POSITIVE, POSITIVE_ICON);
        this.put(VALUE_REVERSE, REVERSE_ICON);
    }});
}
