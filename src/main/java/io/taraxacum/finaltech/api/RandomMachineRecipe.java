package io.taraxacum.finaltech.api;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
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

    @Override
    public ItemStack[] getOutput() {
        int r = (int)(Math.random() * this.weightSum);
        for (RandomOutput randomOutput : this.randomOutputList) {
            if (r > randomOutput.getWeight()) {
                r -= randomOutput.getWeight();
                continue;
            }
            return randomOutput.getOutputItem().toArray(new ItemStack[0]);
        }
        return new ItemStack[0];
    }

    public ItemStack[] getAllOutput() {
        List<ItemStack> itemList = new ArrayList<>();
        for (RandomOutput randomOutput : this.randomOutputList) {
            itemList.addAll(randomOutput.outputItem);
        }
        return itemList.toArray(new ItemStack[0]);
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
