package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.abstractItem.machine.AbstractCargo;
import io.taraxacum.finaltech.abstractItem.machine.AbstractMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.OverclockFrameMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class OverclockFrameMachine extends AbstractCargo implements EnergyNetComponent {
    public OverclockFrameMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new OverclockFrameMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block block) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(OverclockFrameMachineMenu.MACHINE_SLOT);
        if(ItemStackUtil.isItemNull(item) || item.getAmount() == 1) {
            return;
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if(slimefunItem == null) {
            return;
        }
        Block blockMachine = block.getRelative(BlockFace.DOWN);
        String id = BlockStorage.getLocationInfo(blockMachine.getLocation(), "id");
        if(!slimefunItem.getId().equals(id)) {
            return;
        }
        BlockTicker blockTicker = slimefunItem.getBlockTicker();
        if(blockTicker == null) {
            return;
        }
        int capacity = 0;
        if(slimefunItem instanceof EnergyNetComponent && ((EnergyNetComponent) slimefunItem).getEnergyComponentType().equals(EnergyNetComponentType.CONSUMER)) {
            capacity = ((EnergyNetComponent) slimefunItem).getCapacity();
        }
        for(int i = 1; i < item.getAmount(); i++) {
            if(blockTicker.isSynchronized()) {
                Slimefun.runSync(() -> {
                    blockTicker.tick(blockMachine, slimefunItem, BlockStorage.getLocationInfo(blockMachine.getLocation()));
                });
            } else {
                blockTicker.tick(blockMachine, slimefunItem, BlockStorage.getLocationInfo(blockMachine.getLocation()));
            }
            if(capacity != 0) {
                int count = Math.min(capacity - ((EnergyNetComponent) slimefunItem).getCharge(blockMachine.getLocation()), this.getCharge(block.getLocation()));
                if(count <= 0) {
                    continue;
                }
                ((EnergyNetComponent) slimefunItem).addCharge(blockMachine.getLocation(), count);
                this.removeCharge(block.getLocation(), count);
            }
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 536870912;
    }
}
