package io.taraxacum.finaltech.core.items.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class BasicGenerator extends AbstractCubeElectricGenerator {
    private final String electricity = FinalTech.getValueManager().getOrDefault("1", "items", SlimefunUtil.getIdFormatName(BasicGenerator.class), "electricity");
    private final int range = FinalTech.getValueManager().getOrDefault(2, "items", SlimefunUtil.getIdFormatName(BasicGenerator.class), "range");

    public BasicGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
