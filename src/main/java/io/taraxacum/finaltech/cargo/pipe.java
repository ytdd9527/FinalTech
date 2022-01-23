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
import io.taraxacum.finaltech.menu.PipeMenu;
import io.taraxacum.finaltech.util.CargoUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import java.util.*;

public class Pipe extends FinalCargoMachine {
    private static final int MAX_SEARCH = 6;

    public Pipe(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        new PipeMenu(this.getId(), this.getItemName(), false) {
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
                BlockStorage.addBlockInfo(block, CargoUtil.ITEM_COUNT, "64");
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_SIZE, CargoUtil.SIZE_OUTPUTS_ONLY);
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_ORDER, CargoUtil.ORDER_ASCENT);
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_SIZE, CargoUtil.SIZE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_ORDER, CargoUtil.ORDER_ASCENT);
                BlockStorage.addBlockInfo(block, CargoUtil.FILTER_MODE, CargoUtil.FILTER_MODE_BLACK_LIST);
                BlockStorage.addBlockInfo(block, CargoUtil.CARGO_MODE, CargoUtil.CARGO_MODE_INPUT_MAIN);
                BlockStorage.addBlockInfo(block, CargoUtil.INPUT_BLOCK_SEARCH_MODE, CargoUtil.BLOCK_SEARCH_MODE_INHERIT);
                BlockStorage.addBlockInfo(block, CargoUtil.OUTPUT_BLOCK_SEARCH_MODE, CargoUtil.BLOCK_SEARCH_MODE_INHERIT);
            }
        });

        this.addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                BlockStorage.clearBlockInfo(blockBreakEvent.getBlock());
                if (BlockStorage.hasInventory(blockBreakEvent.getBlock())) {
                    BlockMenu blockMenu = BlockStorage.getInventory(blockBreakEvent.getBlock());
                    blockMenu.dropItems(blockMenu.getLocation(), PipeMenu.ITEM_MATCH);
                }
            }
        });
    }

    /**
     * 每粘液刻的工作逻辑
     * @param b
     */
    @Override
    public void tick(Block b) {

        Location location = b.getLocation();
        String inputSize = BlockStorage.getLocationInfo(location, CargoUtil.INPUT_SIZE);
        String outputSize = BlockStorage.getLocationInfo(location, CargoUtil.OUTPUT_SIZE);
        String inputOrder = BlockStorage.getLocationInfo(location, CargoUtil.INPUT_ORDER);
        String outputOrder = BlockStorage.getLocationInfo(location, CargoUtil.OUTPUT_ORDER);

        Block inputBlock = null;
        Block outputBlock = null;

        BlockData blockData = b.getState().getBlockData();
        BlockFace blockFace = null;
        if (blockData instanceof Directional) {
            blockFace = ((Directional) blockData).getFacing();
//            inputBlock = b.getRelative(blockFace.getOppositeFace());
            inputBlock = CargoUtil.searchBlockPiPe(b, BlockStorage.getLocationInfo(location, CargoUtil.INPUT_BLOCK_SEARCH_MODE), blockFace.getOppositeFace(), true);
//            outputBlock = b.getRelative(blockFace);
            outputBlock = CargoUtil.searchBlockPiPe(b, BlockStorage.getLocationInfo(location, CargoUtil.OUTPUT_BLOCK_SEARCH_MODE), blockFace, false);
        }

        if (inputBlock == null || outputBlock == null) {
            return;
        }

        int itemCount = Integer.parseInt(BlockStorage.getLocationInfo(location, CargoUtil.ITEM_COUNT));
        String filterMode = BlockStorage.getLocationInfo(location, CargoUtil.FILTER_MODE);
        String cargoMode = BlockStorage.getLocationInfo(location, CargoUtil.CARGO_MODE);


        BlockMenu blockMenu = BlockStorage.getInventory(b);

        // input main
        if (CargoUtil.CARGO_MODE_INPUT_MAIN.equals(cargoMode)) {
            CargoUtil.InvWithSlots inputMap = CargoUtil.getInv(inputBlock, inputSize, inputOrder);
            if(inputMap == null) {
                return;
            }
            Inventory inputInv = inputMap.getInventory();
            int[] inputSlots = inputMap.getSlots();
            if(inputInv == null || inputSlots == null) {
                return;
            }
            for (int inputSlot : inputSlots) {
                ItemStack inputItem = inputInv.getItem(inputSlot);
                if (inputItem == null) {
                    continue;
                }
                if (CargoUtil.ifMatch(inputItem, blockMenu, filterMode, PipeMenu.ITEM_MATCH) == false) {
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
                for (int outputSlot : outputSlots) {
                    if (itemCount == 0) {
                        break;
                    }
                    ItemStack outputItem = outputInv.getItem(outputSlot);
                    if (outputItem == null) {
                        int count = Math.min(itemCount, inputItem.getAmount());
                        outputInv.setItem(outputSlot, new CustomItemStack(inputItem, count));
                        inputItem.setAmount(inputItem.getAmount() - count);
                        itemCount -= count;
                    } else if (SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                        int count = Math.min(itemCount, Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount()));
                        CargoUtil.changeItemAmount(inputItem, outputItem, count);
                        itemCount -= count;
                    }
                }
                if (itemCount == 0) {
                    break;
                }
            }
        }

        // output main
        if (CargoUtil.CARGO_MODE_OUTPUT_MAIN.equals(cargoMode)) {
            CargoUtil.InvWithSlots outputMap = CargoUtil.getInv(outputBlock, outputSize, outputOrder);
            if(outputMap == null) {
                return;
            }
            Inventory outputInv = outputMap.getInventory();
            int[] outputSlots = outputMap.getSlots();
            if(outputInv == null || outputSlots == null) {
                return;
            }
            for (int outputSlot : outputSlots) {
                ItemStack outputItem = outputInv.getItem(outputSlot);
                CargoUtil.InvWithSlots inputMap = CargoUtil.getInv(inputBlock, inputSize, inputOrder, outputItem);
                if(inputMap == null) {
                    return;
                }
                Inventory inputInv = inputMap.getInventory();
                int[] inputSlots = inputMap.getSlots();
                if(inputInv == null || inputSlots == null) {
                    continue;
                }
                if (outputItem == null) {
                    for (int inputSlot : inputSlots) {
                        if (itemCount == 0) {
                            break;
                        }
                        ItemStack inputItem = inputInv.getItem(inputSlot);
                        if (inputItem == null) {
                            continue;
                        }
                        if (CargoUtil.ifMatch(inputItem, blockMenu, filterMode, PipeMenu.ITEM_MATCH) == false) {
                            continue;
                        }
                        if(outputItem == null) {
                            int count = Math.min(itemCount, inputItem.getAmount());
                            outputItem = new CustomItemStack(inputItem, count);
                            outputInv.setItem(outputSlot, outputItem);
                            inputItem.setAmount(inputItem.getAmount() - count);
                            itemCount -= count;
                        } else if(SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                            int count = Math.min(itemCount, Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount()));
                            CargoUtil.changeItemAmount(inputItem, outputItem, count);
                            itemCount -= count;
                        }
                    }
                    continue;
                }
                if (CargoUtil.ifMatch(outputItem, blockMenu, filterMode, PipeMenu.ITEM_MATCH) == false) {
                    continue;
                }
                for (int inputSlot : inputSlots) {
                    if (itemCount == 0) {
                        break;
                    }
                    ItemStack inputItem = inputInv.getItem(inputSlot);
                    if (inputItem != null && SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                        int count = Math.min(itemCount, Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount()));
                        CargoUtil.changeItemAmount(inputItem, outputItem, count);
                        itemCount -= count;
                    }
                }
                if (itemCount == 0) {
                    break;
                }
            }
        }

        // todo
        // balance mode
        if(CargoUtil.CARGO_MODE_BALANCE.equals(cargoMode)) {

        }
    }
}
