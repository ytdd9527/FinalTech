package io.taraxacum.finaltech.core.items.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.core.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.standard.lock.MatrixCraftingTableMenu;
import io.taraxacum.finaltech.setup.FinalTechRecipes;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.SlimefunUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixCraftingTable extends AbstractStandardMachine {
    public MatrixCraftingTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new MatrixCraftingTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        List<MachineRecipe> recipe = MachineRecipeFactory.getRecipe(this.getClass());
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Map<Integer, ItemStackWithWrapper> itemWithWrapperMap = new HashMap<>(this.getInputSlot().length);
        for (int i = 0; i < this.getInputSlot().length; i++) {
            if (!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(this.getInputSlot()[i]))) {
                itemWithWrapperMap.put(i, new ItemStackWithWrapper(blockMenu.getItemInSlot(this.getInputSlot()[i])));
            }
        }
        MachineRecipe matchRecipe = null;
        for (MachineRecipe machineRecipe : recipe) {
            ItemStack[] input = machineRecipe.getInput();
            if (input.length > this.getInputSlot().length) {
                continue;
            }
            boolean work = true;
            for (int i = 0; i < this.getInputSlot().length; i++) {
                if (ItemStackUtil.isItemNull(input[i])) {
                    continue;
                }
                if (itemWithWrapperMap.get(i).getItemStack().getAmount() < input[i].getAmount() || !ItemStackUtil.isItemSimilar(input[i], itemWithWrapperMap.get(i))) {
                    work = false;
                    break;
                }
            }
            if (work && MachineUtil.calMaxMatch(blockMenu, this.getOutputSlot(), MachineRecipeFactory.getAdvancedRecipe(this.getClass()).get(recipe.indexOf(machineRecipe)).getOutput()) >= 1) {
                matchRecipe = machineRecipe;
                break;
            }
        }

        if (matchRecipe != null) {
            for (int i = 0; i < this.getInputSlot().length; i++) {
                if (ItemStackUtil.isItemNull(matchRecipe.getInput()[i])) {
                    itemWithWrapperMap.get(i).getItemStack().setAmount(itemWithWrapperMap.get(i).getItemStack().getAmount() - matchRecipe.getInput()[i].getAmount());
                }
            }
            blockMenu.pushItem(ItemStackUtil.cloneItem(matchRecipe.getOutput()[0]), this.getOutputSlot());
        }
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerRecipeByRecipeType(this, FinalTechRecipes.RECIPE_TYPE_MATRIX_CRAFTING_TABLE);
    }
}
