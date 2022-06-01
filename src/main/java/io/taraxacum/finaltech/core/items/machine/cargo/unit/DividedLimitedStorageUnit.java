package io.taraxacum.finaltech.core.items.machine.cargo.unit;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.DividedLimitStorageUnitMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 */
public class DividedLimitedStorageUnit extends AbstractCargo implements RecipeItem {
    public DividedLimitedStorageUnit(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new DividedLimitStorageUnitMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {

    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "可存储 " + TextUtil.COLOR_NUMBER + MachineUtil.calMachineSlotSize(this) + "格" + TextUtil.COLOR_NORMAL + " 物品");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "分层",
                "",
                TextUtil.COLOR_NORMAL + "输入槽被限制为上面的 " + TextUtil.COLOR_NUMBER + this.getInputSlot().length + "格",
                TextUtil.COLOR_NORMAL + "输出槽被限制为下面的 " + TextUtil.COLOR_NUMBER + this.getOutputSlot().length + "格");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "限制",
                "",
                TextUtil.COLOR_NORMAL + "粘液科技机器尝试将物品输入进该机器时",
                TextUtil.COLOR_NORMAL + "被输入的物品在该机器的输入槽中至多存在一组");
    }
}
