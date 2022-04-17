package io.taraxacum.finaltech.api.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.machine.standard.OrderedDustFactory;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class OrderedDustOperation implements MachineOperation {
    private int typeCount = 0;
    private int amountCount = 0;

    private ItemStackWrapper[] matchItemList = new ItemStackWrapper[OrderedDustFactory.TYPE_DIFFICULTY + 1];

    public OrderedDustOperation() {
    }

    public void addItem(ItemStack item) {
        if(ItemStackUtil.isItemNull(item)) {
            return;
        }
        if(this.typeCount <= OrderedDustFactory.TYPE_DIFFICULTY) {
            boolean newItem = true;
            for(int i = 0; i < this.typeCount; i++) {
                ItemStack input = this.matchItemList[i];
                if(ItemStackUtil.isItemSimilar(item, input)) {
                    newItem = false;
                    break;
                }
            }
            if(newItem == true) {
                matchItemList[typeCount++] = ItemStackWrapper.wrap(item);
            }
        }
        this.amountCount += item.getAmount();
        if(this.amountCount > OrderedDustFactory.AMOUNT_DIFFICULTY + 1) {
            this.amountCount = OrderedDustFactory.AMOUNT_DIFFICULTY + 1;
        }
    }

    public int getTypeCount() {
        return this.typeCount;
    }

    public int getAmountCount() {
        return this.amountCount;
    }

    @Override
    public boolean isFinished() {
        return (this.amountCount >= OrderedDustFactory.AMOUNT_DIFFICULTY && this.typeCount >= OrderedDustFactory.TYPE_DIFFICULTY);
    }

    public boolean isOrderedDust() {
        return this.amountCount == OrderedDustFactory.AMOUNT_DIFFICULTY && this.typeCount == OrderedDustFactory.TYPE_DIFFICULTY;
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
