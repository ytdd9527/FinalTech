package io.taraxacum.finaltech.api.dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class KeyValueStringHelper {
    private final Set<String> keySet;
    private final Set<String> valueSet;
    private final Map<String, String> map;

    public KeyValueStringHelper(Set<String> keySet, Set<String> valueSet) {
        this.keySet = keySet;
        this.valueSet = valueSet;
        this.map = new LinkedHashMap<>();
    }
    public KeyValueStringHelper(List<String> keySet, List<String> valueSet) {
        this.keySet = new HashSet<>(keySet.size());
        this.keySet.addAll(keySet);
        this.valueSet = new HashSet<>(valueSet.size());
        this.valueSet.addAll(valueSet);
        this.map = new LinkedHashMap<>();
    }
    public KeyValueStringHelper(String[] keys, String[] values) {
        this.keySet = new HashSet<>(keys.length);
        this.keySet.addAll(Arrays.asList(keys));
        this.valueSet = new HashSet<>(values.length);
        this.valueSet.addAll(Arrays.asList(values));
        this.map = new LinkedHashMap<>();
    }

    public boolean validKey(@Nonnull String key) {
        return this.keySet.contains(key);
    }

    public boolean validValue(@Nonnull String value) {
        return this.valueSet.contains(value);
    }

    public void putEntry(@Nonnull String key, @Nullable String value) {
        if (this.validKey(key)) {
            if (value == null && this.map.containsKey(key)) {
                this.map.remove(key);
            } else if (this.validValue(value)) {
                this.map.put(key, value);
            }
        }
    }
    public void deleteEntry(@Nonnull String key) {
        this.putEntry(key, null);
    }

    @Nonnull
    public List<String> getAllMatchKey(@Nonnull String value) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(value)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    @Nullable
    public String getValue(@Nonnull String key) {
        return this.map.get(key);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append(":").append(value);
            stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

    @Nonnull
    public KeyValueStringHelper parseString(@Nullable String string) {
        KeyValueStringHelper keyValueStringHelper = new KeyValueStringHelper(this.keySet, this.valueSet);
        if (string != null) {
            for (String entry : string.split("-")) {
                String[] split = entry.split(":");
                if (split.length == 2 && this.keySet.contains(split[0]) && this.valueSet.contains(split[1])) {
                    keyValueStringHelper.putEntry(split[0], split[1]);
                }
            }
        }
        return keyValueStringHelper;
    }
}
