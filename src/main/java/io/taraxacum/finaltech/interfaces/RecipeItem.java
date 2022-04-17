package io.taraxacum.finaltech.interfaces;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.api.RandomMachineRecipe;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public interface RecipeItem extends RecipeDisplayItem {

    /**
     * Get the machineRecipes for it
     * @return
     */
    default List<MachineRecipe> getMachineRecipes() {
        return MachineRecipeFactory.getRecipe(this.getClass());
    };

    /**
     * register a machineRecipes for itself
     * @param recipe
     */
    default void registerRecipe(MachineRecipe recipe) {
        this.getMachineRecipes().add(recipe);
    }

    default void registerDescriptiveRecipe(ItemStack item) {
        this.registerRecipe(new MachineRecipe(0, new ItemStack[] {item}, new ItemStack[] {ItemStackUtil.AIR}));
    }

    default void registerDescriptiveRecipe(String name, String... lore) {
        this.registerDescriptiveRecipe(new CustomItemStack(Material.BOOK, name, lore));
    }

    default void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(seconds, input, output));
    }

    default void registerRecipe(ItemStack[] input, ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(0, input, output));
    }

    default void registerRecipe(int seconds, ItemStack input, ItemStack output) {
        this.registerRecipe(new MachineRecipe(seconds, new ItemStack[]{input}, new ItemStack[]{output}));
    }

    default void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output, boolean randomOutput) {
        this.registerRecipe(new RandomMachineRecipe(seconds, input, output, randomOutput));
    }

    /**
     * 默认的配方注册方法
     * 继承了该接口的实现类应该重写该方法
     * 并在该方法内实现注册机器的工作配方
     */
    void registerDefaultRecipes();

    /**
     * 获取物品展示出的工作配方图标
     * @return
     */
    @Nonnull
    @Override
    default List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(this.getMachineRecipes().size() * 2);
        for (MachineRecipe recipe : this.getMachineRecipes()) {
            int inputLength = recipe.getInput().length;
            int outputLength = recipe.getOutput().length;
            for (int i = 0; i < inputLength; i++) {
                displayRecipes.add(new ItemStack(recipe.getInput()[i]));
                if (i < inputLength - 1) {
                    displayRecipes.add(new ItemStack(Material.AIR));
                }
            }
            for (int i = 0; i < outputLength; i++) {
                if (i != 0) {
                    displayRecipes.add(new ItemStack(Material.AIR));
                }
                displayRecipes.add(new ItemStack(recipe.getOutput()[i]));
            }
        }
        return displayRecipes;
    }
}
