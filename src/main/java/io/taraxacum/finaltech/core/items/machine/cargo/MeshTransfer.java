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
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.MeshTransferMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
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
public class MeshTransfer extends AbstractCargo implements RecipeItem {
    private final double particleDistance = 0.22;

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

                IgnorePermission.HELPER.checkOrSetBlockStorage(location);
                BlockStorage.addBlockInfo(location, "UUID", blockPlaceEvent.getPlayer().getUniqueId().toString());

                CargoFilter.HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.MESH_INPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.MESH_OUTPUT_HELPER.checkOrSetBlockStorage(location);

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
        return MachineUtil.simpleBlockBreakerHandler(this, MeshTransferMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new MeshTransferMenu(this);
    }

    @Override
    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        // do something now?
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = block.getLocation();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();
        boolean drawParticle = blockMenu.hasViewer();

        BlockFace[] outputBlockFaces = PositionInfo.getBlockFaces(config, PositionInfo.VALUE_OUTPUT, PositionInfo.VALUE_INPUT_AND_OUTPUT);
        BlockFace[] inputBlockFaces = PositionInfo.getBlockFaces(config, PositionInfo.VALUE_INPUT, PositionInfo.VALUE_INPUT_AND_OUTPUT);
        Block[] outputBlocks = new Block[inputBlockFaces.length];
        Block[] inputBlocks = new Block[outputBlockFaces.length];
        String outputBlockSearchMode = BlockSearchMode.MESH_OUTPUT_HELPER.getOrDefaultValue(config);
        String inputBlockSearchMode = BlockSearchMode.MESH_INPUT_HELPER.getOrDefaultValue(config);
        if(primaryThread) {
            for(int i = 0; i < outputBlocks.length; i++) {
                outputBlocks[i] = this.searchBlock(block, inputBlockFaces[i], outputBlockSearchMode, drawParticle);
            }
            for(int i = 0; i < inputBlocks.length; i++) {
                inputBlocks[i] = this.searchBlock(block, outputBlockFaces[i], inputBlockSearchMode, drawParticle);
            }
        } else {
            try {
                javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> {
                    for (int i = 0; i < outputBlocks.length; i++) {
                        outputBlocks[i] = this.searchBlock(block, inputBlockFaces[i], outputBlockSearchMode, drawParticle);
                    }
                    for (int i = 0; i < inputBlocks.length; i++) {
                        inputBlocks[i] = this.searchBlock(block, outputBlockFaces[i], inputBlockSearchMode, drawParticle);
                    }
                    return null;
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if(drawParticle) {
            javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, 0, inputBlocks);
                ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, 0, outputBlocks);
            }, Slimefun.getTickerTask().getTickRate());
        }

        if(!SlimefunUtil.checkOfflinePermission(location, config, LocationUtil.transferToLocation(inputBlocks)) || !SlimefunUtil.checkOfflinePermission(location, config, LocationUtil.transferToLocation(outputBlocks))) {
            return;
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

        String cargoFilter = CargoFilter.HELPER.getOrDefaultValue(config);

        // do cargo
        if(primaryThread) {
            for (Block outputBlock : outputBlocks) {
                int result = CargoUtil.doCargoInputMain(javaPlugin, block, outputBlock, SlotSearchSize.VALUE_OUTPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, outputSlotSearchSize, outputSlotSearchOrder, outputCargoNumber.get(), outputCargoLimit, cargoFilter, blockMenu.toInventory(), MeshTransferMenu.ITEM_MATCH);
                if (CargoNumberMode.VALUE_UNIVERSAL.equals(outputCargoNumberMode)) {
                    outputCargoNumber.addAndGet(-result);
                    if (outputCargoNumber.get() == 0) {
                        break;
                    }
                }
            }

            CargoUtil.doSimpleCargoStrongSymmetry(new InvWithSlots(blockMenu.toInventory(), this.getInputSlot()), new InvWithSlots(blockMenu.toInventory(), this.getOutputSlot()), 576, CargoLimit.VALUE_ALL, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0]);

            for (Block inputBlock : inputBlocks) {
                int result = CargoUtil.doCargoOutputMain(javaPlugin, inputBlock, block, inputSlotSearchSize, inputSlotSearchOrder, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, inputCargoNumber.get(), inputCargoLimit, cargoFilter, blockMenu.toInventory(), MeshTransferMenu.ITEM_MATCH);
                if (CargoNumberMode.VALUE_UNIVERSAL.equals(inputCargoNumberMode)) {
                    inputCargoNumber.addAndGet(-result);
                    if (inputCargoNumber.get() == 0) {
                        break;
                    }
                }
            }
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
            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                InvWithSlots[] inputInvWithSlots = new InvWithSlots[inputBlocks.length];
                InvWithSlots[] outputInvWithSlots = new InvWithSlots[outputBlocks.length];
                InvWithSlots invWithSlots1 = new InvWithSlots(blockMenu.toInventory(), MeshTransfer.this.getInputSlot());
                InvWithSlots invWithSlots2 = new InvWithSlots(blockMenu.toInventory(), MeshTransfer.this.getOutputSlot());
                for(int i = 0; i < inputBlocks.length; i++) {
                    inputInvWithSlots[i] = CargoUtil.getInvWithSlots(inputBlocks[i], outputSlotSearchSize, outputSlotSearchOrder);
                }
                for(int i = 0; i < outputBlocks.length; i++) {
                    outputInvWithSlots[i] = CargoUtil.getInvWithSlots(outputBlocks[i], outputSlotSearchSize, outputSlotSearchOrder);
                }
                ServerRunnableLockFactory.getInstance(javaPlugin, Location.class).waitThenRun(() -> {
                    for(int i = 0; i < outputBlocks.length; i++) {
                        int result = CargoUtil.doSimpleCargoOutputMain(invWithSlots2, block, outputInvWithSlots[i], SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, inputCargoNumber.get(), inputCargoLimit, cargoFilter, blockMenu.toInventory(), MeshTransferMenu.ITEM_MATCH);
                        if (CargoNumberMode.VALUE_UNIVERSAL.equals(inputCargoNumberMode)) {
                            inputCargoNumber.addAndGet(-result);
                            if (inputCargoNumber.get() == 0) {
                                break;
                            }
                        }
                    }

                    CargoUtil.doSimpleCargoStrongSymmetry(invWithSlots1, invWithSlots2, 576, CargoLimit.VALUE_ALL, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0]);

                    for(int i = 0; i < inputBlocks.length; i++) {
                        int result = CargoUtil.doSimpleCargoInputMain(inputInvWithSlots[i], invWithSlots1, block, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, inputCargoNumber.get(), inputCargoLimit, cargoFilter, blockMenu.toInventory(), MeshTransferMenu.ITEM_MATCH);
                        if (CargoNumberMode.VALUE_UNIVERSAL.equals(outputCargoNumberMode)) {
                            outputCargoNumber.addAndGet(-result);
                            if (outputCargoNumber.get() == 0) {
                                break;
                            }
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
    public Block searchBlock(@Nonnull Block sourceBlock, @Nonnull BlockFace blockFace, @Nonnull String searchMode, boolean drawParticle) {
        List<Location> particleLocationList = new ArrayList<>();
        particleLocationList.add(LocationUtil.getCenterLocation(sourceBlock));
        Block result = sourceBlock.getRelative(blockFace);
        if (BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            particleLocationList.add(LocationUtil.getCenterLocation(result));
            if (drawParticle) {
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(Particle.COMPOSTER, Slimefun.getTickerTask().getTickRate() * 50L / particleLocationList.size(), particleDistance, particleLocationList));
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
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(Particle.COMPOSTER, Slimefun.getTickerTask().getTickRate() * 50L / particleLocationList.size(), particleDistance, particleLocationList));
        }
        return result;
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
