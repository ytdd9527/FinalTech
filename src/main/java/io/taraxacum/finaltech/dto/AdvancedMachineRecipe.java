package io.taraxacum.finaltech.dto;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 仅用于机器的合成工序
 * @author Final_ROOT
 */
public class AdvancedMachineRecipe {
    private List<ItemStackWithWrapper> inputList;
    private List<AdvancedRandomOutput> outputList;
    private int weightSum = 0;

    public AdvancedMachineRecipe(List<ItemStackWithWrapper> inputList, List<AdvancedRandomOutput> outputList) {
        this.inputList = inputList;
        this.outputList = outputList;
        for (AdvancedRandomOutput advancedRandomOutput : this.outputList) {
            this.weightSum += advancedRandomOutput.getWeight();
        }
    }

    public List<ItemStackWithWrapper> getInput() {
        return inputList;
    }

    public List<ItemStackWithWrapper> getOutput() {
        int r = (int)(Math.random() * this.weightSum);
        for (AdvancedRandomOutput advancedRandomOutput : this.outputList) {
            if (r > advancedRandomOutput.getWeight()) {
                r -= advancedRandomOutput.getWeight();
                continue;
            }
            return advancedRandomOutput.getOutputItem();
        }
        return new ArrayList<>();
    }

    public List<AdvancedRandomOutput> getOutputList() {
        return outputList;
    }

    public int getWeightSum() {
        return weightSum;
    }

    public static class AdvancedRandomOutput {
        private List<ItemStackWithWrapper> outputItem;
        private int weight;

        public AdvancedRandomOutput(List<ItemStackWithWrapper> outputItem, int weight) {
            this.outputItem = outputItem;
            this.weight = weight;
        }

        public List<ItemStackWithWrapper> getOutputItem() {
            return outputItem;
        }

        public void setOutputItem(List<ItemStackWithWrapper> outputItem) {
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
