package io.taraxacum.finaltech.core.items.machine.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.api.interfaces.AntiAccelerationMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.TextUtil;
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
public class MatrixExpandedCapacitor extends AbstractExpandedElectricCapacitor implements AntiAccelerationMachine {
    public static final int CAPACITY = Integer.MAX_VALUE;
    public static final String STACK = StringNumberUtil.VALUE_INFINITY;
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

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "机制",
                "",
                TextUtil.COLOR_NORMAL + "单组流转电量 " + TextUtil.COLOR_NUMBER + (this.getCapacity() / 2) + "J",
                TextUtil.COLOR_NORMAL + "可流转电容组数 " + TextUtil.COLOR_NUMBER + StringNumberUtil.add(this.getMaxStack(), "2") + "S",
                TextUtil.COLOR_NORMAL + "充电效率 " + TextUtil.COLOR_NUMBER + String.format("%.2f", this.chargeIncrease() * 100) + "%",
                TextUtil.COLOR_NORMAL + "耗电效率 " + TextUtil.COLOR_NUMBER + String.format("%.2f", this.consumeReduce() * 100) + "%");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "电力回转",
                "",
                TextUtil.COLOR_NORMAL + "每 " + TextUtil.COLOR_NUMBER + String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0) + "秒" + TextUtil.COLOR_NORMAL + " 回复等同于已存电组数的电量");
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "电力超载",
                "",
                TextUtil.COLOR_NORMAL + "输入并消耗 " + FinalTechItems.PHONY + TextUtil.COLOR_NORMAL + " 后 存电组数翻倍");
    }
}
