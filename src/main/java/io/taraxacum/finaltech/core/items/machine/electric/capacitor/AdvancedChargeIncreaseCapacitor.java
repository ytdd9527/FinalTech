package io.taraxacum.finaltech.core.items.machine.electric.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedChargeIncreaseCapacitor extends BasicChargeIncreaseCapacitor {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE / 4, this, "capacity");
    private final int efficient = ConfigUtil.getOrDefaultItemSetting(4, this, "efficient");

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
