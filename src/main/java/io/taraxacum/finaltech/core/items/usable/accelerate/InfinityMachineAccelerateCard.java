package io.taraxacum.finaltech.core.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class InfinityMachineAccelerateCard extends AbstractMachineAccelerateCard implements RecipeItem {
    private final int TIMES = FinalTech.getValueManager().getOrDefault(1, "items", SlimefunUtil.getIdFormatName(InfinityMachineAccelerateCard.class), "times");

    public InfinityMachineAccelerateCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected int times() {
        return TIMES;
    }

    @Override
    protected boolean consume() {
        return false;
    }

    @Override
    protected boolean conditionMatch(@Nonnull Player player) {
        if (player.getHealth() > 1) {
            player.setHealth(player.getHealth() - 1);
            return true;
        }
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.times()));
    }
}
