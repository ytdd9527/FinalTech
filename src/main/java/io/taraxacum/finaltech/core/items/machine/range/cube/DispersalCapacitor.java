package io.taraxacum.finaltech.core.items.machine.range.cube;

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
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.TextUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.EnergyUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This item should be updated sometime.
 * But now, just let us disable it.
 * @author Final_ROOT
 * @since 1.0
 */
@Deprecated
public class DispersalCapacitor extends AbstractCubeMachine implements EnergyNetComponent, RecipeItem {
    private final int range = ConfigUtil.getOrDefaultItemSetting(4, this, "range");
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE / 4, this, "capacity");
    private final double loss = ConfigUtil.getOrDefaultItemSetting(16, this, "loss");

    public DispersalCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location blockLocation = block.getLocation();
        int charge = Integer.parseInt(EnergyUtil.getCharge(config));
        int count = 0;
        AtomicInteger maxEnergy = new AtomicInteger();
        AtomicInteger totalEnergy = new AtomicInteger();
        int validCharge = (int)(charge / loss);
        if (charge > 0) {
            count = this.function(block, range, location -> {
                if (BlockStorage.hasBlockInfo(location)) {
                    Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                    if (energyComponentConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                        SlimefunItem item = SlimefunItem.getById(energyComponentConfig.getString(ConstantTableUtil.CONFIG_ID));
                        if (item instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) item).getEnergyComponentType())) {
                            int componentCapacity = ((EnergyNetComponent) item).getCapacity();
                            if (componentCapacity == 0) {
                                return 0;
                            }
                            int componentEnergy = Integer.parseInt(EnergyUtil.getCharge(energyComponentConfig));
                            int transferEnergy = Math.min(componentCapacity - componentEnergy, validCharge);
                            if (transferEnergy > 0) {
                                EnergyUtil.setCharge(location, String.valueOf(transferEnergy + componentEnergy));
                                maxEnergy.set(Math.max(maxEnergy.get(), transferEnergy));
                                totalEnergy.addAndGet(transferEnergy);
                                return 1;
                            }
                        }
                    }
                }
                return 0;
            });
        }
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if(blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, charge, count, totalEnergy, maxEnergy);
        }
        charge = charge - (int) (maxEnergy.get() * loss);
        charge = Math.max(charge, 0);
        EnergyUtil.setCharge(blockLocation, String.valueOf(charge));
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int charge, int count, AtomicInteger totalEnergy, AtomicInteger maxEnergy) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CAPACITOR;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range),
                String.valueOf(this.capacity),
                String.valueOf(this.loss));
    }
}
