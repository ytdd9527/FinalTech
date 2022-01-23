package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.abstractItem.FinalBasicMachine;
import io.taraxacum.finaltech.abstractItem.FinalMachine;
import io.taraxacum.finaltech.abstractItem.MachineMenu;
import io.taraxacum.finaltech.core.AllCompressionCraftingOperation;
import io.taraxacum.finaltech.menu.AllCompressionMenu;
import io.taraxacum.finaltech.menu.BasicMachineMenu;
import io.taraxacum.finaltech.util.FinalUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AllCompression extends FinalMachine {
    public static final int DIFFICULTY = 512;
    private ItemStack item = null;
    private int input = 0;

    public AllCompression(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected MachineMenu setMachineMenu() {
        return new AllCompressionMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        AllCompressionCraftingOperation currentOperation = (AllCompressionCraftingOperation)this.getMachineProcessor().getOperation(b);

        for (int slot : this.getInputSlots()) {
            ItemStack inputItem = inv.getItemInSlot(slot);
            if(inputItem == null) {
                continue;
            }
            if(currentOperation == null) {
                this.getMachineProcessor().startOperation(b, new AllCompressionCraftingOperation(inputItem));
                currentOperation = (AllCompressionCraftingOperation)this.getMachineProcessor().getOperation(b);
                inv.consumeItem(slot, inputItem.getAmount());
            } else if(currentOperation.addItem(inputItem)) {
                inv.consumeItem(slot, inputItem.getAmount());
            }
        }
        if (currentOperation != null && currentOperation.isFinished()) {
            inv.pushItem(currentOperation.getOutput(), this.getOutputSlots());
            this.getMachineProcessor().endOperation(b);
        }

        CustomItemStack progress;
        if (currentOperation != null) {
            progress = new CustomItemStack(currentOperation.getInput(), "&f完成进度",
                    "&7物品名称：" + (currentOperation.getInput().getItemMeta().hasDisplayName() ? currentOperation.getInput().getItemMeta().getDisplayName() : currentOperation.getInput().getItemMeta().getLocalizedName()),
                    "&7输入的物品数量：" + currentOperation.getInputCount() + "/" + DIFFICULTY);
        } else {
            progress = new CustomItemStack(Material.REDSTONE, "&f完成进度",
                    "&7暂未输入物品");
        }
        inv.replaceExistingItem(AllCompressionMenu.PROGRESS_SLOT, progress);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "FINALTECH_ALL_COMPRESSION";
    }
}
