package io.taraxacum.finaltech.core.item.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.finaltech.api.interfaces.item.RecipeItem;
import io.taraxacum.finaltech.core.dto.CargoDTO;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.LocationTransferMenu;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.MachineUtil;
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
        Location location = block.getLocation();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean drawParticle = blockMenu.hasViewer();

        ItemStack locationRecorder = blockMenu.getItemInSlot(LocationTransferMenu.LOCATION_RECORDER_SLOT);
        if (ItemStackUtil.isItemNull(locationRecorder)) {
            return;
        }
        Location targetLocation = LocationUtil.parseLocationInItem(locationRecorder);
        if (targetLocation == null || targetLocation.equals(location)) {
            return;
        }
        Block targetBlock = targetLocation.getBlock();

        if (!PermissionUtil.checkOfflinePermission(locationRecorder, targetLocation)) {
            return;
        }

        if (drawParticle) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.COMPOSTER, 0, targetBlock));
        }

        String slotSearchSize = SlotSearchSize.HELPER.getOrDefaultValue(config);
        String slotSearchOrder = SlotSearchOrder.HELPER.getOrDefaultValue(config);

        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setJavaPlugin(this.addon.getJavaPlugin());

        switch (CargoOrder.HELPER.getOrDefaultValue(config)) {
            case CargoOrder.VALUE_POSITIVE -> {
                cargoDTO.setInputBlock(block);
                cargoDTO.setInputSize(SlotSearchSize.VALUE_INPUTS_ONLY);
                cargoDTO.setInputOrder(SlotSearchOrder.VALUE_ASCENT);

                cargoDTO.setOutputBlock(targetBlock);
                cargoDTO.setOutputSize(slotSearchSize);
                cargoDTO.setOutputOrder(slotSearchOrder);
            }
            case CargoOrder.VALUE_REVERSE -> {
                cargoDTO.setOutputBlock(block);
                cargoDTO.setOutputSize(SlotSearchSize.VALUE_INPUTS_ONLY);
                cargoDTO.setOutputOrder(SlotSearchOrder.VALUE_ASCENT);

                cargoDTO.setInputBlock(targetBlock);
                cargoDTO.setInputSize(slotSearchSize);
                cargoDTO.setInputOrder(slotSearchOrder);
            }
        }

        cargoDTO.setCargoNumber(Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config)));
        cargoDTO.setCargoLimit(CargoLimit.HELPER.getOrDefaultValue(config));
        cargoDTO.setCargoFilter(CargoFilter.VALUE_BLACK);
        cargoDTO.setFilterInv(blockMenu.toInventory());
        cargoDTO.setFilterSlots(new int[0]);

        CargoUtil.doCargo(cargoDTO, CargoMode.HELPER.getOrDefaultValue(config));
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
