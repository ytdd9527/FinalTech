package io.taraxacum.finaltech.core.items.machine.range.line.shooter;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.items.machine.range.AbstractRangeMachine;
import io.taraxacum.finaltech.core.items.machine.range.line.AbstractLineMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractElectricityShootPile extends AbstractLineMachine implements RecipeItem {
    public AbstractElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Summary summary = new Summary();
        int count = 0;
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional) {
            Location capacitorLocation = block.getRelative(((Directional) blockData).getFacing().getOppositeFace()).getLocation();
            if (BlockStorage.hasBlockInfo(capacitorLocation)) {
                Config capacitorConfig = BlockStorage.getLocationInfo(capacitorLocation);
                if (capacitorConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                    SlimefunItem capacitorItem = SlimefunItem.getById(capacitorConfig.getString(ConstantTableUtil.CONFIG_ID));
                    if (capacitorItem instanceof EnergyNetComponent && EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) capacitorItem).getEnergyComponentType())) {
                        summary.setCapacitorEnergy(Integer.parseInt(EnergyUtil.getCharge(capacitorConfig)));
                        count = this.function(block, this.getRange(), this.doFunction(summary));
                        EnergyUtil.setCharge(capacitorLocation, String.valueOf(summary.getCapacitorEnergy()));
                    }
                }
            }
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, count, summary);
        }
    }

    @Override
    protected final boolean isSynchronized() {
        return false;
    }

    protected void updateMenu(@Nonnull BlockMenu blockMenu, int count, @Nonnull Summary summary) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                String.valueOf(count),
                summary.getEnergyCharge()));
    }

    public abstract int getRange();

    @Nonnull
    protected abstract AbstractRangeMachine.RangeFunction doFunction(@Nonnull Summary summary);

    protected static class Summary {
        private String energyCharge;
        private int capacitorEnergy;

        Summary() {
            this.energyCharge = StringNumberUtil.ZERO;
        }

        public String getEnergyCharge() {
            return energyCharge;
        }

        public void setEnergyCharge(String energyCharge) {
            this.energyCharge = energyCharge;
        }

        public int getCapacitorEnergy() {
            return capacitorEnergy;
        }

        public void setCapacitorEnergy(int capacitorEnergy) {
            this.capacitorEnergy = capacitorEnergy;
        }
    }
}
