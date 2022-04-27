package io.taraxacum.finaltech.dto;

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class ItemStackWithWrapper {
    public static ItemStackWithWrapper NULL_ITEM = new ItemStackWithWrapper(new ItemStack(Material.AIR));

    private ItemStack itemStack;
    private ItemStackWrapper itemStackWrapper;
    private int amount;

    public ItemStackWithWrapper (@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemStackWrapper = ItemStackWrapper.wrap(itemStack);
        this.amount = itemStack.getAmount();
    }

    public ItemStackWithWrapper (@Nonnull ItemStack itemStack, @Nonnull ItemStackWrapper itemStackWrapper) {
        this.itemStack = itemStack;
        this.itemStackWrapper = itemStackWrapper;
        this.amount = itemStack.getAmount();
    }

    public ItemStackWithWrapper (@Nonnull ItemStack itemStack, int amount) {
        this.itemStack = itemStack;
        this.itemStackWrapper = ItemStackWrapper.wrap(itemStack);
        this.amount = amount;
    }

    public ItemStackWithWrapper (@Nonnull ItemStack itemStack, @Nonnull ItemStackWrapper itemStackWrapper, int amount) {
        this.itemStack = itemStack;
        this.itemStackWrapper = itemStackWrapper;
        this.amount = amount;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemStackWrapper = ItemStackWrapper.wrap(itemStack);
    }

    public ItemStackWrapper getItemStackWrapper() {
        return itemStackWrapper;
    }

    public void updateItemStackWrapper() {
        this.itemStackWrapper = ItemStackWrapper.wrap(this.itemStack);
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

    public static List<ItemStack> toItemList(List<ItemStackWithWrapper> list) {
        ArrayList<ItemStack> result = new ArrayList<>(list.size());
        for (ItemStackWithWrapper itemStackWithWrapper : list) {
            result.add(itemStackWithWrapper.getItemStack());
        }
        return result;
    }

    public static ItemStack[] toItemArray(List<ItemStackWithWrapper> list) {
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
