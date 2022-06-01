package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.common.annotation.Translate;
import io.taraxacum.finaltech.api.dto.KeyValueStringHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageLoreHelper;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;
import org.w3c.dom.Text;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class SlotSearchLine {
    public static final String KEY = "ssl";

    public static final String VALUE_KEY_L1 = "l1";
    public static final String VALUE_KEY_L2 = "l2";
    public static final String VALUE_KEY_L3 = "l3";

    @Translate
    public static final ItemStack L1_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, TextUtil.colorPseudorandomString("第一行"));
    @Translate
    public static final ItemStack L2_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, TextUtil.colorPseudorandomString("第二行"));
    @Translate
    public static final ItemStack L3_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, TextUtil.colorPseudorandomString("第三行"));

    public static final String VALUE_NULL = null;
    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_RESERVE = "re";
    public static final String VALUE_RANDOM = "ra";

    @Translate
    public static final String NULL_LORE = TextUtil.colorRandomString("无效");
    @Translate
    public static final String POSITIVE_LORE = TextUtil.colorRandomString("正向");
    @Translate
    public static final String RESERVE_LORE = TextUtil.colorRandomString("逆向");
    @Translate
    public static final String RANDOM_LORE = TextUtil.colorRandomString("随机");

    public static final Material NULL_MATERIAL = Material.GRAY_STAINED_GLASS_PANE;
    public static final Material POSITIVE_MATERIAL = Material.BLUE_STAINED_GLASS_PANE;
    public static final Material RESERVE_MATERIAL = Material.ORANGE_STAINED_GLASS_PANE;
    public static final Material RANDOM_MATERIAL = Material.PURPLE_STAINED_GLASS_PANE;

    public static final KeyValueStringHelper MAP_EXAMPLE = new KeyValueStringHelper(Arrays.asList(VALUE_KEY_L1, VALUE_KEY_L2, VALUE_KEY_L3), Arrays.asList(VALUE_NULL, VALUE_POSITIVE, VALUE_RESERVE, VALUE_RANDOM));

    public static final Map<String, Material> valueMaterialMap = new HashMap<>() {{
        this.put(VALUE_NULL, NULL_MATERIAL);
        this.put(VALUE_POSITIVE, POSITIVE_MATERIAL);
        this.put(VALUE_RESERVE, RESERVE_MATERIAL);
        this.put(VALUE_RANDOM, RANDOM_MATERIAL);
    }};

    public static final BlockStorageLoreMaterialHelper L1_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_L1;
        }
    };

    public static final BlockStorageLoreMaterialHelper L2_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_L2;
        }
    };

    public static final BlockStorageLoreMaterialHelper L3_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_L3;
        }
    };

    public static abstract class BlockStorageLoreMaterialHelper extends BlockStorageLoreHelper {
        BlockStorageLoreMaterialHelper() {
            super(BlockStorageHelper.ID_CARGO, new LinkedHashMap<>() {{
                this.put(VALUE_NULL, List.of(NULL_LORE));
                this.put(VALUE_POSITIVE, List.of(POSITIVE_LORE));
                this.put(VALUE_RESERVE, List.of(RESERVE_LORE));
                this.put(VALUE_RANDOM, List.of(RANDOM_LORE));
            }});
        }

        @Nullable
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
        @Nullable
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
        public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value, @Nonnull AbstractMachine abstractMachine) {
            if (BlockStorageLoreMaterialHelper.this.validValue(value) && valueMaterialMap.containsKey(value)) {
                iconItem.setType(valueMaterialMap.get(value));
            }
            return BlockStorageLoreMaterialHelper.super.setIcon(iconItem, value, abstractMachine);
        }

        @Override
        public void setOrClearValue(@Nonnull Location location, @Nullable String value) {
            BlockStorage.addBlockInfo(location, KEY, value);
        }

        @Override
        public void setOrClearValue(@Nonnull Config config, @Nullable String value) {
            config.setValue(KEY, value);
        }

        @Override
        public boolean checkAndUpdateIcon(@Nonnull BlockMenu blockMenu, int slot) {
            String valueMap = BlockStorage.getLocationInfo(blockMenu.getLocation(), this.getKey());
            KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
            String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
            if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                value = BlockStorageLoreMaterialHelper.this.defaultValue();
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorage.addBlockInfo(blockMenu.getLocation(), KEY, keyValueStringHelper.toString());
            }
            ItemStack item = blockMenu.getItemInSlot(slot);
            BlockStorageLoreMaterialHelper.this.setIcon(item, value);
            return true;
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(block.getLocation(), KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                if (!action.isRightClicked()) {
                    value = BlockStorageLoreMaterialHelper.this.clickNextValue(value, action);
                } else {
                    value = BlockStorageLoreMaterialHelper.this.clickPreviousValue(value, action);
                }
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(block.getLocation(), keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getUpdateHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(block.getLocation(), KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                    value = BlockStorageLoreMaterialHelper.this.defaultValue();
                }
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(block.getLocation(), keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getNextHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(block.getLocation(), KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                value = BlockStorageLoreMaterialHelper.this.clickNextValue(value, action);
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(block.getLocation(), keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getPreviousHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(block.getLocation(), KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                value = BlockStorageLoreMaterialHelper.this.clickPreviousValue(value, action);
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(block.getLocation(), keyValueStringHelper.toString());
                return false;
            };
        }
    };
}
