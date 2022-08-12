package io.taraxacum.finaltech.core.items.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixExpandedCapacitor extends AbstractExpandedElectricCapacitor implements AntiAccelerationMachine {
    public final int capacity = FinalTech.getValueManager().getOrDefault(Integer.MAX_VALUE, "capacitor", SlimefunUtil.getIdFormatName(MatrixExpandedCapacitor.class), "capacity");
    public final String stack = FinalTech.getValueManager().getOrDefault(StringNumberUtil.VALUE_INFINITY, "capacitor", SlimefunUtil.getIdFormatName(MatrixExpandedCapacitor.class), "stack");
    public final double chargeIncrease = FinalTech.getValueManager().getOrDefault(4, "capacitor", SlimefunUtil.getIdFormatName(MatrixExpandedCapacitor.class), "charge-increase");
    public final double consumeReduce = FinalTech.getValueManager().getOrDefault(0.25, "capacitor", SlimefunUtil.getIdFormatName(MatrixExpandedCapacitor.class), "consume-reduce");
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
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item) && ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY)) {
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
        return this.capacity * 2;
    }

    @Nonnull
    @Override
    public String getMaxStack() {
        return this.stack;
    }

    @Override
    public double chargeIncrease() {
        return this.chargeIncrease;
    }

    @Override
    public double consumeReduce() {
        return this.consumeReduce;
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf((this.getCapacity() / 2)),
                StringNumberUtil.add(this.getMaxStack(), String.format("%.2f", this.chargeIncrease() * 100)),
                String.format("%.2f", this.consumeReduce() * 100),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }
}
