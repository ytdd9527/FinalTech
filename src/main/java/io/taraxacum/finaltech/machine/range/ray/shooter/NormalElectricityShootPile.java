package io.taraxacum.finaltech.machine.range.ray.shooter;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.common.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class NormalElectricityShootPile extends AbstractElectricityShootPile{
    public static final int RANGE = 16;

    public NormalElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
            if (summary.getCapacitorEnergy() <= 0) {
                return -1;
            }
            if (BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if (energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem energyComponentItem = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                    if (energyComponentItem instanceof EnergyNetComponent) {
                        int componentEnergy = Integer.parseInt(SlimefunUtil.getCharge(energyComponentConfig));
                        int componentCapacity = ((EnergyNetComponent) energyComponentItem).getCapacity();
                        if (componentEnergy >= componentCapacity) {
                            return -1;
                        }
                        int transferEnergy = Math.min(summary.getCapacitorEnergy(), componentCapacity - componentEnergy);
                        SlimefunUtil.setCharge(location, String.valueOf(componentEnergy + transferEnergy));
                        summary.setCapacitorEnergy(summary.getCapacitorEnergy() - transferEnergy);
                        summary.setEnergyCharge(StringNumberUtil.add(summary.getEnergyCharge(), String.valueOf(transferEnergy)));
                        return -1;
                    }
                }
            }
            return 0;
        };
    }

    @Override
    protected void updateMenu(ItemStack item, int count, Summary summary) {
        //todo 优化说明
        ItemStackUtil.setLore(item, "§7实际传输电量= " + summary.getEnergyCharge());
    }
}
