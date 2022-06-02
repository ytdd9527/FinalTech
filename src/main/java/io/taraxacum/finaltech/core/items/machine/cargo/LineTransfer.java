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
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.LineTransferMenu;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.ParticleUtil;
import io.taraxacum.finaltech.util.TextUtil;
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
import org.w3c.dom.Text;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Final_ROOT
 * @since 1.0
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
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "功能",
                "",
                TextUtil.COLOR_NORMAL + "该机器会不断把物品",
                TextUtil.COLOR_NORMAL + "从输入侧方块的容器",
                TextUtil.COLOR_NORMAL + "传输到输出侧方块的容器");
    }
}
