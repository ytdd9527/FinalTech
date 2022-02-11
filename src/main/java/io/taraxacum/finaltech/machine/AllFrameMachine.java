package io.taraxacum.finaltech.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.taraxacum.finaltech.abstractItem.machine.AbstractCargo;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.RandomMachineRecipe;
import io.taraxacum.finaltech.menu.AllFrameMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AllFrameMachine extends AbstractCargo {
    private List<RandomMachineRecipe> recipes;
    private String machineId;
    public AllFrameMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new AllFrameMachineMenu(this.getId(), this.getItemName(), this);
    }

    @Override
    protected void tick(Block block) {
        BlockMenu menu = BlockStorage.getInventory(block);
        ItemStack item = menu.getItemInSlot(AllFrameMachineMenu.MACHINE_SLOT);
        if(SlimefunUtils.isItemSimilar(item, FinalTechItems.ALL_FRAME_MACHINE, true, false)) {
            return;
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if(slimefunItem == null) {
            return;
        }
        if(slimefunItem.getBlockTicker() != null) {
            for(int i = 0; i < menu.getItemInSlot(AllFrameMachineMenu.MACHINE_SLOT).getAmount(); i++) {
                slimefunItem.getBlockTicker().tick(block.getRelative(BlockFace.DOWN), this, null);
            }
        }
//        if(slimefunItem instanceof RecipeDisplayItem) {
//            if(this.machineId == null || !this.machineId.equals(slimefunItem.getId())) {
//                final List<ItemStack> displayRecipes = ((RecipeDisplayItem) slimefunItem).getDisplayRecipes();
//                if(displayRecipes == null) {
//                    this.recipes = new ArrayList<>();
//                } else {
//                    this.recipes = parseRecipe(displayRecipes);
//                }
//                this.machineId = slimefunItem.getId();
//            }
//        }
//        int quantity = menu.getItemInSlot(AllFrameMachineMenu.MACHINE_SLOT).getAmount();
//
//        ArrayList<ItemStack> inputs = new ArrayList<>();
//        for (int slot : this.getInputSlots()) {
//            ItemStack item = menu.getItemInSlot(slot);
//            if (!ItemStackUtil.isItemNull(item) && item.getType() != Material.AIR && item.getAmount() != 0) {
//                inputs.add(item);
//            }
//        }
//
//        ArrayList<ItemStack> outputs = new ArrayList<>();
//        for (RandomMachineRecipe recipe : this.recipes) {
//            if(recipe.isNullInput()) {
//                for(ItemStack item : recipe.getOutput()) {
//                    outputs.add(item);
//                }
//                continue;
//            }
//            int matchAmount = quantity;
//            for(ItemStack recipeInputItem : recipe.getInput()) {
//                if(ItemStackUtil.isItemNull(recipeInputItem)) {
//                    continue;
//                }
//                int matchItemAmount = 0;
//                for(ItemStack slotInputItem : inputs) {
//                    if(SlimefunUtils.isItemSimilar(slotInputItem, recipeInputItem, true, false)) {
//                        matchItemAmount += slotInputItem.getAmount();
//                    }
//                }
//                matchAmount = Math.min(matchAmount, matchItemAmount / recipeInputItem.getAmount());
//                if(matchAmount == 0) {
//                    break;
//                }
//            }
//            ItemStack[] recipeOutput = recipe.getOutput();
//            if(matchAmount != 0 && InvUtils.fitAll(menu.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
//                while(matchAmount > 0) {
//                    int outputSlots = 0;
//                    for(ItemStack item : recipeOutput) {
//                        outputSlots = outputSlots + 1 + item.getAmount() * matchAmount / item.getMaxStackSize();
//                        if(item.getAmount() * matchAmount % item.getMaxStackSize() == 0) {
//                            outputSlots--;
//                        }
//                    }
//                    if(outputSlots > this.getOutputSlots().length) {
//                        matchAmount--;
//                        continue;
//                    }
//                    ItemStack[] resultOutputs = new ItemStack[outputSlots];
//                    int outputPointer = 0;
//                    for(ItemStack item : recipeOutput) {
//                        int resultAmount = item.getAmount() * matchAmount;
//                        while (resultAmount > item.getMaxStackSize()) {
//                            resultOutputs[outputPointer] = new ItemStack(item);
//                            resultOutputs[outputPointer++].setAmount(item.getMaxStackSize());
//                            resultAmount -= item.getMaxStackSize();
//                        }
//                        if(resultAmount != 0) {
//                            resultOutputs[outputPointer] = new ItemStack(item);
//                            resultOutputs[outputPointer++].setAmount(resultAmount);
//                        }
//                    }
//                    if(InvUtils.fitAll(menu.toInventory(), resultOutputs, this.getOutputSlots())) {
//                        int inputSlots = 0;
//                        for (ItemStack item : recipe.getInput()) {
//                            inputSlots = inputSlots + 1 + item.getAmount() * matchAmount / item.getMaxStackSize();
//                            if(item.getAmount() * matchAmount % item.getMaxStackSize() == 0) {
//                                inputSlots--;
//                            }
//                        }
//                        ItemStack[]  resultInputs = new ItemStack[inputSlots];
//                        int inputPointer = 0;
//                        for (ItemStack item : recipe.getInput()) {
//                            int resultAdmount = item.getAmount() * matchAmount;
//                            while(resultAdmount > item.getMaxStackSize()) {
//                                resultInputs[inputPointer] = new ItemStack(item);
//                                resultInputs[inputPointer++].setAmount(item.getMaxStackSize());
//                                resultAdmount -= item.getMaxStackSize();
//                            }
//                            if(resultAdmount != 0) {
//                                resultInputs[inputPointer] = new ItemStack(item);
//                                resultInputs[inputPointer++].setAmount(resultAdmount);
//                            }
//                        }
//                        for (ItemStack inputItem : recipe.getInput()) {
//                            int consumeAmount = inputItem.getAmount() * matchAmount;
//                            for (int slot : this.getInputSlots()) {
//                                ItemStack input = menu.getItemInSlot(slot);
//                                if (SlimefunUtils.isItemSimilar(input, inputItem, true, false)) {
//                                    if (input.getAmount() <= consumeAmount) {
//                                        consumeAmount -= input.getAmount();
//                                        menu.consumeItem(slot, input.getAmount());
//                                    } else {
//                                        menu.consumeItem(slot, consumeAmount);
//                                        consumeAmount = 0;
//                                    }
//                                }
//                                if (consumeAmount == 0) {
//                                    continue;
//                                }
//                            }
//                        }
//                        for(ItemStack item : resultOutputs) {
//                            menu.pushItem(item, this.getOutputSlots());
//                        }
//                        return;
//                    }
//                    matchAmount--;
//                }
//            }
//        }
//        if(outputs.size() > 0) {
//            for(ItemStack item : outputs) {
//                menu.pushItem(new CustomItemStack(item, quantity), this.getOutputSlots());
//            }
//        }
    }
}
