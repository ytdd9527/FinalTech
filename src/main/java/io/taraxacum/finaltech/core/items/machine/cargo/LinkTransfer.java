package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.TransferPipeMenu;
import io.taraxacum.finaltech.core.storage.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
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
import javax.annotation.Nullable;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Final_ROOT
 */
public class LinkTransfer extends AbstractCargo implements RecipeItem {
    public static final int BLOCK_SEARCH_LIMIT = 8;
    public LinkTransfer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
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
                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                FilterMode.HELPER.checkOrSetBlockStorage(location);
                CargoMode.HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.PIPE_INPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.PIPE_OUTPUT_HELPER.checkOrSetBlockStorage(location);
                CargoItemMode.HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, TransferPipeMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new TransferPipeMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config)  {
        Future<Block[]> future = null;
        if(!Bukkit.isPrimaryThread()) {
            future = Bukkit.getScheduler().callSyncMethod(LinkTransfer.this.getAddon().getJavaPlugin(), () -> {
                Block input = null;
                Block output = null;
                BlockData blockData = block.getState().getBlockData();
                if (blockData instanceof Directional) {
                    BlockFace blockFace = ((Directional) blockData).getFacing();
                    input = LinkTransfer.searchBlockPiPe(block, BlockSearchMode.PIPE_INPUT_HELPER.getOrDefaultValue(config), blockFace.getOppositeFace(), true);
                    output = LinkTransfer.searchBlockPiPe(block, BlockSearchMode.PIPE_OUTPUT_HELPER.getOrDefaultValue(config), blockFace, false);
                }
                return new Block[]{input, output};
            });
        }

        String inputSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
        String outputSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
        String inputOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);
        String outputOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);

        int cargoNumber = Integer.parseInt(config.getString(CargoNumber.KEY));
        String filterMode = FilterMode.HELPER.getOrDefaultValue(config);
        String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);
        String cargoItemMode = CargoItemMode.HELPER.getOrDefaultValue(config);

        Inventory blockInv = BlockStorage.getInventory(block).toInventory();

        Optional<Block> inputBlock = Optional.empty();
        Optional<Block> outputBlock = Optional.empty();
        if(Bukkit.isPrimaryThread()) {
            BlockData blockData = block.getState().getBlockData();
            if (blockData instanceof Directional) {
                BlockFace blockFace = ((Directional) blockData).getFacing();
                inputBlock = Optional.ofNullable(LinkTransfer.searchBlockPiPe(block, BlockSearchMode.PIPE_INPUT_HELPER.getOrDefaultValue(config), blockFace.getOppositeFace(), true));
                outputBlock = Optional.ofNullable(LinkTransfer.searchBlockPiPe(block, BlockSearchMode.PIPE_OUTPUT_HELPER.getOrDefaultValue(config), blockFace, false));
            }
        } else {
            try {
                Block[] blocks = future.get();
                inputBlock = Optional.of(blocks[0]);
                outputBlock = Optional.of(blocks[1]);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (inputBlock.isEmpty() || outputBlock.isEmpty()) {
            return;
        }

        Optional<Block> finalInputBlock = inputBlock;
        Optional<Block> finalOutputBlock = outputBlock;

        BlockTaskFactory.getInstance().registerRunnable(slimefunItem, true, (() -> {
            String uuid = config.getString("UUID");
            if (uuid != null) {
                if (!SlimefunUtil.hasPermission(uuid, finalInputBlock.get(), Interaction.INTERACT_BLOCK) || !SlimefunUtil.hasPermission(uuid, finalOutputBlock.get(), Interaction.INTERACT_BLOCK)) {
                    return;
                }
            }

            CargoUtil.doCargo(finalInputBlock.get(), finalOutputBlock.get(), inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockInv, TransferPipeMenu.ITEM_MATCH, cargoMode);
        }), block.getLocation(), inputBlock.get().getLocation(), outputBlock.get().getLocation());
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Nullable
    private static Block searchBlockPiPe(@Nonnull Block begin, @Nonnull String searchMode, @Nonnull BlockFace blockFace, boolean input) {
        Block result = begin.getRelative(blockFace);
        int count = 1;
        if (BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            return result;
        }
        List<Location> locationList = new ArrayList<>();
        locationList.add(begin.getLocation());
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
