package io.taraxacum.finaltech.core.items.machine.electric.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BasicConsumeReduceCapacitor extends AbstractElectricCapacitor implements RecipeItem {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(524288, this, "capacity");
    private final int efficient = ConfigUtil.getOrDefaultItemSetting(2, this, "efficient");

    public BasicConsumeReduceCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void removeCharge(@Nonnull Location l, int charge) {
        charge *= 1.0 / this.getEfficient();
        super.removeCharge(l, charge);
    }

    @Override
    public void setCharge(@Nonnull Location l, int charge) {
        int difference = charge - this.getCharge(l);
        if (difference < 0) {
            charge -= difference;
            difference *= 1.0 / this.getEfficient();
            charge += difference;
        }
        super.setCharge(l, charge);
    }

    protected int getEfficient() {
        return this.efficient;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.capacity),
                String.format("%.2f", (1.0 / this.efficient) * 100.0));
    }
}
