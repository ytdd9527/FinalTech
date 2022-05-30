package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.annotation.Translate;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.PerformanceLimitMachine;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.StationTransferMenu;
import io.taraxacum.finaltech.core.storage.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.api.dto.PositionHelper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class MeshTransfer extends AbstractCargo implements RecipeItem, PerformanceLimitMachine {
    private static final double PARTICLE_DISTANCE = 0.22;

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

                CargoFilter.HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.STATION_INPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.STATION_OUTPUT_HELPER.checkOrSetBlockStorage(location);

                CargoNumber.INPUT_HELPER.checkOrSetBlockStorage(location);
                CargoNumberMode.INPUT_HELPER.getOrDefaultValue(location);
                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.INPUT_HELPER.checkOrSetBlockStorage(location);
                CargoLimit.INPUT_HELPER.checkOrSetBlockStorage(location);

                CargoNumber.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                CargoNumberMode.OUTPUT_HELPER.getOrDefaultValue(location);
                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                CargoLimit.OUTPUT_HELPER.checkOrSetBlockStorage(location);

                BlockStorage.addBlockInfo(block, PositionInfo.KEY, "");
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, StationTransferMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StationTransferMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    public void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        // do something now?
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();
        boolean drawParticle = blockMenu.hasViewer();

        String cargoFilter = CargoFilter.HELPER.getOrDefaultValue(config);
        String locationInfo = config.getString(PositionInfo.KEY);
        PositionHelper positionHelper = new PositionHelper(locationInfo);

        String[] outputs = positionHelper.getOutputs();
        String[] inputs = positionHelper.getInputs();
        Block[] outputBlocks = new Block[outputs.length];
        Block[] inputBlocks = new Block[inputs.length];
        String outputBlockSearchMode = BlockSearchMode.STATION_OUTPUT_HELPER.getOrDefaultValue(config);
        String inputBlockSearchMode = BlockSearchMode.STATION_INPUT_HELPER.getOrDefaultValue(config);
        if(primaryThread) {
            for(int i = 0; i < outputBlocks.length; i++) {
                outputBlocks[i] = this.searchBlock(block, PositionInfo.getBlockFaceByPosition(outputs[i]), outputBlockSearchMode, drawParticle);
            }
            for(int i = 0; i < inputBlocks.length; i++) {
                inputBlocks[i] = this.searchBlock(block, PositionInfo.getBlockFaceByPosition(inputs[i]), inputBlockSearchMode, drawParticle);
            }
        } else {
            try {
                javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> {
                    for (int i = 0; i < outputBlocks.length; i++) {
                        outputBlocks[i] = this.searchBlock(block, PositionInfo.getBlockFaceByPosition(outputs[i]), outputBlockSearchMode, drawParticle);
                    }
                    for (int i = 0; i < inputBlocks.length; i++) {
                        inputBlocks[i] = this.searchBlock(block, PositionInfo.getBlockFaceByPosition(inputs[i]), inputBlockSearchMode, drawParticle);
                    }
                    return null;
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if(inputBlocks.length == 0 && outputBlocks.length == 0) {
            return;
        } else if(drawParticle) {
            javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, 0, inputBlocks);
                ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, 0, outputBlocks);
            }, FinalTech.SLIMEFUN_TICK_TIME_MILLIS / 1000 / 50);
        }

        // parse output
        String outputSlotSearchSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
        String outputSlotSearchOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);
        AtomicInteger outputCargoNumber = new AtomicInteger(Integer.parseInt(CargoNumber.OUTPUT_HELPER.getOrDefaultValue(config)));
        String outputCargoNumberMode = CargoNumberMode.OUTPUT_HELPER.getOrDefaultValue(config);
        String outputCargoLimit = CargoLimit.OUTPUT_HELPER.getOrDefaultValue(config);

        // parse input
        String inputSlotSearchSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
        String inputSlotSearchOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);
        AtomicInteger inputCargoNumber = new AtomicInteger(Integer.parseInt(CargoNumber.INPUT_HELPER.getOrDefaultValue(config)));
        String inputCargoNumberMode = CargoNumberMode.INPUT_HELPER.getOrDefaultValue(config);
        String inputCargoLimit = CargoLimit.INPUT_HELPER.getOrDefaultValue(config);

        Runnable runnable = () -> {
            for (Block outputBlock : outputBlocks) {
                int result = CargoUtil.doCargoInputMain(block, outputBlock, SlotSearchSize.VALUE_OUTPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, outputSlotSearchSize, outputSlotSearchOrder, outputCargoNumber.get(), outputCargoLimit, cargoFilter, blockMenu.toInventory(), StationTransferMenu.ITEM_MATCH);
                if (CargoNumberMode.VALUE_UNIVERSAL.equals(outputCargoNumberMode)) {
                    outputCargoNumber.addAndGet(-result);
                    if (outputCargoNumber.get() == 0) {
                        break;
                    }
                }
            }

            for (int i = 0; i < MeshTransfer.this.getInputSlot().length; i++) {
                ItemStack inputItem = blockMenu.getItemInSlot(MeshTransfer.this.getInputSlot()[i]);
                if (ItemStackUtil.isItemNull(inputItem)) {
                    continue;
                }
                ItemStack outputItem = blockMenu.getItemInSlot(MeshTransfer.this.getOutputSlot()[i]);
                if (ItemStackUtil.isItemNull(outputItem)) {
                    blockMenu.toInventory().setItem(MeshTransfer.this.getOutputSlot()[i], new ItemStack(inputItem));
                    inputItem.setAmount(0);
                } else if (ItemStackUtil.isItemSimilar(inputItem, outputItem)) {
                    ItemStackUtil.stack(inputItem, outputItem);
                }
            }

            for (String inputPosition : inputs) {
                BlockFace blockFace = PositionInfo.getBlockFaceByPosition(inputPosition);
                Block inputBlock = MeshTransfer.this.searchBlock(block, blockFace, BlockSearchMode.STATION_INPUT_HELPER.getOrDefaultValue(config), drawParticle);
                int result = CargoUtil.doCargoOutputMain(inputBlock, block, inputSlotSearchSize, inputSlotSearchOrder, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, inputCargoNumber.get(), inputCargoLimit, cargoFilter, blockMenu.toInventory(), StationTransferMenu.ITEM_MATCH);
                if (CargoNumberMode.VALUE_UNIVERSAL.equals(inputCargoNumberMode)) {
                    inputCargoNumber.addAndGet(-result);
                    if (inputCargoNumber.get() == 0) {
                        break;
                    }
                }
            }
        };

        // do cargo
        if(primaryThread) {
            runnable.run();
        } else {
            Location[] locations = new Location[outputBlocks.length + inputBlocks.length + 1];
            int p = 0;
            for(; p < outputBlocks.length; p++) {
                locations[p] = outputBlocks[p].getLocation();
            }
            for(int i = 0; i < inputBlocks.length; i++) {
                locations[i + p] = inputBlocks[i].getLocation();
            }
            locations[locations.length - 1] = block.getLocation();

            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, runnable, locations);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Nonnull
    public Block searchBlock(@Nonnull Block begin, @Nonnull BlockFace blockFace, @Nonnull String searchMode, boolean drawParticle) {
        List<Location> particleLocationList = new ArrayList<>();
        particleLocationList.add(LocationUtil.getCenterLocation(begin));
        Block result = begin.getRelative(blockFace);
        if (BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            particleLocationList.add(LocationUtil.getCenterLocation(result));
            if (drawParticle) {
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(Particle.COMPOSTER, FinalTech.SLIMEFUN_TICK_TIME_MILLIS / particleLocationList.size() / 1000, PARTICLE_DISTANCE, particleLocationList));
            }
            return result;
        }
        while(true) {
            particleLocationList.add(LocationUtil.getCenterLocation(result));
            if (result.getType() == Material.CHAIN) {
                result = result.getRelative(blockFace);
                continue;
            }
            if (BlockSearchMode.VALUE_PENETRATE.equals(searchMode) && BlockStorage.hasInventory(result) && BlockStorage.getInventory(result).getPreset().getID().equals(FinalTechItems.STATION_TRANSFER.getItemId())) {
                result = result.getRelative(blockFace);
                continue;
            }
            break;
        }
        if (drawParticle) {
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(Particle.COMPOSTER, FinalTech.SLIMEFUN_TICK_TIME_MILLIS / particleLocationList.size() / 1000, PARTICLE_DISTANCE, particleLocationList));
        }
        return result;
    }

    @Translate
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
