package io.taraxacum.finaltech.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.FinalCargoMachine;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class DoubleLinkedBarrel extends FinalCargoMachine {
    public DoubleLinkedBarrel(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(Block block) {

    }
}
