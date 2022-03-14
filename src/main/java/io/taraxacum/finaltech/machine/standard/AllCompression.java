package io.taraxacum.finaltech.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.item.CopyCardItem;
import io.taraxacum.finaltech.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.CopyCardItemCraftingOperation;
import io.taraxacum.finaltech.menu.standard.AllCompressionMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class AllCompression extends AbstractStandardMachine {
    public static final CustomItemStack NULL_INFO_ICON = new CustomItemStack(Material.REDSTONE, "&f完成进度", "&7暂未输入物品");
    public static final String BLOCK_STORAGE_ITEM_KEY = "item";
    public static final String BLOCK_STORAGE_AMOUNT_KEY = "amount";

    public AllCompression(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.registerDefaultRecipes();
    }

    @Nonnull
    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new AllCompressionMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu inv = BlockStorage.getInventory(block);

        CopyCardItemCraftingOperation currentOperation = (CopyCardItemCraftingOperation)this.getMachineProcessor().getOperation(block);

        ItemStack stringItem;

        if(currentOperation == null && config.contains(BLOCK_STORAGE_ITEM_KEY)) {
            String itemString = config.getValue(BLOCK_STORAGE_ITEM_KEY).toString();
            stringItem = ItemStackUtil.stringToItemStack(itemString);
            if(!ItemStackUtil.isItemNull(stringItem) && !ItemStackUtil.isItemSimilar(stringItem, FinalTechItems.SINGULARITY)) {
                int amount =  Integer.parseInt(config.getString(BLOCK_STORAGE_AMOUNT_KEY));
                stringItem.setAmount(1);
                currentOperation = new CopyCardItemCraftingOperation(stringItem);
                currentOperation.setAmount(amount);
                this.getMachineProcessor().startOperation(block, currentOperation);
            }
        }

        for (int slot : this.getInputSlots()) {
            ItemStack inputItem = inv.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if(currentOperation == null) {
                if(isAllowedItem(inputItem)) {
                    stringItem = inputItem.clone();
                    stringItem.setAmount(1);
                    currentOperation = new CopyCardItemCraftingOperation(inputItem);
                    this.getMachineProcessor().startOperation(block, currentOperation);
                    int amount = currentOperation.matchItem(inputItem);
                    currentOperation.setAmount(amount);
                    inv.consumeItem(slot, amount);
                }
            } else {
                int amount = currentOperation.matchItem(inputItem);
                if(amount > 0) {
                    currentOperation.addAmount(amount);
                    inv.consumeItem(slot, amount);
                }
            }
        }

        if (currentOperation != null && currentOperation.isFinished() && InvUtils.fits(inv.toInventory(), currentOperation.getOutput(), this.getOutputSlots())) {
            inv.pushItem(currentOperation.getOutput(), this.getOutputSlots());
            this.getMachineProcessor().endOperation(block);
            currentOperation = null;
            BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_ITEM_KEY, null);
            BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_AMOUNT_KEY, null);
        }

        ItemStack progressItem;
        if (currentOperation != null) {
            progressItem = inv.getItemInSlot(AllCompressionMenu.PROGRESS_SLOT);
            if(ItemStackUtil.isItemSimilar(progressItem, Icon.BORDER_ICON) || ItemStackUtil.isItemSimilar(progressItem, NULL_INFO_ICON)) {
                progressItem = new CustomItemStack(currentOperation.getInput().getType(), "§7完成进度",
                        "§7物品名称= " + ItemStackUtil.getItemName(currentOperation.getInput()),
                        "§7完成进度= " + currentOperation.getAmount() + "§8 / §7" + currentOperation.getDifficulty());
            } else {
                ItemStackUtil.setLastLore(progressItem, "§7完成进度= " + currentOperation.getAmount() + "§8 / §7" + currentOperation.getDifficulty());
            }
            if(!currentOperation.isSingularity()) {
                BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_ITEM_KEY, ItemStackUtil.itemStackToString(currentOperation.getInput()));
                BlockStorage.addBlockInfo(block.getLocation(), BLOCK_STORAGE_AMOUNT_KEY, String.valueOf(currentOperation.getAmount()));
            }
        } else {
            progressItem = NULL_INFO_ICON;
        }
        inv.replaceExistingItem(AllCompressionMenu.PROGRESS_SLOT, progressItem);
    }

    private boolean isAllowedItem(@Nullable ItemStack item) {
        if(ItemStackUtil.isItemSimilar(item, FinalTechItems.SINGULARITY)) {
            return false;
        }
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe("&f制造复制卡",
                "",
                "&f输入" + CopyCardItem.DIFFICULTY + "个相同物品",
                "&f输出1个该物品的复制卡",
                "&f无法生产 奇点 的复制卡");

        this.registerDescriptiveRecipe("&f制造奇点",
                "",
                "&7输入任意" + CopyCardItem.SINGULARITY_DIFFICULTY + "个复制卡",
                "&7输出1个奇点",
                "&f允许使用多种物品的复制卡");
    }
}
