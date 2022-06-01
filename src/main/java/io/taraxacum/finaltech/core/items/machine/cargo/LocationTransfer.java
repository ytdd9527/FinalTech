package io.taraxacum.finaltech.core.items.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.factory.BlockTaskFactory;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.function.LocationTransferMenu;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.util.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();
        boolean drawParticle = blockMenu.hasViewer();

        ItemStack locationRecorder = blockMenu.getItemInSlot(LocationTransferMenu.LOCATION_RECORDER_SLOT);
        if(ItemStackUtil.isItemNull(locationRecorder)) {
            return;
        }
        ItemMeta itemMeta = locationRecorder.getItemMeta();
        Location targetLocation = LocationUtil.parseLocationInItem(itemMeta);
        if (targetLocation == null) {
            return;
        }
        Block targetBlock = targetLocation.getBlock();

        String uuid = PlayerUtil.parseIdInItem(itemMeta);
        if (uuid != null) {
            if (!SlimefunUtil.hasPermission(uuid, targetLocation, Interaction.INTERACT_BLOCK, Interaction.INTERACT_BLOCK)) {
                return;
            }
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

        Runnable runnable = () -> {
            if (CargoOrder.VALUE_POSITIVE.equals(cargoOrder)) {
                CargoUtil.doCargo(block, targetBlock, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, slotSearchSize, slotSearchOrder, cargoNumber, cargoLimit, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0], cargoMode);
            } else {
                CargoUtil.doCargo(targetBlock, block, SlotSearchSize.VALUE_INPUTS_ONLY, SlotSearchOrder.VALUE_ASCENT, slotSearchSize, slotSearchOrder, cargoNumber, cargoLimit, CargoFilter.VALUE_BLACK, blockMenu.toInventory(), new int[0], cargoMode);
            }
        };

        if(primaryThread) {
            runnable.run();
        } else {
            BlockTaskFactory.getInstance().registerRunnable(slimefunItem, false, runnable, block.getLocation(), targetLocation);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(TextUtil.COLOR_PASSIVE + "功能",
                "",
                TextUtil.COLOR_NORMAL + "该机器会不断把物品",
                TextUtil.COLOR_NORMAL + "从输入侧方块的容器",
                TextUtil.COLOR_NORMAL + "传输到输出侧方块的容器");
    }
}
