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
 * @since 2.0
 */
public class MachineChargeCardL1 extends AbstractMachineActivateCard {
    private static final double ENERGY = 256.02;

    public MachineChargeCardL1(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected int times() {
        return 0;
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
        this.registerDescriptiveRecipe(TextUtil.COLOR_INITIATIVE + "使用方式",
                "",
                TextUtil.COLOR_ACTION + "[右键] " + TextUtil.COLOR_NORMAL + "机器使其立即充电 " + TextUtil.COLOR_NUMBER + (int)(Math.floor(ENERGY)) + "J + " + String.format("%.2f", (ENERGY - Math.floor(ENERGY)) * 100) + "%J" + TextUtil.COLOR_NORMAL + " 的电量",
                TextUtil.COLOR_NEGATIVE + "百分比充电量不作用于电容",
                TextUtil.COLOR_NEGATIVE + "消耗品");
    }
}
