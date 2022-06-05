package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.LinkTransferMenu;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class LinkTransfer extends AbstractCargo implements RecipeItem {
    private static final double PARTICLE_DISTANCE = 0.22;
    public static int BLOCK_SEARCH_LIMIT = 8;

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

                CargoNumber.HELPER.checkOrSetBlockStorage(location);
                CargoFilter.HELPER.checkOrSetBlockStorage(location);
                CargoMode.HELPER.checkOrSetBlockStorage(location);
                CargoLimit.HELPER.checkOrSetBlockStorage(location);

                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.INPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.PIPE_INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(location);

                SlotSearchOrder.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.PIPE_OUTPUT_HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, LinkTransferMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new LinkTransferMenu(this);
    }

    @Override
    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config)  {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();
        boolean drawParticle = blockMenu.hasViewer();

        String inputSlotSearchSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
        String inputSlotSearchOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);

        String outputSlotSearchSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
        String outputSlotSearchOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);

        int cargoNumber = Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config));
        String cargoFilter = CargoFilter.HELPER.getOrDefaultValue(config);
        String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);
        String cargoLimit = CargoLimit.HELPER.getOrDefaultValue(config);

        Block inputBlock = null;
        Block outputBlock = null;
        if(primaryThread) {
            BlockData blockData = block.getState().getBlockData();
            if (blockData instanceof Directional) {
                BlockFace blockFace = ((Directional) blockData).getFacing();
                inputBlock = this.searchBlockPiPe(block, BlockSearchMode.PIPE_INPUT_HELPER.getOrDefaultValue(config), blockFace.getOppositeFace(), true, drawParticle);
                outputBlock = this.searchBlockPiPe(block, BlockSearchMode.PIPE_OUTPUT_HELPER.getOrDefaultValue(config), blockFace, false, drawParticle);
            }
        } else {
            try {
                Block[] blocks = javaPlugin.getServer().getScheduler().callSyncMethod(javaPlugin, () -> {
                    BlockData blockData = block.getState().getBlockData();
                    Block[] result = new Block[2];
                    if (blockData instanceof Directional) {
                        BlockFace blockFace = ((Directional) blockData).getFacing();
                        result[0] = LinkTransfer.this.searchBlockPiPe(block, BlockSearchMode.PIPE_INPUT_HELPER.getOrDefaultValue(config), blockFace.getOppositeFace(), true, drawParticle);
                        result[1] = LinkTransfer.this.searchBlockPiPe(block, BlockSearchMode.PIPE_OUTPUT_HELPER.getOrDefaultValue(config), blockFace, false, drawParticle);
                    }
                    return result;
                }).get();
                inputBlock = blocks[0];
                outputBlock = blocks[1];
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (inputBlock == null || outputBlock == null) {
            return;
        } else if(drawParticle) {
            final Block finalInputBlock = inputBlock;
            final Block finalOutputBlock = outputBlock;
            javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, 0, finalInputBlock, finalOutputBlock), FinalTech.SLIMEFUN_TICK_TIME_MILLIS / 1000 / 50);
        }

        String uuid = config.getString("UUID");
        if (uuid != null) {
            if (!SlimefunUtil.hasPermission(uuid, inputBlock, Interaction.INTERACT_BLOCK) || !SlimefunUtil.hasPermission(uuid, outputBlock, Interaction.INTERACT_BLOCK)) {
                return;
            }
        }

        if(primaryThread) {
            CargoUtil.doCargo(inputBlock, outputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LinkTransferMenu.ITEM_MATCH, cargoMode);
        } else {
            final Block finalInputBlock = inputBlock;
            final Block finalOutputBlock = outputBlock;
            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, () -> CargoUtil.doCargo(finalInputBlock, finalOutputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputSlotSearchSize, outputSlotSearchOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), LinkTransferMenu.ITEM_MATCH, cargoMode), block.getLocation(), inputBlock.getLocation(), outputBlock.getLocation());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Nonnull
    private Block searchBlockPiPe(@Nonnull Block begin, @Nonnull String searchMode, @Nonnull BlockFace blockFace, boolean input, boolean drawParticle) {
        List<Location> particleLocationList = new ArrayList<>();
        particleLocationList.add(LocationUtil.getCenterLocation(begin));
        Block result = begin.getRelative(blockFace);
        int count = 1;
        if (BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            particleLocationList.add(LocationUtil.getCenterLocation(result));
            if (drawParticle) {
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(Particle.COMPOSTER, FinalTech.SLIMEFUN_TICK_TIME_MILLIS / particleLocationList.size() / 1000, PARTICLE_DISTANCE, particleLocationList));
            }
            return result;
        }
        Set<Location> locationSet = new HashSet<>();
        locationSet.add(begin.getLocation());
        while(true) {
            particleLocationList.add(LocationUtil.getCenterLocation(result));
            if (BlockStorage.hasInventory(result) && !result.getType().equals(FinalTechItems.LINK_TRANSFER.getType())) {
                break;
            }
            if (PaperLib.getBlockState(result, false).getState() instanceof InventoryHolder) {
                break;
            }
            if (result.getType() == FinalTechItems.LINK_TRANSFER.getType()) {
                count = 0;
                if (locationSet.contains(result.getLocation())) {
                    particleLocationList.add(LocationUtil.getCenterLocation(result));
                    break;
                }
                locationSet.add(result.getLocation());
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
                break;
            }
        }
        if (drawParticle) {
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(Particle.COMPOSTER, FinalTech.SLIMEFUN_TICK_TIME_MILLIS / particleLocationList.size() / 1000, PARTICLE_DISTANCE, particleLocationList));
        }
        return result;
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
