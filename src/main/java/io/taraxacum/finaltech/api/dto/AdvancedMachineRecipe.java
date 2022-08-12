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
    private final List<ItemAmountWrapper> inputList;
    @Nonnull
    private final List<AdvancedRandomOutput> outputList;
    private int weightSum = 0;

    public AdvancedMachineRecipe(@Nonnull List<ItemAmountWrapper> inputList, @Nonnull List<AdvancedRandomOutput> outputList) {
        this.inputList = inputList;
        this.outputList = outputList;
        for (AdvancedRandomOutput advancedRandomOutput : this.outputList) {
            this.weightSum += advancedRandomOutput.getWeight();
        }
    }

    @Nonnull
    public List<ItemAmountWrapper> getInput() {
        return this.inputList;
    }

    @Nonnull
    public List<ItemAmountWrapper> getOutput() {
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
        return this.outputList;
    }

    public int getWeightSum() {
        return this.weightSum;
    }

    public record AdvancedRandomOutput(@Nonnull List<ItemAmountWrapper> outputItem, int weight) {
        public AdvancedRandomOutput(@Nonnull List<ItemAmountWrapper> outputItem, int weight) {
            this.outputItem = outputItem;
            this.weight = weight;
        }

        @Nonnull
        public List<ItemAmountWrapper> getOutputItem() {
            return outputItem;
        }

        public int getWeight() {
            return weight;
        }
    }
}
