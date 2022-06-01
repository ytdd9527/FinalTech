package io.taraxacum.finaltech.core.items.machine.standard.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class AdvancedElectricDustWasher extends AbstractAdvanceMachine {
    public AdvancedElectricDustWasher(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>();
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.IRON_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.GOLD_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.COPPER_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.TIN_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.ZINC_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.ALUMINUM_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.MAGNESIUM_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.LEAD_DUST}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {SlimefunItems.SILVER_DUST}, 1));
        this.registerRecipe(new RandomMachineRecipe(new MachineRecipe(0, new ItemStack[] {SlimefunItems.SIFTED_ORE}, new ItemStack[0]), randomOutputList));

        this.registerRecipe(0, new ItemStack(Material.SAND, 2), SlimefunItems.SALT);
        this.registerRecipe(0, SlimefunItems.PULVERIZED_ORE, SlimefunItems.PURE_ORE_CLUSTER);
    }
}
