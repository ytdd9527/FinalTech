package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractStandardMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractStandardMachineMenu;
import io.taraxacum.finaltech.core.AllCompressionCraftingOperation;
import io.taraxacum.finaltech.menu.AllCompressionMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.cargo.Icon;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

// todo
/**
 * @author Final_ROOT
 */
public class AllCompression extends AbstractStandardMachine {
    public static final int DIFFICULTY = 16777216;
    public static final int SINGULARITY_DIFFICULTY = 256;
    public static final CustomItemStack NULL_INFO_ICON = new CustomItemStack(Material.REDSTONE, "&f完成进度", "&7暂未输入物品");


    private ItemStack item = null;
    private int input = 0;

    public AllCompression(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected AbstractStandardMachineMenu setMachineMenu() {
        return new AllCompressionMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        AllCompressionCraftingOperation currentOperation = (AllCompressionCraftingOperation)this.getMachineProcessor().getOperation(b);

        for (int slot : this.getInputSlots()) {
            ItemStack inputItem = inv.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if(currentOperation == null) {
                if(unAllowedItem(inputItem)) {
                    continue;
                }
                this.getMachineProcessor().startOperation(b, new AllCompressionCraftingOperation(inputItem));
                currentOperation = (AllCompressionCraftingOperation)this.getMachineProcessor().getOperation(b);
            } else {
                currentOperation.addItem(inputItem);
            }
        }
        if (currentOperation != null && currentOperation.isFinished() && InvUtils.fits(inv.toInventory(), currentOperation.getOutput(), this.getOutputSlots())) {
            inv.pushItem(currentOperation.getOutput(), this.getOutputSlots());
            this.getMachineProcessor().endOperation(b);
        }

        ItemStack progress;
        if (currentOperation != null) {
            progress = inv.getItemInSlot(AllCompressionMenu.PROGRESS_SLOT);
            if(SlimefunUtils.isItemSimilar(progress, Icon.BORDER_ICON, true, false) || SlimefunUtils.isItemSimilar(progress, NULL_INFO_ICON, true, false)) {
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
        return SlimefunUtils.isItemSimilar(item, FinalTechItems.SINGULARITY, true, false);
    }

    @Override
    public void registerDefaultRecipes() { }
}
