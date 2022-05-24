package io.taraxacum.finaltech.core.items.usable.accelerate;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MachineActivateCardL1 extends AbstractMachineActivateCard {
    private static final int TIMES = 1;
    private static final double ENERGY = 128.1;
    public MachineActivateCardL1(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_INITIATIVE + "介绍",
                "",
                TextUtil.COLOR_ACTION + "[右键]" + TextUtil.COLOR_NORMAL + "机器使其",
                TextUtil.COLOR_NORMAL + "立即工作" + TIMES + "次",
                TextUtil.COLOR_NORMAL + "并在每次工作前 充电 " + (int)(Math.floor(ENERGY)) + " + " + ((ENERGY - Math.floor(ENERGY)) * 100) + "% 的电量",
                TextUtil.COLOR_NEGATIVE + "消耗品");
    }
}
