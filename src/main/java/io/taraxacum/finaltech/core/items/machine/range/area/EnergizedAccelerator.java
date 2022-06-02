package io.taraxacum.finaltech.core.items.machine.range.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.api.dto.LocationWithConfig;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.TextUtil;
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
 * @since 2.0
 */
public class EnergizedAccelerator extends AbstractCubeMachine implements AntiAccelerationMachine, EnergyNetComponent, RecipeItem {
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
        Location blockLocation = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(blockLocation);
        int machineEnergy = Integer.parseInt(SlimefunUtil.getCharge(config));
        final int finalMachineEnergy = machineEnergy;
        if(machineEnergy == 0) {
            this.updateMenu(blockMenu, 0, 0, 0, 0);
            return;
        }

        Map<Integer, List<LocationWithConfig>> machineConfigMap = new HashMap<>(RANGE * 3);
        int count = this.function(block, RANGE, location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config machineConfig = BlockStorage.getLocationInfo(location);
                if (machineConfig.contains(SlimefunUtil.KEY_ID)) {
                    int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                    List<LocationWithConfig> machineConfigList = machineConfigMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                    machineConfigList.add(new LocationWithConfig(location.clone(), machineConfig));
                    return 1;
                }
            }
            return 0;
        });

        if (count <= 1) {
            this.updateMenu(blockMenu, machineEnergy, 0, 0, 0);
            return;
        }

        int accelerateTime = 0;
        int accelerateEachTime = 0;

        String extraEnergyConsume = StringNumberUtil.ZERO;
        int extraEnergy;

        for(Map.Entry<Integer, List<LocationWithConfig>> entry : machineConfigMap.entrySet()) {
            Iterator<LocationWithConfig> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                LocationWithConfig locationConfig = iterator.next();
                if(locationConfig.getLocation().equals(blockLocation)) {
                    iterator.remove();
                    continue;
                }
                SlimefunItem sfItem = SlimefunItem.getById(locationConfig.getConfig().getString(SlimefunUtil.KEY_ID));
                if(sfItem instanceof EnergyNetComponent) {
                    extraEnergyConsume = StringNumberUtil.add(extraEnergyConsume, String.valueOf(((EnergyNetComponent) sfItem).getCapacity()));
                } else {
                    iterator.remove();
                }
            }
        }

        if(StringNumberUtil.compare(extraEnergyConsume, StringNumberUtil.INTEGER_MAX_VALUE) >= 0) {
            extraEnergy = Integer.MAX_VALUE / 2;
        } else {
            extraEnergy = Integer.parseInt(extraEnergyConsume);
        }

        machineEnergy -= extraEnergy;

        while (machineEnergy > 0) {
            for (int distance = 1; distance <= RANGE * 3; distance++) {
                List<LocationWithConfig> locationConfigList = machineConfigMap.get(distance);
                if (locationConfigList != null) {
                    Collections.shuffle(locationConfigList);
                    for (LocationWithConfig locationConfig : locationConfigList) {
                        SlimefunItem machineItem = SlimefunItem.getById(locationConfig.getConfig().getString(SlimefunUtil.KEY_ID));
                        BlockTicker blockTicker = machineItem.getBlockTicker();
                        if (blockTicker != null) {
                            SlimefunUtil.runBlockTicker(blockTicker, locationConfig.getLocation().getBlock(), machineItem, locationConfig.getConfig());
                            accelerateTime++;
                        }
                    }
                }
            }
            accelerateEachTime++;
            machineEnergy /= count - 1;
            machineEnergy /= accelerateEachTime;
            machineEnergy -= extraEnergy;
        }

        SlimefunUtil.setCharge(config, 0);
        this.updateMenu(blockMenu, finalMachineEnergy, count - 1, accelerateEachTime, accelerateTime);
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
        if(blockMenu.hasViewer()) {
            ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
            ItemStackUtil.setLore(item,
                    TextUtil.COLOR_NORMAL + "当前电量= " + TextUtil.COLOR_NUMBER + machineEnergy + "J",
                    TextUtil.COLOR_NORMAL + "加速机器个数= " + TextUtil.COLOR_NUMBER + accelerateMachine + "个",
                    TextUtil.COLOR_NORMAL + "单机器加速次数= " + TextUtil.COLOR_NUMBER + accelerateEachTime + "次",
                    TextUtil.COLOR_NORMAL + "总加速次数= " + TextUtil.COLOR_NUMBER + accelerateTime + "次");
        }
    }
    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "根据周围机器的最大电容量",
                TextUtil.COLOR_NORMAL + "以及自身存储的电量",
                TextUtil.COLOR_NORMAL + "使周围 " + TextUtil.COLOR_NUMBER + RANGE + "格" + TextUtil.COLOR_NORMAL + " 的机器效率提升",
                "",
                TextUtil.COLOR_NORMAL + "可存储电量= " + TextUtil.COLOR_NUMBER + this.getCapacity() + "J");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制详细",
                "",
                TextUtil.COLOR_NORMAL + "1.统计周围范围内所有的耗电机器机器最大电容量之和 E",
                TextUtil.COLOR_NORMAL + "2.自身存电量 减去 E",
                TextUtil.COLOR_NORMAL + "3.自身周围的耗电机器额外工作一次",
                TextUtil.COLOR_NORMAL + "4.自身存电量除以 加速机器个数",
                TextUtil.COLOR_NORMAL + "5.自身存电量除以 当前加速轮数",
                TextUtil.COLOR_NORMAL + "以此往复循环 直至自身电量为空");
    }
}
