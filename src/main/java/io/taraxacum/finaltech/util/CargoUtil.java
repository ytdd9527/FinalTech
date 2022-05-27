package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.InvWithSlots;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.core.storage.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class CargoUtil {
    public static final int SEARCH_MAP_LIMIT = 3;

    public static int doCargo(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots, @Nonnull String cargoMode) {
        if(inputBlock.getLocation().equals(outputBlock.getLocation())) {
            return 0;
        }
        return switch (cargoMode) {
            case CargoMode.VALUE_INPUT_MAIN -> CargoUtil.doCargoInputMain(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_OUTPUT_MAIN -> CargoUtil.doCargoOutputMain(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_STRONG_SYMMETRY -> CargoUtil.doCargoStrongSymmetry(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_WEAK_SYMMETRY -> CargoUtil.doCargoWeakSymmetry(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            default -> 0;
        };
    }
    public static int doCargoStrongSymmetry(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        InvWithSlots inputMap = null;
        InvWithSlots outputMap = null;
        if((BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock)) || FinalTech.getInstance().getServer().isPrimaryThread()) {
            inputMap = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
            outputMap = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
        } else {
            try {
                InvWithSlots[] invWithSlots = FinalTech.getInstance().getServer().getScheduler().callSyncMethod(FinalTech.getInstance(), () -> {
                    InvWithSlots[] invWithSlots1 = new InvWithSlots[2];
                    invWithSlots1[0] = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
                    invWithSlots1[1] = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
                    return invWithSlots1;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (inputMap == null || outputMap == null) {
            return 0;
        }
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();

        ItemStackWithWrapper typeItem = null;
        int number = 0;
        List<ItemStackWithWrapper> filterItemList = MachineUtil.getItemList(filterInv, filterSlots);
        for (int i = 0, length = Math.min(inputSlots.length, outputSlots.length); i < length; i++) {
            ItemStack inputItem = inputInv.getItem(inputSlots[i]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            ItemStackWithWrapper inputItemWithWrapper = new ItemStackWithWrapper(inputItem);
            if (!CargoUtil.isMatch(inputItemWithWrapper, filterItemList, cargoFilter)) {
                continue;
            }
            if (typeItem != null && CargoLimit.typeLimit(cargoLimit) && !ItemStackUtil.isItemSimilar(inputItemWithWrapper, typeItem)) {
                continue;
            }
            ItemStack outputItem = outputInv.getItem(outputSlots[i]);
            int count;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if(!CargoLimit.VALUE_NONNULL.equals(cargoLimit)) {
                    count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlots[i], outputItem);
                    outputItem = outputInv.getItem(outputSlots[i]);
                    inputItem.setAmount(inputItem.getAmount() - count);
                    if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                        typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                    }
                } else {
                    continue;
                }
            } else {
                count = ItemStackUtil.stack(inputItemWithWrapper, outputItem, cargoNumber);
                if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                    typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                }
                if (count == 0) {
                    continue;
                }
            }
            cargoNumber -= count;
            number += count;
            if (cargoNumber == 0) {
                break;
            }
            if (CargoLimit.VALUE_STACK.equals(cargoLimit)) {
                cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
            }
        }
        return number;
    }
    public static int doCargoWeakSymmetry(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        InvWithSlots inputMap = null;
        InvWithSlots outputMap = null;
        if((BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock)) || FinalTech.getInstance().getServer().isPrimaryThread()) {
            inputMap = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
            outputMap = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
        } else {
            try {
                InvWithSlots[] invWithSlots = FinalTech.getInstance().getServer().getScheduler().callSyncMethod(FinalTech.getInstance(), () -> {
                    InvWithSlots[] invWithSlots1 = new InvWithSlots[2];
                    invWithSlots1[0] = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
                    invWithSlots1[1] = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
                    return invWithSlots1;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (inputMap == null || outputMap == null) {
            return 0;
        }
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();

        ItemStackWithWrapper typeItem = null;
        int number = 0;
        List<ItemStackWithWrapper> filterItemList = MachineUtil.getItemList(filterInv, filterSlots);
        for (int i = 0, length = Math.max(inputSlots.length, outputSlots.length); i < length; i++) {
            ItemStack inputItem = inputInv.getItem(inputSlots[i % inputSlots.length]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            ItemStackWithWrapper inputItemWithWrapper = new ItemStackWithWrapper(inputItem);
            if (!CargoUtil.isMatch(inputItemWithWrapper, filterItemList, cargoFilter)) {
                continue;
            }
            if (typeItem != null && CargoLimit.typeLimit(cargoLimit) && !ItemStackUtil.isItemSimilar(inputItemWithWrapper, typeItem)) {
                continue;
            }
            ItemStack outputItem = outputInv.getItem(outputSlots[i % outputSlots.length]);
            int count;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if(!CargoLimit.VALUE_NONNULL.equals(cargoLimit)) {
                    count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlots[i % outputSlots.length], outputItem);
                    outputItem = outputInv.getItem(outputSlots[i % outputSlots.length]);
                    inputItem.setAmount(inputItem.getAmount() - count);
                    if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                        typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                    }
                } else {
                    continue;
                }
            } else {
                count = ItemStackUtil.stack(inputItemWithWrapper, outputItem, cargoNumber);
                if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                    typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                }
                if (count == 0) {
                    continue;
                }
            }
            cargoNumber -= count;
            number += count;
            if (cargoNumber == 0) {
                break;
            }
            if (CargoLimit.VALUE_STACK.equals(cargoLimit)) {
                cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
            }
        }
        return number;
    }
    public static int doCargoInputMain(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        InvWithSlots inputMap;
        InvWithSlots outputMap = null;
        boolean vanillaOutputBlock;

        if((BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock))) {
            inputMap = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
            vanillaOutputBlock = false;
        } else if(FinalTech.getInstance().getServer().isPrimaryThread()) {
            inputMap = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
            vanillaOutputBlock = !BlockStorage.hasInventory(outputBlock) && PaperLib.getBlockState(outputBlock, false).getState() instanceof InventoryHolder;
            if(vanillaOutputBlock) {
                outputMap = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
            }
        } else {
            try {
                InvWithSlots[] invWithSlots = FinalTech.getInstance().getServer().getScheduler().callSyncMethod(FinalTech.getInstance(), () -> {
                    InvWithSlots[] result = new InvWithSlots[2];
                    result[0] = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
                    if(BlockStorage.hasInventory(outputBlock)) {
                        result[1] = null;
                    } else {
                        result[1] = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
                    }
                    return result;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
                vanillaOutputBlock = outputMap != null;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return 0;
            }
        }

        if (inputMap == null) {
            return 0;
        }
        if(vanillaOutputBlock && outputMap == null) {
            return 0;
        }

        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();

        List<ItemStackWithWrapper> skipItemList = new ArrayList<>(inputSlots.length);
        List<ItemStackWithWrapper> filterItemList = MachineUtil.getItemList(filterInv, filterSlots);
        List<ItemStackWithWrapper> searchItemList = new ArrayList<>(SEARCH_MAP_LIMIT);
        List<InvWithSlots> searchInvList = new ArrayList<>(SEARCH_MAP_LIMIT);
        ItemStackWithWrapper typeItem = null;
        int number = 0;
        boolean newOutputMap = false;
        for (int inputSlot : inputSlots) {
            ItemStack inputItem = inputInv.getItem(inputSlot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            ItemStackWithWrapper inputItemWithWrapper = new ItemStackWithWrapper(inputItem);
            if (typeItem != null && CargoLimit.typeLimit(cargoLimit) && !ItemStackUtil.isItemSimilar(inputItemWithWrapper, typeItem)) {
                continue;
            }
            if (!CargoUtil.isMatch(inputItemWithWrapper, filterItemList, cargoFilter)) {
                continue;
            }
            if (CargoUtil.isMatch(inputItemWithWrapper, skipItemList, CargoFilter.VALUE_WHITE)) {
                continue;
            }
            if (!vanillaOutputBlock) {
                outputMap = null;
                newOutputMap = false;
                for (int i = 0; i < searchItemList.size(); i++) {
                    if (ItemStackUtil.isItemSimilar(inputItemWithWrapper, searchItemList.get(i))) {
                        outputMap = searchInvList.get(i);
                        break;
                    }
                }
                if (outputMap == null) {
                    newOutputMap = true;
                    outputMap = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder, inputItem);
                    if (outputMap == null) {
                        continue;
                    }
                }
            }
            Inventory outputInv = outputMap.getInventory();
            int[] outputSlots = outputMap.getSlots();
            boolean work = false;
            for (int outputSlot : outputSlots) {
                ItemStack outputItem = outputInv.getItem(outputSlot);
                if (ItemStackUtil.isItemNull(outputItem)) {
                    if(!CargoLimit.VALUE_NONNULL.equals(cargoLimit)) {
                        if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                            typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                        }
                        int count = Math.min(inputItem.getAmount(), cargoNumber);
                        outputItem = ItemStackUtil.cloneItem(inputItem);
                        outputItem.setAmount(count);
                        outputInv.setItem(outputSlot, outputItem);
                        inputItem.setAmount(inputItem.getAmount() - count);
                        cargoNumber -= count;
                        number += count;
                        work = true;
                        if (CargoLimit.VALUE_STACK.equals(cargoLimit)) {
                            cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                            break;
                        }
                        if (inputItem.getAmount() == 0 || cargoNumber == 0) {
                            break;
                        }
                    }
                } else if (outputItem.getAmount() < outputItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(inputItemWithWrapper, outputItem)) {
                    if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                        typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                    }
                    int count = ItemStackUtil.stack(inputItemWithWrapper, outputItem, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (CargoLimit.VALUE_STACK.equals(cargoLimit)) {
                        cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                        break;
                    }
                    if (inputItem.getAmount() == 0 || cargoNumber == 0) {
                        break;
                    }
                }
            }
            if (cargoNumber == 0) {
                break;
            }
            if (work) {
                if (newOutputMap && searchItemList.size() < SEARCH_MAP_LIMIT) {
                    searchItemList.add(inputItemWithWrapper);
                    searchInvList.add(inputMap);
                }
            } else {
                skipItemList.add(inputItemWithWrapper);
            }
        }
        return number;
    }
    public static int doCargoOutputMain(@Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, @Nonnull int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        InvWithSlots inputMap = null;
        InvWithSlots outputMap;
        boolean vanillaInputBlock;

        if((BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock))) {
            outputMap = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
            vanillaInputBlock = false;
        } else if(FinalTech.getInstance().getServer().isPrimaryThread()) {
            outputMap = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
            vanillaInputBlock = !BlockStorage.hasInventory(inputBlock) && PaperLib.getBlockState(inputBlock, false).getState() instanceof InventoryHolder;
            if(vanillaInputBlock) {
                inputMap = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
            }
        } else {
            try {
                InvWithSlots[] invWithSlots = FinalTech.getInstance().getServer().getScheduler().callSyncMethod(FinalTech.getInstance(), () -> {
                    InvWithSlots[] result = new InvWithSlots[2];
                    if(BlockStorage.hasInventory(inputBlock)) {
                        result[0] = null;
                    } else {
                        result[0] = CargoUtil.getInvAsync(inputBlock, inputSize, inputOrder);
                    }
                    result[1] = CargoUtil.getInvAsync(outputBlock, outputSize, outputOrder);
                    return result;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
                vanillaInputBlock = inputMap != null;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return 0;
            }
        }

        if (outputMap == null) {
            return 0;
        }
        if (vanillaInputBlock && inputMap == null) {
            return 0;
        }

        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();

        List<ItemStackWithWrapper> skipItemList = new ArrayList<>(outputMap.getSlots().length);
        List<ItemStackWithWrapper> filterList = MachineUtil.getItemList(filterInv, filterSlots);
        List<ItemStackWithWrapper> searchItemList = new ArrayList<>(SEARCH_MAP_LIMIT);
        List<InvWithSlots> searchInvList = new ArrayList<>(SEARCH_MAP_LIMIT);
        ItemStackWithWrapper typeItem = null;
        int number = 0;
        boolean newInputMap = false;
        for (int outputSlot : outputSlots) {
            ItemStack outputItem = outputInv.getItem(outputSlot);
            ItemStackWithWrapper outputItemWithWrapper;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if(CargoLimit.VALUE_NONNULL.equals(cargoLimit)) {
                    continue;
                }
                outputItem = ItemStackUtil.AIR;
                outputItemWithWrapper = new ItemStackWithWrapper(outputItem);
            } else {
                if (outputItem.getAmount() >= outputItem.getMaxStackSize()) {
                    continue;
                }
                outputItemWithWrapper = new ItemStackWithWrapper(outputItem);
                if (!CargoUtil.isMatch(outputItemWithWrapper, filterList, cargoFilter)) {
                    continue;
                }
                if (CargoUtil.isMatch(outputItemWithWrapper, skipItemList, CargoFilter.VALUE_WHITE)) {
                    continue;
                }
            }
            if (!vanillaInputBlock) {
                inputMap = null;
                newInputMap = false;
                for (int i = 0; i < searchItemList.size(); i++) {
                    if (ItemStackUtil.isItemSimilar(outputItemWithWrapper, searchItemList.get(i))) {
                        inputMap = searchInvList.get(i);
                        break;
                    }
                }
                if (inputMap == null) {
                    inputMap = CargoUtil.getInvSync(inputBlock, inputSize, inputOrder, outputItem);
                    if (inputMap == null) {
                        continue;
                    }
                    newInputMap = true;
                }
            }
            Inventory inputInv = inputMap.getInventory();
            int[] inputSlots = inputMap.getSlots();
            boolean work = false;
            for (int inputSlot : inputSlots) {
                ItemStack inputItem = inputInv.getItem(inputSlot);
                if (ItemStackUtil.isItemNull(inputItem)) {
                    continue;
                }
                ItemStackWithWrapper inputItemWithWrapper = new ItemStackWithWrapper(inputItem);
                if (ItemStackUtil.isItemNull(outputItem) && !CargoUtil.isMatch(inputItemWithWrapper, filterList, cargoFilter)) {
                    continue;
                }
                if (typeItem != null && !work && ItemStackUtil.isItemNull(outputItem) && !ItemStackUtil.isItemSimilar(inputItemWithWrapper, typeItem)) {
                    continue;
                }
                if (ItemStackUtil.isItemNull(outputItem)) {
                    if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                        typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                    }
                    int count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlot, outputItem);
                    outputItem = outputInv.getItem(outputSlot);
                    outputItemWithWrapper = new ItemStackWithWrapper(outputItem, inputItemWithWrapper.getItemStackWrapper());
                    inputItem.setAmount(inputItem.getAmount() - count);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (CargoLimit.VALUE_STACK.equals(cargoLimit)) {
                        cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                    }
                    if (outputItem.getAmount() >= outputItem.getMaxStackSize() || cargoNumber == 0) {
                        break;
                    }
                } else if (outputItem.getAmount() < outputItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(inputItemWithWrapper, outputItemWithWrapper)) {
                    if (typeItem == null && CargoLimit.typeLimit(cargoLimit)) {
                        typeItem = new ItemStackWithWrapper(inputItem, inputItemWithWrapper.getItemStackWrapper());
                    }
                    int count = ItemStackUtil.stack(inputItemWithWrapper, outputItemWithWrapper, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (CargoLimit.VALUE_STACK.equals(cargoLimit)) {
                        cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                    }
                    if (outputItem.getAmount() >= outputItem.getMaxStackSize() || cargoNumber == 0) {
                        break;
                    }
                }
            }
            if (cargoNumber == 0) {
                break;
            }
            if (work) {
                if (newInputMap && searchItemList.size() <= SEARCH_MAP_LIMIT) {
                    searchItemList.add(outputItemWithWrapper);
                    searchInvList.add(inputMap);
                }
            } else {
                skipItemList.add(outputItemWithWrapper);
            }
        }
        return number;
    }

    public static boolean isMatch(@Nonnull ItemStackWithWrapper itemStackWithWrapper, @Nonnull List<ItemStackWithWrapper> list, @Nonnull String cargoFilter) {
        for (ItemStackWithWrapper filterItem : list) {
            if (ItemStackUtil.isItemSimilar(itemStackWithWrapper, filterItem)) {
                return CargoFilter.VALUE_WHITE.equals(cargoFilter);
            }
        }
        return CargoFilter.VALUE_BLACK.equals(cargoFilter);
    }

    public static InvWithSlots getInvAsync(@Nonnull Block block, @Nonnull String size, @Nonnull String order) {
        return CargoUtil.getInvAsync(block, size, order, ItemStackUtil.AIR);
    }
    public static InvWithSlots getInvAsync(@Nonnull Block block, @Nonnull String size, @Nonnull String order, @Nonnull ItemStack item) {
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
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                    }
                    break;
                case SlotSearchSize.VALUE_OUTPUTS_ONLY:
                    if (ItemStackUtil.isItemNull(item)) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (slots.length == 0) {
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    break;
                case SlotSearchSize.VALUE_INPUTS_AND_OUTPUTS:
                    if (ItemStackUtil.isItemNull(item)) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (insert.length == 0) {
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
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
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
                        }
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    i = 0;
                    for (; i < withdraw.length; i++) {
                        slots[i] = withdraw[i];
                    }
                    System.arraycopy(insert, 0, slots, i, insert.length);
                    break;
                default:
                    slots = new int[0];
            }
        } else {
            if (FinalTech.getInstance().getServer().isPrimaryThread()) {
                BlockState blockState = PaperLib.getBlockState(block, false).getState();
                if (blockState instanceof InventoryHolder) {
                    inventory = ((InventoryHolder) blockState).getInventory();
                    slots = new int[inventory.getSize()];
                    for (int i = 0; i < slots.length; i++) {
                        slots[i] = i;
                    }
                }
            } else {
                Future<InvWithSlots> future = FinalTech.getInstance().getServer().getScheduler().callSyncMethod(JavaPlugin.getPlugin(FinalTech.class), () -> {
                    BlockState blockState = PaperLib.getBlockState(block, false).getState();
                    if (blockState instanceof InventoryHolder) {
                        Inventory inv = ((InventoryHolder) blockState).getInventory();
                        int[] slots1 = new int[inv.getSize()];
                        for (int i = 0; i < slots1.length; i++) {
                            slots1[i] = i;
                        }
                        return new InvWithSlots(inv, slots1);
                    }
                    return null;
                });
                try {
                    InvWithSlots invWithSlots = future.get();
                    if (invWithSlots == null) {
                        return null;
                    }
                    inventory = invWithSlots.inventory();
                    slots = invWithSlots.slots();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (inventory != null && slots != null) {
            switch (order) {
                case SlotSearchOrder.VALUE_DESCEND:
                    slots = JavaUtil.reserve(slots);
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
                case SlotSearchOrder.VALUE_RANDOM:
                    slots = JavaUtil.shuffle(slots);
                default:
                    break;
            }
            return new InvWithSlots(inventory, slots);
        }
        return null;
    }

    public static InvWithSlots getInvSync(@Nonnull Block block, @Nonnull String size, @Nonnull String order) {
        return CargoUtil.getInvSync(block, size, order, ItemStackUtil.AIR);
    }
    public static InvWithSlots getInvSync(@Nonnull Block block, @Nonnull String size, @Nonnull String order, @Nullable ItemStack item) {
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
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                    }
                    break;
                case SlotSearchSize.VALUE_OUTPUTS_ONLY:
                    if (ItemStackUtil.isItemNull(item)) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (slots.length == 0) {
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    break;
                case SlotSearchSize.VALUE_INPUTS_AND_OUTPUTS:
                    if (ItemStackUtil.isItemNull(item)) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (insert.length == 0) {
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
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
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
                        }
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    i = 0;
                    for (; i < withdraw.length; i++) {
                        slots[i] = withdraw[i];
                    }
                    System.arraycopy(insert, 0, slots, i, insert.length);
                    break;
                default:
                    slots = new int[0];
            }
        } else {
            BlockState state = block.getState();
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
                    slots = JavaUtil.reserve(slots);
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
                case SlotSearchOrder.VALUE_RANDOM:
                    slots = JavaUtil.shuffle(slots);
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
