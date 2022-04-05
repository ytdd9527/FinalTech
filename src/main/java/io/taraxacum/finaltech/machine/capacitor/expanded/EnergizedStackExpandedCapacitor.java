package io.taraxacum.finaltech.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class EnergizedStackExpandedCapacitor extends AbstractExpandedElectricCapacitor{
    public static final int CAPACITY = 536870912;
    public static final int STACK = 536870912;
    public EnergizedStackExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public String getMaxStack() {
        return String.valueOf(STACK - 2);
    }
}
