package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapperAmount;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.setup.FinalTechItems;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public final class MachineUtil {
    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_PLACER_ALLOW = new BlockPlaceHandler(true) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {

        }
    };
    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_PLACER_DENY = new BlockPlaceHandler(false) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {

        }
    };
    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_DENY = new BlockPlaceHandler(false) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
            blockPlaceEvent.setCancelled(true);
        }
    };

    public static BlockBreakHandler simpleBlockBreakerHandler() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {

            }
        };
    }
    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull AbstractMachine abstractMachine) {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, abstractMachine.getInputSlot());
                blockMenu.dropItems(location, abstractMachine.getOutputSlot());
            }
        };
    }
    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull int... slot) {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, slot);
            }
        };
    }
    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull AbstractMachine abstractMachine, int... slot) {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, abstractMachine.getInputSlot());
                blockMenu.dropItems(location, abstractMachine.getOutputSlot());
                blockMenu.dropItems(location, slot);
            }
        };
    }

    /**
     * How many slot has item on it.
     * @param inventory
     * @param slots
     * @return
     */
    public static int itemCount(@Nonnull Inventory inventory, int[] slots) {
        int count = 0;
        ItemStack itemStack;
        for (int slot : slots) {
            itemStack = inventory.getItem(slot);
            if (!ItemStackUtil.isItemNull(itemStack)) {
                count++;
            }
        }
        return count;
    }
    public static int itemCount(@Nonnull BlockMenu blockMenu, int[] slots) {
        int count = 0;
        ItemStack itemStack;
        for (int slot : slots) {
            itemStack = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(itemStack)) {
                count++;
            }
        }
        return count;
    }

    /**
     * If all slot in the inventory has item on it.
     * @param inventory
     * @param slots
     * @return
     */
    public static boolean isFull(@Nonnull Inventory inventory, int[] slots) {
        ItemStack itemStack;
        for (int slot : slots) {
            itemStack = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(itemStack) || itemStack.getAmount() < itemStack.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }
    public static boolean isFull(@Nonnull BlockMenu blockMenu, int[] slots) {
        ItemStack itemStack;
        for (int slot : slots) {
            itemStack = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(itemStack) || itemStack.getAmount() < itemStack.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    /**
     * If all slot in the inventory has no item on it.
     * @param inventory
     * @param slots
     * @return
     */
    public static boolean isEmpty(@Nonnull Inventory inventory, int[] slots) {
        ItemStack itemStack;
        for (int slot : slots) {
            itemStack = inventory.getItem(slot);
            if (!ItemStackUtil.isItemNull(itemStack)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isEmpty(@Nonnull BlockMenu blockMenu, int[] slots) {
        ItemStack itemStack;
        for (int slot : slots) {
            itemStack = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(itemStack)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 堆叠指定容器指定范围的物品
     * @param blockMenu 容器
     * @param slots 指定范围
     */
    public static void stockSlots(@Nonnull BlockMenu blockMenu, int[] slots) {
        List<ItemStackWithWrapper> items = new ArrayList<>(slots.length);
        for (int slot : slots) {
            ItemStack item1 = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(item1) || item1.getAmount() >= item1.getMaxStackSize()) {
                continue;
            }
            for (ItemStackWithWrapper item2 : items) {
                if (ItemStackUtil.isItemNull(item2.getItemStack())) {
                    continue;
                }
                ItemStackUtil.stack(item1, item2);
            }
            if (item1.getAmount() > 0 && item1.getAmount() < item1.getMaxStackSize()) {
                items.add(new ItemStackWithWrapper(item1));
            }
        }
    }
    public static void stockSlots(@Nonnull Inventory inventory, int[] slots) {
        List<ItemStackWithWrapper> items = new ArrayList<>(slots.length);
        for (int slot : slots) {
            ItemStack item1 = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(item1)) {
                continue;
            }
            for (ItemStackWithWrapper item2 : items) {
                ItemStackUtil.stack(item1, item2);
            }
            if (item1.getAmount() > 0 && item1.getAmount() < item1.getMaxStackSize()) {
                items.add(new ItemStackWithWrapper(item1));
            }
        }
    }

    public static List<ItemStackWithWrapper> getItemList(@Nonnull Inventory inventory, int[] filterSlots) {
        List<ItemStackWithWrapper> filterList = new ArrayList<>();
        for (int i = 0; i < filterSlots.length; i++) {
            if (!ItemStackUtil.isItemNull(inventory.getItem(filterSlots[i]))) {
                filterList.add(new ItemStackWithWrapper(inventory.getItem(filterSlots[i])));
            }
        }
        return filterList;
    }
    public static List<ItemStackWithWrapper> getItemList(@Nonnull BlockMenu blockMenu, int[] filterSlots) {
        List<ItemStackWithWrapper> filterList = new ArrayList<>();
        for (int i = 0; i < filterSlots.length; i++) {
            if (!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(filterSlots[i]))) {
                filterList.add(new ItemStackWithWrapper(blockMenu.getItemInSlot(filterSlots[i])));
            }
        }
        return filterList;
    }

    public static Map<Integer, ItemStackWithWrapper> calSlotItemWithWrapperMap(@Nonnull Inventory inventory, int[] slots) {
        Map<Integer, ItemStackWithWrapper> itemMap = new HashMap<>(slots.length);
        for (int slot : slots) {
            ItemStack item = inventory.getItem(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                itemMap.put(slot, new ItemStackWithWrapper(item));
            }
        }
        return itemMap;
    }
    public static Map<Integer, ItemStackWithWrapper> calSlotItemWithWrapperMap(@Nonnull BlockMenu blockMenu, int[] slots) {
        Map<Integer, ItemStackWithWrapper> itemMap = new HashMap<>(slots.length);
        for (int slot : slots) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                itemMap.put(slot, new ItemStackWithWrapper(item));
            }
        }
        return itemMap;
    }

    public static List<ItemStackWithWrapperAmount> calItemListWithAmount(@Nonnull BlockMenu blockMenu, int[] slots) {
        List<ItemStackWithWrapperAmount> itemWithWrapperList = new ArrayList<>(slots.length);
        for (int slot : slots) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(item)) {
                continue;
            }
            ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
            boolean find = false;
            for (ItemStackWithWrapperAmount resultItem : itemWithWrapperList) {
                if (ItemStackUtil.isItemSimilar(itemStackWrapper, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if (!find) {
                itemWithWrapperList.add(new ItemStackWithWrapperAmount(item, itemStackWrapper));
            }
        }
        return itemWithWrapperList;
    }
    public static List<ItemStackWithWrapperAmount> calItemListWithAmount(@Nonnull Inventory inventory, int[] slots) {
        List<ItemStackWithWrapperAmount> itemWithWrapperList = new ArrayList<>(slots.length);
        for (int slot : slots) {
            ItemStack item = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(item)) {
                continue;
            }
            ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
            boolean find = false;
            for (ItemStackWithWrapperAmount resultItem : itemWithWrapperList) {
                if (ItemStackUtil.isItemSimilar(itemStackWrapper, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if (!find) {
                itemWithWrapperList.add(new ItemStackWithWrapperAmount(item, itemStackWrapper));
            }
        }
        return itemWithWrapperList;
    }

    public static int calMaxMatch(@Nonnull BlockMenu blockMenu, int[] slots, @Nonnull List<ItemStackWithWrapperAmount> itemWithWrapperList) {
        List<Integer> countList = new ArrayList<>(itemWithWrapperList.size());
        List<Integer> stackList = new ArrayList<>(itemWithWrapperList.size());
        for (int i = 0; i < itemWithWrapperList.size(); i++) {
            countList.add(0);
            stackList.add(0);
        }

        int emptySlot = 0;
        for (int slot : slots) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(item)) {
                emptySlot++;
                continue;
            } else if (item.getAmount() >= item.getMaxStackSize()) {
                continue;
            }
            ItemStackWrapper itemWrapper = ItemStackWrapper.wrap(item);
            for (int i = 0; i < itemWithWrapperList.size(); i++) {
                if (ItemStackUtil.isItemSimilar(itemWrapper, itemWithWrapperList.get(i))) {
                    countList.set(i, countList.get(i) + (item.getMaxStackSize() - item.getAmount()));
                    stackList.set(i, countList.get(i) / itemWithWrapperList.get(i).getAmount());
                    break;
                }
            }
        }

        while (emptySlot > 0) {
            int minStackP = 0;
            int minStack = stackList.get(0);
            for (int i = 1; i < itemWithWrapperList.size(); i++) {
                if (minStack > stackList.get(i)) {
                    minStack = stackList.get(i);
                    minStackP = i;
                }
            }
            countList.set(minStackP, countList.get(minStackP) + itemWithWrapperList.get(minStackP).getItemStack().getMaxStackSize());
            stackList.set(minStackP, countList.get(minStackP) / itemWithWrapperList.get(minStackP).getAmount());
            emptySlot--;
        }

        int min = stackList.get(0);
        for (int stack : stackList) {
            min = Math.min(min, stack);
        }
        return min;
    }
    public static int calMaxMatch(@Nonnull Inventory inventory, int[] slots, @Nonnull List<ItemStackWithWrapperAmount> itemWithWrapperList) {
        List<Integer> countList = new ArrayList<>(itemWithWrapperList.size());
        List<Integer> stackList = new ArrayList<>(itemWithWrapperList.size());
        for (int i = 0; i < itemWithWrapperList.size(); i++) {
            countList.add(0);
            stackList.add(0);
        }

        int emptySlot = 0;
        for (int slot : slots) {
            ItemStack item = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(item)) {
                emptySlot++;
                continue;
            } else if (item.getAmount() >= item.getMaxStackSize()) {
                continue;
            }
            ItemStackWrapper itemWrapper = ItemStackWrapper.wrap(item);
            for (int i = 0; i < itemWithWrapperList.size(); i++) {
                if (ItemStackUtil.isItemSimilar(itemWrapper, itemWithWrapperList.get(i))) {
                    countList.set(i, countList.get(i) + (item.getMaxStackSize() - item.getAmount()));
                    stackList.set(i, countList.get(i) / itemWithWrapperList.get(i).getAmount());
                    break;
                }
            }
        }

        while (emptySlot > 0) {
            int minStackP = 0;
            int minStack = stackList.get(0);
            for (int i = 1; i < itemWithWrapperList.size(); i++) {
                if (minStack > stackList.get(i)) {
                    minStack = stackList.get(i);
                    minStackP = i;
                }
            }
            countList.set(minStackP, countList.get(minStackP) + itemWithWrapperList.get(minStackP).getItemStack().getMaxStackSize());
            stackList.set(minStackP, countList.get(minStackP) / itemWithWrapperList.get(minStackP).getAmount());
            emptySlot--;
        }

        int min = stackList.get(0);
        for (int stack : stackList) {
            min = Math.min(min, stack);
        }
        return min;
    }

    public static int updateQuantityModule(@Nonnull BlockMenu blockMenu, int quantityModuleSlot, int statusSlot) {
        ItemStack item = blockMenu.getItemInSlot(quantityModuleSlot);
        int amount;
        String lore;
        if (ItemStackUtil.isItemSimilar(item, FinalTechItems.QUANTITY_MODULE)) {
            amount = item.getAmount();
            lore = "§7当前效率=" + amount;
        } else if (ItemStackUtil.isItemSimilar(item, FinalTechItems.QUANTITY_MODULE_INFINITY)) {
            amount = Integer.MAX_VALUE / 64 - 1;
            lore = "§7当前效率=" + "INFINITY";
        } else {
            amount = 1;
            lore = "§7当前效率=" + amount;
        }
        ItemStack infoItem = blockMenu.getItemInSlot(statusSlot);
        if (!infoItem.hasItemMeta()) {
            return amount;
        }
        ItemMeta itemMeta = infoItem.getItemMeta();
        List<String> loreList = itemMeta.getLore();
        if (loreList == null) {
            loreList = new ArrayList<>(2);
        }
        if (loreList.size() == 0) {
            loreList.add("§7该机器可以通过添加[数量组件]进行升级");
        }
        if (loreList.size() == 1) {
            loreList.add(lore);
        } else {
            loreList.set(1, lore);
        }
        itemMeta.setLore(loreList);
        infoItem.setItemMeta(itemMeta);
        return amount;
    }
}
