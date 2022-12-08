package io.taraxacum.finaltech.core.item.machine.range.point;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.item.machine.range.AbstractRangeMachine;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractPointMachine extends AbstractRangeMachine {
    public AbstractPointMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    protected final int function(@Nonnull Block block, int range, @Nonnull AbstractRangeMachine.RangeFunction function) {
        return function.apply(this.getTargetLocation(block.getLocation(), range));
    }

    protected abstract Location getTargetLocation(@Nonnull Location location, int range);
}
