package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.FinalAdvanceMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;
import io.taraxacum.finaltech.menu.AdvancedMachineMenu;
import io.taraxacum.finaltech.util.FinalUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AdvancedAutoDrier extends FinalAdvanceMachine {

    public AdvancedAutoDrier(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        FinalUtil.registerRecipeBySlimefunId(this, "AUTO_DRIER");
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_ELECTRIC_AUTO_DRIER";
    }
}
