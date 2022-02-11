package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.setup.FinalTechItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class MachineUtil {

    /**
     * 堆叠指定容器指定范围的物品
     * @param menu 容器
     * @param slots 指定范围
     */
    public static void stockSlots(BlockMenu menu, int[] slots) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for(int slot : slots) {
            ItemStack item1 = menu.getItemInSlot(slot);
            if(ItemStackUtil.isItemNull(item1)) {
                continue;
            }
            for (ItemStack item2 : itemStacks) {
                if (ItemStackUtil.isItemNull(item2)) {
                    continue;
                }
                ItemStackUtil.stack(item1, item2);
            }
            if(item1.getAmount() > 0 && item1.getAmount() < item1.getMaxStackSize()) {
                itemStacks.add(item1);
            }
        }
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
    public static MachineRecipe matchRecipe(List<ItemStack> items, MachineRecipe machineRecipe, Inventory inv, int[] slots, int maxCount) {
        int matchAmount = maxCount;
        for(ItemStack inputItem : machineRecipe.getInput()) {
            int matchItemAmount = 0;
            for(ItemStack item : items) {
                if(SlimefunUtils.isItemSimilar(item, inputItem, true, false)) {
                    matchItemAmount += item.getAmount();
                }
            }
            matchAmount = Math.min(matchAmount, matchItemAmount / inputItem.getAmount());
        }
        ItemStack[] outputItems = machineRecipe.getOutput();
        if(matchAmount != 0 && InvUtils.fitAll(inv, outputItems, slots)) {
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
                        resultOutputs[outputPointer] = new CustomItemStack(outputItem, resultAmount);
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
                            if (SlimefunUtils.isItemSimilar(item, inputItem, true, false)) {
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

    @Nonnull
    public static int updateQuantityModule(@Nonnull BlockMenu blockMenu, @Nonnull int quantityModuleSlot, @Nonnull int infoSlot) {
        ItemStack item = blockMenu.getItemInSlot(quantityModuleSlot);
        int amount = 1;
        if(!ItemStackUtil.isItemNull(item) && SlimefunUtils.isItemSimilar(item, FinalTechItems.QUANTITY_MODULE, true, false)) {
            amount = item.getAmount();
        }
        ItemStack infoItem = blockMenu.getItemInSlot(infoSlot);
        if(!infoItem.hasItemMeta()) {
            return amount;
        }
        ItemMeta itemMeta = infoItem.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null) {
            lore = new ArrayList<>();
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
