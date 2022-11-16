package io.taraxacum.finaltech.core.items.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.LocationWithConfig;
import io.taraxacum.libs.slimefun.util.BlockTickerUtil;
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
public class OverloadedAccelerator extends AbstractCubeMachine implements RecipeItem {
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    public OverloadedAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        boolean drawParticle = blockMenu.hasViewer();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        Map<Integer, List<LocationWithConfig>> componentConfigMap = new HashMap<>(range * 3);
        int count = this.function(block, range, location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config componentConfig = BlockStorage.getLocationInfo(location);
                if (componentConfig.contains(ConstantTableUtil.CONFIG_ID) && SlimefunItem.getById(componentConfig.getString(ConstantTableUtil.CONFIG_ID)) instanceof EnergyNetComponent) {
                    int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                    List<LocationWithConfig> componentConfigList = componentConfigMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                    componentConfigList.add(new LocationWithConfig(location.clone(), componentConfig));
                    return 1;
                }
            }
            return 0;
        });

        if (count < 1) {
            this.updateMenu(blockMenu, 0, 0);
            return;
        }
        int accelerateMachineCount = 0;

        List<LocationWithConfig> locationConfigList;
        for (int distance = 1; distance <= this.range * 3; distance++) {
            locationConfigList = componentConfigMap.get(distance);
            if (locationConfigList != null) {
                Collections.shuffle(locationConfigList);
                for (LocationWithConfig locationConfig : locationConfigList) {
                    Config machineConfig = locationConfig.getConfig();
                    SlimefunItem machineItem = SlimefunItem.getById(machineConfig.getString(ConstantTableUtil.CONFIG_ID));
                    if (machineItem instanceof EnergyNetComponent && machineItem.getBlockTicker() != null) {
                        BlockTicker blockTicker = machineItem.getBlockTicker();
                        int componentCapacity = ((EnergyNetComponent) machineItem).getCapacity();
                        int componentEnergy = Integer.parseInt(EnergyUtil.getCharge(machineConfig));
                        if (componentCapacity > 0 && componentEnergy > componentCapacity) {
                            accelerateMachineCount++;
                            Block machineBlock = locationConfig.getLocation().getBlock();
                            if (blockTicker.isSynchronized()) {
                                javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                                    int machineEnergy = componentEnergy;
                                    while (machineEnergy > componentCapacity) {
                                        blockTicker.tick(machineBlock, machineItem, machineConfig);
                                        machineEnergy = Integer.parseInt(EnergyUtil.getCharge(machineConfig));
                                        machineEnergy /= 2;
                                        EnergyUtil.setCharge(machineConfig, machineEnergy);
                                    }
                                });
                            } else {
                                BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(machineItem.getId()), () -> {
                                    int machineEnergy = componentEnergy;
                                    while (machineEnergy > componentCapacity) {
                                        blockTicker.tick(machineBlock, machineItem, machineConfig);
                                        machineEnergy = Integer.parseInt(EnergyUtil.getCharge(machineConfig));
                                        machineEnergy /= 2;
                                        EnergyUtil.setCharge(machineConfig, machineEnergy);
                                    }
                                }, locationConfig.getLocation());
                            }
                            if (drawParticle) {
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, locationConfig.getLocation().getBlock()));
                            }
                        }
                    }
                }
            }
        }

        this.updateMenu(blockMenu, count, accelerateMachineCount);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int count, int accelerateMachineCount) {
        if (blockMenu.hasViewer()) {
            ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
            ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                    String.valueOf(count),
                    String.valueOf(accelerateMachineCount)));
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range));
    }
}
