package io.taraxacum.finaltech.core.items.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MediumExpandedCapacitor extends AbstractExpandedElectricCapacitor {
    public final int capacity = FinalTech.getValueManager().getOrDefault(512, "capacitor", SlimefunUtil.getIdFormatName(MediumExpandedCapacitor.class), "capacity");
    public final int stack = FinalTech.getValueManager().getOrDefault(512, "capacitor", SlimefunUtil.getIdFormatName(MediumExpandedCapacitor.class), "stack");
    public final double chargeIncrease = FinalTech.getValueManager().getOrDefault(1.1, "capacitor", SlimefunUtil.getIdFormatName(MediumExpandedCapacitor.class), "charge-increase");
    public final double consumeReduce = FinalTech.getValueManager().getOrDefault(0.95, "capacitor", SlimefunUtil.getIdFormatName(MediumExpandedCapacitor.class), "consume-reduce");

    public MediumExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return this.capacity * 2;
    }

    @Nonnull
    @Override
    public String getMaxStack() {
        return String.valueOf(this.stack - 2);
    }

    @Override
    public double chargeIncrease() {
        return this.chargeIncrease;
    }

    @Override
    public double consumeReduce() {
        return this.consumeReduce;
    }
}
