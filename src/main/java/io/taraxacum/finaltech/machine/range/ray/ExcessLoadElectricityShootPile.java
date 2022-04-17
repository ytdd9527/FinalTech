package io.taraxacum.finaltech.machine.range.ray;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.StatusMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Final_ROOT
 */
@Deprecated
public class ExcessLoadElectricityShootPile extends AbstractRayMachine implements RecipeItem {
    public static final int RANGE = 512;
    public ExcessLoadElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
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
        return new StatusMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockData blockData = block.getBlockData();
        int transferEnergy = 0;
        int count = 0;
        if(blockData instanceof Directional) {
            Location capacitorLocation = block.getRelative(((Directional) blockData).getFacing().getOppositeFace()).getLocation();
            if(BlockStorage.hasBlockInfo(capacitorLocation)) {
                Config capacitorConfig = BlockStorage.getLocationInfo(capacitorLocation);
                if(config.contains(SlimefunUtil.KEY_ID)) {
                    SlimefunItem capacitorItem = SlimefunItem.getById(capacitorConfig.getString(SlimefunUtil.KEY_ID));
                    if(capacitorItem instanceof EnergyNetComponent && EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) capacitorItem).getEnergyComponentType())) {
                        transferEnergy = Integer.parseInt(SlimefunUtil.getCharge(capacitorConfig));
                        AtomicInteger charge = new AtomicInteger(transferEnergy);
                        count = this.function(block, RANGE, location -> {
                            if(charge.get() > 0 && BlockStorage.hasBlockInfo(location)) {
                                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                                if(energyComponentConfig.contains(SlimefunUtil.KEY_ID)) {
                                    SlimefunItem energyComponentItem = SlimefunItem.getById(energyComponentConfig.getString(SlimefunUtil.KEY_ID));
                                    if(energyComponentItem instanceof EnergyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(((EnergyNetComponent) energyComponentItem).getEnergyComponentType())) {
                                        int componentCharge = Integer.parseInt(SlimefunUtil.getCharge(energyComponentConfig));
                                        int capacity = Integer.MAX_VALUE;
                                        int energy = Math.min(capacity - componentCharge, charge.get() > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : charge.get() * 2);
                                        SlimefunUtil.setCharge(location, String.valueOf(componentCharge + energy));
                                        charge.addAndGet(-energy / 2);
                                        return 1;
                                    }
                                }
                            }
                            return 0;
                        });
                        SlimefunUtil.setCharge(capacitorLocation, String.valueOf(charge.get()));
                        transferEnergy -= charge.get();
                    }
                }
            }
        }
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.CENTER_SLOT);
        ItemStackUtil.setLore(item,
                "§7当前生效机器= " + count,
                "§7实际传输电量= " + transferEnergy);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f工作原理",
                "",
                "&f背对电容机器生效",
                "&f将该电容内的电量按照从近至远的顺序",
                "&f不断传输到机器自身所面向方向的" + RANGE + "格机器",
                "",
                "&f电力无法传输给电容类机器");
        this.registerDescriptiveRecipe("&f过载传输",
                "",
                "&f电力传输时",
                "&f以200%的效率进行传输",
                "&f并且可能导致机器的存电量超过限制的最大储电量");
    }
}
