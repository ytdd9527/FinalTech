package io.taraxacum.finaltech.api.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.taraxacum.finaltech.items.unusable.CopyCardItem;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public interface ItemSerializationConstructorOperation extends MachineOperation {
    int COPY_CARD = 1;
    int ITEM_FAKE = 2;
    int ERROR_ITEM = -1;

    static int getType(@Nonnull ItemStack item) {
        if (CopyCardItem.isValid(item)) {
            return ITEM_FAKE;
        }
        if (ItemStackUtil.isItemSimilar(item, FinalTechItems.SINGULARITY) || ItemStackUtil.isItemSimilar(item, FinalTechItems.SPIROCHETE) || ItemStackUtil.isItemSimilar(item, FinalTechItems.PHONY)) {
            return ERROR_ITEM;
        }
        return COPY_CARD;
    }

    @Nullable
    static ItemSerializationConstructorOperation newInstance(@Nonnull ItemStack item) {
        int type = getType(item);
        if (type == COPY_CARD) {
            ItemStack itemStack = item.clone();
            item.setAmount(0);
            return new ItemCopyCardOperation(itemStack);
        } else if (type == ITEM_FAKE) {
            ItemStack itemStack = item.clone();
            item.setAmount(0);
            return new ItemFakeOperation(itemStack);
        }
        return null;
    }

    int getType();

    @Nonnull
    ItemStack getShowItem();

    void updateShowItem();

    int addItem(@Nullable ItemStack item);

    @Nonnull
    ItemStack getResult() ;
}
