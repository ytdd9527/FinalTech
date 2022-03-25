package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.nms.ItemNameAdapter;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    public static ItemStack cloneItem(ItemStack item) {
        return item instanceof ItemStackWrapper ? new ItemStack(item) : item.clone();
    }

    public static boolean isItemNull(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR) || item.getAmount() == 0;
    }

    public static boolean isItemSame(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        if(isItemNull(item1) && isItemNull(item2)) {
            return true;
        } else if(isItemNull(item1) || isItemNull(item2)) {
            return false;
        }
        if(item1.getAmount() != item2.getAmount()) {
            return false;
        }
        if(item1.getType() != item2.getType()) {
            return false;
        }
        if(item1.hasItemMeta() && item2.hasItemMeta()) {
            return isItemMetaSame(item1.getItemMeta(), item2.getItemMeta());
        }
        return !item1.hasItemMeta() && !item2.hasItemMeta();
    }

    public static boolean isItemSimilar(@Nonnull ItemStackWithWrapper itemStackWithWrapper1, @Nonnull ItemStackWithWrapper itemStackWithWrapper2) {
        boolean itemNull1 = isItemNull(itemStackWithWrapper1.getItemStack());
        boolean itemNull2 = isItemNull(itemStackWithWrapper2.getItemStack());
        if(itemNull1 && itemNull2) {
            return true;
        } else if(itemNull1 || itemNull2) {
            return false;
        }
        if(!itemStackWithWrapper1.getItemStack().getType().equals(itemStackWithWrapper2.getItemStack().getType())) {
            return false;
        }
        if(itemStackWithWrapper1.getItemStackWrapper().hasItemMeta() && itemStackWithWrapper2.getItemStackWrapper().hasItemMeta()) {
            return isItemMetaSame(itemStackWithWrapper1.getItemStackWrapper().getItemMeta(), itemStackWithWrapper2.getItemStackWrapper().getItemMeta());
        }
        return !itemStackWithWrapper1.getItemStackWrapper().hasItemMeta() && !itemStackWithWrapper2.getItemStackWrapper().hasItemMeta();
    }

    public static boolean isItemSimilar(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        boolean itemNull1 = isItemNull(item1);
        boolean itemNull2 = isItemNull(item2);
        if(itemNull1 && itemNull2) {
            return true;
        } else if(itemNull1 || itemNull2) {
            return false;
        }
        if(!item1.getType().equals(item2.getType())) {
            return false;
        }
        if(item1.hasItemMeta() && item2.hasItemMeta()) {
            return isItemMetaSame(item1.getItemMeta(), item2.getItemMeta());
        }
        return !item1.hasItemMeta() && !item2.hasItemMeta();
    }

    public static boolean isItemMetaSame(@Nullable ItemMeta itemMeta1, @Nullable ItemMeta itemMeta2) {
        if(itemMeta1 == null && itemMeta2 == null) {
            return true;
        } else if(itemMeta1 == null || itemMeta2 == null) {
            return false;
        }
        if(itemMeta1.hasDisplayName() && itemMeta2.hasDisplayName()) {
            if(!itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName())) {
                return false;
            }
        } else if(itemMeta1.hasDisplayName() || itemMeta2.hasDisplayName()) {
            return false;
        }
        return isLoreSame(itemMeta1, itemMeta2);
    }

    public static boolean isLoreSame(@Nonnull ItemMeta itemMeta1, @Nonnull ItemMeta itemMeta2) {
        if(itemMeta1.hasLore() && itemMeta2.hasLore()) {
            List<String> lore1 = itemMeta1.getLore();
            List<String> lore2 = itemMeta2.getLore();
            if(lore1.size() != lore2.size()) {
                return false;
            }
            for(int i = 0; i < lore1.size(); i++) {
                if(!lore1.get(i).equals(lore2.get(i))) {
                    return false;
                }
            }
            return true;
        } return !itemMeta1.hasLore() && !itemMeta2.hasLore();
    }

    /**
     * 把 List<ItemStack>类型的对象解析并返回 ItemStack[]类型的对象
     * @param items List<ItemStack>类型的对象
     * @param allowNull 解析时是否允许添加空的 ItemStack
     * @return ItemStack[]类型的对象
     */
    @Nonnull
    public static ItemStack[] toArray(@Nonnull List<ItemStack> items, boolean allowNull) {
        int length = items.size();
        if(!allowNull) {
            length = 0;
            for(ItemStack item : items) {
                if(!ItemStackUtil.isItemNull(item)) {
                    length++;
                }
            }
        }
        ItemStack[] result = new ItemStack[length];
        int i = 0;
        for(ItemStack item : items) {
            if(allowNull || !ItemStackUtil.isItemNull(item)) {
                result[i++] = item instanceof ItemStackWrapper ? ItemStackWrapper.wrap(item) : new ItemStack(item);
            }
        }
        return result;
    }

    /**
     * 把List<ItemStack>类型的对象解析并返回ItemStack[]类型的对象
     * 返回的ItemStack[]类型的对象，不会包含空的ItemStack对象
     * @param items List<ItemStack>类型的对象
     * @return ItemStack[]类型的对象
     */
    @Nonnull
    public static ItemStack[] toArray(@Nonnull List<ItemStack> items) {
        return toArray(items, false);
    }

    /**
     * 把输入的列表中，相同的物品进行合并
     * @param list 输入的列表
     * @return 合并后的列表
     */
    @Nonnull
    public static List<ItemStack> mergeSameItem(@Nonnull List<ItemStack> list) {
        List<ItemStackWithWrapper> resultList = new ArrayList<>(list.size());
        for(ItemStack item : list) {
            if(isItemNull(item)) {
                continue;
            }
            ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
            int amount = item.getAmount();
            for(ItemStackWithWrapper resultItem : resultList) {
                ItemStack itemStack = resultItem.getItemStack();
                if(itemStack.getMaxStackSize() == itemStack.getAmount()) {
                    continue;
                }
                if(isItemSimilar(itemStackWrapper, resultItem.getItemStackWrapper())) {
                    int count = Math.min(amount, itemStack.getMaxStackSize() - itemStack.getAmount());
                    itemStack.setAmount(itemStack.getAmount() + count);
                    amount -= count;
                    if(amount == 0) {
                        break;
                    }
                }
            }
            if(amount != 0) {
                ItemStack itemstack = ItemStackUtil.cloneItem(item);
                itemstack.setAmount(amount);
                resultList.add(new ItemStackWithWrapper(itemstack, itemStackWrapper));
            }
        }
        return ItemStackWithWrapper.toItemList(resultList);
    }

    @Nonnull
    public static ItemStack[] mergeSameItem(@Nonnull ItemStack[] items) {
        List<ItemStack> list = mergeSameItem(Arrays.stream(items).toList());
        ItemStack[] result = new ItemStack[list.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @Nonnull
    public static ItemStack[] mergeArray(@Nonnull ItemStack[] items1, @Nonnull ItemStack[] items2) {
        List<ItemStack> list = new ArrayList<>(items1.length + items2.length);
        list.addAll(Arrays.asList(items1));
        list.addAll(Arrays.asList(items2));
        list = mergeSameItem(list);
        ItemStack[] result = new ItemStack[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @Nonnull
    public static ItemStack[] enlargeItemArray(@Nonnull ItemStack[] items, int amount) {
        int slot = 0;
        for(ItemStack item : items) {
            slot = slot + 1 + item.getAmount() * amount / item.getMaxStackSize();
            if(item.getAmount() * amount % item.getMaxStackSize() == 0) {
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
            if(resultAmount != 0) {
                result[pointer] = ItemStackUtil.cloneItem(item);
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
    public static List<ItemStackWithWrapper> parseItemWithAmount(@Nonnull ItemStack[] items) {
        List<ItemStackWithWrapper> list = new ArrayList<>(items.length);
        for(ItemStack item : items) {
            ItemStackWrapper wrap = ItemStackWrapper.wrap(item);
            boolean find = false;
            for(ItemStackWithWrapper resultItem : list) {
                if(isItemSimilar(wrap, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if(!find) {
                list.add(new ItemStackWithWrapper(item, wrap));
            }
        }
        return list;
    }

    /**
     * ItemStackWithWrapper中
     * 使用getAmount()获取该类物品的数量
     * @param items
     * @return
     */
    @Nonnull
    public static List<ItemStackWithWrapper> parseItemWithAmount(@Nonnull List<ItemStack> items) {
        List<ItemStackWithWrapper> list = new ArrayList<>(items.size());
        for(ItemStack item : items) {
            ItemStackWrapper wrap = ItemStackWrapper.wrap(item);
            boolean find = false;
            for(ItemStackWithWrapper resultItem : list) {
                if(isItemSimilar(wrap, resultItem.getItemStackWrapper())) {
                    resultItem.addAmount(item.getAmount());
                    find = true;
                    break;
                }
            }
            if(!find) {
                list.add(new ItemStackWithWrapper(item, wrap));
            }
        }
        return list;
    }

    /**
     * 使其中一个物品尝试堆叠至另一个物品
     * @param input 输入物品，由于堆叠这个物品的数量会减少
     * @param output 输出物品，由于堆叠这个物品的数量会增多
     * @return 实际进行了堆叠的数量
     */
    public static int stack(@Nullable ItemStack input, @Nullable ItemStack output) {
        if(!isItemNull(output) && output.getMaxStackSize() > output.getAmount() && ItemStackUtil.isItemSimilar(input, output)) {
            int amount = Math.min(input.getAmount(), output.getMaxStackSize() - output.getAmount());
            input.setAmount(input.getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper input, @Nullable ItemStack output) {
        if(input != null && !isItemNull(output) && output.getMaxStackSize() > output.getAmount() && ItemStackUtil.isItemSimilar(input.getItemStackWrapper(), output)) {
            int amount = Math.min(input.getItemStack().getAmount(), output.getMaxStackSize() - output.getAmount());
            input.getItemStack().setAmount(input.getItemStack().getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStack input, @Nullable ItemStackWithWrapper output) {
        if(output != null && !isItemNull(output.getItemStack()) && output.getItemStack().getMaxStackSize() > output.getItemStack().getAmount() && ItemStackUtil.isItemSimilar(input, output.getItemStackWrapper())) {
            int amount = Math.min(input.getAmount(), output.getItemStack().getMaxStackSize() - output.getItemStack().getAmount());
            input.setAmount(input.getAmount() - amount);
            output.getItemStack().setAmount(output.getItemStack().getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper input, @Nullable ItemStackWithWrapper output) {
        if(input != null && output != null && output.getItemStack().getMaxStackSize() > output.getItemStack().getAmount() && ItemStackUtil.isItemSimilar(input.getItemStackWrapper(), output.getItemStackWrapper())) {
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
        if(!isItemNull(output) && output.getMaxStackSize() > output.getAmount() && ItemStackUtil.isItemSimilar(input, output)) {
            int amount = Math.min(maxAmount, Math.min(input.getAmount(), output.getMaxStackSize() - output.getAmount()));
            input.setAmount(input.getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper input, @Nullable ItemStack output, int maxAmount) {
        if(input != null && !isItemNull(output) && output.getMaxStackSize() > output.getAmount() && ItemStackUtil.isItemSimilar(input.getItemStackWrapper(), output)) {
            int amount = Math.min(maxAmount, Math.min(input.getItemStack().getAmount(), output.getMaxStackSize() - output.getAmount()));
            input.getItemStack().setAmount(input.getItemStack().getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStack input, @Nullable ItemStackWithWrapper output, int maxAmount) {
        if(output != null && !isItemNull(output.getItemStack()) && output.getItemStack().getMaxStackSize() > output.getItemStack().getAmount() && ItemStackUtil.isItemSimilar(input, output.getItemStackWrapper())) {
            int amount = Math.min(maxAmount, Math.min(input.getAmount(), output.getItemStack().getMaxStackSize() - output.getItemStack().getAmount()));
            input.setAmount(input.getAmount() - amount);
            output.getItemStack().setAmount(output.getItemStack().getAmount() + amount);
            return amount;
        }
        return 0;
    }
    public static int stack(@Nullable ItemStackWithWrapper input, @Nullable ItemStackWithWrapper output, int maxAmount) {
        if(input != null && output != null && output.getItemStack().getMaxStackSize() > output.getItemStack().getAmount() && ItemStackUtil.isItemSimilar(input.getItemStackWrapper(), output.getItemStackWrapper())) {
            int amount = Math.min(maxAmount, Math.min(input.getItemStack().getAmount(), output.getItemStack().getMaxStackSize() - output.getItemStack().getAmount()));
            input.getItemStack().setAmount(input.getItemStack().getAmount() - amount);
            output.getItemStack().setAmount(output.getItemStack().getAmount() + amount);
            return amount;
        }
        return 0;
    }

    public static String getItemName(@Nullable ItemStack item) {
        if(isItemNull(item)) {
            return "null";
        }
        if(item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if(itemMeta.hasDisplayName()) {
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
        if(isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
            lore = new ArrayList<>(8);
        }
        lore.add(s);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static void addLoreToLast(@Nonnull ItemMeta itemMeta, @Nonnull String s) {
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
            lore = new ArrayList<>(8);
        }
        lore.add(s);
        itemMeta.setLore(lore);
    }

    public static void removeLastLore(@Nonnull ItemStack item) {
        if(!item.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null || lore.size() == 0) {
            return;
        }
        lore = lore.subList(0, Math.max(lore.size() - 1, 0));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static void removeLastLore(@Nonnull ItemMeta itemMeta) {
        List<String> lore = itemMeta.getLore();
        if(lore == null || lore.size() == 0) {
            return;
        }
        lore = lore.subList(0, Math.max(lore.size() - 1, 0));
        itemMeta.setLore(lore);
    }

    public static void setLastLore(@Nonnull ItemStack item, @Nonnull String s) {
        if(isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null || lore.size() == 0) {
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
        if(lore == null || lore.size() == 0) {
            lore = new ArrayList<>();
            lore.add(s);
        } else {
            lore.set(lore.size() - 1, s);
        }
        itemMeta.setLore(lore);
    }

    public static String getLastLore(@Nonnull ItemStack item) {
        if(isItemNull(item)) {
            return "null";
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null || lore.size() == 0) {
            return null;
        }
        return lore.get(lore.size() - 1);
    }

    public static String getLastLore(@Nonnull ItemMeta itemMeta) {
        List<String> lore = itemMeta.getLore();
        if(lore == null || lore.size() == 0) {
            return null;
        }
        return lore.get(lore.size() - 1);
    }

    public static void setLore(@Nonnull ItemStack item, String... lore) {
        if(isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(Arrays.stream(lore).toList());
        item.setItemMeta(itemMeta);
    }

    public static ItemStack getDried(@Nonnull ItemStack item) {
        if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
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
