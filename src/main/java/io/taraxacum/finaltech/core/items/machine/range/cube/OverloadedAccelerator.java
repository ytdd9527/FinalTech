package io.taraxacum.finaltech.core.items.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
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
    public static final int RANGE = FinalTech.getValueManager().getOrDefault(2, "items", SlimefunUtil.getIdFormatName(OverloadedAccelerator.class), "range");;

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
                    List<LocationWithConfig> componentConfigList = componentConfigMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                    componentConfigList.add(new LocationWithConfig(location.clone(), componentConfig));
                    return 1;
                }
            }
            return 0;
        });

        count--; // not include itself
        int accelerateMachineCount = 0;

        List<LocationWithConfig> locationConfigList;
        SlimefunItem sfItem;
        Block componentBlock;
        for (int distance = 1; distance <= RANGE * 3; distance++) {
            locationConfigList = componentConfigMap.get(distance);
            if (locationConfigList != null) {
                Collections.shuffle(locationConfigList);
                for (LocationWithConfig locationConfig : locationConfigList) {
                    Config componentConfig = locationConfig.getConfig();
                    sfItem = SlimefunItem.getById(componentConfig.getString(SlimefunUtil.KEY_ID));
                    if(sfItem instanceof EnergyNetComponent) {
                        BlockTicker blockTicker = sfItem.getBlockTicker();
                        if (blockTicker != null) {
                            int componentCapacity = ((EnergyNetComponent) sfItem).getCapacity();
                            int componentEnergy = Integer.parseInt(SlimefunUtil.getCharge(componentConfig));
                            if (componentCapacity > 0 && componentEnergy > componentCapacity) {
                                accelerateMachineCount++;
                                componentBlock = locationConfig.getLocation().getBlock();
                                BlockTicker finalBlockTicker = blockTicker;
                                blockTicker = new BlockTicker() {
                                    @Override
                                    public boolean isSynchronized() {
                                        return finalBlockTicker.isSynchronized();
                                    }

                                    @Override
                                    public void tick(Block b, SlimefunItem item, Config data) {
                                        int fComponentEnergy = componentEnergy;
                                        while (fComponentEnergy > componentCapacity) {
                                            finalBlockTicker.tick(b, item, data);
                                            fComponentEnergy = Integer.parseInt(SlimefunUtil.getCharge(componentConfig));
                                            fComponentEnergy /= 2;
                                            SlimefunUtil.setCharge(componentConfig, fComponentEnergy);
                                        }
                                    }
                                };
                                if(FinalTech.getForceSlimefunMultiThread() && FinalTech.getMultiThreadLevel() >= 1) {
                                    SlimefunUtil.runBlockTicker(FinalTech.getLocationRunnableFactory(), blockTicker, componentBlock, sfItem, componentConfig, locationConfig.getLocation());
                                } else {
                                    SlimefunUtil.runBlockTickerLocal(this.getAddon().getJavaPlugin(), blockTicker, componentBlock, sfItem, componentConfig);
                                }
                            }
                        }
                    }
                }
            }
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if(blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, accelerateMachineCount);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int accelerateMachineCount) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, SlimefunUtil.updateMenuLore(FinalTech.getLanguageManager(), this, String.valueOf(accelerateMachineCount)));
        ItemStackUtil.setLore(item,
                TextUtil.COLOR_NORMAL + "检测到的机器个数= " + TextUtil.COLOR_NUMBER + accelerateMachineCount + "个");
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                TextUtil.COLOR_NORMAL + "对于周围 " + TextUtil.COLOR_NUMBER + RANGE + "格" + TextUtil.COLOR_NORMAL + " 的耗电机器",
                TextUtil.COLOR_NORMAL + "对其进行加速",
                TextUtil.COLOR_NORMAL + "每次加速时 使其存电量减半 直至其存电量小于最大电容量");
    }
}
