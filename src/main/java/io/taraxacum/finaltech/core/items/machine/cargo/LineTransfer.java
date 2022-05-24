package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.TransferLineMenu;
import io.taraxacum.finaltech.core.storage.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.MachineUtil;
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
public class LineTransfer extends AbstractCargo implements RecipeItem {
    public LineTransfer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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

                BlockStorage.addBlockInfo(location, CargoNumber.KEY, "64");

                BlockCargoOrder.HELPER.checkOrSetBlockStorage(location);
                BlockSearchCycle.HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.LINE_HELPER.checkOrSetBlockStorage(location);
                BlockSearchOrder.HELPER.checkOrSetBlockStorage(location);
                BlockSearchSelf.HELPER.checkOrSetBlockStorage(location);
                CargoMode.HELPER.checkOrSetBlockStorage(location);
                FilterMode.HELPER.checkOrSetBlockStorage(location);
                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                CargoItemMode.HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, TransferLineMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new TransferLineMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);

        BlockFace blockFace = null;
        BlockData blockData = block.getState().getBlockData();
        if (blockData instanceof Directional) {
            blockFace = ((Directional) blockData).getFacing();
        }
        if (blockFace == null) {
            return;
        }
        List<Block> blockList = new ArrayList<>();
        if (BlockSearchSelf.VALUE_TRUE.equals(config.getString(BlockSearchSelf.KEY))) {
            blockList.add(0, block);
        }
        blockList.addAll(LineTransfer.searchBlock(block, blockFace, BlockSearchMode.LINE_HELPER.getOrDefaultValue(config)));

        if (BlockSearchOrder.VALUE_REVERSE.equals(BlockSearchOrder.HELPER.getOrDefaultValue(config))) {
            blockList = new JavaUtil<Block>().reserve(blockList);
        }

        if (BlockSearchCycle.VALUE_TRUE.equals(BlockSearchCycle.HELPER.getOrDefaultValue(config))) {
            blockList.add(blockList.get(0));
        }

        String blockCargoOrder = BlockCargoOrder.HELPER.getOrDefaultValue(config);
        String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);
        String filterMode = FilterMode.HELPER.getOrDefaultValue(config);
        String inputSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
        String inputOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);
        String outputSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
        String outputOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);
        String cargoItemMode = CargoItemMode.HELPER.getOrDefaultValue(config);
        int cargoNumber = Integer.parseInt(CargoNumber.KEY);

        for (int i = 0, size = blockList.size(); i < size - 1; i++) {
            Block inputBlock = blockList.get(i);
            Block outputBlock = blockList.get((i + 1) % size);
            if (BlockCargoOrder.VALUE_POSITIVE.equals(blockCargoOrder)) {
                CargoUtil.doCargo(inputBlock, outputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockMenu.toInventory(), TransferLineMenu.ITEM_MATCH, cargoMode);
            } else if (BlockCargoOrder.VALUE_REVERSE.equals(blockCargoOrder)) {
                CargoUtil.doCargo(outputBlock, inputBlock, inputSize, inputOrder, outputSize, outputOrder, cargoNumber, cargoItemMode, filterMode, blockMenu.toInventory(), TransferLineMenu.ITEM_MATCH, cargoMode);
            }
        }
    }

    public static List<Block> searchBlock(@Nonnull Block begin, @Nonnull BlockFace blockFace, @Nonnull String blockSearchMode) {
        List<Block> list = new ArrayList<>();
        Block block = begin.getRelative(blockFace);
        if (BlockSearchMode.VALUE_ZERO.equals(blockSearchMode)) {
            if (CargoUtil.hasInventory(block)) {
                list.add(block);
            }
            block = block.getRelative(blockFace);
            if (CargoUtil.hasInventory(block)) {
                list.add(block);
            }
            return list;
        }
        while (CargoUtil.hasInventory(block)) {
            if (BlockStorage.hasInventory(block) && BlockStorage.getInventory(block).getPreset().getID().equals(FinalTechItems.TRANSFER_LINE.getItemId())) {
                if (BlockSearchMode.VALUE_PENETRATE.equals(blockSearchMode)) {
                    block = block.getRelative(blockFace);
                    continue;
                } else if (BlockSearchMode.VALUE_INTERRUPT.equals(blockSearchMode)) {
                    list.add(block);
                    break;
                }
            }
            list.add(block);
            block = block.getRelative(blockFace);
        }
        return list;
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
                "&f该机器搜索方向上的某一方块",
                "&f该方块的物品将被取出",
                "",
                "&f输出侧",
                "&f该机器搜索方向上的下一方块",
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
                "&f搜索范围锁定为自身前方两格的方块",
                "",
                "&f中断模式",
                "&f当该机器指向了相同机器方块",
                "&f在其所在位置处停止搜索",
                "",
                "&f穿透模式",
                "&f当该机器指向了相同机器方块",
                "&f跨过其所在位置",
                "&f并继续搜索");
        this.registerDescriptiveRecipe("&f运输模式",
                "",
                "&f首尾循环运输",
                "&f让搜索方向上的最后一个方块",
                "&f与第一个方块进行运输",
                "",
                "&f自运输",
                "&f是否将自身加入到搜索范围中");
    }
}
