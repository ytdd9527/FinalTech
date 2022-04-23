package io.taraxacum.finaltech.api;

import io.taraxacum.finaltech.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class RandomMachineRecipe extends MachineRecipe {
    private final List<RandomOutput> randomOutputList;
    private int weightSum = 0;

    public RandomMachineRecipe(MachineRecipe machineRecipe, List<RandomOutput> randomOutputList) {
        super(machineRecipe.getTicks(), machineRecipe.getInput(), machineRecipe.getOutput());
        this.randomOutputList = randomOutputList;
        for (RandomOutput randomOutput : this.randomOutputList) {
            this.weightSum += randomOutput.weight;
        }
    }

    public RandomMachineRecipe(int ticks, ItemStack[] input, List<RandomOutput> randomOutputList) {
        super(ticks, input, new ItemStack[0]);
        this.randomOutputList = randomOutputList;
        for (RandomOutput randomOutput : this.randomOutputList) {
            this.weightSum += randomOutput.weight;
        }
    }

    @Override
    public ItemStack[] getOutput() {
        int r = (int)(Math.random() * this.weightSum);
        for (RandomOutput randomOutput : this.randomOutputList) {
            if (r > randomOutput.getWeight()) {
                r -= randomOutput.getWeight();
                continue;
            }
            return ItemStackUtil.calItemArray(randomOutput.getOutputItem());
        }
        return new ItemStack[0];
    }

    public ItemStack[] getAllOutput() {
        List<ItemStack> itemList = new ArrayList<>();
        for (RandomOutput randomOutput : this.randomOutputList) {
            itemList.addAll(randomOutput.outputItem);
        }
        return ItemStackUtil.calItemArray(itemList);
    }

    public List<RandomOutput> getRandomOutputList() {
        return randomOutputList;
    }

    public static class RandomOutput {
        private List<ItemStack> outputItem;
        private int weight;

        public RandomOutput(List<ItemStack> outputItem, int weight) {
            this.outputItem = outputItem;
            this.weight = weight;
        }

        public RandomOutput(ItemStack[] outputItem, int weight) {
            this.outputItem = Arrays.stream(outputItem).toList();
            this.weight = weight;
        }

        public RandomOutput(ItemStack outputItem, int weight) {
            this.outputItem = Arrays.asList(outputItem);
            this.weight = weight;
        }

        public List<ItemStack> getOutputItem() {
            return outputItem;
        }

        public void setOutputItem(List<ItemStack> outputItem) {
            this.outputItem = outputItem;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
