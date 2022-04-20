package io.taraxacum.finaltech.util.menu;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class FilterMode {
    public static final String KEY = "filter-mode";

    public static final String VALUE_WHITE = "white";
    public static final String VALUE_BLACK = "black";

    public static final ItemStack FILTER_MODE_WHITE_ICON = new CustomItemStack(Material.WHITE_WOOL, "&f白名单模式", "&7仅当物品匹配下方9个格子的物品时", "&7才会进行运输");
    public static final ItemStack FILTER_MODE_BLACK_ICON = new CustomItemStack(Material.BLACK_WOOL, "&7黑名单模式", "&7仅当物品不匹配下方9个格子的物品时", "&7才会进行运输");
    public static String next(String filterMode) {
        if (filterMode == null) {
            return VALUE_WHITE;
        }
        switch (filterMode) {
            case VALUE_WHITE:
                return VALUE_BLACK;
            case VALUE_BLACK:
            default:
                return VALUE_WHITE;
        }
    }
    public static ItemStack getIcon(String filterMode) {
        if (filterMode == null) {
            return Icon.ERROR_ICON;
        }
        return switch (filterMode) {
            case VALUE_WHITE -> FILTER_MODE_WHITE_ICON;
            case VALUE_BLACK -> FILTER_MODE_BLACK_ICON;
            default -> Icon.ERROR_ICON;
        };
    }

    public static List<ItemStackWithWrapper> getMatchList(@Nonnull Inventory inventory, int[] filterSlots) {
        List<ItemStackWithWrapper> filterList = new ArrayList<>();
        for (int i = 0; i < filterSlots.length; i++) {
            if (!ItemStackUtil.isItemNull(inventory.getItem(filterSlots[i]))) {
                filterList.add(new ItemStackWithWrapper(inventory.getItem(filterSlots[i])));
            }
        }
        return filterList;
    }

    public static boolean ifMatch(@Nonnull ItemStack item, @Nonnull List<ItemStackWithWrapper> list, String filterMode) {
        if (ItemStackUtil.isItemNull(item)) {
            return VALUE_BLACK.equals(filterMode);
        }
        ItemStackWithWrapper itemStackWithWrapper = new ItemStackWithWrapper(item);
        for (ItemStackWithWrapper filterItem : list) {
            if (ItemStackUtil.isItemSimilar(itemStackWithWrapper.getItemStackWrapper(), filterItem.getItemStackWrapper())) {
                return VALUE_WHITE.equals(filterMode);
            }
        }
        return VALUE_BLACK.equals(filterMode);
    }

    public static boolean ifMatch(@Nonnull ItemStackWithWrapper itemStackWithWrapper, @Nonnull List<ItemStackWithWrapper> list, String filterMode) {
        for (ItemStackWithWrapper filterItem : list) {
            if (ItemStackUtil.isItemSimilar(itemStackWithWrapper.getItemStackWrapper(), filterItem.getItemStackWrapper())) {
                return VALUE_WHITE.equals(filterMode);
            }
        }
        return VALUE_BLACK.equals(filterMode);
    }

    public static boolean ifMatch(@Nonnull ItemStack itemStack, BlockMenu blockMenu, String filterMode, int[] filterSlots) {
        return ifMatch(itemStack, blockMenu.toInventory(), filterMode, filterSlots);
    }

    // todo
    public static boolean ifMatch(@Nonnull ItemStack itemStack, Inventory inv, String filterMode, int[] filterSlots) {
        for (int slot : filterSlots) {
            boolean itemSimilar = !ItemStackUtil.isItemNull(inv.getItem(slot)) && ItemStackUtil.isItemSimilar(itemStack, inv.getItem(slot));
            if (itemSimilar) {
                return VALUE_WHITE.equals(filterMode);
            }
        }
        return VALUE_BLACK.equals(filterMode);
    }
}
