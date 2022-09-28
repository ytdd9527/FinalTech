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
import io.taraxacum.finaltech.core.menu.machine.ItemDismantleTableMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.SfItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemDismantleTable extends AbstractMachine implements RecipeItem {
    private final Set<String> ALLOWED_RECIPE_ID = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-recipe-id"));

//        RECIPE_TYPE_LIST.add(RecipeType.ENHANCED_CRAFTING_TABLE);
//        RECIPE_TYPE_LIST.add(RecipeType.GRIND_STONE);
//        RECIPE_TYPE_LIST.add(RecipeType.ARMOR_FORGE);
//        RECIPE_TYPE_LIST.add(RecipeType.ORE_CRUSHER);
//        RECIPE_TYPE_LIST.add(RecipeType.COMPRESSOR);
//        RECIPE_TYPE_LIST.add(RecipeType.SMELTERY);
//        RECIPE_TYPE_LIST.add(RecipeType.PRESSURE_CHAMBER);
//        RECIPE_TYPE_LIST.add(RecipeType.MAGIC_WORKBENCH);
////        RECIPE_TYPE_LIST.add(RecipeType.ORE_WASHER);
//        RECIPE_TYPE_LIST.add(RecipeType.GOLD_PAN);
//        RECIPE_TYPE_LIST.add(RecipeType.JUICER);
//        RECIPE_TYPE_LIST.add(RecipeType.ANCIENT_ALTAR);
//        RECIPE_TYPE_LIST.add(RecipeType.HEATED_PRESSURE_CHAMBER);
//        RECIPE_TYPE_LIST.add(RecipeType.FOOD_COMPOSTER);
//        RECIPE_TYPE_LIST.add(RecipeType.FOOD_FABRICATOR);
//        RECIPE_TYPE_LIST.add(RecipeType.FREEZER);

    public ItemDismantleTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new ItemDismantleTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (MachineUtil.isEmpty(blockMenu.toInventory(), this.getOutputSlot())) {
            ItemStack item = blockMenu.getItemInSlot(this.getInputSlot()[0]);
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null && item.getAmount() >= sfItem.getRecipeOutput().getAmount() && sfItem.getRecipe().length <= this.getOutputSlot().length && ALLOWED_RECIPE_ID.contains(sfItem.getRecipeType().getMachine().getId()) && ItemStackUtil.isEnchantmentSame(item, sfItem.getRecipeOutput()) && ItemStackUtil.isItemSimilar(item, sfItem.getRecipeOutput())) {
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
        for (String id : ALLOWED_RECIPE_ID) {
            SlimefunItem slimefunItem = SlimefunItem.getById(id);
            if(slimefunItem != null) {
                this.registerDescriptiveRecipe(slimefunItem.getItem());
            }
        }
    }
}
