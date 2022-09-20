package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.InvWithSlots;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.LocationTransferMenu;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.util.slimefun.PermissionUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LocationTransfer extends AbstractCargo implements RecipeItem {
    public LocationTransfer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();
                Location location = block.getLocation();

                CargoNumber.HELPER.checkOrSetBlockStorage(location);
                SlotSearchSize.HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.HELPER.checkOrSetBlockStorage(location);
                CargoLimit.HELPER.checkOrSetBlockStorage(location);

                CargoMode.HELPER.checkOrSetBlockStorage(location);
                CargoOrder.HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, LocationTransferMenu.LOCATION_RECORDER_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new LocationTransferMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = blockMenu.getLocation();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();
        boolean drawParticle = blockMenu.hasViewer();

        ItemStack locationRecorder = blockMenu.getItemInSlot(LocationTransferMenu.LOCATION_RECORDER_SLOT);
        if(ItemStackUtil.isItemNull(locationRecorder)) {
            return;
        }
        Location targetLocation = LocationUtil.parseLocationInItem(locationRecorder);
        if (targetLocation == null || targetLocation.equals(location)) {
            return;
        }
        Block targetBlock = targetLocation.getBlock();

        if(!PermissionUtil.checkOfflinePermission(locationRecorder, targetLocation)) {
            return;
        }

        if(drawParticle) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(Particle.COMPOSTER, 0, targetBlock));
        }

        int cargoNumber = Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config));
        String slotSearchSize = SlotSearchSize.HELPER.getOrDefaultValue(config);
        String slotSearchOrder = SlotSearchOrder.HELPER.getOrDefaultValue(config);
        String cargoLimit = CargoLimit.HELPER.getOrDefaultValue(config);
        String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);
        String cargoOrder = CargoOrder.HELPER.getOrDefaultValue(config);

        if(primaryThread) {
            switch (cargoOrder) {
                case CargoOrder.VALUE_POSITIVE -> CargoUtil.doCargo(javaPlugin, block, targetBlock, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, slotSearchSize, slotSearchOrder, cargoNumber, cargoLimit, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0], cargoMode);
                case CargoOrder.VALUE_REVERSE -> CargoUtil.doCargo(javaPlugin, targetBlock, block, slotSearchSize, slotSearchOrder, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, cargoNumber, cargoLimit, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0], cargoMode);
            }
        } else {
            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                InvWithSlots invWithSlots = CargoUtil.getInvWithSlots(block, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT);
                InvWithSlots targetInvWithSlots = CargoUtil.getInvWithSlots(targetBlock, slotSearchSize, slotSearchOrder);
                FinalTech.getLocationRunnableFactory().waitThenRun(() -> {
                    switch (cargoOrder) {
                        case CargoOrder.VALUE_POSITIVE -> CargoUtil.doSimpleCargo(invWithSlots, block, targetInvWithSlots, targetBlock, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, slotSearchSize, slotSearchOrder, cargoNumber, cargoLimit, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0], cargoMode);
                        case CargoOrder.VALUE_REVERSE -> CargoUtil.doSimpleCargo(targetInvWithSlots, targetBlock, invWithSlots, block, slotSearchSize, slotSearchOrder, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, cargoNumber, cargoLimit, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0], cargoMode);
                    }
                }, targetLocation, location);
            });
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
