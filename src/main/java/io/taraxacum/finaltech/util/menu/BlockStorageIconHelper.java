package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BlockStorageIconHelper extends BlockStorageHelper {
    private final Map<String, ItemStack> valueIconMap;
    private static final ItemStack ERROR_ICON = new CustomItemStack(Material.BARRIER, "&cERROR");

    protected BlockStorageIconHelper(@Nonnull String id, @Nonnull Map<String, ItemStack> valueIconMap) {
        super(id, BlockStorageIconHelper.init(valueIconMap));
        this.valueIconMap = valueIconMap;
    }

    protected BlockStorageIconHelper(@Nonnull SlimefunItem slimefunItem, @Nonnull Map<String, ItemStack> valueIconMap) {
        super(slimefunItem.getId(), BlockStorageIconHelper.init(valueIconMap));
        this.valueIconMap = valueIconMap;
    }

    @Nonnull
    public ItemStack getIcon(@Nullable String value) {
        return valueIconMap.getOrDefault(value, ERROR_ICON);
    }

    @Nonnull
    public ItemStack getDefaultIcon() {
        return valueIconMap.get(this.defaultValue());
    }

    @Nonnull
    public static BlockStorageIconHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull Map<String, ItemStack> valueIconMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                if(blockStorageHelper instanceof BlockStorageIconHelper) {
                    return (BlockStorageIconHelper) blockStorageHelper;
                } else {
                    blockStorageHelper = new BlockStorageIconHelper(id, valueIconMap) {
                        @Nonnull
                        @Override
                        public String getKey() {
                            return key;
                        }
                    };
                    stringBlockStorageHelperMap.put(key, blockStorageHelper);
                    return (BlockStorageIconHelper) blockStorageHelper;
                }
            }
        }
        return new BlockStorageIconHelper(id, valueIconMap) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull Map<String, ItemStack> valueIconMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageIconHelper(slimefunItem.getId(), valueIconMap) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    public static ItemStack getIcon(@Nonnull String id, @Nonnull String key, @Nullable String value) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                if(blockStorageHelper instanceof BlockStorageIconHelper) {
                    return ((BlockStorageIconHelper) blockStorageHelper).getIcon(value);
                }
            }
        }
        return ERROR_ICON;
    }

    public static ItemStack getIcon(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nullable String value) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                if(blockStorageHelper instanceof BlockStorageIconHelper) {
                    return ((BlockStorageIconHelper) blockStorageHelper).getIcon(value);
                }
            }
        }
        return ERROR_ICON;
    }

    @Nonnull
    private static List<String> init(@Nonnull Map<String, ItemStack> valueIconMap) {
        List<String> valueList = new ArrayList<>(valueIconMap.size());
        for(Map.Entry<String, ItemStack> entry : valueIconMap.entrySet()) {
            if(!ItemStackUtil.isItemNull(entry.getValue())) {
                valueList.add(entry.getKey());
            }
        }
        return valueList;
    }
}
