package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.KeyValueStringHelper;
import io.taraxacum.finaltech.api.factory.BlockStorageHelper;
import io.taraxacum.finaltech.api.factory.BlockStorageLoreHelper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class PositionInfo {
    public static final String KEY = "pi";

    public static final String VALUE_KEY_NORTH = "n";
    public static final String VALUE_KEY_EAST = "e";
    public static final String VALUE_KEY_SOUTH = "s";
    public static final String VALUE_KEY_WEST = "w";
    public static final String VALUE_KEY_UP = "u";
    public static final String VALUE_KEY_DOWN = "d";

    public static final ItemStack NORTH_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "position-info", "north", "name"), FinalTech.getLanguageStringArray("helper", "position-info", "north", "lore"));
    public static final ItemStack EAST_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "position-info", "east", "name"), FinalTech.getLanguageStringArray("helper", "position-info", "east", "lore"));
    public static final ItemStack SOUTH_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "position-info", "south", "name"), FinalTech.getLanguageStringArray("helper", "position-info", "south", "lore"));
    public static final ItemStack WEST_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "position-info", "west", "name"), FinalTech.getLanguageStringArray("helper", "position-info", "west", "lore"));
    public static final ItemStack UP_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "position-info", "up", "name"), FinalTech.getLanguageStringArray("helper", "position-info", "up", "lore"));
    public static final ItemStack DOWN_ICON = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, FinalTech.getLanguageString("helper", "position-info", "down", "name"), FinalTech.getLanguageStringArray("helper", "position-info", "down", "lore"));

    public static final String VALUE_NULL = null;
    public static final String VALUE_INPUT = "i";
    public static final String VALUE_OUTPUT = "o";
    public static final String VALUE_INPUT_AND_OUTPUT = "io";

    public static final List<String> NULL_LORE = FinalTech.getLanguageStringList("helper", "position-info", "null", "lore");
    public static final List<String> INPUT_LORE = FinalTech.getLanguageStringList("helper", "position-info", "input", "lore");
    public static final List<String> OUTPUT_LORE = FinalTech.getLanguageStringList("helper", "position-info", "output", "lore");
    public static final List<String> INPUT_AND_OUTPUT_LORE = FinalTech.getLanguageStringList("helper", "position-info", "input-and-output", "lore");

    public static final Material NULL_MATERIAL = Material.GRAY_STAINED_GLASS_PANE;
    public static final Material INPUT_MATERIAL = Material.BLUE_STAINED_GLASS_PANE;
    public static final Material OUTPUT_MATERIAL = Material.ORANGE_STAINED_GLASS_PANE;
    public static final Material INPUT_AND_OUTPUT_MATERIAL = Material.PURPLE_STAINED_GLASS_PANE;

    public static final KeyValueStringHelper MAP_EXAMPLE = new KeyValueStringHelper(Arrays.asList(VALUE_KEY_NORTH, VALUE_KEY_DOWN, VALUE_KEY_SOUTH, VALUE_KEY_WEST, VALUE_KEY_UP, VALUE_KEY_DOWN), Arrays.asList(VALUE_NULL, VALUE_INPUT, VALUE_OUTPUT, VALUE_INPUT_AND_OUTPUT));

    public static final Map<String, Material> valueMaterialMap = new HashMap<>() {{
        this.put(VALUE_NULL, NULL_MATERIAL);
        this.put(VALUE_INPUT, INPUT_MATERIAL);
        this.put(VALUE_OUTPUT, OUTPUT_MATERIAL);
        this.put(VALUE_INPUT_AND_OUTPUT, INPUT_AND_OUTPUT_MATERIAL);
    }};

    public static final BlockStorageLoreMaterialHelper NORTH_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_NORTH;
        }
    };

    public static final BlockStorageLoreMaterialHelper EAST_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_EAST;
        }
    };

    public static final BlockStorageLoreMaterialHelper SOUTH_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_SOUTH;
        }
    };

    public static final BlockStorageLoreMaterialHelper WEST_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_WEST;
        }
    };

    public static final BlockStorageLoreMaterialHelper UP_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_UP;
        }
    };

    public static final BlockStorageLoreMaterialHelper DOWN_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_DOWN;
        }
    };


    public static abstract class BlockStorageLoreMaterialHelper extends BlockStorageLoreHelper {
        BlockStorageLoreMaterialHelper() {
            super(BlockStorageHelper.ID_CARGO, new LinkedHashMap<>() {{
                this.put(VALUE_NULL, NULL_LORE);
                this.put(VALUE_INPUT, INPUT_LORE);
                this.put(VALUE_OUTPUT, OUTPUT_LORE);
                this.put(VALUE_INPUT_AND_OUTPUT, INPUT_AND_OUTPUT_LORE);
            }});
        }

        @Nonnull
        @Override
        public String getOrDefaultValue(@Nonnull Location location) {
            String valueMap = BlockStorage.getLocationInfo(location, KEY);
            KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
            String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
            if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                value = BlockStorageLoreMaterialHelper.this.defaultValue();
            }
            return value;
        }
        @Nonnull
        @Override
        public String getOrDefaultValue(@Nonnull Config config) {
            String valueMap = config.getString(KEY);
            KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
            String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
            if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                value = BlockStorageLoreMaterialHelper.this.defaultValue();
            }
            return value;
        }

        @Override
        public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value) {
            if (BlockStorageLoreMaterialHelper.this.validValue(value) && valueMaterialMap.containsKey(value)) {
                iconItem.setType(valueMaterialMap.get(value));
            }
            return BlockStorageLoreMaterialHelper.super.setIcon(iconItem, value);
        }
        @Override
        public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value, @Nonnull SlimefunItem slimefunItem) {
            if (BlockStorageLoreMaterialHelper.this.validValue(value) && valueMaterialMap.containsKey(value)) {
                iconItem.setType(valueMaterialMap.get(value));
            }
            return BlockStorageLoreMaterialHelper.super.setIcon(iconItem, value, slimefunItem);
        }

        @Override
        public boolean checkAndUpdateIcon(@Nonnull Inventory inventory, @Nonnull Location location, int slot) {
            String valueMap = BlockStorage.getLocationInfo(location, this.getKey());
            if(valueMap == null) {
                valueMap = "";
            }
            KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
            String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
            if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                value = BlockStorageLoreMaterialHelper.this.defaultValue();
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorage.addBlockInfo(location, KEY, keyValueStringHelper.toString());
            }
            ItemStack item = inventory.getItem(slot);
            BlockStorageLoreMaterialHelper.this.setIcon(item, value);
            return true;
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                if (!action.isRightClicked()) {
                    value = BlockStorageLoreMaterialHelper.this.clickNextValue(value, action);
                } else {
                    value = BlockStorageLoreMaterialHelper.this.clickPreviousValue(value, action);
                }
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getUpdateHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                    value = BlockStorageLoreMaterialHelper.this.defaultValue();
                }
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getNextHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                value = BlockStorageLoreMaterialHelper.this.clickNextValue(value, action);
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getPreviousHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                value = BlockStorageLoreMaterialHelper.this.clickPreviousValue(value, action);
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }
    }

    @Nonnull
    public static BlockFace[] getBlockFaces(@Nonnull String string, @Nonnull String... values) {
        KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(string);
        List<String> keyList = keyValueStringHelper.getAllMatchKey(values);
        BlockFace[] blockFaces = new BlockFace[keyList.size()];
        int i = 0;
        for(String key : keyList) {
            switch (key) {
                case VALUE_KEY_NORTH -> blockFaces[i++] = BlockFace.NORTH;
                case VALUE_KEY_EAST -> blockFaces[i++] = BlockFace.EAST;
                case VALUE_KEY_SOUTH -> blockFaces[i++] = BlockFace.SOUTH;
                case VALUE_KEY_WEST -> blockFaces[i++] = BlockFace.WEST;
                case VALUE_KEY_UP -> blockFaces[i++] = BlockFace.UP;
                case VALUE_KEY_DOWN -> blockFaces[i++] = BlockFace.DOWN;
            }
        }
        return blockFaces;
    }
    @Nonnull
    public static BlockFace[] getBlockFaces(@Nonnull Config config, @Nonnull String... values) {
        String string = config.getString(KEY);
        return PositionInfo.getBlockFaces(string, values);
    }
}
