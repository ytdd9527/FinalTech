package io.taraxacum.finaltech.core.item.machine.electric.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractConsumeReduceCapacitor extends AbstractElectricCapacitor {
    public AbstractConsumeReduceCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public abstract int getCapacity();

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

    protected abstract int getEfficient();
}
