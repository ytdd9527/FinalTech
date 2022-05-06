package io.taraxacum.finaltech.dto;

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemStackWithWrapper {
    private ItemStack itemStack;
    private ItemStackWrapper itemStackWrapper;

    public ItemStackWithWrapper(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemStackWrapper = ItemStackWrapper.wrap(itemStack);
    }

    public ItemStackWithWrapper(@Nonnull ItemStack itemStack, @Nonnull ItemStackWrapper itemStackWrapper) {
        this.itemStack = itemStack;
        this.itemStackWrapper = itemStackWrapper;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemStackWrapper getItemStackWrapper() {
        return itemStackWrapper;
    }

    public void warpItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemStackWrapper = ItemStackWrapper.wrap(itemStack);
    }

    public void updateItemStackWrapper() {
        this.itemStackWrapper = ItemStackWrapper.wrap(this.itemStack);
    }

    public static List<ItemStack> toItemList(List<ItemStackWithWrapper> list) {
        ArrayList<ItemStack> result = new ArrayList<>(list.size());
        for (ItemStackWithWrapper itemStackWithWrapper : list) {
            result.add(itemStackWithWrapper.getItemStack());
        }
        return result;
    }

    public static ItemStack[] toItemArray(List<? extends ItemStackWithWrapper> list) {
        ItemStack[] result = new ItemStack[list.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = list.get(i).getItemStack();
        }
        return result;
    }

    public static List<ItemStackWrapper> toItemWrapperList(List<ItemStackWithWrapper> list) {
        ArrayList<ItemStackWrapper> result = new ArrayList<>(list.size());
        for (ItemStackWithWrapper itemStackWithWrapper : list) {
            result.add(itemStackWithWrapper.getItemStackWrapper());
        }
        return result;
    }

    /**
     * Try not use this in code
     * @return
     */
    @Override
    public int hashCode() {
        if (this.itemStack instanceof ItemStackWrapper) {
            return new ItemStack(this.itemStack).hashCode();
        } else {
            return this.itemStack.hashCode();
        }
    }


    /**
     * Try not use this in code
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this.itemStack instanceof ItemStackWrapper) {
            return new ItemStack(this.itemStack).equals(obj);
        } else {
            return this.itemStack.equals(obj);
        }
    }
}
