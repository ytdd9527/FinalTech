package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.item.CopyCardItem;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class ItemCopyCardOperation implements AllCompressionOperation {
    private int count = 0;
    private final int difficulty;

    private final ItemStack matchItem;
    private final ItemStack copyCardItem;

    private final ItemStack showItem;

    protected ItemCopyCardOperation(ItemStack item) {
        this.matchItem = item.clone();
        this.matchItem.setAmount(1);
        this.copyCardItem = CopyCardItem.newCopyCardItem(this.matchItem, "1");
        this.count = item.getAmount();
        this.difficulty = CopyCardItem.COPY_CARD_DIFFICULTY;
        this.showItem = new CustomItemStack(item.getType(), "§f完成进度", "§f物品名称= " + ItemStackUtil.getItemName(item), "§f压缩数量= " + this.count + "/" + this.difficulty);
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ItemStack getMatchItem() {
        return this.matchItem;
    }

    @Override
    public int getType() {
        return AllCompressionOperation.COPY_CARD;
    }

    @Override
    public ItemStack getShowItem() {
        return this.showItem;
    }

    @Override
    public void updateShowItem() {
        ItemStackUtil.setLastLore(this.showItem, "§f压缩数量= " + this.count + "/" + this.difficulty);
    }

    @Override
    public int addItem(@Nullable ItemStack item) {
        if(!this.isFinished()) {
            if (ItemStackUtil.isItemSimilar(item, this.matchItem) || ItemStackUtil.isItemSimilar(item, FinalTechItems.FAKE)) {
                int amount = Math.min(item.getAmount(), this.difficulty - this.count);
                item.setAmount(item.getAmount() - amount);
                this.count += amount;
                return amount;
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
