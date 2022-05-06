package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Just for easily change value of the given key in specific condition
 * see {@link BlockStorage}
 *
 * @author Final_ROOT
 */
public abstract class BlockStorageHelper {
    private final List<String> valueList;
    /**
     * The id of a {@link SlimefunItem},
     * Or a public id
     */
    private final String id;

    public static final String ID_CARGO = "cargo";
    public static final String ID_STANDARD = "standard";

    private static final String ERROR = "0";

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

    @Nonnull
    public String getValue(@Nonnull Location location) {
        String value = BlockStorage.getLocationInfo(location, this.getKey());
        return value == null ? this.defaultValue() : value;
    }

    @Nonnull
    public String getValue(@Nonnull Config config) {
        return config.contains(this.getKey()) ? config.getString(this.getKey()) : this.defaultValue();
    }

    public void setValue(@Nonnull Location location, @Nullable String value) {
        BlockStorage.addBlockInfo(location, this.getKey(), value);
    }

    public void setValue(@Nonnull Config config, @Nullable String value) {
        config.setValue(this.getKey(), value);
    }

    @Nonnull
    public String defaultValue() {
        return this.valueList.isEmpty() ? ERROR : this.valueList.get(0);
    }

    @Nonnull
    public String nextValue(@Nullable String value) {
        return !this.valueList.contains(value) ? this.valueList.get((this.valueList.indexOf(value) + 1) % this.valueList.size()) : this.defaultValue();
    }

    @Nonnull
    public String previousValue(@Nullable String value) {
        return !this.valueList.contains(value) ? this.valueList.get((this.valueList.indexOf(value) - 1 + this.valueList.size()) % this.valueList.size()) : this.defaultValue();
    }

    public boolean validValue(@Nullable String value) {
        return valueList.contains(value);
    }

    public boolean checkOrSetBlockStorage(@Nonnull Location location) {
        if(BlockStorage.getLocationInfo(location, this.getKey()) != null) {
            BlockStorage.addBlockInfo(location, this.getKey(), this.defaultValue());
            return false;
        }
        return true;
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
