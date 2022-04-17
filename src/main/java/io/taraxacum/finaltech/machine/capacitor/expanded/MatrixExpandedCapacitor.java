package io.taraxacum.finaltech.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.StatusL2Menu;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MatrixExpandedCapacitor extends AbstractExpandedElectricCapacitor{
    public static final int CAPACITY = Integer.MAX_VALUE;
    public static final String STACK = "MAX";
    public static final double CHARGE_INCREASE = 4;
    public static final double CONSUME_REDUCE = 0.25;
    private static final String KEY = "stack";
    public MatrixExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        for(int slot : this.getInputSlots()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if(!ItemStackUtil.isItemNull(item) && ItemStackUtil.isItemSimilar(item, FinalTechItems.FAKE)) {
                String energyStack = String.valueOf(config.getValue(KEY));
                energyStack = StringNumberUtil.add(energyStack, energyStack);
                BlockStorage.addBlockInfo(location, KEY, energyStack);
                item.setAmount(item.getAmount() - 1);
            }
        }
        super.tick(block, slimefunItem, config);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public String getMaxStack() {
        return STACK;
    }

    @Override
    public double chargeIncrease() {
        return CHARGE_INCREASE;
    }

    @Override
    public double consumeReduce() {
        return CONSUME_REDUCE;
    }
}
