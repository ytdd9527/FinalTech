package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.FinalAdvanceMachine;
import io.taraxacum.finaltech.util.FinalUtil;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AdvancedEletricPress extends FinalAdvanceMachine {

    public AdvancedEletricPress(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        FinalUtil.registerRecipeBySlimefunId(this, "ELECTRIC_PRESS");
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_ADVANCED_ELETRIC_PRESS";
    }

    @Override
    protected void tickBefore(Block block) {

    }

    @Override
    protected void tickAfter(Block block) {

    }
}
