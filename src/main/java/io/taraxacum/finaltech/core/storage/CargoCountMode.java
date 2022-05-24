package io.taraxacum.finaltech.core.storage;

import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageLoreHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class CargoCountMode {
    public static final String KEY = "ccm";
    public static final String KEY_INPUT = "ccmi";
    public static final String KEY_OUTPUT = "ccmo";

    public static final String VALUE_UNIVERSAL = "u";
    public static final String VALUE_STANDALONE = "s";

    private static final String UNIVERSAL_LORE = "§7传输数量限制模式 §f通用模式";
    private static final String STANDALONE_LORE = "§7传输数量限制模式 §f独立模式";

    public static final BlockStorageLoreHelper HELPER = BlockStorageLoreHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});

    public static final BlockStorageLoreHelper INPUT_HELPER = BlockStorageLoreHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});

    public static final BlockStorageLoreHelper OUTPUT_HELPER = BlockStorageLoreHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});

    @Deprecated
    public static final String next(String countMode) {
        if (countMode == null) {
            return VALUE_UNIVERSAL;
        }
        switch (countMode) {
            case VALUE_UNIVERSAL:
                return VALUE_STANDALONE;
            case VALUE_STANDALONE:
            default:
                return VALUE_UNIVERSAL;
        }
    }

    @Deprecated
    public static final void setIcon(@Nonnull ItemStack item, String countMode) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        if (lore.size() < 1) {
            lore.set(0, "§7当前数量限制: §f" + item.getAmount());
        }
        switch (countMode) {
            case VALUE_UNIVERSAL:
                if (lore.size() < 2) {
                    lore.add(1, UNIVERSAL_LORE);
                } else {
                    lore.set(1, UNIVERSAL_LORE);
                }
                break;
            case VALUE_STANDALONE:
                if (lore.size() < 2) {
                    lore.add(1, STANDALONE_LORE);
                } else {
                    lore.set(1, STANDALONE_LORE);
                }
                break;
            default:
                break;
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
}
