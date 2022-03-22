package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.setup.register.FinalTechItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Final_ROOT
 */
public final class MachineUtil {
    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_PLACER_ALLOW = new BlockPlaceHandler(true) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {}
    };

    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_PLACER_DENY = new BlockPlaceHandler(false) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {}
    };

    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_DENY = new BlockPlaceHandler(false) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
            blockPlaceEvent.setCancelled(true);
        }
    };

    public static int itemCount(@Nonnull Inventory inventory, int[] slots) {
        ItemStack itemStack;
        int count = 0;
        for(int slot : slots) {
            itemStack = inventory.getItem(slot);
            if(!ItemStackUtil.isItemNull(itemStack)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isFull(@Nonnull Inventory inventory, int[] slots) {
        ItemStack itemStack;
        for(int slot : slots) {
            itemStack = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(itemStack) || itemStack.getAmount() < itemStack.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(@Nonnull Inventory inventory, int[] slots) {
        ItemStack itemStack;
        for(int slot : slots) {
            itemStack = inventory.getItem(slot);
            if(!ItemStackUtil.isItemNull(itemStack)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 堆叠指定容器指定范围的物品
     * @param menu 容器
     * @param slots 指定范围
     */
    public static void stockSlots(@Nonnull BlockMenu menu, int[] slots) {
        LinkedList<ItemStack> items = new LinkedList<>();
        for(int slot : slots) {
            ItemStack item1 = menu.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(item1)) {
                continue;
            }
            for (ItemStack item2 : items) {
                if (ItemStackUtil.isItemNull(item2)) {
                    continue;
                }
                ItemStackUtil.stack(item1, item2);
            }
            if(item1.getAmount() > 0 && item1.getAmount() < item1.getMaxStackSize()) {
                items.add(item1);
            } else {
                items.remove(item1);
            }
        }
    }

    /**
     * 效率更高的检测配方是否匹配的方法
     * @param items
     * @param machineRecipe
     * @return
     */
    public static boolean isMatchRecipe(@Nonnull List<ItemStack> items, @Nonnull MachineRecipe machineRecipe) {
        Map<ItemStack, ItemStackWrapper> map = ItemStackUtil.wrap(items);
        boolean match = false;
        for(ItemStack recipeItem : machineRecipe.getInput()) {
            ItemStackWrapper recipeItemWrap = ItemStackWrapper.wrap(recipeItem);
            for(Map.Entry<ItemStack, ItemStackWrapper> entry : map.entrySet()) {
                if(entry.getKey().getAmount() >= recipeItem.getAmount() && ItemStackUtil.isItemSimilar(entry.getValue(), recipeItemWrap)){
                    match = true;
                }
            }
            if(!match) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public static MachineRecipe matchRecipe(@Nonnull List<ItemStack> items, @Nonnull List<MachineRecipe> machineRecipes, int offset) {
        Map<ItemStack, ItemStackWrapper> map = ItemStackUtil.wrap(items);
        if(map.size() == 0) {
            return null;
        }
        if(offset < 0) {
            offset = 0;
        }
        int count;
        MachineRecipe machineRecipe;
        int length = machineRecipes.size();
        boolean match;
        for(int i = 0; i < length; i++) {
            machineRecipe = machineRecipes.get((i + offset) % length);
            count = 0;
            for(ItemStack recipeItem : machineRecipe.getInput()) {
                match = false;
                ItemStackWrapper recipeItemWrap = ItemStackWrapper.wrap(recipeItem);
                for(Map.Entry<ItemStack, ItemStackWrapper> entry : map.entrySet()) {
                    if(entry.getKey().getAmount() >= recipeItem.getAmount() && ItemStackUtil.isItemSimilar(entry.getValue(), recipeItemWrap)){
                        match = true;
                        count++;
                    }
                }
                if(!match) {
                    break;
                }
            }
            if(count == machineRecipe.getInput().length) {
                return machineRecipe;
            }
        }
        return null;
    }

    @Nullable
    public static MachineRecipe matchRecipe(@Nonnull List<ItemStack> items, @Nonnull List<MachineRecipe> machineRecipes) {
        return matchRecipe(items, machineRecipes, 0);
    }

    public static MachineRecipe matchRecipe(@Nonnull List<ItemStack> items, @Nonnull List<MachineRecipe> machineRecipes, int offset, @Nonnull Inventory inventory, int[] slots, int maxCount) {
        Map<ItemStack, ItemStackWrapper> map = ItemStackUtil.wrap(items);
        int length = machineRecipes.size();
        if(offset < 0) {
            offset = 0;
        }
        for(int i = 0; i < length; i++) {
            MachineRecipe machineRecipe = machineRecipes.get((i + offset) % length);
            int matchAmount = maxCount;
            for(ItemStack inputItem : machineRecipe.getInput()) {
                int matchItemAmount = 0;
                ItemStackWrapper inputItemWrap = ItemStackWrapper.wrap(inputItem);
                for(Map.Entry<ItemStack, ItemStackWrapper> entry : map.entrySet()) {
                    if(ItemStackUtil.isItemSimilar(entry.getValue(), inputItemWrap)) {
                        matchItemAmount += entry.getKey().getAmount();
                    }
                }
                matchAmount = Math.min(matchAmount, matchItemAmount / inputItem.getAmount());
                if(matchAmount == 0) {
                    break;
                }
            }
            ItemStack[] outputItems = machineRecipe.getOutput();
            if(matchAmount != 0 && (matchAmount == 1 || InvUtils.fitAll(inventory, outputItems, slots))) {
                while (matchAmount > 0) {
                    int outputSlots = 0;
                    for(ItemStack outputItem : outputItems) {
                        outputSlots += 1 + outputItem.getAmount() * matchAmount / outputItem.getMaxStackSize();
                        if(outputItem.getAmount() * matchAmount % outputItem.getMaxStackSize() == 0) {
                            outputSlots--;
                        }
                    }
                    if(outputSlots > slots.length) {
                        matchAmount--;
                        continue;
                    }
                    ItemStack[] resultOutputs = new ItemStack[outputSlots];
                    int outputPointer = 0;
                    for(ItemStack outputItem : outputItems) {
                        int resultAmount = outputItem.getAmount() * matchAmount;
                        while (resultAmount > outputItem.getMaxStackSize()) {
                            resultOutputs[outputPointer] = new ItemStack(outputItem);
                            resultOutputs[outputPointer++].setAmount(outputItem.getMaxStackSize());
                            resultAmount -= outputItem.getMaxStackSize();
                        }
                        if(resultAmount != 0) {
                            resultOutputs[outputPointer] = new ItemStack(outputItem);
                            resultOutputs[outputPointer++].setAmount(resultAmount);
                        }
                    }
                    if(InvUtils.fitAll(inventory, resultOutputs, slots)) {
                        int inputSlots = 0;
                        for(ItemStack item : machineRecipe.getInput()) {
                            inputSlots = inputSlots + 1 + item.getAmount() * matchAmount / item.getMaxStackSize();
                            if(item.getAmount() * matchAmount % item.getMaxStackSize() == 0) {
                                inputSlots--;
                            }
                        }
                        ItemStack[]  resultInputs = new ItemStack[inputSlots];
                        int inputPointer = 0;
                        for (ItemStack item : machineRecipe.getInput()) {
                            int resultAmount = item.getAmount() * matchAmount;
                            while(resultAmount > item.getMaxStackSize()) {
                                resultInputs[inputPointer] = new ItemStack(item);
                                resultInputs[inputPointer++].setAmount(item.getMaxStackSize());
                                resultAmount -= item.getMaxStackSize();
                            }
                            if(resultAmount != 0) {
                                resultInputs[inputPointer] = new ItemStack(item);
                                resultInputs[inputPointer++].setAmount(resultAmount);
                            }
                        }
                        return new MachineRecipe(machineRecipe.getTicks(), resultInputs, resultOutputs);
                    }
                    matchAmount--;
                }
            }
        }
        return null;
    }

    public static MachineRecipe matchRecipe(@Nonnull List<ItemStack> items, @Nonnull List<MachineRecipe> machineRecipes, @Nonnull Inventory inventory, int[] slots, int maxCount) {
        return matchRecipe(items, machineRecipes, 0, inventory, slots, maxCount);
    }

    /**
     * 根据指定的输入物品、工作配方、输出槽大小，计算最大可以处理的次数
     * 匹配成功时，物品会被消耗
     * @param items 输入物品
     * @param machineRecipe 需要匹配的工作配方
     * @param inv 输出的容器
     * @param slots 输出容器的可输出范围
     * @param maxCount 最大可以处理的次数不超过此数值
     * @return 根据可匹配数量，提高了输入和输出物品数量的工作配方
     */
    @Nullable
    @Deprecated
    public static MachineRecipe matchRecipe(@Nonnull List<ItemStack> items, @Nonnull MachineRecipe machineRecipe, @Nonnull Inventory inv, int[] slots, int maxCount) {
        int matchAmount = maxCount;
        for(ItemStack inputItem : machineRecipe.getInput()) {
            int matchItemAmount = 0;
            for(ItemStack item : items) {
                if(ItemStackUtil.isItemSimilar(item, inputItem)) {
                    matchItemAmount += item.getAmount();
                }
            }
            matchAmount = Math.min(matchAmount, matchItemAmount / inputItem.getAmount());
            if(matchItemAmount == 0) {
                return null;
            }
        }
        ItemStack[] outputItems = machineRecipe.getOutput();
        if(matchAmount != 0 && (matchAmount == 1 || InvUtils.fitAll(inv, outputItems, slots))) {
            while (matchAmount > 0) {
                int outputSlots = 0;
                for(ItemStack outputItem : outputItems) {
                    outputSlots += 1 + outputItem.getAmount() * matchAmount / outputItem.getMaxStackSize();
                    if(outputItem.getAmount() * matchAmount % outputItem.getMaxStackSize() == 0) {
                        outputSlots--;
                    }
                }
                if(outputSlots > slots.length) {
                    matchAmount--;
                    continue;
                }
                ItemStack[] resultOutputs = new ItemStack[outputSlots];
                int outputPointer = 0;
                for(ItemStack outputItem : outputItems) {
                    int resultAmount = outputItem.getAmount() * matchAmount;
                    while (resultAmount > outputItem.getMaxStackSize()) {
                        resultOutputs[outputPointer++] = new CustomItemStack(outputItem, outputItem.getMaxStackSize());
                        resultAmount -= outputItem.getMaxStackSize();
                    }
                    if(resultAmount != 0) {
                        resultOutputs[outputPointer++] = new CustomItemStack(outputItem, resultAmount);
                    }
                }
                if(InvUtils.fitAll(inv, resultOutputs, slots)) {
                    int inputSlots = 0;
                    for(ItemStack item : machineRecipe.getInput()) {
                        inputSlots = inputSlots + 1 + item.getAmount() * matchAmount / item.getMaxStackSize();
                        if(item.getAmount() * matchAmount % item.getMaxStackSize() == 0) {
                            inputSlots--;
                        }
                    }
                    ItemStack[]  resultInputs = new ItemStack[inputSlots];
                    int inputPointer = 0;
                    for (ItemStack item : machineRecipe.getInput()) {
                        int resultAmount = item.getAmount() * matchAmount;
                        while(resultAmount > item.getMaxStackSize()) {
                            resultInputs[inputPointer++] = new CustomItemStack(item, item.getMaxStackSize());
                            resultAmount -= item.getMaxStackSize();
                        }
                        if(resultAmount != 0) {
                            resultInputs[inputPointer++] = new CustomItemStack(item, resultAmount);
                        }
                    }
                    for (ItemStack inputItem : machineRecipe.getInput()) {
                        int consumeAmount = inputItem.getAmount() * matchAmount;
                        for (ItemStack item : items) {
                            if (ItemStackUtil.isItemSimilar(item, inputItem)) {
                                if (item.getAmount() <= consumeAmount) {
                                    consumeAmount -= item.getAmount();
                                    ItemUtils.consumeItem(item, item.getAmount(), false);
                                } else {
                                    ItemUtils.consumeItem(item, consumeAmount, false);
                                    consumeAmount = 0;
                                }
                            }
                            if (consumeAmount == 0) {
                                break;
                            }
                        }
                    }
                    return new MachineRecipe(machineRecipe.getTicks(), resultInputs, resultOutputs);
                }
                matchAmount--;
            }
        }
        return null;
    }

    public static void consumeItem(@Nonnull List<ItemStack> items, @Nonnull ItemStack[] matchItems) {
        List<ItemStackWithWrapper> itemList = new ArrayList<>(items.size());
        List<ItemStackWithWrapper> matchItemList = ItemStackUtil.parseItemWithAmount(matchItems);
        for(ItemStack item : items) {
            itemList.add(new ItemStackWithWrapper(item));
        }
        for(ItemStackWithWrapper matchItem : matchItemList) {
            int amount = matchItem.getAmount();
            for(ItemStackWithWrapper item : itemList) {
                if(item.getItemStack().getAmount() == 0) {
                    continue;
                }
                if(ItemStackUtil.isItemSimilar(item.getItemStackWrapper(), matchItem.getItemStackWrapper())) {
                    int count = Math.min(amount, item.getItemStack().getAmount());
                    item.getItemStack().setAmount(item.getItemStack().getAmount() - count);
                    if(item.getItemStack().getAmount() == 0) {
                        item.updateItemStackWrapper();
                    }
                    amount -= count;
                    if(amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    public static int maxMatch(@Nonnull Inventory inventory, int[] slots, @Nonnull ItemStack[] items, int count) {
        List<ItemStackWithWrapper> matchList = ItemStackUtil.parseItemWithAmount(items);
        List<ItemStackWithWrapper> itemList = new ArrayList<>(slots.length);
        int emptySlot = 0;
        for(int slot : slots) {
            ItemStack item = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(item)) {
                emptySlot++ ;
            } else if(item.getAmount() < item.getMaxStackSize()) {
                itemList.add(new ItemStackWithWrapper(item));
            }
        }
        int empty;
        int amount;
        while (count > 0) {
            empty = emptySlot;
            for(ItemStackWithWrapper matchItem : matchList) {
                amount = matchItem.getAmount() * count;
                for(ItemStackWithWrapper item : itemList) {
                    if(item.getAmount() < item.getItemStack().getMaxStackSize() && ItemStackUtil.isItemSimilar(item.getItemStackWrapper(), matchItem.getItemStackWrapper())) {
                        amount -= item.getAmount();
                    }
                }
                if(amount > 0) {
                    empty -= (amount - 1) / matchItem.getItemStack().getMaxStackSize() + 1;
                    if(empty < 0) {
                        break;
                    }
                }
            }
            if(empty >= 0) {
                return count;
            }
            count--;
        }

        return 0;
    }

    public static int updateQuantityModule(@Nonnull BlockMenu blockMenu, int quantityModuleSlot, int infoSlot) {
        ItemStack item = blockMenu.getItemInSlot(quantityModuleSlot);
        int amount = 1;
        if(ItemStackUtil.isItemSimilar(item, FinalTechItems.QUANTITY_MODULE)) {
            amount = item.getAmount();
        } else if(ItemStackUtil.isItemSimilar(item, FinalTechItems.QUANTITY_MODULE_V2)) {
            amount = item.getAmount() * (item.getAmount() + 1) / 2;
        }
        ItemStack infoItem = blockMenu.getItemInSlot(infoSlot);
        if(!infoItem.hasItemMeta()) {
            return amount;
        }
        ItemMeta itemMeta = infoItem.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
            lore = new LinkedList<>();
        }
        if(lore.size() == 0) {
            lore.add("§7该机器可以通过添加[数量组件]进行升级");
        }
        if(lore.size() == 1) {
            lore.add("§7当前效率=" + amount);
        } else {
            lore.set(1, "§7当前效率=" + amount);
        }
        itemMeta.setLore(lore);
        infoItem.setItemMeta(itemMeta);
        return amount;
    }
}
