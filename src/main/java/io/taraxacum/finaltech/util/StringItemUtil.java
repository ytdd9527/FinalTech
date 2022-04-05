package io.taraxacum.finaltech.util;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.item.StorageCardItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class StringItemUtil {
    public static final NamespacedKey ITEM_KEY = new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "item");
    public static final NamespacedKey AMOUNT_KEY = new NamespacedKey(FinalTech.getPlugin(FinalTech.class), "amount");

    public static int pushItemFromCard(@Nonnull ItemStack item, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        if(!item.hasItemMeta()) {
            return 0;
        }
        if(item.getAmount() != 1) {
            return 0;
        }
        ItemMeta itemMeta = item.getItemMeta();
        int count = pushItemFromCard(itemMeta, inventory, slots);
        item.setItemMeta(itemMeta);
        return count;
    }

    public static int pushItemFromCard(@Nonnull ItemMeta itemMeta, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING)) {
            String itemString = persistentDataContainer.get(ITEM_KEY, PersistentDataType.STRING);
            ItemStackWithWrapper stringItem = new ItemStackWithWrapper(ItemStackUtil.stringToItemStack(itemString));
            return pushItemFromCard(itemMeta, stringItem, inventory, slots);
        }
        return 0;
    }

    public static int pushItemFromCard(@Nonnull ItemMeta itemMeta, @Nonnull ItemStackWithWrapper stringItem, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        String amount = persistentDataContainer.get(AMOUNT_KEY, PersistentDataType.STRING);
        int maxStackSize = stringItem.getItemStack().getMaxStackSize();
        String maxStack = String.valueOf(maxStackSize);
        ItemStack stack;
        int count = 0;
        for(int slot : slots) {
            stack = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(stack)) {
                if(StringNumberUtil.easilyCompare(amount, maxStack) >= 1) {
                    stack = stringItem.getItemStack().clone();
                    stack.setAmount(maxStackSize);
                    count += maxStackSize;
                    inventory.setItem(slot, stack);
                    amount = StringNumberUtil.sub(amount, maxStack);
                } else if(StringNumberUtil.easilyCompare(amount, StringNumberUtil.ZERO) == 1) {
                    stack = stringItem.getItemStack().clone();
                    stack.setAmount(Integer.parseInt(amount));
                    count += Integer.parseInt(amount);
                    inventory.setItem(slot, stack);
                    amount = StringNumberUtil.ZERO;
                    break;
                }
            } else if(stack.getMaxStackSize() > stack.getAmount() && ItemStackUtil.isItemSimilar(stack, stringItem.getItemStackWrapper())) {
                int stackableAmount = stack.getMaxStackSize() - stack.getAmount();
                if(StringNumberUtil.easilyCompare(amount, String.valueOf(stackableAmount)) >= 1) {
                    stack.setAmount(stack.getMaxStackSize());
                    count += stackableAmount;
                    amount = StringNumberUtil.sub(amount, String.valueOf(stackableAmount));
                } else {
                    stack.setAmount(stack.getAmount() + Integer.parseInt(amount));
                    count += Integer.parseInt(amount);
                    amount = StringNumberUtil.ZERO;
                    break;
                }
            }
            if(StringNumberUtil.ZERO.equals(amount)) {
                break;
            }
        }
        if(StringNumberUtil.ZERO.equals(amount)) {
            persistentDataContainer.remove(ITEM_KEY);
            persistentDataContainer.remove(AMOUNT_KEY);
        } else {
            persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, amount);
        }
        return count;
    }

    public static int storageItemToCard(@Nonnull ItemStack item, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        if(!item.hasItemMeta()) {
            return 0;
        }
        ItemMeta itemMeta = item.getItemMeta();
        int count = storageItemToCard(itemMeta, item.getAmount(), inventory, slots);
        item.setItemMeta(itemMeta);
        return count;
    }

    public static int storageItemToCard(@Nonnull ItemMeta itemMeta, int size, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        ItemStackWithWrapper stringItem = null;
        if(persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING)) {
            String itemString = persistentDataContainer.get(ITEM_KEY, PersistentDataType.STRING);
            stringItem = new ItemStackWithWrapper(ItemStackUtil.stringToItemStack(itemString));
        }
        return storageItemToCard(itemMeta, stringItem, size, inventory, slots);
    }

    public static int storageItemToCard(@Nonnull ItemMeta itemMeta, @Nullable ItemStackWithWrapper stringItem, int size, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        String amount = StringNumberUtil.ZERO;
        if(persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            amount = persistentDataContainer.get(AMOUNT_KEY, PersistentDataType.STRING);
        } else {
            stringItem = null;
        }
        ItemStack stack;
        int itemAmount = 0;
        int count = 0;
        for(int slot : slots) {
            stack = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(stack)) {
                continue;
            }
            itemAmount = stack.getAmount() / size;
            if(itemAmount == 0) {
                continue;
            }
            if(stringItem == null || ItemStackUtil.isItemNull(stringItem.getItemStack())) {
                stringItem = new ItemStackWithWrapper(stack.clone());
                stringItem.setAmount(1);
                persistentDataContainer.set(ITEM_KEY, PersistentDataType.STRING, ItemStackUtil.itemStackToString(stringItem.getItemStack()));
                amount = String.valueOf(itemAmount);
                stack.setAmount(stack.getAmount() - itemAmount * size);
                count += itemAmount * size;
            } else if(ItemStackUtil.isItemSimilar(stringItem.getItemStackWrapper(), stack)) {
                amount = StringNumberUtil.add(amount, String.valueOf(itemAmount));
                stack.setAmount(stack.getAmount() - itemAmount * size);
                count += itemAmount * size;
            }
        }
        if(!StringNumberUtil.ZERO.equals(amount)) {
            persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, amount);
        }
        return count;
    }

    @Nullable
    public static ItemStack parseItemInCard(@Nonnull ItemStack cardItem) {
        if(!cardItem.hasItemMeta()) {
            return null;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        return parseItemInCard(itemMeta);
    }

    @Nullable
    public static ItemStack parseItemInCard(@Nonnull ItemMeta cardItemMeta) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING)) {
            String itemString = persistentDataContainer.get(ITEM_KEY, PersistentDataType.STRING);
            ItemStack stringItem = ItemStackUtil.stringToItemStack(itemString);
            stringItem.setAmount(1);
            return stringItem;
        }
        return null;
    }

    @Nonnull
    public static String parseAmountInCard(@Nonnull ItemStack cardItem) {
        if(!cardItem.hasItemMeta()) {
            return null;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        return parseAmountInCard(itemMeta);
    }

    @Nonnull
    public static String parseAmountInCard(@Nonnull ItemMeta cardItemMeta) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            return persistentDataContainer.get(AMOUNT_KEY, PersistentDataType.STRING);
        }
        return StringNumberUtil.ZERO;
    }

    public static void setItemInCard(@Nonnull ItemStack cardItem, @Nonnull ItemStack item, @Nonnull int amount) {
        setItemInCard(cardItem, item, String.valueOf(amount));
    }

    public static void setItemInCard(@Nonnull ItemStack cardItem, @Nonnull ItemStack item, @Nonnull String amount) {
        if(!cardItem.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(ITEM_KEY, PersistentDataType.STRING, ItemStackUtil.itemStackToString(item));
        persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, amount);
        cardItem.setItemMeta(itemMeta);
    }

    public static void setAmountInCard(@Nonnull ItemStack cardItem, String amount) {
        if(!cardItem.hasItemMeta()) {
            return;
        }
        ItemMeta cardItemMeta = cardItem.getItemMeta();
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING) && persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            if(StringNumberUtil.easilyCompare(amount, StringNumberUtil.ZERO) == 0) {
                persistentDataContainer.remove(ITEM_KEY);
                persistentDataContainer.remove(AMOUNT_KEY);
            } else {
                persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, amount);
            }
        }
        cardItem.setItemMeta(cardItemMeta);
    }

    public static void updateStorageCardLore(@Nonnull ItemStack item) {
        if(!item.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        updateStorageCardLore(itemMeta);
        item.setItemMeta(itemMeta);
    }

    public static void updateStorageCardLore(@Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        ItemStack stringItem = null;
        if(persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING)) {
            String itemString = persistentDataContainer.get(ITEM_KEY, PersistentDataType.STRING);
            stringItem = ItemStackUtil.stringToItemStack(itemString);
        }
        updateStorageCardLore(itemMeta, stringItem);
    }

    public static void updateStorageCardLore(@Nonnull ItemMeta itemMeta, @Nullable ItemStack stringItem) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            String amount = persistentDataContainer.get(AMOUNT_KEY, PersistentDataType.STRING);
            List<String> lore = itemMeta.getLore();
            if(lore == null || lore.isEmpty()) {
                lore = new ArrayList<>(4);
                lore.add(StorageCardItem.ITEM_LORE);
            } else {
                lore.set(0, StorageCardItem.ITEM_LORE);
            }
            String name = ItemStackUtil.getItemName(stringItem);
            if(lore.size() == 1) {
                lore.add("§7存储的物品=" + name);
            } else {
                lore.set(1, "§7存储的物品=" + name);
            }
            if(lore.size() == 2) {
                lore.add("§7存储的数量=" + amount);
            } else {
                lore.set(2, "§7存储的数量=" + amount);
            }
            itemMeta.setLore(lore);
        } else {
            List<String> lore = new ArrayList<>(4);
            lore.add(StorageCardItem.ITEM_LORE);
            lore.add("§7未存储物品");
            itemMeta.setLore(lore);
        }
    }

    public static void updateStorageCardType(@Nonnull ItemStack item) {
        updateStorageCardType(item, item.getItemMeta());
    }

    public static void updateStorageCardType(@Nonnull ItemStack item, @Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if(persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            Material type = item.getType();
            switch (type) {
                case WHITE_CONCRETE_POWDER -> type = Material.WHITE_CONCRETE;
                case ORANGE_CONCRETE_POWDER -> type = Material.ORANGE_CONCRETE;
                case MAGENTA_CONCRETE_POWDER -> type = Material.MAGENTA_CONCRETE;
                case LIGHT_BLUE_CONCRETE_POWDER -> type = Material.LIGHT_BLUE_CONCRETE;
                case YELLOW_CONCRETE_POWDER -> type = Material.YELLOW_CONCRETE;
                case LIME_CONCRETE_POWDER -> type = Material.LIME_CONCRETE;
                case PINK_CONCRETE_POWDER -> type = Material.PINK_CONCRETE;
                case GRAY_CONCRETE_POWDER -> type = Material.GRAY_CONCRETE;
                case LIGHT_GRAY_CONCRETE_POWDER -> type = Material.LIGHT_GRAY_CONCRETE;
                case CYAN_CONCRETE_POWDER -> type = Material.CYAN_CONCRETE;
                case PURPLE_CONCRETE_POWDER -> type = Material.PURPLE_CONCRETE;
                case BLUE_CONCRETE_POWDER -> type = Material.BLUE_CONCRETE;
                case BROWN_CONCRETE_POWDER -> type = Material.BROWN_CONCRETE;
                case GREEN_CONCRETE_POWDER -> type = Material.GREEN_CONCRETE;
                case RED_CONCRETE_POWDER -> type = Material.RED_CONCRETE;
                case BLACK_CONCRETE_POWDER -> type = Material.BLACK_CONCRETE;
                default -> {
                }
            }
            item.setType(type);
        } else {
            Material type = item.getType();
            switch (type) {
                case WHITE_CONCRETE -> type = Material.WHITE_CONCRETE_POWDER;
                case ORANGE_CONCRETE -> type = Material.ORANGE_CONCRETE_POWDER;
                case MAGENTA_CONCRETE -> type = Material.MAGENTA_CONCRETE_POWDER;
                case LIGHT_BLUE_CONCRETE -> type = Material.LIGHT_BLUE_CONCRETE_POWDER;
                case YELLOW_CONCRETE -> type = Material.YELLOW_CONCRETE_POWDER;
                case LIME_CONCRETE -> type = Material.LIME_CONCRETE_POWDER;
                case PINK_CONCRETE -> type = Material.PINK_CONCRETE_POWDER;
                case GRAY_CONCRETE -> type = Material.GRAY_CONCRETE_POWDER;
                case LIGHT_GRAY_CONCRETE -> type = Material.LIGHT_GRAY_CONCRETE_POWDER;
                case CYAN_CONCRETE -> type = Material.CYAN_CONCRETE_POWDER;
                case PURPLE_CONCRETE -> type = Material.PURPLE_CONCRETE_POWDER;
                case BLUE_CONCRETE -> type = Material.BLUE_CONCRETE_POWDER;
                case BROWN_CONCRETE -> type = Material.BROWN_CONCRETE_POWDER;
                case GREEN_CONCRETE -> type = Material.GREEN_CONCRETE_POWDER;
                case RED_CONCRETE -> type = Material.RED_CONCRETE_POWDER;
                case BLACK_CONCRETE -> type = Material.BLACK_CONCRETE_POWDER;
                default -> {
                }
            }
            item.setType(type);
        }
    }
}
