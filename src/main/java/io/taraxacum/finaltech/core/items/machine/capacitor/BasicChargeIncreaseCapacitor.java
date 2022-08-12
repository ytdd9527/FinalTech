package io.taraxacum.finaltech.core.items.machine.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BasicChargeIncreaseCapacitor extends AbstractElectricCapacitor implements RecipeItem {
    private final int capacity = FinalTech.getValueManager().getOrDefault(524288, "capacitor", SlimefunUtil.getIdFormatName(BasicChargeIncreaseCapacitor.class), "capacity");
    private final int efficient = FinalTech.getValueManager().getOrDefault(2, "capacitor", SlimefunUtil.getIdFormatName(BasicChargeIncreaseCapacitor.class), "efficient");

    public BasicChargeIncreaseCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

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

    protected int getEfficient() {
        return this.efficient;
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.capacity),
                String.format("%.2f", this.efficient * 100.0));
    }
}
