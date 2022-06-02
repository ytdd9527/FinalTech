package io.taraxacum.finaltech.api.dto;

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemStackWithWrapper {
    @Nonnull
    private ItemStack itemStack;
    @Nonnull
    private ItemStackWrapper itemStackWrapper;

    public ItemStackWithWrapper(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemStackWrapper = ItemStackWrapper.wrap(itemStack);
    }

    public ItemStackWithWrapper(@Nonnull ItemStack itemStack, @Nonnull ItemStackWrapper itemStackWrapper) {
        this.itemStack = itemStack;
        this.itemStackWrapper = itemStackWrapper;
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Nonnull
    public ItemStackWrapper getItemStackWrapper() {
        return itemStackWrapper;
    }

    public void warpItem(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemStackWrapper = ItemStackWrapper.wrap(itemStack);
    }

    public void updateItemStackWrapper() {
        this.itemStackWrapper = ItemStackWrapper.wrap(this.itemStack);
    }

    @Nonnull
    public static List<ItemStack> toItemList(@Nonnull List<? extends ItemStackWithWrapper> list) {
        ArrayList<ItemStack> result = new ArrayList<>(list.size());
        for (ItemStackWithWrapper itemStackWithWrapper : list) {
            result.add(itemStackWithWrapper.getItemStack());
        }
        return result;
    }

    @Nonnull
    public static ItemStack[] toItemArray(@Nonnull List<? extends ItemStackWithWrapper> list) {
        ItemStack[] result = new ItemStack[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i).getItemStack();
        }
        return result;
    }

    @Nonnull
    public static List<ItemStackWrapper> toItemWrapperList(@Nonnull List<? extends ItemStackWithWrapper> list) {
        ArrayList<ItemStackWrapper> result = new ArrayList<>(list.size());
        for (ItemStackWithWrapper itemStackWithWrapper : list) {
            result.add(itemStackWithWrapper.getItemStackWrapper());
        }
        return result;
    }

    @Override
    public int hashCode() {
        if (this.itemStack instanceof ItemStackWrapper) {
            return new ItemStack(this.itemStack).hashCode();
        } else {
            return this.itemStack.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.itemStack instanceof ItemStackWrapper) {
            return new ItemStack(this.itemStack).equals(obj);
        } else {
            return this.itemStack.equals(obj);
        }
    }
}
