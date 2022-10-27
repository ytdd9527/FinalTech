package io.taraxacum.finaltech.core.items.machine.electric.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.machine.electric.capacitor.AbstractElectricCapacitor;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.EnergyUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractExpandedElectricCapacitor extends AbstractElectricCapacitor implements RecipeItem {
    protected static final String KEY = "s";

    public AbstractExpandedElectricCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), KEY, StringNumberUtil.ZERO);
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        int energy = Integer.parseInt(EnergyUtil.getCharge(config));
        int capacity = this.getCapacity();
        int generateEnergy = 0;
        String energyStack = config.getValue(KEY).toString();
        boolean updateEnergy = false;
        boolean updateStack = false;

        if ("".equals(energyStack)) {
            energyStack = StringNumberUtil.ZERO;
        } else if (StringNumberUtil.compare(energyStack, StringNumberUtil.INTEGER_MAX_VALUE) < 0) {
            generateEnergy = Integer.parseInt(energyStack);
            if (generateEnergy >= capacity / 2) {
                if (StringNumberUtil.compare(energyStack, this.getMaxStack()) < 0) {
                    generateEnergy -= capacity / 2;
                    energyStack = StringNumberUtil.add(energyStack);
                    updateStack = true;
                }
            }
            int transferEnergy = Math.min(capacity - energy, generateEnergy);
            energy += transferEnergy;
            generateEnergy -= transferEnergy;
            updateEnergy = true;
        } else if (StringNumberUtil.compare(energyStack, StringNumberUtil.INTEGER_MAX_VALUE) >= 0) {
            energyStack = StringNumberUtil.add(energyStack);
            updateStack = true;
        }

        if (energy < capacity / 4 && StringNumberUtil.compare(energyStack, StringNumberUtil.ZERO) > 0) {
            energy += capacity / 2;
            updateEnergy = true;
            energyStack = StringNumberUtil.sub(energyStack);
            updateStack = true;
        } else if (energy > capacity / 4 * 3 && StringNumberUtil.compare(energyStack, this.getMaxStack()) < 0) {
            energy -= capacity / 2;
            updateEnergy = true;
            energyStack = StringNumberUtil.add(energyStack);
            updateStack = true;
        }

        if (generateEnergy > 0) {
            generateEnergy = Math.min(capacity - energy, generateEnergy);
            energy += generateEnergy;
            updateEnergy = true;
        }

        if (updateEnergy) {
            EnergyUtil.setCharge(block.getLocation(), String.valueOf(energy));
        }
        if (updateStack) {
            BlockStorage.addBlockInfo(block.getLocation(), KEY, energyStack);
        }
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT), energy, energyStack);
        }
    }

    @Override
    public void setCharge(@Nonnull Location l, int charge) {
        int oldCharge = this.getCharge(l);
        int difference = charge - oldCharge;
        if (difference > 0) {
            difference *= this.chargeIncrease();
            if (oldCharge / 2 + difference / 2 > Integer.MAX_VALUE / 2) {
                difference = Integer.MAX_VALUE - oldCharge;
            }
        } else if (difference < 0) {
            difference *= this.consumeReduce();
        } else {
            return;
        }
        charge = oldCharge + difference;
        super.setCharge(l, charge);
    }

    @Override
    public void addCharge(@Nonnull Location l, int charge) {
        charge *= this.chargeIncrease();
        super.addCharge(l, charge);
    }

    @Override
    public void removeCharge(@Nonnull Location l, int charge) {
        charge *= this.consumeReduce();
        super.removeCharge(l, charge);
    }

    @Override
    public abstract int getCapacity();

    @Nonnull
    public abstract String getMaxStack();

    public abstract double chargeIncrease();

    public abstract double consumeReduce();

    protected void updateMenu(@Nonnull ItemStack item, int energy, @Nonnull String energyStack) {
        ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                String.valueOf(energy),
                energyStack));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf((this.getCapacity() / 2)),
                this.getMaxStack(),
                String.format("%.2f", this.chargeIncrease() * 100),
                String.format("%.2f", this.consumeReduce() * 100),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }
}
