package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.LineTransferMenu;
import io.taraxacum.finaltech.core.storage.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.ParticleUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Final_ROOT
 */
public class LineTransfer extends AbstractCargo implements RecipeItem {
    private static final long PARTICLE_INTERVAL = 100L;

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

                BlockSearchMode.LINE_HELPER.checkOrSetBlockStorage(location);
                BlockSearchOrder.HELPER.checkOrSetBlockStorage(location);
                CargoOrder.HELPER.checkOrSetBlockStorage(location);
                BlockSearchCycle.HELPER.checkOrSetBlockStorage(location);
                BlockSearchSelf.HELPER.checkOrSetBlockStorage(location);

                CargoNumber.HELPER.checkOrSetBlockStorage(location);
                CargoMode.HELPER.checkOrSetBlockStorage(location);
                CargoFilter.HELPER.checkOrSetBlockStorage(location);

                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.INPUT_HELPER.checkOrSetBlockStorage(location);
                CargoLimit.HELPER.checkOrSetBlockStorage(location);
                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.OUTPUT_HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, LineTransferMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new LineTransferMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();
        boolean drawParticle = blockMenu.hasViewer();

        List<Block> blockList;
        if(primaryThread) {
            BlockFace blockFace = null;
            BlockData blockData = block.getState().getBlockData();
            if (blockData instanceof Directional) {
                blockFace = ((Directional) blockData).getFacing();
            }
            if (blockFace == null) {
                return;
            }
            blockList = this.searchBlock(block, blockFace, BlockSearchMode.LINE_HELPER.getOrDefaultValue(config));
        } else {
            try {
                blockList = javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, (Callable<List<Block>>) () -> {
                    BlockFace blockFace = null;
                    BlockData blockData = block.getState().getBlockData();
                    if (blockData instanceof Directional) {
                        blockFace = ((Directional) blockData).getFacing();
                    }
                    if (blockFace == null) {
                        return new ArrayList<>();
                    }
                    return LineTransfer.this.searchBlock(block, blockFace, BlockSearchMode.LINE_HELPER.getOrDefaultValue(config));
                }).get();
                if(blockList.isEmpty()) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        String blockSearchSelf = BlockSearchSelf.HELPER.getOrDefaultValue(config);
        if (BlockSearchSelf.VALUE_BEGIN.equals(blockSearchSelf)) {
            blockList.add(0, block);
        } else if (BlockSearchSelf.VALUE_LAST.equals(blockSearchSelf)) {
            blockList.add(block);
        }

        String blockSearchOrder = BlockSearchOrder.HELPER.getOrDefaultValue(config);
        if (BlockSearchOrder.VALUE_REVERSE.equals(blockSearchOrder)) {
            blockList = JavaUtil.reserve(blockList);
        } else if(BlockSearchOrder.VALUE_RANDOM.equals(blockSearchOrder)) {
            blockList = JavaUtil.shuffle(blockList);
        }

        if (BlockSearchCycle.VALUE_TRUE.equals(BlockSearchCycle.HELPER.getOrDefaultValue(config))) {
            blockList.add(blockList.get(0));
        }

        final List<Block> finalBlockList = blockList;
        if (drawParticle && blockList.size() > 0) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, FinalTech.SLIMEFUN_TICK_TIME_MILLIS / finalBlockList.size() / 1000, finalBlockList));
        }

        String cargoOrder = CargoOrder.HELPER.getOrDefaultValue(config);
        int cargoNumber = Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config));
        String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);
        String cargoFilter = CargoFilter.HELPER.getOrDefaultValue(config);
        String inputSlotSearchSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
        String inputSlotSearchOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);
        String cargoLimit = CargoLimit.HELPER.getOrDefaultValue(config);
        String outputSlotSearchSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
        String outputSlotSearchOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);

        Runnable runnable = () -> {
            for(int i = 0, size = finalBlockList.size(); i< size - 1; i++) {
                Block inputBlock = finalBlockList.get(i);
                Block outputBlock = finalBlockList.get((i + 1) % size);
                if (CargoOrder.VALUE_POSITIVE.equals(cargoOrder)) {
                    CargoUtil.doCargo(inputBlock, outputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LineTransferMenu.ITEM_MATCH, cargoMode);
                } else if (CargoOrder.VALUE_REVERSE.equals(cargoOrder)) {
                    CargoUtil.doCargo(outputBlock, inputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LineTransferMenu.ITEM_MATCH, cargoMode);
                }
            }
        };

        if(primaryThread) {
            runnable.run();
        } else {
            Location[] locations = new Location[blockList.size() + 1];
            for(int i = 0; i < blockList.size(); i++) {
                locations[i] = blockList.get(i).getLocation();
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
    public List<Block> searchBlock(@Nonnull Block begin, @Nonnull BlockFace blockFace, @Nonnull String blockSearchMode) {
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
            if (BlockStorage.hasInventory(block) && BlockStorage.getInventory(block).getPreset().getID().equals(FinalTechItems.LINE_TRANSFER.getItemId())) {
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
