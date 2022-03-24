package io.taraxacum.finaltech.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

public class MatrixExpandedCapacitor extends AbstractExpandedElectricCapacitor{
    public static final int CAPACITY = Integer.MAX_VALUE;
    public static final String STACK = "MAX";
    public MatrixExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public String getMaxStack() {
        return STACK;
    }
}
