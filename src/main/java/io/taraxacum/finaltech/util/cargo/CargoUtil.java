package io.taraxacum.finaltech.util.cargo;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.util.InvWithSlots;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.JavaUtil;
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

    public static final int doCargoInputMain(@Nonnull Block inputBlock, @Nonnull Block outputBlock, String inputSize, String inputOrder, String outputSize, String outputOrder, int cargoNumber, String itemMode, String filterMode, Inventory filterInv, int[] filterSlots) {
        InvWithSlots inputMap = getInv(inputBlock, inputSize, inputOrder);
        if(inputMap == null) {
            return 0;
        }
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();
        if(inputInv == null || inputSlots == null) {
            return 0;
        }
        InvWithSlots outputMap = null;
        boolean vanillaBlock = !BlockStorage.hasInventory(outputBlock) && PaperLib.getBlockState(outputBlock, false).getState() instanceof  InventoryHolder;
        if(vanillaBlock) {
            outputMap = getInv(outputBlock, outputSize, outputOrder);
        }
        List<ItemStack> unWorkItem = new ArrayList<>();
        Map<ItemStack, InvWithSlots> searchMap = new LinkedHashMap<>();
        ItemStack typeItem = null;
        int number = 0;
        for(int inputSlot : inputSlots) {
            if(cargoNumber == 0) {
                break;
            }
            ItemStack inputItem = inputInv.getItem(inputSlot);
            if(ItemStackUtil.isItemNull(inputItem) || !FilterMode.ifMatch(inputItem, filterInv, filterMode, filterSlots)) {
                continue;
            }
            if(!CargoItemMode.VALUE_ALL.equals(itemMode) && !ItemStackUtil.isItemNull(typeItem) && !SlimefunUtils.isItemSimilar(inputItem, typeItem, true, false)) {
                continue;
            }
            boolean ifWork = true;
            for(ItemStack item : unWorkItem) {
                if(SlimefunUtils.isItemSimilar(inputItem, item, true, false)) {
                    ifWork = false;
                    break;
                }
            }
            if(!ifWork) {
                break;
            }
            if(!vanillaBlock) {
                outputMap = null;
                Iterator<ItemStack> iterator = searchMap.keySet().iterator();
                while (iterator.hasNext()) {
                    ItemStack item = iterator.next();
                    if(SlimefunUtils.isItemSimilar(inputItem, item, true, false)) {
                        outputMap = searchMap.get(item);
                        break;
                    }
                }
                if(outputMap == null) {
                    outputMap = getInv(outputBlock, outputSize, outputOrder, inputItem);
                }
            }
            if(outputMap == null) {
                continue;
            }
            Inventory outputInv = outputMap.getInventory();
            int[] outputSlots = outputMap.getSlots();
            if(outputInv == null || outputSlots == null) {
                continue;
            }
            ifWork = false;
            for(int outputSlot : outputSlots) {
                if(cargoNumber == 0) {
                    break;
                }
                ItemStack outputItem = outputInv.getItem(outputSlot);
                if(ItemStackUtil.isItemNull(outputItem)) {
                    if(ItemStackUtil.isItemNull(typeItem)) {
                        typeItem = new ItemStack(inputItem);
                    }
                    int count = Math.min(cargoNumber, inputItem.getAmount());
                    outputInv.setItem(outputSlot, new CustomItemStack(inputItem, count));
                    inputItem.setAmount(inputItem.getAmount() - count);
                    cargoNumber -= count;
                    number += count;
                    if(CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                        break;
                    }
                    ifWork = true;
                } else if(SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false) && outputItem.getMaxStackSize() > outputItem.getAmount()) {
                    if(ItemStackUtil.isItemNull(typeItem)) {
                        typeItem = new ItemStack(inputItem);
                    }
                    int count = ItemStackUtil.stack(inputItem, outputItem, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    if(CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                        break;
                    }
                    ifWork = true;
                }
            }
            if(ifWork) {
                searchMap.put(new CustomItemStack(inputItem, 1), outputMap);
            } else {
                unWorkItem.add(inputItem);
            }
        }
        return number;
    }

    public static final int doCargoOutputMain(@Nonnull Block inputBlock, @Nonnull Block outputBlock, String inputSize, String inputOrder, String outputSize, String outputOrder, int cargoNumber, String itemMode, String filterMode, Inventory filterInv, int[] filterSlots) {
        InvWithSlots outputMap = getInv(outputBlock, outputSize, outputOrder);
        if(outputMap == null) {
            return 0;
        }
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();
        if(outputInv == null || outputSlots == null) {
            return 0;
        }
        InvWithSlots inputMap = null;
        boolean vanillaBlock = !BlockStorage.hasInventory(inputBlock) && PaperLib.getBlockState(inputBlock, false).getState() instanceof InventoryHolder;
        if(vanillaBlock) {
            inputMap = CargoUtil.getInv(inputBlock, inputSize, inputOrder);
        }
        List<ItemStack> unWorkItem = new ArrayList<>();
        Map<ItemStack, InvWithSlots> searchMap = new LinkedHashMap<>();
        ItemStack typeItem = null;
        int number = 0;
        for(int outputSlot : outputSlots) {
            ItemStack outputItem = outputInv.getItem(outputSlot);
            if(!ItemStackUtil.isItemNull(outputItem)) {
                if(outputItem.getMaxStackSize() == outputItem.getAmount() || !FilterMode.ifMatch(outputItem, filterInv, filterMode, filterSlots)) {
                    continue;
                }
            }
            boolean ifWork = true;
            for(ItemStack item : unWorkItem) {
                if(SlimefunUtils.isItemSimilar(outputItem, item, true, false)) {
                    ifWork = false;
                    break;
                }
            }
            if(!ifWork) {
                break;
            }
            if(!vanillaBlock) {
                inputMap = null;
                Iterator<ItemStack> iterator = searchMap.keySet().iterator();
                while (iterator.hasNext()) {
                    ItemStack item = iterator.next();
                    if(SlimefunUtils.isItemSimilar(outputItem, item, true, false)) {
                        inputMap = searchMap.get(item);
                        break;
                    }
                }
                if(inputMap == null) {
                    inputMap = getInv(inputBlock, inputSize, inputOrder, outputItem);
                }
            }
            if(inputMap == null) {
                continue;
            }
            Inventory inputInv = inputMap.getInventory();
            int[] inputSlots = inputMap.getSlots();
            if(inputInv == null || inputSlots == null) {
                continue;
            }
            ifWork = false;
            for(int inputSlot : inputSlots) {
                if(cargoNumber == 0) {
                    break;
                }
                ItemStack inputItem = inputInv.getItem(inputSlot);
                if(ItemStackUtil.isItemNull(inputItem) || !FilterMode.ifMatch(inputItem, filterInv, filterMode, filterSlots)) {
                    continue;
                }
                if(!CargoItemMode.VALUE_ALL.equals(itemMode) && !ItemStackUtil.isItemNull(typeItem) && !SlimefunUtils.isItemSimilar(inputItem, typeItem, true, false)) {
                    continue;
                }
                if(ItemStackUtil.isItemNull(outputItem)) {
                    if(ItemStackUtil.isItemNull(typeItem)) {
                        typeItem = new ItemStack(inputItem);
                    }
                    int count = Math.min(cargoNumber, inputItem.getAmount());
                    outputItem = new CustomItemStack(inputItem, count);
                    outputInv.setItem(outputSlot, outputItem);
                    inputItem.setAmount(inputItem.getAmount() - count);
                    cargoNumber -= count;
                    number += count;
                    if(CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                    }
                    ifWork = true;
                } else if(SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false) && outputItem.getMaxStackSize() > outputItem.getAmount()) {
                    if(ItemStackUtil.isItemNull(typeItem)) {
                        typeItem = new ItemStack(inputItem);
                    }
                    int count = ItemStackUtil.stack(inputItem, outputItem, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    if(CargoItemMode.VALUE_STACK.equals(itemMode)) {
                        cargoNumber = outputItem.getMaxStackSize() - outputItem.getAmount();
                    }
                    ifWork = true;
                }
            }
            if(ifWork) {
                searchMap.put(new CustomItemStack(outputItem, 1), inputMap);
            } else {
                unWorkItem.add(outputItem);
            }
        }
        return number;
    }

    public static final InvWithSlots getInv(@Nonnull Block block, String size, String order) {
        return getInv(block, size, order, new ItemStack(Material.AIR));
    }

    public static final InvWithSlots getInv(@Nonnull Block block, String size, String order, ItemStack item) {
        Inventory inventory = null;
        int[] slots = null;

        if (BlockStorage.hasInventory(block)) {
            BlockMenu blockMenu = BlockStorage.getInventory(block);
            inventory = blockMenu.toInventory();
            switch (size) {
                case SlotSearchSize.VALUE_INPUTS_ONLY:
                    if(item == null) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                    }
//                    if(slots == null || slots.length == 0) {
//                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
//                    }
                    break;
                case SlotSearchSize.VALUE_OUTPUTS_ONLY:
                    if(item == null) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
//                    if(slots == null || slots.length == 0) {
//                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
//                    }
                    break;
                case SlotSearchSize.VALUE_INPUTS_AND_OUTPUTS:
                    int[] insert;
//                    if(insert == null || insert.length == 0) {
//                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
//                    }
                    int[] withdraw;
//                    if(withdraw == null || withdraw.length == 0) {
//                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
//                    }
                    if(item == null) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    int i = 0;
                    for (; i < insert.length; i++) {
                        slots[i] = insert[i];
                    }
                    for (int j = 0; j < withdraw.length; j++) {
                        slots[i + j] = withdraw[j];
                    }
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
            slots = Arrays.copyOf(slots, slots.length);
            switch (order) {
                case SlotSearchOrder.VALUE_ASCENT:
                    break;
                case SlotSearchOrder.VALUE_DESCEND:
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
            }
            return new InvWithSlots(inventory, slots);
        }
        return null;
    }
}
