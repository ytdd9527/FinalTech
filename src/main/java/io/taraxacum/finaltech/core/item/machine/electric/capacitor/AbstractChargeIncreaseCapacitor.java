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
public abstract class AbstractChargeIncreaseCapacitor extends AbstractElectricCapacitor {
    public AbstractChargeIncreaseCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public abstract int getCapacity();

    @Override
    public void addCharge(@Nonnull Location l, int charge) {
        charge *= this.getEfficient();
        super.addCharge(l, charge);
    }

    @Override
    public void setCharge(@Nonnull Location l, int charge) {
        int difference = charge - this.getCharge(l);
        if (difference > 0) {
            charge -= difference;
            difference *= this.getEfficient();
            charge += difference;
        }
        charge = Math.min(charge, this.getCapacity());
        super.setCharge(l, charge);
    }

    protected abstract int getEfficient();
}
