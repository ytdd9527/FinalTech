package io.taraxacum.finaltech.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractCargo;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.TransferStationMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.PositionHelper;
import io.taraxacum.finaltech.util.cargo.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class TransferStation extends AbstractCargo {
    public TransferStation(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new TransferStationMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            @Override
            public void onBlockBreak(@Nonnull Block block) {
                BlockStorage.clearBlockInfo(block);
                BlockMenu inv = BlockStorage.getInventory(block);
                if (inv != null) {
                    inv.dropItems(inv.getLocation(), TransferStationMenu.ITEM_MATCH);
                    inv.dropItems(inv.getLocation(), TransferStationMenu.INPUT_SLOTS);
                    inv.dropItems(inv.getLocation(), TransferStationMenu.OUTPUT_SLOTS);
                    BlockStorage.clearBlockInfo(block.getLocation());
                }
            }
        };
    }

    @Override
    public void preRegister() {
        super.preRegister();
        this.addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();
                BlockStorage.addBlockInfo(block, CargoNumber.KEY_INPUT, "64");
                BlockStorage.addBlockInfo(block, SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
                BlockStorage.addBlockInfo(block, SlotSearchOrder.KEY_INPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(block, CargoCountMode.KEY_INPUT, CargoCountMode.VALUE_UNIVERSAL);
                BlockStorage.addBlockInfo(block, CargoItemMode.KEY_INPUT, CargoItemMode.VALUE_ALL);
                BlockStorage.addBlockInfo(block, CargoNumber.KEY_OUTPUT, "64");
                BlockStorage.addBlockInfo(block, SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(block, SlotSearchOrder.KEY_OUTPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(block, CargoCountMode.KEY_OUTPUT, CargoCountMode.VALUE_UNIVERSAL);
                BlockStorage.addBlockInfo(block, CargoItemMode.KEY_OUTPUT, CargoItemMode.VALUE_ALL);
                BlockStorage.addBlockInfo(block, FilterMode.KEY, FilterMode.VALUE_BLACK);
                BlockStorage.addBlockInfo(block, PositionInfo.KEY, "");
                BlockStorage.addBlockInfo(block, BlockSearchMode.KEY_INPUT, BlockSearchMode.VALUE_ZERO);
                BlockStorage.addBlockInfo(block, BlockSearchMode.KEY_OUTPUT, BlockSearchMode.VALUE_ZERO);
            }
        });
    }

    @Override
    public void tick(Block block) {
        // do something now?
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        String filterMode = BlockStorage.getLocationInfo(location, FilterMode.KEY);
        String locationInfo = BlockStorage.getLocationInfo(location, PositionInfo.KEY);
        PositionHelper positionHelper = new PositionHelper(locationInfo);
        Inventory blockInv = blockMenu.toInventory();

        // do output
        String outputSize = BlockStorage.getLocationInfo(location, SlotSearchSize.KEY_OUTPUT);
        String outputOrder = BlockStorage.getLocationInfo(location, SlotSearchOrder.KEY_OUTPUT);
        int outputCargoNumber = Integer.parseInt(BlockStorage.getLocationInfo(location, CargoNumber.KEY_OUTPUT));
        String outputCountMode = BlockStorage.getLocationInfo(location, CargoCountMode.KEY_OUTPUT);
        String outputItemMode = BlockStorage.getLocationInfo(location, CargoItemMode.KEY_OUTPUT);
        String[] outputs = positionHelper.getOutputs();

        for(String outputPosition : outputs) {
            BlockFace blockFace = PositionInfo.getBlockFaceByPosition(outputPosition);
            Block outputBlock = searchBlock(block, BlockStorage.getLocationInfo(location, BlockSearchMode.KEY_OUTPUT), blockFace);
            if(outputBlock == null) {
                continue;
            }
            int cargoNumber = outputCargoNumber;
            int result = CargoUtil.doCargoInputMain(block, outputBlock, SlotSearchSize.VALUE_OUTPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, outputSize, outputOrder, cargoNumber, outputItemMode, filterMode, blockInv, TransferStationMenu.ITEM_MATCH);
            if(CargoCountMode.VALUE_UNIVERSAL.equals(outputCountMode)) {
                outputCargoNumber -= result;
            }
        }

        // do move
        for(int i = 0; i < TransferStationMenu.INPUT_SLOTS.length; i++) {
            ItemStack inputItem = blockMenu.getItemInSlot(TransferStationMenu.INPUT_SLOTS[i]);
            if(inputItem == null) {
                continue;
            }
            ItemStack outputItem = blockMenu.getItemInSlot(TransferStationMenu.OUTPUT_SLOTS[i]);
            if(outputItem == null) {
                blockMenu.toInventory().setItem(TransferStationMenu.OUTPUT_SLOTS[i], new ItemStack(inputItem));
                inputItem.setAmount(0);
            } else if(SlimefunUtils.isItemSimilar(inputItem, outputItem, true, false)) {
                int count = Math.min(inputItem.getAmount(), outputItem.getMaxStackSize() - outputItem.getAmount());
                inputItem.setAmount(inputItem.getAmount() - count);
                outputItem.setAmount(outputItem.getAmount() + count);
            }
        }

        // do input
        String inputSize = BlockStorage.getLocationInfo(location, SlotSearchSize.KEY_INPUT);
        String inputOrder = BlockStorage.getLocationInfo(location, SlotSearchOrder.KEY_INPUT);
        int inputCargoNumber = Integer.parseInt(BlockStorage.getLocationInfo(location, CargoNumber.KEY_INPUT));
        String inputItemCountMode = BlockStorage.getLocationInfo(location, CargoCountMode.KEY_INPUT);
        String inputItemMode = BlockStorage.getLocationInfo(location, CargoItemMode.KEY_INPUT);
        String[] inputs = positionHelper.getInputs();

        for(String inputPosition : inputs) {
            BlockFace blockFace = PositionInfo.getBlockFaceByPosition(inputPosition);
            Block inputBlock = searchBlock(block, BlockStorage.getLocationInfo(location, BlockSearchMode.KEY_INPUT), blockFace);
            if(inputBlock == null) {
                continue;
            }
            int cargoNumber = inputCargoNumber;
            int result = CargoUtil.doCargoOutputMain(inputBlock, block, inputSize, inputOrder, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, cargoNumber, inputItemMode, filterMode, blockInv, TransferStationMenu.ITEM_MATCH);
            if(CargoCountMode.VALUE_UNIVERSAL.equals(inputItemCountMode)) {
                inputCargoNumber -= result;
            }
        }
    }

    public static Block searchBlock(@Nonnull Block begin, String searchMode, BlockFace blockFace) {
        Block result = begin.getRelative(blockFace);
        if(BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            return result;
        }
        while(true) {
            if(result.getType() == Material.CHAIN) {
                result = result.getRelative(blockFace);
                continue;
            }
            if(result.getType() == FinalTechItems.TRANSFER_STATION.getType() && BlockStorage.hasInventory(result) && BlockSearchMode.VALUE_PENETRATE.equals(searchMode) && BlockStorage.getInventory(result).getPreset().getID().equals(FinalTechItems.TRANSFER_STATION.getItemId())) {
                result = result.getRelative(blockFace);
                continue;
            }
            break;
        }
        return result;
    }
}
