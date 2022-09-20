package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.interfaces.LogicItem;
import io.taraxacum.finaltech.core.items.unusable.digital.AbstractDigitalNumber;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.LogicCrafterMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class LogicCrafter extends AbstractMachine implements RecipeItem {
    public LogicCrafter(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new LogicCrafterMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        int digit = 0;
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory inventory = blockMenu.toInventory();
        if(MachineUtil.itemCount(inventory, this.getOutputSlot()) == this.getOutputSlot().length) {
            return;
        }
        for(int slot : this.getInputSlot()) {
            ItemStack item = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(item)) {
                return;
            }
            SlimefunItem logicItem = SlimefunItem.getByItem(item);
            if(logicItem instanceof LogicItem) {
                boolean logic = ((LogicItem) logicItem).getLogic();
                digit = digit << 1;
                digit += logic ? 1 : 0;
            } else {
                return;
            }
        }
        ItemStack result = AbstractDigitalNumber.INTEGER_ITEM_STACK_MAP.get(digit);
        if(result != null) {
            for(int slot : this.getInputSlot()) {
                ItemStack itemStack = inventory.getItem(slot);
                if(ItemStackUtil.isItemNull(itemStack)) {
                    return;
                }
                itemStack.setAmount(itemStack.getAmount() - 1);
            }
            inventory.setItem(this.getOutputSlot()[0], result);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
