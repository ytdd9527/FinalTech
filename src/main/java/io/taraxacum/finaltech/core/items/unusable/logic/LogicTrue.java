package io.taraxacum.finaltech.core.items.unusable.logic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.LogicItem;
import io.taraxacum.finaltech.core.items.unusable.UnusableSlimefunItem;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LogicTrue extends UnusableSlimefunItem implements LogicItem {
    public LogicTrue(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public boolean getLogic() {
        return true;
    }
}
