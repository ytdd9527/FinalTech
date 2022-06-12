package io.taraxacum.finaltech.core.items.machine.standard.production;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapperAmount;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.standard.ProductionStationMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractProductionStationMachine extends AbstractStandardMachine implements RecipeItem {
    private RandomMachineRecipe emptyInputRecipe;

    public AbstractProductionStationMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ProductionStationMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getClass());
        AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((int) (new Random().nextDouble() * advancedMachineRecipeList.size()));
        List<ItemStackWithWrapperAmount> outputList = advancedMachineRecipe.getOutput();
        int maxMatch = MachineUtil.calMaxMatch(blockMenu, this.getOutputSlot(), outputList);
        if(maxMatch > 0) {
            for(ItemStackWithWrapper itemStackWithWrapper : outputList) {
                blockMenu.pushItem(ItemStackUtil.cloneItem(itemStackWithWrapper.getItemStack()), this.getOutputSlot());
            }
        }
    }

    @Override
    public final void registerDefaultRecipes() {
        this.registerRandomOutputRecipes();
        this.getMachineRecipes().add(this.emptyInputRecipe);
    }

    @Override
    public void registerRecipe(@Nonnull MachineRecipe recipe) {
        if(recipe.getInput().length > 0) {
            throw new IllegalArgumentException("Register recipe for " + this.getItemName() + " has made a error: " + " input item amount should not be more than zero");
        }

        RandomMachineRecipe randomMachineRecipe;
        if(recipe instanceof RandomMachineRecipe) {
            randomMachineRecipe = (RandomMachineRecipe) recipe;
        } else {
            List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(1);
            randomOutputList.add(new RandomMachineRecipe.RandomOutput(recipe.getOutput(), 1));
            randomMachineRecipe = new RandomMachineRecipe(recipe, randomOutputList);
        }

        if(this.emptyInputRecipe == null) {
            this.emptyInputRecipe = new RandomMachineRecipe(0, new ItemStack[0], randomMachineRecipe.getRandomOutputList());
        } else {
            this.emptyInputRecipe.addRandomOutput(randomMachineRecipe.getRandomOutputList());
        }
    }

    abstract void registerRandomOutputRecipes();

    public void registerRecipe(ItemStack output, int weight) {
        this.registerRecipe(new RandomMachineRecipe(0, new ItemStack[0], List.of(new RandomMachineRecipe.RandomOutput(output, weight))));
    }
    public void registerRecipe(ItemStack output) {
        this.registerRecipe(new RandomMachineRecipe(0, new ItemStack[0], List.of(new RandomMachineRecipe.RandomOutput(output, 1))));
    }
}
