package io.taraxacum.finaltech.core.items.unusable.laquid;

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
public class WaterCard extends LiquidCard implements RecipeItem {
    public WaterCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_POSITIVE + "说明",
                "",
                TextUtil.COLOR_NORMAL + "可以在部分机器的合成配方中代替水桶",
                TextUtil.COLOR_NEGATIVE + "无法使用");
    }
}
