package io.taraxacum.finaltech.machine.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class MatrixGenerator extends AbstractAreaElectricGenerator {
    public final static int ELECTRICITY = Integer.MAX_VALUE - 1;
    public final static int RANGE = 16;
    public MatrixGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    int getElectricity() {
        return ELECTRICITY;
    }

    @Override
    int getRange() {
        return RANGE;
    }
}
