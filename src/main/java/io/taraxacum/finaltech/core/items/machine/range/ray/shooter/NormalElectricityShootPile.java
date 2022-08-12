package io.taraxacum.finaltech.core.items.machine.range.ray.shooter;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class NormalElectricityShootPile extends AbstractElectricityShootPile implements AntiAccelerationMachine {
    public final Integer range = FinalTech.getValueManager().getOrDefault(16, "items", SlimefunUtil.getIdFormatName(NormalElectricityShootPile.class), "range");

    public NormalElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    protected Function doFunction(@Nonnull Summary summary) {
        return location -> {
            if (summary.getCapacitorEnergy() <= 0) {
                return -1;
            }
            if (BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if (energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem energyComponentItem = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                    if (energyComponentItem instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) energyComponentItem).getEnergyComponentType())) {
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
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range));
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "将自身背向的电容的电量",
                TextUtil.COLOR_NORMAL + "传输给自身面向的 " + TextUtil.COLOR_NUMBER + this.getRange() +"格" + TextUtil.COLOR_NORMAL + " 范围内的第一个机器",
                TextUtil.COLOR_NEGATIVE + "无法传电至电容");
    }
}
