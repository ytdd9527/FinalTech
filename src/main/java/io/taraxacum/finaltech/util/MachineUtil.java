package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
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
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {

            }
        };
    }
    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull AbstractMachine abstractMachine) {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, abstractMachine.getInputSlots());
                blockMenu.dropItems(location, abstractMachine.getOutputSlots());
            }
        };
    }
    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull int... slot) {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, slot);
            }
        };
    }
    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull AbstractMachine abstractMachine, int... slot) {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, abstractMachine.getInputSlots());
                blockMenu.dropItems(location, abstractMachine.getOutputSlots());
                blockMenu.dropItems(location, slot);
            }
        };
    }

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

    /**
     * 堆叠指定容器指定范围的物品
     * @param menu 容器
     * @param slots 指定范围
     */
    public static void stockSlots(@Nonnull BlockMenu menu, int[] slots) {
        List<ItemStackWithWrapper> items = new ArrayList<>(slots.length);
        for (int slot : slots) {
            ItemStack item1 = menu.getItemInSlot(slot);
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

    public static List<ItemStackWithWrapper> calItemListWithAmount(@Nonnull BlockMenu blockMenu, int[] slots) {
        List<ItemStackWithWrapper> itemWithWrapperList = new ArrayList<>(slots.length);
        for(int slot : slots) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(item)) {
                continue;
            }
            ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
            boolean find = false;
            for (ItemStackWithWrapper resultItem : itemWithWrapperList) {
                if (ItemStackUtil.isItemSimilar(itemStackWrapper, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if (!find) {
                itemWithWrapperList.add(new ItemStackWithWrapper(item, itemStackWrapper));
            }
        }
        return itemWithWrapperList;
    }
    public static List<ItemStackWithWrapper> calItemListWithAmount(@Nonnull Inventory inventory, int[] slots) {
        List<ItemStackWithWrapper> itemWithWrapperList = new ArrayList<>(slots.length);
        for(int slot : slots) {
            ItemStack item = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(item)) {
                continue;
            }
            ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
            boolean find = false;
            for (ItemStackWithWrapper resultItem : itemWithWrapperList) {
                if (ItemStackUtil.isItemSimilar(itemStackWrapper, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if (!find) {
                itemWithWrapperList.add(new ItemStackWithWrapper(item, itemStackWrapper));
            }
        }
        return itemWithWrapperList;
    }

    public static int calMaxMatch(@Nonnull BlockMenu blockMenu, int[] slots, @Nonnull List<ItemStackWithWrapper> itemWithWrapperList) {
        List<Integer> countList = new ArrayList<>(itemWithWrapperList.size());
        List<Integer> stackList = new ArrayList<>(itemWithWrapperList.size());
        for(int i = 0; i < itemWithWrapperList.size(); i++) {
            countList.add(0);
            stackList.add(0);
        }

        int emptySlot = 0;
        for(int slot : slots) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(item)) {
                emptySlot++;
                continue;
            } else if(item.getAmount() >= item.getMaxStackSize()) {
                continue;
            }
            ItemStackWrapper itemWrapper = ItemStackWrapper.wrap(item);
            for(int i = 0; i < itemWithWrapperList.size(); i++) {
                if(ItemStackUtil.isItemSimilar(itemWrapper, itemWithWrapperList.get(i))) {
                    countList.set(i, countList.get(i) + (item.getMaxStackSize() - item.getAmount()));
                    stackList.set(i, countList.get(i) / itemWithWrapperList.get(i).getAmount());
                    break;
                }
            }
        }

        while (emptySlot > 0) {
            int minStackP = 0;
            int minStack = stackList.get(0);
            for(int i = 1; i < itemWithWrapperList.size(); i++) {
                if(minStack > stackList.get(i)) {
                    minStack = stackList.get(i);
                    minStackP = i;
                }
            }
            countList.set(minStackP, countList.get(minStackP) + itemWithWrapperList.get(minStackP).getItemStack().getMaxStackSize());
            stackList.set(minStackP, countList.get(minStackP) / itemWithWrapperList.get(minStackP).getAmount());
            emptySlot--;
        }

        int min = stackList.get(0);
        for(int stack : stackList) {
            min = Math.min(min, stack);
        }
        return min;
    }
    public static int calMaxMatch(@Nonnull Inventory inventory, int[] slots, @Nonnull List<ItemStackWithWrapper> itemWithWrapperList) {
        List<Integer> countList = new ArrayList<>(itemWithWrapperList.size());
        List<Integer> stackList = new ArrayList<>(itemWithWrapperList.size());
        for(int i = 0; i < itemWithWrapperList.size(); i++) {
            countList.add(0);
            stackList.add(0);
        }

        int emptySlot = 0;
        for(int slot : slots) {
            ItemStack item = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(item)) {
                emptySlot++;
                continue;
            } else if(item.getAmount() >= item.getMaxStackSize()) {
                continue;
            }
            ItemStackWrapper itemWrapper = ItemStackWrapper.wrap(item);
            for(int i = 0; i < itemWithWrapperList.size(); i++) {
                if(ItemStackUtil.isItemSimilar(itemWrapper, itemWithWrapperList.get(i))) {
                    countList.set(i, countList.get(i) + (item.getMaxStackSize() - item.getAmount()));
                    stackList.set(i, countList.get(i) / itemWithWrapperList.get(i).getAmount());
                    break;
                }
            }
        }

        while (emptySlot > 0) {
            int minStackP = 0;
            int minStack = stackList.get(0);
            for(int i = 1; i < itemWithWrapperList.size(); i++) {
                if(minStack > stackList.get(i)) {
                    minStack = stackList.get(i);
                    minStackP = i;
                }
            }
            countList.set(minStackP, countList.get(minStackP) + itemWithWrapperList.get(minStackP).getItemStack().getMaxStackSize());
            stackList.set(minStackP, countList.get(minStackP) / itemWithWrapperList.get(minStackP).getAmount());
            emptySlot--;
        }

        int min = stackList.get(0);
        for(int stack : stackList) {
            min = Math.min(min, stack);
        }
        return min;
    }

    @Deprecated
    public static int maxMatch(@Nonnull Inventory inventory, int[] slots, @Nonnull ItemStack[] items, int count) {
        List<ItemStackWithWrapper> matchList = ItemStackUtil.calItemListWithAmount(items);
        List<ItemStackWithWrapper> itemList = new ArrayList<>(slots.length);
        int emptySlot = 0;
        for (int slot : slots) {
            ItemStack item = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(item)) {
                emptySlot++ ;
            } else if (item.getAmount() < item.getMaxStackSize()) {
                itemList.add(new ItemStackWithWrapper(item));
            }
        }
        int empty;
        int amount;
        while (count > 0) {
            empty = emptySlot;
            for (ItemStackWithWrapper matchItem : matchList) {
                amount = matchItem.getAmount() * count;
                for (ItemStackWithWrapper item : itemList) {
                    if (item.getAmount() < item.getItemStack().getMaxStackSize() && ItemStackUtil.isItemSimilar(item.getItemStackWrapper(), matchItem.getItemStackWrapper())) {
                        amount -= item.getAmount();
                    }
                }
                if (amount > 0) {
                    empty -= (amount - 1) / matchItem.getItemStack().getMaxStackSize() + 1;
                    if (empty < 0) {
                        break;
                    }
                }
            }
            if (empty >= 0) {
                return count;
            }
            count--;
        }

        return 0;
    }

    public static int updateQuantityModule(@Nonnull BlockMenu blockMenu, int quantityModuleSlot, int infoSlot) {
        ItemStack item = blockMenu.getItemInSlot(quantityModuleSlot);
        int amount = 1;
        if (ItemStackUtil.isItemSimilar(item, FinalTechItems.QUANTITY_MODULE)) {
            amount = item.getAmount();
        }
        ItemStack infoItem = blockMenu.getItemInSlot(infoSlot);
        if (!infoItem.hasItemMeta()) {
            return amount;
        }
        ItemMeta itemMeta = infoItem.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new LinkedList<>();
        }
        if (lore.size() == 0) {
            lore.add("§7该机器可以通过添加[数量组件]进行升级");
        }
        if (lore.size() == 1) {
            lore.add("§7当前效率=" + amount);
        } else {
            lore.set(1, "§7当前效率=" + amount);
        }
        itemMeta.setLore(lore);
        infoItem.setItemMeta(itemMeta);
        return amount;
    }
}
