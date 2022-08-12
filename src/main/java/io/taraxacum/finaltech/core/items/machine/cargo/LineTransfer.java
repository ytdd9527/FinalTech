package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.InvWithSlots;
import io.taraxacum.finaltech.api.factory.ServerRunnableLockFactory;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.LineTransferMenu;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.util.*;
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

                IgnorePermission.HELPER.checkOrSetBlockStorage(location);
                BlockStorage.addBlockInfo(location, "UUID", blockPlaceEvent.getPlayer().getUniqueId().toString());

                BlockSearchMode.LINE_HELPER.checkOrSetBlockStorage(location);
                BlockSearchOrder.HELPER.checkOrSetBlockStorage(location);
                CargoOrder.HELPER.checkOrSetBlockStorage(location);
                BlockSearchCycle.HELPER.checkOrSetBlockStorage(location);
                BlockSearchSelf.HELPER.checkOrSetBlockStorage(location);

                CargoNumber.HELPER.checkOrSetBlockStorage(location);
                CargoNumberMode.HELPER.checkOrSetBlockStorage(location);
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
        Location location = blockMenu.getLocation();
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

        if(!SlimefunUtil.checkOfflinePermission(location, config, LocationUtil.transferToLocation(blockList))) {
            return;
        }

        switch (BlockSearchSelf.HELPER.getOrDefaultValue(config)) {
            case BlockSearchSelf.VALUE_BEGIN -> blockList.add(0, block);
            case BlockSearchSelf.VALUE_LAST -> blockList.add(block);
        }

        switch (BlockSearchOrder.HELPER.getOrDefaultValue(config)) {
            case BlockSearchOrder.VALUE_REVERSE -> blockList = JavaUtil.reserve(blockList);
            case BlockSearchOrder.VALUE_RANDOM -> blockList = JavaUtil.shuffle(blockList);
        }

        if (BlockSearchCycle.VALUE_TRUE.equals(BlockSearchCycle.HELPER.getOrDefaultValue(config)) && blockList.size() > 0) {
            blockList.add(blockList.get(0));
        }

        final List<Block> finalBlockList = blockList;
        if (drawParticle && blockList.size() > 0) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, Slimefun.getTickerTask().getTickRate() * 50L / finalBlockList.size(), finalBlockList));
        }

        String cargoOrder = CargoOrder.HELPER.getOrDefaultValue(config);
        int cargoNumber = Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config));
        String cargoNumberMode = CargoNumberMode.HELPER.getOrDefaultValue(config);
        String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);
        String cargoFilter = CargoFilter.HELPER.getOrDefaultValue(config);
        String inputSlotSearchSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
        String inputSlotSearchOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);
        String cargoLimit = CargoLimit.HELPER.getOrDefaultValue(config);
        String outputSlotSearchSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
        String outputSlotSearchOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);

        if(!primaryThread) {
            int number = 0;
            for(int i = 0, size = blockList.size(); i < size - 1; i++) {
                Block inputBlock = blockList.get(i);
                Block outputBlock = blockList.get((i + 1) % size);
                if(inputBlock.getLocation().equals(outputBlock.getLocation())) {
                    continue;
                }
                switch (cargoOrder) {
                    case CargoOrder.VALUE_POSITIVE -> number = CargoUtil.doCargo(javaPlugin, inputBlock, outputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LineTransferMenu.ITEM_MATCH, cargoMode);
                    case CargoOrder.VALUE_REVERSE -> number = CargoUtil.doCargo(javaPlugin, outputBlock, inputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LineTransferMenu.ITEM_MATCH, cargoMode);
                }
                if(CargoNumberMode.VALUE_UNIVERSAL.equals(cargoNumberMode)) {
                    cargoNumber -= number;
                }
            }
        } else {
            Location[] locations = LocationUtil.transferToLocation(blockList);
            int finalCargoNumber = cargoNumber;
            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                InvWithSlots[] inputInvWithSlots = new InvWithSlots[finalBlockList.size()];
                InvWithSlots[] outputInvWithSlots = new InvWithSlots[finalBlockList.size()];
                for(int i = 0, size = finalBlockList.size(); i < size; i++) {
                    inputInvWithSlots[i] = CargoUtil.getInvWithSlots(finalBlockList.get(i), inputSlotSearchSize, inputSlotSearchOrder);
                    outputInvWithSlots[i] = CargoUtil.getInvWithSlots(finalBlockList.get(i), outputSlotSearchSize, outputSlotSearchOrder);
                }
                ServerRunnableLockFactory.getInstance(javaPlugin, Location.class).waitThenRun(() -> {
                    int number = 0;
                    int ffCargoNumber = finalCargoNumber;
                    for(int i = 0, size = finalBlockList.size(); i < size - 1; i++) {
                        Block inputBlock = finalBlockList.get(i);
                        Block outputBlock = finalBlockList.get((i + 1) % size);
                        if(inputBlock.getLocation().equals(outputBlock.getLocation())) {
                            continue;
                        }
                        switch (cargoOrder) {
                            case CargoOrder.VALUE_POSITIVE -> number = CargoUtil.doSimpleCargo(inputInvWithSlots[i], inputBlock, outputInvWithSlots[(i + 1) % size], outputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, ffCargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LineTransferMenu.ITEM_MATCH, cargoMode);
                            case CargoOrder.VALUE_REVERSE -> number = CargoUtil.doSimpleCargo(outputInvWithSlots[(i + 1) % size], outputBlock, inputInvWithSlots[i], inputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, ffCargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LineTransferMenu.ITEM_MATCH, cargoMode);
                        }
                        if(CargoNumberMode.VALUE_UNIVERSAL.equals(cargoNumberMode)) {
                            ffCargoNumber -= number;
                        }
                    }
                }, locations);
            });
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
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
