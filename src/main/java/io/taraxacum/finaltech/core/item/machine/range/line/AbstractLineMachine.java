package io.taraxacum.finaltech.core.item.machine.range.line;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.item.machine.range.AbstractRangeMachine;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractLineMachine extends AbstractRangeMachine {
    public AbstractLineMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected final int function(@Nonnull Block block, int range, @Nonnull RangeFunction function) {
        int count = 0;
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional) {
            BlockFace blockFace = ((Directional) blockData).getFacing();
            for (int i = 0; i < range; i++) {
                block = block.getRelative(blockFace);
                int result = function.apply(block.getLocation());
                if (result == -1) {
                    return count;
                }
                count += result;
            }
        }
        return count;
    }
}
