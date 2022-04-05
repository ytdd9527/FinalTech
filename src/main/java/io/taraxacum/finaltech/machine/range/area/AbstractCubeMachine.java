package io.taraxacum.finaltech.machine.range.area;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.machine.range.AbstractRangeMachine;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractCubeMachine extends AbstractRangeMachine {
    public AbstractCubeMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected final int function(@Nonnull Block block, int range, @Nonnull Function function) {
        int count = 0;
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
                    count += function.function(location);
                }
            }
        }
        return count;
    }
}
