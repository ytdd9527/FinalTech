package io.taraxacum.finaltech.machine.capacitor;

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
        charge *= EFFICIENT * (getCapacity() - getCharge(l)) / (double) getCapacity();
        super.addCharge(l, charge);
    }

    @Override
    public void setCharge(@Nonnull Location l, int charge) {
        int difference = charge - getCharge(l);
        if (difference > 0) {
            charge -= difference;
            difference *= EFFICIENT * (getCapacity() - getCharge(l)) / (double) getCapacity();
            charge += difference;
        }
        super.setCharge(l, charge);
    }
}
