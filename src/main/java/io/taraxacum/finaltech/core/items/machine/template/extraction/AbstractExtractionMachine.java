package io.taraxacum.finaltech.core.items.machine.template.extraction;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.AdvancedMachineRecipe;
import io.taraxacum.finaltech.api.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.api.dto.ItemWrapper;
import io.taraxacum.finaltech.api.factory.MachineRecipeFactory;
import io.taraxacum.finaltech.core.items.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ExtractionMachineMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractExtractionMachine extends AbstractMachine {
    public AbstractExtractionMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ExtractionMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int itemSlot = this.getInputSlot()[FinalTech.getRandom().nextInt(this.getInputSlot().length)];
        ItemStack item = blockMenu.getItemInSlot(itemSlot);
        if(ItemStackUtil.isItemNull(item)) {
            return;
        }
        ItemWrapper itemWrapper = new ItemWrapper(item);
        int matchAmount = 0;
        ItemAmountWrapper[] outputItems = null;
        List<AdvancedMachineRecipe> advancedRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getClass());
        for(AdvancedMachineRecipe advancedMachineRecipe : advancedRecipeList) {
            if(itemWrapper.getItemStack().getAmount() >= advancedMachineRecipe.getInput()[0].getAmount() && ItemStackUtil.isItemSimilar(advancedMachineRecipe.getInput()[0], itemWrapper)) {
                outputItems = advancedMachineRecipe.getOutput();
                matchAmount = itemWrapper.getItemStack().getAmount() / advancedMachineRecipe.getInput()[0].getAmount();
                break;
            }
        }

        if(outputItems != null) {
            matchAmount = Math.min(matchAmount, MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), outputItems));
            if(matchAmount > 0) {
                for(ItemStack outputItem : ItemStackUtil.calEnlargeItemArray(outputItems, matchAmount)) {
                    blockMenu.pushItem(outputItem, this.getOutputSlot());
                }
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }
}
