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
public class MachineActivateCardL3 extends AbstractMachineActivateCard {
    private static final int TIMES = 1800;
    private static final double ENERGY = 4096.48;
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
        this.registerDescriptiveRecipe(TextUtil.COLOR_INITIATIVE + "使用方式",
                "",
                TextUtil.COLOR_ACTION + "[右键] " + TextUtil.COLOR_NORMAL + "机器使其",
                TextUtil.COLOR_NORMAL + "立即工作 " + TextUtil.COLOR_NUMBER + TIMES + "次",
                TextUtil.COLOR_NORMAL + "并在每次工作前 充电 " + TextUtil.COLOR_NUMBER + (int)(Math.floor(ENERGY)) + "J + " + String.format("%.2f", (ENERGY - Math.floor(ENERGY)) * 100) + "%J" + TextUtil.COLOR_NORMAL + " 的电量",
                TextUtil.COLOR_NEGATIVE + "每次使用损失 10% 生命值 和  1 等级",
                TextUtil.COLOR_NEGATIVE + "消耗品");
    }
}
