package io.taraxacum.finaltech.core.items.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.operation.ItemSerializationConstructorOperation;
import io.taraxacum.finaltech.core.operation.ItemCopyCardOperation;
import io.taraxacum.finaltech.core.menu.machine.ItemSerializationConstructorMenu;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.ConstantTableUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
/**
 * This is a slimefun machine
 * it will be used in gameplay
 * It's not a function class!
 * @author Final_ROOT
 * @since 1.0
 */
public class ItemSerializationConstructor extends AbstractOperationMachine {
    private final CustomItemStack nullInfoIcon = new CustomItemStack(Material.REDSTONE, ConfigUtil.getStatusMenuName(FinalTech.getLanguageManager(), this, "null-info"), ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this, "null-info"));
    private final String blockStorageItemKey = "item";
    private final String blockStorageAmountKey = "amount";

    public ItemSerializationConstructor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ItemSerializationConstructorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        ItemSerializationConstructorOperation operation = (ItemSerializationConstructorOperation) this.getMachineProcessor().getOperation(block);

        if (operation == null && config.contains(this.blockStorageItemKey)) {
            String itemString = config.getString(this.blockStorageItemKey);
            ItemStack stringItem = ItemStackUtil.stringToItemStack(itemString);
            if (!ItemStackUtil.isItemNull(stringItem) && ItemSerializationConstructorOperation.getType(stringItem) == ItemSerializationConstructorOperation.COPY_CARD) {
                operation = ItemSerializationConstructorOperation.newInstance(stringItem);
                if (operation != null) {
                    this.getMachineProcessor().startOperation(block, operation);
                    int amount = (int) Double.parseDouble(config.getString(blockStorageAmountKey));
                    ((ItemCopyCardOperation)operation).setCount(amount);
                }
            }
        }

        for (int slot : this.getInputSlot()) {
            ItemStack inputItem = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if (operation == null) {
                if(SlimefunItem.getByItem(inputItem) == null) {
                    break;
                }
                operation = ItemSerializationConstructorOperation.newInstance(inputItem);
                if (operation != null) {
                    this.getMachineProcessor().startOperation(block, operation);
                }
            } else {
                operation.addItem(inputItem);
            }
        }

        if (operation != null && operation.isFinished() && InvUtils.fits(blockMenu.toInventory(), operation.getResult(), this.getOutputSlot())) {
            blockMenu.pushItem(operation.getResult(), this.getOutputSlot());
            this.getMachineProcessor().endOperation(block);
            operation = null;
            BlockStorage.addBlockInfo(block.getLocation(), this.blockStorageItemKey, null);
            BlockStorage.addBlockInfo(block.getLocation(), this.blockStorageAmountKey, null);
        }

        if(operation != null && operation.getType() == ItemSerializationConstructorOperation.COPY_CARD) {
            BlockStorage.addBlockInfo(block.getLocation(), this.blockStorageItemKey, ItemStackUtil.itemStackToString(((ItemCopyCardOperation)operation).getMatchItem()));
            BlockStorage.addBlockInfo(block.getLocation(), this.blockStorageAmountKey, String.valueOf((int)((ItemCopyCardOperation)operation).getCount()));
        }

        if(blockMenu.hasViewer()) {
            ItemStack showItem;
            if (operation != null) {
                operation.updateShowItem();
                showItem = operation.getShowItem();
            } else {
                showItem = this.nullInfoIcon;
            }
            blockMenu.replaceExistingItem(ItemSerializationConstructorMenu.STATUS_SLOT, showItem);
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT),
                String.valueOf(ItemCopyCardOperation.RATE));
    }
}
