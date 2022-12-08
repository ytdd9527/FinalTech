package io.taraxacum.finaltech.core.item.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.LocationWithConfig;
import io.taraxacum.finaltech.api.interfaces.item.RecipeItem;
import io.taraxacum.finaltech.core.item.unusable.ItemPhony;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixAccelerator extends AbstractCubeMachine implements RecipeItem {
    private final int range = ConfigUtil.getOrDefaultItemSetting(1, this, "range");
    // System.nanoTime
    // 1,000,000ns = 1ms
    private final int syncThreshold = ConfigUtil.getOrDefaultItemSetting(300000, this, "threshold-sync");
    private final int asyncThreshold = ConfigUtil.getOrDefaultItemSetting(1600000, this, "threshold-async");
    private final Set<String> invalidIdSet = new HashSet<>(ConfigUtil.getItemStringList(this, "invalid-ids"));

    public MatrixAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location blockLocation = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(blockLocation);
        boolean drawParticle = blockMenu.hasViewer();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        int accelerate = 0;
        int range = this.range;

        SlimefunItem machineItem = null;
        String machineId = null;

        // parse item
        ItemStack matchItem = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        if (ItemPhony.isValid(matchItem)) {
            int amount = matchItem.getAmount();
            for (int i = 2, j = amount; j > 0; j /= i) {
                accelerate++;
            }
            for (int i = 2, j = amount; j > 0; j /= i++) {
                range++;
            }
        } else {
            machineItem = SlimefunItem.getByItem(matchItem);
            if (machineItem == null || this.invalidIdSet.contains(machineItem.getId()) || machineItem.getBlockTicker() == null) {
                this.updateMenu(blockMenu, range, 0, 0);
                return;
            }
            machineId = machineItem.getId();
            accelerate = matchItem.getAmount();
        }

        // search around block
        Map<Integer, List<LocationWithConfig>> machineConfigMap = new HashMap<>(range * 3);
        if (machineId == null) {
            int count = this.function(block, range, location -> {
                if (BlockStorage.hasBlockInfo(location)) {
                    Config machineConfig = BlockStorage.getLocationInfo(location);
                    if (machineConfig.contains(ConstantTableUtil.CONFIG_ID) && !MatrixAccelerator.this.invalidIdSet.contains(machineConfig.getString(ConstantTableUtil.CONFIG_ID))) {
                        int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                        List<LocationWithConfig> machineConfigList = machineConfigMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                        machineConfigList.add(new LocationWithConfig(location.clone(), machineConfig));
                        return 1;
                    }
                }
                return 0;
            });
            if (count <= 1) {
                this.updateMenu(blockMenu, range, 0, 0);
                return;
            }
        } else {
            final String finalMachineId = machineId;
            int count = this.function(block, range, location -> {
                if (BlockStorage.hasBlockInfo(location)) {
                    Config machineConfig = BlockStorage.getLocationInfo(location);
                    if (machineConfig.contains(ConstantTableUtil.CONFIG_ID) && finalMachineId.equals(machineConfig.getString(ConstantTableUtil.CONFIG_ID))) {
                        int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                        List<LocationWithConfig> machineConfigList = machineConfigMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                        machineConfigList.add(new LocationWithConfig(location.clone(), machineConfig));
                        return 1;
                    }
                }
                return 0;
            });
            if (count == 0) {
                this.updateMenu(blockMenu, range, 0, 0);
                return;
            }
            accelerate /= count;
        }

        int accelerateTimeCount = 0;
        int accelerateMachineCount = 0;

        // do accelerate
        if (machineId == null) {
            List<LocationWithConfig> locationConfigList;
            for (int distance = 1; distance <= range * 3; distance++) {
                locationConfigList = machineConfigMap.get(distance);
                if (locationConfigList != null) {
                    Collections.shuffle(locationConfigList);
                    for (LocationWithConfig locationConfig : locationConfigList) {
                        Config machineConfig = locationConfig.getConfig();
                        Location machineLocation = locationConfig.getLocation();
                        final String finalMachineId = machineConfig.getString(ConstantTableUtil.CONFIG_ID);
                        if (finalMachineId == null || this.invalidIdSet.contains(finalMachineId)) {
                            continue;
                        }
                        final SlimefunItem finalMachineItem = SlimefunItem.getById(finalMachineId);
                        if (finalMachineItem == null || finalMachineItem.getBlockTicker() == null) {
                            continue;
                        }
                        BlockTicker blockTicker = finalMachineItem.getBlockTicker();
                        final int finalAccelerate = accelerate;
                        if (blockTicker.isSynchronized()) {
                            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                                for (int i = 0; i < finalAccelerate; i++) {
                                    long testTime = JavaUtil.testTime(() -> blockTicker.tick(machineLocation.getBlock(), finalMachineItem, machineConfig));
                                    if (testTime > MatrixAccelerator.this.syncThreshold) {
                                        FinalTech.logger().warning(this.getId() + " cost " + testTime + "ns to run blockTicker for " + finalMachineId);
                                        MatrixAccelerator.this.invalidIdSet.add(finalMachineId);
                                        break;
                                    }
                                }
                            });
                        } else {
                            BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(finalMachineId), () -> {
                                for (int i = 0; i < finalAccelerate; i++) {
                                    long testTime = JavaUtil.testTime(() -> blockTicker.tick(machineLocation.getBlock(), finalMachineItem, machineConfig));
                                    if (testTime > MatrixAccelerator.this.asyncThreshold) {
                                        FinalTech.logger().warning(this.getId() + " cost " + testTime + "ns to run blockTicker for " + finalMachineId);
                                        MatrixAccelerator.this.invalidIdSet.add(finalMachineId);
                                        break;
                                    }
                                }
                            }, machineLocation);
                        }
                        if (drawParticle) {
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, locationConfig.getLocation().getBlock()));
                        }
                        accelerateTimeCount += accelerate;
                        accelerateMachineCount++;
                    }
                }
            }
        } else {
            BlockTicker blockTicker = machineItem.getBlockTicker();
            final SlimefunItem finalMachineItem = machineItem;
            final String finalMachineId = machineId;
            List<LocationWithConfig> locationConfigList;
            for (int distance = 1; distance <= range * 3; distance++) {
                locationConfigList = machineConfigMap.get(distance);
                if (locationConfigList != null) {
                    Collections.shuffle(locationConfigList);
                    for (LocationWithConfig locationConfig : locationConfigList) {
                        Config machineConfig = locationConfig.getConfig();
                        Location machineLocation = locationConfig.getLocation();
                        if (!machineId.equals(machineConfig.getString(ConstantTableUtil.CONFIG_ID))) {
                            continue;
                        }
                        final int finalAccelerate = accelerate;
                        if (blockTicker.isSynchronized()) {
                            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                                for (int i = 0; i < finalAccelerate; i++) {
                                    long testTime = JavaUtil.testTime(() -> blockTicker.tick(machineLocation.getBlock(), finalMachineItem, machineConfig));
                                    if (testTime > MatrixAccelerator.this.syncThreshold) {
                                        FinalTech.logger().warning(this.getId() + " cost " + testTime + "ns to run blockTicker for " + finalMachineId);
                                        MatrixAccelerator.this.invalidIdSet.add(finalMachineId);
                                        break;
                                    }
                                }
                            });
                        } else {
                            BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(finalMachineId), () -> {
                                for (int i = 0; i < finalAccelerate; i++) {
                                    long testTime = JavaUtil.testTime(() -> blockTicker.tick(machineLocation.getBlock(), finalMachineItem, machineConfig));
                                    if (testTime > MatrixAccelerator.this.asyncThreshold) {
                                        FinalTech.logger().warning(this.getId() + " cost " + testTime + "ns to run blockTicker for " + finalMachineId);
                                        MatrixAccelerator.this.invalidIdSet.add(finalMachineId);
                                        break;
                                    }
                                }
                            }, machineLocation);
                        }
                        if (drawParticle) {
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, locationConfig.getLocation().getBlock()));
                        }
                        accelerateTimeCount += accelerate;
                        accelerateMachineCount++;
                    }
                }
            }
        }

        this.updateMenu(blockMenu, range, accelerateTimeCount, accelerateMachineCount);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int range, int accelerateTimeCount, int accelerateMachineCount) {
        if (blockMenu.hasViewer()) {
            ItemStack item = blockMenu.getItemInSlot(StatusL2Menu.STATUS_SLOT);
            ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                    String.valueOf(range),
                    String.valueOf(accelerateMachineCount),
                    String.valueOf(accelerateTimeCount)));
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range));
    }
}
