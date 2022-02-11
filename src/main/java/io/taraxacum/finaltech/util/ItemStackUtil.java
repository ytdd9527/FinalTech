package io.taraxacum.finaltech.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.core.RandomMachineRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public final class ItemStackUtil {

    /**
     * 判断输入的物品是否为实际意义上的空物品
     * @param item 输入的物品
     * @return 是否为空物品
     */
    public static boolean isItemNull(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR) || item.getAmount() == 0;
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
                result[i++] = new ItemStack(item);
            }
        }
        return result;
    }

    /**
     * 把List<ItemStack>类型的对象解析并返回ItemStack[]类型的对象
     * 返回的ItemStack[]类型的对象，不包含空的ItemStack
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
        List<ItemStack> result = new ArrayList<>();
        for(ItemStack item : list) {
            if(isItemNull(item)) {
                continue;
            }
            int amount = item.getAmount();
            for(ItemStack resultItem : result) {
                if(SlimefunUtils.isItemSimilar(item, resultItem, true, false)) {
                    int count = Math.min(amount, resultItem.getMaxStackSize() - resultItem.getAmount());
                    resultItem.setAmount(resultItem.getAmount() + count);
                    amount -= count;
                    if(amount == 0) {
                        break;
                    }
                }
            }
            if(amount != 0) {
                result.add(new CustomItemStack(item, amount));
            }
        }
        return result;
    }

    /**
     * 把输入的数组中，相同的物品进行合并
     * @param items 输入的数组
     * @return 合并后的数组
     */
    @Nonnull
    public static ItemStack[] mergeSameItem(@Nonnull ItemStack[] items) {
        List<ItemStack> list = mergeSameItem(Arrays.stream(items).toList());
        ItemStack[] result = new ItemStack[list.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 把输入的两个列表，合并为一个列表
     * 会合并其中相同的物品
     * @param items1 输入的第一个列表
     * @param items2 输入的第二个列表
     * @return 合并后的列表
     */
    @Nonnull
    public static ItemStack[] mergeArray(@Nonnull ItemStack[] items1, @Nonnull ItemStack[] items2) {
        List<ItemStack> list = new ArrayList<>();
        list.addAll(Arrays.asList(items1));
        list.addAll(Arrays.asList(items2));
        list = mergeSameItem(list);
        ItemStack[] result = new ItemStack[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 使其中一个物品尝试堆叠至另一个物品
     * @param input 输入物品，由于堆叠这个物品的数量会减少
     * @param output 输出物品，由于堆叠这个物品的数量会增多
     * @return 实际进行了堆叠的数量
     */
    public static int stack(ItemStack input, ItemStack output) {
        if(SlimefunUtils.isItemSimilar(input, output, true, false) && output.getMaxStackSize() > output.getAmount()) {
            int amount = Math.min(input.getAmount(), output.getMaxStackSize() - output.getAmount());
            input.setAmount(input.getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
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
    public static int stack(ItemStack input, ItemStack output, int maxAmount) {
        if(SlimefunUtils.isItemSimilar(input, output, true, false) && output.getMaxStackSize() > output.getAmount()) {
            int amount = Math.min(maxAmount, Math.min(input.getAmount(), output.getMaxStackSize() - output.getAmount()));
            input.setAmount(input.getAmount() - amount);
            output.setAmount(output.getAmount() + amount);
            return amount;
        }
        return 0;
    }

    public static void addLoreToLast(ItemStack item, String lore) {
        if(isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null) {
            return;
        }
        List<String> lores = itemMeta.getLore();
        if(lores == null) {
            lores = new ArrayList<>();
        }
        lores.add(lore);
        itemMeta.setLore(lores);
        item.setItemMeta(itemMeta);
    }

    public static void removeLastLore(ItemStack item) {
        if(isItemNull(item) || !item.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lores = itemMeta.getLore();
        if(lores == null) {
            lores = new ArrayList<>();
        }
        lores = lores.subList(0, Math.max(lores.size() - 1, 0));
        itemMeta.setLore(lores);
        item.setItemMeta(itemMeta);
    }

    /**
     * 设置物品的最后一行lore
     * @param item 需要设置lore的物品
     * @param lore 需要设置的lore
     */
    public static void setLastLore(ItemStack item, String lore) {
        if(isItemNull(item)) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null) {
            return;
        }
        List<String> lores = itemMeta.getLore();
        if(lores == null || lores.size() == 0) {
            lores = new ArrayList<>();
            lores.add(lore);
        } else {
            lores.set(lores.size() - 1, lore);
        }
        itemMeta.setLore(lores);
        item.setItemMeta(itemMeta);
    }

    /**
     * 读取物品的最后一行lore
     * @param item 需要读取的物品
     * @return 物品最后一行lore
     */
    public static String getLastLore(ItemStack item) {
        if(isItemNull(item)) {
            return null;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null) {
            return null;
        }
        List<String> lores = itemMeta.getLore();
        if(lores == null || lores.size() == 0) {
            return null;
        }
        return lores.get(lores.size() - 1);
    }

    /**
     * 获取物品在烘干后的形式
     * 这个过程不会消耗物品
     * @param item 需要解析的物品
     * @return 解析后获得的物品
     */
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

    /**
     * 根据给定的输入和输出，生成工作配方
     * @param inputs 输入物品
     * @param outputs 输出物品
     * @return
     */
    public static final RandomMachineRecipe createRandomMachineRecipe(List<ItemStack> inputs, List<ItemStack> outputs) {
        boolean randomOutput = false;
        for(int m = 0; m < inputs.size(); m++) {
            for(int n = m+1; n < inputs.size(); n++) {
                if(SlimefunUtils.isItemSimilar(inputs.get(m), inputs.get(n), true, false) && inputs.get(m).getAmount() == inputs.get(n).getAmount()) {
                    randomOutput = true;
                }
                if(randomOutput) {
                    break;
                }
            }
            if(randomOutput) {
                break;
            }
        }
        if(inputs.size() == 0) {
            randomOutput = true;
        }
        return new RandomMachineRecipe(0, ItemStackUtil.toArray(inputs), ItemStackUtil.toArray(outputs), randomOutput);
    }

    // todo
    public static String toLocal(ItemStack itemStack) {
        Map<String, Object> serializeItem = itemStack.serialize();
//        String s = "";
//        Iterator iterator = serialize.keySet().iterator();
//        while (iterator.hasNext()) {
//            String key = iterator.next().toString();
//            String value = serialize.get(key).toString();
//            s += key;
//            s += "--key:value--";
//            s += value;
//            s += "Iterator:end";
//        }
//        Gson gson = new Gson(serialize);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Map<String, Object> serializeMeta = itemMeta.serialize();
        Bukkit.getLogger().info(serializeItem.toString());
        Bukkit.getLogger().info(serializeMeta.toString());
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        ItemMeta meta = gson.fromJson(serializeMeta.toString(), ItemMeta.class);
        itemStack.setItemMeta(meta);
        Bukkit.getLogger().info("ok!");
        return serializeItem.toString();
    }

    // todo
    public static ItemStack toItem(String local) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.fromJson(local, ItemStack.class);
//        return ItemStack.deserialize(jsonObject.toMap());
//        Map<String, Object> serialize = new LinkedHashMap<>();
//        for(String s : local.split("Iterator:end")) {
//            Bukkit.getLogger().info("key:" + s.split("--key:value--")[0]);
//            Bukkit.getLogger().info("value:" + s.split("--key:value--")[1]);
//            String key = s.split("--key:value--")[0];
//            Object value = null;
//            switch (key) {
//                case "v":
//                case "amount":
//                    value = Integer.parseInt(s.split("--key:value--")[1]);
//                    serialize.put(key, value);
//                    break;
//                case "damage":
//                    value = Short.parseShort(s.split("--key:value--")[1]);
//                    serialize.put(key, value);
//                    break;
//                case "meta":
//                default:
//                    value = s.split("--key:value--")[1];
//                    serialize.put(key, value);
//            }
//        }
//        Bukkit.getLogger().info(serialize.toString());
//        ItemStack itemStack = ItemStack.deserialize(serialize);
//        return itemStack;
    }
}
