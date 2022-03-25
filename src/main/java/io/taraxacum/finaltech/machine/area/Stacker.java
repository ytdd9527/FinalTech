package io.taraxacum.finaltech.machine.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.VoidMenu;
import io.taraxacum.finaltech.util.InvWithSlots;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.cargo.CargoUtil;
import io.taraxacum.finaltech.util.cargo.SlotSearchOrder;
import io.taraxacum.finaltech.util.cargo.SlotSearchSize;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class Stacker extends AbstractAreaMachine{
    private static final int RANGE = 16;
    public Stacker(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull Location location, @Nonnull Config config) {
        InvWithSlots invWithSlots = CargoUtil.getInv(location.getBlock(), SlotSearchSize.VALUE_OUTPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT);
        if(invWithSlots != null && invWithSlots.getInventory() != null && invWithSlots.getSlots() != null) {
            MachineUtil.stockSlots(invWithSlots.getInventory(), invWithSlots.getSlots());
        }
        invWithSlots = CargoUtil.getInv(location.getBlock(), SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT);
        if(invWithSlots != null && invWithSlots.getInventory() != null && invWithSlots.getSlots() != null) {
            MachineUtil.stockSlots(invWithSlots.getInventory(), invWithSlots.getSlots());
        }
    }

    @Override
    protected int getRange() {
        return RANGE;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
            }
        };
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new VoidMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }
}
