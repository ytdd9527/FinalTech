package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BlockStorageLoreHelper extends BlockStorageHelper {
    private Map<String, List<String>> valueLoreMap;

    protected BlockStorageLoreHelper(@Nonnull String id, @Nonnull Map<String, List<String>> valueLoreMap) {
        super(id, BlockStorageLoreHelper.init(valueLoreMap));
        this.valueLoreMap = valueLoreMap;
    }

    protected BlockStorageLoreHelper(@Nonnull SlimefunItem slimefunItem, @Nonnull Map<String, List<String>> valueLoreMap) {
        super(slimefunItem.getId(), BlockStorageLoreHelper.init(valueLoreMap));
        this.valueLoreMap = valueLoreMap;
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull Map<String, List<String>> valueLoreMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageLoreHelper(id, valueLoreMap) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull Map<String, List<String>> valueLoreMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageLoreHelper(slimefunItem.getId(), valueLoreMap) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value) {
        if(valueLoreMap.containsKey(value)) {
            ItemStackUtil.setLore(iconItem, valueLoreMap.get(value));
            return true;
        } else {
            ItemStackUtil.setLore(iconItem, "§cERROR");
            return false;
        }
    }

    public static boolean setIcon(@Nonnull String id, @Nonnull String key, @Nonnull ItemStack iconItem, @Nullable String value) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                if(blockStorageHelper instanceof BlockStorageLoreHelper) {
                    ((BlockStorageLoreHelper) blockStorageHelper).setIcon(iconItem, value);
                    return true;
                }
            }
        }
        ItemStackUtil.setLore(iconItem, "§cERROR");
        return false;
    }

    public static boolean setIcon(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull ItemStack iconItem, @Nullable String value) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                if(blockStorageHelper instanceof BlockStorageLoreHelper) {
                    ((BlockStorageLoreHelper) blockStorageHelper).setIcon(iconItem, value);
                    return true;
                }
            }
        }
        ItemStackUtil.setLore(iconItem, "§cERROR");
        return false;
    }

    private static List<String> init(@Nonnull Map<String, List<String>> valueLoreMap) {
        List<String> valueList = new ArrayList<>(valueLoreMap.size());
        for(Map.Entry<String, List<String>> entry : valueLoreMap.entrySet()) {
            valueList.add(entry.getKey());
        }
        return valueList;
    }
}
