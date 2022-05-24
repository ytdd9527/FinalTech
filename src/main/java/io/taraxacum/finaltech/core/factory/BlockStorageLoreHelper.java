package io.taraxacum.finaltech.core.factory;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class BlockStorageLoreHelper extends BlockStorageHelper {
    private Map<String, List<String>> valueLoreMap;
    private int loreOffset = -1;
    private static final List<String> ERROR_LORE = List.of("§cERROR");

    protected BlockStorageLoreHelper(@Nonnull String id, @Nonnull Map<String, List<String>> valueLoreMap) {
        super(id, BlockStorageLoreHelper.init(valueLoreMap));
        this.valueLoreMap = valueLoreMap;
    }

    protected BlockStorageLoreHelper(@Nonnull SlimefunItem slimefunItem, @Nonnull Map<String, List<String>> valueLoreMap) {
        super(slimefunItem.getId(), BlockStorageLoreHelper.init(valueLoreMap));
        this.valueLoreMap = valueLoreMap;
    }

    protected BlockStorageLoreHelper(@Nonnull String id, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        this(id, valueLoreMap);
        this.loreOffset = loreOffset;
    }

    protected BlockStorageLoreHelper(@Nonnull SlimefunItem slimefunItem, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        this(slimefunItem, valueLoreMap);
        this.loreOffset = loreOffset;
    }

    public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value) {
        if(valueLoreMap.containsKey(value)) {
            if(this.loreOffset < 0) {
                ItemStackUtil.setLore(iconItem, valueLoreMap.get(value));
            } else {
                ItemStackUtil.replaceLore(iconItem, this.loreOffset, valueLoreMap.get(value));
            }
            return true;
        } else {
            if(this.loreOffset < 0) {
                ItemStackUtil.setLore(iconItem, ERROR_LORE);
            } else {
                ItemStackUtil.replaceLore(iconItem, this.loreOffset, ERROR_LORE);
            }
            return false;
        }
    }
    /**
     * Override this method to do some function
     * @param iconItem
     * @param value
     * @param abstractMachine
     * @return
     */
    public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value, @Nonnull AbstractMachine abstractMachine) {
        return this.setIcon(iconItem, value);
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(block.getLocation());
            if(clickAction.isRightClicked()) {
                value = BlockStorageLoreHelper.this.previousOrDefaultValue(value);
            } else {
                value = BlockStorageLoreHelper.this.nextOrDefaultValue(value);
            }
            ItemStack item = blockMenu.getItemInSlot(slot);
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getUpdateHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(block.getLocation());
            ItemStack item = blockMenu.getItemInSlot(slot);
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getNextHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(block.getLocation());
            value = BlockStorageLoreHelper.this.clickNextValue(value, clickAction);
            ItemStack item = blockMenu.getItemInSlot(slot);
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getPreviousHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(block.getLocation());
            value = BlockStorageLoreHelper.this.clickPreviousValue(value, clickAction);
            ItemStack item = blockMenu.getItemInSlot(slot);
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }

    public String clickNextValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
        return this.nextOrDefaultValue(value);
    }

    public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
        return this.previousOrDefaultValue(value);
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
        ItemStackUtil.setLore(iconItem, ERROR_LORE);
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
        ItemStackUtil.setLore(iconItem, ERROR_LORE);
        return false;
    }

    @Nonnull
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull Map<String, List<String>> valueLoreMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return (BlockStorageLoreHelper) stringBlockStorageHelperMap.get(key);
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
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull Map<String, List<String>> valueLoreMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return (BlockStorageLoreHelper) stringBlockStorageHelperMap.get(key);
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

    @Nonnull
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return (BlockStorageLoreHelper) stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageLoreHelper(id, loreOffset, valueLoreMap) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }
    @Nonnull
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                return (BlockStorageLoreHelper) stringBlockStorageHelperMap.get(key);
            }
        }
        return new BlockStorageLoreHelper(slimefunItem.getId(), loreOffset, valueLoreMap) {
            @Nonnull
            @Override
            public String getKey() {
                return key;
            }
        };
    }

    private static List<String> init(@Nonnull Map<String, List<String>> valueLoreMap) {
        List<String> valueList = new ArrayList<>(valueLoreMap.size());
        for(Map.Entry<String, List<String>> entry : valueLoreMap.entrySet()) {
            valueList.add(entry.getKey());
        }
        return valueList;
    }
}
