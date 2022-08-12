package io.taraxacum.finaltech.core.items.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EnergizedStackGenerator extends AbstractCubeElectricGenerator {
    private final String electricity = FinalTech.getValueManager().getOrDefault("512", "items", SlimefunUtil.getIdFormatName(EnergizedStackGenerator.class), "electricity");
    private final int range = FinalTech.getValueManager().getOrDefault(6, "items", SlimefunUtil.getIdFormatName(EnergizedStackGenerator.class), "range");

    public EnergizedStackGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected String getElectricity() {
        return this.electricity;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
