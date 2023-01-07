package io.taraxacum.finaltech.core.item.machine.range.point;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class NormalConfigurableElectricityShootPile extends AbstractPointMachine implements RecipeItem, MenuUpdater {
    private final int range = ConfigUtil.getOrDefaultItemSetting(16, this, "range");

    public NormalConfigurableElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int digital = -1;
        ItemStack itemStack = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        SlimefunItem digitalSlimefunItem = SlimefunItem.getByItem(itemStack);
        if(digitalSlimefunItem instanceof DigitalItem digitalItem) {
            digital = digitalItem.getDigit();
        }

        int capacitorEnergy = 0;
        int transferEnergy = 0;

        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            Location capacitorLocation = block.getRelative(directional.getFacing().getOppositeFace()).getLocation();
            if (BlockStorage.hasBlockInfo(capacitorLocation)) {
                Config capacitorConfig = BlockStorage.getLocationInfo(capacitorLocation);
                if (capacitorConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                    SlimefunItem capacitorSlimefunItem = SlimefunItem.getById(capacitorConfig.getString(ConstantTableUtil.CONFIG_ID));
                    if (capacitorSlimefunItem instanceof EnergyNetComponent capacitor && JavaUtil.matchOnce(capacitor.getEnergyComponentType(), EnergyNetComponentType.CAPACITOR, EnergyNetComponentType.GENERATOR)) {
                        capacitorEnergy = Integer.parseInt(EnergyUtil.getCharge(capacitorConfig));
                        if(digital != -1) {
                            Block targetBlock = block;
                            Location targetLocation;
                            if(digital != 0) {
                                targetBlock = targetBlock.getRelative(directional.getFacing(), digital);
                                targetLocation = targetBlock.getLocation();
                                if (BlockStorage.hasBlockInfo(targetLocation)) {
                                    Config energyComponentConfig = BlockStorage.getLocationInfo(targetLocation);
                                    if (energyComponentConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                                        SlimefunItem energyComponentItem = SlimefunItem.getById(energyComponentConfig.getString(ConstantTableUtil.CONFIG_ID));
                                        if (energyComponentItem instanceof EnergyNetComponent energyNetComponent1 && !EnergyNetComponentType.NONE.equals(energyNetComponent1.getEnergyComponentType())) {
                                            int componentEnergy = Integer.parseInt(EnergyUtil.getCharge(energyComponentConfig));
                                            int componentCapacity = ((EnergyNetComponent) energyComponentItem).getCapacity();
                                            if (componentEnergy < componentCapacity) {
                                                transferEnergy = Math.min(capacitorEnergy, componentCapacity - componentEnergy);
                                                EnergyUtil.setCharge(targetLocation, String.valueOf(componentEnergy + transferEnergy));
                                                EnergyUtil.setCharge(capacitorLocation, String.valueOf(capacitorEnergy - transferEnergy));
                                            }
                                        }
                                    }
                                }
                            } else {
                                for(int i = 0; i < this.range; i++) {
                                    targetBlock = targetBlock.getRelative(directional.getFacing());
                                    targetLocation = targetBlock.getLocation();
                                    if (BlockStorage.hasBlockInfo(targetLocation)) {
                                        Config energyComponentConfig = BlockStorage.getLocationInfo(targetLocation);
                                        if (energyComponentConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                                            SlimefunItem energyComponentItem = SlimefunItem.getById(energyComponentConfig.getString(ConstantTableUtil.CONFIG_ID));
                                            if (energyComponentItem instanceof EnergyNetComponent energyNetComponent1 && !EnergyNetComponentType.NONE.equals(energyNetComponent1.getEnergyComponentType())) {
                                                int componentEnergy = Integer.parseInt(EnergyUtil.getCharge(energyComponentConfig));
                                                int componentCapacity = ((EnergyNetComponent) energyComponentItem).getCapacity();
                                                if (componentEnergy < componentCapacity) {
                                                    transferEnergy = Math.min(capacitorEnergy, componentCapacity - componentEnergy);
                                                    EnergyUtil.setCharge(targetLocation, String.valueOf(componentEnergy + transferEnergy));
                                                    EnergyUtil.setCharge(capacitorLocation, String.valueOf(capacitorEnergy - transferEnergy));
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                    String.valueOf(capacitorEnergy),
                    String.valueOf(transferEnergy));
        }
    }

    @Nonnull
    @Override
    protected Location getTargetLocation(@Nonnull Location location, int range) {
        Block block = location.getBlock();
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            return block.getRelative(directional.getFacing().getOppositeFace()).getLocation();
        }
        return location;
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
