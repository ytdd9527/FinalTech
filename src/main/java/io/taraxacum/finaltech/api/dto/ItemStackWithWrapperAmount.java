package io.taraxacum.finaltech.api.dto;

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemStackWithWrapperAmount extends ItemStackWithWrapper {
    private int amount;

    public ItemStackWithWrapperAmount(@Nonnull ItemStack itemStack) {
        super(itemStack);
        this.amount = itemStack.getAmount();
    }

    public ItemStackWithWrapperAmount(@Nonnull ItemStack itemStack, @Nonnull ItemStackWrapper itemStackWrapper) {
        super(itemStack, itemStackWrapper);
        this.amount = itemStack.getAmount();
    }

    public ItemStackWithWrapperAmount(@Nonnull ItemStack itemStack, int amount) {
        super(itemStack);
        this.amount = amount;
    }

    public ItemStackWithWrapperAmount(@Nonnull ItemStack itemStack, @Nonnull ItemStackWrapper itemStackWrapper, int amount) {
        super(itemStack, itemStackWrapper);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void subAmount(int amount) {
        this.amount -= amount;
    }

    public void addAmount() {
        this.amount++;
    }

    public void subAmount() {
        this.amount--;
    }

    public static List<ItemStackWithWrapperAmount> addToList(@Nonnull List<ItemStackWithWrapperAmount> list, @Nonnull ItemStackWithWrapperAmount item) {
        for (ItemStackWithWrapperAmount existedItem : list) {
            if (ItemStackUtil.isItemSimilar(existedItem, item)) {
                existedItem.addAmount(item.getAmount());
                return list;
            }
        }
        list.add(new ItemStackWithWrapperAmount(item.getItemStack(), item.getItemStackWrapper(), item.getAmount()));
        return list;
    }

    public static List<ItemStackWithWrapperAmount> addToList(@Nonnull List<ItemStackWithWrapperAmount> list, @Nonnull ItemStackWithWrapperAmount item, int mul) {
        for (ItemStackWithWrapperAmount existedItem : list) {
            if (ItemStackUtil.isItemSimilar(existedItem, item)) {
                existedItem.addAmount(item.getAmount() * mul);
                return list;
            }
        }
        list.add(new ItemStackWithWrapperAmount(item.getItemStack(), item.getItemStackWrapper(), item.getAmount() * mul));
        return list;
    }
}
