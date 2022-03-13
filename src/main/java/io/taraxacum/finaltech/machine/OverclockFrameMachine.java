package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.taraxacum.finaltech.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.AdvancedAutoCraftMenu;
import io.taraxacum.finaltech.menu.OverclockFrameMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class OverclockFrameMachine extends AbstractMachine implements EnergyNetComponent {
    public OverclockFrameMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new OverclockFrameMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {
            @Override
            public void onBlockBreak(@Nonnull Block block) {
                BlockMenu blockMenu = BlockStorage.getInventory(block);
                blockMenu.dropItems(block.getLocation(), OverclockFrameMachineMenu.MACHINE_SLOT);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem sfItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(OverclockFrameMachineMenu.MACHINE_SLOT);
        if(ItemStackUtil.isItemNull(item) || item.getAmount() == 1) {
            return;
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if(slimefunItem == null || slimefunItem.getBlockTicker() == null) {
            return;
        }
        Block blockMachine = block.getRelative(BlockFace.DOWN);
        String id = BlockStorage.getLocationInfo(blockMachine.getLocation(), "id");
        if(!slimefunItem.getId().equals(id)) {
            return;
        }
        BlockTicker blockTicker = slimefunItem.getBlockTicker();
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

    /**
     * 是否是线程同步的
     *
     * @return
     */
    @Override
    protected boolean isSynchronized() {
        return false;
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
