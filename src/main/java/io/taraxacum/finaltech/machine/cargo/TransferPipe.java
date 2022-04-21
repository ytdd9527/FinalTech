package io.taraxacum.finaltech.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.interfaces.PerformanceLimitMachine;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.function.TransferPipeMenu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.menu.*;
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
public class TransferPipe extends AbstractCargo implements RecipeItem, PerformanceLimitMachine {
    public static final int BLOCK_SEARCH_LIMIT = 8;
    public TransferPipe(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new TransferPipeMenu(this.getId(), this.getItemName(), this);
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
                    inv.dropItems(block.getLocation(), TransferPipeMenu.ITEM_MATCH);
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
                BlockStorage.addBlockInfo(location, CargoMode.KEY, CargoMode.VALUE_SYMMETRY);
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
        if (uuid != null) {
            if (!SlimefunUtil.hasPermission(inputBlock, uuid) || !SlimefunUtil.hasPermission(outputBlock, uuid)) {
                return;
            }
        }
        int cargoNumber = Integer.parseInt(config.getString(CargoNumber.KEY));
        String filterMode = config.getString(FilterMode.KEY);
        String cargoMode = config.getString(CargoMode.KEY);
        String cargoItemMode = config.getString(CargoItemMode.KEY);

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory blockInv = blockMenu.toInventory();

        CargoUtil.doCargo(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockInv, TransferPipeMenu.ITEM_MATCH, cargoMode);
    }

    public static Block searchBlockPiPe(@Nonnull Block begin, String searchMode, BlockFace blockFace, boolean input) {
        Block result = begin.getRelative(blockFace);
        int count = 1;
        if (BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            return result;
        }
        List<Location> locationList = new ArrayList<>();
        while(true) {
            if (BlockStorage.hasInventory(result) && !result.getType().equals(FinalTechItems.TRANSFER_PIPE.getType())) {
                break;
            }
            if (PaperLib.getBlockState(result, false).getState() instanceof InventoryHolder) {
                break;
            }
            if (result.getType() == FinalTechItems.TRANSFER_PIPE.getType()) {
                count = 0;
                for (Location location : locationList) {
                    if (location.equals(result.getLocation())) {
                        return result;
                    }
                }
                locationList.add(result.getLocation());
                if (BlockSearchMode.VALUE_INHERIT.equals(searchMode)) {
                    BlockData blockData = result.getState().getBlockData();
                    if (blockData instanceof Directional) {
                        blockFace = ((Directional) blockData).getFacing();
                        if (input) {
                            blockFace = blockFace.getOppositeFace();
                        }
                    }
                }
            }
            result = result.getRelative(blockFace);
            if (count++ > BLOCK_SEARCH_LIMIT) {
                return null;
            }
        }
        return result;
    }

    /**
     * 默认的配方注册方法
     * 继承了该接口的实现类应该重写该方法
     * 并在该方法内实现注册机器的工作配方
     */
    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f基础功能",
                "",
                "&f该机器会不断把物品",
                "&f从输入侧方块的容器",
                "&f传输到输出侧方块的容器");
        this.registerDescriptiveRecipe("&f相关概念",
                "",
                "&f输入侧",
                "&f该机器尾部所指向的方块",
                "&f该方块的物品将被取出",
                "",
                "&f输出侧",
                "&f该机器头部所指向的方块",
                "&f该方块将被传入物品");
        this.registerDescriptiveRecipe("&f传输模式",
                "",
                "&f对称传输",
                "&f会把物品按照在输入侧容器的格子顺序",
                "&f传输到输出侧容器对应的格子位置上",
                "",
                "&f主输入侧",
                "&f尝试把输入侧的物品一个一个地",
                "&f传输到输出侧容器的各个格子上",
                "",
                "&f主输出侧",
                "&f尝试按照输出侧容器的格子顺序",
                "&f一个一个地从输入侧容器取出物品");
        this.registerDescriptiveRecipe("&f搜索模式",
                "",
                "&f零模式",
                "&f输入侧/输出侧锁定为该机器头部/尾部指向的相邻一格的方块",
                "",
                "&f继承模式",
                "&f当该机器头部/尾部指向了同材质（末地烛）方块",
                "&f继承其头部/尾部的方向",
                "&f重置最大搜索距离并继续搜索",
                "",
                "&f穿透模式",
                "&f当该机器头部/尾部指向了同材质（末地烛）方块",
                "&f无视其头部/尾部的方向",
                "&f重置最大搜索距离并继续搜索",
                "",
                "&f最大搜索距离为" + BLOCK_SEARCH_LIMIT + "格");
    }
}
