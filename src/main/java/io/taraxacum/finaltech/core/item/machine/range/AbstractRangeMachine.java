package io.taraxacum.finaltech.core.item.machine.range;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractRangeMachine extends AbstractMachine {
    public AbstractRangeMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    protected abstract int function(@Nonnull Block block, int range, @Nonnull AbstractRangeMachine.RangeFunction function);

    protected interface RangeFunction extends Function<Location, Integer> {
        @Override
        Integer apply(Location location);
    }
}
