package io.taraxacum.finaltech.machine.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.interfaces.RecipeItem;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.VoidMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Final_ROOT
 */
public abstract class AbstractAreaElectricGenerator extends AbstractMachine implements RecipeItem {
    private static final String KEY = "energy-charge";
    private static final List<MachineRecipe> RECIPE = new ArrayList<>();
    public AbstractAreaElectricGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    abstract int getElectricity();

    abstract int getRange();

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
            }
        };
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new VoidMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        int electricity = getElectricity();
        int range = getRange();
        Location location = block.getLocation();
        World world = location.getWorld();
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();
        int capacity;
        int charge;
        for(int x = blockX - range, maxX = blockX + range; x < maxX; x++) {
            location.setX(x);
            for(int y = Math.max(blockY - range, world.getMinHeight()), maxY = Math.min(blockY + range, world.getMaxHeight()); y < maxY; y++) {
                location.setY(y);
                for(int z = blockZ - range, maxZ = blockZ + range; z < maxZ; z++) {
                    location.setZ(z);
                    if(BlockStorage.hasBlockInfo(location)) {
                        Config locationInfo = BlockStorage.getLocationInfo(location);
                        if(locationInfo.contains("id")) {
                            SlimefunItem item = SlimefunItem.getById(locationInfo.getString("id"));
                            if(item instanceof EnergyNetComponent) {
                                capacity = ((EnergyNetComponent) item).getCapacity();
                                if(capacity == 0) {
                                    continue;
                                }
                                charge = locationInfo.contains(KEY) ? Integer.parseInt(locationInfo.getString(KEY)) : 0;
                                charge = Math.min(charge + electricity, capacity);
                                charge = charge < 0 ? capacity : charge;
                                BlockStorage.addBlockInfo(location, KEY, String.valueOf(charge));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public List<MachineRecipe> getMachineRecipes() {
        return RECIPE;
    }

    @Override
    public void registerDefaultRecipes() {
        registerDescriptiveRecipe("&f" + this.getItemName(),
                "",
                "&f传输半径=" + this.getRange() + "格",
                "&f即以自身为中心",
                "&f边长" + (this.getRange() * 2 + 1) + "格（含）的正方体区域");
    }
}
