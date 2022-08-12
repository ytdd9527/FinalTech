package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.InvWithSlots;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.api.dto.ItemWrapper;
import io.taraxacum.finaltech.api.factory.ServerRunnableLockFactory;
import io.taraxacum.finaltech.core.helper.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    /**
     * Do cargo action.
     * inputBlock should not be same with outputBlock
     * @param inputBlock Source #{@link Location} of #{@link BlockMenu} or #{@link Inventory}
     * @param outputBlock Target #{@link Location} of #{@link BlockMenu} or #{@link Inventory}
     * @param inputSize #{@link SlotSearchSize}
     * @param inputOrder #{@link SlotSearchOrder}
     * @param outputSize #{@link SlotSearchSize}
     * @param outputOrder #{@link SlotSearchOrder}
     * @param cargoNumber Number limited in one cargo action.
     * @param cargoLimit #{@link CargoLimit}
     * @param cargoFilter #{@link CargoFilter}
     * @param filterInv #{@link Inventory} for #{@link CargoFilter} to use
     * @param filterSlots the slots of the filterInv to be used.
     * @param cargoMode #{@link CargoMode}
     * @return
     */
    public static int doCargo(@Nonnull JavaPlugin javaPlugin, @Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots, @Nonnull String cargoMode) {
        return switch (cargoMode) {
            case CargoMode.VALUE_INPUT_MAIN -> CargoUtil.doCargoInputMain(javaPlugin, inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_OUTPUT_MAIN -> CargoUtil.doCargoOutputMain(javaPlugin, inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_STRONG_SYMMETRY -> CargoUtil.doCargoStrongSymmetry(javaPlugin, inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_WEAK_SYMMETRY -> CargoUtil.doCargoWeakSymmetry(javaPlugin, inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            default -> 0;
        };
    }
    public static int doCargoStrongSymmetry(@Nonnull JavaPlugin javaPlugin, @Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        // Get Inventory and it's slots.
        InvWithSlots inputMap;
        InvWithSlots outputMap;
        if(javaPlugin.getServer().isPrimaryThread() || (BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock))) {
            inputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
            outputMap = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
        } else {
            try {
                InvWithSlots[] invWithSlots = javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> {
                    InvWithSlots[] result = new InvWithSlots[2];
                    result[0] = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                    result[1] = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
                    return result;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage() == null) {
                    e.printStackTrace();
                } else {
                    javaPlugin.getServer().getLogger().severe(e.getMessage());
                }
                return 0;
            }
        }

        // Verify whether inventory is null
        if (inputMap == null || outputMap == null) {
            return 0;
        }

        // Do cargo action.
        if(javaPlugin.getServer().isPrimaryThread()) {
            return CargoUtil.doSimpleCargoStrongSymmetry(inputMap, outputMap, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
        } else {
            // While this method is called in async thread.
            final InvWithSlots finalInputMap = inputMap;
            final InvWithSlots finalOutputMap = outputMap;
            try {
                return ServerRunnableLockFactory.getInstance(javaPlugin, Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoStrongSymmetry(finalInputMap, finalOutputMap, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots), inputBlock.getLocation(), outputBlock.getLocation()).get();
            } catch (InterruptedException | ExecutionException e) {
                Bukkit.getLogger().severe(e.getMessage());
                return 0;
            }
        }
    }
    public static int doCargoWeakSymmetry(@Nonnull JavaPlugin javaPlugin, @Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        // Get Inventory and it's slots.
        InvWithSlots inputMap;
        InvWithSlots outputMap;
        if(javaPlugin.getServer().isPrimaryThread() || (BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock))) {
            inputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
            outputMap = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
        } else {
            try {
                InvWithSlots[] invWithSlots = javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> {
                    InvWithSlots[] result = new InvWithSlots[2];
                    result[0] = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                    result[1] = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
                    return result;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage() == null) {
                    e.printStackTrace();
                } else {
                    javaPlugin.getServer().getLogger().severe(e.getMessage());
                }
                return 0;
            }
        }

        // Verify whether inventory is null
        if (inputMap == null || outputMap == null) {
            return 0;
        }

        // Do cargo action.
        if(javaPlugin.getServer().isPrimaryThread()) {
            return CargoUtil.doSimpleCargoWeakSymmetry(inputMap, outputMap, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
        } else {
            // While this method is called in async thread.
            final InvWithSlots finalInputMap = inputMap;
            final InvWithSlots finalOutputMap = outputMap;
            try {
                return ServerRunnableLockFactory.getInstance(javaPlugin, Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoWeakSymmetry(finalInputMap, finalOutputMap, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots), inputBlock.getLocation(), outputBlock.getLocation()).get();
            } catch (InterruptedException | ExecutionException e) {
                Bukkit.getLogger().severe(e.getMessage());
                return 0;
            }
        }
    }
    public static int doCargoInputMain(@Nonnull JavaPlugin javaPlugin, @Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        final InvWithSlots inputMap;
        final InvWithSlots outputMap;
        // OutputMap will be null if BlockStorage has inventory in output block.
        //      In this situation, we will get output inventory dynamically.
        // If there is no output inventory, just return 0.

        // Get inventory.
        if(javaPlugin.getServer().isPrimaryThread()) {
            // Just get inputMap.
            inputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
            if(BlockStorage.hasInventory(outputBlock)) {
                outputMap = null;
            } else {
                if(PaperLib.getBlockState(outputBlock, false).getState() instanceof InventoryHolder) {
                    // Output Inventory is vanilla container.
                    outputMap = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
                    if(outputMap == null) {
                        return 0;
                    }
                } else {
                    // Output Inventory not existed.
                    return 0;
                }
            }
        } else if((BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock))) {
            // In this situation, we should get inventory dynamically, so outputMap is still null.
            inputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
            outputMap = null;
        } else {
            // This method is not called in main thread, and we must get inventory in main thread.
            try {
                InvWithSlots[] invWithSlots = javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> {
                    InvWithSlots[] result = new InvWithSlots[2];
                    if(BlockStorage.hasInventory(outputBlock)) {
                        result[0] = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                        result[1] = null;
                    } else if(!CargoUtil.hasInventory(outputBlock)) {
                        return result;
                    } else {
                        // Output inventory is vanilla container.
                        result[0] = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                        if(result[0] == null) {
                            // Input inventory is not existed. No need to get output inventory.
                            return result;
                        }
                        result[1] = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
                    }
                    return result;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage() == null) {
                    e.printStackTrace();
                } else {
                    javaPlugin.getServer().getLogger().severe(e.getMessage());
                }
                return 0;
            }
        }

        if (inputMap == null) {
            return 0;
        }

        if(javaPlugin.getServer().isPrimaryThread()) {
            return CargoUtil.doSimpleCargoInputMain(inputMap, outputMap, outputBlock, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
        } else {
            // While this method is called in async thread.
            try {
                return ServerRunnableLockFactory.getInstance(javaPlugin, Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoInputMain(inputMap, outputMap, outputBlock, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots), inputBlock.getLocation(), outputBlock.getLocation()).get();
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage() == null) {
                    e.printStackTrace();
                } else {
                    javaPlugin.getServer().getLogger().severe(e.getMessage());
                }
                return 0;
            }
        }
    }
    public static int doCargoOutputMain(@Nonnull JavaPlugin javaPlugin, @Nonnull Block inputBlock, @Nonnull Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        final InvWithSlots inputMap;
        final InvWithSlots outputMap;
        // InputMap will be null if BlockStorage has inventory in input block.
        //      In this situation, we will get input inventory dynamically.
        // If there is no input inventory, just return 0.

        // Get inventory.
        if(javaPlugin.getServer().isPrimaryThread()) {
            // Just get outputMap.
            outputMap = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
            if(BlockStorage.hasInventory(inputBlock)) {
                inputMap = null;
            } else {
                if(PaperLib.getBlockState(inputBlock, false).getState() instanceof InventoryHolder) {
                    // Input Inventory is vanilla container.
                    inputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                    if(inputMap == null) {
                        return 0;
                    }
                } else {
                    // Input Inventory not existed.
                    return 0;
                }
            }
        } else if((BlockStorage.hasInventory(inputBlock) && BlockStorage.hasInventory(outputBlock))) {
            // In this situation, we should get inventory dynamically, so inputMap is still null.
            outputMap = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
            inputMap = null;
        } else {
            // This method is not called in main thread, and we must get inventory in main thread.
            try {
                InvWithSlots[] invWithSlots = javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> {
                    InvWithSlots[] result = new InvWithSlots[2];
                    if(BlockStorage.hasInventory(inputBlock)) {
                        result[0] = null;
                        result[1] = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
                    } else if(!CargoUtil.hasInventory(inputBlock)) {
                        return result;
                    } else {
                        // Input inventory is vanilla container.
                        result[1] = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
                        if(result[1] == null) {
                            // Output inventory is not existed. No need to get input inventory.
                            return result;
                        }
                        result[0] = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                    }
                    return result;
                }).get();
                inputMap = invWithSlots[0];
                outputMap = invWithSlots[1];
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage() == null) {
                    e.printStackTrace();
                } else {
                    javaPlugin.getServer().getLogger().severe(e.getMessage());
                }
                return 0;
            }
        }

        if (outputMap == null) {
            return 0;
        }

        if(javaPlugin.getServer().isPrimaryThread()) {
            return CargoUtil.doSimpleCargoOutputMain(inputMap, inputBlock, outputMap, inputSize, inputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
        } else {
            // While this method is called in async thread.
            try {
                return ServerRunnableLockFactory.getInstance(javaPlugin, Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoOutputMain(inputMap, inputBlock, outputMap, inputSize, inputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots), inputBlock.getLocation(), outputBlock.getLocation()).get();
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage() == null) {
                    e.printStackTrace();
                } else {
                    javaPlugin.getServer().getLogger().severe(e.getMessage());
                }
                return 0;
            }
        }
    }

    /**
     * @param cargoMode Check whether params could be null depend on it.#{@link CargoMode#VALUE_INPUT_MAIN} #{@link CargoMode#VALUE_OUTPUT_MAIN} #{@link CargoMode#VALUE_STRONG_SYMMETRY} #{@link CargoMode#VALUE_WEAK_SYMMETRY}
     */
    public static int doSimpleCargo(@Nullable InvWithSlots inputMap, @Nullable Block inputBlock, @Nullable InvWithSlots outputMap, @Nullable Block outputBlock, @Nonnull String inputSize, @Nonnull String inputOrder, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots, @Nonnull String cargoMode) {
        return switch (cargoMode) {
            case CargoMode.VALUE_INPUT_MAIN -> CargoUtil.doSimpleCargoInputMain(inputMap, outputMap, outputBlock, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_OUTPUT_MAIN -> CargoUtil.doSimpleCargoOutputMain(inputMap, inputBlock, outputMap, inputSize, inputOrder, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_STRONG_SYMMETRY -> CargoUtil.doSimpleCargoStrongSymmetry(inputMap, outputMap, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            case CargoMode.VALUE_WEAK_SYMMETRY -> CargoUtil.doSimpleCargoWeakSymmetry(inputMap, outputMap, cargoNumber, cargoLimit, cargoFilter, filterInv, filterSlots);
            default -> 0;
        };
    }

    public static int doSimpleCargoStrongSymmetry(@Nonnull InvWithSlots inputMap, @Nonnull InvWithSlots outputMap, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();

        List<ItemWrapper> filterItemList = MachineUtil.getItemList(filterInv, filterSlots);

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(cargoLimit);
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(cargoLimit);
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(cargoLimit);
        boolean typeLimit = !nonnull && !stack && !first && CargoLimit.typeLimit(cargoLimit);

        int number = 0;
        ItemWrapper typeItem = null;
        ItemWrapper inputItemWrapper = new ItemWrapper();

        for (int i = 0, length = Math.min(inputSlots.length, outputSlots.length); i < length; i++) {
            ItemStack inputItem = inputInv.getItem(inputSlots[i]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            inputItemWrapper.newWrap(inputItem);
            if (!CargoUtil.isMatch(inputItemWrapper, filterItemList, cargoFilter)) {
                continue;
            }
            if (typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                continue;
            }

            ItemStack outputItem = outputInv.getItem(outputSlots[i]);
            int count;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if(!nonnull) {
                    // Move input item to output slot.
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                    }
                    count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlots[i], outputItem);
                    outputItem = outputInv.getItem(outputSlots[i]);
                    inputItem.setAmount(inputItem.getAmount() - count);
                } else {
                    continue;
                }
            } else {
                if (typeItem == null && typeLimit) {
                    typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                }
                count = ItemStackUtil.stack(inputItemWrapper, outputItem, cargoNumber);
                if (count == 0) {
                    continue;
                }
            }

            cargoNumber -= count;
            number += count;
            if (cargoNumber == 0) {
                break;
            }
            if (stack) {
                cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
            }
            if (first) {
                break;
            }
        }
        return number;
    }
    public static int doSimpleCargoWeakSymmetry(@Nonnull InvWithSlots inputMap, @Nonnull InvWithSlots outputMap, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();

        List<ItemWrapper> filterItemList = MachineUtil.getItemList(filterInv, filterSlots);

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(cargoLimit);
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(cargoLimit);
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(cargoLimit);
        boolean typeLimit = !nonnull && !stack && !first && CargoLimit.typeLimit(cargoLimit);

        int number = 0;
        ItemWrapper typeItem = null;
        ItemWrapper inputItemWrapper = new ItemWrapper();

        for (int i = 0, length = Math.max(inputSlots.length, outputSlots.length); i < length; i++) {
            ItemStack inputItem = inputInv.getItem(inputSlots[i % inputSlots.length]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            inputItemWrapper.newWrap(inputItem);
            if (!CargoUtil.isMatch(inputItemWrapper, filterItemList, cargoFilter)) {
                continue;
            }
            if (typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                continue;
            }

            ItemStack outputItem = outputInv.getItem(outputSlots[i % outputSlots.length]);
            int count;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if(!nonnull) {
                    // Move input item to output slot.
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                    }
                    count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlots[i], outputItem);
                    outputItem = outputInv.getItem(outputSlots[i]);
                    inputItem.setAmount(inputItem.getAmount() - count);
                } else {
                    continue;
                }
            } else {
                if (typeItem == null && typeLimit) {
                    typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                }
                count = ItemStackUtil.stack(inputItemWrapper, outputItem, cargoNumber);
                if (count == 0) {
                    continue;
                }
            }

            cargoNumber -= count;
            number += count;
            if (cargoNumber == 0) {
                break;
            }
            if (stack) {
                cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
            }
            if (first) {
                break;
            }
        }
        return number;
    }

    /**
     * @param outputMap Null while outputBlock is vanilla container
     * @param outputBlock should not be null if outputMap is null
     */
    public static int doSimpleCargoInputMain(@Nonnull InvWithSlots inputMap, @Nullable InvWithSlots outputMap, @Nullable Block outputBlock, @Nonnull String outputSize, @Nonnull String outputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        Inventory inputInv = inputMap.getInventory();
        int[] inputSlots = inputMap.getSlots();

        List<ItemWrapper> skipItemList = new ArrayList<>(inputSlots.length);
        List<ItemWrapper> filterItemList = MachineUtil.getItemList(filterInv, filterSlots);

        boolean dynamicOutputBlock = outputBlock != null && outputMap == null;
        List<ItemWrapper> searchItemList = new ArrayList<>(SEARCH_MAP_LIMIT);
        List<InvWithSlots> searchInvList = new ArrayList<>(SEARCH_MAP_LIMIT);
        boolean newOutputMap = false;

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(cargoLimit);
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(cargoLimit);
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(cargoLimit);
        boolean typeLimit = !nonnull && !stack && !first && CargoLimit.typeLimit(cargoLimit);

        int number = 0;
        ItemWrapper typeItem = null;
        ItemWrapper inputItemWrapper = new ItemWrapper();

        for (int inputSlot : inputSlots) {
            ItemStack inputItem = inputInv.getItem(inputSlot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            inputItemWrapper.newWrap(inputItem);
            if (typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                continue;
            }
            if (!CargoUtil.isMatch(inputItemWrapper, filterItemList, cargoFilter)) {
                continue;
            }
            if (CargoUtil.isMatch(inputItemWrapper, skipItemList, CargoFilter.VALUE_WHITE)) {
                continue;
            }
            if (dynamicOutputBlock) {
                outputMap = null;
                newOutputMap = false;
                for (int i = 0; i < searchItemList.size(); i++) {
                    if (ItemStackUtil.isItemSimilar(inputItemWrapper, searchItemList.get(i))) {
                        outputMap = searchInvList.get(i);
                        break;
                    }
                }
                if (outputMap == null) {
                    newOutputMap = true;
                    outputMap = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder, inputItem);
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
                    if(!nonnull) {
                        if (typeItem == null && typeLimit) {
                            typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                        }
                        int count = Math.min(inputItem.getAmount(), cargoNumber);
                        outputItem = ItemStackUtil.cloneItem(inputItem);
                        outputItem.setAmount(count);
                        outputInv.setItem(outputSlot, outputItem);
                        inputItem.setAmount(inputItem.getAmount() - count);
                        cargoNumber -= count;
                        number += count;
                        work = true;
                        if (stack) {
                            cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                            break;
                        }
                        if (inputItem.getAmount() == 0 || cargoNumber == 0) {
                            break;
                        }
                    }
                } else if (outputItem.getAmount() < outputItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(inputItemWrapper, outputItem)) {
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                    }
                    int count = ItemStackUtil.stack(inputItemWrapper, outputItem, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (stack) {
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
                if (first) {
                    break;
                } else if (newOutputMap && searchItemList.size() < SEARCH_MAP_LIMIT) {
                    searchItemList.add(inputItemWrapper);
                    searchInvList.add(inputMap);
                }
            } else {
                skipItemList.add(inputItemWrapper);
            }
        }
        return number;
    }

    /**
     * @param inputMap Null while inputBlock is vanilla container
     * @param inputBlock should not be null if outputMap is null
     */
    public static int doSimpleCargoOutputMain(@Nullable InvWithSlots inputMap, @Nullable Block inputBlock, @Nonnull InvWithSlots outputMap, @Nonnull String inputSize, @Nonnull String inputOrder, int cargoNumber, @Nonnull String cargoLimit, @Nonnull String cargoFilter, @Nonnull Inventory filterInv, int[] filterSlots) {
        Inventory outputInv = outputMap.getInventory();
        int[] outputSlots = outputMap.getSlots();

        List<ItemWrapper> skipItemList = new ArrayList<>(outputMap.getSlots().length);
        List<ItemWrapper> filterList = MachineUtil.getItemList(filterInv, filterSlots);

        boolean dynamicInputBlock = inputBlock != null && inputMap == null;
        List<ItemWrapper> searchItemList = new ArrayList<>(SEARCH_MAP_LIMIT);
        List<InvWithSlots> searchInvList = new ArrayList<>(SEARCH_MAP_LIMIT);
        InvWithSlots nullItemInputMap = null;
        boolean newInputMap = false;

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(cargoLimit);
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(cargoLimit);
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(cargoLimit);
        boolean typeLimit = !nonnull && !stack && !first && CargoLimit.typeLimit(cargoLimit);

        int number = 0;
        ItemWrapper typeItem = null;
        final ItemWrapper finalItemWrapper = new ItemWrapper();
        ItemWrapper outputItemWrapper = finalItemWrapper;

        for (int outputSlot : outputSlots) {
            ItemStack outputItem = outputInv.getItem(outputSlot);
            outputItemWrapper = null;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if(nonnull) {
                    continue;
                }
            } else {
                if (outputItem.getAmount() >= outputItem.getMaxStackSize()) {
                    continue;
                }
                outputItemWrapper = finalItemWrapper;
                outputItemWrapper.newWrap(outputItem);
                if (!CargoUtil.isMatch(outputItemWrapper, filterList, cargoFilter)) {
                    continue;
                }
                if (CargoUtil.isMatch(outputItemWrapper, skipItemList, CargoFilter.VALUE_WHITE)) {
                    continue;
                }
                if (typeItem != null && !ItemStackUtil.isItemSimilar(outputItemWrapper, typeItem)) {
                    continue;
                }
            }
            if (dynamicInputBlock) {
                if(outputItemWrapper == null) {
                    if(nullItemInputMap == null) {
                        nullItemInputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                    }
                    inputMap = nullItemInputMap;
                    newInputMap = false;
                } else {
                    inputMap = null;
                    newInputMap = false;
                    for (int i = 0; i < searchItemList.size(); i++) {
                        if (ItemStackUtil.isItemSimilar(outputItemWrapper, searchItemList.get(i))) {
                            inputMap = searchInvList.get(i);
                            break;
                        }
                    }
                    if (inputMap == null) {
                        inputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder, outputItem);
                        if (inputMap == null) {
                            continue;
                        }
                        newInputMap = true;
                    }
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
                ItemWrapper inputItemWrapper = new ItemWrapper(inputItem);
                if (outputItemWrapper == null) {
                    if(!CargoUtil.isMatch(inputItemWrapper, filterList, cargoFilter)) {
                        continue;
                    }
                    if(typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                        continue;
                    }
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(inputItem, inputItemWrapper.getItemMeta());
                    }
                    int count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlot, outputItem);
                    outputItem = outputInv.getItem(outputSlot);
                    outputItemWrapper = finalItemWrapper;
                    outputItemWrapper.newWrap(outputItem, inputItemWrapper.getItemMeta());
                    inputItem.setAmount(inputItem.getAmount() - count);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (stack) {
                        cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                    }
                    if (outputItem.getAmount() >= outputItem.getMaxStackSize() || cargoNumber == 0) {
                        break;
                    }
                } else if (outputItem.getAmount() < outputItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(inputItemWrapper, outputItemWrapper)) {
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(inputItem, inputItemWrapper.getItemMeta());
                    }
                    int count = ItemStackUtil.stack(inputItemWrapper, outputItemWrapper, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (stack) {
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
                if (first) {
                    break;
                } else if (newInputMap && searchItemList.size() <= SEARCH_MAP_LIMIT) {
                    searchItemList.add(outputItemWrapper);
                    searchInvList.add(inputMap);
                }
            } else {
                if(outputItemWrapper == null) {
                    break;
                } else {
                    skipItemList.add(outputItemWrapper);
                }
            }
        }
        return number;
    }

    public static boolean isMatch(@Nonnull ItemWrapper itemWrapper, @Nonnull List<ItemWrapper> list, @Nonnull String cargoFilter) {
        for (ItemWrapper filterItem : list) {
            if (ItemStackUtil.isItemSimilar(itemWrapper, filterItem)) {
                return CargoFilter.VALUE_WHITE.equals(cargoFilter);
            }
        }
        return CargoFilter.VALUE_BLACK.equals(cargoFilter);
    }

    @Nullable
    public static InvWithSlots getInvWithSlots(@Nonnull Block block, @Nonnull String size, @Nonnull String order) {
        return CargoUtil.getInvWithSlots(block, size, order, ItemStackUtil.AIR);
    }
    @Nullable
    public static InvWithSlots getInvWithSlots(@Nonnull Block block, @Nonnull String size, @Nonnull String order, @Nonnull ItemStack item) {
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
            if (Bukkit.isPrimaryThread()) {
                BlockState blockState = PaperLib.getBlockState(block, false).getState();
                if (blockState instanceof InventoryHolder) {
                    inventory = ((InventoryHolder) blockState).getInventory();
                    slots = new int[inventory.getSize()];
                    for (int i = 0; i < slots.length; i++) {
                        slots[i] = i;
                    }
                }
            } else {
                Future<InvWithSlots> future = Bukkit.getScheduler().callSyncMethod(FinalTech.getInstance(), () -> {
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

    public static boolean hasInventory(@Nonnull Block block) {
        if (BlockStorage.hasInventory(block)) {
            return true;
        }
        return PaperLib.getBlockState(block, false).getState() instanceof InventoryHolder;
    }
}
