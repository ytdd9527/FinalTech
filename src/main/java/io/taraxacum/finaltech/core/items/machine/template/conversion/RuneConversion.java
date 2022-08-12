package io.taraxacum.finaltech.core.items.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuneConversion extends AbstractConversionMachine{
    public RuneConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {

        // EARTH
        List<RandomMachineRecipe.RandomOutput> earthRandomMachineRecipeList = new ArrayList<>(Tag.DIRT.getValues().size());
        for(Material material : Tag.DIRT.getValues()) {
            earthRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(material, 1));
        }
        this.registerRecipe(new RandomMachineRecipe(SlimefunItems.EARTH_RUNE, earthRandomMachineRecipeList));

        // AIR
        List<RandomMachineRecipe.RandomOutput> airRandomMachineRecipeList = new ArrayList<>(Tag.LEAVES.getValues().size());
        for(Material material : Tag.LEAVES.getValues()) {
            airRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(material, 1));
        }
        this.registerRecipe(new RandomMachineRecipe(SlimefunItems.AIR_RUNE, airRandomMachineRecipeList));

        // FIRE
        List<RandomMachineRecipe.RandomOutput> fireRandomMachineRecipeList = new ArrayList<>();
        fireRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.BEEF_JERKY, 1));
        fireRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.PORK_JERKY, 1));
        fireRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.CHICKEN_JERKY, 1));
        fireRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.MUTTON_JERKY, 1));
        fireRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.RABBIT_JERKY, 1));
        fireRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.FISH_JERKY, 1));
        fireRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.MONSTER_JERKY, 1));
        this.registerRecipe(new RandomMachineRecipe(SlimefunItems.FIRE_RUNE, fireRandomMachineRecipeList));

        // WATER
        List<RandomMachineRecipe.RandomOutput> waterRandomMachineRecipeList = new ArrayList<>();
        waterRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.APPLE_JUICE, 1));
        waterRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.CARROT_JUICE, 1));
        waterRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.MELON_JUICE, 1));
        waterRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.PUMPKIN_JUICE, 1));
        waterRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(SlimefunItems.SWEET_BERRY_JUICE, 1));
        this.registerRecipe(new RandomMachineRecipe(SlimefunItems.WATER_RUNE, waterRandomMachineRecipeList));

        // RAINBOW
        List<RandomMachineRecipe.RandomOutput> rainbowRandomMachineRecipeList = new ArrayList<>(Tag.FLOWERS.getValues().size());
        for(Material material : Tag.FLOWERS.getValues()) {
            rainbowRandomMachineRecipeList.add(new RandomMachineRecipe.RandomOutput(material, 1));
        }
        this.registerRecipe(new RandomMachineRecipe(SlimefunItems.RAINBOW_RUNE, rainbowRandomMachineRecipeList));
    }
}
