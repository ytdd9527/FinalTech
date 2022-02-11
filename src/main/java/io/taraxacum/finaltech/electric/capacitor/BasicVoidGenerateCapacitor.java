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
public class BasicVoidGenerateCapacitor extends AbstractElectricCapacitor {
    public static final int CAPACITOR = 2097152;
    public static final int EFFICIENT = 1024;
    public BasicVoidGenerateCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        if(getCharge(location) == 0) {
            setCharge(location, getCapacity() / EFFICIENT);
        }
        super.tick(block);
    }
}
