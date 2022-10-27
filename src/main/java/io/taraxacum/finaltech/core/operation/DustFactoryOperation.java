package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.dto.ItemWrapper;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustFactoryOperation implements MachineOperation {
    private int typeCount = 0;
    private int amountCount = 0;
    private final ItemWrapper[] matchItemList = new ItemWrapper[DustFactoryOperation.TYPE_DIFFICULTY + 1];
    public static final int TYPE_DIFFICULTY = ConfigUtil.getOrDefaultItemSetting(16 + FinalTech.getRandom().nextInt(5), FinalTechItems.ORDERED_DUST_FACTORY_DIRT.getItemId(), "type");
    public static final int AMOUNT_DIFFICULTY = ConfigUtil.getOrDefaultItemSetting(1024 + DustFactoryOperation.calAmountRandomNumber(), FinalTechItems.ORDERED_DUST_FACTORY_DIRT.getItemId(), "amount");

    public DustFactoryOperation() {

    }

    public void addItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return;
        }
        if (this.typeCount <= DustFactoryOperation.TYPE_DIFFICULTY) {
            boolean newItem = true;
            for (int i = 0; i < this.typeCount; i++) {
                ItemWrapper existedItem = this.matchItemList[i];
                if (ItemStackUtil.isItemSimilar(item, existedItem)) {
                    newItem = false;
                    break;
                }
            }
            if (newItem) {
                this.matchItemList[this.typeCount++] = new ItemWrapper(item);
            }
        }
        this.amountCount += item.getAmount();
        if (this.amountCount > DustFactoryOperation.AMOUNT_DIFFICULTY + 1) {
            this.amountCount = DustFactoryOperation.AMOUNT_DIFFICULTY + 1;
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
        return (this.amountCount >= DustFactoryOperation.AMOUNT_DIFFICULTY && this.typeCount >= DustFactoryOperation.TYPE_DIFFICULTY);
    }

    @Nullable
    public ItemStack getResult() {
        if (this.amountCount == DustFactoryOperation.AMOUNT_DIFFICULTY && this.typeCount == DustFactoryOperation.TYPE_DIFFICULTY) {
            return ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST);
        } else if (this.isFinished()) {
            return ItemStackUtil.cloneItem(FinalTechItems.UNORDERED_DUST);
        } else {
            return null;
        }
    }

    private static int calAmountRandomNumber() {
        int result = FinalTech.getRandom().nextInt(257);
        while (result % 16 == 0 || (1024 + result) % DustFactoryOperation.TYPE_DIFFICULTY == 0) {
            result = FinalTech.getRandom().nextInt(257);
        }
        return result;
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
