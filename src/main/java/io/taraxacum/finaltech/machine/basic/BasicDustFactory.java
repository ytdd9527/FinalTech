package io.taraxacum.finaltech.machine.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.abstractItem.machine.AbstractBasicMachine;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BasicDustFactory extends AbstractBasicMachine {
    public BasicDustFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.IRON_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.IRON_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.GOLD_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.GOLD_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.COPPER_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.COPPER_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.TIN_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.TIN_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.LEAD_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.LEAD_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.SILVER_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.SILVER_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.ALUMINUM_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.ALUMINUM_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.ZINC_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.ZINC_DUST, 24)});
        this.registerRecipe(0, new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64), new CustomItemStack(SlimefunItems.MAGNESIUM_DUST, 20)}, new ItemStack[] { new CustomItemStack(SlimefunItems.MAGNESIUM_DUST, 24)});
    }
}
