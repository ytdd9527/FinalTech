package io.taraxacum.finaltech.core.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MachineActivateCardL3 extends AbstractMachineActivateCard implements RecipeItem {
    private final int times = ConfigUtil.getOrDefaultItemSetting(1, this, "times");
    private final double energy = ConfigUtil.getOrDefaultItemSetting(16.04, this, "energy");

    public MachineActivateCardL3(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected int times() {
        return times;
    }

    @Override
    protected double energy() {
        return energy;
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
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.times()),
                String.valueOf((int)(Math.floor(energy))),
                String.format("%.2f", (energy - Math.floor(energy)) * 100));
    }
}
