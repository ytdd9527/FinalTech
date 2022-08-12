package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.InvWithSlots;
import io.taraxacum.finaltech.core.helper.CargoFilter;
import io.taraxacum.finaltech.core.helper.CargoLimit;
import io.taraxacum.finaltech.core.items.machine.operation.AbstractOperationMachine;
import io.taraxacum.finaltech.core.menu.limit.AbstractLimitMachineMenu;
import io.taraxacum.finaltech.core.menu.limit.BasicFrameMachineMenu;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class BasicFrameMachine extends AbstractOperationMachine {
    public BasicFrameMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractLimitMachineMenu setMachineMenu() {
        return new BasicFrameMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory inventory = blockMenu.toInventory();
        MachineUtil.stockSlots(blockMenu.toInventory(), this.getInputSlot());
        CargoUtil.doSimpleCargoStrongSymmetry(new InvWithSlots(inventory, this.getInputSlot()), new InvWithSlots(inventory, this.getOutputSlot()), this.getInputSlot().length, CargoLimit.VALUE_ALL, CargoFilter.VALUE_BLACK, inventory, new int[0]);
        MachineUtil.stockSlots(blockMenu.toInventory(), this.getOutputSlot());
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
