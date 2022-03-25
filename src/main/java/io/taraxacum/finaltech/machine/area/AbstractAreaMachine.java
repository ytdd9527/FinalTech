package io.taraxacum.finaltech.machine.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractAreaMachine extends AbstractMachine {
    public AbstractAreaMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    protected abstract int getRange();

    protected abstract void function(@Nonnull Location location, @Nonnull Config config);

    @Override
    protected final void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        int range = getRange();
        Location location = block.getLocation();
        World world = location.getWorld();
        int minX = location.getBlockX() - range;
        int minY = Math.max(location.getBlockY() - range, world.getMinHeight());
        int minZ = location.getBlockZ() - range;
        int maxX = location.getBlockX() + range;
        int maxY  = Math.min(location.getBlockY() + range, world.getMaxHeight());
        int maxZ = location.getBlockZ() + range;
        for(int x = minX; x <= maxX; x++) {
            location.setX(x);
            for(int y = minY; y <= maxY; y++) {
                location.setY(y);
                for(int z = minZ; z <= maxZ; z++) {
                    location.setZ(z);
                    function(location, config);
                }
            }
        }
    }
}
