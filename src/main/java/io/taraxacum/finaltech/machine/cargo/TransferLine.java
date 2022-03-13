package io.taraxacum.finaltech.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.TransferLineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.JavaUtil;
import io.taraxacum.finaltech.util.cargo.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class TransferLine extends AbstractCargo {
    public TransferLine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new TransferLineMenu(this.getId(), this.getItemName(), this);
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
                    inv.dropItems(inv.getLocation(), TransferLineMenu.ITEM_MATCH);
                    inv.dropItems(inv.getLocation(), TransferLineMenu.INPUT_SLOTS);
                    inv.dropItems(inv.getLocation(), TransferLineMenu.OUTPUT_SLOTS);
                    BlockStorage.clearBlockInfo(block.getLocation());
                }
            }
        };
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();
                Location location = block.getLocation();
                BlockStorage.addBlockInfo(location, CargoNumber.KEY, "64");
                BlockStorage.addBlockInfo(location, BlockCargoOrder.KEY, BlockCargoOrder.VALUE_POSITIVE);
                BlockStorage.addBlockInfo(location, BlockSearchCycle.KEY, BlockSearchCycle.VALUE_FALSE);
                BlockStorage.addBlockInfo(location, BlockSearchMode.KEY, BlockSearchMode.VALUE_ZERO);
                BlockStorage.addBlockInfo(location, BlockSearchOrder.KEY, BlockSearchOrder.VALUE_POSITIVE);
                BlockStorage.addBlockInfo(location, BlockSearchSelf.KEY, BlockSearchSelf.VALUE_FALSE);
                BlockStorage.addBlockInfo(location, CargoMode.KEY, CargoMode.VALUE_INPUT_MAIN);
                BlockStorage.addBlockInfo(location, FilterMode.KEY, FilterMode.VALUE_BLACK);
                BlockStorage.addBlockInfo(location, SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
                BlockStorage.addBlockInfo(location, SlotSearchOrder.KEY_INPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(location, SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(location, SlotSearchOrder.KEY_OUTPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(block, CargoItemMode.KEY, CargoItemMode.VALUE_ALL);
            }
        };
    }

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);

        BlockFace blockFace = null;
        BlockData blockData = block.getState().getBlockData();
        if(blockData instanceof Directional) {
            blockFace = ((Directional) blockData).getFacing();
        }
        if(blockFace == null) {
            return;
        }
        String blockSearchMode = config.getString(BlockSearchMode.KEY);
        List<Block> blockList = new ArrayList<>();
        String blockSearchSelf = config.getString(BlockSearchSelf.KEY);
        if(BlockSearchSelf.VALUE_TRUE.equals(blockSearchSelf)) {
            blockList.add(0, block);
        }
        for(Block searchBlock : searchBlock(block, blockSearchMode, blockFace)) {
            blockList.add(searchBlock);
        }

        String blockSearchOrder = config.getString(BlockSearchOrder.KEY);
        if(BlockSearchOrder.VALUE_REVERSE.equals(blockSearchOrder)) {
            blockList = new JavaUtil<Block>().reserve(blockList);
        }

        String blockCargoOrder = config.getString(BlockCargoOrder.KEY);
        String cargoMode = config.getString(CargoMode.KEY);
        String filterMode = config.getString(FilterMode.KEY);
        String inputSize = config.getString(SlotSearchSize.KEY_INPUT);
        String inputOrder = config.getString(SlotSearchOrder.KEY_INPUT);
        String outputSize = config.getString(SlotSearchSize.KEY_OUTPUT);
        String outputOrder = config.getString(SlotSearchOrder.KEY_OUTPUT);
        String cargoItemMode = config.getString(CargoItemMode.KEY);
        int cargoNumber = Integer.parseInt(config.getString(CargoNumber.KEY));

        int size = blockList.size();
        for (int i = 0; i < size - 1; i++) {
            Block inputBlock = blockList.get(i);
            Block outputBlock = blockList.get((i + 1) % size);
            if(BlockCargoOrder.VALUE_POSITIVE.equals(blockCargoOrder)) {
                CargoUtil.doCargo(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockMenu.toInventory(), TransferLineMenu.ITEM_MATCH, cargoMode);
            } else if(BlockCargoOrder.VALUE_REVERSE.equals(blockCargoOrder)) {
                CargoUtil.doCargo(outputBlock, inputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockMenu.toInventory(), TransferLineMenu.ITEM_MATCH, cargoMode);
            }
        }

        String blockSearchCycle = config.getString(BlockSearchCycle.KEY);
        if(BlockSearchCycle.VALUE_TRUE.equals(blockSearchCycle)) {
            if(BlockCargoOrder.VALUE_POSITIVE.equals(blockCargoOrder)) {
                CargoUtil.doCargo(blockList.get(size - 1), blockList.get(0), inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockMenu.toInventory(), TransferLineMenu.ITEM_MATCH, cargoMode);
            } else if(BlockCargoOrder.VALUE_REVERSE.equals(blockCargoOrder)) {
                CargoUtil.doCargo(blockList.get(0), blockList.get(size - 1), inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockMenu.toInventory(), TransferLineMenu.ITEM_MATCH, cargoMode);
            }
        }
    }

    public static List<Block> searchBlock(@Nonnull Block begin, String blockSearchMode, BlockFace blockFace) {
        List<Block> list = new ArrayList<>();
        if(BlockSearchMode.VALUE_ZERO.equals(blockSearchMode)) {
            if(CargoUtil.hasInventory(begin.getRelative(blockFace))) {
                list.add(begin.getRelative(blockFace));
            }
            Block block = begin.getRelative(blockFace).getRelative(blockFace);
            if(CargoUtil.hasInventory(block)) {
                list.add(block);
            }
            return list;
        }
        Block block = begin.getRelative(blockFace);
        while (CargoUtil.hasInventory(block)) {
            if (BlockStorage.hasInventory(block) && BlockStorage.getInventory(block).getPreset().getID().equals(FinalTechItems.TRANSFER_LINE.getItemId())) {
                if(BlockSearchMode.VALUE_PENETRATE.equals(blockSearchMode)) {
                    block = block.getRelative(blockFace);
                    continue;
                } else if(BlockSearchMode.VALUE_INTERRUPT.equals(blockSearchMode)) {
                    list.add(block);
                    break;
                }
            }
            list.add(block);
            block = block.getRelative(blockFace);
        }
        return list;
    }
}
