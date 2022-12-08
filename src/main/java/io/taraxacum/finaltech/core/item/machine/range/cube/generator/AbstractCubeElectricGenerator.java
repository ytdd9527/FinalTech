package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.plugin.util.StringItemUtil;
import io.taraxacum.finaltech.api.interfaces.item.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.range.cube.AbstractCubeMachine;
import io.taraxacum.finaltech.core.item.unusable.StorageCard;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.libs.slimefun.util.BlockTickerUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractCubeElectricGenerator extends AbstractCubeMachine implements RecipeItem {
    public AbstractCubeElectricGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        boolean drawParticle = blockMenu.hasViewer();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        String extraEnergy = this.getElectricity();
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                String amount = String.valueOf(item.getAmount());
                if (StorageCard.isValid(item)) {
                    amount = StringItemUtil.parseAmountInCard(item);
                    item = StringItemUtil.parseItemInCard(item);
                }
                if (ItemStackUtil.isItemSimilar(item, this.getItem())) {
                    extraEnergy = StringNumberUtil.add(extraEnergy, StringNumberUtil.mul(this.getElectricity(), amount));
                }
            }
        }

        String finalEnergy = extraEnergy;
        int count = this.function(block, this.getRange(), location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if (energyComponentConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                    String machineId = energyComponentConfig.getString(ConstantTableUtil.CONFIG_ID);
                    SlimefunItem machineItem = SlimefunItem.getById(machineId);
                    if (machineItem instanceof EnergyNetComponent energyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(energyNetComponent.getEnergyComponentType()) && !EnergyNetComponentType.GENERATOR.equals(energyNetComponent.getEnergyComponentType())) {
                        BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(machineId), () -> AbstractCubeElectricGenerator.this.chargeMachine((EnergyNetComponent) machineItem, finalEnergy, energyComponentConfig, location), location);
                        if (drawParticle) {
                            Location cloneLocation = location.clone();
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.GLOW, 0, cloneLocation.getBlock()));
                        }
                        return 1;
                    }
                }
            }
            return 0;
        });

        blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, finalEnergy, count);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void chargeMachine(@Nonnull EnergyNetComponent energyNetComponent, @Nonnull String chargeEnergy, @Nonnull Config config, @Nonnull Location location) {
        int capacity = energyNetComponent.getCapacity();
        if (capacity == 0) {
            return;
        }
        String storedEnergy = config.contains(ConstantTableUtil.CONFIG_CHARGE) ? config.getString(ConstantTableUtil.CONFIG_CHARGE) : StringNumberUtil.ZERO;
        if (StringNumberUtil.compare(storedEnergy, String.valueOf(capacity)) >= 0) {
            return;
        }
        String transferEnergy = StringNumberUtil.min(StringNumberUtil.sub(String.valueOf(capacity), storedEnergy), chargeEnergy);
        if (StringNumberUtil.compare(transferEnergy, StringNumberUtil.ZERO) > 0) {
            EnergyUtil.setCharge(config, StringNumberUtil.add(storedEnergy, transferEnergy));
        }
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, @Nonnull String chargeEnergy, int count) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                chargeEnergy,
                String.valueOf(count)));
        if (count == 0) {
            item.setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            item.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.getElectricity()),
                String.valueOf(this.getRange()),
                String.valueOf(String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0)));
    }

    protected abstract String getElectricity();

    protected abstract int getRange();
}
