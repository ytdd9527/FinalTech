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
public class InfinityMachineChargeCard extends AbstractMachineChargeCard implements RecipeItem {
    private final double ENERGY = FinalTech.getValueManager().getOrDefault(16.04, "items", SlimefunUtil.getIdFormatName(InfinityMachineChargeCard.class), "energy");

    public InfinityMachineChargeCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected double energy() {
        return ENERGY;
    }

    @Override
    protected boolean consume() {
        return false;
    }

    @Override
    protected boolean conditionMatch(@Nonnull Player player) {
        if (player.getTotalExperience() > 1) {
            player.setTotalExperience(player.getTotalExperience() - 1);
            return true;
        }
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf((int)(Math.floor(ENERGY))),
                String.format("%.2f", (ENERGY - Math.floor(ENERGY)) * 100));
    }
}
