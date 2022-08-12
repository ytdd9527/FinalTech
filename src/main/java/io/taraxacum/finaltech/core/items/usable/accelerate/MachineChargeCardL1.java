package io.taraxacum.finaltech.core.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MachineChargeCardL1 extends AbstractMachineChargeCard implements RecipeItem {
    private final double ENERGY = FinalTech.getValueManager().getOrDefault(16.04, "items", SlimefunUtil.getIdFormatName(MachineChargeCardL1.class), "energy");

    public MachineChargeCardL1(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected double energy() {
        return ENERGY;
    }

    @Override
    protected boolean consume() {
        return true;
    }

    @Override
    protected boolean conditionMatch(@Nonnull Player player) {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf((int)(Math.floor(ENERGY))),
                String.format("%.2f", (ENERGY - Math.floor(ENERGY)) * 100));
    }
}
