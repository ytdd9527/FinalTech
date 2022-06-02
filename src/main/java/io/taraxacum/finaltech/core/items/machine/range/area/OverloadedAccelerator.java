package io.taraxacum.finaltech.core.items.machine.range.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
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
public class OverloadedAccelerator extends AbstractCubeMachine implements AntiAccelerationMachine, RecipeItem {
    public static final int RANGE = 2;

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
        Map<Integer, List<LocationWithConfig>> componentConfigMap = new HashMap<>(RANGE * 3);
        Location blockLocation = block.getLocation();
        int count = this.function(block, RANGE, location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config componentConfig = BlockStorage.getLocationInfo(location);
                if (componentConfig.contains(SlimefunUtil.KEY_ID)) {
                    int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                    List<LocationWithConfig> componentConfigList = componentConfigMap.computeIfAbsent(distance, d -> new ArrayList(d * d * 4 + 2));
                    componentConfigList.add(new LocationWithConfig(location.clone(), componentConfig));
                    return 1;
                }
            }
            return 0;
        });

        count--; // not include itself
        int accelerateTimeCount = 0;
        int accelerateMachineCount = 0;

        for (int distance = 1; distance <= RANGE * 3; distance++) {
            List<LocationWithConfig> locationConfigList = componentConfigMap.get(distance);
            if (locationConfigList != null) {
                Collections.shuffle(locationConfigList);
                for (LocationWithConfig locationConfig : locationConfigList) {
                    Config componentConfig = locationConfig.getConfig();
                    SlimefunItem item = SlimefunItem.getById(componentConfig.getString(SlimefunUtil.KEY_ID));
                    if (item instanceof EnergyNetComponent) {
                        int componentCapacity = ((EnergyNetComponent) item).getCapacity();
                        if (componentCapacity > 0) {
                            accelerateMachineCount++;
                            int componentEnergy = Integer.parseInt(SlimefunUtil.getCharge(componentConfig));
                            if (componentEnergy > componentCapacity) {
                                BlockTicker blockTicker = item.getBlockTicker();
                                if (blockTicker != null) {
                                    Block componentBlock = locationConfig.getLocation().getBlock();
                                    while (componentEnergy > componentCapacity) {
                                        accelerateTimeCount++;
                                        if (blockTicker.isSynchronized()) {
                                            Slimefun.runSync(() -> blockTicker.tick(componentBlock, item, componentConfig));
                                        } else if (!blockTicker.isSynchronized()) {
                                            blockTicker.tick(componentBlock, item, componentConfig);
                                        }
                                        componentEnergy = Integer.parseInt(SlimefunUtil.getCharge(componentConfig));
                                        componentEnergy /= 2;
                                        SlimefunUtil.setCharge(componentConfig, componentEnergy);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item,
                TextUtil.COLOR_NORMAL + "检测到的机器个数= " + TextUtil.COLOR_NUMBER + accelerateMachineCount + "个",
                TextUtil.COLOR_NORMAL + "加速次数= " + TextUtil.COLOR_NUMBER + accelerateTimeCount + "次");
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                TextUtil.COLOR_NORMAL + "对于周围 " + TextUtil.COLOR_NUMBER + RANGE + "格" + TextUtil.COLOR_NORMAL + " 的耗电机器",
                TextUtil.COLOR_NORMAL + "对其进行加速",
                TextUtil.COLOR_NORMAL + "每次加速时 使其存电量减半 直至其存电量小于最大电容量");
    }
}
