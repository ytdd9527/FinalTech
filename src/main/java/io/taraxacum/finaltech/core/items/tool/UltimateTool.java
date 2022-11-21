package io.taraxacum.finaltech.core.items.tool;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.AbstractMySlimefunItem;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class UltimateTool extends AbstractMySlimefunItem {
    public UltimateTool(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler((ToolUseHandler) (blockBreakEvent, itemStack, fortune, drops) -> {
            blockBreakEvent.setDropItems(false);
            int count = 1;
            for(ItemStack drop : drops) {
                count += drop.getAmount();
            }
            blockBreakEvent.setExpToDrop(blockBreakEvent.getExpToDrop() + count);
        });
    }
}
