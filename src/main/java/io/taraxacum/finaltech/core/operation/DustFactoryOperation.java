package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.core.items.machine.standard.DustFactoryDirt;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustFactoryOperation implements MachineOperation {
    private int typeCount = 0;
    private int amountCount = 0;
    private final ItemStackWrapper[] matchItemList = new ItemStackWrapper[DustFactoryDirt.TYPE_DIFFICULTY + 1];

    public DustFactoryOperation() {

    }

    public void addItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return;
        }
        if (this.typeCount <= DustFactoryDirt.TYPE_DIFFICULTY) {
            boolean newItem = true;
            for (int i = 0; i < this.typeCount; i++) {
                ItemStack existedItem = this.matchItemList[i];
                if (ItemStackUtil.isItemSimilar(item, existedItem)) {
                    newItem = false;
                    break;
                }
            }
            if (newItem == true) {
                this.matchItemList[this.typeCount++] = ItemStackWrapper.wrap(item);
            }
        }
        this.amountCount += item.getAmount();
        if (this.amountCount > DustFactoryDirt.AMOUNT_DIFFICULTY + 1) {
            this.amountCount = DustFactoryDirt.AMOUNT_DIFFICULTY + 1;
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
        return (this.amountCount >= DustFactoryDirt.AMOUNT_DIFFICULTY && this.typeCount >= DustFactoryDirt.TYPE_DIFFICULTY);
    }

    public boolean isOrderedDust() {
        return this.amountCount == DustFactoryDirt.AMOUNT_DIFFICULTY && this.typeCount == DustFactoryDirt.TYPE_DIFFICULTY;
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
