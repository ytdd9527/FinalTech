package io.taraxacum.finaltech.util.cargo;

import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 */
public class CargoUtil {
    public static final ItemStack NULL_ITEM = new ItemStack(Material.AIR);
    public static final int SEARCH_MAP_LIMIT = 3;

    /**
     * 进行物品传输
     * @param inputBlock 输入侧容器方块
     * @param outputBlock 输出侧容器方块
     * @param inputSize 输入容器搜索范围 {@link SlotSearchSize}
     * @param inputOrder 输入容器搜索顺序 {@link SlotSearchOrder}
     * @param outputSize 输出容器搜索范围 {@link SlotSearchSize}
     * @param outputOrder 输出容器搜索顺序 {@link SlotSearchOrder}
     * @param cargoNumber 传输物品数量限制
     * @param itemMode 传输类型 {@link CargoItemMode}
     * @param filterMode 过滤模式 {@link FilterMode}
     * @param filterInv 过滤模式所匹配物品所在的容器
     * @param filterSlots 过滤模式所匹配物品所在的位置
     * @param cargoMode 传输模式 {@link CargoMode}
     * @return 实际传输数量
     */
    public static int doCargo(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String itemMode, @Nonnull String filterMode, @Nonnull Inventory filterInv, int[] filterSlots, @Nonnull String cargoMode) {
        return switch (cargoMode) {
            case CargoMode.VALUE_INPUT_MAIN -> doCargoInputMain(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, itemMode, filterMode, filterInv, filterSlots);
            case CargoMode.VALUE_OUTPUT_MAIN -> doCargoOutputMain(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, itemMode, filterMode, filterInv, filterSlots);
            case CargoMode.VALUE_SYMMETRY -> doCargoSymmetry(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, itemMode, filterMode, filterInv, filterSlots);
            default -> 0;
        };
    }
    public static int doCargoSymmetry(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String itemMode, @Nonnull String filterMode, @Nonnull Inventory filterInv, int[] filterSlots) {
        InvWithSlots inputMap = getInv(inputBlock, inputSize, inputOrder);
        InvWithSlots outputMap = getInv(outputBlock, outputSize, outputOrder);
        if (inputMap == null || outputMap == null) {
            return 0;
        }
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();

        ItemStackWithWrapper typeItem = null;
        int number = 0;
        int length = Math.min(inputSlots.length, outputSlots.length);
        List<ItemStackWithWrapper> filterItemList = FilterMode.getMatchList(filterInv, filterSlots);
        for(int i = 0; i < length; i++) {
            ItemStack inputItem = inputInv.getItem(inputSlots[i]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            ItemStackWithWrapper inputItemWithWrapper = new ItemStackWithWrapper(inputItem);
            if (!FilterMode.ifMatch(inputItemWithWrapper, filterItemList, filterMode)) {
                continue;
            }
            if (!CargoItemMode.VALUE_ALL.equals(itemMode) && typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWithWrapper.getItemStackWrapper(), typeItem.getItemStackWrapper())) {
                continue;
            }
            ItemStack outputItem = outputInv.getItem(outputSlots[i]);
            int count;
            if (ItemStackUtil.isItemNull(outputItem)) {
                count = Math.min(inputItem.getAmount(), cargoNumber);
                outputItem = inputItem.clone();
                outputItem.setAmount(count);
                outputInv.setItem(outputSlots[i], outputItem);
                inputItem.setAmount(inputItem.getAmount() - count);
            } else {
                count = ItemStackUtil.stack(inputItemWithWrapper, outputItem, cargoNumber);
                if (count == 0) {
                    continue;
                }
            }
            if (typeItem == null) {
                typeItem = new ItemStackWithWrapper(inputItem.clone());
            }
            cargoNumber -= count;
            number += count;
            if (CargoItemMode.VALUE_STACK.equals(itemMode)) {
                cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
            }
        }
        return number;
    }
    public static int doCargoInputMain(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String itemMode, @Nonnull String filterMode, @Nonnull Inventory filterInv, int[] filterSlots) {
        InvWithSlots inputMap = getInv(inputBlock, inputSize, inputOrder);
        if (inputMap == null) {
            return 0;
        }
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();
        if (inputSlots.length == 0) {
            return 0;
        }
        InvWithSlots outputMap = null;
        boolean isVanillaBlock = !BlockStorage.hasInventory(outputBlock) && PaperLib.getBlockState(outputBlock, false).getState() instanceof InventoryHolder;
        if (isVanillaBlock) {
            outputMap = getInv(outputBlock, outputSize, outputOrder);
            if (outputMap == null || outputMap.getSlots().length == 0) {
                return 0;
            }
        }
        List<ItemStackWithWrapper> skipItems = new ArrayList<>(inputSlots.length);
        List<ItemStackWithWrapper> filterItemList = FilterMode.getMatchList(filterInv, filterSlots);
        Map<ItemStackWithWrapper, InvWithSlots> searchMap = new HashMap<>(SEARCH_MAP_LIMIT);
        ItemStackWithWrapper typeItem = null;
        int number = 0;
        for(int inputSlot : inputSlots) {
            if (cargoNumber == 0) {
                break;
            }
            ItemStack inputItem = inputInv.getItem(inputSlot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            ItemStackWithWrapper inputItemWithWrapper = new ItemStackWithWrapper(inputItem);
            if (!CargoItemMode.VALUE_ALL.equals(itemMode) && typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWithWrapper.getItemStackWrapper(), typeItem.getItemStackWrapper())) {
                continue;
            }
            if (!FilterMode.ifMatch(inputItemWithWrapper, filterItemList, filterMode)) {
                continue;
            }
            if (FilterMode.ifMatch(inputItemWithWrapper, skipItems, FilterMode.VALUE_WHITE)) {
                continue;
            }
            if (!isVanillaBlock) {
                outputMap = null;
                for (ItemStackWithWrapper item : searchMap.keySet()) {
                    if (ItemStackUtil.isItemSimilar(inputItemWithWrapper.getItemStackWrapper(), item.getItemStackWrapper())) {
                        outputMap = searchMap.get(item);
                        break;
                    }
                }
                if (outputMap == null) {
                    outputMap = getInv(outputBlock, outputSize, outputOrder, inputItem);
                    if (outputMap == null) {
                        continue;
                    }
                }
            }
            Inventory outputInv = outputMap.getInventory();
            int[] outputSlots = outputMap.getSlots();
            boolean work = false;
            for(int outputSlot : outputSlots) {
                if (cargoNumber == 0) {
                    break;
                }
                ItemStack outputItem = outputInv.getItem(outputSlot);
                if (ItemStackUtil.isItemNull(outputItem)) {
                    if (typeItem == null) {
                        typeItem = new ItemStackWithWrapper(inputItem.clone(), inputItemWithWrapper.getItemStackWrapper());
                    }
                    int count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlot, outputItem);
                    inputItem.setAmount(inputItem.getAmount() - count);
                    cargoNumber -= count;
                    number += count;
                    if (CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                        break;
                    }
                    work = true;
                    if (inputItem.getAmount() == 0) {
                        break;
                    }
                } else if (ItemStackUtil.isItemSimilar(inputItemWithWrapper.getItemStackWrapper(), outputItem) && outputItem.getMaxStackSize() > outputItem.getAmount()) {
                    if (typeItem == null) {
                        typeItem = new ItemStackWithWrapper(inputItem.clone(), inputItemWithWrapper.getItemStackWrapper());
                    }
                    int count = ItemStackUtil.stack(inputItemWithWrapper, outputItem, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    if (CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                        break;
                    }
                    work = true;
                    if (inputItem.getAmount() == 0) {
                        break;
                    }
                }
            }
            if (work) {
                if (searchMap.size() < SEARCH_MAP_LIMIT) {
                    searchMap.put(new ItemStackWithWrapper(inputItem.clone()), outputMap);
                }
            } else {
                skipItems.add(new ItemStackWithWrapper(inputItem));
            }
        }
        return number;
    }
    public static int doCargoOutputMain(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, @Nonnull int cargoNumber, @Nonnull String itemMode, @Nonnull String filterMode, @Nonnull Inventory filterInv, int[] filterSlots) {
        InvWithSlots outputMap = getInv(outputBlock, outputSize, outputOrder);
        if (outputMap == null) {
            return 0;
        }
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();
        if (outputSlots.length == 0) {
            return 0;
        }
        InvWithSlots inputMap = null;
        boolean isVanillaBlock = !BlockStorage.hasInventory(inputBlock) && PaperLib.getBlockState(inputBlock, false).getState() instanceof InventoryHolder;
        if (isVanillaBlock) {
            inputMap = CargoUtil.getInv(inputBlock, inputSize, inputOrder);
            if (inputMap == null || inputMap.getSlots().length == 0) {
                return 0;
            }
        }
        List<ItemStackWithWrapper> skipItems = new ArrayList<>(outputMap.getSlots().length);
        List<ItemStackWithWrapper> filterList = FilterMode.getMatchList(filterInv, filterSlots);
        Map<ItemStackWithWrapper, InvWithSlots> searchMap = new LinkedHashMap<>(SEARCH_MAP_LIMIT);
        ItemStackWithWrapper typeItem = null;
        int number = 0;
        for(int outputSlot : outputSlots) {
            ItemStack outputItem = outputInv.getItem(outputSlot);
            ItemStackWithWrapper outputItemWithWrapper;
            if (!ItemStackUtil.isItemNull(outputItem)) {
                if (outputItem.getMaxStackSize() == outputItem.getAmount()) {
                    continue;
                }
                outputItemWithWrapper = new ItemStackWithWrapper(outputItem);
                if (!FilterMode.ifMatch(outputItemWithWrapper, filterList, filterMode)) {
                    continue;
                }
                if (FilterMode.ifMatch(outputItemWithWrapper, skipItems, FilterMode.VALUE_WHITE)) {
                    continue;
                }
            } else {
                outputItemWithWrapper = new ItemStackWithWrapper(NULL_ITEM.clone());
            }
            if (!isVanillaBlock) {
                inputMap = null;
                for (ItemStackWithWrapper item : searchMap.keySet()) {
                    if (ItemStackUtil.isItemSimilar(outputItemWithWrapper.getItemStackWrapper(), item.getItemStackWrapper())) {
                        inputMap = searchMap.get(item);
                        break;
                    }
                }
                if (inputMap == null) {
                    inputMap = getInv(inputBlock, inputSize, inputOrder, outputItem);
                    if (inputMap == null) {
                        continue;
                    }
                }
            }
            Inventory inputInv = inputMap.getInventory();
            int[] inputSlots = inputMap.getSlots();
            boolean work = false;
            for(int inputSlot : inputSlots) {
                if (cargoNumber == 0) {
                    break;
                }
                ItemStack inputItem = inputInv.getItem(inputSlot);
                if (ItemStackUtil.isItemNull(inputItem)) {
                    continue;
                }
                ItemStackWithWrapper inputItemWithWrapper = new ItemStackWithWrapper(inputItem);
                if (!FilterMode.ifMatch(inputItemWithWrapper, filterList, filterMode)) {
                    continue;
                }
                if (!CargoItemMode.VALUE_ALL.equals(itemMode) && typeItem != null && !work && !ItemStackUtil.isItemSimilar(inputItemWithWrapper.getItemStackWrapper(), typeItem.getItemStackWrapper())) {
                    continue;
                }
                if (ItemStackUtil.isItemNull(outputItem)) {
                    if (typeItem == null) {
                        typeItem = new ItemStackWithWrapper(inputItem.clone(), inputItemWithWrapper.getItemStackWrapper());
                    }
                    int count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlot, outputItem);
                    outputItemWithWrapper = new ItemStackWithWrapper(outputItem, inputItemWithWrapper.getItemStackWrapper());
                    inputItem.setAmount(inputItem.getAmount() - count);
                    cargoNumber -= count;
                    number += count;
                    if (CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                    }
                    work = true;
                } else if (outputItem.getMaxStackSize() > outputItem.getAmount() && ItemStackUtil.isItemSimilar(inputItemWithWrapper.getItemStackWrapper(), outputItemWithWrapper.getItemStackWrapper())) {
                    if (typeItem == null) {
                        typeItem = new ItemStackWithWrapper(inputItem.clone(), inputItemWithWrapper.getItemStackWrapper());
                    }
                    int count = ItemStackUtil.stack(inputItemWithWrapper, outputItemWithWrapper, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    if (CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                    }
                    work = true;
                }
            }
            if (work) {
                if (searchMap.size() < SEARCH_MAP_LIMIT) {
                    searchMap.put(new ItemStackWithWrapper(outputItemWithWrapper.getItemStack(), outputItemWithWrapper.getItemStackWrapper()), inputMap);
                }
            } else {
                skipItems.add(outputItemWithWrapper);
            }
        }
        return number;
    }

    /**
     * 按照指定格式
     * 获取指定方块位置的容器
     * @param block 容器所在位置的方块
     * @param size 搜索范围 {@link SlotSearchSize}
     * @param order 搜索顺序 {@link SlotSearchOrder}
     * @return 容器和物品槽位 {@link InvWithSlots}
     */
    public static InvWithSlots getInv(@Nonnull Block block, String size, String order) {
        return getInv(block, size, order, new ItemStack(Material.AIR));
    }
    public static InvWithSlots getInv(@Nonnull Block block, String size, String order, ItemStack item) {
        Inventory inventory = null;
        int[] slots = null;

        if (BlockStorage.hasInventory(block)) {
            BlockMenu blockMenu = BlockStorage.getInventory(block);
            inventory = blockMenu.toInventory();
            int[] insert;
            int[] withdraw;
            int i;
            switch (size) {
                case SlotSearchSize.VALUE_INPUTS_ONLY:
                    if (ItemStackUtil.isItemNull(item)) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (slots.length == 0) {
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, NULL_ITEM);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                    }
                    break;
                case SlotSearchSize.VALUE_OUTPUTS_ONLY:
                    if (ItemStackUtil.isItemNull(item)) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (slots.length == 0) {
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, NULL_ITEM);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    break;
                case SlotSearchSize.VALUE_INPUTS_AND_OUTPUTS:
                    if (ItemStackUtil.isItemNull(item)) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (insert.length == 0) {
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, NULL_ITEM);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, NULL_ITEM);
                        }
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    i = 0;
                    for (; i < insert.length; i++) {
                        slots[i] = insert[i];
                    }
                    System.arraycopy(withdraw, 0, slots, i, withdraw.length);
                    break;
                case SlotSearchSize.VALUE_OUTPUTS_AND_INPUTS:
                    if (ItemStackUtil.isItemNull(item)) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (insert.length == 0) {
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, NULL_ITEM);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, NULL_ITEM);
                        }
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    i = 0;
                    for (; i < withdraw.length; i++) {
                        slots[i] = insert[i];
                    }
                    System.arraycopy(insert, 0, slots, i, insert.length);
                    break;
                default:
                    slots = new int[0];
            }
        } else {
            BlockState blockState = PaperLib.getBlockState(block, false).getState();
            if (blockState instanceof InventoryHolder) {
                inventory = ((InventoryHolder) blockState).getInventory();
                slots = new int[inventory.getSize()];
                for (int i = 0; i < slots.length; i++) {
                    slots[i] = i;
                }
            }
        }

        if (inventory != null && slots != null) {
            switch (order) {
                case SlotSearchOrder.VALUE_DESCEND:
                    slots = Arrays.copyOf(slots, slots.length);
                    JavaUtil.reserve(slots);
                    break;
                case SlotSearchOrder.VALUE_FIRST_ONLY:
                    if (slots.length > 0) {
                        slots = new int[] {slots[0]};
                    }
                    break;
                case SlotSearchOrder.VALUE_LAST_ONLY:
                    if (slots.length > 0) {
                        slots = new int[] {slots[slots.length - 1]};
                    }
                default:
                    break;
            }
            return new InvWithSlots(inventory, slots);
        }
        return null;
    }

    public static boolean hasInventory(@Nonnull Block block) {
        if (BlockStorage.hasInventory(block)) {
            return true;
        }
        return PaperLib.getBlockState(block, false).getState() instanceof InventoryHolder;
    }
}
