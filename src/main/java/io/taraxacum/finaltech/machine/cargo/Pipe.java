package io.taraxacum.finaltech.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.PipeMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.cargo.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
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

    @Nonnull
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

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();
                Location location = block.getLocation();
                BlockStorage.addBlockInfo(location, "UUID", blockPlaceEvent.getPlayer().getUniqueId().toString());
                BlockStorage.addBlockInfo(location, CargoNumber.KEY, "64");
                BlockStorage.addBlockInfo(location, SlotSearchSize.KEY_INPUT, SlotSearchSize.VALUE_OUTPUTS_ONLY);
                BlockStorage.addBlockInfo(location, SlotSearchOrder.KEY_INPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(location, SlotSearchSize.KEY_OUTPUT, SlotSearchSize.VALUE_INPUTS_ONLY);
                BlockStorage.addBlockInfo(location, SlotSearchOrder.KEY_OUTPUT, SlotSearchOrder.VALUE_ASCENT);
                BlockStorage.addBlockInfo(location, FilterMode.KEY, FilterMode.VALUE_BLACK);
                BlockStorage.addBlockInfo(location, CargoMode.KEY, CargoMode.VALUE_INPUT_MAIN);
                BlockStorage.addBlockInfo(location, BlockSearchMode.KEY_INPUT, BlockSearchMode.VALUE_ZERO);
                BlockStorage.addBlockInfo(location, BlockSearchMode.KEY_OUTPUT, BlockSearchMode.VALUE_ZERO);
                BlockStorage.addBlockInfo(location, CargoItemMode.KEY, CargoItemMode.VALUE_ALL);
            }
        };
    }

    @Override
    public void tick(Block block, @Nonnull SlimefunItem slimefunItem, Config config) {
        String inputSize = config.getString(SlotSearchSize.KEY_INPUT);
        String outputSize = config.getString(SlotSearchSize.KEY_OUTPUT);
        String inputOrder = config.getString(SlotSearchOrder.KEY_INPUT);
        String outputOrder = config.getString(SlotSearchOrder.KEY_OUTPUT);

        Block inputBlock = null;
        Block outputBlock = null;

        BlockData blockData = block.getState().getBlockData();
        BlockFace blockFace;
        if (blockData instanceof Directional) {
            blockFace = ((Directional) blockData).getFacing();
            inputBlock = searchBlockPiPe(block, config.getString(BlockSearchMode.KEY_INPUT), blockFace.getOppositeFace(), true);
            outputBlock = searchBlockPiPe(block, config.getString(BlockSearchMode.KEY_OUTPUT), blockFace, false);
        }

        if (inputBlock == null || outputBlock == null) {
            return;
        }

        String uuid = config.getString("UUID");
        if(uuid != null) {
            if(!SlimefunUtil.hasPermission(inputBlock, uuid) || !SlimefunUtil.hasPermission(outputBlock, uuid)) {
                return;
            }
        }
        int cargoNumber = Integer.parseInt(config.getString(CargoNumber.KEY));
        String filterMode = config.getString(FilterMode.KEY);
        String cargoMode = config.getString(CargoMode.KEY);
        String cargoItemMode = config.getString(CargoItemMode.KEY);

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory blockInv = blockMenu.toInventory();

        CargoUtil.doCargo(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockInv, PipeMenu.ITEM_MATCH, cargoMode);
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
