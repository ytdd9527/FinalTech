package io.taraxacum.finaltech.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.abstractItem.machine.AbstractCargo;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.PipeMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.cargo.*;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import java.util.*;

/**
 * @author Final_ROOT
 */
public class Pipe extends AbstractCargo {
    public static final int BLOCK_SEARCH_LIMIT = 8;
    public Pipe(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new PipeMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            @Override
            public void onBlockBreak(@Nonnull Block block) {
                BlockMenu inv = BlockStorage.getInventory(block);
                if (inv != null) {
                    inv.dropItems(block.getLocation(), getInputSlots());
                    inv.dropItems(block.getLocation(), getOutputSlots());
                    inv.dropItems(block.getLocation(), PipeMenu.ITEM_MATCH);
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
                BlockStorage.addBlockInfo(block, "UUID", blockPlaceEvent.getPlayer().getUniqueId().toString());
                BlockStorage.addBlockInfo(block, CargoNumber.KEY, "64");
                BlockStorage.addBlockInfo(block, SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
                BlockStorage.addBlockInfo(block, SlotSearchOrder.KEY_INPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(block, SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(block, SlotSearchOrder.KEY_OUTPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(block, FilterMode.KEY, FilterMode.VALUE_BLACK);
                BlockStorage.addBlockInfo(block, CargoMode.KEY, CargoMode.VALUE_INPUT_MAIN);
                BlockStorage.addBlockInfo(block, BlockSearchMode.KEY_INPUT, BlockSearchMode.VALUE_ZERO);
                BlockStorage.addBlockInfo(block, BlockSearchMode.KEY_OUTPUT, BlockSearchMode.VALUE_ZERO);
            }
        });
    }

    /**
     * 每粘液刻的工作逻辑
     * @param
     */
    @Override
    public void tick(Block block) {
        Location location = block.getLocation();
        String inputSize = BlockStorage.getLocationInfo(location, SlotSearchSize.KEY_INPUT);
        String outputSize = BlockStorage.getLocationInfo(location, SlotSearchSize.KEY_OUTPUT);
        String inputOrder = BlockStorage.getLocationInfo(location, SlotSearchOrder.KEY_INPUT);
        String outputOrder = BlockStorage.getLocationInfo(location, SlotSearchOrder.KEY_OUTPUT);

        Block inputBlock = null;
        Block outputBlock = null;

        BlockData blockData = block.getState().getBlockData();
        BlockFace blockFace;
        if (blockData instanceof Directional) {
            blockFace = ((Directional) blockData).getFacing();
            inputBlock = searchBlockPiPe(block, BlockStorage.getLocationInfo(location, BlockSearchMode.KEY_INPUT), blockFace.getOppositeFace(), true);
            outputBlock = searchBlockPiPe(block, BlockStorage.getLocationInfo(location, BlockSearchMode.KEY_OUTPUT), blockFace, false);
        }

        if (inputBlock == null || outputBlock == null) {
            return;
        }

        String uuid = BlockStorage.getLocationInfo(location, "UUID");
        if(uuid != null) {
            if(!SlimefunUtil.hasPermission(inputBlock, uuid) || !SlimefunUtil.hasPermission(outputBlock, uuid)) {
                return;
            }
        }
        int cargoNumber = Integer.parseInt(BlockStorage.getLocationInfo(location, CargoNumber.KEY));
        String filterMode = BlockStorage.getLocationInfo(location, FilterMode.KEY);
        String cargoMode = BlockStorage.getLocationInfo(location, CargoMode.KEY);

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory blockInv = blockMenu.toInventory();

        // input main
        if (CargoMode.VALUE_INPUT_MAIN.equals(cargoMode)) {
            CargoUtil.doCargoInputMain(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, CargoItemMode.VALUE_ALL, filterMode, blockInv, PipeMenu.ITEM_MATCH);
        }

        // output main
        if (CargoMode.VALUE_OUTPUT_MAIN.equals(cargoMode)) {
            CargoUtil.doCargoOutputMain(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, CargoItemMode.VALUE_ALL, filterMode, blockInv, PipeMenu.ITEM_MATCH);
        }
    }

    public static Block searchBlockPiPe(@Nonnull Block begin, String searchMode, BlockFace blockFace, boolean input) {
        Block result = begin.getRelative(blockFace);
        int count = 1;
        if(BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            return result;
        }
        List<Location> locationList = new ArrayList<>();
        while(true) {
            if(BlockStorage.hasInventory(result) && !result.getType().equals(FinalTechItems.PIPE.getType())) {
                break;
            }
            if(PaperLib.getBlockState(result, false).getState() instanceof InventoryHolder) {
                break;
            }
            if (result.getType() == FinalTechItems.PIPE.getType()) {
                count = 0;
                for(Location location : locationList) {
                    if(location.equals(result.getLocation())) {
                        return result;
                    }
                }
                locationList.add(result.getLocation());
                if(BlockSearchMode.VALUE_INHERIT.equals(searchMode)){
                    BlockData blockData = result.getState().getBlockData();
                    if(blockData instanceof Directional) {
                        blockFace = ((Directional) blockData).getFacing();
                        if(input) {
                            blockFace = blockFace.getOppositeFace();
                        }
                    }
                }
            }
            result = result.getRelative(blockFace);
            if(count++ > BLOCK_SEARCH_LIMIT) {
                return null;
            }
        }
        return result;
    }
}
