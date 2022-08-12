package io.taraxacum.finaltech.core.items.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.tools.GoldPan;
import io.github.thebusybiscuit.slimefun4.implementation.items.tools.NetherGoldPan;
import io.github.thebusybiscuit.slimefun4.implementation.settings.GoldPanDrop;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SoulSandConversion extends AbstractConversionMachine{
    public SoulSandConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        GoldPan netherGoldPan = SlimefunItems.NETHER_GOLD_PAN.getItem(GoldPan.class);
        try {
            Method method = NetherGoldPan.class.getDeclaredMethod("getGoldPanDrops");
            method.setAccessible(true);
            Set<GoldPanDrop> invoke = (Set<GoldPanDrop>)method.invoke(netherGoldPan);
            List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(invoke.size());
            for (GoldPanDrop goldPanDrop : invoke) {
                randomOutputList.add(new RandomMachineRecipe.RandomOutput(goldPanDrop.getOutput(), goldPanDrop.getValue()));
            }
            this.registerRecipe(new RandomMachineRecipe(0, new ItemStack[] {new ItemStack(netherGoldPan.getInputMaterial())}, randomOutputList));
        } catch (Exception e) {
            List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>();
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {new ItemStack(Material.QUARTZ)}, 50));
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {new ItemStack(Material.GOLD_NUGGET)}, 25));
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {new ItemStack(Material.NETHER_WART)}, 10));
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {new ItemStack(Material.BLAZE_POWDER)}, 8));
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {new ItemStack(Material.GLOWSTONE_DUST)}, 5));
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[] {new ItemStack(Material.GHAST_TEAR)}, 2));
            this.registerRecipe(new RandomMachineRecipe(0, new ItemStack[] {new ItemStack(netherGoldPan.getInputMaterial())}, randomOutputList));
        }
    }
}
