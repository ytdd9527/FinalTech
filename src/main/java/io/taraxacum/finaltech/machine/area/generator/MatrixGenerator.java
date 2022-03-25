package io.taraxacum.finaltech.machine.area.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MatrixGenerator extends AbstractAreaElectricGenerator {
    public final static int ELECTRICITY = Integer.MAX_VALUE - 1;
    public final static int RANGE = 16;
    public MatrixGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull Location location, @Nonnull Config config) {
        if(BlockStorage.hasBlockInfo(location)) {
            Config locationInfo = BlockStorage.getLocationInfo(location);
            if(locationInfo.contains("id")) {
                SlimefunItem item = SlimefunItem.getById(locationInfo.getString("id"));
                if(item instanceof EnergyNetComponent) {
                    int capacity = ((EnergyNetComponent) item).getCapacity();
                    if(capacity == 0) {
                        return;
                    }
                    BlockStorage.addBlockInfo(location, KEY, String.valueOf(capacity));
                }
            }
        }
    }

    @Override
    protected int getElectricity() {
        return ELECTRICITY;
    }

    @Override
    protected int getRange() {
        return RANGE;
    }
}
