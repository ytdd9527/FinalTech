package io.taraxacum.finaltech.core;

import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.taraxacum.finaltech.item.CopyCardItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author Final_ROOT
 */
public class CopyCardItemCraftingOperation extends CraftingOperation {
    private final ItemStack input;
    private final ItemStack output;
    private final int difficulty;
    private final boolean singularity;
    private int amount = 0;

    public CopyCardItemCraftingOperation(ItemStack item) {
        this(new MachineRecipe(0, new ItemStack[] {new ItemStack(item)}, new ItemStack[] {new ItemStack(item)}));
    }

    private CopyCardItemCraftingOperation(MachineRecipe machineRecipe) {
        super(machineRecipe);
        this.singularity = CopyCardItem.isCopyCardItem(machineRecipe.getInput()[0]);
        if(this.singularity) {
            this.input = new ItemStack(FinalTechItems.SINGULARITY);
            this.output = new ItemStack(FinalTechItems.SINGULARITY);
            this.difficulty = CopyCardItem.SINGULARITY_DIFFICULTY;
        } else {
            this.input = machineRecipe.getInput()[0];
            this.input.setAmount(1);
            this.output = new ItemStack(FinalTechItems.COPY_CARD);
            this.output.setAmount(1);
            StringItemUtil.setItemInCard(this.output, this.input, 1);
            ItemStackUtil.setLore(this.output, CopyCardItem.ITEM_LORE, "§7物品= " + ItemStackUtil.getItemName(this.input));
            this.difficulty = CopyCardItem.DIFFICULTY;
        }
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public int matchItem(ItemStack item) {
        if(this.isFinished()) {
            return 0;
        }
        if(this.singularity && CopyCardItem.isCopyCardItem(item)) {
            return Math.min(item.getAmount(), this.difficulty - this.amount);
        } else if (ItemStackUtil.isItemSimilar(item, this.input) || ItemStackUtil.isItemSimilar(item, FinalTechItems.SINGULARITY)) {
            return Math.min(item.getAmount(), this.difficulty - this.amount);
        }
        return 0;
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public boolean isSingularity() {
        return this.singularity;
    }

    @Override
    public boolean isFinished() {
        return (this.amount >= this.difficulty);
    }
}
