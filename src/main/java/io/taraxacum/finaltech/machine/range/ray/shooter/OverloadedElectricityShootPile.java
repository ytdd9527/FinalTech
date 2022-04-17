package io.taraxacum.finaltech.machine.range.ray.shooter;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class OverloadedElectricityShootPile extends AbstractElectricityShootPile {
    public static final int RANGE = 16;

    public OverloadedElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        //todo 添加说明
    }

    @Override
    public int getRange() {
        return RANGE;
    }

    @Override
    protected Function doFunction(Summary summary) {
        return location -> {
            if(summary.getCapacitorEnergy() <= 0) {
                return -1;
            }
            if(BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if(energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem energyComponentItem = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                    if(energyComponentItem instanceof EnergyNetComponent) {
                        int componentEnergy = Integer.parseInt(SlimefunUtil.getCharge(energyComponentConfig));
                        int componentCapacity = Integer.MAX_VALUE;
                        int transferEnergy = Math.max(componentCapacity - componentEnergy, 0) / 2;
                        if(transferEnergy == 0) {
                            return 0;
                        }
                        transferEnergy = Math.min(transferEnergy, summary.getCapacitorEnergy());
                        SlimefunUtil.setCharge(location, String.valueOf(componentEnergy + transferEnergy * 2));
                        summary.setCapacitorEnergy(summary.getCapacitorEnergy() - transferEnergy);
                        //todo 性能优化
                        summary.setEnergyCharge(StringNumberUtil.add(summary.getEnergyCharge(), String.valueOf(transferEnergy)));
                        summary.setEnergyCharge(StringNumberUtil.add(summary.getEnergyCharge(), String.valueOf(transferEnergy)));
                        return 1;
                    }
                }
            }
            return 0;
        };
    }
}
