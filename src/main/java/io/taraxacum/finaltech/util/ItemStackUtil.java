package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.nms.ItemNameAdapter;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.dto.ItemStackWithWrapper;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Final_ROOT
 */
public final class ItemStackUtil {
    public static final ItemStack AIR = new ItemStack(Material.AIR);
    public static final ItemNameAdapter itemNameAdapter = ItemNameAdapter.get();

    public static ItemStack cloneItem(@Nonnull ItemStack item) {
        return item instanceof ItemStackWrapper ? new ItemStack(item) : item.clone();
    }

    public static boolean isItemNull(@Nullable ItemStack item) {
        return item == null || item.getType().equals(Material.AIR) || item.getAmount() == 0;
    }

    public static boolean isItemSame(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        if (isItemNull(item1) && isItemNull(item2)) {
            return true;
        } else if (isItemNull(item1) || isItemNull(item2)) {
            return false;
        }
        if (item1.getAmount() != item2.getAmount()) {
            return false;
        }
        if (item1.getType() != item2.getType()) {
            return false;
        }
        if (item1.hasItemMeta() && item2.hasItemMeta()) {
            return isItemMetaSame(item1.getItemMeta(), item2.getItemMeta());
        }
        return !item1.hasItemMeta() && !item2.hasItemMeta();
    }

    public static boolean isItemSimilar(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        boolean itemNull1 = isItemNull(item1);
        boolean itemNull2 = isItemNull(item2);
        if (itemNull1 && itemNull2) {
            return true;
        } else if (itemNull1 || itemNull2) {
            return false;
        }
        if (!item1.getType().equals(item2.getType())) {
            return false;
        }
        if (item1.hasItemMeta() && item2.hasItemMeta()) {
            return isItemMetaSame(item1.getItemMeta(), item2.getItemMeta());
        }
        return !item1.hasItemMeta() && !item2.hasItemMeta();
    }
    public static boolean isItemSimilar(@Nullable ItemStackWithWrapper itemWithWrapper, @Nullable ItemStack item) {
        boolean itemNull1 = itemWithWrapper == null || isItemNull(itemWithWrapper.getItemStack());
        boolean itemNull2 = isItemNull(item);
        if (itemNull1 && itemNull2) {
            return true;
        } else if (itemNull1 || itemNull2) {
            return false;
        }
        if (!itemWithWrapper.getItemStack().getType().equals(item.getType())) {
            return false;
        }
        if (itemWithWrapper.getItemStackWrapper().hasItemMeta() && item.hasItemMeta()) {
            return isItemMetaSame(itemWithWrapper.getItemStackWrapper().getItemMeta(), item.getItemMeta());
        }
        return !itemWithWrapper.getItemStackWrapper().hasItemMeta() && !item.hasItemMeta();
    }
    public static boolean isItemSimilar(@Nullable ItemStack item, @Nullable ItemStackWithWrapper itemWithWrapper) {
        boolean itemNull1 = isItemNull(item);
        boolean itemNull2 = itemWithWrapper == null || isItemNull(itemWithWrapper.getItemStack());
        if (itemNull1 && itemNull2) {
            return true;
        } else if (itemNull1 || itemNull2) {
            return false;
        }
        if (!itemWithWrapper.getItemStack().getType().equals(item.getType())) {
            return false;
        }
        if (itemWithWrapper.getItemStackWrapper().hasItemMeta() && item.hasItemMeta()) {
            return isItemMetaSame(itemWithWrapper.getItemStackWrapper().getItemMeta(), item.getItemMeta());
        }
        return !itemWithWrapper.getItemStackWrapper().hasItemMeta() && !item.hasItemMeta();
    }
    public static boolean isItemSimilar(@Nonnull ItemStackWithWrapper itemStackWithWrapper1, @Nonnull ItemStackWithWrapper itemStackWithWrapper2) {
        boolean itemNull1 = isItemNull(itemStackWithWrapper1.getItemStack());
        boolean itemNull2 = isItemNull(itemStackWithWrapper2.getItemStack());
        if (itemNull1 && itemNull2) {
            return true;
        } else if (itemNull1 || itemNull2) {
            return false;
        }
        if (!itemStackWithWrapper1.getItemStack().getType().equals(itemStackWithWrapper2.getItemStack().getType())) {
            return false;
        }
        if (itemStackWithWrapper1.getItemStackWrapper().hasItemMeta() && itemStackWithWrapper2.getItemStackWrapper().hasItemMeta()) {
            return isItemMetaSame(itemStackWithWrapper1.getItemStackWrapper().getItemMeta(), itemStackWithWrapper2.getItemStackWrapper().getItemMeta());
        }
        return !itemStackWithWrapper1.getItemStackWrapper().hasItemMeta() && !itemStackWithWrapper2.getItemStackWrapper().hasItemMeta();
    }

    public static boolean isItemMetaSame(@Nullable ItemMeta itemMeta1, @Nullable ItemMeta itemMeta2) {
        if (itemMeta1 == null && itemMeta2 == null) {
            return true;
        } else if (itemMeta1 == null || itemMeta2 == null) {
            return false;
        }
        if (itemMeta1.hasDisplayName() && itemMeta2.hasDisplayName()) {
            if (!itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName())) {
                return false;
            }
        } else if (itemMeta1.hasDisplayName() || itemMeta2.hasDisplayName()) {
            return false;
        }
        return isLoreSame(itemMeta1, itemMeta2) && checkContainer(itemMeta1, itemMeta2);
    }

    public static boolean isLoreSame(@Nonnull ItemMeta itemMeta1, @Nonnull ItemMeta itemMeta2) {
        if (itemMeta1.hasLore() && itemMeta2.hasLore()) {
            List<String> lore1 = itemMeta1.getLore();
            List<String> lore2 = itemMeta2.getLore();
            if (lore1.size() != lore2.size()) {
                return false;
            }
            for (int i = 0; i < lore1.size(); i++) {
                if (!lore1.get(i).equals(lore2.get(i))) {
                    return false;
                }
            }
            return true;
        } return !itemMeta1.hasLore() && !itemMeta2.hasLore();
    }

    public static boolean checkContainer(@Nonnull ItemMeta itemMeta1, @Nonnull ItemMeta itemMeta2) {
        PersistentDataContainer persistentDataContainer1 = itemMeta1.getPersistentDataContainer();
        PersistentDataContainer persistentDataContainer2 = itemMeta2.getPersistentDataContainer();
        Set<NamespacedKey> keys1 = persistentDataContainer1.getKeys();
        Set<NamespacedKey> keys2 = persistentDataContainer2.getKeys();
        if (keys1.size() != keys2.size()) {
            return false;
        }
        for (NamespacedKey namespacedKey : keys1) {
            if (!persistentDataContainer2.has(namespacedKey, PersistentDataType.STRING)) {
                return false;
            }
            if (!Objects.equals(persistentDataContainer1.get(namespacedKey, PersistentDataType.STRING), persistentDataContainer2.get(namespacedKey, PersistentDataType.STRING))) {
                return false;
            }
        }
        return true;
    }

    @Nonnull
    public static ItemStack[] calNoNullItemArray(@Nonnull List<ItemStack> itemList) {
        List<ItemStack> noNullItemList = new ArrayList<>(itemList.size());
        for (ItemStack item : itemList) {
            if (!ItemStackUtil.isItemNull(item)) {
                noNullItemList.add(item);
            }
        }
        return calItemArray(noNullItemList);
    }
    @Nonnull
    public static ItemStack[] calItemArray(@Nonnull List<ItemStack> itemList) {
        ItemStack[] items = new ItemStack[itemList.size()];
        for(int i = 0; i < items.length; i++) {
            items[i] = itemList.get(i);
        }
        return items;
    }

    /**
     * 把输入的列表中，相同的物品进行合并
     * @param itemList 输入的列表
     * @return 合并后的列表
     */
    @Nonnull
    public static List<ItemStack> calMergeItemList(@Nonnull List<ItemStack> itemList) {
        List<ItemStackWithWrapper> itemWithWrapperList = new ArrayList<>(itemList.size());
        for (ItemStack item : itemList) {
            if (isItemNull(item)) {
                continue;
            }
            ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
            int amount = item.getAmount();
            for (ItemStackWithWrapper resultItem : itemWithWrapperList) {
                ItemStack itemStack = resultItem.getItemStack();
                if (itemStack.getAmount() >= itemStack.getMaxStackSize()) {
                    continue;
                }
                if (isItemSimilar(itemStackWrapper, resultItem.getItemStackWrapper())) {
                    int count = Math.min(amount, itemStack.getMaxStackSize() - itemStack.getAmount());
                    itemStack.setAmount(itemStack.getAmount() + count);
                    amount -= count;
                    if (amount == 0) {
                        break;
                    }
                }
            }
            if (amount != 0) {
                ItemStack itemstack = ItemStackUtil.cloneItem(item);
                itemstack.setAmount(amount);
                itemWithWrapperList.add(new ItemStackWithWrapper(itemstack, itemStackWrapper));
            }
        }
        return ItemStackWithWrapper.toItemList(itemWithWrapperList);
    }
    @Nonnull
    public static ItemStack[] calMergeItemList(@Nonnull ItemStack[] items) {
        List<ItemStack> itemList = calMergeItemList(Arrays.stream(items).toList());
        ItemStack[] result = new ItemStack[itemList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = itemList.get(i);
        }
        return result;
    }

    @Nonnull
    public static ItemStack[] calEnlargeItemArray(@Nonnull ItemStack[] items, int amount) {
        int slot = 0;
        for (ItemStack item : items) {
            slot = slot + 1 + item.getAmount() * amount / item.getMaxStackSize();
            if (item.getAmount() * amount % item.getMaxStackSize() == 0) {
                slot--;
            }
        }
        ItemStack[] result = new ItemStack[slot];
        int pointer = 0;
        for (ItemStack item : items) {
            int resultAmount = item.getAmount() * amount;
            while(resultAmount > item.getMaxStackSize()) {
                result[pointer] = ItemStackUtil.cloneItem(item);
                result[pointer++].setAmount(item.getMaxStackSize());
                resultAmount -= item.getMaxStackSize();
            }
            if (resultAmount != 0) {
                result[pointer] = ItemStackUtil.cloneItem(item);
                result[pointer++].setAmount(resultAmount);
            }
        }
        return result;
    }
    @Nonnull
    public static ItemStack[] calEnlargeItemArray(@Nonnull List<ItemStackWithWrapper> itemWithWrapperList, int amount) {
        int slot = 0;
        for (ItemStackWithWrapper itemWithWrapper : itemWithWrapperList) {
            slot = slot + 1 + itemWithWrapper.getAmount() * amount / itemWithWrapper.getItemStack().getMaxStackSize();
            if (itemWithWrapper.getAmount() * amount % itemWithWrapper.getItemStack().getMaxStackSize() == 0) {
                slot--;
            }
        }
        ItemStack[] result = new ItemStack[slot];
        int pointer = 0;
        for (ItemStackWithWrapper itemWithWrapper : itemWithWrapperList) {
            int resultAmount = itemWithWrapper.getAmount() * amount;
            while(resultAmount > itemWithWrapper.getItemStack().getMaxStackSize()) {
                result[pointer] = ItemStackUtil.cloneItem(itemWithWrapper.getItemStack());
                result[pointer++].setAmount(itemWithWrapper.getItemStack().getMaxStackSize());
                resultAmount -= itemWithWrapper.getItemStack().getMaxStackSize();
            }
            if (resultAmount != 0) {
                result[pointer] = ItemStackUtil.cloneItem(itemWithWrapper.getItemStack());
                result[pointer++].setAmount(resultAmount);
            }
        }
        return result;
    }

    /**
     * ItemStackWithWrapper中
     * 使用getAmount()获取该类物品的数量
     * @param items
     * @return
     */
    @Nonnull
    public static List<ItemStackWithWrapper> calItemListWithAmount(@Nonnull ItemStack[] items) {
        List<ItemStackWithWrapper> itemWithWrapperList = new ArrayList<>(items.length);
        for (ItemStack item : items) {
            ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
            boolean find = false;
            for (ItemStackWithWrapper resultItem : itemWithWrapperList) {
                if (isItemSimilar(itemStackWrapper, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if (!find) {
                itemWithWrapperList.add(new ItemStackWithWrapper(item, itemStackWrapper));
            }
        }
        return itemWithWrapperList;
    }
    @Nonnull
    public static List<ItemStackWithWrapper> calItemListWithAmount(@Nonnull List<ItemStack> itemList) {
        List<ItemStackWithWrapper> itemWithWrapperlist = new ArrayList<>(itemList.size());
        for (ItemStack item : itemList) {
            ItemStackWrapper itemWithWrapper = ItemStackWrapper.wrap(item);
            boolean find = false;
            for (ItemStackWithWrapper resultItem : itemWithWrapperlist) {
                if (isItemSimilar(itemWithWrapper, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if (!find) {
                itemWithWrapperlist.add(new ItemStackWithWrapper(item, itemWithWrapper));
            }
        }
        return itemWithWrapperlist;
    }

    /**
     * 使其中一个物品尝试堆叠至另一个物品
     * @param input 输入物品，由于堆叠这个物品的数量会减少
     * @param output 输出物品，由于堆叠这个物品的数量会增多
     * @return 实际进行了堆叠的数量
     */
    public static int stack(@Nullable ItemStack input, @Nullable ItemStack output) {
        if (!isItemNull(output) && output.getAmount() < output.getMaxStackSize() && ItemStackUtil.isItemSimilar(input, output)) {
            int amount = Math.min(input.getAmount(), output.getMaxStackSize() - output.getAmount());
            input.setAmount(input.getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper inputItem, @Nullable ItemStack output) {
        if (inputItem != null && !isItemNull(output) && output.getAmount() < output.getMaxStackSize() && ItemStackUtil.isItemSimilar(inputItem.getItemStackWrapper(), output)) {
            int amount = Math.min(inputItem.getItemStack().getAmount(), output.getMaxStackSize() - output.getAmount());
            inputItem.getItemStack().setAmount(inputItem.getItemStack().getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStack input, @Nullable ItemStackWithWrapper output) {
        if (output != null && !isItemNull(output.getItemStack()) && output.getItemStack().getAmount() < output.getItemStack().getMaxStackSize() && ItemStackUtil.isItemSimilar(input, output.getItemStackWrapper())) {
            int amount = Math.min(input.getAmount(), output.getItemStack().getMaxStackSize() - output.getItemStack().getAmount());
            input.setAmount(input.getAmount() - amount);
            output.getItemStack().setAmount(output.getItemStack().getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper input, @Nullable ItemStackWithWrapper output) {
        if (input != null && output != null && output.getItemStack().getAmount() < output.getItemStack().getMaxStackSize() && ItemStackUtil.isItemSimilar(input.getItemStackWrapper(), output.getItemStackWrapper())) {
            int amount = Math.min(input.getItemStack().getAmount(), output.getItemStack().getMaxStackSize() - output.getItemStack().getAmount());
            input.getItemStack().setAmount(input.getItemStack().getAmount() - amount);
            output.getItemStack().setAmount(output.getItemStack().getAmount() + amount);
            return amount;
        }
        return 0;
    }

    /**
     * 使其中一个物品尝试堆叠至另一个物品
     * @param input 输入物品，由于堆叠这个物品的数量会减少
     * @param output 输出物品，由于堆叠这个物品的数量会增多
     * @param maxAmount 堆叠数量限制
     * @return 实际进行了堆叠的数量
     */
    public static int stack(@Nullable ItemStack input, @Nullable ItemStack output, int maxAmount) {
        if (!isItemNull(output) && output.getMaxStackSize() > output.getAmount() && ItemStackUtil.isItemSimilar(input, output)) {
            int amount = Math.min(maxAmount, Math.min(input.getAmount(), output.getMaxStackSize() - output.getAmount()));
            input.setAmount(input.getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper input, @Nullable ItemStack output, int maxAmount) {
        if (input != null && !isItemNull(output) && output.getMaxStackSize() > output.getAmount() && ItemStackUtil.isItemSimilar(input.getItemStackWrapper(), output)) {
            int amount = Math.min(maxAmount, Math.min(input.getItemStack().getAmount(), output.getMaxStackSize() - output.getAmount()));
            input.getItemStack().setAmount(input.getItemStack().getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStack input, @Nullable ItemStackWithWrapper output, int maxAmount) {
        if (output != null && !isItemNull(output.getItemStack()) && output.getItemStack().getMaxStackSize() > output.getItemStack().getAmount() && ItemStackUtil.isItemSimilar(input, output.getItemStackWrapper())) {
            int amount = Math.min(maxAmount, Math.min(input.getAmount(), output.getItemStack().getMaxStackSize() - output.getItemStack().getAmount()));
            input.setAmount(input.getAmount() - amount);
            output.getItemStack().setAmount(output.getItemStack().getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper input, @Nullable ItemStackWithWrapper output, int maxAmount) {
        if (input != null && output != null && output.getItemStack().getMaxStackSize() > output.getItemStack().getAmount() && ItemStackUtil.isItemSimilar(input.getItemStackWrapper(), output.getItemStackWrapper())) {
            int amount = Math.min(maxAmount, Math.min(input.getItemStack().getAmount(), output.getItemStack().getMaxStackSize() - output.getItemStack().getAmount()));
            input.getItemStack().setAmount(input.getItemStack().getAmount() - amount);
            output.getItemStack().setAmount(output.getItemStack().getAmount() + amount);
            return amount;
        }
        return 0;
    }

    public static String getItemName(@Nullable ItemStack item) {
        if (isItemNull(item)) {
            return "null";
        }
        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                return itemMeta.getDisplayName();
            }
        } else {
            try {
                return itemNameAdapter.getName(item);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return "unknown";
    }

    public static void addLoreToLast(@Nonnull ItemStack item, @Nonnull String s) {
        if (isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>(8);
        }
        lore.add(s);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static void addLoreToLast(@Nonnull ItemMeta itemMeta, @Nonnull String s) {
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>(8);
        }
        lore.add(s);
        itemMeta.setLore(lore);
    }

    public static void removeLastLore(@Nonnull ItemStack item) {
        if (!item.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() == 0) {
            return;
        }
        lore = lore.subList(0, Math.max(lore.size() - 1, 0));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static void removeLastLore(@Nonnull ItemMeta itemMeta) {
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() == 0) {
            return;
        }
        lore = lore.subList(0, Math.max(lore.size() - 1, 0));
        itemMeta.setLore(lore);
    }

    public static void setLastLore(@Nonnull ItemStack item, @Nonnull String s) {
        if (isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() == 0) {
            lore = new ArrayList<>();
            lore.add(s);
        } else {
            lore.set(lore.size() - 1, s);
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
    public static void setLastLore(@Nonnull ItemMeta itemMeta, @Nonnull String s) {
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() == 0) {
            lore = new ArrayList<>();
            lore.add(s);
        } else {
            lore.set(lore.size() - 1, s);
        }
        itemMeta.setLore(lore);
    }

    public static String getLastLore(@Nonnull ItemStack item) {
        if (isItemNull(item)) {
            return "null";
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() == 0) {
            return null;
        }
        return lore.get(lore.size() - 1);
    }

    public static String getLastLore(@Nonnull ItemMeta itemMeta) {
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() == 0) {
            return null;
        }
        return lore.get(lore.size() - 1);
    }

    public static void setLore(@Nonnull ItemStack item, String... lore) {
        if (isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(Arrays.stream(lore).toList());
        item.setItemMeta(itemMeta);
    }

    public static ItemStack getDried(@Nonnull ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return null;
        }
        switch (item.getType()) {
            case POTION:
            case DRAGON_BREATH:
            case HONEY_BOTTLE:
                return new ItemStack(Material.GLASS_BOTTLE, 1);
            case WATER_BUCKET:
            case LAVA_BUCKET:
            case MILK_BUCKET:
                return new ItemStack(Material.BUCKET, 1);
            default:
                return null;
        }
    }

    @Nullable
    public static ItemStack getLiquidCard(@Nullable ItemStack item) {
        if (isItemNull(item)) {
            return null;
        }
        if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return null;
        }
        return switch (item.getType()) {
            case WATER_BUCKET, POTION -> new ItemStack(FinalTechItems.WATER_CARD);
            case LAVA_BUCKET -> new ItemStack(FinalTechItems.LAVA_CARD);
            case MILK_BUCKET -> new ItemStack(FinalTechItems.MILK_CARD);
            default -> null;
        };
    }

    public static String itemStackToString(ItemStack itemStack) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("item", itemStack);
        return yamlConfiguration.saveToString();
    }

    public static ItemStack stringToItemStack(String local) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.loadFromString(local);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return yamlConfiguration.getItemStack("item");
    }
}
