package io.taraxacum.finaltech.machine.range.ray;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.machine.range.AbstractRangeMachine;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractRayMachine extends AbstractRangeMachine {
    public AbstractRayMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected final int function(@Nonnull Block block, int range, @Nonnull Function function) {
        int count = 0;
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional) {
            BlockFace blockFace = ((Directional) blockData).getFacing();
            for (int i = 0; i < range; i++) {
                block = block.getRelative(blockFace);
                int result = function.function(block.getLocation());
                if (result == -1) {
                    return count;
                }
                count += result;
            }
        }
        return count;
    }
}
