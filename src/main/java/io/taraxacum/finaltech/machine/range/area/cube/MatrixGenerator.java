package io.taraxacum.finaltech.machine.range.area.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MatrixGenerator extends AbstractCubeElectricGenerator {
    public final static String ELECTRICITY = StringNumberUtil.VALUE_MAX;
    public final static int RANGE = 16;
    public MatrixGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        super.tick(block, slimefunItem, config);
    }

    @Override
    protected String getElectricity() {
        return ELECTRICITY;
    }

    @Override
    protected int getRange() {
        return RANGE;
    }

    @Override
    public void registerDefaultRecipes() {
        registerDescriptiveRecipe("&f供电量",
                "",
                "&f对范围内的每个机器",
                "&f使其存电量达到最大储电量");
        registerDescriptiveRecipe("&f供电范围",
                "",
                "&f传输半径=" + this.getRange() + "格",
                "&f即以自身为中心",
                "&f边长" + (this.getRange() * 2 + 1) + "格（含）的正方体区域");
    }
}
