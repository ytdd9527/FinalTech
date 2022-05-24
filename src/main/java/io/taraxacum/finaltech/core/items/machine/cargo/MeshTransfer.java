package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.api.interfaces.PerformanceLimitMachine;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.TransferStationMenu;
import io.taraxacum.finaltech.core.storage.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.api.dto.PositionHelper;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
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
public class MeshTransfer extends AbstractCargo implements RecipeItem, PerformanceLimitMachine {
    public MeshTransfer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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

                BlockStorage.addBlockInfo(block, CargoNumber.KEY_INPUT, "64");
                BlockStorage.addBlockInfo(block, CargoCountMode.KEY_INPUT, CargoCountMode.VALUE_UNIVERSAL);
                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.INPUT_HELPER.checkOrSetBlockStorage(location);
                CargoItemMode.INPUT_HELPER.checkOrSetBlockStorage(location);
                BlockStorage.addBlockInfo(block, CargoNumber.KEY_OUTPUT, "64");
                BlockStorage.addBlockInfo(block, CargoCountMode.KEY_OUTPUT, CargoCountMode.VALUE_UNIVERSAL);
                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                CargoItemMode.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                FilterMode.HELPER.checkOrSetBlockStorage(location);
                BlockStorage.addBlockInfo(block, PositionInfo.KEY, "");
                BlockSearchMode.STATION_INPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.STATION_OUTPUT_HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, TransferStationMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new TransferStationMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    public void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        // do something now?
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        Inventory blockInv = blockMenu.toInventory();
        String filterMode = FilterMode.HELPER.getOrDefaultValue(config);
        String locationInfo = config.getString(PositionInfo.KEY);
        PositionHelper positionHelper = new PositionHelper(locationInfo);

        // do output
        String outputSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
        String outputOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);
        int outputCargoNumber = Integer.parseInt(config.getString(CargoNumber.KEY_OUTPUT));
        String outputCountMode = config.getString(CargoCountMode.KEY_OUTPUT);
        String outputItemMode = CargoItemMode.OUTPUT_HELPER.getOrDefaultValue(config);
        String[] outputs = positionHelper.getOutputs();

        for (String outputPosition : outputs) {
            BlockFace blockFace = PositionInfo.getBlockFaceByPosition(outputPosition);
            Block outputBlock = MeshTransfer.searchBlock(block, blockFace, BlockSearchMode.STATION_OUTPUT_HELPER.getOrDefaultValue(config));
            if (outputBlock == null) {
                continue;
            }
            int cargoNumber = outputCargoNumber;
            int result = CargoUtil.doCargoInputMain(block, outputBlock, SlotSearchSize.VALUE_OUTPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, outputSize, outputOrder, cargoNumber, outputItemMode, filterMode, blockInv, TransferStationMenu.ITEM_MATCH);
            if (CargoCountMode.VALUE_UNIVERSAL.equals(outputCountMode)) {
                outputCargoNumber -= result;
                if(outputCargoNumber == 0) {
                    break;
                }
            }
        }

        // do move
        for (int i = 0; i < TransferStationMenu.INPUT_SLOT.length; i++) {
            ItemStack inputItem = blockMenu.getItemInSlot(TransferStationMenu.INPUT_SLOT[i]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            ItemStack outputItem = blockMenu.getItemInSlot(TransferStationMenu.OUTPUT_SLOT[i]);
            if (ItemStackUtil.isItemNull(outputItem)) {
                blockMenu.toInventory().setItem(TransferStationMenu.OUTPUT_SLOT[i], new ItemStack(inputItem));
                inputItem.setAmount(0);
            } else if (ItemStackUtil.isItemSimilar(inputItem, outputItem)) {
                ItemStackUtil.stack(inputItem, outputItem);
            }
        }

        // do input
        String inputSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
        String inputOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);
        int inputCargoNumber = Integer.parseInt(config.getString(CargoNumber.KEY_INPUT));
        String inputItemCountMode = config.getString(CargoCountMode.KEY_INPUT);
        String inputItemMode = CargoItemMode.INPUT_HELPER.getOrDefaultValue(config);
        String[] inputs = positionHelper.getInputs();

        for (String inputPosition : inputs) {
            BlockFace blockFace = PositionInfo.getBlockFaceByPosition(inputPosition);
            Block inputBlock = MeshTransfer.searchBlock(block, blockFace, BlockSearchMode.STATION_INPUT_HELPER.getOrDefaultValue(config));
            if (inputBlock == null) {
                continue;
            }
            int cargoNumber = inputCargoNumber;
            int result = CargoUtil.doCargoOutputMain(inputBlock, block, inputSize, inputOrder, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, cargoNumber, inputItemMode, filterMode, blockInv, TransferStationMenu.ITEM_MATCH);
            if (CargoCountMode.VALUE_UNIVERSAL.equals(inputItemCountMode)) {
                inputCargoNumber -= result;
                if(inputCargoNumber == 0) {
                    break;
                }
            }
        }
    }

    public static Block searchBlock(@Nonnull Block begin, @Nonnull BlockFace blockFace, @Nonnull String searchMode) {
        Block result = begin.getRelative(blockFace);
        if (BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            return result;
        }
        while(true) {
            if (result.getType() == Material.CHAIN) {
                result = result.getRelative(blockFace);
                continue;
            }
            if (BlockSearchMode.VALUE_PENETRATE.equals(searchMode) && BlockStorage.hasInventory(result) && BlockStorage.getInventory(result).getPreset().getID().equals(FinalTechItems.TRANSFER_STATION.getItemId())) {
                result = result.getRelative(blockFace);
                continue;
            }
            break;
        }
        return result;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f基础功能",
                "",
                "&f该机器会不断把物品",
                "&f1.从自身的输出槽物品传出到输出侧容器",
                "&f2.把自身的输入槽物品移到输出槽",
                "&f3.从输入侧容器取出物品到自身输入槽");
        this.registerDescriptiveRecipe("&f相关概念",
                "",
                "&f输入侧",
                "&f该机器搜索方向上的某一方块",
                "&f该方块的物品将被取出",
                "&f需通过设置方向以指定输入侧容器位置",
                "",
                "&f输出侧",
                "&f该机器搜索方向上的某一方块",
                "&f该方块将被传入物品",
                "&f需通过设置方向以指定输出侧容器位置");
        this.registerDescriptiveRecipe("&f传输模式",
                "",
                "&f对称传输",
                "&f会把物品按照在输入侧容器的格子顺序",
                "&f传输到输出侧容器对应的格子位置上",
                "&f该机器转移自身输入槽和输出槽物品时所用的模式",
                "",
                "&f主输入侧",
                "&f尝试把输入侧的物品一个一个地",
                "&f传输到输出侧容器的各个格子上",
                "&f该机器把物品传出到输出侧容器时所用的模式",
                "",
                "&f主输出侧",
                "&f尝试按照输出侧容器的格子顺序",
                "&f一个一个地从输入侧容器取出物品",
                "&f该机器把物品从输入侧容器取出时所用的模式");
        this.registerDescriptiveRecipe("&f搜索模式",
                "",
                "&f零模式",
                "&f搜索范围锁定为自身指定方向上相邻一格的方块",
                "",
                "&f穿透模式",
                "&f当该机器指向了相同机器方块",
                "&f跨过其所在位置",
                "&f并继续搜索",
                "",
                "&f链接模式",
                "&f当该机器指向了相同机器方块",
                "&f在其所在位置处停止搜索");
        this.registerDescriptiveRecipe("&f数量限制模式",
                "",
                "&f通用模式",
                "&f输入侧/输出侧传输物品时",
                "&f每个容器使用共通的总计数量限制",
                "",
                "&f独立模式",
                "&f输入侧/输出侧传输物品时",
                "&f每个容器的数量限制独立计算互不影响");
        this.registerDescriptiveRecipe("&f搜索顺序",
                "",
                "&f该机器会按照输入侧/输出侧的设定顺序",
                "&f搜索其对应方向的方块容器",
                "&f设定顺序会以物品数量的形式显示");
    }
}
