package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.MatrixCraftingTableMenu;
import io.taraxacum.finaltech.setup.FinalTechRecipes;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixCraftingTable extends AbstractManualMachine implements RecipeItem {
    public MatrixCraftingTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new MatrixCraftingTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerRecipeByRecipeType(this, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE);
    }
}
