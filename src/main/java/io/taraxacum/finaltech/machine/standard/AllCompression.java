package io.taraxacum.finaltech.machine.standard;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.menu.standard.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.AllCompressionCraftingOperation;
import io.taraxacum.finaltech.menu.standard.AllCompressionMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class AllCompression extends AbstractStandardMachine {
    public static final int DIFFICULTY = 16777216;
    public static final int SINGULARITY_DIFFICULTY = 256;
    public static final CustomItemStack NULL_INFO_ICON = new CustomItemStack(Material.REDSTONE, "&f完成进度", "&7暂未输入物品");
    public static final String BLOCK_STORAGE_ITEM_KEY = "item";
    public static final String BLOCK_STORAGE_COUNT_KEY = "count";

    public AllCompression(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new AllCompressionMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu inv = BlockStorage.getInventory(block);
        AllCompressionCraftingOperation currentOperation = (AllCompressionCraftingOperation)this.getMachineProcessor().getOperation(block);
        // todo
//        if(currentOperation == null) {
//            String itemString = config.getString(BLOCK_STORAGE_ITEM_KEY);
//            if(itemString != null && !"".equals(itemString)) {
//                ItemStack item = ItemStackUtil.stringToItemStack(itemString);
//                int count =  Integer.parseInt(config.getString(BLOCK_STORAGE_COUNT_KEY));
//                item.setAmount(1);
//                this.getMachineProcessor().startOperation(block, new AllCompressionCraftingOperation(item));
//            }
//        }

        for (int slot : this.getInputSlots()) {
            ItemStack inputItem = inv.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if(currentOperation == null) {
                if(unAllowedItem(inputItem)) {
                    continue;
                }
                this.getMachineProcessor().startOperation(block, new AllCompressionCraftingOperation(inputItem));
                currentOperation = (AllCompressionCraftingOperation)this.getMachineProcessor().getOperation(block);
            } else {
                currentOperation.addItem(inputItem);
            }
        }
        if (currentOperation != null && currentOperation.isFinished() && InvUtils.fits(inv.toInventory(), currentOperation.getOutput(), this.getOutputSlots())) {
            inv.pushItem(currentOperation.getOutput(), this.getOutputSlots());
            this.getMachineProcessor().endOperation(block);
        }

        ItemStack progress;
        if (currentOperation != null) {
            progress = inv.getItemInSlot(AllCompressionMenu.PROGRESS_SLOT);
            if(ItemStackUtil.isItemSimilar(progress, Icon.BORDER_ICON) || ItemStackUtil.isItemSimilar(progress, NULL_INFO_ICON)) {
                ItemStack item = currentOperation.getInput();
                String name = "";
                if(item.hasItemMeta()) {
                    ItemMeta itemMeta = item.getItemMeta();
                    name = itemMeta.hasLocalizedName() ? itemMeta.getLocalizedName() : itemMeta.getDisplayName();
                }
                progress = new CustomItemStack(currentOperation.getInput().getType(), "§7完成进度",
                        "§7物品名称=" + name,
                        "§7完成进度=" + currentOperation.getCount() + "/" + currentOperation.getDifficulty());
            } else {
                ItemStackUtil.setLastLore(progress, "§7完成进度=" + currentOperation.getCount() + "/" + currentOperation.getDifficulty());
            }
        } else {
            progress = NULL_INFO_ICON;
        }
        inv.replaceExistingItem(AllCompressionMenu.PROGRESS_SLOT, progress);
    }

    private static boolean unAllowedItem(ItemStack item) {
        switch (item.getType()) {
            case SHULKER_BOX:
            case BLACK_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case LIGHT_GRAY_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
            case BUNDLE:
                return true;
        }
        return ItemStackUtil.isItemSimilar(item, FinalTechItems.SINGULARITY);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerDescriptiveRecipe(new CustomItemStack(Material.BOOK, "&d生产带签名的物品",
                "",
                "&7输入" + DIFFICULTY + "个相同物品",
                "&7输出1个带有签名的该物品",
                "&d无法输入以下物品->潜影盒 收纳袋 奇点"));

        this.registerDescriptiveRecipe(new CustomItemStack(Material.BOOK, "&d生产奇点",
                "",
                "&7输入任意" + SINGULARITY_DIFFICULTY + "个带有签名的物品",
                "&7输出1个奇点",
                "&d允许使用多种带有签名的物品"));
    }
}
