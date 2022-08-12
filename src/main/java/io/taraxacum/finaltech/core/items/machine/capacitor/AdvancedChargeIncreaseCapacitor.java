package io.taraxacum.finaltech.core.items.machine.capacitor;

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
public class AdvancedChargeIncreaseCapacitor extends BasicChargeIncreaseCapacitor {
    private final int capacity = FinalTech.getValueManager().getOrDefault(Integer.MAX_VALUE / 4, "capacitor", SlimefunUtil.getIdFormatName(AdvancedChargeIncreaseCapacitor.class), "capacity");
    private final int efficient = FinalTech.getValueManager().getOrDefault(4, "capacitor", SlimefunUtil.getIdFormatName(AdvancedChargeIncreaseCapacitor.class), "efficient");

    public AdvancedChargeIncreaseCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    protected int getEfficient() {
        return this.efficient;
    }
}
