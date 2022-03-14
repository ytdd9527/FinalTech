package io.taraxacum.finaltech.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.machine.capacitor.AbstractElectricCapacitor;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.ElectricCapacitorMenu;
import io.taraxacum.finaltech.util.CapacitorUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author LJC
 */
public abstract class AbstractExpandedElectricCapacitor extends AbstractElectricCapacitor {
    protected static final String KEY = "stack";
    public AbstractExpandedElectricCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ElectricCapacitorMenu(this.getId(), this.getItemName(), this);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), KEY, "0");
            }
        };
    }

    @Override
    public abstract int getCapacity();

    public abstract int getMaxStack();

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        int electric = getCharge(block.getLocation());
        int capacity = getCapacity();
        int stack = Integer.parseInt(config.getValue(KEY).toString());
        if(electric < capacity / 4 && stack > 0) {
            electric += capacity / 2;
            stack--;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(stack));
        } else if(electric > capacity / 4 * 3 && stack < getMaxStack()) {
            electric -= capacity / 2;
            stack++;
            BlockStorage.addBlockInfo(block.getLocation(), KEY, String.valueOf(stack));
        }
        setCharge(block.getLocation(), electric);
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(ElectricCapacitorMenu.INFO_SLOT);
        CapacitorUtil.setIcon(item, String.valueOf((long) electric + (long) stack * getCapacity() / 2));
    }
}
