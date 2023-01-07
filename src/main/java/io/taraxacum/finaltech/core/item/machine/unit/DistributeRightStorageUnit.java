package io.taraxacum.finaltech.core.item.machine.unit;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.OneLineStorageUnitMenu;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
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
public class DistributeRightStorageUnit extends AbstractCargo implements RecipeItem {
    public DistributeRightStorageUnit(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new OneLineStorageUnitMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int beginSlot = 0;
        int endSlot = 0;
        int i;
        ItemAmountWrapper itemWithWrapperAmount = null;
        for (i = 0; i < this.getInputSlot().length; i++) {
            if (!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(i))) {
                itemWithWrapperAmount = new ItemAmountWrapper(blockMenu.getItemInSlot(i));
                beginSlot = i;
                endSlot = i++;
                break;
            }
        }
        for (; i < this.getInputSlot().length; i++) {
            if (ItemStackUtil.isItemNull(blockMenu.getItemInSlot(i))) {
                endSlot = i;
            } else if (ItemStackUtil.isItemSimilar(itemWithWrapperAmount, blockMenu.getItemInSlot(i))) {
                itemWithWrapperAmount.addAmount(blockMenu.getItemInSlot(i).getAmount());
                endSlot = i;
            } else {
                int amount = itemWithWrapperAmount.getAmount() / (endSlot + 1 - beginSlot);
                if (amount > 0) {
                    for (int j = beginSlot; j <= endSlot; j++) {
                        ItemStack item = ItemStackUtil.cloneItem(itemWithWrapperAmount.getItemStack());
                        item.setAmount(amount);
                        blockMenu.replaceExistingItem(j, item);
                    }
                    ItemStack item = ItemStackUtil.cloneItem(itemWithWrapperAmount.getItemStack());
                    item.setAmount(amount + (itemWithWrapperAmount.getAmount() % (endSlot + 1 - beginSlot)));
                    blockMenu.replaceExistingItem(beginSlot, item);
                }
                itemWithWrapperAmount = new ItemAmountWrapper(blockMenu.getItemInSlot(i));
                beginSlot = i;
                endSlot = i;
            }
        }
        if (beginSlot != endSlot) {
            int amount = itemWithWrapperAmount.getAmount() / (endSlot + 1 - beginSlot);
            if (amount > 0) {
                for (int j = beginSlot; j <= endSlot; j++) {
                    ItemStack item = ItemStackUtil.cloneItem(itemWithWrapperAmount.getItemStack());
                    item.setAmount(amount);
                    blockMenu.replaceExistingItem(j, item);
                }
                ItemStack item = ItemStackUtil.cloneItem(itemWithWrapperAmount.getItemStack());
                item.setAmount(amount + itemWithWrapperAmount.getAmount() % (endSlot + 1 - beginSlot));
                blockMenu.replaceExistingItem(beginSlot, item);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this, String.valueOf(MachineUtil.calMachineSlotSize(this)));
    }
}
