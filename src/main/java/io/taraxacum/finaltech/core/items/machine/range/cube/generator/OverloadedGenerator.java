package io.taraxacum.finaltech.core.items.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.ConfigUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class OverloadedGenerator extends AbstractCubeElectricGenerator{
    private final String electricity = ConfigUtil.getOrDefaultItemSetting("2048", this, "electricity");
    private final int range = ConfigUtil.getOrDefaultItemSetting(4, this, "range");

    public OverloadedGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
