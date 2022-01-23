package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.FinalBasicMachine;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class BasicCobbleFactory extends FinalBasicMachine {

    public BasicCobbleFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerRecipe(0, new ItemStack(Material.COBBLESTONE, 20), new ItemStack(Material.COBBLESTONE, 32));
        this.registerRecipe(0, new ItemStack(Material.COBBLED_DEEPSLATE, 20), new ItemStack(Material.COBBLED_DEEPSLATE, 32));
        this.registerRecipe(0, new ItemStack(Material.GRANITE, 20), new ItemStack(Material.GRANITE, 32));
        this.registerRecipe(0, new ItemStack(Material.DIORITE, 20), new ItemStack(Material.DIORITE, 32));
        this.registerRecipe(0, new ItemStack(Material.ANDESITE, 20), new ItemStack(Material.ANDESITE, 32));
        this.registerRecipe(0, new ItemStack(Material.NETHERRACK, 20), new ItemStack(Material.NETHERRACK, 32));
        this.registerRecipe(0, new ItemStack(Material.BASALT, 20), new ItemStack(Material.BASALT, 32));
        this.registerRecipe(0, new ItemStack(Material.BLACKSTONE, 20), new ItemStack(Material.BLACKSTONE, 32));
        this.registerRecipe(0, new ItemStack(Material.OBSIDIAN, 20), new ItemStack(Material.OBSIDIAN, 32));
        this.registerRecipe(0, new ItemStack(Material.CALCITE, 20), new ItemStack(Material.CALCITE, 32));
        this.registerRecipe(0, new ItemStack(Material.TUFF, 20), new ItemStack(Material.TUFF, 32));
        this.registerRecipe(0, new ItemStack(Material.DIRT, 20), new ItemStack(Material.DIRT, 32));
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINAL_BASIC_COBBLE_FACTORY";
    }
}
