package io.taraxacum.finaltech.abstractItem;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.setup.FinalTechItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class FinalAdvanceMachine extends FinalBasicMachine {
    private final ItemStack progressBar = new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, "可升级模块", "&8这个机器可升级","&8在下方放入组件以升级"); // 机器工作图标

    protected FinalAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    protected FinalAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        super(itemGroup, item, recipeType, recipe, recipeOutput);
    }

    // I do it!
    protected MachineRecipe findNextRecipe(BlockMenu inv) {

        ItemStack quantityModule = inv.getItemInSlot(31);
        int amount = 1;
        if (quantityModule != null && SlimefunUtils.isItemSimilar(FinalTechItems.QUANTITY_MODULE, quantityModule, true, false)) {
            amount = quantityModule.getAmount();
        }

        ArrayList<ItemStack> inputs = new ArrayList<>();
        for (int slot : this.getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);
            if (item != null && item.getType() != Material.AIR && item.getAmount() != 0) {
                inputs.add(item);
            }
        }

        for (MachineRecipe recipe : recipes) {
            int matchAmount = amount;
            // 根据实际输入的物品数量，计算最多可以匹配的倍数
            for (ItemStack recipeInputItem : recipe.getInput()) {
                int matchItemAmount = 0;
                for (ItemStack slotInputItem : inputs) {
                    if (SlimefunUtils.isItemSimilar(recipeInputItem, slotInputItem, true, false)) {
                        matchItemAmount += slotInputItem.getAmount();
                    }
                }
                matchAmount = Math.min(matchAmount, matchItemAmount / recipeInputItem.getAmount());
                if (matchAmount == 0) {
                    break;
                }
            }
            // 匹配成功，计算输出的数量
            if (matchAmount != 0) {
                // 先计算一次1倍率情况下是否可以输出
                if (InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    // 再计算最大可以塞下的输出倍率
                    // todo 计算效率优化（二分查找、或优化计算方式）
                    while(matchAmount > 0) {
                        // 计算输出物品占用的格子数
                        int outputSlots = 0;
                        for (ItemStack item : recipe.getOutput()) {
                            outputSlots = outputSlots + 1 + item.getAmount() * matchAmount / item.getMaxStackSize();
                            if(item.getAmount() * matchAmount % item.getMaxStackSize() == 0) {
                                outputSlots--;
                            }
                        }
                        // 大于输出槽个数，无论如何都无法输出，直接pass
                        if (outputSlots > this.getOutputSlots().length) {
                            matchAmount--;
                            continue;
                        }
                        // 计算输出槽输出的物品
                        ItemStack[] resultOutputs = new ItemStack[outputSlots];
                        int outputPointer = 0;
                        for (ItemStack item : recipe.getOutput()) {
                            int resultAmount = item.getAmount() * matchAmount;
                            while(resultAmount > item.getMaxStackSize()) {
                                resultOutputs[outputPointer] = new ItemStack(item);
                                resultOutputs[outputPointer++].setAmount(item.getMaxStackSize());
                                resultAmount -= item.getMaxStackSize();
                            }
                            if(resultAmount != 0) {
                                resultOutputs[outputPointer] = new ItemStack(item);
                                resultOutputs[outputPointer++].setAmount(resultAmount);
                            }
                        }
                        // 计算输出物品数量是否可以塞得下输出槽
                        if (InvUtils.fitAll(inv.toInventory(), resultOutputs, this.getOutputSlots())) {
                            // 计算加工时所需要的物品
                            int inputSlots = 0;
                            for (ItemStack item : recipe.getInput()) {
                                inputSlots = inputSlots + 1 + item.getAmount() * matchAmount / item.getMaxStackSize();
                                if(item.getAmount() * matchAmount % item.getMaxStackSize() == 0) {
                                    inputSlots--;
                                }
                            }
                            ItemStack[]  resultInputs = new ItemStack[inputSlots];
                            int inputPointer = 0;
                            for (ItemStack item : recipe.getInput()) {
                                int resultAdmount = item.getAmount() * matchAmount;
                                while(resultAdmount > item.getMaxStackSize()) {
                                    resultInputs[inputPointer] = new ItemStack(item);
                                    resultInputs[inputPointer++].setAmount(item.getMaxStackSize());
                                    resultAdmount -= item.getMaxStackSize();
                                }
                                if(resultAdmount != 0) {
                                    resultInputs[inputPointer] = new ItemStack(item);
                                    resultInputs[inputPointer++].setAmount(resultAdmount);
                                }
                            }
                            // 消耗物品
                            for (ItemStack inputItem : recipe.getInput()) {
                                int consumeAmount = inputItem.getAmount() * matchAmount;
                                for (int slot : this.getInputSlots()) {
                                    ItemStack input = inv.getItemInSlot(slot);
                                    if (SlimefunUtils.isItemSimilar(input, inputItem, true, false)) {
                                        if (input.getAmount() <= consumeAmount) {
                                            consumeAmount -= input.getAmount();
                                            inv.consumeItem(slot, input.getAmount());
                                        } else {
                                            inv.consumeItem(slot, consumeAmount);
                                            consumeAmount = 0;
                                        }
                                    }
                                    if (consumeAmount == 0) {
                                        continue;
                                    }
                                }
                            }
                            // 返回加工配方
                            // 用于声明输出的产物
                            // 以及当加工过程中机器被破坏时，返还的物品
                            return new MachineRecipe(recipe.getTicks(), resultInputs, resultOutputs);
                        }
                        matchAmount--;
                    }
                }

            }
        }

        return null;
    }
}
