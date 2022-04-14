package io.taraxacum.finaltech.machine.range.area.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class EnergizedGenerator extends AbstractCubeElectricGenerator {
    public final static String ELECTRICITY = "256";
    public final static int RANGE = 8;
    public EnergizedGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected String getElectricity() {
        return ELECTRICITY;
    }

    @Override
    protected int getRange() {
        return RANGE;
    }
}