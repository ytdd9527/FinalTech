package io.taraxacum.finaltech.core.items.machine.standard.basic;

import io.github.thebusybiscuit.slimefun4.api.items.*;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.taraxacum.finaltech.api.dto.AdvancedCraft;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.standard.lock.AbstractLockMachineMenu;
import io.taraxacum.finaltech.core.menu.standard.lock.BasicMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.core.helper.MachineRecipeLock;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractBasicMachine extends AbstractStandardMachine {
    private static final String OFFSET_KEY = "offset";

    @ParametersAreNonnullByDefault
    protected AbstractBasicMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new BasicMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int offset = config.contains(OFFSET_KEY) ? Integer.parseInt(config.getString(OFFSET_KEY)) : 0;
        int recipeLock = config.contains(MachineRecipeLock.KEY) ? Integer.parseInt(config.getString(MachineRecipeLock.KEY)) : -2;

        CraftingOperation currentOperation = (CraftingOperation) this.getMachineProcessor().getOperation(block);
        if (currentOperation == null) {
            MachineUtil.stockSlots(blockMenu, this.getInputSlot());
            MachineRecipe machineRecipe = this.matchRecipe(blockMenu, offset, recipeLock);
            if (machineRecipe != null) {
                if (machineRecipe.getTicks() == 0) {
                    ItemStack[] outputItems = machineRecipe.getOutput();
                    for (ItemStack output : outputItems) {
                        blockMenu.pushItem(ItemStackUtil.cloneItem(output), this.getOutputSlot());
                    }
                    MachineUtil.stockSlots(blockMenu, this.getOutputSlot());
                } else {
                    this.getMachineProcessor().startOperation(block, new CraftingOperation(machineRecipe));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                if (InvUtils.fitAll(blockMenu.toInventory(), outputItems, this.getOutputSlot())) {
                    for (ItemStack output : outputItems) {
                        blockMenu.pushItem(ItemStackUtil.cloneItem(output), this.getOutputSlot());
                    }
                    this.getMachineProcessor().endOperation(block);
                }
                MachineUtil.stockSlots(blockMenu, this.getOutputSlot());
            } else {
                currentOperation.addProgress(1);
            }
        }
    }

    @Nullable
    protected final MachineRecipe matchRecipe(@Nonnull BlockMenu blockMenu, int offset, int recipeLock) {
        List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getClass());
        if (recipeLock >= 0) {
            List<AdvancedMachineRecipe> finalAdvancedMachineRecipeList = advancedMachineRecipeList;
            advancedMachineRecipeList = new ArrayList<>(1);
            advancedMachineRecipeList.add(finalAdvancedMachineRecipeList.get(recipeLock));
        }

        AdvancedCraft craft = AdvancedCraft.craftAsc(blockMenu, this.getInputSlot(), advancedMachineRecipeList, 1, offset);
        if (craft != null) {
            craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu, this.getOutputSlot(), craft.getOutputItemList())));
            if (craft.getMatchCount() > 0) {
                craft.consumeItem(blockMenu);
                if (recipeLock == Integer.parseInt(MachineRecipeLock.VALUE_UNLOCK)) {
                    ItemStack item = blockMenu.getItemInSlot(AbstractLockMachineMenu.RECIPE_LOCK_SLOT);
                    MachineRecipeLock.HELPER.setIcon(item, String.valueOf(craft.getOffset()), this);
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), MachineRecipeLock.KEY, String.valueOf(craft.getOffset()));
                } else if (recipeLock == Integer.parseInt(MachineRecipeLock.VALUE_LOCK_OFF)) {
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, String.valueOf(craft.getOffset()));
                }
                return craft.calMachineRecipe(this.getMachineRecipes().get(offset).getTicks());
            }
        }
        BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, null);
        return null;
    }
}
