package io.taraxacum.finaltech.core.items.machine.cargo.unit;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.RandomInputStorageUnitMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class RandomInputStorageUnit extends AbstractCargo implements RecipeItem {
    public RandomInputStorageUnit(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new RandomInputStorageUnitMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {

    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "可存储 " + TextUtil.COLOR_NUMBER + MachineUtil.calMachineSlotSize(this) + "格" + TextUtil.COLOR_NORMAL + " 物品");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "随机存入",
                "",
                TextUtil.COLOR_NORMAL + "被访问输入槽时",
                TextUtil.COLOR_NORMAL + "输入槽会被随机排序");
    }
}
