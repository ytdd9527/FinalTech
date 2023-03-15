package io.taraxacum.finaltech.core.item.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.LocationMachine;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class OverloadedAccelerator extends AbstractCubeMachine implements RecipeItem, MenuUpdater, LocationMachine {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    public OverloadedAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.notAllowedId.add(this.getId());
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
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        boolean hasViewer = blockMenu.hasViewer();

        Map<Integer, List<LocationInfo>> locationInfoMap = new HashMap<>(this.range * 3);
        int count = this.cubeFunction(block, this.range, location -> {
            LocationInfo locationInfo = LocationInfo.get(location);
            if(locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && locationInfo.getSlimefunItem().getBlockTicker() != null && energyNetComponent.isChargeable()) {
                int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                locationInfoMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2)).add(locationInfo);
                locationInfo.cloneLocation();
                return 1;
            }
            return 0;
        });

        if (count == 0) {
            if(hasViewer) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                        "0", "0");
            }
            return;
        }

        int accelerateCount = 0;
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        List<LocationInfo> locationInfoList;
        for (int distance = 1; distance <= this.range * 3; distance++) {
            locationInfoList = locationInfoMap.get(distance);
            if (locationInfoList != null) {
                Collections.shuffle(locationInfoList);
                for (LocationInfo locationInfo : locationInfoList) {
                    BlockTicker blockTicker = locationInfo.getSlimefunItem().getBlockTicker();
                    EnergyNetComponent energyNetComponent = (EnergyNetComponent) locationInfo.getSlimefunItem();
                    if(blockTicker != null && locationInfo.getId().equals(locationInfo.getConfig().getString(ConstantTableUtil.CONFIG_ID))) {
                        int capacity = energyNetComponent.getCapacity();
                        int energy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getConfig()));
                        if (energy > capacity) {
                            accelerateCount++;
                            Block machineBlock = locationInfo.getLocation().getBlock();

                            Runnable runnable = () -> {
                                int machineEnergy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getConfig()));
                                while (machineEnergy > capacity) {
                                    blockTicker.tick(machineBlock, locationInfo.getSlimefunItem(), locationInfo.getConfig());
                                    machineEnergy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getConfig()));
                                    machineEnergy -= capacity;
                                    EnergyUtil.setCharge(locationInfo.getConfig(), machineEnergy);
                                }
                            };

                            if (blockTicker.isSynchronized()) {
                                javaPlugin.getServer().getScheduler().runTask(javaPlugin, runnable);
                            } else {
                                BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(locationInfo.getId()), runnable, locationInfo.getLocation());
                            }

                            if (hasViewer) {
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.GLOW, 0, locationInfo.getLocation().getBlock()));
                            }
                        }
                    }
                }
            }
        }

        if(hasViewer) {
            this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                    String.valueOf(count),
                    String.valueOf(accelerateCount));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range));
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        int i = 0;
        Location location = sourceLocation.clone();
        World world = location.getWorld();
        int minX = location.getBlockX() - this.range;
        int minY = Math.max(location.getBlockY() - this.range, world.getMinHeight());
        int minZ = location.getBlockZ() - this.range;
        int maxX = location.getBlockX() + this.range;
        int maxY  = Math.min(location.getBlockY() + this.range, world.getMaxHeight());
        int maxZ = location.getBlockZ() + this.range;
        Location[] locations = new Location[(maxX - minX + 1) * (maxY - minY + 1) + (maxZ - minZ + 1)];
        for (int x = minX; x <= maxX; x++) {
            location.setX(x);
            for (int y = minY; y <= maxY; y++) {
                location.setY(y);
                for (int z = minZ; z <= maxZ; z++) {
                    location.setZ(z);
                    locations[i++] = location.clone();
                }
            }
        }

        return locations;
    }
}
