package io.taraxacum.finaltech.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.machine.AbstractMachine;
import io.taraxacum.finaltech.menu.manual.AbstractManualMachineMenu;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractManualMachine extends AbstractMachine {
    private AbstractManualMachineMenu menu;

    public AbstractManualMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected final AbstractManualMachineMenu setMachineMenu() {
        this.menu = this.newMachineMenu();
        return this.menu;
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    protected final AbstractManualMachineMenu getMachineMenu() {
        if (this.menu == null) {
            this.menu = this.newMachineMenu();
        }
        return this.menu;
    }

    @Nonnull
    protected abstract AbstractManualMachineMenu newMachineMenu();
}
