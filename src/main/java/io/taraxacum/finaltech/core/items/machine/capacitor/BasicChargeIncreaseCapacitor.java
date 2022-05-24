package io.taraxacum.finaltech.core.items.machine.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class BasicChargeIncreaseCapacitor extends AbstractElectricCapacitor {
    public static final int CAPACITOR = 524288;
    public static final int EFFICIENT = 2;
    public BasicChargeIncreaseCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return CAPACITOR;
    }

    @Override
    public void addCharge(@Nonnull Location l, int charge) {
        charge *= this.getEfficient() * (this.getCapacity() - this.getCharge(l)) / (double) this.getCapacity();
        super.addCharge(l, charge);
    }

    @Override
    public void setCharge(@Nonnull Location l, int charge) {
        int difference = charge - this.getCharge(l);
        if (difference > 0) {
            charge -= difference;
            difference *= this.getEfficient() * (this.getCapacity() - this.getCharge(l)) / (double) this.getCapacity();
            charge += difference;
        }
        super.setCharge(l, charge);
    }

    protected int getEfficient() {
        return EFFICIENT;
    }
}
