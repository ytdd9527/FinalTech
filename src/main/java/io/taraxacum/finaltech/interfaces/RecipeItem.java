package io.taraxacum.finaltech.interfaces;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.api.RandomMachineRecipe;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Final_ROOT
 */
public interface RecipeItem extends RecipeDisplayItem {

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
            int outputLength = recipe instanceof RandomMachineRecipe ? ((RandomMachineRecipe) recipe).getAllOutput().length : recipe.getOutput().length;
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
                displayRecipes.add(new ItemStack(recipe instanceof RandomMachineRecipe ? ((RandomMachineRecipe) recipe).getAllOutput()[i] : recipe.getOutput()[i]));
            }
        }
        return displayRecipes;
    }

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

    default void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(seconds, input, output));
    }

    default void registerRecipe(ItemStack[] input, ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(0, input, output));
    }

    default void registerRecipe(int seconds, ItemStack input, ItemStack output) {
        this.registerRecipe(new MachineRecipe(seconds, new ItemStack[] {input}, new ItemStack[] {output}));
    }

    default void registerRecipeInCard(int seconds, SlimefunItem slimefunItem) {
        this.registerRecipeInCard(seconds, slimefunItem.getRecipe(), new ItemStack[] {slimefunItem.getItem()});
    }

    default void registerRecipeInCard(int seconds, ItemStack[] input, ItemStack[] output) {
        boolean extraRecipe = false;
        List<ItemStack> inputList1 = new ArrayList<>(input.length);
        List<ItemStack> inputList2 = new ArrayList<>(input.length);
        List<ItemStack> outputList1 = new ArrayList<>(output.length);
        List<ItemStack> outputList2 = new ArrayList<>(output.length);
        for(ItemStack item : output) {
            if(!ItemStackUtil.isItemNull(item) && !ItemStackUtil.isItemSimilar(item, new CustomItemStack(Material.BUCKET))) {
                outputList1.add(item);
            }
        }
        outputList2.addAll(outputList1);
        for(ItemStack item : input) {
            if(ItemStackUtil.isItemNull(item)) {
                continue;
            }
            ItemStack liquidCardItem = ItemStackUtil.getLiquidCard(item);
            if(liquidCardItem == null) {
                inputList1.add(item);
                inputList2.add(item);
            } else {
                extraRecipe = true;
                inputList1.add(item);
                inputList2.add(liquidCardItem);
                outputList1.add(new CustomItemStack(Material.BUCKET));
            }
        }
        if(extraRecipe) {
            this.registerRecipe(seconds, ItemStackWrapper.wrapArray(ItemStackUtil.calNoNullItemArray(ItemStackUtil.calMergeItemList(inputList1))), ItemStackUtil.calNoNullItemArray(ItemStackUtil.calMergeItemList(outputList1)));
            this.registerRecipe(seconds, ItemStackWrapper.wrapArray(ItemStackUtil.calNoNullItemArray(ItemStackUtil.calMergeItemList(inputList2))), ItemStackUtil.calNoNullItemArray(ItemStackUtil.calMergeItemList(outputList2)));
        } else {
            this.registerRecipe(seconds, ItemStackWrapper.wrapArray(ItemStackUtil.calMergeItemList(input)), ItemStackWrapper.wrapArray(ItemStackUtil.calMergeItemList(output)));
        }
    }

    default void registerDescriptiveRecipe(ItemStack item) {
        this.registerRecipe(new MachineRecipe(0, new ItemStack[] {item}, new ItemStack[] {ItemStackUtil.AIR}));
    }

    default void registerDescriptiveRecipe(String name, String... lore) {
        this.registerDescriptiveRecipe(new CustomItemStack(Material.BOOK, name, lore));
    }

    /**
     * 默认的配方注册方法
     * 继承了该接口的实现类应该重写该方法
     * 并在该方法内实现注册机器的工作配方
     */
    void registerDefaultRecipes();
}
