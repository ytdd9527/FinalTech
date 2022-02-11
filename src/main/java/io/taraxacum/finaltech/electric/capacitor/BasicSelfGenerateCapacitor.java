package io.taraxacum.finaltech.electric.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.machine.AbstractElectricCapacitor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class BasicSelfGenerateCapacitor extends AbstractElectricCapacitor {
    public static final int CAPACITOR = 2097152;
    public static final int EFFICIENT = 1024;
    public BasicSelfGenerateCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        // todo
        return CAPACITOR;
    }

    @Override
    protected void tick(Block block) {
        Location location = block.getLocation();
        setCharge(location, (int) (getCharge(location) * (1 + 1L / (double) EFFICIENT)));
        super.tick(block);
    }
}
