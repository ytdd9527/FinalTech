package io.taraxacum.finaltech.core.storage;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.core.factory.BlockStorageHelper;
import io.taraxacum.finaltech.core.factory.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public final class CargoItemMode {
    public static final String KEY = "im";
    public static final String KEY_INPUT = "imi";
    public static final String KEY_OUTPUT = "imo";

    public static final String VALUE_ALL = "a";
    public static final String VALUE_TYPE = "t";
    public static final String VALUE_STACK = "s";
    public static final String VALUE_NONNULL = "n";


    public static final ItemStack ALL_ICON = new CustomItemStack(Material.PUMPKIN, "&7传输限制-否", "&7无效果");
    public static final ItemStack TYPE_ICON = new CustomItemStack(Material.CARVED_PUMPKIN, "&7传输限制-仅一种", "&7每次仅会传输一种物品");
    public static final ItemStack STACK_ICON = new CustomItemStack(Material.JACK_O_LANTERN, "&7传输限制-仅一组", "&7每次仅会传输一组物品", "&7并至多改变一个格子的物品数量");
    public static final ItemStack NONNULL_ICON = new CustomItemStack(Material.PAPER, "&a传输限制-非空", "&7每次传输时", "&7无视未含有物品的格子");


    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
    }});

    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
    }});

    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
    }});

    @Deprecated
    public static String next(String itemMode) {
        if (itemMode == null) {
            return VALUE_ALL;
        }
        switch (itemMode) {
            case VALUE_ALL:
                return VALUE_TYPE;
            case VALUE_TYPE:
                return VALUE_STACK;
            case VALUE_STACK:
            default:
                return VALUE_ALL;
        }
    }

    @Deprecated
    public static ItemStack getIcon(String itemMode) {
        if (itemMode == null) {
            return Icon.ERROR_ICON;
        }
        switch (itemMode) {
            case VALUE_ALL:
                return ALL_ICON;
            case VALUE_TYPE:
                return TYPE_ICON;
            case VALUE_STACK:
                return STACK_ICON;
            default:
                return Icon.ERROR_ICON;
        }
    }
}
