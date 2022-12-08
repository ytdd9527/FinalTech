package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.libs.plugin.util.BlockTickerUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class MatrixGenerator extends AbstractCubeElectricGenerator {
    private final String electricity = ConfigUtil.getOrDefaultItemSetting(StringNumberUtil.VALUE_INFINITY, this, "electricity");
    private final int range = ConfigUtil.getOrDefaultItemSetting(10, this, "range");

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Player player = blockPlaceEvent.getPlayer();
                Location location = blockPlaceEvent.getBlock().getLocation();
                BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_UUID, String.valueOf(player.getUniqueId()));
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    public MatrixGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);

        int range = 0;
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item)) {
                if (ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY)) {
                    for (int i = item.getAmount(); i > 0; i /= 2) {
                        range++;
                    }
                }
            }
        }

        String uuid = config.getString(ConstantTableUtil.CONFIG_UUID);
        int count = this.function(block, range + this.getRange(), location -> {
            if (BlockStorage.hasBlockInfo(location)) {
                Config energyComponentConfig = BlockStorage.getLocationInfo(location);
                if (energyComponentConfig.contains(ConstantTableUtil.CONFIG_ID)) {
                    String machineId = energyComponentConfig.getString(ConstantTableUtil.CONFIG_ID);

                    if (machineId.equals(MatrixGenerator.this.getId()) && !location.equals(block.getLocation())) {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null) {
                            BlockBreakEvent blockBreakEvent = new BlockBreakEvent(location.getBlock(), player);
                            Bukkit.getPluginManager().callEvent(blockBreakEvent);
                        }
                        return -1;
                    }

                    SlimefunItem machineItem = SlimefunItem.getById(energyComponentConfig.getString(ConstantTableUtil.CONFIG_ID));
                    if (machineItem instanceof EnergyNetComponent energyNetComponent && !EnergyNetComponentType.CAPACITOR.equals(energyNetComponent.getEnergyComponentType()) && !EnergyNetComponentType.GENERATOR.equals(energyNetComponent.getEnergyComponentType())) {
                        BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(machineId), () -> MatrixGenerator.this.chargeMachine((EnergyNetComponent) machineItem, energyComponentConfig), location);
                        return 1;
                    }
                }
            }
            return 0;
        });

        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, count, range);
        }
    }

    private void chargeMachine(@Nonnull EnergyNetComponent energyNetComponent, @Nonnull Config config) {
        int capacity = energyNetComponent.getCapacity();
        if (capacity == 0) {
            return;
        }
        EnergyUtil.setCharge(config, String.valueOf(capacity));
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, int count, int range) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                String.valueOf(count),
                String.valueOf(range)));
        if (count == 0) {
            item.setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            item.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }

    @Override
    protected String getElectricity() {
        return this.electricity;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
