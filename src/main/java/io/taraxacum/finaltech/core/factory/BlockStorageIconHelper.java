package io.taraxacum.finaltech.core.factory;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
    public ItemStack getOrErrorIcon(@Nullable String value) {
        return valueIconMap.getOrDefault(value, ERROR_ICON);
    }

    @Nonnull
    public ItemStack defaultIcon() {
        return valueIconMap.get(this.defaultValue());
    }

    public boolean checkAndUpdateIcon(@Nonnull BlockMenu blockMenu, int slot) {
        String value = BlockStorage.getLocationInfo(blockMenu.getLocation(), this.getKey());
        if(!this.validValue(value)) {
            value = this.defaultValue();
        }
        blockMenu.replaceExistingItem(slot, this.getOrErrorIcon(value));
        return true;
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(block.getLocation());
            if(clickAction.isRightClicked()) {
                value = BlockStorageIconHelper.this.previousOrDefaultValue(value);
            } else {
                value = BlockStorageIconHelper.this.nextOrDefaultValue(value);
            }
            blockMenu.replaceExistingItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }
    @Nonnull
    public ChestMenu.MenuClickHandler getUpdateHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(block.getLocation());
            blockMenu.replaceExistingItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }
    @Nonnull
    public ChestMenu.MenuClickHandler getNextHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(block.getLocation());
            value = BlockStorageIconHelper.this.clickNextValue(value, clickAction);
            blockMenu.replaceExistingItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }
    @Nonnull
    public ChestMenu.MenuClickHandler getPreviousHandler(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull AbstractMachineMenu abstractMachineMenu, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(block.getLocation());
            value = BlockStorageIconHelper.this.clickPreviousValue(value, clickAction);
            blockMenu.replaceExistingItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(block.getLocation(), value);
            return false;
        };
    }

    public String clickNextValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
        return this.nextOrDefaultValue(value);
    }
    public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
        return this.previousOrDefaultValue(value);
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

    public static ItemStack getOrErrorIcon(@Nonnull String id, @Nonnull String key, @Nullable String value) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if(stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                if(blockStorageHelper instanceof BlockStorageIconHelper) {
                    return ((BlockStorageIconHelper) blockStorageHelper).getOrErrorIcon(value);
                }
            }
        }
        return ERROR_ICON;
    }

    public static ItemStack getOrErrorIcon(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nullable String value) {
        if(BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(slimefunItem.getId())) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(slimefunItem.getId());
            if(stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                if(blockStorageHelper instanceof BlockStorageIconHelper) {
                    return ((BlockStorageIconHelper) blockStorageHelper).getOrErrorIcon(value);
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
