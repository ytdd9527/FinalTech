package io.taraxacum.finaltech.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.machine.AbstractCargo;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.LinkedBarrelMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class DoubleLinkedBarrel extends AbstractCargo {
    public DoubleLinkedBarrel(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new LinkedBarrelMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block block) {

    }
}
