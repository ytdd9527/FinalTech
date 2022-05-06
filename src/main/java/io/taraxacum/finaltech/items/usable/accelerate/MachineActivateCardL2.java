package io.taraxacum.finaltech.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MachineActivateCardL2 extends AbstractMachineActivateCard {
    private static final int TIMES = 32;
    private static final double ENERGY = 256.2;
    public MachineActivateCardL2(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        if(player.getHealth() > 1 && player.getExp() > 1) {
            player.setHealth(player.getHealth() - 1);
            player.setExp(player.getExp() - 1);
            return true;
        }
        return false;
    }
}
