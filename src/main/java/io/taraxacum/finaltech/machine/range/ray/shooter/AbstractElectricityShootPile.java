package io.taraxacum.finaltech.machine.range.ray.shooter;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.machine.range.AbstractRangeMachine;
import io.taraxacum.finaltech.machine.range.ray.AbstractRayMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.simple.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import io.taraxacum.common.util.StringNumberUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractElectricityShootPile extends AbstractRayMachine implements RecipeItem {
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
                if (capacitorConfig.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem capacitorItem = SlimefunItem.getById(capacitorConfig.getString(SlimefunUtil.KEY_ID));
                    if (capacitorItem instanceof EnergyNetComponent && EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) capacitorItem).getEnergyComponentType())) {
                        summary.setCapacitorEnergy(Integer.parseInt(SlimefunUtil.getCharge(capacitorConfig)));
                        count = this.function(block, this.getRange(), this.doFunction(summary));
                        SlimefunUtil.setCharge(capacitorLocation, String.valueOf(summary.getCapacitorEnergy()));
                    }
                }
            }
        }

        this.updateMenu(BlockStorage.getInventory(block).getItemInSlot(StatusMenu.STATUS_SLOT), count, summary);
    }

    @Override
    protected final boolean isSynchronized() {
        return false;
    }

    protected void updateMenu(ItemStack item, int count, Summary summary) {
        //todo 优化说明
        ItemStackUtil.setLore(item,
                "§7当前生效机器= " + count,
                "§7实际传输电量= " + summary.getEnergyCharge());
    }

    public abstract int getRange();

    protected abstract AbstractRangeMachine.Function doFunction(Summary summary);

    protected class Summary {
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
