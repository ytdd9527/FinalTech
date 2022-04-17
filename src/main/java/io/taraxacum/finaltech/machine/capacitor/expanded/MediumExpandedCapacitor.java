package io.taraxacum.finaltech.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class MediumExpandedCapacitor extends AbstractExpandedElectricCapacitor {
    public static final int CAPACITY = 512;
    public static final int STACK = 512;
    public static final double CHARGE_INCREASE = 1.1;
    public static final double CONSUME_REDUCE = 0.95;
    public MediumExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return CAPACITY * 2;
    }

    @Override
    public String getMaxStack() {
        return String.valueOf(STACK - 2);
    }

    @Override
    public double chargeIncrease() {
        return CHARGE_INCREASE;
    }

    @Override
    public double consumeReduce() {
        return CONSUME_REDUCE;
    }
}
