package io.taraxacum.finaltech.core.items.machine.standard.workshop;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.api.dto.RandomMachineRecipe;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.standard.WorkshopMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractWorkshopMachine extends AbstractStandardMachine implements RecipeItem {
    public AbstractWorkshopMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new WorkshopMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory inventory = blockMenu.toInventory();
        List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getClass());
        ItemStackWithWrapper itemStackWithWrapper = null;
        for(int slot : this.getInputSlot()) {
            ItemStack item = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(item)) {
                continue;
            }
            if(itemStackWithWrapper == null) {
                itemStackWithWrapper = new ItemStackWithWrapper(item);
            } else {
                itemStackWithWrapper.warpItem(item);
            }
            for(AdvancedMachineRecipe advancedMachineRecipe : advancedMachineRecipeList) {
                if(ItemStackUtil.isItemSimilar(itemStackWithWrapper, advancedMachineRecipe.getInput().get(0))) {
                    inventory.setItem(slot, new CustomItemStack(advancedMachineRecipe.getOutput().get(0).getItemStack(), item.getAmount()));
                    break;
                }
            }
        }
    }

    @Override
    public void registerRecipe(@Nonnull MachineRecipe recipe) {
        if(recipe.getInput().length != 1) {
            throw new IllegalArgumentException("Register recipe for " + this.getItemName() + " has made a error: " + " input item type should be just one");
        }
        if(recipe.getInput()[0].getAmount() > 1) {
            this.getAddon().getJavaPlugin().getServer().getLogger().info("Register recipe for " + this.getItemName() + " has made a error: " + " input item amount should be one");
            recipe.getInput()[0] = new CustomItemStack(recipe.getInput()[0], 1);
        }
        if(recipe instanceof RandomMachineRecipe) {
            for(RandomMachineRecipe.RandomOutput randomOutput : ((RandomMachineRecipe) recipe).getRandomOutputList()) {
                if(randomOutput.getOutputItem().size() != 1) {
                    throw new IllegalArgumentException("Register recipe for " + this.getItemName() + " has made a error: " + " out item type should only just one");
                }
                if(randomOutput.getOutputItem().get(0).getAmount() != 1) {
                    this.getAddon().getJavaPlugin().getServer().getLogger().info("Register recipe for " + this.getItemName() + " has made a error: " + " output item amount should be one");
                    randomOutput.getOutputItem().set(0, new CustomItemStack(randomOutput.getOutputItem().get(0), 1));
                }
            }
        } else {
            for(int i = 0; i < 100; i++) {
                if(recipe.getOutput().length != 1) {
                    throw new IllegalArgumentException("Register recipe for " + this.getItemName() + " has made a error: " + " out item type should only just one");
                }
                if(recipe.getOutput()[0].getAmount() != 1) {
                    this.getAddon().getJavaPlugin().getServer().getLogger().info("Register recipe for " + this.getItemName() + " has made a error: " + " output item amount should be one");
                    recipe.getOutput()[0] = new CustomItemStack(recipe.getOutput()[0], 1);
                }
            }
        }

        super.registerRecipe(recipe);
    }

    public void registerRecipe(ItemStack input, ItemStack output) {
        this.registerRecipe(0, input, output);
    }
}
