package io.taraxacum.finaltech.core.items.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.MatrixItemDismantleTableMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.slimefun.SfItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixItemDismantleTable extends AbstractMachine implements RecipeItem {
    private final Set<String> NOT_ALLOWED_RECIPE_ID = new HashSet<>(FinalTech.getItemManager().getStringList(SfItemUtil.getIdFormatName(this.getClass()), "not-allowed-recipe-id"));
    private final Set<String> ALLOWED_ID = new HashSet<>(FinalTech.getItemManager().getStringList(SfItemUtil.getIdFormatName(this.getClass()), "allowed-id"));
    private final Set<String> NOT_ALLOWED_ID = new HashSet<>(FinalTech.getItemManager().getStringList(SfItemUtil.getIdFormatName(this.getClass()), "not-allowed-id"));

    public MatrixItemDismantleTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new MatrixItemDismantleTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (MachineUtil.isEmpty(blockMenu.toInventory(), this.getOutputSlot())) {
            ItemStack item = blockMenu.getItemInSlot(this.getInputSlot()[0]);
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null && item.getAmount() >= sfItem.getRecipeOutput().getAmount() && this.calAllowed(sfItem) && ItemStackUtil.isEnchantmentSame(item, sfItem.getRecipeOutput()) && ItemStackUtil.isItemSimilar(item, sfItem.getRecipeOutput())) {
                int amount = item.getAmount() / sfItem.getRecipeOutput().getAmount();
                for (ItemStack outputItem : sfItem.getRecipe()) {
                    if (!ItemStackUtil.isItemNull(outputItem)) {
                        amount = Math.min(amount, outputItem.getMaxStackSize() / outputItem.getAmount());
                    }
                }
                item.setAmount(item.getAmount() - sfItem.getRecipeOutput().getAmount() * amount);
                for (int i = 0; i < this.getOutputSlot().length && i < sfItem.getRecipe().length; i++) {
                    if (!ItemStackUtil.isItemNull(sfItem.getRecipe()[i])) {
                        ItemStack outputItem = ItemStackUtil.cloneItem(sfItem.getRecipe()[i]);
                        ItemStack liquidCard = ItemStackUtil.getLiquidCard(outputItem);
                        if(liquidCard != null) {
                            outputItem = liquidCard;
                        }
                        outputItem.setAmount(outputItem.getAmount() * amount);
                        blockMenu.replaceExistingItem(this.getOutputSlot()[i], outputItem);
                    }
                }
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        for (String id : NOT_ALLOWED_RECIPE_ID) {
            SlimefunItem slimefunItem = SlimefunItem.getById(id);
            if(slimefunItem != null) {
                this.registerDescriptiveRecipe(slimefunItem.getItem());
            }
        }
    }

    private boolean calAllowed(@Nonnull SlimefunItem slimefunItem) {
        if(this.ALLOWED_ID.contains(slimefunItem.getId())) {
            return true;
        } else if(this.NOT_ALLOWED_ID.contains(slimefunItem.getId())) {
            return false;
        } else {
            synchronized (this) {
                if(this.ALLOWED_ID.contains(slimefunItem.getId())) {
                    return true;
                } else if(this.NOT_ALLOWED_ID.contains(slimefunItem.getId())) {
                    return false;
                }

                if(this.NOT_ALLOWED_RECIPE_ID.contains(slimefunItem.getRecipeType().getMachine().getId())) {
                    this.NOT_ALLOWED_ID.add(slimefunItem.getId());
                    return false;
                }

                if(slimefunItem.getRecipe().length > this.getOutputSlot().length) {
                    this.NOT_ALLOWED_ID.add(slimefunItem.getId());
                    return false;
                }

                boolean hasRecipe = false;
                for(ItemStack itemStack : slimefunItem.getRecipe()) {
                    if(ItemStackUtil.isItemNull(itemStack)) {
                        continue;
                    }
                    hasRecipe = true;
                    if(SlimefunItem.getByItem(itemStack) == null && !ItemStackUtil.isItemSimilar(itemStack, new ItemStack(itemStack.getType()))) {
                        this.NOT_ALLOWED_ID.add(slimefunItem.getId());
                        return false;
                    }
                }
                if(!hasRecipe) {
                    this.NOT_ALLOWED_ID.add(slimefunItem.getId());
                    return false;
                }

                this.ALLOWED_ID.add(slimefunItem.getId());
                return true;
            }
        }
    }
}
