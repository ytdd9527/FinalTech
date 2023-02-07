package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.AutoItemDismantleTableMenu;
import io.taraxacum.finaltech.core.menu.machine.ItemDismantleTableMenu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.RecipeTypeRegistry;
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
public class AutoItemDismantleTable extends AbstractMachine implements RecipeItem {
    private final Set<String> allowedRecipeType = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-recipe-type"));
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));

    public AutoItemDismantleTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new AutoItemDismantleTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (MachineUtil.isEmpty(blockMenu.toInventory(), this.getOutputSlot())) {
            ItemStack item = blockMenu.getItemInSlot(this.getInputSlot()[0]);
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null && !this.notAllowedId.contains(sfItem.getId()) && sfItem.getRecipeType().getMachine() != null && item.getAmount() >= sfItem.getRecipeOutput().getAmount() && sfItem.getRecipe().length <= this.getOutputSlot().length && this.allowedRecipeType.contains(sfItem.getRecipeType().getKey().getKey()) && ItemStackUtil.isEnchantmentSame(item, sfItem.getRecipeOutput()) && ItemStackUtil.isItemSimilar(item, sfItem.getRecipeOutput())) {
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
                        ItemStack liquidCard = RecipeUtil.getLiquidCard(outputItem);
                        if (liquidCard != null) {
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
        RecipeTypeRegistry.getInstance().reload();

        for (String id : this.allowedRecipeType) {
            RecipeType recipeType = RecipeTypeRegistry.getInstance().getRecipeTypeById(id);
            if (recipeType != null && !ItemStackUtil.isItemNull(recipeType.toItem())) {
                this.registerDescriptiveRecipe(recipeType.toItem());
            }
        }
    }
}
