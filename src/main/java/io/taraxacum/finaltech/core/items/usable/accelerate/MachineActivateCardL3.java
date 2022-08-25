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
public class MachineActivateCardL3 extends AbstractMachineActivateCard implements RecipeItem {
    private final int TIMES = FinalTech.getValueManager().getOrDefault(1, "items", SlimefunUtil.getIdFormatName(MachineActivateCardL3.class), "times");
    private final double ENERGY = FinalTech.getValueManager().getOrDefault(16.04, "items", SlimefunUtil.getIdFormatName(MachineActivateCardL3.class), "energy");

    public MachineActivateCardL3(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected int times() {
        return TIMES;
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
        if (player.getHealth() > player.getMaxHealth() * 0.1 && player.getLevel() > 1) {
            player.setHealth(player.getHealth() - player.getMaxHealth() * 0.1);
            player.setLevel(player.getLevel() - 1);
            return true;
        }
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.times()),
                String.valueOf((int)(Math.floor(ENERGY))),
                String.format("%.2f", (ENERGY - Math.floor(ENERGY)) * 100));
    }
}
