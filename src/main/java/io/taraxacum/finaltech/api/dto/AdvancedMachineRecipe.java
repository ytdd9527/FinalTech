package io.taraxacum.finaltech.api.dto;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class AdvancedMachineRecipe {
    @Nonnull
    private List<ItemStackWithWrapperAmount> inputList;
    @Nonnull
    private List<AdvancedRandomOutput> outputList;
    private int weightSum = 0;

    public AdvancedMachineRecipe(@Nonnull List<ItemStackWithWrapperAmount> inputList, @Nonnull List<AdvancedRandomOutput> outputList) {
        this.inputList = inputList;
        this.outputList = outputList;
        for (AdvancedRandomOutput advancedRandomOutput : this.outputList) {
            this.weightSum += advancedRandomOutput.getWeight();
        }
    }

    @Nonnull
    public List<ItemStackWithWrapperAmount> getInput() {
        return this.inputList;
    }

    @Nonnull
    public List<ItemStackWithWrapperAmount> getOutput() {
        int r = (int)(Math.random() * this.weightSum);
        for (AdvancedRandomOutput advancedRandomOutput : this.outputList) {
            if (r >= advancedRandomOutput.getWeight()) {
                r -= advancedRandomOutput.getWeight();
                continue;
            }
            return advancedRandomOutput.getOutputItem();
        }
        return new ArrayList<>();
    }

    @Nonnull
    public List<AdvancedRandomOutput> getOutputList() {
        return outputList;
    }

    public int getWeightSum() {
        return weightSum;
    }

    public static class AdvancedRandomOutput {
        @Nonnull
        private List<ItemStackWithWrapperAmount> outputItem;
        private int weight;

        public AdvancedRandomOutput(@Nonnull List<ItemStackWithWrapperAmount> outputItem, int weight) {
            this.outputItem = outputItem;
            this.weight = weight;
        }

        @Nonnull
        public List<ItemStackWithWrapperAmount> getOutputItem() {
            return outputItem;
        }

        public void setOutputItem(@Nonnull List<ItemStackWithWrapperAmount> outputItem) {
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
