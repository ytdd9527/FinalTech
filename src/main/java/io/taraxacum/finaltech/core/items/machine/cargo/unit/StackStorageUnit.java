package io.taraxacum.finaltech.core.items.machine.cargo.unit;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.NormalStorageUnitMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.TextUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class StackStorageUnit extends AbstractCargo implements RecipeItem {
    public StackStorageUnit(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new NormalStorageUnitMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        MachineUtil.stockSlots(blockMenu, this.getInputSlot());
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "可存储 " + TextUtil.COLOR_NUMBER + MachineUtil.calMachineSlotSize(this) + "格" + TextUtil.COLOR_NORMAL + " 物品");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "堆叠",
                "",
                TextUtil.COLOR_NORMAL + "该机器每 " + TextUtil.COLOR_NUMBER + String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0) + "秒" + TextUtil.COLOR_NORMAL + " 会尝试将自身输入槽或输出槽中的物品进行合并");
    }
}
