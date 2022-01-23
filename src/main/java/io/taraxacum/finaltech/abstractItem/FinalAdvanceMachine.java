package io.taraxacum.finaltech.abstractItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.menu.AdvancedMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.FinalUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

public abstract class FinalAdvanceMachine extends FinalMachine {

    protected FinalAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    protected FinalAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        super(itemGroup, item, recipeType, recipe);
        this.recipeOutput = recipeOutput;
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        this.addon = addon;
        super.register(addon);
        getServer().getScheduler().runTask((Plugin)addon, () -> {
            this.registerDefaultRecipes();
        });
    }

    @Override
    protected final MachineMenu setMachineMenu() {
        return new AdvancedMachineMenu(this.getId(), this.getItemName(), this);
    }

    protected abstract void registerDefaultRecipes();

    @Override
    protected final void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CraftingOperation currentOperation = this.getMachineProcessor().getOperation(b);

        if(currentOperation == null) {
            FinalUtil.stock(inv, this.getInputSlots());
            MachineRecipe next = this.findNextRecipe(inv);
            if (next != null) {
                if(next.getTicks() == 0) {
                    ItemStack[] outputItems = next.getOutput();
                    int outputItemLength = outputItems.length;
                    for(int i = 0; i < outputItemLength; ++i) {
                        ItemStack output = outputItems[i];
                        inv.pushItem(output.clone(), this.getOutputSlots());
                    }
                    FinalUtil.stock(inv, this.getOutputSlots());
                } else {
                    this.getMachineProcessor().startOperation(b, new CraftingOperation(next));
                }
            }
        } else {
            if (currentOperation.isFinished()) {
                ItemStack[] outputItems = currentOperation.getResults();
                int outputItemLength = outputItems.length;

                for(int i = 0; i < outputItemLength; ++i) {
                    ItemStack output = outputItems[i];
                    inv.pushItem(output.clone(), this.getOutputSlots());
                }

                this.getMachineProcessor().endOperation(b);
            } else {
                currentOperation.addProgress(1);
            }
        }
    }

    protected MachineRecipe findNextRecipe(BlockMenu inv) {
        ItemStack quantityModule = inv.getItemInSlot(AdvancedMachineMenu.MODULE_SLOT);
        int amount = 1;
        if (quantityModule != null && SlimefunUtils.isItemSimilar(FinalTechItems.QUANTITY_MODULE, quantityModule, true, false)) {
            amount = quantityModule.getAmount();
        }

        ItemStack moduleIcon = inv.getItemInSlot(AdvancedMachineMenu.ICON_SLOT);
        ItemMeta itemMeta = moduleIcon.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if(lore == null || lore.size() < 2) {
            lore = new ArrayList<>();
            lore.add(0, "§7该机器可以进行升级");
            lore.add(1, "§7当前效率：" + amount);
        } else {
            lore.set(1, "§7当前效率：" + amount);
        }
        itemMeta.setLore(lore);
        moduleIcon.setItemMeta(itemMeta);

        ArrayList<ItemStack> inputs = new ArrayList<>();
        for (int slot : this.getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);
            if (item != null && item.getType() != Material.AIR && item.getAmount() != 0) {
                inputs.add(item);
            }
        }

        for (MachineRecipe recipe : recipes) {
            int matchAmount = amount;
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
            if (matchAmount != 0) {
                if (InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
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
                        if (outputSlots > this.getOutputSlots().length) {
                            matchAmount--;
                            continue;
                        }
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
                        if (InvUtils.fitAll(inv.toInventory(), resultOutputs, this.getOutputSlots())) {
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
