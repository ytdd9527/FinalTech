package io.taraxacum.finaltech.api.dto;

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ItemWrapper {
    @Nonnull
    private ItemStack itemStack;

    @Nullable
    private ItemMeta itemMeta;

    private boolean hasItemMeta;

    public ItemWrapper(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.hasItemMeta = itemStack.hasItemMeta();
        this.itemMeta = this.hasItemMeta ? this.itemStack.getItemMeta() : null;
    }

    public ItemWrapper(@Nonnull ItemStack itemStack, @Nullable ItemMeta itemMeta) {
        this.itemStack = itemStack;
        this.itemMeta = itemMeta;
        this.hasItemMeta = itemMeta == null;
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void replaceItemStack(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.hasItemMeta = this.itemStack.hasItemMeta();
        this.itemMeta = this.hasItemMeta ? this.itemStack.getItemMeta() : null;
    }

    @Nullable
    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public void setItemMeta(@Nullable ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        this.hasItemMeta = this.itemMeta == null;
    }

    public boolean isHasItemMeta() {
        return hasItemMeta;
    }

    public void updateItemMeta() {
        this.hasItemMeta = this.itemStack.hasItemMeta();
        this.itemMeta = this.hasItemMeta ? this.itemStack.getItemMeta() : null;
    }

    @Override
    public int hashCode() {
        int hash = 31 + this.itemStack.getType().hashCode();
        hash = hash * 31 + this.itemStack.getAmount();
        hash = hash * 31 + (this.itemStack.getDurability() & 0xffff);
        hash = hash * 31 + (this.hasItemMeta ? (this.itemMeta.hashCode()) : 0);
        return hash;
    }

    @Override
    public boolean equals(@Nonnull Object obj) {
        if (this.itemStack instanceof ItemStackWrapper) {
            return new ItemStack(this.itemStack).equals(obj);
        } else {
            return this.itemStack.equals(obj);
        }
    }
}
