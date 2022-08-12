package io.taraxacum.finaltech.api.interfaces;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.unusable.liquid.LiquidCard;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link SlimefunItem} that will show its working-recipe in {@link SlimefunGuide}.
 * @author Final_ROOT
 * @since 1.0
 */
public interface RecipeItem extends RecipeDisplayItem {
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
            if(inputLength == 0) {
                displayRecipes.add(new ItemStack(Material.AIR));
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

    @Nonnull
    default List<MachineRecipe> getMachineRecipes() {
        return MachineRecipeFactory.getInstance().getRecipe(this.getClass());
    }

    default void registerRecipe(@Nonnull MachineRecipe recipe) {
        this.getMachineRecipes().add(recipe);
    }

    @Deprecated
    default void registerRecipe(int seconds, @Nonnull ItemStack[] input, @Nonnull ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(seconds, input, output));
    }

    default void registerRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output) {
        this.registerRecipe(new MachineRecipe(0, new ItemStack[] {input}, new ItemStack[] {output}));
    }

    default void registerRecipe(@Nonnull ItemStack input, @Nonnull Material output) {
        this.registerRecipe(new MachineRecipe(0, new ItemStack[] {input}, new ItemStack[] {new ItemStack(output)}));
    }

    default void registerRecipe(@Nonnull Material input, @Nonnull ItemStack output) {
        this.registerRecipe(new MachineRecipe(0, new ItemStack[] {new ItemStack(input)}, new ItemStack[] {output}));
    }

    default void registerRecipe(@Nonnull Material input, @Nonnull Material output) {
        this.registerRecipe(new MachineRecipe(0, new ItemStack[] {new ItemStack(input)}, new ItemStack[] {new ItemStack(output)}));
    }

    default void registerRecipe(@Nonnull ItemStack[] input, @Nonnull ItemStack[] output) {
        this.registerRecipe(new MachineRecipe(0, input, output));
    }

    @Deprecated
    default void registerRecipe(int seconds, @Nonnull ItemStack input, @Nonnull ItemStack output) {
        this.registerRecipe(new MachineRecipe(seconds, new ItemStack[] {input}, new ItemStack[] {output}));
    }

    /**
     * register a {@link SlimefunItem} whit it's recipe as a {@link MachineRecipe}.
     * if the slimefun-item's recipe contains liquid-bucket(water bucket, lava bucket e.g.),
     * this method will also register another similar machine-recipe that replace liquid-bucket with {@link LiquidCard}
     */
    default void registerRecipeInCard(int seconds, @Nonnull SlimefunItem slimefunItem) {
        this.registerRecipeInCard(seconds, slimefunItem.getRecipe(), new ItemStack[] {slimefunItem.getRecipeOutput()});
    }

    default void registerRecipeInCard(int seconds, @Nonnull ItemStack[] input, @Nonnull ItemStack[] output) {
        boolean extraRecipe = false;
        List<ItemStack> inputList1 = new ArrayList<>(input.length);
        List<ItemStack> inputList2 = new ArrayList<>(input.length);
        List<ItemStack> outputList1 = new ArrayList<>(output.length);
        List<ItemStack> outputList2 = new ArrayList<>(output.length);
        for (ItemStack item : output) {
            if (!ItemStackUtil.isItemNull(item) && !ItemStackUtil.isItemSimilar(item, new CustomItemStack(Material.BUCKET))) {
                outputList1.add(item);
            }
        }
        outputList2.addAll(outputList1);
        for (ItemStack item : input) {
            if (ItemStackUtil.isItemNull(item)) {
                continue;
            }
            ItemStack liquidCardItem = ItemStackUtil.getLiquidCard(item);
            if (liquidCardItem == null) {
                inputList1.add(item);
                inputList2.add(item);
            } else {
                extraRecipe = true;
                inputList1.add(item);
                inputList2.add(liquidCardItem);
                outputList1.add(new CustomItemStack(Material.BUCKET));
            }
        }
        if (extraRecipe) {
            this.registerRecipe(seconds, ItemStackWrapper.wrapArray(ItemStackUtil.getNoNullItemArray(ItemStackUtil.calMergeItems(inputList1))), ItemStackUtil.getNoNullItemArray(ItemStackUtil.calMergeItems(outputList1)));
            this.registerRecipe(seconds, ItemStackWrapper.wrapArray(ItemStackUtil.getNoNullItemArray(ItemStackUtil.calMergeItems(inputList2))), ItemStackUtil.getNoNullItemArray(ItemStackUtil.calMergeItems(outputList2)));
        } else {
            this.registerRecipe(seconds, ItemStackWrapper.wrapArray(ItemStackUtil.calMergeItems(input)), ItemStackWrapper.wrapArray(ItemStackUtil.calMergeItems(output)));
        }
    }

    /**
     * Register a {@link MachineRecipe} that will only be used to show info to player.
     * @param item
     */
    default void registerDescriptiveRecipe(@Nonnull ItemStack item) {
        this.registerRecipe(new MachineRecipe(0, new ItemStack[] {item}, new ItemStack[] {ItemStackUtil.AIR}));
    }
    default void registerDescriptiveRecipe(@Nonnull String name, @Nonnull String... lore) {
        this.registerDescriptiveRecipe(new CustomItemStack(Material.BOOK, name, lore));
    }
    default void registerDescriptiveRecipe(@Nonnull ItemStack item, @Nonnull String name, @Nonnull String... lore) {
        this.registerRecipe(0, new CustomItemStack(Material.BOOK, name, lore), item);
    }

    void registerDefaultRecipes();
}
