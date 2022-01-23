package io.taraxacum.finaltech.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.FinalCargoMachine;
import io.taraxacum.finaltech.menu.PipeGroupMenu;
import io.taraxacum.finaltech.util.CargoUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nonnull;
import java.util.List;

public class PipeGroup extends FinalCargoMachine {

    public PipeGroup(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        new PipeGroupMenu(this.getId(), this.getItemName(), false) {

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return player.hasPermission("slimefun.inventory.bypass") ? true : canUse(player, false) && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }
        };
    }

    @Override
    public void preRegister() {
        super.preRegister();

        this.addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();;
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_ITEM_COUNT, "64");
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_SIZE, CargoUtil.SIZE_OUTPUTS_ONLY);
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_ORDER, CargoUtil.ORDER_ASCENT);
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_ITEM_COUNT_MODE, CargoUtil.ITEM_COUNT_MODE_UNIVERSAL);
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_ITEM_MODE, CargoUtil.ITEM_MODE_ALL);
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_ITEM_COUNT, "64");
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_SIZE, CargoUtil.SIZE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_ORDER, CargoUtil.ORDER_ASCENT);
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_ITEM_COUNT_MODE, CargoUtil.ITEM_COUNT_MODE_UNIVERSAL);
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_ITEM_MODE, CargoUtil.ITEM_MODE_ALL);
                BlockStorage.addBlockInfo(block, CargoUtil.FILTER_MODE, CargoUtil.FILTER_MODE_BLACK_LIST);
                BlockStorage.addBlockInfo(block, CargoUtil.POSITION_INFO, "");
                BlockStorage.addBlockInfo(block, CargoUtil.PIPE_GROUP_SEARCH_MODE, CargoUtil.PIPE_GROUP_SEARCH_MODE_RESPECT);
            }
        });

        this.addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                BlockStorage.clearBlockInfo(blockBreakEvent.getBlock());
                if (BlockStorage.hasInventory(blockBreakEvent.getBlock())) {
                    BlockMenu blockMenu = BlockStorage.getInventory(blockBreakEvent.getBlock());
                    blockMenu.dropItems(blockMenu.getLocation(), PipeGroupMenu.ITEM_MATCH);
                    blockMenu.dropItems(blockMenu.getLocation(), PipeGroupMenu.INPUT_SLOTS);
                    blockMenu.dropItems(blockMenu.getLocation(), PipeGroupMenu.OUTPUT_SLOTS);
                }
            }
        });
    }

    @Override
    public void tick(Block block) {

        // do something now?
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        String filterMode = BlockStorage.getLocationInfo(location, CargoUtil.FILTER_MODE);
        String locationInfo = BlockStorage.getLocationInfo(location, CargoUtil.POSITION_INFO);
        CargoUtil.Position position = new CargoUtil.Position(locationInfo);

        // do output
        String outputSize = BlockStorage.getLocationInfo(location, CargoUtil.OUTPUT_SIZE);
        String outputOrder = BlockStorage.getLocationInfo(location, CargoUtil.INPUT_ORDER);
        int outputItemCount = Integer.valueOf(BlockStorage.getLocationInfo(location, CargoUtil.OUTPUT_ITEM_COUNT));
        String outputItemCountMode = BlockStorage.getLocationInfo(location, CargoUtil.OUTPUT_ITEM_COUNT_MODE);
        String outputItemMode = BlockStorage.getLocationInfo(location, CargoUtil.OUTPUT_ITEM_MODE);
        String[] outputs = position.getOutputs();

        for(String outputPosition : outputs) {
            BlockFace blockFace = CargoUtil.getBlockFaceByPosition(outputPosition);
            Block outputBlock = CargoUtil.searchBlockPipeGroup(block, BlockStorage.getLocationInfo(location, CargoUtil.PIPE_GROUP_SEARCH_MODE), blockFace);
//            Block outputBlock = block.getRelative(CargoUtil.getBlockFaceByPosition(outputPosition));
//            while (outputBlock.getType() == Material.CHAIN && BlockStorage.getInventory(outputBlock) == null) {
//                BlockData blockData = outputBlock.getBlockData();
//                if(blockData instanceof Orientable) {
//                    Axis axis = ((Orientable) blockData).getAxis();
//                    if(!CargoUtil.ifAxisMatchFace(axis, blockFace)) {
//                        break;
//                    }
//                }
//                outputBlock = outputBlock.getRelative(blockFace);
//            }
            if(outputBlock == null) {
                continue;
            }
            int itemCount = outputItemCount;
            ItemStack typeItem = null;
            for(int inputSlot : PipeGroupMenu.OUTPUT_SLOTS) {
                if (outputItemCount == 0) {
                    break;
                }
                if(CargoUtil.ITEM_MODE_STACK.equals(outputItemMode) && typeItem != null) {
                    break;
                }
                ItemStack inputItem = blockMenu.getItemInSlot(inputSlot);
                if (inputItem == null || CargoUtil.ifMatch(inputItem, blockMenu, filterMode, PipeGroupMenu.ITEM_MATCH) == false) {
                    continue;
                }
                if(CargoUtil.ITEM_MODE_TYPE.equals(outputItemMode) && typeItem != null && !SlimefunUtils.isItemSimilar(inputItem, typeItem, true, false)) {
                    continue;
                }
                CargoUtil.InvWithSlots outputMap = CargoUtil.getInv(outputBlock, outputSize, outputOrder, inputItem);
                if(outputMap == null) {
                    continue;
                }
                Inventory outputInv = outputMap.getInventory();
                int[] outputSlots = outputMap.getSlots();
                if(outputInv == null || outputSlots == null) {
                    continue;
                }
                for(int outputSlot : outputSlots) {
                    if(itemCount == 0) {
                        break;
                    }
                    ItemStack outputItem = outputInv.getItem(outputSlot);
                    if(outputItem == null) {
                        if(typeItem == null) {
                            typeItem = new ItemStack(inputItem);
                        }
                        int count = Math.min(itemCount, inputItem.getAmount());
                        outputInv.setItem(outputSlot, new CustomItemStack(inputItem, count));
                        inputItem.setAmount(inputItem.getAmount() - count);
                        itemCount -= count;
                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(outputItemCountMode)) {
                            outputItemCount -= count;
                        }
                        if (CargoUtil.ITEM_MODE_STACK.equals(outputItemMode)) {
                            break;
                        }
                    } else if(SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                        if(typeItem == null) {
                            typeItem = new ItemStack(inputItem);
                        }
                        int count = Math.min(itemCount, Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount()));
                        CargoUtil.changeItemAmount(inputItem, outputItem, count);
                        itemCount -= count;
                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(outputItemCountMode)) {
                            outputItemCount -= count;
                        }
                        if (CargoUtil.ITEM_MODE_STACK.equals(outputItemMode)) {
                            break;
                        }
                    }
                }
            }
        }

//        for (String outputPosition: outputs) {
//            Block outputBlock = block.getRelative(CargoUtil.getBlockFaceByPosition(outputPosition));
//            if(outputBlock == null) {
//                continue;
//            }
//            CargoUtil.InvWithSlots outputMap = CargoUtil.getInv(outputBlock, outputSize, outputOrder);
//            if(outputMap == null) {
//                continue;
//            }
//            Inventory outputInv = outputMap.getInventory();
//            int[] outputSlots = outputMap.getSlots();
//            int itemCount = outputItemCount;
//            ItemStack typeItem = null;
//            for(int outputSlot : outputSlots) {
//                if(itemCount == 0) {
//                    break;
//                }
//                if(CargoUtil.ITEM_MODE_STACK.equals(outputItemMode) && typeItem != null) {
//                    break;
//                }
//                ItemStack outputItem = outputInv.getItem(outputSlot);
//                for (int slot : PipeGroupMenu.OUTPUT_SLOTS) {
//                    if(itemCount == 0) {
//                        break;
//                    }
//                    ItemStack item = blockMenu.getItemInSlot(slot);
//                    if(item == null || CargoUtil.ifMatch(item, blockMenu, filterMode, PipeGroupMenu.ITEM_MATCH) == false) {
//                        continue;
//                    }
//                    if(CargoUtil.ITEM_MODE_TYPE.equals(outputItemMode) && typeItem != null && item != null && !SlimefunUtils.isItemSimilar(item, typeItem, true, false)) {
//                        continue;
//                    }
//                    if(outputItem == null) {
//                        if(typeItem == null) {
//                            typeItem = new ItemStack(item);
//                        }
//                        int count = Math.min(itemCount, item.getAmount());
//                        outputInv.setItem(outputSlot, new CustomItemStack(item, count));
//                        item.setAmount(item.getAmount() - count);
//                        itemCount -=count;
//                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(outputItemCountMode)) {
//                            outputItemCount -= count;
//                        }
//                        if (CargoUtil.ITEM_MODE_STACK.equals(outputItemMode)) {
//                            break;
//                        }
//                    } else if(SlimefunUtils.isItemSimilar(outputItem, item, true, false)) {
//                        if(typeItem == null) {
//                            typeItem = new ItemStack(item);
//                        }
//                        int count = Math.min(itemCount, Math.min(item.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount()));
//                        CargoUtil.changeItemAmount(item, outputItem, count);
//                        itemCount -= count;
//                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(outputItemCountMode)) {
//                            outputItemCount -= count;
//                        }
//                        if (CargoUtil.ITEM_MODE_STACK.equals(outputItemMode)) {
//                            break;
//                        }
//                    }
//                }
//            }
//        }

        // do move
        for(int i = 0; i < PipeGroupMenu.INPUT_SLOTS.length; i++) {
            ItemStack inputItem = blockMenu.getItemInSlot(PipeGroupMenu.INPUT_SLOTS[i]);
            if(inputItem == null) {
                continue;
            }
            ItemStack outputItem = blockMenu.getItemInSlot(PipeGroupMenu.OUTPUT_SLOTS[i]);
            if(outputItem == null) {
                blockMenu.toInventory().setItem(PipeGroupMenu.OUTPUT_SLOTS[i], new ItemStack(inputItem));
                inputItem.setAmount(0);
            } else if(SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                int count = Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount());
                inputItem.setAmount(inputItem.getAmount() - count);
                outputItem.setAmount(outputItem.getAmount() + count);
            }
        }

        // do input
        String inputSize = BlockStorage.getLocationInfo(location, CargoUtil.INPUT_SIZE);
        String inputOrder = BlockStorage.getLocationInfo(location, CargoUtil.INPUT_ORDER);
        int inputItemCount = Integer.valueOf(BlockStorage.getLocationInfo(location, CargoUtil.INPUT_ITEM_COUNT));
        String inputItemCountMode = BlockStorage.getLocationInfo(location, CargoUtil.INPUT_ITEM_COUNT_MODE);
        String inputItemMode = BlockStorage.getLocationInfo(location, CargoUtil.INPUT_ITEM_MODE);
        String[] inputs = position.getInputs();

        for(String inputPosition : inputs) {
            BlockFace blockFace = CargoUtil.getBlockFaceByPosition(inputPosition);
            Block inputBlock = CargoUtil.searchBlockPipeGroup(block, BlockStorage.getLocationInfo(location, CargoUtil.PIPE_GROUP_SEARCH_MODE), blockFace);
//            Block inputBlock = block.getRelative(CargoUtil.getBlockFaceByPosition(inputPosition));
//            while (inputBlock.getType() == Material.CHAIN && BlockStorage.getInventory(inputBlock) == null) {
//                BlockData blockData = inputBlock.getBlockData();
//                if(blockData instanceof Orientable) {
//                    Axis axis = ((Orientable) blockData).getAxis();
//                    if(!CargoUtil.ifAxisMatchFace(axis, blockFace)) {
//                        break;
//                    }
//                }
//                inputBlock = inputBlock.getRelative(blockFace);
//            }
            if(inputBlock == null) {
                continue;
            }
            int itemCount = inputItemCount;
            ItemStack typeItem = null;
            for(int outputSlot : PipeGroupMenu.INPUT_SLOTS) {
                if(itemCount == 0) {
                    break;
                }
                ItemStack outputItem = blockMenu.getItemInSlot(outputSlot);
                if(CargoUtil.ITEM_MODE_TYPE.equals(inputItemMode) && typeItem != null && outputItem != null && !SlimefunUtils.isItemSimilar(outputItem, typeItem, true, false)) {
                    continue;
                }
                if(CargoUtil.ITEM_MODE_STACK.equals(inputItemMode) && typeItem != null) {
                    break;
                }
                CargoUtil.InvWithSlots inputMap = CargoUtil.getInv(inputBlock, inputSize, inputOrder, outputItem);
                if(inputMap == null) {
                    continue;
                }
                Inventory inputInv = inputMap.getInventory();
                int[] inputSlots = inputMap.getSlots();
                if(inputInv == null || inputSlots == null) {
                    continue;
                }
                for(int inputSlot : inputSlots) {
                    if(itemCount == 0) {
                        break;
                    }
                    if(CargoUtil.ITEM_MODE_STACK.equals(inputItemMode) && typeItem != null) {
                        break;
                    }
                    if(outputItem != null && outputItem.getAmount() == outputItem.getMaxStackSize()) {
                        break;
                    }
                    ItemStack inputItem = inputInv.getItem(inputSlot);
                    if(inputItem == null || CargoUtil.ifMatch(inputItem, blockMenu, filterMode, PipeGroupMenu.ITEM_MATCH) == false) {
                        continue;
                    }
                    if(CargoUtil.ITEM_MODE_TYPE.equals(inputItemMode) && typeItem != null && !SlimefunUtils.isItemSimilar(inputItem, typeItem, true, false)) {
                        continue;
                    }
                    if(outputItem == null) {
                        if(typeItem == null) {
                            typeItem = new ItemStack(inputItem);
                        }
                        int count = Math.min(itemCount, inputItem.getAmount());
                        blockMenu.toInventory().setItem(outputSlot, new CustomItemStack(inputItem, count));
                        outputItem = blockMenu.getItemInSlot(outputSlot);
                        inputItem.setAmount(inputItem.getAmount() - count);
                        itemCount -= count;
                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(inputItemCountMode)) {
                            inputItemCount -= count;
                        }
                        if (CargoUtil.ITEM_MODE_STACK.equals(inputItemMode)) {
                            break;
                        }
                    } else if(SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                        if(typeItem == null) {
                            typeItem = new ItemStack(inputItem);
                        }
                        int count = Math.min(itemCount, Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount()));
                        CargoUtil.changeItemAmount(inputItem, outputItem, count);
                        itemCount -= count;
                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(inputItemCountMode)) {
                            inputItemCount -= count;
                        }
                        if (CargoUtil.ITEM_MODE_STACK.equals(inputItemMode)) {
                            break;
                        }
                    }
                }
            }
        }
//        for (String inputPosition : inputs) {
//            Block inputBlock = block.getRelative(CargoUtil.getBlockFaceByPosition(inputPosition));
//            if (inputBlock == null) {
//                continue;
//            }
//            CargoUtil.InvWithSlots inputMap = CargoUtil.getInv(inputBlock, inputSize, inputOrder);
//            if (inputMap == null) {
//                continue;
//            }
//            Inventory inputInv = inputMap.getInventory();
//            int[] inputSlots = inputMap.getSlots();
//            int itemCount = inputItemCount;
//            ItemStack typeItem = null;
//            for (int inputSlot : inputSlots) {
//                if (itemCount == 0) {
//                    break;
//                }
//                if(CargoUtil.ITEM_MODE_STACK.equals(inputItemMode) && typeItem != null) {
//                    break;
//                }
//                ItemStack inputItem = inputInv.getItem(inputSlot);
//                if (inputItem == null || CargoUtil.ifMatch(inputItem, blockMenu, filterMode, PipeGroupMenu.ITEM_MATCH) == false) {
//                    continue;
//                }
//                if (CargoUtil.ITEM_MODE_TYPE.equals(inputItemMode) && typeItem != null && !SlimefunUtils.isItemSimilar(inputItem, typeItem, true, false)) {
//                    continue;
//                }
//                for (int slot : PipeGroupMenu.INPUT_SLOTS) {
//                    if (itemCount == 0) {
//                        break;
//                    }
//                    ItemStack item = blockMenu.getItemInSlot(slot);
//                    if (item == null) {
//                        if (typeItem == null) {
//                            typeItem = new ItemStack(inputItem);
//                        }
//                        int count = Math.min(itemCount, inputItem.getAmount());
//                        blockMenu.toInventory().setItem(slot, new CustomItemStack(inputItem, count));
//                        inputItem.setAmount(inputItem.getAmount() - count);
//                        itemCount -= count;
//                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(inputItemCountMode)) {
//                            inputItemCount -= count;
//                        }
//                        if (CargoUtil.ITEM_MODE_STACK.equals(inputItemMode)) {
//                            break;
//                        }
//                    } else if (SlimefunUtils.isItemSimilar(inputItem, item, true, false)) {
//                        if (typeItem == null) {
//                            typeItem = new ItemStack(inputItem);
//                        }
//                        int count = Math.min(itemCount, Math.min(inputItem.getAmount(), item.getMaxStackSize() - item.getAmount()));
//                        CargoUtil.changeItemAmount(inputItem, item, count);
//                        itemCount -= count;
//                        if (CargoUtil.ITEM_COUNT_MODE_UNIVERSAL.equals(inputItemCountMode)) {
//                            inputItemCount -= count;
//                        }
//                        if (CargoUtil.ITEM_MODE_STACK.equals(inputItemMode)) {
//                            break;
//                        }
//                    }
//                }
//            }
//        }
    }
}
