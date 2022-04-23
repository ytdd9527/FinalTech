package io.taraxacum.finaltech.machine.standard.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.taraxacum.finaltech.dto.AdvancedCraftV2;
import io.taraxacum.finaltech.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.machine.standard.AbstractStandardMachine;
import io.taraxacum.finaltech.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.menu.standard.lock.AbstractLockMachineMenu;
import io.taraxacum.finaltech.menu.standard.lock.AdvancedMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.menu.RecipeLockHelper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public abstract class AbstractAdvanceMachine extends AbstractStandardMachine {
    private static final String OFFSET_KEY = "offset";

    protected AbstractAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, AdvancedMachineMenu.MODULE_SLOT);
    }

    @Nonnull
    @Override
    protected final AbstractMachineMenu setMachineMenu() {
        return new AdvancedMachineMenu(this);
    }

    @Override
    protected final void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int offset = config.contains(OFFSET_KEY) ? Integer.parseInt(config.getString(OFFSET_KEY)) : 0;
        int recipeLock = config.contains(RecipeLockHelper.KEY) ? Integer.parseInt(config.getString(RecipeLockHelper.KEY)) : -2;

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

    protected MachineRecipe matchRecipe(@Nonnull BlockMenu blockMenu, int offset, int recipeLock) {
        int quantityModule = MachineUtil.updateQuantityModule(blockMenu, AdvancedMachineMenu.MODULE_SLOT, AdvancedMachineMenu.STATUS_SLOT);

        List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getAdvancedRecipe(this.getClass());
        if(recipeLock >= 0) {
            List<AdvancedMachineRecipe> finalAdvancedMachineRecipeList = advancedMachineRecipeList;
            advancedMachineRecipeList = new ArrayList<>(1);
            advancedMachineRecipeList.add(finalAdvancedMachineRecipeList.get(recipeLock));
        }

        AdvancedCraftV2 craft = AdvancedCraftV2.craftAsc(blockMenu, this.getInputSlot(), advancedMachineRecipeList, quantityModule, offset);
        if(craft != null) {
            craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu, this.getOutputSlot(), craft.getOutputItemList())));
            if(craft.getMatchCount() > 0) {
                craft.consumeItem(blockMenu);
                if(recipeLock == RecipeLockHelper.INT_VALUE_UNLOCK) {
                    ItemStack item = blockMenu.getItemInSlot(AbstractLockMachineMenu.RECIPE_LOCK_SLOT);
                    RecipeLockHelper.setIcon(item, craft.getOffset(), this);
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), RecipeLockHelper.KEY, String.valueOf(craft.getOffset()));
                } else if(recipeLock == RecipeLockHelper.INT_VALUE_LOCK_OFF) {
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, String.valueOf(craft.getOffset()));
                }
                return craft.calMachineRecipe(this.getMachineRecipes().get(offset).getTicks());
            }
        }
        BlockStorage.addBlockInfo(blockMenu.getLocation(), OFFSET_KEY, null);
        return null;
    }
}
