package io.taraxacum.finaltech.core.items.machine.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedChargeIncreaseCapacitor extends BasicChargeIncreaseCapacitor implements RecipeItem {
    public static final int CAPACITOR = Integer.MAX_VALUE / 4;
    public static final int EFFICIENT = 4;

    public AdvancedChargeIncreaseCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return CAPACITOR;
    }

    @Override
    protected int getEfficient() {
        return EFFICIENT;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "可存储电量 " + TextUtil.COLOR_NUMBER + CAPACITOR + "J",
                TextUtil.COLOR_NORMAL + "充电效率 " + TextUtil.COLOR_NUMBER + String.format("%.2f", EFFICIENT * 100.0) + "%");
    }
}
