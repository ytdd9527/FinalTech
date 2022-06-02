package io.taraxacum.finaltech.core.items.machine.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class BasicConsumeReduceCapacitor extends AbstractElectricCapacitor implements RecipeItem {
    public static final int CAPACITOR = 524288;
    public static final int EFFICIENT = 2;
    public BasicConsumeReduceCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return CAPACITOR;
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
        return EFFICIENT;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "可存储电量 " + TextUtil.COLOR_NUMBER + CAPACITOR + "J",
                TextUtil.COLOR_NORMAL + "耗电效率 " + TextUtil.COLOR_NUMBER + String.format("%.2f", (1.0 / EFFICIENT) * 100.0) + "%");
    }
}
