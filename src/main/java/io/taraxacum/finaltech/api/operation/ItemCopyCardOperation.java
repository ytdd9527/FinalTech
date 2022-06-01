package io.taraxacum.finaltech.api.operation;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.TextUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class ItemCopyCardOperation implements ItemSerializationConstructorOperation {
    private double count;
    private final int difficulty;

    private final ItemStack matchItem;
    private final ItemStack copyCardItem;

    private final ItemStack showItem;

    protected ItemCopyCardOperation(ItemStack item) {
        this.matchItem = item.clone();
        this.matchItem.setAmount(1);
        this.copyCardItem = CopyCardItem.newItem(this.matchItem, "1");
        this.count = item.getAmount();
        this.difficulty = CopyCardItem.DIFFICULTY;
        this.showItem = new CustomItemStack(item.getType(), TextUtil.COLOR_NORMAL + "完成进度", TextUtil.COLOR_NORMAL + "物品名称= &f" + ItemStackUtil.getItemName(item), TextUtil.COLOR_NORMAL + "压缩数量= " + TextUtil.COLOR_NUMBER + this.count + "/" + this.difficulty);
    }

    public double getCount() {
        return this.count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public ItemStack getMatchItem() {
        return this.matchItem;
    }

    @Override
    public int getType() {
        return ItemSerializationConstructorOperation.COPY_CARD;
    }

    @Nonnull
    @Override
    public ItemStack getShowItem() {
        return this.showItem;
    }

    @Override
    public void updateShowItem() {
        ItemStackUtil.setLastLore(this.showItem, TextUtil.COLOR_NORMAL + "压缩数量= " + TextUtil.COLOR_NUMBER + this.count + "/" + this.difficulty);
    }

    @Override
    public int addItem(@Nullable ItemStack item) {
        if (!this.isFinished()) {
            if (ItemStackUtil.isItemSimilar(item, this.matchItem)) {
                double efficiency = Math.pow(0.5, 20.0 - 20.0 * 1000 / FinalTech.getMSPS());
                if(item.getAmount() * efficiency + this.count < this.difficulty) {
                    int amount = item.getAmount();
                    item.setAmount(item.getAmount() - amount);
                    this.count += amount * efficiency;
                    return amount;
                } else {
                    double amount = (this.difficulty - this.count) / efficiency;
                    item.setAmount(item.getAmount() - (int) Math.ceil(amount));
                    this.count = this.difficulty;
                    return (int) Math.ceil(amount);
                }
            } else if(ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY)) {
                double amount = Math.min(item.getAmount(), this.difficulty - this.count);
                item.setAmount(item.getAmount() - (int) Math.ceil(amount));
                this.count += amount;
                return (int) Math.ceil(amount);
            }
        }
        return 0;
    }

    @Override
    public boolean isFinished() {
        return this.count >= this.difficulty;
    }

    @Nonnull
    @Override
    public ItemStack getResult() {
        return this.copyCardItem;
    }

    @Deprecated
    @Override
    public void addProgress(int i) {

    }

    @Deprecated
    @Override
    public int getProgress() {
        return 0;
    }

    @Deprecated
    @Override
    public int getTotalTicks() {
        return 0;
    }
}
