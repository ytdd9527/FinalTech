package io.taraxacum.finaltech.core.items.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class GlassConversion extends AbstractConversionMachine{
    public GlassConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        RandomMachineRecipe.RandomOutput randomOutput1 = new RandomMachineRecipe.RandomOutput(Material.WHITE_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput2 = new RandomMachineRecipe.RandomOutput(Material.ORANGE_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput3 = new RandomMachineRecipe.RandomOutput(Material.MAGENTA_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput4 = new RandomMachineRecipe.RandomOutput(Material.LIGHT_BLUE_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput5 = new RandomMachineRecipe.RandomOutput(Material.YELLOW_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput6 = new RandomMachineRecipe.RandomOutput(Material.LIME_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput7 = new RandomMachineRecipe.RandomOutput(Material.PINK_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput8 = new RandomMachineRecipe.RandomOutput(Material.GRAY_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput9 = new RandomMachineRecipe.RandomOutput(Material.LIGHT_GRAY_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput10 = new RandomMachineRecipe.RandomOutput(Material.CYAN_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput11 = new RandomMachineRecipe.RandomOutput(Material.PURPLE_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput12 = new RandomMachineRecipe.RandomOutput(Material.BLUE_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput13 = new RandomMachineRecipe.RandomOutput(Material.BROWN_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput14 = new RandomMachineRecipe.RandomOutput(Material.GREEN_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput15 = new RandomMachineRecipe.RandomOutput(Material.RED_STAINED_GLASS, 1);
        RandomMachineRecipe.RandomOutput randomOutput16 = new RandomMachineRecipe.RandomOutput(Material.BLACK_STAINED_GLASS, 1);
        RandomMachineRecipe randomMachineRecipe = new RandomMachineRecipe(new ItemStack[] {new ItemStack(Material.GLASS)}, List.of(randomOutput1, randomOutput2, randomOutput3, randomOutput4, randomOutput5, randomOutput6, randomOutput7, randomOutput8, randomOutput9, randomOutput10, randomOutput11, randomOutput12, randomOutput13, randomOutput14, randomOutput15, randomOutput16));
        this.registerRecipe(randomMachineRecipe);
    }
}
