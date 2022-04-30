package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public abstract class BlockStorageHelper {
    private final List<String> valueList;
    private final String id;

    protected static final Map<String, Map<String, BlockStorageHelper>> BLOCK_STORAGE_HELPER_FACTORY = new HashMap<>();

    protected BlockStorageHelper(@Nonnull String id, @Nonnull List<String> valueList) {
        this.id = id;
        this.valueList = valueList;
        if(!BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            BLOCK_STORAGE_HELPER_FACTORY.put(id, new HashMap<>());
        }
        BLOCK_STORAGE_HELPER_FACTORY.get(id).put(this.getKey(), this);
    }

    protected BlockStorageHelper(@Nonnull SlimefunItem slimefunItem, @Nonnull List<String> valueList) {
        this.id = slimefunItem.getId();
        this.valueList = valueList;
        if(!BLOCK_STORAGE_HELPER_FACTORY.containsKey(this.id)) {
            BLOCK_STORAGE_HELPER_FACTORY.put(this.id, new HashMap<>());
        }
        BLOCK_STORAGE_HELPER_FACTORY.get(this.id).put(this.getKey(), this);
    }

    @Nullable
    public String defaultValue() {
        return this.valueList.isEmpty() ? null : this.valueList.get(0);
    }

    @Nullable
    public String nextValue(@Nullable String value) {
        return !this.valueList.contains(value) ? this.valueList.get((this.valueList.indexOf(value) + 1) % this.valueList.size()) : this.defaultValue();
    }

    @Nullable
    public String previousValue(@Nullable String value) {
        return !this.valueList.contains(value) ? this.valueList.get((this.valueList.indexOf(value) - 1 + this.valueList.size()) % this.valueList.size()) : this.defaultValue();
    }

    @Nonnull
    public abstract String getKey();

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull List<String> valueList) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageHelper(id, valueList) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull String... values) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageHelper(id, new ArrayList<>(List.of(values))) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull List<String> valueList) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageHelper(slimefunItem.getId(), valueList) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull String... values) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageHelper(slimefunItem.getId(), new ArrayList<>(List.of(values))) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    @Nullable
    public static String defaultValue(@Nonnull String id, @Nullable String key) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key).defaultValue();
            }
        }
        return null;
    }

    @Nullable
    public static String defaultValue(@Nonnull SlimefunItem slimefunItem, @Nullable String key) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key).defaultValue();
            }
        }
        return null;
    }

    @Nullable
    public static String nextValue(@Nonnull String id, @Nullable String key, @Nullable String value) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key).nextValue(value);
            }
        }
        return null;
    }

    @Nullable
    public static String nextValue(@Nonnull SlimefunItem slimefunItem, @Nullable String key, @Nullable String value) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key).nextValue(value);
            }
        }
        return null;
    }

    @Nullable
    public static String previousValue(@Nonnull String id, @Nullable String key, @Nullable String value) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key).previousValue(value);
            }
        }
        return null;
    }

    @Nullable
    public static String previousValue(@Nonnull SlimefunItem slimefunItem, @Nullable String key, @Nullable String value) {
        if(BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key).previousValue(value);
            }
        }
        return null;
    }
}
