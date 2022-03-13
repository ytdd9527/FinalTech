package io.taraxacum.finaltech.machine.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.ElectricCapacitorMenu;
import io.taraxacum.finaltech.util.CapacitorUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 */
public abstract class AbstractElectricCapacitor extends AbstractMachine implements EnergyNetComponent {
    public AbstractElectricCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ElectricCapacitorMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {

            }
        };
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }


    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(ElectricCapacitorMenu.INFO_SLOT);
        CapacitorUtil.setIcon(item, getCharge(block.getLocation()));
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public final EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CAPACITOR;
    }

    @Override
    public abstract int getCapacity();

    @Override
    public boolean isChargeable() {
        return EnergyNetComponent.super.isChargeable();
    }

    @Override
    public int getCharge(@Nonnull Location l) {
        return EnergyNetComponent.super.getCharge(l);
    }

    @Override
    public int getCharge(@Nonnull Location l, @Nonnull Config data) {
        return EnergyNetComponent.super.getCharge(l, data);
    }

    @Override
    public void setCharge(@Nonnull Location l, int charge) {
        EnergyNetComponent.super.setCharge(l, charge);
    }

    @Override
    public void addCharge(@Nonnull Location l, int charge) {
        EnergyNetComponent.super.addCharge(l, charge);
    }

    @Override
    public void removeCharge(@Nonnull Location l, int charge) {
        EnergyNetComponent.super.removeCharge(l, charge);
    }
}
