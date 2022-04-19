package io.taraxacum.finaltech.machine.range.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.LocationWithConfig;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 */
public class EnergizedAccelerator extends AbstractCubeMachine implements AntiAccelerationMachine, EnergyNetComponent {
    private static final int RANGE = 2;
    private static final int CAPACITY = Integer.MAX_VALUE / 4;
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
        Map<Integer, List<LocationWithConfig>> machineConfigMap = new HashMap<>(RANGE * 3);
        Location blockLocation = block.getLocation();
        int count = this.function(block, RANGE, location -> {
            if(BlockStorage.hasBlockInfo(location)) {
                Config machineConfig = BlockStorage.getLocationInfo(location);
                if(machineConfig.contains(SlimefunUtil.KEY_ID)) {
                    int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                    List<LocationWithConfig> machineConfigList = machineConfigMap.computeIfAbsent(distance, d -> new ArrayList(d * d * 4 + 2));
                    machineConfigList.add(new LocationWithConfig(location.clone(), machineConfig));
                    return 1;
                }
            }
            return 0;
        });

        BlockMenu blockMenu = BlockStorage.getInventory(blockLocation);
        int machineEnergy = Integer.parseInt(SlimefunUtil.getCharge(config));
        if(count <= 1) {
            this.updateMenu(blockMenu, machineEnergy, 0, 0, 0);
            return;
        }
        int accelerateTime = 0;
        int energy = machineEnergy / count;
        int accelerateEachTime = 0;

        while (energy > 0) {
            for (int distance = 1; distance <= RANGE * 3; distance++) {
                List<LocationWithConfig> locationConfigList = machineConfigMap.get(distance);
                if (locationConfigList != null) {
                    Collections.shuffle(locationConfigList);
                    for (LocationWithConfig locationConfig : locationConfigList) {
                        Config machineConfig = locationConfig.getConfig();
                        SlimefunItem machineItem = SlimefunItem.getById(machineConfig.getString(SlimefunUtil.KEY_ID));
                        BlockTicker blockTicker = machineItem.getBlockTicker();
                        if (blockTicker != null) {
                            SlimefunUtil.runBlockTicker(blockTicker, locationConfig.getLocation().getBlock(), machineItem, machineConfig);
                            accelerateTime++;
                        }
                    }
                }
            }
            accelerateEachTime++;
            energy /= count;
            energy /= accelerateEachTime;
        }

        SlimefunUtil.setCharge(config, energy);

        this.updateMenu(blockMenu, machineEnergy, count - 1, accelerateEachTime, accelerateTime);
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
        return CAPACITY;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int machineEnergy, int accelerateMachine, int accelerateEachTime, int accelerateTime) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.CENTER_SLOT);
        ItemStackUtil.setLore(item,
                "§7当前电量= " + machineEnergy,
                "§7加速机器个数= " + accelerateMachine,
                "§7单机器加速次数= " + accelerateEachTime,
                "§7总加速次数= " + accelerateTime);

    }
}
