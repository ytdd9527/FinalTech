package io.taraxacum.finaltech.core.item.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.libs.slimefun.dto.LocationWithConfig;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
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
public class EnergizedAccelerator extends AbstractCubeMachine implements EnergyNetComponent, RecipeItem, MenuUpdater {
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE / 4, this, "capacity");

    public EnergizedAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location blockLocation = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(blockLocation);
        boolean hasViewer = blockMenu.hasViewer();

        int machineEnergy = Integer.parseInt(EnergyUtil.getCharge(config));
        if (machineEnergy == 0) {
            if(hasViewer) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                        "0", "0", "0", "0");
            }
            return;
        }

        Map<Integer, List<LocationWithConfig>> configMap = new HashMap<>(this.range * 3);
        int count = this.function(block, this.range, targetLocation -> {
            if (BlockStorage.hasBlockInfo(targetLocation)) {
                Config targetConfig = BlockStorage.getLocationInfo(targetLocation);
                if (targetConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                    SlimefunItem sfItem = SlimefunItem.getById(targetConfig.getString(ConstantTableUtil.CONFIG_ID));
                    if(sfItem instanceof EnergyNetComponent energyNetComponent && sfItem.getBlockTicker() != null && EnergyNetComponentType.CONSUMER.equals(energyNetComponent.getEnergyComponentType()) && energyNetComponent.getCapacity() > 0 && sfItem != EnergizedAccelerator.this) {
                        int distance = Math.abs(targetLocation.getBlockX() - blockLocation.getBlockX()) + Math.abs(targetLocation.getBlockY() - blockLocation.getBlockY()) + Math.abs(targetLocation.getBlockZ() - blockLocation.getBlockZ());
                        List<LocationWithConfig> configList = configMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                        configList.add(new LocationWithConfig(targetLocation.clone(), targetConfig));
                        return 1;
                    }
                }
            }
            return 0;
        });

        if (count == 0) {
            if(hasViewer) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                        "0", "0", "0", "0");
            }
            return;
        }
        int storedMachineEnergy = machineEnergy;

        int accelerateTotalTime = 0;
        int accelerateRoundTime = 0;

        String extraEnergyString = StringNumberUtil.ZERO;
        int extraEnergy;

        int validMachineCount = 0;
        for (Map.Entry<Integer, List<LocationWithConfig>> entry : configMap.entrySet()) {
            Iterator<LocationWithConfig> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                SlimefunItem sfItem = SlimefunItem.getById(iterator.next().getConfig().getString(ConstantTableUtil.CONFIG_ID));
                if (!(sfItem instanceof EnergyNetComponent)) {
                    iterator.remove();
                    continue;
                }
                extraEnergyString = StringNumberUtil.add(extraEnergyString, String.valueOf(((EnergyNetComponent) sfItem).getCapacity()));
                validMachineCount++;
            }
        }

        if (validMachineCount == 0) {
            if(hasViewer) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                        "0", "0", "0", "0");
            }
            return;
        }

        if (StringNumberUtil.compare(extraEnergyString, StringNumberUtil.INTEGER_MAX_VALUE) >= 0) {
            extraEnergy = Integer.MAX_VALUE / 2;
        } else {
            extraEnergy = Integer.parseInt(extraEnergyString);
        }

        machineEnergy -= extraEnergy;

        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        List<LocationWithConfig> locationConfigList;
        String machineItemId;
        while (machineEnergy > 0) {
            for (int distance = 1; distance <= this.range * 3; distance++) {
                locationConfigList = configMap.get(distance);
                if (locationConfigList != null) {
                    Collections.shuffle(locationConfigList);
                    for (LocationWithConfig locationConfig : locationConfigList) {
                        machineItemId = locationConfig.getConfig().getString(ConstantTableUtil.CONFIG_ID);
                        SlimefunItem machineItem = SlimefunItem.getById(locationConfig.getConfig().getString(ConstantTableUtil.CONFIG_ID));
                        if (machineItem instanceof EnergyNetComponent) {
                            BlockTicker blockTicker = machineItem.getBlockTicker();
                            if (blockTicker != null) {
                                if (blockTicker.isSynchronized()) {
                                    javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> blockTicker.tick(locationConfig.getLocation().getBlock(), machineItem, locationConfig.getConfig()));
                                } else {
                                    BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(machineItemId), () -> blockTicker.tick(locationConfig.getLocation().getBlock(), machineItem, locationConfig.getConfig()), locationConfig.getLocation());
                                }
                                if (hasViewer) {
                                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.GLOW, 0, locationConfig.getLocation().getBlock()));
                                }
                                accelerateTotalTime++;
                            }
                        }
                    }
                }
            }
            accelerateRoundTime++;
            machineEnergy /= validMachineCount;
            machineEnergy /= accelerateRoundTime;
            machineEnergy -= extraEnergy;

            hasViewer = false;
        }

        if (accelerateTotalTime > 0) {
            EnergyUtil.setCharge(config, 0);
        }

        if(blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                    String.valueOf(storedMachineEnergy),
                    String.valueOf(validMachineCount),
                    String.valueOf(accelerateRoundTime),
                    String.valueOf(accelerateTotalTime));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range),
                String.valueOf(this.getCapacity()));
    }
}
