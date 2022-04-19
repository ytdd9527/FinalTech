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
import io.taraxacum.finaltech.menu.StatusL2Menu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author LJC
 */
public class MatrixAccelerator extends AbstractCubeMachine implements AntiAccelerationMachine {
    public static final int RANGE = 2;
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
        ItemStack matchItem = blockMenu.getItemInSlot(this.getInputSlots()[0]);

        int accelerate = 0;
        int range = RANGE;

        SlimefunItem machineItem = null;
        String machineId = null;
        BlockTicker blockTicker = null;

        if(ItemStackUtil.isItemNull(matchItem)) {
            accelerate = 1;
        } else if(ItemStackUtil.isItemSimilar(matchItem, FinalTechItems.FAKE)) {
            int amount = matchItem.getAmount();
            while (amount > 0) {
                accelerate++;
                range++;
                amount /= 2;
            }
        } else {
            machineItem = SlimefunItem.getByItem(matchItem);
            if(machineItem != null) {
                blockTicker = machineItem.getBlockTicker();
                if(blockTicker == null) {
                    this.updateMenu(blockMenu, 0, 0, StringNumberUtil.ZERO);
                    return;
                }
                machineId = machineItem.getId();
                accelerate = matchItem.getAmount();
            } else {
                this.updateMenu(blockMenu, 0, 0, StringNumberUtil.ZERO);
                return;
            }
        }

        Map<Integer, List<LocationWithConfig>> machineConfigMap = new HashMap<>(range * 3);
        String finalMachineId = machineId;
        int count = this.function(block, range, location -> {
            if(BlockStorage.hasBlockInfo(location)) {
                Config machineConfig = BlockStorage.getLocationInfo(location);
                if(machineConfig.contains(SlimefunUtil.KEY_ID)) {
                    if(finalMachineId == null || finalMachineId.equals(machineConfig.getString(SlimefunUtil.KEY_ID))) {
                        int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                        List<LocationWithConfig> machineConfigList = machineConfigMap.computeIfAbsent(distance, d -> new ArrayList(d * d * 4 + 2));
                        machineConfigList.add(new LocationWithConfig(location.clone(), machineConfig));
                        return 1;
                    }
                }
            }
            return 0;
        });

        int accelerateTimeCount = 0;
        int accelerateMachineCount = 0;
        String accelerateEnergy = StringNumberUtil.ZERO;

        boolean energyNetComponent = false;
        String machineCapacity = null;

        if(machineId != null) {
            if(count == 0) {
                updateMenu(blockMenu, 0, 0, StringNumberUtil.ZERO);
                return;
            }
            energyNetComponent = machineItem instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) machineItem).getEnergyComponentType());
            if(energyNetComponent) {
                machineCapacity = String.valueOf(((EnergyNetComponent) machineItem).getCapacity());
                if(StringNumberUtil.ZERO.equals(machineCapacity)) {
                    energyNetComponent = false;
                }
            }
            accelerate /= count;
        }

        Map<String, String> energyComponentCapacityMap = new HashMap<>(count);
        if(machineId == null) {
            count--;
            for(List<LocationWithConfig> locationConfigList : machineConfigMap.values()) {
                for(LocationWithConfig locationConfig : locationConfigList) {
                    Config machineConfig = locationConfig.getConfig();
                    machineId = machineConfig.getString(SlimefunUtil.KEY_ID);
                    if(energyComponentCapacityMap.containsKey(machineId)) {
                        continue;
                    }
                    machineItem = SlimefunItem.getById(machineId);
                    machineCapacity = StringNumberUtil.ZERO;
                    if(machineItem instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) machineItem).getEnergyComponentType())) {
                        machineCapacity = String.valueOf(((EnergyNetComponent) machineItem).getCapacity());
                    }
                    energyComponentCapacityMap.put(machineId, machineCapacity);
                }
            }
            machineId = null;
            machineItem = null;
        }

        for(int distance = 1; distance <= range * 3; distance++) {
            List<LocationWithConfig> locationConfigList = machineConfigMap.get(distance);
            if(locationConfigList != null) {
                Collections.shuffle(locationConfigList);
                if(machineId != null) {
                    for(LocationWithConfig locationConfig : locationConfigList) {
                        Config machineConfig = locationConfig.getConfig();
                        Location machineLocation = locationConfig.getLocation();
                        accelerateMachineCount++;
                        for(int i = 0; i < accelerate; i++) {
                            if(energyNetComponent) {
                                String machineEnergy = SlimefunUtil.getCharge(machineConfig);
                                String transferEnergy = StringNumberUtil.min(StringNumberUtil.sub(machineCapacity, machineEnergy), machineEnergy);
                                SlimefunUtil.setCharge(machineConfig, StringNumberUtil.add(machineEnergy, transferEnergy));
                                accelerateEnergy = StringNumberUtil.add(accelerateEnergy, transferEnergy);
                            }
                            SlimefunUtil.runBlockTicker(blockTicker, machineLocation.getBlock(), machineItem, machineConfig);
                            accelerateTimeCount++;
                        }
                    }
                } else {
                    for(LocationWithConfig locationConfig : locationConfigList) {
                        Config machineConfig = locationConfig.getConfig();
                        Location machineLocation = locationConfig.getLocation();
                        machineId = machineConfig.getString(SlimefunUtil.KEY_ID);
                        machineItem = SlimefunItem.getById(machineId);
                        machineCapacity = energyComponentCapacityMap.get(machineId);
                        blockTicker = machineItem.getBlockTicker();
                        if(blockTicker != null || !StringNumberUtil.ZERO.equals(machineCapacity)) {
                            accelerateMachineCount++;
                            for(int i = 0; i < accelerate; i++) {
                                if(blockTicker != null) {
                                    SlimefunUtil.runBlockTicker(blockTicker, machineLocation.getBlock(), machineItem, machineConfig);
                                }
                                if(!StringNumberUtil.ZERO.equals(machineCapacity)) {
                                    String machineEnergy = SlimefunUtil.getCharge(machineConfig);
                                    String transferEnergy = StringNumberUtil.min(StringNumberUtil.sub(machineCapacity, machineEnergy), machineEnergy);
                                    SlimefunUtil.setCharge(machineConfig, StringNumberUtil.add(machineEnergy, transferEnergy));
                                    accelerateEnergy = StringNumberUtil.add(accelerateEnergy, transferEnergy);
                                }
                                accelerateTimeCount++;
                            }
                        }
                    }
                    machineId = null;
                    machineItem = null;
                }
            }
        }

        this.updateMenu(blockMenu, accelerateTimeCount, accelerateMachineCount, accelerateEnergy);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int accelerateTimeCount, int accelerateMachineCount, String accelerateEnergy) {
        ItemStack item = blockMenu.getItemInSlot(StatusL2Menu.CENTER_SLOT);
        ItemStackUtil.setLore(item,
                "§7生效的机器= " + accelerateMachineCount,
                "§7生效的总次数= " + accelerateTimeCount,
                "§7产生的电量= " + accelerateEnergy + "J");
    }
}
